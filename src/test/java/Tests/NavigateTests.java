package Tests;
import Pages.HomePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import java.util.concurrent.TimeUnit;

public class NavigateTests {
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

    @Test(description = "Navigate back to the Home page from the 'Contacts' section")
    public void testNavigateBackToHomePageFromContacts() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Navigate to the 'CONTACTS' section");
        homePage.clickContactsLink();

        String originalWindow = driver.getWindowHandle();

        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        homePage.switchToNewWindow(originalWindow);
        wait.until(ExpectedConditions.urlContains("/contacts"));

        System.out.println("Step 4. Confirm that the 'Contacts' page is opened");
        WebElement pageTitleElement = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[contains(text(), 'Контактная информация')]")));
        String pageTitle = pageTitleElement.getText();

        System.out.println("Page Title: " + pageTitle);
        Assert.assertEquals(pageTitle, "Контактная информация", "The 'Contacts' page did not open correctly");

        System.out.println("Step 5. Click on the site logo to return to the Home page from 'Contacts'");
        homePage.clickLogoContacts();
        wait.until(ExpectedConditions.urlToBe("https://skillfactory.ru/"));

        System.out.println("Step 6. Confirm that the Home page is loaded in the same window");
        Assert.assertTrue(driver.getCurrentUrl().equals("https://skillfactory.ru/"), "Failed to navigate back to the Home page from 'Contacts'");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test(description = "Navigate to the 'Career' page")
    public void testNavigateToCareerPage() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Click on the 'Вакансии' menu");
        homePage.clickCareerLink();

        String originalWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        homePage.switchToNewWindow(originalWindow);

        System.out.println("Step 4. Wait for the 'Центр Карьеры' page to load");
        wait.until(ExpectedConditions.urlContains("https://skillfactory.ru/career-center"));

        System.out.println("Step 5. Confirm that the 'Центр Карьеры' page is opened");
        Assert.assertTrue(driver.getCurrentUrl().contains("https://skillfactory.ru/career-center"), "Failed to navigate to the Центр карьеры page");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test(description = "Navigate to the 'Practice and internships' page")
    public void testNavigateToPracticePage() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Click on the 'Practice' menu");
        homePage.clickPRACTICELink();

        String originalWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        wait.until(ExpectedConditions.numberOfWindowsToBe(2));
        homePage.switchToNewWindow(originalWindow);

        System.out.println("Step 4. Wait for the 'Practice' page to load");
        wait.until(ExpectedConditions.urlContains("https://skillfactory.ru/factory"));

        System.out.println("Step 5. Confirm that the 'Practice' page is opened");
        Assert.assertTrue(driver.getCurrentUrl().contains("https://skillfactory.ru/factory"), "Failed to navigate to the Practice page");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @Test(description = "Navigate to the 'Vacancies' section in footer")
    public void testNavigateToVacanciesSection() {
        System.out.println("Step 1. Open 'SkillFactory' Home Page");
        driver.get("https://skillfactory.ru/");

        System.out.println("Step 2. Confirm that the main page is loaded");
        homePage.pageLoading();

        System.out.println("Step 3. Click on the 'Вакансии' menu");
        homePage.clickvacanciesMenu();

        String originalWindow = driver.getWindowHandle();
        WebDriverWait wait = new WebDriverWait(driver, 5);
        homePage.switchToNewWindow(originalWindow);

        System.out.println("Step 4. Wait for the 'Vacancies' page to load");
        wait.until(ExpectedConditions.urlContains("https://new.skillfactory.ru/career"));

        System.out.println("Step 5. Confirm that the 'Vacancies' page is opened");
        Assert.assertTrue(driver.getCurrentUrl().contains("https://new.skillfactory.ru/career"), "Failed to navigate to the vacancies page");

        driver.close();
        driver.switchTo().window(originalWindow);
    }

    @AfterTest
    public void tearDown() {
        driver.quit();
    }
}