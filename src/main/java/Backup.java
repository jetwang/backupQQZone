
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;

public class Backup {
    static WebDriver getWebDriver() {
        System.getProperties().setProperty("webdriver.chrome.driver","chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("1.1_0.crx"));
        return new ChromeDriver(options);
    }

    static WebDriverWait login(WebDriver driver) {
        driver.get("http://user.qzone.qq.com/273081688");
        driver.switchTo().frame("login_frame");

        driver.findElement(By.id("switcher_plogin")).click();
        WebElement userId = driver.findElement(By.id("u"));
        userId.sendKeys("273081688");
        WebElement password = driver.findElement(By.id("p"));
        password.sendKeys("");
        driver.findElement(By.id("login_button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.className("head-nav-menu")));
        return wait;
    }

    static void downloadFromDetailPage(WebDriver driver) throws IOException {
        WebDriverWait wait;
        while (true) {
            String pubTime = driver.findElement(By.id("pubTime")).getText();

            String paperTitle = driver.findElement(By.cssSelector("#title > strong > span > span")).getText();
            String blogHTML = driver.findElement(By.id("blogDetailDiv")).getAttribute("innerHTML");
            BlogStore.createBlogHTML("ll", pubTime, paperTitle, blogHTML);
            try {
                WebElement nextArticle = driver.findElement(By.cssSelector("a#navigatorSpan1"));
                if (nextArticle.getAttribute("href").indexOf("blog") > 0) {
                    nextArticle.click();
                    wait = new WebDriverWait(driver, 5);
                    wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("a#navigatorSpan1")));
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


}
