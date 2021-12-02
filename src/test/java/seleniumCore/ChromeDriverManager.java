package seleniumCore;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import dataProvider.ConfigFileReader;

//Implementing the Factory Design Pattern
public class ChromeDriverManager extends DriverManager{

    static ConfigFileReader configFileReader;

    @Override
    protected void createWebDriver() {

        configFileReader = new ConfigFileReader();
        System.setProperty("webdriver.chrome.driver", configFileReader.getDriverPath());

        //Setting Chrome options
        ChromeOptions options = new ChromeOptions();
        options.addArguments("start-maximized");
        options.addArguments("incognito");
        options.addArguments("disable-popup-blocking");
        options.addArguments("disable-infobars");

        this.driver = new ChromeDriver(options);
    }
}
