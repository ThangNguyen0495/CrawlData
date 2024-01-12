package utilities.download;

import utilities.folderManager.FolderManager;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class DownloadFileFromURL {
    public void download(String Url, String folderName, String fileName) {
        new FolderManager().createFolder(folderName);
        String filePath = "%s%s".formatted(System.getProperty("user.dir"), "/src/main/resources/download/%s/%s.jpg".formatted(folderName, fileName)).replace("/", File.separator);
        try (BufferedInputStream inputStream = new BufferedInputStream(new URL(Url).openStream());
             FileOutputStream fileOS = new FileOutputStream(filePath)) {
            byte[] data = new byte[1024];
            int byteContent;
            while ((byteContent = inputStream.read(data, 0, 1024)) != -1) fileOS.write(data, 0, byteContent);
        } catch (IOException e) {
            // handles IO exceptions
        }
    }

}
