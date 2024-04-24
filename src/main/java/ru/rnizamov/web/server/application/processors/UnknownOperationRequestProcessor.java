package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class UnknownOperationRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output) throws IOException {
        String response = "HTTP/1.1 404 Not Found\r\nContent-Type: text/html\r\n\r\n<html><body>" +
                "<img src='https://l-tech.ru/uploads/publication_post/main_image/122/full_screen_retina_e5f347c8e4.jpeg' style='max-width: 100%; height: auto;'></body></html>";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}