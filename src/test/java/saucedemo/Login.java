package saucedemo;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class Login {
    @Test
    public void success_login_case() {
        WebDriver driver;
        String baseUrl = "https://www.saucedemo.com";

        WebDriverManager.firefoxdriver().setup();

        //apply chrome driver setup
        //membuka halaman login
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);

        //assert masuk halaman login
//        String loginPageAssert = driver.findElement(By.xpath("//span[contains(text(),'Products')]")).getText();
        String loginPageAssert = driver.findElement(By.cssSelector(".login_logo")).getText();
        Assert.assertEquals(loginPageAssert,"Swag Labs");
        //input email
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        //input password
        driver.findElement(By.id("password")).sendKeys("secret_sauce");
        //click login
        driver.findElement(By.id("login-button")).click();

        driver.close();
    }

    @Test
    public void failed_login_case(){
        WebDriver driver;
        String baseUrl = "https://www.saucedemo.com";

        WebDriverManager.firefoxdriver().setup();

        //apply chrome driver setup
        //membuka halaman login
        driver = new FirefoxDriver();
        driver.manage().window().maximize();
        driver.get(baseUrl);
        //assert masuk halaman login
//        String loginPageAssert = driver.findElement(By.xpath("//span[contains(text(),'Products')]")).getText();
        String loginPageAssert = driver.findElement(By.cssSelector(".login_logo")).getText();
        Assert.assertEquals(loginPageAssert,"Swag Labs");
        //input email
        driver.findElement(By.id("user-name")).sendKeys("standard_user");
        //input password yang salah
        driver.findElement(By.id("password")).sendKeys("secret_saos");
        //click login
        driver.findElement(By.id("login-button")).click();

        //String ErrorLogin = driver.findElement(By.cssSelector(".error-message-container error")).getText();
        String ErrorLogin = driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]")).getText();
        Assert.assertEquals(ErrorLogin,"Epic sadface: Username and password do not match any user in this service");
        driver.close();
    }
}
