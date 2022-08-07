package CatalogAndProducts;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class PopularProductsPage extends BasePageObject {

    public PopularProductsPage(WebDriver driver) {
        super(driver);
    }

    private final static String productCardsXpath = "//div[contains(@class, 'card__link')]//h3";

    @FindBy(xpath = productCardsXpath)
    private List<WebElement> productCards;
}
