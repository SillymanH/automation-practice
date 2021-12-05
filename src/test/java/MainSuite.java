import POM.HomePage;
import dataProvider.ConfigFileReader;
import io.qameta.allure.Step;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import seleniumCore.DriverManager;
import seleniumCore.DriverManagerFactory;
import seleniumCore.DriverType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class MainSuite {

    static DriverManager driverManager;
    static WebDriver driver;
    static WebDriverWait wait;
    String EXPECTED_RESULT_SUCCESS_LABEL;
    String ACTUAL_RESULT_LABEL;
    HomePage homePage;

    @BeforeAll
    public static void init(){

        driverManager = DriverManagerFactory.getDriverManager(DriverType.CHROME);
        driver = driverManager.getWebDriver();
    }

    @Test
    public void guestCheckout(){

        EXPECTED_RESULT_SUCCESS_LABEL = "Order confirmation";

        homePage = new HomePage(driver);
        homePage.goToHomePage();
        homePage.selectProduct();
        homePage.addToCart();
        homePage.proceedToCheckout();
        homePage.validateSummary();
        homePage.signIn();
        homePage.validateShippingAddress();
        homePage.chooseShippingMethod();
        homePage.choosePaymentMethod();
        homePage.confirmOrder();

        ACTUAL_RESULT_LABEL = driver.findElement(By.className("navigation_page")).getAttribute("innerText");
        assertEquals(EXPECTED_RESULT_SUCCESS_LABEL, ACTUAL_RESULT_LABEL);
    }

    @AfterAll
    public static void tearDown(){
        driverManager.quitWebDriver();
    }

}
