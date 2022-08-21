import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AmazonProducts {

	public static void main(String[] args) {
		System.setProperty("webdriver.chrome.driver", "chromedriver");
		
		WebDriver driver = new ChromeDriver();
		
		// Go to www.amazon.in
		driver.get("https://www.amazon.in/");
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30000,  TimeUnit.MILLISECONDS);

		// Search for 'Samsung' products
		WebElement searchLine = driver.findElement(By.id("twotabsearchtextbox"));
		WebElement searchButton = driver.findElement(By.id("nav-search-submit-button"));

		searchLine.sendKeys("Samsung");
		searchButton.click();
		
		List<WebElement> productList = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//h2/a"));
		List<WebElement> productPrice = driver.findElements(By.xpath("//div[@data-component-type='s-search-result']//span[@class='a-price']"));				
		
		// Print total number of products found
		System.out.println("Total number of products found: " + productList.size());
		
		// Iterate over products and print with their prices
		for(WebElement product:productList) {
			int index = productList.indexOf(product);
			System.out.println("Product: " + product.getText() + "\nProduct Price: " + productPrice.get(index).getText());
		}

		String parentWin = driver.getWindowHandle();
		String expectedValue = productList.get(0).getText();

		// Click on first product in list
		productList.get(0).click();
		
		// Switch to tab
		Set<String> allWins = driver.getWindowHandles();
		System.out.println("Parent window handle: " + parentWin);
		for(String win: allWins) {
			if(!win.equals(parentWin)) {
				System.out.println("Child window handle: " + win);
				driver.switchTo().window(win);
			}
		}
		
		// Get product title
		WebElement productTitle = driver.findElement(By.id("productTitle"));
		String actualValue = productTitle.getText();
		
		// Validate product title the same on PDP
		if(actualValue.equals(expectedValue)) {
			System.out.println("test case result: PASS");
		}
		else {
			System.out.println("test case result: FAIL");
		}
		
		// Close all windows
		driver.quit();
	}
}
