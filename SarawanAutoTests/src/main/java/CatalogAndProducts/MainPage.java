package CatalogAndProducts;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class MainPage extends BasePageObject {

    public MainPage (WebDriver driver) {
        super(driver);
    }

    private final static String buttonBasketXpath = "//div[contains(@class, 'header__group')]//a[contains(@href, '/basket')]";
    private final static String buttonCatalogXpath = "//div[contains(@class, 'header__bottom')]//a[contains(@href, '/category')]";
    public final static String buttonMainPage = "//a[contains(@class, 'header__logo')]";

    @FindBy(xpath = buttonBasketXpath)
    WebElement buttonBasket;

    @FindBy(xpath = buttonCatalogXpath)
    WebElement buttonCatalog;

    @Step("Кликнуть на корзину")
    public BasketPage clickOnTheBasketIcon() {
        buttonBasket.click();
        return new BasketPage(driver);
    }

    @Step("Кликнуть на Каталог")
    public CatalogPage clickOnTheCatalogButton() {
        buttonCatalog.click();
        return new CatalogPage(driver);
    }
}
