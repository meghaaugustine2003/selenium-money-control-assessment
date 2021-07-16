package com.selenium.tests;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.selenium.driver.Driver;
import lombok.SneakyThrows;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.io.FileHandler;
import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

import static com.selenium.constants.Constants.getScreenshotsFilePath;
import static com.selenium.constants.Constants.getTestReportPath;

public class BaseTest {
    protected BaseTest() {

    }

    private static ExtentHtmlReporter htmlReporter;
    private static ExtentReports extentReports;
    private static String screenShotParentPath;
    ExtentTest test;

    @BeforeClass
    protected void setUp() {
        htmlReporter = getHtmlReporterInstance();
        extentReports = getExtentInstance();
        extentReports.attachReporter(htmlReporter);

        extentReports.setSystemInfo("OS", "MAC");
        extentReports.setSystemInfo("Environment", "QA");
        extentReports.setSystemInfo("Author", "Selenium Tester");

        htmlReporter.config().setDocumentTitle("Selenium Assessment");
        htmlReporter.config().setReportName("Assessment Test Report");
        htmlReporter.config().setTheme(Theme.DARK);

        Driver.inItDriver();

        screenShotParentPath = getScreenshotsFilePath();
    }

    @AfterClass
    protected void tearDown() {
        getExtentInstance().flush();
        Driver.quitDriver();
    }

    @AfterMethod
    public void after(ITestResult result) {
        if (result.getStatus() == ITestResult.FAILURE) {
            test.fail(result.getThrowable());
        } else {
            test.pass(MarkupHelper.createLabel(result.getName() + " Test passed.", ExtentColor.GREEN));
        }
    }

    static ExtentReports getExtentInstance() {
        if (extentReports == null) {
            extentReports = new ExtentReports();
        }
        return extentReports;
    }

    static ExtentHtmlReporter getHtmlReporterInstance() {
        if (htmlReporter == null) {
            htmlReporter = new ExtentHtmlReporter(getTestReportPath() + "test_report.html");
        }
        return htmlReporter;
    }

    @SneakyThrows
    String captureScreenshot(WebDriver driver, String testName, String screenshotName) {
        TakesScreenshot screenshot = ((TakesScreenshot) driver);
        File source = screenshot.getScreenshotAs(OutputType.FILE);
        if (!Files.exists(Paths.get(screenShotParentPath, testName))) {
            Files.createDirectories(Paths.get(screenShotParentPath, testName));
        }
        String dest = Paths.get(screenShotParentPath, testName, screenshotName).toAbsolutePath().toString();
        File destination = new File(dest);
        FileHandler.copy(source, destination);
        return dest;
    }
}
