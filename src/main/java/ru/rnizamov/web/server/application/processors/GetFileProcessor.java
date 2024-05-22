package ru.rnizamov.web.server.application.processors;

import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.ProductService;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

import static ru.rnizamov.web.server.application.filemanager.FileManager.getBytesOfFile;


public class GetFileProcessor implements RequestProcessor {
    @Override
    public void execute(HttpRequest httpRequest, OutputStream output, ProductService productService) throws IOException {
        String fileName = httpRequest.getUri().substring(1);
        String firstPart;
        if (fileName.contains("html")) {
            firstPart = "HTTP/1.1 200 OK\r\nContent-Type: text/html\r\n\r\n";
        } else {
            firstPart = "HTTP/1.1 200 OK\r\nContent-Type: application/octet-stream\r\n\r\n";
        }
        byte[] firstArr = firstPart.getBytes(StandardCharsets.UTF_8);
        byte[] fileBytes = getBytesOfFile(fileName);
        byte[] res = new byte[firstArr.length + fileBytes.length];
        System.arraycopy(firstArr, 0, res, 0, firstArr.length);
        System.arraycopy(fileBytes, 0, res, firstArr.length, fileBytes.length);
        output.write(res);
    }
}
