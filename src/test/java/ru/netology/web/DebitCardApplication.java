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
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
        driver = null;
    }

    @Test
    void shouldDebitCardApplicationPositive() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText().trim();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", actualText);
    }

    @Test
        // ввод латинских букв в поле фамилия и имя
    void enteringLatinLetters() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Vladimirov Vasiliy");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
    }

    @Test
    void emptyLastNameAndFirstNameField() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
    // ввод спецсимволов в поле фамилия и имя
    void enteringSpecialCharacters() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров/ Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
    }


    @Test
        // ввод имени с буквой ё в поле фамилия и имя
    void invalidLetter() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Семён");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=name] .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", actualText);
    }

    @Test
    // ввод 11значного номера без +
    void invalidPhoneEntryFirst() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    @Test
        // ввод 12значного номера
    void invalidPhoneEntrySecond() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+798888888888");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    @Test
        // ввод букв в поле номера
    void invalidPhoneEntryThird() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+798888888аа");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", actualText);
    }

    @Test
        // пустое поле номера
    void invalidPhoneEntryFourth() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=phone] .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", actualText);
    }

    @Test
        // не проставлена галочка напротив строки Согласия на обработку данных
    void submittingFormWithoutConsent() throws InterruptedException {
        driver.get("http://localhost:7777");
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Владимиров Василий");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79888888888");
        driver.findElement(By.className("button")).click();
        String actualText = driver.findElement(By.cssSelector("[data-test-id=agreement] .checkbox__text")).getText();
        assertEquals("Я соглашаюсь с условиями обработки и использования моих персональных данных и разрешаю сделать запрос в бюро кредитных историй", actualText);
    }
}