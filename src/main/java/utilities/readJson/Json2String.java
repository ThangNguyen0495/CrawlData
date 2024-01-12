package utilities.readJson;

import lombok.SneakyThrows;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Json2String {
    @SneakyThrows
    public String readFileAsString(String fileName) {
        String path = "%s%s".formatted(System.getProperty("user.dir"), "/src/main/resources/rawData/%s".formatted(fileName)).replace("/", File.separator);
        return new String(Files.readAllBytes(Paths.get(path)));
    }
}
