import java.util.regex.Matcher
import java.util.regex.Pattern

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// open the target web page in browser
WebUI.openBrowser('')
WebUI.navigateToUrl('https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/')

// execute a javascript which returs a list of "Computed CSS properties" of the header element of the page
String js1 = """
let element = document.querySelector("div#ember7 header");
let compStyles = window.getComputedStyle(element);
return compStyles
"""
def result1 = WebUI.executeJavaScript(js1, null)

// let's see the list
result1.each { item ->
	println item
}

// execute another javascript which returns the value of background-color property of the header elemennt of the page
String js2 = """
let element = document.querySelector("div#ember7 header");
let compStyles = window.getComputedStyle(element);
let propertyValue = compStyles.getPropertyValue('background-color')
return propertyValue
"""
String rgb = WebUI.executeJavaScript(js2, null)

// let's see the returned value
println "backgroundColor is ${rgb}"            // we will see "rgb(255, 255, 255)"

// I want to verify each color factors (Red, Green, Blue)
// So I will parse the "rgb" string using Regular expression
Pattern pattern = Pattern.compile(/rgb\(\s*(\d+),\s*(\d+),\s*(\d+)\s*\)/)
Matcher matcher = pattern.matcher(rgb)
boolean result2 = matcher.find()
if (result2  == true) {
	String r = matcher.group(1)
	String g = matcher.group(2)
	String b = matcher.group(3)
	// I will verify if each color factors are equal to what I expected.
	// If unequal, the test should fail.
	WebUI.verifyMatch(r, "255", false)
	WebUI.verifyMatch(g, "255", false)
	WebUI.verifyMatch(b, "255", false)
}

// Done. Bye!
WebUI.closeBrowser()