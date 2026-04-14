package listeners;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import config.ConfigManager;

public class RetryAnalyzer implements IRetryAnalyzer {

    private int retryCount = 0;
    private final int maxRetryCount;

    public RetryAnalyzer() {
        this.maxRetryCount = getMaxRetryCount();
    }

    @Override
    public boolean retry(ITestResult result) {
        if (!shouldRetry(result)) {
            return false;
        }

        retryCount++;

        Throwable throwable = result.getThrowable();
        String reason = (throwable != null)
                ? throwable.getClass().getSimpleName() + " - " + throwable.getMessage()
                : "Unknown failure";

        System.out.println(
                "[RetryAnalyzer] Retrying test: " + getTestName(result)
                        + " | Attempt: " + retryCount + "/" + maxRetryCount
                        + " | Reason: " + reason
        );

        return true;
    }

    private boolean shouldRetry(ITestResult result) {
        if (result == null) {
            return false;
        }

        if (retryCount >= maxRetryCount) {
            return false;
        }

        if (result.isSuccess()) {
            return false;
        }

        if (result.getStatus() == ITestResult.SKIP) {
            return false;
        }

        Throwable throwable = result.getThrowable();

        if (throwable instanceof AssertionError) {
            System.out.println(
                    "[RetryAnalyzer] Assertion failure detected. Retry skipped for: " + getTestName(result)
            );
            return false;
        }

        return true;
    }

    private int getMaxRetryCount() {
        String retryCountFromSystem = System.getProperty("retryCount");

        if (retryCountFromSystem != null && !retryCountFromSystem.isBlank()) {
            try {
                return Math.max(Integer.parseInt(retryCountFromSystem.trim()), 0);
            } catch (NumberFormatException e) {
                System.out.println("[RetryAnalyzer] Invalid system retryCount. Falling back to config value.");
            }
        }

        return Math.max(ConfigManager.getIntProperty("retryCount", 1), 0);
    }

    private String getTestName(ITestResult result) {
        String className = result.getTestClass() != null
                ? result.getTestClass().getRealClass().getSimpleName()
                : "UnknownClass";

        String methodName = result.getMethod() != null
                ? result.getMethod().getMethodName()
                : "UnknownMethod";

        return className + "." + methodName;
    }
}