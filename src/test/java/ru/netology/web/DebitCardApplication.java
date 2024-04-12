package ru.netology.web;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import static org.junit.jupiter.api.Assertions.assertEquals;


class DebitCardApplication {
    private WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
    driver = new ChromeDriver();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldDebitCardApplicationPositive() throws InterruptedException {
        driver.get("http://localhost:7777");
        WebElement name = driver.findElement(By.cssSelector("[data-test-id=name]"));
        WebElement phone = driver.findElement(By.cssSelector("[data-test-id=phone]"));
        WebElement buttonAgreement = driver.findElement(By.cssSelector("[data-test-id=agreement]"));
        WebElement buttonRole = driver.findElement(By.cssSelector("[role=button]"));
        name.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        phone.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        buttonAgreement.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        buttonRole.findElement(By.cssSelector("[role=button]")).click();
        String text = driver.findElement(By.className("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время", text.trim());
        Thread.sleep(5000);
    }
    }

