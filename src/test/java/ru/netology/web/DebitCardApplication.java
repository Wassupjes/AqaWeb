package ru.netology.web;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;


import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;


class DebitCardApplication {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldDebitCardApplicationPositive() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name]")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone]")).sendKeys("+79888888888");
        driver.findElement(By.className("checkbox__control")).click();
        driver.findElement(By.className("button")).click();
        String text = driver.findElement(By.className("Success")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время", text.trim());
        Thread.sleep(50000);
    }
    }

