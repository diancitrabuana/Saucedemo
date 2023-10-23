package saucedemo;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvValidationException;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileReader;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class LoginDDT {
    //login menggunakan fitur Data Drive test (DDT)
    @Test
    public void login_ddt(){
        WebDriver driver;
        String baseUrl = "https://www.saucedemo.com";

        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions opt = new FirefoxOptions();
        opt.setHeadless(false);

        String csvDir = System.getProperty("user.dir") + "/src/test/data/test-data.csv";

        try(CSVReader reader = new CSVReader(new FileReader(csvDir))) {
            String[] nextLine;
            while((nextLine = reader.readNext()) != null){
                String username = nextLine[0]; //read column 1 for username
                String password = nextLine[1]; //read column 2 for password
                String status = nextLine[2]; //read column 3 for expected login status

                driver = new FirefoxDriver(opt);
                driver.manage().timeouts().implicitlyWait(60,TimeUnit.SECONDS); //set timeout for web driver on waiting element
                driver.manage().window().maximize(); //maximize window

                //interaksi element
                driver.findElement(By.id("user-name")).sendKeys(username);
                driver.findElement(By.id("password")).sendKeys(password);
                driver.findElement(By.id("login-button")).click();

                //Assertion
                if(status.equals("success")){ //jika success
                    String hasil = driver.findElement(By.xpath("//span[contains(text(),'Products')]")).getText();
                    Assert.assertEquals(hasil, "Products");
                }else { //jika failed
                    String ErrorLogin = driver.findElement(By.xpath("//h3[contains(text(),'Epic sadface: Username and password do not match any user in this service')]")).getText();
                    Assert.assertEquals(ErrorLogin,"Epic sadface: Username and password do not match any user in this service");

                }
                driver.close();
            }
        }catch (IOException e){
            throw new RuntimeException(e);
        }catch (CsvValidationException e){
            e.printStackTrace();
        }
    }
}
