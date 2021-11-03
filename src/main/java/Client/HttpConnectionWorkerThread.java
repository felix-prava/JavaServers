package Client;

import Server.ServerListenerThread;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalTime;
import java.util.HashMap;

import static java.lang.System.out;

public class HttpConnectionWorkerThread extends Thread {

    private Socket socket;
    private ServerListenerThread serverListenerThread;
    private String request;
    private HashMap<String, String> resourceMap = new HashMap<>();

    public HttpConnectionWorkerThread(Socket socket, ServerListenerThread serverListenerThread) {
        this.socket = socket;
        this.serverListenerThread = serverListenerThread;
        resourceMap.put("/", "index.html");
        resourceMap.put("/index.html", "index.html");
        resourceMap.put("/localhost:3000", "index.html");
        resourceMap.put("/aboutUs.html", "aboutUs.html");
        resourceMap.put("/careers.html", "careers.html");
        resourceMap.put("/customers.html", "customers.html");
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
            String firstLineResource = "";
            if (firstLine.split(" ").length > 1) {
                firstLineResource = firstLine.split(" ")[1];
            }
            out.println("-----------------------------------------------------------------------------");
            out.println(resource);
            out.println(firstLineResource);
            out.println(LocalTime.now());
            out.println("-----------------------------------------------------------------------------");
            this.request = request.toString();



            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
            String css = Files.readString(Paths.get("C:\\Users\\Mihai\\Desktop\\JavaServersVVS\\clientWebsite\\rootDirectory\\style.css"), StandardCharsets.US_ASCII);

            String html;
            //if (!resource.equals("")) {
            if (resourceMap.containsKey(firstLineResource)) {
                html = getResponse(firstLineResource);
            } else {
                html = getResponse(resource);
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
        String path = serverListenerThread.getRootDirectory();
        if (!serverListenerThread.getServerStatus()) {
            path = serverListenerThread.getMaintenanceDirectory() + "maintenance.html";
            return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
        }
        path += resourceMap.getOrDefault(resource, "pageNotFound.html");
        return Files.readString(Paths.get(path), StandardCharsets.US_ASCII);
    }
}
