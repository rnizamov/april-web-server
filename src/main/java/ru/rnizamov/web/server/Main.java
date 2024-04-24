package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class.getName());

    public static void main(String[] args) {
        logger.info("Точка входа в приложение. Запуск сервера");
        new HttpServer(8189).start();
    }
}