package ConstantVariables;

import org.openqa.selenium.By;

public class Constant {

	public static final By TITLE = By.xpath("/html//title[.='UI Testing Site']");
	public static final By UI_TESTING_BUTTON = By.cssSelector("a#site");
	public static final By SITE_LOGO = By.cssSelector("img#dh_logo");
	public static final By HOME_BUTTON = By.cssSelector("#home");
	public static final By FORM_BUTTON = By.cssSelector("#form");
	public static final By ERROR_BUTTON = By.cssSelector("#error");
	public static final By ERROR_RESPONSE = By.cssSelector("h1");
	public static final By TEXT_H1 = By.cssSelector("h1");
	public static final By TEXT_P = By.cssSelector(".lead");
	public static final By INPUT = By.cssSelector("input");
	public static final By SUBMIT = By.cssSelector("[type='submit']");
	public static final By HELLO_TEXT = By.cssSelector("#hello-text");



}
