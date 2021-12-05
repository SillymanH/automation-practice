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
    static WebDriverWait wait;
    String EXPECTED_RESULT_SUCCESS_LABEL;
    String ACTUAL_RESULT_LABEL;
    static ConfigFileReader configFileReader  = new ConfigFileReader();

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
        List<WebElement> aTagElements = driver.findElements(By.tagName("a"));
        for (WebElement quickViewElement : aTagElements){
            if (quickViewElement.getAttribute("href").equals(itemURL)) {
                quickViewElement.click();
                break;
            }
        }
    }
    public void addToCart(){

        WebElement addToCartBtn = driver.findElement(By.name("Submit"));
        addToCartBtn.click();
        driver.switchTo().defaultContent();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("icon-ok")));
    }
    public void proceedToCheckout() {

        WebElement proceedToCheckoutButton = driver.findElement(By.className("button-medium"));
        proceedToCheckoutButton.click();
    }
    public void validateSummary() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Authentication";
        WebElement proceedToCheckoutButton = driver.findElement(By.className("standard-checkout"));
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElement(By.className("navigation_page")).getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }
    public void signIn() {

        String USERNAME = configFileReader.getUsername();
        String PASSWORD = configFileReader.getPassword();
        WebElement emailField = driver.findElement(By.id("email"));
        WebElement passwordField = driver.findElement(By.id("passwd"));
        WebElement submitButton = driver.findElement(By.id("SubmitLogin"));
        emailField.sendKeys(USERNAME);
        passwordField.sendKeys(PASSWORD);
        submitButton.click();
    }
    public void validateShippingAddress() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Shipping";
        WebElement proceedToCheckoutButton = driver.findElement(By.name("processAddress"));
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElement(By.className("navigation_page")).getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }
    public void chooseShippingMethod() {

        WebElement checkbox = driver.findElement(By.id("cgv"));
        WebElement proceedToCheckoutButton = driver.findElement(By.name("processCarrier"));
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        proceedToCheckoutButton.click();
    }
    public void choosePaymentMethod() {

        WebElement payByBankWireButton = driver.findElement(By.className("bankwire"));
        payByBankWireButton.click();

    }
    public void confirmOrder() {

        WebElement confirmOrderButton = driver.findElement(By.cssSelector("#cart_navigation > button"));
        confirmOrderButton.click();

    }

}
