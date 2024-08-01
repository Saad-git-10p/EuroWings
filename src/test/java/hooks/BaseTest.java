package hooks;

import com.codeborne.selenide.SelenideElement;
import com.qa.driver.DriverFactory;
import hooks.BaseTest;
import io.qameta.allure.Description;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static com.codeborne.selenide.Selenide.*;
import static com.codeborne.selenide.WebDriverRunner.getWebDriver;

public class BaseTest implements PagesForTest {

    @BeforeTest
    public void setUp() throws InterruptedException {
        DriverFactory.createSelenideDriver();
        String baseUrl = "https://www.eurowings.com/en/information/at-the-airport/flight-status.html";
        open(baseUrl);
        System.out.println("--------------Url directed");

        // Handle the consent prompt
        if ($(".cookie-consent--cta-accept").exists()) {
            $(".cookie-consent--cta-accept").click();
        }
        scrollDown40Percent();


    }
    public void scrollDown40Percent() {
        int scrollAmount = (int) (getWebDriver().manage().window().getSize().getHeight() * 0.9);
        executeJavaScript("window.scrollBy(0, arguments[0]);", scrollAmount);
    }
    @AfterTest
    public void tearDown() {
        DriverFactory.cleanUpDriver();
    }
}
