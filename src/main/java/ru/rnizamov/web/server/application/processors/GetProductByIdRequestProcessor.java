package ru.rnizamov.web.server.application.processors;

import com.google.gson.Gson;
import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.Item;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class GetProductByIdRequestProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException, SQLException {
        int id = Integer.parseInt(httpRequest.getParameter("id"));
        Item item = productService.getItemById(id);
        Gson gson = new Gson();
        String result = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + gson.toJson(item);
        output.write(result.getBytes(StandardCharsets.UTF_8));
    }
}
