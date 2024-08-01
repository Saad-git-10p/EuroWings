package com.qa.driver;


import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.WebDriverRunner;

import log.Log;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import com.qa.utils.csv_reader.Settings;

import static com.codeborne.selenide.Selenide.open;

public class DriverFactory {
    public static  WebDriver webDriver =null;

    public static WebDriverRunner selenideRunner = null;
    public  WebDriver getDriver(){
        if(webDriver == null){
            //webDriver = createDriver();
        }
        return webDriver;
    }

    private  WebDriver createDriver(){
        WebDriver driver = null;
        String browserType = Settings.BROWSER_TYPE;
        Boolean isHeadLess = Settings.HEADLESS_EXECUTION;

        switch (browserType) {
            case "chrome" -> {
                Log.Info("Starting Chrome Instance!");
                ChromeOptions chromeOptions = new ChromeOptions();
                // Wait for the whole page to load
                chromeOptions.addArguments("--disable-notifications");
                chromeOptions.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});

                chromeOptions.setPageLoadStrategy(PageLoadStrategy.NORMAL);
                if (isHeadLess) {
                    chromeOptions.addArguments("--headless");
                }

                driver = new ChromeDriver(chromeOptions);
                Log.Info("Chrome Driver Initialized Successfully!");
            }
            case "firefox" -> {
                FirefoxOptions firefoxOptions = new FirefoxOptions();

                firefoxOptions.addArguments("--disable-notifications");
                if(isHeadLess){
                    firefoxOptions.addArguments("--headless");
                }
                driver = new FirefoxDriver(firefoxOptions);
            }
        }
        driver.manage().window().maximize();
        return driver;
    }

    public static WebDriverRunner createSelenideDriver(){

        selenideRunner = new WebDriverRunner();
        Configuration.headless = Settings.HEADLESS_EXECUTION;
        Configuration.browser = Settings.BROWSER_TYPE;

        open();

        selenideRunner.getWebDriver().manage().window().maximize();

        return selenideRunner;
    }

    public static void cleanUpDriver() {
        WebDriverRunner.getWebDriver().quit();
    }
}
