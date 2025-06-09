package support;

import java.io.*;
import java.nio.file.*;

public class DeviceManager {

    private static final String AVD_NAME = "ciEmulator";
    // ATUALIZE a system image para a disponível conforme o log:
    // Antes:
    // private static final String SYSTEM_IMAGE = "system-images;android-31;google_apis;x86_64";
    private static final String SYSTEM_IMAGE = "system-images;android-36;google_apis_playstore;arm64-v8a";
    private static final String DEVICE_PROFILE = "pixel";
    private static Process emulatorProcess;
    private static String androidHome = null;

    static {
        androidHome = System.getenv("ANDROID_HOME");
        if (androidHome == null) {
            String os = System.getProperty("os.name").toLowerCase();
            String userHome = System.getProperty("user.home");
            if (os.contains("win")) {
                androidHome = userHome + "\\AppData\\Local\\Android\\Sdk";
            } else if (os.contains("mac")) {
                androidHome = userHome + "/Library/Android/sdk";
            } else {
                androidHome = userHome + "/Android/Sdk";
            }
        }
    }

    public static void startEmulator() throws IOException, InterruptedException {
        String avdHome = System.getProperty("user.home")
                + File.separator + ".android"
                + File.separator + "avd"
                + File.separator + AVD_NAME + ".avd";

        // -- Check and create AVD if needed
        if (!Files.exists(Paths.get(avdHome))) {
            String avdManager = androidHome + "/cmdline-tools/latest/bin/avdmanager";
            if (!Files.exists(Paths.get(avdManager))) {
                throw new FileNotFoundException("avdmanager not found at: " + avdManager);
            }
            String[] createAvdCmd = {
                    avdManager,
                    "create", "avd",
                    "-n", AVD_NAME,
                    "-k", SYSTEM_IMAGE,
                    "--device", DEVICE_PROFILE
            };
            ProcessBuilder createAvdBuilder = new ProcessBuilder(createAvdCmd);

            // Redirecione saída de erro para ajudar na depuração
            createAvdBuilder.redirectErrorStream(true);

            Process process = createAvdBuilder.start();
            // Leia a saída do processo para evitar bloqueios e diagnosticar erros
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
                 BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(process.getOutputStream()))) {
                String line;
                boolean wroteNo = false;
                while ((line = reader.readLine()) != null) {
                    System.out.println("[AVDMANAGER] " + line);
                    // Caso apareça prompt de overwrite, escreva "no"
                    if (!wroteNo && line.toLowerCase().contains("overwrite")) {
                        writer.write("no\n");
                        writer.flush();
                        wroteNo = true;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            int exitCode = process.waitFor();
            if (exitCode != 0) {
                throw new IOException("Failed to create AVD. Exit code: " + exitCode);
            }
        }

        String emulatorPath = androidHome + "/emulator/emulator";
        if (!Files.exists(Paths.get(emulatorPath))) {
            throw new FileNotFoundException("emulator not found at: " + emulatorPath);
        }
        ProcessBuilder builder = new ProcessBuilder(
                emulatorPath, "-avd", AVD_NAME, "-no-window", "-no-audio"
                //        emulatorPath, "-avd", AVD_NAME, "-no-audio"
        );
        builder.redirectOutput(ProcessBuilder.Redirect.INHERIT);
        builder.redirectError(ProcessBuilder.Redirect.INHERIT); // também capture erros do emulador
        emulatorProcess = builder.start();

        waitForDevice();
    }

    public static void waitForDevice() throws IOException, InterruptedException {
        String adbPath = androidHome + "/platform-tools/adb";
        if (!Files.exists(Paths.get(adbPath))) {
            throw new FileNotFoundException("adb not found at: " + adbPath);
        }

        ProcessBuilder builder = new ProcessBuilder(adbPath, "wait-for-device");
        builder.redirectErrorStream(true);
        Process waitProc = builder.start();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(waitProc.getInputStream()))) {
            while (reader.readLine() != null) {} // read to EOF
        }
        waitProc.waitFor();

        boolean booted = false;
        while (!booted) {
            ProcessBuilder pb = new ProcessBuilder(adbPath, "shell", "getprop", "sys.boot_completed");
            Process proc = pb.start();
            String output;
            try (BufferedReader br = new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
                output = br.readLine();
            }
            proc.waitFor();
            if ("1".equals(output != null ? output.trim() : "")) {
                booted = true;
            } else {
                Thread.sleep(2000);
            }
        }
    }

    public static void stopEmulator() throws IOException, InterruptedException {
        String adbPath = androidHome + "/platform-tools/adb";
        if (!Files.exists(Paths.get(adbPath))) {
            throw new FileNotFoundException("adb not found at: " + adbPath);
        }
        new ProcessBuilder(adbPath, "emu", "kill").start();
        if (emulatorProcess != null) {
            emulatorProcess.destroy();
        }
    }
}