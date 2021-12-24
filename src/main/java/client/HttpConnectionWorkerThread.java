package client;

import server.ServerListenerThread;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;

public class HttpConnectionWorkerThread extends Thread {

    private Socket socket;
    private ServerListenerThread serverListenerThread;
    protected HashMap<String, String> resourcesMap = new HashMap<>();

    public HttpConnectionWorkerThread(Socket socketReceived, ServerListenerThread serverListenerThreadReceived) {
        this.socket = socketReceived;
        this.serverListenerThread = serverListenerThreadReceived;
    }

    @Override
    public void run() {
        InputStream inputStream = null;
        OutputStream outputStream = null;
        try {
            // Read the content of the request
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
            String firstLineResource = "";
            if (firstLine.split(" ").length > 1) {
                firstLineResource = firstLine.split(" ")[1];
            }

            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            resourcesMap = serverListenerThread.getResourcesMap();
            String css = Files.readString(Paths.get("C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\rootDirectory\\style.css"), StandardCharsets.US_ASCII);


            // Get the HTML page based on client's request and write back the response
            String html, response;
            if (resourcesMap != null && resourcesMap.containsKey(firstLineResource)) {
                resource = firstLineResource;
            }
            html = getHTMLPage(resource);
            html += css;
            response = getResponse(html, resource);

            outputStream.write(response.getBytes());
            outputStream.flush();
        } catch (IOException e) {
            System.err.println("Problem with comunication:\n" + e);
            e.printStackTrace();
        } finally {
            // close everything
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

    protected String getResponse(String html, String resource) {
        String CRLF = "\n\r";
        String response;
        if (!serverListenerThread.getServerStatus()) {
            response = "HTTP/1.1 503 Service Unavailable" + CRLF;
        } else {
            if (resourcesMap.containsKey(resource)) {
                response = "HTTP/1.1 200 OK" + CRLF;
            } else {
                response = "HTTP/1.1 404 Page Not Found" + CRLF;
            }
        }
        response += "Content-Length: " + html.getBytes().length + CRLF +
                CRLF +
                html +
                CRLF + CRLF;
        return response;
    }

    public String getHTMLPage(String resource) throws IOException {
        String path = serverListenerThread.getRootDirectory();
        if (!serverListenerThread.getServerStatus()) {
            path = serverListenerThread.getMaintenanceDirectory() + "maintenance.html";
            return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
        }
        path += resourcesMap.getOrDefault(resource, "pageNotFound.html");
        return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
    }
}
