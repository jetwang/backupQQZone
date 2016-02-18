import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;

public class BackupFromBegining {
    public static void main(String[] args) throws IOException {
        WebDriver driver = Backup.getWebDriver();
        WebDriverWait wait = Backup.login(driver);
        driver.get("http://user.qzone.qq.com/1035665670/2");
        wait.until(ExpectedConditions.frameToBeAvailableAndSwitchToIt(By.id("tblog")));
        driver.findElement(By.cssSelector("#listArea > ul > li:nth-child(2) > div.list_tit > span > a")).click();
        Backup.downloadFromDetailPage(driver);
    }

}
