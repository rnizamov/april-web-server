package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rnizamov.web.server.application.exception.ExceptionHandler;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class ClientHandler {
    private static final Logger logger = LogManager.getLogger(ClientHandler.class.getName());
    private HttpRequest request;

    public ClientHandler(HttpServer server, Socket socket) {
        server.getServ().execute(() -> {
            logger.debug("Подключился новый клиент");
            try {
                byte[] buffer = new byte[8192];
                int n = socket.getInputStream().read(buffer);
                if (n > 0) {
                    String rawRequest = new String(buffer, 0, n, StandardCharsets.UTF_8);
                    request = new HttpRequest(rawRequest);
                    request.info();
                    server.getDispatcher().execute(request, socket.getOutputStream(), server.getProductService());
                }
            } catch (Exception e) {
                logger.error("Возникла ошибка при обработке подключившегося клиента", e);
                new ExceptionHandler().handle(e, server, request, socket);
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
