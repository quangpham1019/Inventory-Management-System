package com.example.demo.IntegrationTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.time.Duration;

public class IntegrationTest {

    WebDriver driver;


    @BeforeEach
    public void setup() {
        driver = new EdgeDriver();
    }

    @Test
    public void eightComponents() {

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//        driver.get("https://www.selenium.dev/selenium/web");
//        String title = driver.getTitle();
//        assertEquals("Web form", title);
//
//        WebElement textBox = driver.findElement(By.name("my-text"));
//        WebElement submitButton = driver.findElement(By.cssSelector("button"));
//
//        textBox.sendKeys("Selenium");
//        submitButton.click();
//
//        WebElement message = driver.findElement(By.id("message"));
//        String value = message.getText();
//        assertEquals("Received!", value);

        driver.get("http://localhost:8080");
        String title = driver.getTitle();
        assertEquals("Sign In", title);

    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }
}
