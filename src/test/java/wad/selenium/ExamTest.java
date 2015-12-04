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
        
        
        
        
    }
    @Test
    public void dateSearchWorking() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();
        
        WebElement startdateField = driver.findElement(By.id("startDate"));
        WebElement enddateField = driver.findElement(By.id("endDate"));
        
        startdateField.sendKeys("05.06.2016");
        enddateField.sendKeys("07.06.2016");
        
        WebElement datesearch = driver.findElement(By.id("dateSearchButton"));
        datesearch.submit();
        assertTrue(pageSource().contains("Java"));
        assertFalse(pageSource().contains("Tietorakenteet"));
        
    }
    @Test
    public void canLogin() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        
        WebElement login = driver.findElement(By.id("login"));
        login.submit();
        assertFalse(pageSource().contains("Login with Username and Password"));
    }
    @Test
    public void canAddExamWhileLoggedIn() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        
        WebElement login = driver.findElement(By.id("login"));
        login.submit();
        
        WebElement addExam = driver.findElement(By.id("addExam"));
        addExam.click();
        WebElement locationField = driver.findElement(By.name("location"));
        WebElement examDateField = driver.findElement(By.name("examDate"));
        locationField.sendKeys("A111");
        examDateField.sendKeys("07.06.2016 12:00");
        WebElement examinerField = driver.findElement(By.name("examiner"));
        examinerField.sendKeys("Arto Vihavainen");
        
        WebElement addExamOk = driver.findElement(By.id("addExamOk"));
        addExamOk.submit();
        assertTrue(pageSource().contains("07.06.2016"));
    }
    @Test
    public void canRemoveExamWhileLoggedIn() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        
        WebElement login = driver.findElement(By.id("login"));
        login.submit();
        
        WebElement delete = driver.findElement(By.id("delete"));
        delete.click();
        assertFalse(pageSource().contains("06.06.2016"));
    }
    @Test
    public void cantRemoveExamIfNotLoggedIn() {
        goTo(getUrl());
        assertFalse(pageSource().contains("Poista"));
    }
    @Test
    public void cantAddExamIfNotLoggedIn() {
        goTo(getUrl());
        assertFalse(pageSource().contains("Lisää koe"));
    }
    @Test
    public void cantAddSameCourseExamToSameDate() {
        goTo(getUrl());
        WebDriver driver = getDefaultDriver();
        
        WebElement usernameField = driver.findElement(By.name("username"));
        WebElement passwordField = driver.findElement(By.name("password"));
        usernameField.sendKeys("admin");
        passwordField.sendKeys("password");
        
        WebElement login = driver.findElement(By.id("login"));
        login.submit();
        
        WebElement addExam = driver.findElement(By.id("addExam"));
        addExam.click();
        WebElement locationField = driver.findElement(By.name("location"));
        WebElement examDateField = driver.findElement(By.name("examDate"));
        locationField.sendKeys("A111");
        examDateField.sendKeys("06.06.2016 10:00");
        WebElement examinerField = driver.findElement(By.name("examiner"));
        examinerField.sendKeys("Arto Vihavainen");
        
        WebElement addExamOk = driver.findElement(By.id("addExamOk"));
        addExamOk.submit();
        int size = driver.findElements(By.xpath("//*[text()='06.06.2016']")).size();
        assertTrue(size < 2);
    }
            
}
