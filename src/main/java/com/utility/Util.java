package com.utility;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import com.baseclass.DriverManager;

public class Util {

	 public static String properties(String fileName, String key) {
	        Properties props = new Properties();

	        // ✅ 1. Jenkins / Maven -D se pehle read karo
	        String sysValue = System.getProperty(key);
	        if (sysValue != null && !sysValue.isEmpty()) {
	            System.out.println("🔹 " + key + " (System Property)");
	            return sysValue.trim();
	        }

	        // ✅ 2. Config file fallback (local run)
	        String[] possiblePaths = {
	                "AppConfig/" + fileName + ".properties",
	                "src/test/resources/AppConfig/" + fileName + ".properties"
	        };

	        File found = null;
	        for (String path : possiblePaths) {
	            File f = new File(path);
	            if (f.exists()) {
	                found = f;
	                break;
	            }
	        }

	        if (found == null) {
	            throw new RuntimeException("❌ Could not find " + fileName + ".properties");
	        }

	        try (FileInputStream fis = new FileInputStream(found)) {
	            props.load(fis);
	        } catch (IOException e) {
	            throw new RuntimeException("❌ Failed to load config file", e);
	        }

	        String value = props.getProperty(key);
	        if (value == null || value.isEmpty()) {
	            throw new RuntimeException(
	                    "⚠ Missing property '" + key + "' in " + found.getAbsolutePath()
	            );
	        }

	        System.out.println("🔹 " + key + " = " + value);
	        return value.trim();
	    }

    public static byte[] takeScreenShot() {
        WebDriver driver = DriverManager.webDriver.get();
        if (driver == null) return new byte[0];
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }
}
