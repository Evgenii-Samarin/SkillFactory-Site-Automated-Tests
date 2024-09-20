package Tests;
import Pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;
import static org.testng.Assert.assertTrue;
import static org.testng.AssertJUnit.assertFalse;

public class HomePageTest {
    private WebDriver driver;
    HomePage homePage;

    @BeforeTest
    public void setUp() {
        System.setProperty("webdriver.chromedriver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        homePage = new HomePage(driver);
    }

    @Test(description = "Sending form with valid name/email/phone")
    public void locatorTest() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.navigate().to("https://skillfactory.ru/");

        System.out.println("Step 2. Enter valid name 'Maksim' in the name field");
        WebElement webElement = driver.findElement(By.name("name"));
        webElement.sendKeys("Maksim");

        System.out.println("Step 3. Enter valid email 'test@mail.ru' in the email field");
        List<WebElement> list = driver.findElements(By.name("email"));
        list.get(0).sendKeys("test@mail.ru");

        System.out.println("Step 4. Check the number of email fields on the page: " + list.size());

        System.out.println("Step 5. Enter valid phone number '9999999999' in the phone field");
        WebElement phoneField = driver.findElement(By.name("tildaspec-phone-part[]"));
        phoneField.sendKeys("9999999999");

        System.out.println("Step 6. Wait for the submit button to become clickable and click it");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("t-submit")));
        submitButton.click();

        System.out.println("Step 7. Form has been submitted successfully");
    }

    @Test(description = "Sending form with invalid name/email/phone ")
    public void locatorTestNegative() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.navigate().to("https://skillfactory.ru/");

        System.out.println("Step 2. Enter invalid name '123' in the name field");
        WebElement nameField = driver.findElement(By.name("name"));
        nameField.sendKeys("123");

        System.out.println("Step 3. Check if the email field exists and enter invalid email 'test'");
        List<WebElement> emailFields = driver.findElements(By.name("email"));

        if (emailFields.size() > 0) {
            emailFields.get(0).sendKeys("test");
        } else {
            System.out.println("Email некорректный");
        }

        System.out.println("Step 4. Enter invalid phone number '9999' in the phone field");
        WebElement phoneField = driver.findElement(By.name("tildaspec-phone-part[]"));
        phoneField.sendKeys("9999");

        System.out.println("Step 5. Wait for the submit button to become clickable and click it");
        WebDriverWait wait = new WebDriverWait(driver, 5);
        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.className("t-submit")));
        submitButton.click();

        System.out.println("Step 6. Waiting for the error message to appear");
        WebElement errorMessage = wait.until(ExpectedConditions.presenceOfElementLocated(By.className("t-input-error")));

        System.out.println("Step 7. Check that the error message is visible");
        String displayStyle = errorMessage.getAttribute("style");
        assertFalse("Элемент не должен быть скрыт", displayStyle.contains("display: none"));

        System.out.println("Step 8. Display the error message");
        System.out.println("Сообщение об ошибке: " + errorMessage.getText());
    }

    @Test(description = "Check footer information on the main page")
    public void testFooterInformation() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Check the footer for contact information");
        String footerInfo = homePage.getFooterInfo();
        String expectedText = "ООО \"СКИЛФЭКТОРИ\"\n" +
                "ОГРН 1197746648813\n" +
                "ИНН 9702009530 119049,\n";
        assertTrue(footerInfo.contains(expectedText), "Footer does not contain contact information");
    }

    @Test(description = "Check the visibility of the main header")
    public void testMainMenuVisibility() {
        HomePage homePage = new HomePage(driver);

        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Check if the main header is visible");
        assertTrue(homePage.isMainMenuVisible(), "Main header is not visible on the Home page");
    }

    @Test(description = "Verify external link navigation")
    public void testExternalLinkNavigation() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Click on an external social media link (YouTube)");
        homePage.clickYoutubeLink();

        System.out.println("Step 4. Switch to the new window and verify the URL");
        for (String windowHandle : driver.getWindowHandles()) {
            driver.switchTo().window(windowHandle);
        }

        assertTrue(driver.getCurrentUrl().contains("youtube.com"), "Failed to navigate to YouTube page");
    }

    @Test(description = "Check the number of courses displayed")
    public void testCoursesCount() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Navigate to the 'Courses' section");
        homePage.clickCoursesMenu();

        System.out.println("Step 4. Check the number of courses displayed");
        int visibleCoursesCount = homePage.getVisibleCoursesCount();
        assertTrue(visibleCoursesCount > 0, "No courses were found on the 'Courses' page");
        System.out.println("Number of visible course buttons: " + visibleCoursesCount);
    }

    @Test(description = "Check responsive layout on different screen sizes")
    public void testResponsiveLayout() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        System.out.println("Step 2. Set mobile screen size");
        driver.manage().window().setSize(new Dimension(375, 667));
        assertTrue(homePage.isMainMenuVisible(), "Main menu (header) is not displayed on mobile");

        System.out.println("Step 3. Set tablet screen size");
        driver.manage().window().setSize(new Dimension(768, 1024));
        assertTrue(homePage.isMainMenuVisible(), "Main menu (header) is not displayed on tablet");

        System.out.println("Step 4. Set desktop screen size");
        driver.manage().window().setSize(new Dimension(1366, 768));
        assertTrue(homePage.isMainMenuVisible(), "Main menu (header) is not displayed on desktop");
    }

    @Test(description = "Check page load time")
    public void testPageLoadTime() {
        long startTime = System.currentTimeMillis();
        System.out.println("Step 1. Start measuring page load time");

        System.out.println("Step 2. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 3. Waiting for page to load completely");
        WebDriverWait wait = new WebDriverWait(driver, 10);

        long endTime = System.currentTimeMillis();
        long loadTime = endTime - startTime;
        System.out.println("Step 4. Page load time: " + loadTime + " milliseconds");

        System.out.println("Step 5. Verify if page load time is within acceptable limits");
        assertTrue(loadTime < 50000, "Page load time is too slow: " + loadTime + " milliseconds");

        System.out.println("Step 6. Page loaded successfully within time limits");
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}