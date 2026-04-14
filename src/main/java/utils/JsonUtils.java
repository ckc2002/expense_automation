package utils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class JsonUtils {

    public static Object[][] getJsonData(String filePath) {
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            List<Map<String, String>> users = objectMapper.readValue(
                    new File(filePath),
                    new TypeReference<List<Map<String, String>>>() {}
            );

            Object[][] data = new Object[users.size()][3];

            for (int i = 0; i < users.size(); i++) {
                data[i][0] = users.get(i).get("username");
                data[i][1] = users.get(i).get("password");
                data[i][2] = users.get(i).get("name");
            }

            return data;

        } catch (IOException e) {
            throw new RuntimeException("Unable to read JSON file: " + filePath, e);
        }
    }
}