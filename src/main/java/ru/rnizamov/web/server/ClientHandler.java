package ru.rnizamov.web.server;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    public ClientHandler(HttpServer server, Socket socket) {
        server.getServ().execute(() -> {
            System.out.println("Подключился новый клиент");
            try {
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                if (n > 0) {
                    String rawRequest = new String(buffer, 0, n);
                    HttpRequest request = new HttpRequest(rawRequest);
                    request.info(true);
                    server.getDispatcher().execute(request, socket.getOutputStream());
                }
            } catch (Exception e) {
                System.out.println("Возникла ошибка при обработке подключившегося клиента");
                e.printStackTrace();
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        });
    }
}
