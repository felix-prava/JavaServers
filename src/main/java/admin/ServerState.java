package admin;

import java.io.File;
import java.util.HashMap;

public final class ServerState {

    public static final int DEFAULT_PORT = 3000;
    private int port = DEFAULT_PORT;
    private String rootDirectory = "clientWebsite\\rootDirectory\\";
    private String maintenanceDirectory = "clientWebsite\\maintenanceDirectory\\";
    private HashMap<String, String> resourcesMap = new HashMap<>();

    public void setPort(int portReceived) { this.port = portReceived; }

    public void setRootDirectory(String rootDirectoryReceived) { this.rootDirectory = rootDirectoryReceived; }

    public void setMaintenanceDirectory(String maintenanceDirectoryReceived) {
        this.maintenanceDirectory = maintenanceDirectoryReceived;
    }

    protected void updateResources() {
        try {
            resourcesMap.clear();
            File[] files = new File(rootDirectory).listFiles();
            for (File file : files) {
                if (file.isFile()) {
                    if (file.getName().endsWith(".html")) {
                        if (file.getName().equals("index.html")) {
                            resourcesMap.put("/", "index.html");
                            resourcesMap.put("/index.html", "index.html");
                            resourcesMap.put("/localhost:3000", "index.html");
                        } else {
                            resourcesMap.put("/" + file.getName(), file.getName());
                        }
                    }
                }
            }
        } catch (Exception ignored) {

        }
    }

    protected String pathIsCorrect(String directoryPath, boolean isRootDirectory) {
        File directory = new File(directoryPath);
        if (!directory.isDirectory()) {
            return("This path does not correspond to a directory\n");
        }
        if (isRootDirectory) {
            if (!new File(directoryPath, "pageNotFound.html").exists()) {
                return("This directory does not have a pageNotFound.html file\n");
            }
        } else {
            if (!new File(directoryPath, "maintenance.html").exists()) {
                return("This directory does not have a maintenance.html file\n");
            }
        }
        return "OK";
    }

    protected boolean isNumber(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public int getPort() {
        return port;
    }

    public String getRootDirectory() {
        return rootDirectory;
    }

    public String getMaintenanceDirectory() {
        return maintenanceDirectory;
    }

    public HashMap<String, String> getResourcesMap() {
        return resourcesMap;
    }
}

    
