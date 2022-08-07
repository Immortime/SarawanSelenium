package CatalogAndProducts;

import io.qameta.allure.Story;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class BasketPage extends BasePageObject {

    public BasketPage (WebDriver driver) {
        super(driver);
    }

    private final static String buttonBasketUrnCssSelector = ".basket__urn";
    public final static String cardOfTheProductXpath = "//div[contains(@class, 'basket__specification')]//h3";
    private final static String buttonNegativeXpath = "//div[contains(@class, 'basket__minus')]//button";
    private final static String buttonPositiveXpath = "//div[contains(@class, 'basket__plus')]//button";
    public final static String inputValueThingXpath = "//input[contains(@class, 'basket__number')]";
    private final static String buttonToTheCatalogInBasketXpath = "//a[contains(@class, 'header__item-link')]";

    @FindBy(css = buttonBasketUrnCssSelector)
    WebElement buttonBasketUrn;

    @FindBy(xpath = cardOfTheProductXpath)
    WebElement cardOfTheProduct;

    @FindBy(xpath = buttonNegativeXpath)
    WebElement buttonNegative;

    @FindBy(xpath = buttonPositiveXpath)
    WebElement buttonPositive;

    @FindBy(xpath = inputValueThingXpath)
    WebElement inputValueThing;

    @FindBy(xpath = buttonToTheCatalogInBasketXpath)
    WebElement buttonToTheCatalogInBasket;

    @Story("Удалить один продукт")
    public BasketPage deleteOneProduct() {
        buttonBasketUrn.click();
        return new BasketPage(driver);
    }

    @Story("Удалить все продукты")
    public BasketPage deleteAllProducts() {
        try {
            do {
                buttonBasketUrn.click();
            } while (isElementPresent(By.cssSelector(buttonBasketUrnCssSelector)));
        } catch (NoSuchElementException e) {
            return null;
        }
        return new BasketPage(driver);
    }

    @Story("Увеличить кол-во товаров на заданное кол-во")
    public BasketPage increaseValueOfProducts(int num) {
        for (int i = 0; i < num; i++) {
            try {
                buttonPositive.click();
            } catch (TimeoutException | NoSuchElementException ex) {
                return new BasketPage(driver);
            }
        }
        return new BasketPage(driver);
    }

    @Story("Уменьшить кол-во товаров на заданное кол-во")
    public BasketPage decreaseValueOfProducts(int num) {
        for (int i = 0; i < num; i++) {
            try {
                buttonNegative.click();
            } catch (TimeoutException | NoSuchElementException ex) {
                return new BasketPage(driver);
            }
        }
        return new BasketPage(driver);
    }

    @Story("Перейти в каталог")
    public CatalogPage goToTheCatalog() {
        buttonToTheCatalogInBasket.click();
        return new CatalogPage(driver);
    }

    @Story("Ввести num в поле для ввода кол-ва товара")
    public BasketPage writeValueOnTheInput(String num) {
        inputValueThing.sendKeys(Keys.CONTROL + "a");
        inputValueThing.sendKeys(Keys.DELETE);
        inputValueThing.sendKeys(num);
        return new BasketPage(driver);
    }

    @Story("Костыль для поднятия страницы")
    public BasketPage kostilForPage() {
        driver.findElement(By.tagName("body")).sendKeys(Keys.HOME);
        return new BasketPage(driver);
    }
}
