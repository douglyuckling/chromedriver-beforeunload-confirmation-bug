package com.example;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.equalTo;

@SpringBootApplication
public class TestApplication implements ApplicationRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestApplication.class, args);
    }

    @Autowired
    private ServletWebServerApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        int port = applicationContext.getWebServer().getPort();
        String baseUrl = String.format("http://127.0.0.1:%d/", port);

        ChromeDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(2, SECONDS);
        Runtime.getRuntime().addShutdownHook(new Thread(driver::quit));

        driver.navigate().to(baseUrl + "unloadConfirmation.html");

        for (int i = 0; i < 5; i++) {
            WebElement textareaElement = driver.findElementByCssSelector("textarea");
            assert "Original value".equals(textareaElement.getAttribute("value"))
                    : "textarea does not have expected original value";

            textareaElement.clear();
            textareaElement.sendKeys("New value");
            assert "New value".equals(textareaElement.getAttribute("value"))
                    : "textarea does not have expected new value after typing it";

            driver.navigate().refresh();
            driver.switchTo().alert().dismiss();

            textareaElement = driver.findElementByCssSelector("textarea");
            assert "New value".equals(textareaElement.getAttribute("value"))
                    : "textarea no longer has expected value after aborting a refresh";

            driver.navigate().refresh();
            driver.switchTo().alert().accept();

            await().until(() ->
                    driver.findElementByCssSelector("textarea").getAttribute("value"), equalTo("Original value"));
        }

        Thread.sleep(5000);
        applicationContext.close();
    }

}
