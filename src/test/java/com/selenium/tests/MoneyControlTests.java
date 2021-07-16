package com.selenium.tests;


import lombok.SneakyThrows;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.Test;
import java.util.*;
import java.util.stream.IntStream;
import static com.selenium.driver.DriverManager.browser;
import static com.selenium.utils.ReadPropertyFile.propertyOf;
import static org.testng.Assert.*;

public final class MoneyControlTests extends BaseTest {

    @Test
    @SneakyThrows
    public void testBestMutualFundSecondStockAllocation() {
        // Arrange
        test = getExtentInstance().createTest("Second highest stock allocation (3Y) in the best mutual fund in moneycontrol");
        String website = propertyOf("url");

        // Act
        // Step 1: Open chrome browser , navigate to moneycontrol.com.
        browser().navigate().to(website);
        test.addScreenCaptureFromPath(captureScreenshot(browser(), "0", "1_home.png"));

        // Step 2: Open Mutual funds tab, click the Top ranked funds link.
        browser()
                .findElement(By.xpath("//header/div[1]/div[3]/nav[1]/div[1]/ul[1]/li[7]/a[1]"))
                .click();
        browser()
                .findElement(By.xpath("//header/div[1]/div[3]/div[1]/div[1]/div[1]/ul[1]/li[2]/a[1]"))
                .click();

        test.addScreenCaptureFromPath(captureScreenshot(browser(), "0", "2_top_ranked.png"));

        // Step 3: Find the mutual fund (regular plan) which has given maximum returns over 3 yrs period
        WebElement checkbox =
                browser()
                        .findElement(By.xpath("//body/section[2]/div[1]/div[1]/div[2]/div[1]/div[3]/ul[1]/li[2]/label[1]/span[1]"));

        Actions actions = new Actions(browser());
        actions.moveToElement(checkbox);
        actions.perform();
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        browser()
                .findElement(By.xpath("//body/section[2]/div[1]/div[1]/div[2]/div[1]/div[3]/ul[1]/li[1]/label[1]/span[1]"))
                .click();
        WebElement descSortArrow = browser().findElement(By.xpath("//thead/tr[1]/th[13]/div[1]/span[2]"));
        if (!descSortArrow.isSelected()) {
            descSortArrow.click();
        }
        descSortArrow.click();

        // Find the mutual fund (regular plan) which has given maximum returns over 3 yrs period
        WebElement table = browser().findElement(By.xpath("//table[@id='dataTableId']"));
        List<WebElement> tableRows = table.findElements(By.xpath("//tbody/tr[not(contains(@class,'sponsor_ad_bg'))]"));
        Optional<WebElement> highestValueRow =
                IntStream
                        .range(0, tableRows.size())
                        .mapToObj(x -> {
                            if (tableRows.get(x).getAttribute("class").contains("group")) {
                                return tableRows.get(x + 1);
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .max(Comparator.comparingDouble(
                                element -> Double.parseDouble(
                                        element.findElement(By.className("sorting_2")).getText().replaceAll(".$", "")
                                )));
        highestValueRow.get().findElements(By.tagName("td")).get(0).click();

        test.addScreenCaptureFromPath(captureScreenshot(browser(), "0", "3_mutual_fund.png"));

        // Step 4: Open the portfolio of the mutual fund , find the stock  which has second highest allocation percentage.
        ArrayList<String> tabs = new ArrayList<String>(browser().getWindowHandles());
        browser().switchTo().window(tabs.get(1));


        WebElement portFolio = browser().findElement(By.xpath("//p[contains(text(),'Top 10 Stocks in Portfolio')]"));
        Actions scrollToPortFolio = new Actions(browser());
        scrollToPortFolio.moveToElement(portFolio);
        scrollToPortFolio.perform();

        WebElement stock = browser().findElement(By.xpath("//*[@id=\"portfolioEquityTable\"]/tbody/tr[2]/td[1]/span[2]/a"));
        stock.click();
        tabs = new ArrayList<String>(browser().getWindowHandles());
        browser().switchTo().window(tabs.get(2));
        test.addScreenCaptureFromPath(captureScreenshot(browser(), "0", "4_stock_allocation.png"));

        // Assert the stock

        WebElement stockName = browser().findElement(By.xpath("//*[@id=\"stockName\"]/h1"));
        String actualStockName = stockName.getText();
        assertEquals(actualStockName,"Aurobindo Pharma Ltd.");

    }
}
