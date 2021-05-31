import dataProvider.ConfigFileReader;
import org.junit.FixMethodOrder;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.util.List;
import java.util.concurrent.TimeUnit;
import org.testng.Assert;
import org.testng.annotations.*;

@FixMethodOrder()
public class MainSuite {

    private static ChromeDriver driver;
    private static WebDriverWait wait;
    private String EXPECTED_RESULT_SUCCESS_LABEL;
    private String ACTUAL_RESULT_LABEL;
    private static ConfigFileReader configFileReader;

    @BeforeClass
    public static void init(){

        configFileReader = new ConfigFileReader();
        System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, 10);
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();
    }

    @Test
    public void AgoToHomePage(){

        String URL = configFileReader.getURL();
        String EXPECTED_RESULT_SUCCESS_LABEL = "My Store";

        driver.get(URL);
        String ACTUAL_RESULT_LABEL = driver.getTitle();
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void BselectProduct(){

        String itemURL = "http://automationpractice.com/index.php?id_product=2&controller=product";
        String EXPECTED_RESULT_SUCCESS_LABEL = "Blouse";
        String ACTUAL_RESULT_LABEL = "";

        List<WebElement> aTagElements = driver.findElementsByTagName("a");;
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
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void CaddToCart(){

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
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void DproceedToCheckout() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Your shopping cart";

        WebElement proceedToCheckoutButton = driver.findElementByClassName("button-medium");
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void EvalidateSummary() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Authentication";

        WebElement proceedToCheckoutButton = driver.findElementByClassName("standard-checkout");
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void FsignIn() {

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
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void GvalidateShippingAddress() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Shipping";

        WebElement proceedToCheckoutButton = driver.findElementByName("processAddress");
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void HchooseShippingMethod() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Your payment method";

        WebElement checkbox = driver.findElementById("cgv");
        WebElement proceedToCheckoutButton = driver.findElementByName("processCarrier");
        if (!checkbox.isSelected()) {
            checkbox.click();
        }
        proceedToCheckoutButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void IchoosePaymentMethod() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Bank-wire payment.";
        WebElement payByBankWireButton = driver.findElementByClassName("bankwire");
        payByBankWireButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @Test
    public void JconfirmOrder() {

        EXPECTED_RESULT_SUCCESS_LABEL = "Order confirmation";

        WebElement confirmOrderButton = driver.findElementByCssSelector("#cart_navigation > button");
        confirmOrderButton.click();
        ACTUAL_RESULT_LABEL = driver.findElementByClassName("navigation_page").getAttribute("innerText");
        Assert.assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @AfterClass
    public static void tearDown(){
        driver.close();
    }

}
