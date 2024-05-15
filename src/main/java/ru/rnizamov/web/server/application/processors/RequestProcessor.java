package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

public interface RequestProcessor {
    void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException, SQLException;
}
