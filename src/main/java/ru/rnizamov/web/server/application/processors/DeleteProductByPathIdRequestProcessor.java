package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class DeleteProductByPathIdRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException, SQLException {
        int id = Integer.parseInt(httpRequest.getPathVariable());
        boolean deleted = productService.deleteItemById(id);
        String result = "HTTP/1.1 422 Unprocessable Entity\r\nContent-Type: application/json\r\n\r\n" + "{\"id:\"" +
                id + "}";
        if (deleted) {
            result = "HTTP/1.1 204 No Content\r\n\r\n\r\n";
        }
        output.write(result.getBytes(StandardCharsets.UTF_8));
    }
}
