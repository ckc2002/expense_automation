package listeners;

import org.testng.ITestListener;
import org.testng.ITestResult;

import driver.DriverManager;
import utils.AllureUtils;


public class AllureListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String methodName = result.getMethod().getMethodName();

        Throwable throwable = result.getThrowable();
        String failureReason = throwable != null ? throwable.toString() : "No exception available";

        AllureUtils.attachText("Failure Details",
                "Class: " + result.getTestClass().getName() + System.lineSeparator()
                        + "Method: " + methodName + System.lineSeparator()
                        + "Error: " + failureReason);

        AllureUtils.attachScreenshot(DriverManager.getDriver(), methodName + " - failure screenshot");
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        Throwable throwable = result.getThrowable();
        String skipReason = throwable != null ? throwable.toString() : "No skip reason available";

        AllureUtils.attachText("Skipped Details",
                "Class: " + result.getTestClass().getName() + System.lineSeparator()
                        + "Method: " + result.getMethod().getMethodName() + System.lineSeparator()
                        + "Reason: " + skipReason);
    }
}