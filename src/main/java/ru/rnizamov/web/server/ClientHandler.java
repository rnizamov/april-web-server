package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.net.Socket;

public class ClientHandler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class.getName());

    public ClientHandler(HttpServer server, Socket socket) {
        server.getServ().execute(() -> {
            logger.debug("Подключился новый клиент");
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
                logger.error("Возникла ошибка при обработке подключившегося клиента", e);
            } finally {
                if (socket != null) {
                    try {
                        socket.close();
                        logger.debug("Закрытие сокета клиента");
                    } catch (IOException e) {
                        logger.error("Ошибка при закрытии сокета", e);
                    }
                }
            }
        });
    }
}
