import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.GeckoDriverService;
import org.openqa.selenium.safari.SafariDriver;

public class Main {
    public static void main(String args[]){
        //System.setProperty("webdriver.chrome.driver", "/Users/u17364669/IdeaProjects/OtusLearning/src/main/resources/WebDrivers/chromedriver");
        //WebDriver webDriver = new ChromeDriver();
        System.setProperty("webdriver.gecko.driver", "../OtusLearning/src/main/resources/WebDrivers/geckodriver");
        WebDriver webDriver = new FirefoxDriver();

        webDriver.get("https://otus.ru");
    }
}
