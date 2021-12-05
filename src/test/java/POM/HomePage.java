package POM;
import dataProvider.ConfigFileReader;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class HomePage {

    WebDriver driver;
    String EXPECTED_RESULT_SUCCESS_LABEL;
    String ACTUAL_RESULT_LABEL;

    static WebDriverWait wait;
    static ConfigFileReader configFileReader;

    //POM Objects
    private final By aTags = By.tagName("a");
    private final By addToCartBtn = By.name("Submit");
    private final By okayIcon = By.className("icon-ok");
    private final By proceedToCheckoutBtn = By.className("button-medium");
    private final By proceedToCheckoutSummaryBtn = By.className("standard-checkout");
    private final By navigationPageLocator = By.className("navigation_page");
    private final By emailField = By.id("email");
    private final By passwordField = By.id("passwd");
    private final By submitButton = By.id("SubmitLogin");
    private final By proceedToCheckoutShippingBtn = By.name("processAddress");
    private final By shippingMethodCheckbox = By.id("cgv");
    private final By proceedToCheckoutShippingMethodBtn = By.name("processCarrier");
    private final By payByBankWireBtn = By.className("bankwire");
    private final By confirmOrderBtn = By.cssSelector("#cart_navigation > button");

    public HomePage(WebDriver driver){

        this.driver = driver;
        wait = new WebDriverWait(driver, 10);
        configFileReader = new ConfigFileReader();
    }
    public String getPageTitle(){
        return driver.getTitle();
    }
    public void goToHomePage(){

        String URL = configFileReader.getHomePageURL();
        driver.get(URL);

    }
    public void selectProduct(){

        String itemURL = configFileReader.getItemURL();
        List<WebElement> aTagElements = driver.findElements(aTags);
        for (WebElement quickViewElement : aTagElements){
            if (quickViewElement.getAttribute("href").equals(itemURL)) {
                quickViewElement.click();
                break;
            }
        }
    }
    public void addToCart(){

        driver.findElement(addToCartBtn).click();
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(okayIcon));
    }
    public void proceedToCheckout() {

        driver.findElement(proceedToCheckoutBtn).click();
    }
    public void validateSummary() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Authentication";
        driver.findElement(proceedToCheckoutSummaryBtn).click();
        ACTUAL_RESULT_LABEL = driver.findElement(navigationPageLocator).getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }
    public void signIn() {

        String USERNAME = configFileReader.getUsername();
        String PASSWORD = configFileReader.getPassword();
        driver.findElement(emailField).sendKeys(USERNAME);
        driver.findElement(passwordField).sendKeys(PASSWORD);
        driver.findElement(submitButton).click();
    }
    public void validateShippingAddress() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Shipping";
        driver.findElement(proceedToCheckoutShippingBtn).click();
        ACTUAL_RESULT_LABEL = driver.findElement(navigationPageLocator).getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }
    public void chooseShippingMethod() {

        if (!driver.findElement(shippingMethodCheckbox).isSelected()) {
            driver.findElement(shippingMethodCheckbox).click();
        }
        driver.findElement(proceedToCheckoutShippingMethodBtn).click();
    }
    public void choosePaymentMethod() {

        driver.findElement(payByBankWireBtn).click();
    }
    public void confirmOrder() {

        driver.findElement(confirmOrderBtn).click();

    }

}
