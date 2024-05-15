package ru.rnizamov.web.server.application.processors;

import com.google.gson.Gson;
import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.Item;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

public class UpdateProductProcessor implements RequestProcessor{
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException, SQLException {
        Gson gson = new Gson();
        Item item = gson.fromJson(httpRequest.getBody(), Item.class);
        productService.updateItem(item);
        String jsonOutItem = gson.toJson(item);

        String response = "HTTP/1.1 200 OK\r\nContent-Type: application/json\r\n\r\n" + jsonOutItem;
        output.write(response.getBytes(StandardCharsets.UTF_8));
    }
}
