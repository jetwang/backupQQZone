
import com.google.common.io.Files;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

public class Backup {
    //    public static void main(String[] args) throws IOException {
//        WebDriver driver = getWebDriver();
//        WebDriverWait wait = login(driver);
//        driver.get("http://user.qzone.qq.com/15661273/2");
//        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tblog")));
//        driver.findElement(By.cssSelector("#listArea > ul > li:nth-child(2) > div.list_tit > span > a")).click();
//        downloadFromDetailPage(driver);
//    }
    public static void main(String[] args) throws IOException {
        WebDriver driver = getWebDriver();
        WebDriverWait wait = login(driver);
        driver.get("http://user.qzone.qq.com/15661273/blog/1257737755");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tblog")));
        downloadFromDetailPage(driver);
    }

    private static WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addExtensions(new File("1.1_0.crx"));
        return new ChromeDriver(options);
    }

    private static WebDriverWait login(WebDriver driver) {
        driver.get("http://user.qzone.qq.com/15661273/");
        driver.switchTo().frame("login_frame");

        driver.findElement(By.id("switcher_plogin")).click();
        WebElement userId = driver.findElement(By.id("u"));
        userId.sendKeys("15661273");
        WebElement password = driver.findElement(By.id("p"));
        password.sendKeys("CHENwang9");
        driver.findElement(By.id("login_button")).click();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.urlContains("t="));
        return wait;
    }

    private static void downloadFromDetailPage(WebDriver driver) throws IOException {
        WebDriverWait wait;
        while (true) {
            String pubTime = driver.findElement(By.id("pubTime")).getText();

            String paperTitle = driver.findElement(By.cssSelector("#title > strong > span > span")).getText();
            String blogHTML = driver.findElement(By.id("blogContainer")).getAttribute("innerHTML");
            createBlogHTML(pubTime, paperTitle, blogHTML);
            try {
                WebElement nextArticle = driver.findElement(By.cssSelector("a#navigatorSpan1"));
                if (nextArticle.getAttribute("href").indexOf("blog") > 0) {
                    nextArticle.click();
                    wait = new WebDriverWait(driver, 5);
                    wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("a#navigatorSpan1")));
                } else {
                    break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    static void createBlogHTML(String pubTime, String paperTitle, String blogHTML) throws IOException {
        try {
            pubTime = filter(pubTime) // filter out ? \ / : | < > *
                    .replaceAll("\\s", "_");
            paperTitle = filter(paperTitle) // filter out ? \ / : | < > *
                    .replaceAll("\\s", "_");
            String fileName = "blogs/" + pubTime + "  " + paperTitle + ".html";
            File file = new File(fileName);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
            }
            StringBuilder builder = new StringBuilder();

            builder.append("<html><head>");
            builder.append("\t<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />\n");
            builder.append(blogHTML);
            builder.append("</head></html>");
            Files.write(builder.toString(), file, Charset.forName("UTF-8"));
            System.out.println("Backup: " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static String filter(String pubTime) {
        return pubTime.replaceAll("[\\?\\\\/:|<>\\*\\[\\]@#\\$%~&\\(\\)\\+!\\^,~`]", " ");
    }
}
