package rhea.sonarqubeparser;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.time.Duration;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxBinary;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


        

public class seleniumSonar {
	
	private String username = "admin";
	private String password = "admin1";
	private String proj_key = "";
	private String proj_name = "";
	private String proj_token = "";
	
	public String getKey() {
		return this.proj_key;
	}
	
	public String getToken() {
		return this.proj_token;
	}
	
	public String getName() {
		return this.proj_name;
	}
	
	public void generateProjectKey(String project_name) {
		this.proj_name = project_name;
		Random r = new Random();
		long l = r.nextLong();
		this.proj_key = this.proj_key + Long.toString(l);
	}
	
	public boolean automateSonarTokenGeneration() {
		
		try {
				InputStream geckoStream = getClass().getClassLoader().getResourceAsStream("geckodriver");
				FileOutputStream outGeckoStream = new FileOutputStream(System.getProperty("user.dir") + "/geckodriver");
				
				    byte[] buf = new byte[2048];
				    int r;
				    while(-1 != (r = geckoStream.read(buf))) {
				        outGeckoStream.write(buf, 0, r);
				    }
							
		} catch (Exception e) {
			System.err.println("Error Extracting GeckoDriver");
		}
		
		File geckoFile = new File(System.getProperty("user.dir") +"/geckodriver");
		
		while(geckoFile.canExecute() == false) {
			try {
				TimeUnit.SECONDS.sleep(5);
			} catch (Exception e) {
				System.err.println("Error Pausing Application");
			}
		}
		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"/geckodriver");
		FirefoxBinary firefoxBinary = new FirefoxBinary();
		FirefoxOptions options = new FirefoxOptions();
		options.setBinary(firefoxBinary);
		options.setHeadless(true); 
		WebDriver driver = new FirefoxDriver(options);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(120));
		driver.get("http://sonarqube:9000");
		
		wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.id("login")), ExpectedConditions.visibilityOfElementLocated(By.id("password")), ExpectedConditions.visibilityOfElementLocated(By.className("button"))));
		
		
		int caseNumber = 0, caseNumber1 = 0, caseNumber2 = 0, caseNumber3 = 0, caseNumber4 = 0, caseNumber5 = 0, caseNumber6 = 0;

		while(caseNumber == 0){
				wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.id("login")), ExpectedConditions.visibilityOfElementLocated(By.id("password")), ExpectedConditions.visibilityOfElementLocated(By.className("button"))));
				driver.findElement(By.id("login")).clear();
				driver.findElement(By.id("password")).clear();
				driver.findElement(By.id("login")).sendKeys("admin");
				driver.findElement(By.id("password")).sendKeys("admin");
				driver.findElement(By.className("button")).click();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					System.err.println("Error Pausing Application");
				}
				wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.id("login")), ExpectedConditions.visibilityOfElementLocated(By.id("old_password"))));
            if(driver.findElements(By.id("login")).size() == 0 && driver.findElements(By.id("old_password")).size() != 0){
				caseNumber = 1;
			}
        }

        while(caseNumber1 == 0){
				driver.findElement(By.id("old_password")).clear();
				driver.findElement(By.id("password")).clear();
				driver.findElement(By.id("password_confirmation")).clear();
			  	driver.findElement(By.id("old_password")).sendKeys(username);
				driver.findElement(By.id("password")).sendKeys(password);
				driver.findElement(By.id("password_confirmation")).sendKeys(password);
				driver.findElement(By.id("change-password")).click();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					System.err.println("Error Pausing Application");
				}
                wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.id("old_password")), ExpectedConditions.visibilityOfElementLocated(By.className("create-project-mode-type-manual"))));
                if(driver.findElements(By.className("create-project-mode-type-manual")).size() != 0 && driver.findElements(By.id("old_password")).size() == 0){
                    caseNumber1 = 1;
                }

        }


        while(caseNumber2 == 0){
				driver.findElement(By.className("create-project-mode-type-manual")).click();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					System.err.println("Error Pausing Application");
				}
				wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.className("create-project-mode-type-manual")), ExpectedConditions.visibilityOfElementLocated(By.id("project-key"))));
                if(driver.findElements(By.id("project-key")).size() != 0 && driver.findElements(By.className("create-project-mode-type-manual")).size() == 0) {
						caseNumber2 = 1;
				}
        }
					
		while(caseNumber3 == 0){
                driver.findElement(By.id("project-name")).clear();
				driver.findElement(By.id("project-key")).clear();
				driver.findElement(By.id("project-name")).sendKeys(this.proj_name); 
				driver.findElement(By.id("project-key")).sendKeys(this.proj_key);
                driver.findElement(By.xpath("//button[@class=\"button\" and @aria-disabled=\"false\" and @type=\"submit\"]")).click();
                try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					System.err.println("Error Pausing Application");
				}
                wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.id("project-key")), ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"button button-huge display-flex-column big-spacer-right big-spacer-bottom tutorial-mode-manual\" and @type=\"button\"]"))));
                if(driver.findElements(By.xpath("//button[@class=\"button\" and @aria-disabled=\"false\" and @type=\"submit\"]")).size() == 0 && driver.findElements(By.xpath("//button[@class=\"button button-huge display-flex-column big-spacer-right big-spacer-bottom tutorial-mode-manual\" and @type=\"button\"]")).size() != 0){
							caseNumber3 = 1;
                }
        }				
					

		while(caseNumber4 == 0){
                driver.findElement(By.xpath("//button[@class=\"button button-huge display-flex-column big-spacer-right big-spacer-bottom tutorial-mode-manual\" and @type=\"button\"]")).click();
                try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					System.err.println("Error Pausing Application");
				}
                wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@class=\"button button-huge display-flex-column big-spacer-right big-spacer-bottom tutorial-mode-manual\" and @type=\"button\"]")), ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class=\"input-super-large spacer-right text-middle\" and @required=\"\" and @type=\"text\"]"))));
               	if(driver.findElements(By.xpath("//button[@class=\"button button-huge display-flex-column big-spacer-right big-spacer-bottom tutorial-mode-manual\" and @type=\"button\"]")).size() == 0 && driver.findElements(By.xpath("//input[@class=\"input-super-large spacer-right text-middle\" and @required=\"\" and @type=\"text\"]")).size() != 0){
							caseNumber4 = 1;
                   }
        }
						
						
		while(caseNumber5 == 0){
                driver.findElement(By.xpath("//input[@class=\"input-super-large spacer-right text-middle\" and @required=\"\" and @type=\"text\"]")).clear();
				driver.findElement(By.xpath("//input[@class=\"input-super-large spacer-right text-middle\" and @required=\"\" and @type=\"text\"]")).sendKeys(this.proj_key);
				driver.findElement(By.xpath("//button[@class=\"button text-middle\" and @type=\"submit\"]")).click();
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (Exception e) {
					System.err.println("Error Pausing Application");
				}
				wait.until(ExpectedConditions.or(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@class=\"input-super-large spacer-right text-middle\" and @required=\"\" and @type=\"text\"]")), ExpectedConditions.visibilityOfElementLocated(By.xpath("//strong[@class=\"spacer-right text-middle\"]"))));
                	if(driver.findElements(By.xpath("//input[@class=\"input-super-large spacer-right text-middle\" and @required=\"\" and @type=\"text\"]")).size() == 0 && driver.findElements(By.xpath("//strong[@class=\"spacer-right text-middle\"]")).size() != 0){
							caseNumber5 = 1;
					}
        }				
					
        while(caseNumber6 == 0){
        	try {
				TimeUnit.SECONDS.sleep(10);
			} catch (Exception e) {
				System.err.println("Error Pausing Application");
			}
        this.proj_token = driver.findElement(By.xpath("//strong[@class=\"spacer-right text-middle\"]")).getText();
        if(!this.proj_token.equals("")){
            caseNumber6 = 1;
        }
        }
		
        driver.quit();
		return true;
            
	

		}
		
		
    }

	
	
