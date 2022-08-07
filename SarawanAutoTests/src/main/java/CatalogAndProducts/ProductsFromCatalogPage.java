package CatalogAndProducts;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;

public class ProductsFromCatalogPage extends BasePageObject {

    public ProductsFromCatalogPage (WebDriver driver) {
        super(driver);
    }
    Random r = new Random();

    private static final String productCardXpath = "//div[contains(@class, 'card__link')]//h3";
    private final static String buttonBasketXpath = "//div[contains(@class, 'header__group')]//a[contains(@href, '/basket')]";
    private final static String buttonIncreaseValueXpath = "//button[contains(@class, 'button btn-clear') and contains(text(), '+')]";
    private final static String buttonDecreaseValueXpath = "//button[contains(@class, 'button btn-clear') and contains(text(), '-')]";
    private final static String buttonBuyOnTheCardXpath = "//button[contains(@class, 'button btn-main btn-small') and contains(text(), 'Купить')]";

    @FindBy(xpath = productCardXpath)
    private List<WebElement> productCard;

    @FindBy(xpath = buttonBasketXpath)
    WebElement buttonBasket;

    @FindBy(xpath = buttonIncreaseValueXpath)
    WebElement buttonIncreaseValue;

    @FindBy(xpath = buttonDecreaseValueXpath)
    WebElement buttonDecreaseValue;

    @FindBy(xpath = buttonBuyOnTheCardXpath)
    WebElement buttonBuyOnTheCard;

    @Step("Кликнуть на карточку товара по названию")
    public ProductCardPage clickOnTheProductCard() {
        for (int retries = 0;; retries++) {
            try {
                productCard.stream().skip(r.nextInt(productCard.size())).findFirst().get().click();
                return new ProductCardPage(driver);
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

    @Step("Купить товар и попробовать уменшить его кол-во до отрицательного")
    public ProductsFromCatalogPage clickOnTheBuyButtonAndTryGoNegative(int num) {
        webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonBuyOnTheCardXpath)));
        buttonBuyOnTheCard.click();
        try {
            for (int i = 0; i < num; i++) {
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonDecreaseValueXpath)));
                buttonDecreaseValue.click();
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonBuyOnTheCardXpath)));
                buttonBuyOnTheCard.click();
                }
            } catch (TimeoutException e) {
            for (int i = 0; i < num; i++) {
                webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(buttonDecreaseValueXpath)));
                buttonDecreaseValue.click();
            }
        }
        return new ProductsFromCatalogPage(driver);
    }
}
