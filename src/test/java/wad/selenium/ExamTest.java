package wad.selenium;

import java.util.concurrent.TimeUnit;
import static org.apache.catalina.security.SecurityUtil.remove;
import org.fluentlenium.adapter.FluentTest;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import wad.Application;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@IntegrationTest("server.port:0")
public class ExamTest extends FluentTest {

    @Value("${local.server.port}")
    private int serverPort;
    private WebElement element;
    private WebDriver webDriver = new HtmlUnitDriver();

    private String getUrl() {
        return "http://localhost:" + serverPort;
    }

    @Override
    public WebDriver getDefaultDriver() {
        return webDriver;
    }

    @Test
    public void listsAllExams() {
        goTo(getUrl());

        assertTrue(pageSource().contains("Tietorakenteet"));
        assertTrue(pageSource().contains("Java"));

    }

    @Test
    public void showsInformationOfSelectedExam() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();

        assertTrue(pageSource().contains("Tietorakenteet"));
        assertTrue(pageSource().contains("Java"));
        driver.findElement(By.linkText("Tiedot")).click();

        assertTrue(pageSource().contains("Arto"));

    }

    @Test
    public void nameSearchWorking() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();
        
        assertTrue(pageSource().contains("Tietorakenteet"));
        assertTrue(pageSource().contains("Java"));
        
        element = driver.findElement(By.id("search"));
        element.sendKeys("Java");
        element.submit();
        
        
        assertTrue(pageSource().contains("Java"));
        assertFalse(pageSource().contains("Tietorakenteet"));
        
        
        
    }
}
