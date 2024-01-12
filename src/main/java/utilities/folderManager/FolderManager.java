package utilities.folderManager;

import java.io.File;

public class FolderManager {
    public void createFolder(String folderName) {
        String path = "%s%s".formatted(System.getProperty("user.dir"), "/src/main/resources/download/%s".formatted(folderName)).replace("/", File.separator);
        File folder = new File(path);

        if (folder.exists()) {
            // Folder already exists, delete it
            if (!deleteFolder(folder)) {
                System.err.println("Error deleting folder.");
                return; // Terminate if deletion failed
            }
        }

        // Create the folder
        folder.mkdir();
    }

    private boolean deleteFolder(File folder) {
        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteFolder(file);
                } else {
                    if (!file.delete()) {
                        return false; // Terminate if any file deletion fails
                    }
                }
            }
        }
        return folder.delete();
    }
}
