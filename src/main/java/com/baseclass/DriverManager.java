package com.baseclass;



import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;


import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import io.github.bonigarcia.wdm.WebDriverManager;

import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;


import com.utility.Util;




public class DriverManager {

	 public static ThreadLocal<WebDriver> webDriver = new ThreadLocal<>();

	    // Call this once per test (Before each scenario)
	    public static void createDriverFromConfig() {
	        String executionEnv = Util.properties("config", "execution_env").trim().toLowerCase();
	        String browser = Util.properties("config", "browser").trim().toLowerCase();

	        if ("remote".equals(executionEnv)) {
	            createRemoteDriver(browser);
	        } else {
	            createLocalDriver(browser);
	        }

	        // common settings
	        WebDriver driver = webDriver.get();
	        driver.manage().deleteAllCookies();
	        long implicit = Long.parseLong(Util.properties("config", "implicit_wait_seconds"));
	        long pageLoad = Long.parseLong(Util.properties("config", "page_load_timeout_seconds"));
	        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(implicit));
	        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoad));
	        driver.manage().window().maximize();
	        // open app URL
	        driver.get(Util.properties("config", "Applink"));
	    }

	    private static void createLocalDriver(String browser) {
	        WebDriver driver;
	        switch (browser) {
	            case "chrome":
	                WebDriverManager.chromedriver().setup();
	                ChromeOptions co = new ChromeOptions();
	                co.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	                driver = new org.openqa.selenium.chrome.ChromeDriver(co);
	                break;
	            case "firefox":
	                WebDriverManager.firefoxdriver().setup();
	                FirefoxOptions fo = new FirefoxOptions();
	                fo.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	                driver = new org.openqa.selenium.firefox.FirefoxDriver(fo);
	                break;
	            case "edge":
	                WebDriverManager.edgedriver().setup();
	                EdgeOptions eo = new EdgeOptions();
	                eo.setPageLoadStrategy(PageLoadStrategy.NORMAL);
	                driver = new org.openqa.selenium.edge.EdgeDriver(eo);
	                break;
	            default:
	                throw new RuntimeException("Unsupported browser: " + browser);
	        }
	        webDriver.set(driver);
	    }

//	    private static void createRemoteDriver(String browser) {
//	        String remoteUrl = Util.properties("config", "remote_url");
//	        MutableCapabilities caps;
//	        switch (browser) {
//	            case "chrome":
//	                ChromeOptions co = new ChromeOptions();
//	                co.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//	                caps = co;
//	                break;
//	            case "firefox":
//	                FirefoxOptions fo = new FirefoxOptions();
//	                fo.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//	                caps = fo;
//	                break;
//	            case "edge":
//	                EdgeOptions eo = new EdgeOptions();
//	                eo.setPageLoadStrategy(PageLoadStrategy.NORMAL);
//	                caps = eo;
//	                break;
//	            default:
//	                throw new RuntimeException("Unsupported browser for remote: " + browser);
//	        }
//
//	        // If you want to set platform from properties, do that here:
//	        String os = null;
//	        try {
//	            os = Util.properties("config", "os");
//	        } catch (Exception ignored) {}
//	        if (os != null) {
//	            switch (os.toLowerCase()) {
//	                case "windows":
//	                    caps.setCapability("platformName", "WINDOWS");
//	                    break;
//	                case "linux":
//	                    caps.setCapability("platformName", "LINUX");
//	                    break;
//	                case "mac":
//	                    caps.setCapability("platformName", "MAC");
//	                    break;
//	            }
//	        }
//
//	        try {
//	            RemoteWebDriver remote = new RemoteWebDriver(new URL(remoteUrl), caps);
//	            webDriver.set(remote);
//	        } catch (MalformedURLException e) {
//	            throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
//	        }
//	    }
	    
	    
	    private static void createRemoteDriver(String browser) {
	        String remoteProvider = Util.properties("config", "remote_provider").trim().toLowerCase();

	        if ("lambdatest".equals(remoteProvider)) {
	            createLambdaTestDriver(browser);
	        } else {
	            // fallback: normal remote (Selenium Grid / Docker Grid)
	            String remoteUrl = Util.properties("config", "remote_url");
	            MutableCapabilities caps;

	            switch (browser) {
	                case "chrome":
	                    caps = new ChromeOptions();
	                    break;
	                case "firefox":
	                    caps = new FirefoxOptions();
	                    break;
	                case "edge":
	                    caps = new EdgeOptions();
	                    break;
	                default:
	                    throw new RuntimeException("Unsupported browser for remote: " + browser);
	            }

	            try {
	                RemoteWebDriver remote = new RemoteWebDriver(new URL(remoteUrl), caps);
	                webDriver.set(remote);
	            } catch (MalformedURLException e) {
	                throw new RuntimeException("Invalid remote URL: " + remoteUrl, e);
	            }
	        }
	    }

	    private static void createLambdaTestDriver(String browser) {
	    	  String username  = Util.properties("config", "lambdatest_username");
	    	    String accessKey = Util.properties("config", "lambdatest_access_key");

	    	    String gridURL = "https://" + username + ":" + accessKey + "@hub.lambdatest.com/wd/hub";

	    	    MutableCapabilities ltOptions = new MutableCapabilities();
	    	    ltOptions.setCapability("build", "Jenkins Build");
	    	    ltOptions.setCapability("project", "BDD Cucumber");
	    	    ltOptions.setCapability("video", true);
	    	    ltOptions.setCapability("network", true);
	    	    ltOptions.setCapability("console", true);

	    	    try {
	    	        MutableCapabilities options;

	    	        switch (browser.toLowerCase()) {
	    	            case "chrome":
	    	                options = new ChromeOptions();
	    	                break;
	    	            case "firefox":
	    	                options = new FirefoxOptions();
	    	                break;
	    	            case "edge":
	    	                options = new EdgeOptions();
	    	                break;
	    	            default:
	    	                throw new RuntimeException("Unsupported browser: " + browser);
	    	        }

	    	        options.setCapability("platformName", "Windows 11");
	    	        options.setCapability("browserVersion", "latest");
	    	        options.setCapability("LT:Options", ltOptions);

	    	        webDriver.set(new RemoteWebDriver(new URL(gridURL), options));

	    	    } catch (Exception e) {
	    	        throw new RuntimeException("❌ LambdaTest driver init failed", e);
	    	    }
	    }


	    // Cleanup utility you can call from @After
	    public static synchronized void quitDriver() {
	        if (webDriver.get() != null) {
	            try {
	                webDriver.get().quit();
	            } catch (Exception e) {
	                // ignore
	            } finally {
	                webDriver.remove();
	            }
	        }
	    	System.out.println("For better debugging");
	    }
	
}
