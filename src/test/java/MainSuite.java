import dataProvider.ConfigFileReader;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainSuite {

    private static ChromeDriver driver;
    private static WebDriverWait wait;
    private String EXPECTED_RESULT_SUCCESS_LABEL;
    private String ACTUAL_RESULT_LABEL;
    private static ConfigFileReader configFileReader;

    @BeforeAll
    public static void init(){

        configFileReader = new ConfigFileReader();
        System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    @Order(1)
    public void goToHomePage(){

        String URL = configFileReader.getHomePageURL();
        String EXPECTED_RESULT_SUCCESS_LABEL = "My Store";

        driver.get(URL);
        String ACTUAL_RESULT_LABEL = driver.getTitle();
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(2)
    public void selectProduct(){

        String itemURL = configFileReader.getItemURL();
        String EXPECTED_RESULT_SUCCESS_LABEL = "Blouse";
        String ACTUAL_RESULT_LABEL = "";

        List<WebElement> aTagElements = driver.findElementsByTagName("a");
        for (WebElement quickViewElement : aTagElements){
            if (quickViewElement.getAttribute("href").equals(itemURL)) {

                quickViewElement.click();
                wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("fancybox-iframe")));
                WebElement iframe = driver.findElementByClassName("fancybox-iframe");
                driver.switchTo().frame(iframe);
                List<WebElement> headerElements = driver.findElementsByTagName("h1");
                for (WebElement header : headerElements) {

                    ACTUAL_RESULT_LABEL = header.getAttribute("innerHTML");
                    break;
                }
                break;
            }
        }
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(3)
    public void addToCart(){

        String EXPECTED_RESULT_SUCCESS_LABEL = "Product successfully added to your shopping cart";
        String ACTUAL_RESULT_LABEL = "";

        WebElement addToCartBtn = driver.findElementByName("Submit");
        addToCartBtn.click();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("icon-ok")));
        List<WebElement> addToCartHeaders = driver.findElementsByTagName("h2");
        for (WebElement header : addToCartHeaders) {
            if (header.getAttribute("innerText").equals(EXPECTED_RESULT_SUCCESS_LABEL)) {

                ACTUAL_RESULT_LABEL = header.getAttribute("innerText");
                break;
            }
        }
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(4)
    public void proceedToCheckout() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Your shopping cart";

        WebElement proceedToCheckoutButton = driver.findElementByClassName("button-medium");
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(5)
    public void validateSummary() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Authentication";

        WebElement proceedToCheckoutButton = driver.findElementByClassName("standard-checkout");
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(6)
    public void signIn() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Addresses";
        String USERNAME = configFileReader.getUsername();
        String PASSWORD = configFileReader.getPassword();

        WebElement emailField = driver.findElementById("email");
        WebElement passwordField = driver.findElementById("passwd");
        WebElement submitButton = driver.findElementById("SubmitLogin");
        emailField.sendKeys(USERNAME);
        passwordField.sendKeys(PASSWORD);
        submitButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(7)
    public void validateShippingAddress() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Shipping";

        WebElement proceedToCheckoutButton = driver.findElementByName("processAddress");
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(8)
    public void chooseShippingMethod() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Your payment method";

        WebElement checkbox = driver.findElementById("cgv");
        WebElement proceedToCheckoutButton = driver.findElementByName("processCarrier");
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(9)
    public void choosePaymentMethod() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Bank-wire payment.";
        WebElement payByBankWireButton = driver.findElementByClassName("bankwire");
        payByBankWireButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    @Order(10)
    public void confirmOrder() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Order confirmation";

        WebElement confirmOrderButton = driver.findElementByCssSelector("#cart_navigation > button");
        confirmOrderButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @AfterAll
    public static void tearDown(){
        driver.close();
    }

}
