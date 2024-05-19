package ru.rnizamov.web.server.application.exception;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.HttpServer;
import java.net.Socket;

public class ExceptionHandler {
    private static final Logger logger = LogManager.getLogger(ExceptionHandler.class.getName());

    public void handle(Exception e, HttpServer server, HttpRequest request, Socket socket) {
        try {
            switch (e.getClass().getSimpleName()) {
                case "NotFoundException":
                    server.getDispatcher().getRouter().get("NotFoundException").execute(request, socket.getOutputStream(), server.getProductService());
                    break;
                case "PSQLException":
                    server.getDispatcher().getRouter().get("PSQLException").execute(request, socket.getOutputStream(), server.getProductService());
                    break;
            }
        } catch (Exception ex) {
            logger.error("Возникла ошибка при обработке подключившегося клиента", e);
        }
    }
}