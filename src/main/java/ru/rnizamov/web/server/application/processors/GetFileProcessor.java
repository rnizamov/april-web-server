package ru.rnizamov.web.server.application.processors;
import ru.rnizamov.web.server.HttpRequest;
import ru.rnizamov.web.server.application.ProductService;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


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
        output.write(firstPart.getBytes(StandardCharsets.UTF_8));
        try (FileInputStream in = new FileInputStream("static/" + fileName)) {
            byte[] buf = new byte[8192];
            int n = in.read(buf);
            while (n > 0) {
                output.write(buf, 0, n);
                n = in.read(buf);
            }
        }
    }
}