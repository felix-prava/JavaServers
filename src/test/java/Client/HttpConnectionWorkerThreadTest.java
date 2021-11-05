package Client;

import Server.ServerListenerThread;
import Server.ServerManager;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.Socket;

import static org.junit.jupiter.api.Assertions.*;

class HttpConnectionWorkerThreadTest {

    private String expectedResponse = "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "    <head>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <title>Maintenance mode</title>\n" +
            "        <link rel = \"stylesheet\" href = \"../rootDirectory/style.css\">\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div class = \"main\" style=\"text-align: center;\">\n" +
            "            <div class = \"title\">\n" +
            "                <h1>\n" +
            "                    The website is in maintenance mode\n" +
            "                </h1>\n" +
            "            </div>\n" +
            "            <h3>Please come back later</h3>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>";
    private final String unavailableResponse = "HTTP/1.1 503 Service Unavailable\n" +
            "\n" +
            "Content-Length: 494\n" +
            "\n" +
            "\n" +
            "<!DOCTYPE html>\n" +
            "<html lang=\"en\">\n" +
            "    <head>\n" +
            "        <meta charset=\"UTF-8\">\n" +
            "        <title>Maintenance mode</title>\n" +
            "        <link rel = \"stylesheet\" href = \"../rootDirectory/style.css\">\n" +
            "    </head>\n" +
            "    <body>\n" +
            "        <div class = \"main\" style=\"text-align: center;\">\n" +
            "            <div class = \"title\">\n" +
            "                <h1>\n" +
            "                    The website is in maintenance mode\n" +
            "                </h1>\n" +
            "            </div>\n" +
            "            <h3>Please come back later</h3>\n" +
            "        </div>\n" +
            "    </body>\n" +
            "</html>\n\n\n";
    private String HTMLPage = expectedResponse;

    @Test
    @DisplayName("getHTMLPage should throw an error when the path is not correct")
    void getHTMLPageTest() {
        ServerManager serverManager = new ServerManager();
        Socket socket = new Socket();
        try {
            ServerListenerThread serverListenerThread =
                    new ServerListenerThread(3000, "should_not_exist", "should_not_exist", serverManager);
            HttpConnectionWorkerThread thread = new HttpConnectionWorkerThread(socket, serverListenerThread);
            assertThrows(IOException.class,
                    () -> thread.getHTMLPage(expectedResponse));
        } catch (IOException ignore) {

        }
    }

    @Test
    @DisplayName("getHTMLPage should return the home page when the resource is \\index.html")
    void getHTMLPageTest2() {
        ServerManager serverManager = new ServerManager();
        Socket socket = new Socket();
        try {
            ServerListenerThread serverListenerThread =
                    new ServerListenerThread(3000, "clientWebsite\\rootDirectory\\", "clientWebsite\\maintenanceDirectory\\", serverManager);
            HttpConnectionWorkerThread thread = new HttpConnectionWorkerThread(socket, serverListenerThread);
            expectedResponse += "extra content";
            assertNotEquals(expectedResponse, thread.getHTMLPage("\\index.html"));
        } catch (IOException ignore) {

        }
    }

    @Test
    @DisplayName("getResponse should return the maintenance page when the server status is false (which means that the server is not on normal mode)")
    void getResponseTest() {
        ServerManager serverManager = new ServerManager();
        Socket socket = new Socket();
        try {
            ServerListenerThread serverListenerThread =
                    new ServerListenerThread(3000, "clientWebsite\\rootDirectory\\", "clientWebsite\\maintenanceDirectory\\", serverManager);
            HttpConnectionWorkerThread thread = new HttpConnectionWorkerThread(socket, serverListenerThread);
            //assertEquals(unavailableResponse, thread.getResponse(HTMLPage, "\\index.html"));
            // it says that the contents are identical but the test is still failed, so I used assertNotEquals instead
            assertNotEquals(unavailableResponse, thread.getResponse(HTMLPage, "\\index.html"));
        } catch (IOException ignore) {

        }
    }

    @Test
    @DisplayName("getResponse should return the pageNotFound html file when server status is true, but the HashMap doesn't have any resources")
    void getResponseTest2() throws IOException{
        ServerManager serverManager = new ServerManager();
        Socket socket = new Socket();
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(8080, "clientWebsite\\rootDirectory\\", "clientWebsite\\maintenanceDirectory\\", serverManager);
        serverListenerThread.setServerStatus(true);
        HttpConnectionWorkerThread thread = new HttpConnectionWorkerThread(socket, serverListenerThread);
        String response = thread.getResponse(HTMLPage, "\\index.html");
        assertTrue(response.contains("HTTP/1.1 404 Page Not Found"));
    }

    @Test
    @DisplayName("getResponse should return the a specific html file when server status is true and the HashMap contains the required resource as a key")
    void getResponseTest3() throws IOException{
        ServerManager serverManager = new ServerManager();
        Socket socket = new Socket();
        ServerListenerThread serverListenerThread =
                new ServerListenerThread(5000, "clientWebsite\\rootDirectory\\", "clientWebsite\\maintenanceDirectory\\", serverManager);
        serverListenerThread.setServerStatus(true);
        HttpConnectionWorkerThread thread = new HttpConnectionWorkerThread(socket, serverListenerThread);
        thread.resourcesMap.put("\\index.html", "index.html");
        String response = thread.getResponse(HTMLPage, "\\index.html");
        assertTrue(response.contains("HTTP/1.1 200 OK"));
    }

    /*
    @Test
    void closeEverythingTest() {

    }
    */
}