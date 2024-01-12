package utilities.crawlData;

import io.restassured.path.json.JsonPath;
import utilities.models.DataStructure;
import utilities.readJson.Json2String;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class CrawlData {
    public List<DataStructure> getData(String file) {
        List<DataStructure> listProcessedData = new ArrayList<>();
        String rawData = new Json2String().readFileAsString(file);
        List<String> name = JsonPath.from(rawData).getList("results.name");
        List<String> address1 = JsonPath.from(rawData).getList("results.formatted_address");
        List<String> address2 = JsonPath.from(rawData).getList("results.vicinity");
        IntStream.range(0, name.size()).forEach(index -> {
            DataStructure processedData = new DataStructure();
            processedData.setName(name.get(index));
            processedData.setAddress(address1.get(index) == null ? address2.get(index) : address1.get(index));
            listProcessedData.add(processedData);
        });
        return listProcessedData;
    }
}
