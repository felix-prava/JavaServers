package Client;

import Server.ServerListenerThread;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.Date;

import static java.lang.System.out;

public class HttpConnectionWorkerThread extends Thread {

    private Socket socket;
    private ServerListenerThread serverListenerThread;
    private String request;

    private byte[] readFileData(File file, int fileLength) throws IOException {
        FileInputStream fileIn = null;
        byte[] fileData = new byte[fileLength];

        try {
            fileIn = new FileInputStream(file);
            fileIn.read(fileData);
        } finally {
            if (fileIn != null)
                fileIn.close();
        }

        return fileData;
    }

    public HttpConnectionWorkerThread(Socket socket, ServerListenerThread serverListenerThread) {
        this.socket = socket;
        this.serverListenerThread = serverListenerThread;
    }

    @Override
    public void run() {
        //super.run();
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            InputStreamReader isr = new InputStreamReader(socket.getInputStream());
            BufferedReader br = new BufferedReader(isr);
            StringBuilder request = new StringBuilder();
            String line, resource = "";
            line = br.readLine();
            while (line != null && !line.isBlank()) {
                request.append(line + "\r\n");
                if (line.startsWith("Referer: http://localhost")) {
                    int length = line.split("/").length;
                    resource = "/" + line.split("/")[length - 1];
                }
                line = br.readLine();
            }
            String firstLine = request.toString().split("\n")[0];
            String firstLineResource = firstLine.split(" ")[1];
            out.println("-----------------------------------------------------------------------------");
            out.println(resource);
            out.println(firstLineResource);
            out.println(LocalTime.now());
            out.println("-----------------------------------------------------------------------------");
            this.request = request.toString();



            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            String css = Files.readString(Paths.get("C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\style.css"), StandardCharsets.US_ASCII);

            String html;
            if (!resource.equals("")) {
                html = getResponse(resource);
            } else {
                html = getResponse(firstLineResource);
            }
            final String CRLF = "\n\r";
            html += css;
            String response =
                    "HTTP/1.1 200 OK" + CRLF +
                    "Content-Length: " + html.getBytes().length + CRLF +
                    CRLF +
                    html +
                    CRLF + CRLF;
            outputStream.write(response.getBytes());
            out.println("Responded to the page");
            outputStream.flush();
            if (serverListenerThread.getServerStatus()) {
                out.println("--------Connection Processing Finished-------" + " Running");
            } else {
                out.println("--------Connection Processing Finished-------" + " Maintenance");
            }
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

    public String getResponse(String resource) throws IOException {
        String path = "C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\";
        out.println(resource);
        if (resource.equals("/") || resource.equals("/index.html") || resource.equals("/localhost:3000")) {
            path += "index.html";
            out.println("Homeee");
            out.println(request);
            //return "<html><head><title>Home</title></head><body><h1>Home</h1></body></html>";
        } else if (resource.equals("/aboutUs.html")) {
            out.println("This is about us");
            path += "aboutUs.html";
        } else if (resource.equals("/careers.html")) {
            out.println("This is careers");
            path += "careers.html";
        } else if (resource.equals("/customers.html")) {
            path += "customers.html";
        } else {
            out.println("Howww " + resource + " bbb");
            out.println(request);
            return "<html><head><title>Simple Java Server</title></head><body><h1>Server using simple Java HTTP server</h1></body></html>";
        }
        return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
    }
}
