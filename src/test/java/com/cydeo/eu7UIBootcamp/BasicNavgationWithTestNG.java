package com.cydeo.eu7UIBootcamp;



import com.cydeo.utilities.ConfigurationReader;
import com.cydeo.utilities.WebDriverFactory;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
public class BasicNavgationWithTestNG {

        WebDriver driver;
        @BeforeMethod
        public void setUp(){
            driver = WebDriverFactory.getDriver(ConfigurationReader.get("browser"));
            driver.get(ConfigurationReader.get("webTable.url"));
        }
        @AfterMethod
        public void tearDown(){
            driver.close();
        }
        @Test
        public void Test1(){


            // there are some other methods that we can use that are not covered during the class
            System.out.println(driver.findElement(By.name("username")).getLocation());// (128, 285) -- x and y coordinates of the webelement in the page
            // size of the webElement
            System.out.println(driver.findElement(By.name("username")).getSize());

            // to send some value to the webPage we used sendKeys
            driver.findElement(By.name("username")).sendKeys(ConfigurationReader.get("web.table.username"));
            // send password
            driver.findElement(By.name("password")).sendKeys(ConfigurationReader.get("web.table.pw"));

            // using text with xpath
            driver.findElement(By.xpath("//button[.='Login']")).click();

            String expectedUrl = "https://web-table-2.cydeo.com/orders";
            String actualUrl = driver.getCurrentUrl();

            Assert.assertEquals(actualUrl,expectedUrl,"URL is not as expected");
    }
}
