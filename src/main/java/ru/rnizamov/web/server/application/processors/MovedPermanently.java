package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class MovedPermanently implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response = "HTTP/1.1 301 Moved Permanently\r\nLocation: https://otus.ru/lessons/java-professional/";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}