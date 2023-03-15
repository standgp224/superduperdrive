package com.udacity.jwdnd.course1.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.io.File;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

	public static final String[] username = {"timsss", "allensss", "pagesss", "dukesss", "annasss", "willsss", "kensss", "johnsss", "wonsss", "evesss", "sunsss"};

	@LocalServerPort
	private int port;

	private WebDriver driver;

	@BeforeAll
	static void beforeAll() {
		WebDriverManager.chromedriver().setup();
	}

	@BeforeEach
	public void beforeEach() {
		this.driver = new ChromeDriver();
	}

	@AfterEach
	public void afterEach() {
		if (this.driver != null) {
			driver.quit();
		}
	}

	@Test
	public void getLoginPage() {
		driver.get("http://localhost:" + this.port + "/login");
		Assertions.assertEquals("Login", driver.getTitle());
	}

	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doMockSignUp(String firstName, String lastName, String userName, String password){
		// Create a dummy account for logging in later.
		// Visit the sign-up page.
		WebDriverWait webDriverWait = new WebDriverWait(driver, 5);
		driver.get("http://localhost:" + this.port + "/signup");
		webDriverWait.until(ExpectedConditions.titleContains("Sign Up"));
		
		// Fill out credentials
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputFirstName")));
		WebElement inputFirstName = driver.findElement(By.id("inputFirstName"));
		inputFirstName.click();
		inputFirstName.sendKeys(firstName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputLastName")));
		WebElement inputLastName = driver.findElement(By.id("inputLastName"));
		inputLastName.click();
		inputLastName.sendKeys(lastName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement inputUsername = driver.findElement(By.id("inputUsername"));
		inputUsername.click();
		inputUsername.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement inputPassword = driver.findElement(By.id("inputPassword"));
		inputPassword.click();
		inputPassword.sendKeys(password);

		// Attempt to sign up.
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("buttonSignUp")));
		WebElement buttonSignUp = driver.findElement(By.id("buttonSignUp"));
		buttonSignUp.click();

		/* Check that the sign-up was successful.
		// You may have to modify the element "success-msg" and the sign-up 
		// success message below depending on the rest of your code.
		*/
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
		Assertions.assertTrue(driver.findElement(By.id("success-msg")).getText().contains("You successfully signed up!"));
	}

	
	
	/**
	 * PLEASE DO NOT DELETE THIS method.
	 * Helper method for Udacity-supplied sanity checks.
	 **/
	private void doLogIn(String userName, String password)
	{
		// Log in to our dummy account.
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys(userName);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys(password);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));

	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling redirecting users 
	 * back to the login page after a successful sign up.
	 * Read more about the requirement in the rubric: 
	 * https://review.udacity.com/#!/rubrics/2724/view 
	 */
	@Test
	public void testRedirection() {
		// Create a test account
		doMockSignUp("sfdasdffds","Test",username[0], "123");
		// Check if we have been redirected to the log in page.
		Assertions.assertEquals("http://localhost:" + this.port + "/login", driver.getCurrentUrl());
	}

	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling bad URLs 
	 * gracefully, for example with a custom error page.
	 * 
	 * Read more about custom error pages at: 
	 * https://attacomsian.com/blog/spring-boot-custom-error-page#displaying-custom-error-page
	 */
	@Test
	public void testBadUrl() {
		// Create a test account
		doMockSignUp("URL","Test",username[1], "123");
		doLogIn(username[1], "123");
		
		// Try to access a random made-up URL.
		driver.get("http://localhost:" + this.port + "/some-random-page");
		Assertions.assertFalse(driver.getPageSource().contains("Whitelabel Error Page"));
	}


	/**
	 * PLEASE DO NOT DELETE THIS TEST. You may modify this test to work with the 
	 * rest of your code. 
	 * This test is provided by Udacity to perform some basic sanity testing of 
	 * your code to ensure that it meets certain rubric criteria. 
	 * 
	 * If this test is failing, please ensure that you are handling uploading large files (>1MB),
	 * gracefully in your code. 
	 * 
	 * Read more about file size limits here: 
	 * https://spring.io/guides/gs/uploading-files/ under the "Tuning File Upload Limits" section.
	 */
	@Test
	public void testLargeUpload() {
		// Create a test account
		doMockSignUp("Large File","Test",username[2], "123");
		doLogIn(username[2], "123");

		// Try to upload an arbitrary large file
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		String fileName = "VisualVM_215.dmg";

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("fileUpload")));
		WebElement fileSelectButton = driver.findElement(By.id("fileUpload"));
		fileSelectButton.sendKeys(new File(fileName).getAbsolutePath());

		WebElement uploadButton = driver.findElement(By.id("uploadButton"));
		uploadButton.click();
		try {
			webDriverWait.until(ExpectedConditions.presenceOfElementLocated(By.id("success")));
		} catch (org.openqa.selenium.TimeoutException e) {
			System.out.println("Large File upload failed");
		}
		Assertions.assertFalse(driver.getPageSource().contains("HTTP Status 403 – Forbidden"));

	}

	@Test
	public void testUnauthorizedLogin() {
		driver.get("http://localhost:" + this.port + "/login");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputUsername")));
		WebElement loginUserName = driver.findElement(By.id("inputUsername"));
		loginUserName.click();
		loginUserName.sendKeys("RTSS");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("inputPassword")));
		WebElement loginPassword = driver.findElement(By.id("inputPassword"));
		loginPassword.click();
		loginPassword.sendKeys("123");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("login-button")));
		WebElement loginButton = driver.findElement(By.id("login-button"));
		loginButton.click();
		Assertions.assertTrue(driver.getPageSource().contains("Invalid username or password"));
	}

	@Test
	public void testLoginAndLogout() {
		doMockSignUp("login&out","Test",username[3], "123");
		doLogIn(username[3], "123");
		Assertions.assertEquals("Home", driver.getTitle());

		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("logout-button")));
		WebElement logoutButton = driver.findElement(By.id("logout-button"));
		logoutButton.click();

		Assertions.assertNotEquals("Home", driver.getTitle());
	}

	@Test
	public void testNote() {
		createNewNote(username[4]);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertTrue(driver.getPageSource().contains("hello"));
		Assertions.assertTrue(driver.getPageSource().contains("1"));

	}

	public void createNewNote(String name) {
		doMockSignUp("note","Test",name,"123");
		doLogIn(name, "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-notes-tab")));
		WebElement noteTab = driver.findElement(By.id("nav-notes-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("addNewNote")));
		WebElement addNewNoteButton = driver.findElement(By.id("addNewNote"));
		addNewNoteButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement addNoteTitle = driver.findElement(By.id("note-title"));
		addNoteTitle.click();
		addNoteTitle.sendKeys("1");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement addNoteDescription = driver.findElement(By.id("note-description"));
		addNoteDescription.click();
		addNoteDescription.sendKeys("hello");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-save-changes")));
		WebElement noteSaveChangesButton = driver.findElement(By.id("note-save-changes"));
		noteSaveChangesButton.click();
	}

	@Test
	public void testNoteEdit() {
		createNewNote(username[5]);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-edit")));
		WebElement noteEditButton = driver.findElement(By.id("note-edit"));
		noteEditButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-title")));
		WebElement addNoteTitle = driver.findElement(By.id("note-title"));
		addNoteTitle.click();
		addNoteTitle.sendKeys("2");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-description")));
		WebElement addNoteDescription = driver.findElement(By.id("note-description"));
		addNoteDescription.click();
		addNoteDescription.sendKeys("boys");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-save-changes")));
		WebElement noteSaveChangesButton = driver.findElement(By.id("note-save-changes"));
		noteSaveChangesButton.click();

		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertTrue(driver.getPageSource().contains("helloboys"));
		Assertions.assertTrue(driver.getPageSource().contains("12"));

	}

	@Test
	public void testDeleteNote() throws InterruptedException {
		createNewNote(username[6]);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Thread.sleep(2000);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note-delete")));
		WebElement NoteDelete = driver.findElement(By.id("note-delete"));
		NoteDelete.click();
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		try {
			WebElement element = driver.findElement(By.id("note-title-onPage"));
			// 如果元素存在，抛出异常并断言测试失败
			Assertions.fail("Element still exists");
		} catch (NoSuchElementException e) {
			// 如果元素不存在，断言测试通过
			Assertions.assertTrue(true);
		}

	}

	@Test
	public void testCredentials() throws InterruptedException {
		createCredentials(username[7]);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Assertions.assertTrue(driver.getPageSource().contains("www.google.com"));
		Assertions.assertTrue(driver.getPageSource().contains("tom"));
		Thread.sleep(2000);
		Assertions.assertFalse(driver.findElement(By.id("encrypted-password")).getText().equals(driver.findElement(By.id("credential-password")).getText()));
	}

	public void createCredentials(String name) {
		doMockSignUp("credentials","Test", name, "123");
		doLogIn(name, "123");
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("nav-credentials-tab")));
		WebElement noteTab = driver.findElement(By.id("nav-credentials-tab"));
		noteTab.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("add-new-credentials")));
		WebElement addNewCredentialsButton = driver.findElement(By.id("add-new-credentials"));
		addNewCredentialsButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement addCredentialsUrl = driver.findElement(By.id("credential-url"));
		addCredentialsUrl.click();
		addCredentialsUrl.sendKeys("www.google.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement addCredentialUsername = driver.findElement(By.id("credential-username"));
		addCredentialUsername.click();
		addCredentialUsername.sendKeys("tom");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement addCredentialPassword = driver.findElement(By.id("credential-password"));
		addCredentialPassword.click();
		addCredentialPassword.sendKeys("123");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-save-changes")));
		WebElement credentialsSaveChangesButton = driver.findElement(By.id("credentials-save-changes"));
		credentialsSaveChangesButton.click();
	}

	@Test
	public void testCredentialsEdit() {
		createCredentials(username[8]);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-edit")));
		WebElement editCredentialsButton = driver.findElement(By.id("credentials-edit"));
		editCredentialsButton.click();

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-url")));
		WebElement addCredentialsUrl = driver.findElement(By.id("credential-url"));
		addCredentialsUrl.click();
		addCredentialsUrl.sendKeys("www.baidu.com");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-username")));
		WebElement addCredentialUsername = driver.findElement(By.id("credential-username"));
		addCredentialUsername.click();
		addCredentialUsername.sendKeys("jerry");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-password")));
		WebElement addCredentialPassword = driver.findElement(By.id("credential-password"));
		addCredentialPassword.click();
		addCredentialPassword.sendKeys("333");

		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credentials-save-changes")));
		WebElement credentialsSaveChangesButton = driver.findElement(By.id("credentials-save-changes"));
		credentialsSaveChangesButton.click();

		Assertions.assertTrue(driver.getPageSource().contains("www.baidu.com"));
		Assertions.assertTrue(driver.getPageSource().contains("jerry"));
		Assertions.assertTrue(driver.getPageSource().contains("333"));

	}

	@Test
	public void testCredentialDelete() throws InterruptedException {
		createCredentials(username[9]);
		WebDriverWait webDriverWait = new WebDriverWait(driver, 2);
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		Thread.sleep(2000);
		webDriverWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("credential-delete")));
		WebElement CredentialDelete = driver.findElement(By.id("credential-delete"));
		CredentialDelete.click();
		webDriverWait.until(ExpectedConditions.titleContains("Home"));
		try {
			WebElement element = driver.findElement(By.id("encrypted-password"));
			// 如果元素存在，抛出异常并断言测试失败
			Assertions.fail("Element still exists");
		} catch (NoSuchElementException e) {
			// 如果元素不存在，断言测试通过
			Assertions.assertTrue(true);
		}

	}



}
