import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.Select;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

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
        //ChromeOptions options = new ChromeOptions();
        //options.addArguments("headless");
        driver = new ChromeDriver();
        logger.info("Драйвер поднят");
    }

    @Test
    public void openPage() {
        driver.get(cfg.url());
        logger.info("Открыта страница " + cfg.url());
        Assert.assertEquals("Заголовок страницы не равен ожидаемому",driver.getTitle(),"Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям");
    }

    @Test
    public void getCookies(){
        driver.get(cfg.url());
        driver.manage().addCookie(new Cookie("Otus1","value1"));
        driver.manage().addCookie(new Cookie("Otus2","value2"));
        Cookie cookie = new Cookie("Otus3","value3");
        driver.manage().addCookie(cookie);
        driver.manage().addCookie(new Cookie("Otus4","value4"));
        logger.info(driver.manage().getCookies().toString());
        logger.info(driver.manage().getCookieNamed("Otus1"));
        driver.manage().deleteCookieNamed("Otus2");
        driver.manage().deleteCookie(cookie);
        driver.manage().deleteAllCookies();
        Assert.assertEquals(0, driver.manage().getCookies().size());

    }

@Test
public void testWait30sec(){
     driver.get(cfg.url());
    driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
     driver.findElement(By.cssSelector("body > div.body-wrapper > div > div.container > div > div > button")).click();
     //driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
     //driver.findElement(By.id("jivo_close_button")).click();
}

@Test
public void moveWindows(){
    driver.get(cfg.url());
    driver.manage().timeouts().implicitlyWait(3, TimeUnit.SECONDS);
    driver.manage().window().setPosition(new Point(200,20));
    driver.manage().window().setPosition(new Point(330,330));
    driver.manage().window().setPosition(new Point(40,450));
}

@Test
public void headlesTest(){
    driver.get(cfg.url());
    logger.info("Открыта страница " + cfg.url());
    Assert.assertEquals("Заголовок страницы не равен ожидаемому",driver.getTitle(),"Онлайн‑курсы для профессионалов, дистанционное обучение современным профессиям");

}

@Test
public void openYandex(){
        driver.get("https://ya.ru");
        driver.findElement(By.id("text")).sendKeys("otus");
    Select select = new Select(driver.findElement(By.className("suggest2-item__text")));
    select.getFirstSelectedOption().click();
        //driver.findElement(By.className("search2__button")).click();
}

    @After
    public void setDown() {
       /* if (driver != null) {
            driver.quit();
            logger.info("Закрытие браузера");
        }*/
    }
}
