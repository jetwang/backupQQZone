
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class BackupFromLastFailedBlog {
    public static void main(String[] args) throws IOException {
        WebDriver driver = Backup.getWebDriver();
        WebDriverWait wait = Backup.login(driver);
        driver.get("http://user.qzone.qq.com/1035665670/blog/1406872491");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tblog")));
        Backup.downloadFromDetailPage(driver);
    }
}
