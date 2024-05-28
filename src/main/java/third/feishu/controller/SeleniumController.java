package third.feishu.controller;


import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@RestController
@Slf4j
public class SeleniumController {

    @Autowired
    private OllamaChatClient chatClient;

    @Async
    public void getNovel1(int size) {
        String novelUrl = "https://www.bigee.cc/book/33700/";
        String drivePath = "C:\\jiahangchun\\idea\\code\\chromdriver";
        System.setProperty("webdriver.chrom.driver", drivePath);

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
        driver.get(novelUrl);

        WebElement textBox = driver.findElement(By.className("listmain"));
        List<WebElement> elements = textBox.findElements(By.cssSelector("a"));

        Map<String, String> map = new LinkedHashMap();
        for (WebElement webElement : elements) {
            String name = webElement.getText();
            String link = webElement.getAttribute("href");
            if (name.contains("章") && name.contains("第")) {
                map.put(name, link);
            }
        }

        int i = 0;
        for (Map.Entry<String, String> entry : map.entrySet()) {
            i++;
            try {
                String title = entry.getKey();
                String link = entry.getValue();
                driver.get(link);
                WebElement contentElement = driver.findElement(By.id("chaptercontent"));
                String content = contentElement.getText();
                content = content.replaceAll("“", "");
                content = content.replaceAll("”", "");
                String[] list = content.split("\n\n");
                //讲size章的时候中断
                if (i > size) {
                    break;
                }
                for (String s : list) {
                    if (s.contains("点此报错") || s.contains("请收藏本站")) {
                        continue;
                    }
                    String resp = chatClient.call("小说后续是这么发展的：" + s);
                    System.out.println(title + "---->" + s);
                    System.out.println(title + "=======================>" + resp);
                }
                System.out.println(title + " …………………………………………………………………… 讲完了");

                try {
                    Thread.sleep(100);
                } catch (InterruptedException ex) {

                }
            } catch (Exception e) {
                log.info("error {}", e.getMessage(), e);
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException ex) {

                }
            }
        }


        for (int k = 0; k < 1000; k++) {
            String value = chatClient.call("你猜后续会怎么发展？请用200字描述下");
            System.out.println(k + "ooooooooooooooooooooo" + value);
        }
        driver.quit();
    }

    /**
     * demo
     *
     * @param args
     */
    public static void main(String[] args) {
        new SeleniumController().getNovel1(3);


//        String drivePath="C:\\jiahangchun\\idea\\code\\chromdriver";
//        System.setProperty("webdriver.chrom.driver",drivePath);
//
//        WebDriver driver = new ChromeDriver();
//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
//
//        String title = driver.getTitle();
//
//        WebElement textBox = driver.findElement(By.name("my-text"));
//        WebElement submitButton = driver.findElement(By.cssSelector("button"));
//
//        textBox.sendKeys("Selenium");
//        submitButton.click();
//
//        WebElement message = driver.findElement(By.id("message"));
//        String value = message.getText();
//        System.out.println(value);
//        driver.quit();
    }

}
