package VVS.httpserver.core;

import java.io.*;
import java.net.Socket;
import java.util.stream.Collectors;

import static java.lang.System.out;

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


            File web_root = new File(".");
            String default_file = "index.html";
            File file = new File(web_root, default_file);
            InputStream in = this.getClass().getClassLoader()
                    .getResourceAsStream("C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\index.html");
            //String s = new BufferedReader(new InputStreamReader(in))
              //      .lines().collect(Collectors.joining("\n"));
            //out.println(s);
            String html = "<html><head><title>Simple Java Server</title></head><body><h1>Server using simple Java HTTP server</h1></body></html>";
            final String CRLF = "\n\r";

            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                            "Content-Length: " + html.getBytes().length + CRLF +
                            CRLF +
                            html +
                            CRLF + CRLF;
            outputStream.write(response.getBytes());

            out.println("--------Connection Processing Finished-------");
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
