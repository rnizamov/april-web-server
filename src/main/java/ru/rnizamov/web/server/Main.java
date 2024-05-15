package ru.rnizamov.web.server;

public class Main {
    public static void main(String[] args) {
        int port = Integer.parseInt((String) System.getProperties().getOrDefault("port", "8189"));
        String url = (String) System.getProperties().getOrDefault("url", "jdbc:postgresql://localhost:5432/product_repository");
        String user = (String) System.getProperties().getOrDefault("user", "root");
        String password = (String) System.getProperties().getOrDefault("password", "root");
        new HttpServer(port, url, user, password).start();
    }
}