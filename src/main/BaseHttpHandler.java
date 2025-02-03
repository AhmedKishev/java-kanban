package main;

import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class BaseHttpHandler {

    public void sendText(HttpExchange e, String text, int code) throws IOException {
        byte[] resp = text.getBytes(StandardCharsets.UTF_8);
        e.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        e.sendResponseHeaders(code, resp.length);
        e.getResponseBody().write(resp);
        e.close();
    }

}
