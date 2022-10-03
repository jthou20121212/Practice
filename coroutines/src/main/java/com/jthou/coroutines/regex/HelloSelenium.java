package com.jthou.coroutines.regex;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;
import java.util.List;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.github.bonigarcia.wdm.WebDriverManager;

class HelloSelenium {

    public static void main(String[] args) {
        // System.setProperty("webdriver.chrome.driver", "driver/chromedriver");
        WebDriverManager.chromedriver().setup();
        try {
            WebDriver driver = new ChromeDriver();
            driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
            driver.get("https://appgallery.huawei.com/app/C102708419");
            List<WebElement> list = driver.findElements(By.className("info_val"));
            Pattern pattern = Pattern.compile("(\\d\\.\\d\\.\\d)");
            list.forEach(new Consumer<WebElement>() {
                @Override
                public void accept(WebElement webElement) {
                    String text = webElement.getText();
                    Matcher matcher = pattern.matcher(text);
                    if (matcher.find()) {
                        String group = matcher.group(1);
                        System.out.println("group : " + group);
                    }
                }
            });
            driver.quit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
