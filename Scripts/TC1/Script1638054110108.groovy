import static com.kms.katalon.core.checkpoint.CheckpointFactory.findCheckpoint
import static com.kms.katalon.core.testcase.TestCaseFactory.findTestCase
import static com.kms.katalon.core.testdata.TestDataFactory.findTestData
import static com.kms.katalon.core.testobject.ObjectRepository.findTestObject
import static com.kms.katalon.core.testobject.ObjectRepository.findWindowsObject
import com.kms.katalon.core.checkpoint.Checkpoint as Checkpoint
import com.kms.katalon.core.cucumber.keyword.CucumberBuiltinKeywords as CucumberKW
import com.kms.katalon.core.mobile.keyword.MobileBuiltInKeywords as Mobile
import com.kms.katalon.core.model.FailureHandling as FailureHandling
import com.kms.katalon.core.testcase.TestCase as TestCase
import com.kms.katalon.core.testdata.TestData as TestData
import com.kms.katalon.core.testng.keyword.TestNGBuiltinKeywords as TestNGKW
import com.kms.katalon.core.testobject.TestObject as TestObject
import com.kms.katalon.core.webservice.keyword.WSBuiltInKeywords as WS
import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI
import com.kms.katalon.core.windows.keyword.WindowsBuiltinKeywords as Windows
import internal.GlobalVariable as GlobalVariable
import org.openqa.selenium.Keys as Keys
import com.kms.katalon.core.testobject.ConditionType
import java.util.regex.Pattern
import java.util.regex.Matcher

WebUI.openBrowser('')
WebUI.navigateToUrl('https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/')

String js1 = """
let element = document.querySelector("div#ember6 header");
let compStyles = window.getComputedStyle(element);
return compStyles
"""
def result1 = WebUI.executeJavaScript(js1, null)
result1.each { item ->
	println item
}

//
String js2 = """
let element = document.querySelector("div#ember6 header");
let compStyles = window.getComputedStyle(element);
let propertyValue = compStyles.getPropertyValue('background-color')
return propertyValue
"""
String rgb = WebUI.executeJavaScript(js2, null)
println "backgroundColor is ${rgb}"            // "rgb(23, 121, 222)"

Pattern pattern = Pattern.compile(/rgb\(\s*(\d+),\s*(\d+),\s*(\d+)\s*\)/)
Matcher matcher = pattern.matcher(rgb)
boolean result2 = matcher.find()
if (result2  == true) {
	String r = matcher.group(1)
	String g = matcher.group(2)
	String b = matcher.group(3)
	WebUI.verifyMatch(r, "23", false)
	WebUI.verifyMatch(g, "121", false)
	WebUI.verifyMatch(b, "222", false)
}

WebUI.closeBrowser()