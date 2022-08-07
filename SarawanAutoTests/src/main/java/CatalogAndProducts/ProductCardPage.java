package CatalogAndProducts;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;
import java.util.Random;

public class ProductCardPage extends BasePageObject {

    public ProductCardPage(WebDriver driver) {
        super(driver);
    }
    Random r = new Random();

    private final static String buttonAddToTheBasketXpath = "//div[contains(@class, 'ProductCardModal_card__btn-to-basket__YLEbj')]//button[contains(@class, 'button btn-main')]";
    private final static String buttonNegativeOnAddingXpath = "//div[contains(@class, 'ProductCardModal_card__shop-toBasket__kwE7B ProductCardModal_card__shop-toBasket--active__9nA0z')]//button[contains(text(), '-')]";
    private final static String buttonPositiveOnAddingXpath = "//div[contains(@class, 'ProductCardModal_card__shop-toBasket__kwE7B ProductCardModal_card__shop-toBasket--active__9nA0z')]//button[contains(text(), '+')]";
    private final static String buttonBuyOnTheBottomOfTheCardXpath = "//div[contains(@class, 'inModal')]//button[contains(@class, 'button btn-main btn-small')]";
    private final static String buttonTheCartOnTheBottomXpath = "//div[contains(@class, 'ProductCardModal_card__shop-toBasket__kwE7B')]/*[name()='svg']/*[name()='path']";
    private final static String buttonExitFromTheCardXpath = "//button[contains(@class, 'button btn-clear btn-close')]";

    @FindBy(xpath = buttonAddToTheBasketXpath)
    WebElement buttonAddToTheBasket;

    @FindBy(xpath = buttonNegativeOnAddingXpath)
    WebElement buttonNegativeOnAdding;

    @FindBy(xpath = buttonPositiveOnAddingXpath)
    WebElement buttonPositiveOnAdding;

    @FindBy(xpath = buttonBuyOnTheBottomOfTheCardXpath)
    private List<WebElement> buttonBuyOnTheBottomOfTheCard;

    @FindBy(xpath = buttonTheCartOnTheBottomXpath)
    WebElement buttonTheCartOnTheBottom;

    @FindBy(xpath = buttonExitFromTheCardXpath)
    WebElement buttonExitFromTheCard;

    @Step("Купить продукт из предложенных")
    public ProductCardPage clickOnBuyAnotherProduct() {
        for (int retries = 0;; retries++) {
            try {
                buttonBuyOnTheBottomOfTheCard.stream().skip(4).findFirst().get().click();
                return new ProductCardPage(driver);
            } catch (NoSuchElementException | ElementNotInteractableException ex) {
                if (retries < 5) {
                    continue;
                } else {
                    throw ex;
                }
            }
        }
    }

    @Step("Добавить товар в корзину")
    public ProductCardPage addToTheBasket() {
        webDriverWait.until(ExpectedConditions.elementToBeClickable(By.xpath(buttonAddToTheBasketXpath)));
        buttonAddToTheBasket.click();
        return new ProductCardPage(driver);
    }

    @Step("Увеличить кол-во товара")
    public ProductCardPage increaseValueOnNum(int num) {
        try {
            for (int i = num; i > 0; i--) {
                buttonPositiveOnAdding.click();
            }
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException ex) {
            return new ProductCardPage(driver);
        }
        return new ProductCardPage(driver);
    }

    @Step("Уменьшить кол-во товара")
    public ProductCardPage decreaseValueOnNum(int num) {
        try {
            for (int i = 0; i < num; i++) {
                buttonNegativeOnAdding.click();
            }
        } catch (TimeoutException | NoSuchElementException ex) {
                return new ProductCardPage(driver);
        }
        return new ProductCardPage(driver);
    }

    @Step("Кликнуть на корзинку в нижнем блоке")
    public ProductCardPage clickOnTheCart() {
        buttonTheCartOnTheBottom.click();
        return new ProductCardPage(driver);
    }

    @Step("Выйти из карточки товара")
    public ProductsFromCatalogPage exitTheCard() {
        buttonExitFromTheCard.click();
        return new ProductsFromCatalogPage(driver);
    }
}
