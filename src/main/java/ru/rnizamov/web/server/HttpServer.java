package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rnizamov.web.server.application.Storage;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger logger = LogManager.getLogger(HttpServer.class.getName());
    private final int port;
    private Dispatcher dispatcher;
    private ExecutorService serv = Executors.newCachedThreadPool();

    public HttpServer(int port) {
        this.port = port;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ExecutorService getServ() {
        return serv;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту: " + port);
            this.dispatcher = new Dispatcher();
            logger.info("Диспетчер проинициализирован");
            Storage.init();
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            logger.fatal("Ошибка при создании ServerSocket", e);
        }
    }
}
