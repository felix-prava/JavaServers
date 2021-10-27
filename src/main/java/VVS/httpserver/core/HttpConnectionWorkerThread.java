package VVS.httpserver.core;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

public class HttpConnectionWorkerThread extends Thread {

    private Socket socket;

    public HttpConnectionWorkerThread(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        super.run();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();

            String html = "<html><head><title>Simple Java Server</title></head><body><h1>Server using simple Java HTTP server</h1></body></html>";
            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content-Length: " + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

            System.out.println("--------Connection Processing Finished-------");
        } catch (IOException e) {
            System.err.println("Problem with comunication:\n" + e);
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (socket != null) {
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
