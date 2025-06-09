package support;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Classe utilitária para gerenciar o ciclo de vida do servidor Appium.
 */
public class AppiumServerManager {

    private static final String DEFAULT_APPIUM_URL = "http://localhost:4723/status";
    private static final int MAX_ATTEMPTS = 30;
    private static final int WAIT_INTERVAL_MS = 1000;

    private static Process appiumProcess;

    /**
     * Inicia o servidor Appium se ainda não estiver em execução.
     *
     * @throws IOException Caso ocorra erro ao iniciar o processo.
     */
    public static void startAppiumServer() throws IOException {
        if (appiumProcess == null) {
            System.out.println("Iniciando Appium via ProcessBuilder...");
            appiumProcess = new ProcessBuilder("appium")
                    .redirectErrorStream(true)
                    .inheritIO()
                    .start();

            // Pequeno delay inicial para permitir que o processo inicialize a porta
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

            waitUntilAppiumIsReady();
        }
    }

    /**
     * Aguarda até que o Appium esteja aceitando conexões.
     */
    private static void waitUntilAppiumIsReady() {
        for (int i = 0; i < MAX_ATTEMPTS; i++) {
            if (isAppiumRunning()) {
                System.out.println("Appium iniciado com sucesso e pronto para uso!");
                return;
            }
            try {
                Thread.sleep(WAIT_INTERVAL_MS);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                throw new RuntimeException("Aguardando Appium foi interrompido.", e);
            }
        }
        throw new RuntimeException("O servidor Appium não ficou pronto a tempo.");
    }

    /**
     * Verifica se o Appium está rodando e respondendo.
     *
     * @return true se estiver rodando; false caso contrário.
     */
    public static boolean isAppiumRunning() {
        try {
            URL url = new URL(DEFAULT_APPIUM_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setConnectTimeout(1000);
            connection.setReadTimeout(1000);
            connection.setRequestMethod("GET");

            int responseCode = connection.getResponseCode();

            if (responseCode != 200) {
                System.out.println("Appium ainda não pronto. Código HTTP: " + responseCode);
                return false;
            }

            try (var reader = new java.io.BufferedReader(new java.io.InputStreamReader(connection.getInputStream()))) {
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }

                String json = response.toString();
                // Verifica se contém "ready":true
                boolean ready = json.contains("\"ready\":true");
                System.out.println("Verificando resposta do Appium: ready=" + ready);
                return ready;
            }
        } catch (IOException e) {
            System.out.println("Erro ao verificar Appium: " + e.getMessage());
            return false;
        }
    }

    /**
     * Para o servidor Appium caso esteja em execução.
     */
    public static void stopAppiumServer() {
        if (appiumProcess != null) {
            appiumProcess.destroy();
            appiumProcess = null;
            System.out.println("Appium parado.");
        }
    }
}