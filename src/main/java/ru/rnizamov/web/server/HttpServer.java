package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rnizamov.web.server.application.DataBaseProductService;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {
    private static final Logger logger = LogManager.getLogger(HttpServer.class.getName());
    private final int port;
    private final String url;
    private final String user;
    private final String password;
    private Dispatcher dispatcher;
    private ExecutorService serv = Executors.newCachedThreadPool();
    private ProductService productService;

    public HttpServer(int port, String url, String user, String password) {
        this.port = port;
        this.url = url;
        this.user = user;
        this.password = password;
    }

    public Dispatcher getDispatcher() {
        return dispatcher;
    }

    public ProductService getProductService() {
        return productService;
    }

    public ExecutorService getServ() {
        return serv;
    }

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(port)) {
            logger.info("Сервер запущен на порту: " + port);
            this.dispatcher = new Dispatcher();
            logger.info("Диспетчер проинициализирован");
            productService = new DataBaseProductService(url, user, password);
            while (true) {
                Socket socket = serverSocket.accept();
                new ClientHandler(this, socket);
            }
        } catch (IOException e) {
            logger.fatal("Ошибка при создании ServerSocket", e);
        } catch (SQLException e) {
            logger.fatal("Ошибка при подключении к ProductService", e);
        } finally {
            productService.close();
        }
    }
}
