package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class DatabaseErrorRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException {
        String response = "HTTP/1.1 500 Internal Server Error\r\nContent-Type: application/json\r\n\r\n" +
                "{\"message\":" + "\"Internal server error occurred. Please try again later.\"}";
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
