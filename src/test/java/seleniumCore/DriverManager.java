package seleniumCore;

import org.openqa.selenium.WebDriver;

//Implementing the Factory Design Pattern
public abstract class DriverManager {

    protected WebDriver driver;
    protected  abstract void createWebDriver();
    public void quitWebDriver(){
        if (driver != null){
            driver.quit();
            driver = null;
        }
    }
    public WebDriver getWebDriver(){
        if (driver == null){
            createWebDriver();
        }
        return driver;
    }
}
