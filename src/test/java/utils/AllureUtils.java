package utils;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import io.qameta.allure.Allure;

public final class AllureUtils {

    private AllureUtils() {
    }

    public static void attachText(String name, String value) {
        if (value == null) {
            value = "";
        }
        Allure.addAttachment(name, new ByteArrayInputStream(value.getBytes(StandardCharsets.UTF_8)));
    }

    public static void attachScreenshot(WebDriver driver, String attachmentName) {
        if (driver == null) {
            return;
        }

        if (!(driver instanceof TakesScreenshot)) {
            return;
        }

        byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);

        Allure.getLifecycle().addAttachment(
                attachmentName,
                "image/png",
                "png",
                screenshot
        );
    }
}