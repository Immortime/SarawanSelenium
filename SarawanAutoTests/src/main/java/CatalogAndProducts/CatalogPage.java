package CatalogAndProducts;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class CatalogPage extends BasePageObject {

    public CatalogPage (WebDriver driver) {
        super(driver);
    }
    Random r = new Random();

    private final static String categoryCardsXpath = "//a[contains(@class, 'CardCategory_categoryCard__exxjI')]//h3";
    private final static String buttonBasketXpath = "//div[contains(@class, 'header__group')]//a[contains(@href, '/basket')]";
    private final static String buttonWatchAllPopularProductsXpath = "//button[contains(@class, 'button btn-secondary')]//a";

    @FindBy(xpath = categoryCardsXpath)
    private List<WebElement> categoryCards;

    @FindBy(xpath = buttonBasketXpath)
    WebElement buttonBasket;

    @FindBy(xpath = buttonBasketXpath)
    WebElement buttonWatchAllPopularProducts;

    @Step("Кликнуть на категорию товара по имени")
    public ProductsFromCatalogPage clickOnTheCategory() {
        for (int retries = 0;; retries++) {
            try {
                categoryCards.stream().skip(r.nextInt(categoryCards.size())).findFirst().get().click();
                return new ProductsFromCatalogPage(driver);
            } catch (NoSuchElementException | ElementClickInterceptedException ex) {
                if (retries < 6) {
                    continue;
                } else {
                    throw ex;
                }
            }
        }
    }

    @Step("Кликнуть на корзину")
    public BasketPage clickOnTheBasketIcon() {
        buttonBasket.click();
        return new BasketPage(driver);
    }

    @Step("Кликнуть на смотреть все популярные товары")
    public PopularProductsPage clickOnThePopularProductsButton() {
        buttonWatchAllPopularProducts.click();
        return new PopularProductsPage(driver);
    }
}
