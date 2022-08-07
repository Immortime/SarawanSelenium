import CatalogAndProducts.BasketPage;
import CatalogAndProducts.MainPage;
import LoggerAndStaff.CustomLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Allure;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.time.Duration;

public class AddAndTryNegativePrice {

    WebDriver driver;
    private final static String MAIN_PAGE_URL = "https://sarawan.ru";

    @BeforeAll
    static void startDriver() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setupDrivers() {
        driver = new EventFiringDecorator(new CustomLogger()).decorate(new ChromeDriver());
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
        driver.get(MAIN_PAGE_URL);
        driver.manage().window().maximize();
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        } catch (NoSuchElementException e) {
            return false;
        }
    }

    @Test
    void Test() {
        new MainPage(driver)
                .clickOnTheCatalogButton()
                .clickOnTheCategory()
                .clickOnTheProductCard()
                .addToTheBasket()
                .increaseValueOnNum(1)
                .clickOnTheCart()
                .decreaseValueOnNum(5)
                .exitTheCard()
                .clickOnTheBasketIcon();

        try {
            Assertions.assertEquals(driver.findElement(By.xpath(BasketPage.inputValueThingXpath)).getAttribute("value"), "-3");
            Allure.addAttachment("ТЕСТ ПРОВАЛИЛСЯ!!!", "Получилась негативная цена");
        } catch (NoSuchElementException | TimeoutException ex) {
            Allure.addAttachment("Тест прошел успешно", "Товара в корзине нет");
            Assertions.assertFalse(isElementPresent(By.xpath(BasketPage.cardOfTheProductXpath)));
        }
    }

    @AfterEach
    void shutDown() {
        driver.quit();
    }
}
