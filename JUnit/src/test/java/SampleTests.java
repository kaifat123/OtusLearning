import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SampleTests {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTests.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

   /* @Test
    public void log() {
        logger.info("Info log");
        logger.warn("Warn log");
        logger.debug("Debug log");
        logger.fatal("Fatal log");
        logger.error("Error log");
    }*/

    @Before
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @Test
    public void openPage() {
        driver.get(cfg.url());
        logger.info("Открыта страница " + cfg.url());
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Закрытие браузера");
        }
    }
}
