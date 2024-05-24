package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.ProductService;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public class AddFileProcessor implements RequestProcessor{
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException, SQLException {
        String file = httpRequest.getBody();
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("static/file" + Math.random()*10 + ".pdf"))) {
            out.write(file.getBytes());
        }
    }
}
