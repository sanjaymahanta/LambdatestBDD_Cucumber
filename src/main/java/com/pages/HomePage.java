package com.pages;


import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.baseclass.DriverManager;
import com.utility.Log;
import com.utility.WaitUtils;



public class HomePage extends DriverManager{

	
	
	
	// Elements
		@FindBy(xpath = "//a[contains(normalize-space(.),'Add/Remove Elements')]")
		WebElement addRemove;


		@FindBy(xpath = "//button[contains(normalize-space(.),'Add Element')]")
		WebElement addElementButton;
		
		
		public HomePage(){
			
			PageFactory.initElements(webDriver.get(), this);
		}
		
		
		//Action Methods
		
		
	public void AddRemoveLink() {
		WaitUtils.clickWithFluentWait(addRemove);
		Log.info("click on add/remove link");
	
	}
	
	
	public void addElementButton() {
		Log.info("Page is open");
			WaitUtils.clickWithFluentWait(addElementButton);
		
		
	}
			
		
		}
		
		

		


