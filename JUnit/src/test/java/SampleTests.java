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
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class SampleTests {
    protected static WebDriver driver;
    private Logger logger = LogManager.getLogger(SampleTests.class);
    private ServerConfig cfg = ConfigFactory.create(ServerConfig.class);

    /*@Test
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
        driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        logger.info("Before test: Драйвер поднят");
    }

    @Test
    public void openPage() throws Exception {

        driver.get("https://market.yandex.ru/");
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.titleContains("Яндекс.Маркет"));
        Assert.assertEquals("Заголовок страницы не равен ожидаемому", driver.getTitle(), "Яндекс.Маркет — выбор и покупка товаров из проверенных интернет-магазинов");
        logger.info("Step 1: Открытие ссылки https://market.yandex.ru/");

        Thread.sleep(7000);
        driver.findElement(By.linkText("Электроника")).click();
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.titleContains("Электроника"));
        Assert.assertEquals("Заголовок страницы не равен ожидаемому", driver.getTitle(), "Электроника — купить на Яндекс.Маркете");
        logger.info("Step 2: Открытие раздела \"Электроника\"");

        driver.findElement(By.linkText("Мобильные телефоны")).click();
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.titleContains("телефоны"));
        Assert.assertTrue(driver.getTitle().contains("телефоны"));
        logger.info("Step 3: Открытие раздела \"Мобильные телефоны\"");

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_PAGE_DOWN);

        checkBoxClickProducer("7893318_15292504");
        logger.info("Step 4: Выбран производитель  \"HONOR\"");

        checkBoxClickProducer("7893318_153061");
        logger.info("Step 5: Выбран производитель  \"Samsung\"");

        WebElement waiting = driver.findElement(By.xpath("/html[1]/body[1]/div[2]/div[7]/div[2]/div[1]/div[1]/div[1]/div[1]/div[2]/div[1]/div[1]"));
        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(waiting));
        driver.findElement(By.xpath("//div[@class='layout layout_type_maya']//button[text()='по цене']")).click();
        logger.info("Step 6: Произведена фильтрация по цене ASC");


        new WebDriverWait(driver, 10).until(ExpectedConditions.invisibilityOf(waiting));
        new WebDriverWait(driver, 20).until(ExpectedConditions.invisibilityOf(driver.findElement(By.xpath("/html/body/div[12]/div[3]"))));
        List<String> phoneList = getPathToPhone("SAMSUNG");
        WebElement addPhone1 = driver.findElement(By.xpath(phoneList.get(1)));

        Actions action = new Actions(driver);
        action.moveToElement(addPhone1).build().perform();
        addPhone1.click();
        String phoneName = changeProduct(phoneList.get(0));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@data-apiary-widget-name='@market/PopupInformer']/div[1]/div[1]/div[2]/div[1]")).getText().contains(phoneName));
        logger.info("Step 7: Смартфон Samsung добавлен к сравнению");

        phoneList = getPathToPhone("HONOR");
        addPhone1 = driver.findElement(By.xpath(phoneList.get(1)));

        action = new Actions(driver);
        action.moveToElement(addPhone1).build().perform();
        addPhone1.click();
        phoneName = changeProduct(phoneList.get(0));
        Assert.assertTrue(driver.findElement(By.xpath("//div[@data-apiary-widget-name='@market/PopupInformer']/div[1]/div[1]/div[2]/div[1]")).getText().contains(phoneName));
        logger.info("Step 8: Смартфон Honor добавлен к сравнению");

        driver.findElement(By.linkText("Сравнить")).click();
        (new WebDriverWait(driver, 5)).until(ExpectedConditions.titleContains("Сравнение"));
        Assert.assertTrue(driver.getTitle().contains("Сравнение товаров"));
        logger.info("Step 9: Выполнен переход на страницу сравнения выбранных товаров");

        Assert.assertTrue(driver.findElements(By.xpath("/html/body/div[2]/div[5]/div[2]/div/div[2]/div/div/div/div")).size() == 2);
        logger.info("Step 10: Проверено, что в сравнении 2 позиции");

        driver.findElement(By.xpath("//button[text()='Все характеристики']")).click();
        logger.info("Step 11: Нажата кнопка все характеристики");

        Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Операционная система']")).isDisplayed());
        logger.info("Step 12: Проверено отбражение пункта \"Операционная система\"");

        driver.findElement(By.xpath("//button[text()='Различающиеся характеристики']")).click();
        logger.info("Step 13: Нажата кнопка \"Различающиеся характеристики\"");

        Assert.assertTrue(driver.findElements(By.xpath("//div[text()='Операционная система']")).isEmpty());
        logger.info("Step 14: Проверено отсутствие пункта \"Операционная система\"");
    }

    public static ArrayList<String> getPathToPhone(String name) throws Exception {
        ArrayList<String> result = new ArrayList<String>();
        //Thread.sleep(2000);
        List<WebElement> elementArrayList = driver.findElements(By.tagName("article"));
        for (int i = 0; i < 47; i++) {

            if (elementArrayList.get(i).getText().contains(name) & (elementArrayList.get(i).getText().contains("Смартфон") || elementArrayList.get(i).getText().contains("Телефон"))) {
                String[] list = elementArrayList.get(i).getText().split("\n");
                if (list[0].equals("Новинка")) {
                    result.add(list[2]);
                } else {
                    result.add(list[1]);
                }
                i += 1;
                result.add("//div[@class='cia-vs']//article[" + i + "]/div[2]/div[1]");
                i = 50;
            }
        }
        return result;
    }

    public static String changeProduct(String str) {
        str = str.replaceAll("Смартфон ", "");
        str = str.replaceAll("Телефон ", "");

        return str;
    }

    public static void checkBoxClickProducer(String id) {
        WebElement element = driver.findElement(By.id(id));
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("arguments[0].click()", element);
        Assert.assertTrue(element.isSelected());
    }

    @After
    public void setDown() {
        if (driver != null) {
            driver.quit();
            logger.info("Закрытие браузера");
        }
    }
}
