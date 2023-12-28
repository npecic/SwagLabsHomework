import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SwagLabs {
    WebDriver driver;

    @BeforeMethod
    public void setUp(){
        System.setProperty("webdriver.chrome.driver","src/main/resources/chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterMethod
    public void tearDown (){
        driver.quit();
    }

    @Test
    @Parameters({"username", "password", "elementMessage", "expectedResult", "elementError", "expectedResultError"})
    public void automationTest(
            @Optional String username, @Optional String password,
            @Optional String elementMessage, @Optional String expectedResult,
            @Optional String elementError, @Optional String expectedResultError
    ) {
        driver.get("https://www.saucedemo.com/v1/");

        // Login
        driver.findElement(By.id("user-name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);
        driver.findElement(By.cssSelector("#login-button")).click();

        // Positive Test: Verify successful login
        if (elementMessage != null && expectedResult != null) {
            Assert.assertEquals(driver.findElement(By.xpath(elementMessage)).getText(), expectedResult);
        }

        // Negative Test: Verify error message for invalid login
        if (elementError != null && expectedResultError != null) {
            Assert.assertEquals(driver.findElement(By.xpath(elementError)).getText(), expectedResultError);
        }

        // Additional UI Interaction Test Case: Click on a product after login
        if (elementMessage != null && expectedResult != null) {
            Actions actions = new Actions(driver);
            actions.moveToElement(driver.findElement(By.xpath(elementMessage))).click().perform();
            // Add assertions or further interactions as needed
        }

        // Logout Test Case: Log out after successful login
        if (elementMessage != null && expectedResult != null) {
            // Perform actions after successful login
            // ...

            // Logout
            // Example: Click on the logout button
            driver.findElement(By.id("logout-button")).click();
            // Add assertions for successful logout
        }
    }

    @Test
    @Parameters({"validUsername", "validPassword", "successElement"})
    public void positiveLoginTest(
            @Optional String validUsername, @Optional String validPassword,
            @Optional String successElement
    ) {
        driver.get("https://www.saucedemo.com/v1/");

        // Positive Test: Log in with valid credentials
        driver.findElement(By.id("user-name")).sendKeys(validUsername);
        driver.findElement(By.id("password")).sendKeys(validPassword);
        driver.findElement(By.cssSelector("#login-button")).click();

        // Verify successful login
        if (successElement != null) {
            Assert.assertEquals(driver.findElement(By.xpath(successElement)).getText(), "Products");
        }
    }

    @Test
    @Parameters({"invalidUsername", "invalidPassword", "errorElement", "expectedErrorMessage"})
    public void negativeLoginTest(
            @Optional String invalidUsername, @Optional String invalidPassword,
            @Optional String errorElement, @Optional String expectedErrorMessage
    ) {
        driver.get("https://www.saucedemo.com/v1/");

        // Negative Test: Log in with invalid credentials
        driver.findElement(By.id("user-name")).sendKeys(invalidUsername);
        driver.findElement(By.id("password")).sendKeys(invalidPassword);
        driver.findElement(By.cssSelector("#login-button")).click();

        // Verify error message for invalid login
        if (errorElement != null && expectedErrorMessage != null) {
            Assert.assertEquals(driver.findElement(By.xpath(errorElement)).getText(), expectedErrorMessage);
        }
    }

    // Additional Test Cases:

    @Test
    @Parameters({"timeoutUsername", "timeoutPassword", "elementMessage"})
    public void timeoutTest(
            @Optional String timeoutUsername, @Optional String timeoutPassword,
            @Optional String elementMessage
    ) {
        // Test scenario: Verify the behavior when the login process takes longer than the specified implicit wait time
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        driver.get("https://www.saucedemo.com/v1/");
        driver.findElement(By.id("user-name")).sendKeys(timeoutUsername);
        driver.findElement(By.id("password")).sendKeys(timeoutPassword);
        driver.findElement(By.cssSelector("#login-button")).click();

        // Verify element after login
        if (elementMessage != null) {
            Assert.assertEquals(driver.findElement(By.xpath(elementMessage)).getText(), "Products");
        }
    }
}
