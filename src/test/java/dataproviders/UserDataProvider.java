package dataproviders;

import config.ConfigManager;
import org.testng.annotations.DataProvider;
import utils.JsonUtils;

public class UserDataProvider {

    @DataProvider(name = "loginUsers")
    public static Object[][] jsonUsers() {
        String env = System.getProperty("env");
        if (env == null || env.isBlank()) {
            env = "qa";
        }

        ConfigManager.loadProperties(env);

        String jsonFilePath = ConfigManager.getProperty("jsonFilePath");
        return JsonUtils.getJsonData(jsonFilePath);
    }
}