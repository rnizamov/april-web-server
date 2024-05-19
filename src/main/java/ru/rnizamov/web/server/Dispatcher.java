package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rnizamov.web.server.application.ProductService;
import ru.rnizamov.web.server.application.exception.NotFoundException;
import ru.rnizamov.web.server.application.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ru.rnizamov.web.server.application.filemanager.FileManager.existFile;


public class Dispatcher {
    private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());
    private Map<String, RequestProcessor> router = new HashMap<>();

    public Map<String, RequestProcessor> getRouter() {
        return router;
    }

    public Dispatcher() {
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("GET /items?", new GetProductByParamIdRequestProcessor());
        this.router.put("GET /items/", new GetProductByPathIdRequestProcessor());
        this.router.put("DELETE /items?", new DeleteProductByParamIdRequestProcessor());
        this.router.put("DELETE /items/", new DeleteProductByPathIdRequestProcessor());
        this.router.put("POST /items", new CreateNewProductProcessor());
        this.router.put("PUT /items", new UpdateProductProcessor());
        this.router.put("NotFoundException", new NotFoundRequestProcessor());
        this.router.put("PSQLException", new DatabaseErrorRequestProcessor());
        this.router.put("FileProcessor", new GetFileProcessor());
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream, ProductService productService) throws IOException, SQLException, NotFoundException {
        logger.debug("RouteKey: " + httpRequest.getRouteKey());
        if (router.containsKey(httpRequest.getRouteKey())) {
            router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream, productService);
        } else {
            String fileName = httpRequest.getUri().substring(1);
            if (existFile(fileName)) {
                router.get("FileProcessor").execute(httpRequest, outputStream, productService);
                return;
            }
            throw new NotFoundException("NotFoundException: " + httpRequest.getRouteKey());
        }
    }
}
