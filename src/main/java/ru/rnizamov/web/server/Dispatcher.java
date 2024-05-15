package ru.rnizamov.web.server;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.rnizamov.web.server.application.ProductService;
import ru.rnizamov.web.server.application.processors.*;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import static ru.rnizamov.web.server.application.filemanager.FileManager.existFile;


public class Dispatcher {
    private static final Logger logger = LogManager.getLogger(Dispatcher.class.getName());
    private Map<String, RequestProcessor> router;
    private RequestProcessor unknownOperationRequestProcessor;
    private RequestProcessor getFileProcessor;

    public Dispatcher() {
        this.router = new HashMap<>();
        this.router.put("GET /items", new GetAllProductsProcessor());
        this.router.put("GET /items?", new GetProductByIdRequestProcessor());
        this.router.put("DELETE /items?", new DeleteProductByIdRequestProcessor());
        this.router.put("POST /items", new CreateNewProductProcessor());
        this.router.put("PUT /items", new UpdateProductProcessor());
        this.unknownOperationRequestProcessor = new UnknownOperationRequestProcessor();
        this.getFileProcessor = new GetFileProcessor();
    }

    public void execute(HttpRequest httpRequest, OutputStream outputStream, ProductService productService) throws IOException, SQLException {
        logger.debug("RouteKey: " + httpRequest.getRouteKey());
        if (!router.containsKey(httpRequest.getRouteKey())) {
            String fileName = httpRequest.getUri().substring(1);
            if (existFile(fileName)) {
                getFileProcessor.execute(httpRequest, outputStream, productService);
                return;
            }
            unknownOperationRequestProcessor.execute(httpRequest, outputStream, productService);
            return;
        }
        router.get(httpRequest.getRouteKey()).execute(httpRequest, outputStream, productService);
    }
}
