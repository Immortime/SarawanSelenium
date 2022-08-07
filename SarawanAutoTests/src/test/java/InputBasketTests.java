import CatalogAndProducts.BasketPage;
import CatalogAndProducts.MainPage;
import LoggerAndStaff.CustomLogger;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Feature;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.events.EventFiringDecorator;

import java.time.Duration;

public class InputBasketTests {

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

    @Feature("Первый тест на невозможность повторить отрицательную цену")
    @Test
    void negativeTryOne() {
        new MainPage(driver)
                .clickOnTheCatalogButton()
                .clickOnTheCategory()
                .clickOnTheProductCard()
                .addToTheBasket()
                .exitTheCard()
                .clickOnTheBasketIcon()
                .kostilForPage()
                .writeValueOnTheInput("00")
                .decreaseValueOfProducts(1);

        Assertions.assertEquals(isElementPresent(By.xpath(BasketPage.cardOfTheProductXpath)), false);
    }

    @Feature("Второй тест на невозможность повторить отрицательную цену")
    @Test
    void negativeTryTwo() {
        new MainPage(driver)
                .clickOnTheCatalogButton()
                .clickOnTheCategory()
                .clickOnTheProductCard()
                .addToTheBasket()
                .exitTheCard()
                .clickOnTheBasketIcon()
                .kostilForPage()
                .decreaseValueOfProducts(1);

        Assertions.assertEquals(isElementPresent(By.xpath(BasketPage.cardOfTheProductXpath)), false);
    }

    @Feature("Проверка на предел инпута")
    @Test
    void maxValueCheck() {
        new MainPage(driver)
                .clickOnTheCatalogButton()
                .clickOnTheCategory()
                .clickOnTheProductCard()
                .addToTheBasket()
                .exitTheCard()
                .clickOnTheBasketIcon()
                .writeValueOnTheInput("99999");

        Assertions.assertEquals(driver.findElement(By.xpath(BasketPage.inputValueThingXpath)).getAttribute("value"), "99");
    }

    @AfterEach
    void shutDown() {
        driver.quit();
    }
}
