package Pages;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.ElementClickInterceptedException;
import org.openqa.selenium.JavascriptExecutor;
import java.util.List;
import org.openqa.selenium.TimeoutException;

public class HomePage {
    private WebDriver driver;

    public HomePage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    /* **************************************
     *************** Locators ***************
     ***************************************/

    public static final String LOGO = "//div[@class='main__logo-wrapper']";
    public static final String LOGO_Contacts = "//img[@imgfield='tn_img_1678370260052']";
    public static final String VACANCIES_MENU = "//a[text()='Вакансии']";
    public static final String CONTACTS_LINK = "//a[text()='контакты']";
    public static final String CAREER_LINK = "//a[text()='Центр карьеры']";
    public static final String PRACTICE_LINK = "//a[text()='Практика и стажировки']";
    @FindBy(xpath = "//div[@field='tn_text_1676892134781']")
    WebElement footerElement;
    @FindBy(xpath = "//div[@class='main__header']")
    WebElement mainMenu;
    @FindBy(xpath = "//a[@href='https://www.youtube.com/channel/UClOTq6XN8g7-16QLGnZ6_EA']")
    WebElement youtube;
    @FindBy(xpath = "//a[@class='directions__list-link directions__list-link_green']//span[text()='Все курсы']")
    WebElement coursesButton;

    /* **************************************
     *************** Methods ****************
     ***************************************/

    public void pageLoading() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement mainMenu = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(LOGO)));
            if (!mainMenu.isDisplayed()) {
                throw new Exception();
            }
        }
        catch (Exception ex) {
            throw new AssertionError("The 'Main Page' was not loaded: " + ex.getMessage());
        }
    }

    public void clickCoursesMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        String originalWindow = driver.getWindowHandle();
        try {
            wait.until(ExpectedConditions.visibilityOf(coursesButton));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", coursesButton);
            coursesButton.click();
            System.out.println("Clicked on 'Все курсы'");
            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            System.out.println("Switched to the new window with courses.");
        }
        catch (ElementClickInterceptedException e) {
            System.out.println("ElementClickInterceptedException caught. Trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", coursesButton);

            wait.until(ExpectedConditions.numberOfWindowsToBe(2));

            for (String windowHandle : driver.getWindowHandles()) {
                if (!originalWindow.equals(windowHandle)) {
                    driver.switchTo().window(windowHandle);
                    break;
                }
            }
            System.out.println("Switched to the new window with courses using JavaScript click.");
        }
        catch (TimeoutException e) {
            System.out.println("Element 'Все курсы' was not clickable within the timeout period.");
        }
    }

    public void clickvacanciesMenu() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement vacanciesButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(VACANCIES_MENU)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", vacanciesButton);
            vacanciesButton.click();
        }
        catch (ElementClickInterceptedException e) {
            WebElement vacanciesButton = driver.findElement(By.xpath(VACANCIES_MENU));
            System.out.println("ElementClickInterceptedException caught. Trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", vacanciesButton);
        }
        catch (TimeoutException e) {
            System.out.println("Element 'Вакансии' was not clickable within the timeout period.");
        }
    }

    public int getVisibleCoursesCount() {
        List<WebElement> courses = driver.findElements(By.xpath("//a[contains(@class, 'tn-atom') and (contains(@href, '/courses') or contains(@href, 'https://new.skillfactory.ru/'))]"));

        int visibleCoursesCount = 0;
        for (WebElement course : courses) {
            if (course.isDisplayed()) {
                visibleCoursesCount++;
                System.out.println("Visible course found: " + course.getText());
            }
            else {
                System.out.println("Course not visible: " + course.getAttribute("href"));
            }
        }
        return visibleCoursesCount;
    }

    public String getFooterInfo() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.visibilityOf(footerElement));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", footerElement);
        return footerElement.getText();
    }

    public void clickLogoContacts() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        WebElement logo = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(LOGO_Contacts)));
        logo.click();
        System.out.println("Clicked on site logo");
    }

    public void clickContactsLink() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement contactsButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CONTACTS_LINK)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", contactsButton);
            contactsButton.click();
        }
        catch (ElementClickInterceptedException e) {
            WebElement contactsButton = driver.findElement(By.xpath(CONTACTS_LINK));
            System.out.println("ElementClickInterceptedException caught. Trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", contactsButton);
        }
        catch (TimeoutException e) {
            System.out.println("Element 'Контакты' was not clickable within the timeout period.");
        }
    }

    public void clickCareerLink() {
    WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
        WebElement careerButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(CAREER_LINK)));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", careerButton);
        careerButton.click();
    }
        catch (ElementClickInterceptedException e) {
        WebElement careerButton = driver.findElement(By.xpath(CAREER_LINK));
        System.out.println("ElementClickInterceptedException caught. Trying JavaScript click.");
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", careerButton);
    }
        catch (TimeoutException e) {
        System.out.println("Element 'Центр карьеры' was not clickable within the timeout period.");
    }
}

    public void clickPRACTICELink() {
        WebDriverWait wait = new WebDriverWait(driver, 10);
        try {
            WebElement practiceButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(PRACTICE_LINK)));
            ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", practiceButton);
            practiceButton.click();
        }
        catch (ElementClickInterceptedException e) {
            WebElement practiceButton = driver.findElement(By.xpath(PRACTICE_LINK));
            System.out.println("ElementClickInterceptedException caught. Trying JavaScript click.");
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", practiceButton);
        }
        catch (TimeoutException e) {
            System.out.println("Element 'Practice' was not clickable within the timeout period.");
        }
    }

    public void clickYoutubeLink() {
            WebDriverWait wait = new WebDriverWait(driver, 10);
            try {
                WebElement youtubeLink = wait.until(ExpectedConditions.visibilityOf(youtube));
                ((JavascriptExecutor) driver).executeScript("arguments[0].click();", youtubeLink);
                System.out.println("Clicked on the YouTube link using JavaScript.");
            }
            catch (TimeoutException e) {
                System.out.println("Element 'YouTube link' was not clickable within the timeout period.");
            }
        }

    public void switchToNewWindow(String originalWindow) {
        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }
    }

    public boolean isMainMenuVisible() {
        return mainMenu.isDisplayed();
    }
}