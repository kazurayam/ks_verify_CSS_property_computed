# How to verify CSS property of HTML element

I made this project to propose a solution to a question raised in the Katalon Studio User Forum.

- https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/13

## Problem to solve

Here is an usual web page.

- [User forum page](https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/)

I want to write a Test Case script in Katalon Studio, that verifies if the background-color of the page header elemennt is `#1779de`.

![screenshot](docs/images/screenshot.png)

Katalon Studio does not provide a built-in Keyword that solves my problem out of box. I need to develop a custom solution. But how?

## Solution

In JavaScript on browser, you can call *window.getComputedStyle(element)* :

- [Winndow.getComputedStyle(element)](https://developer.mozilla.org/en-US/docs/Web/API/Window/getComputedStyle)

>The Window.getComputedStyle() method returns an object containing the values of all CSS properties of an element, after applying active stylesheets and resolving any basic computation those values may contain.

>Individual CSS property values are accessed through APIs provided by the object, or by indexing with CSS property names.


## Description

### Test Case

- [TC1](Scripts/TC1/Script1638054110108.groovy)

```groovy
import java.util.regex.Matcher
import java.util.regex.Pattern

import com.kms.katalon.core.webui.keyword.WebUiBuiltInKeywords as WebUI

// open the target web page in browser
WebUI.openBrowser('')
WebUI.navigateToUrl('https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/')

// execute a javascript which returs a list of "Computed CSS properties" of the header element of the page
String js1 = """
let element = document.querySelector("div#ember6 header");
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
let element = document.querySelector("div#ember6 header");
let compStyles = window.getComputedStyle(element);
let propertyValue = compStyles.getPropertyValue('background-color')
return propertyValue
"""
String rgb = WebUI.executeJavaScript(js2, null)

// let's see the returned value
println "backgroundColor is ${rgb}"            // we will see "rgb(23, 121, 222)"

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
	WebUI.verifyMatch(r, "23", false)
	WebUI.verifyMatch(g, "121", false)
	WebUI.verifyMatch(b, "222", false)
}

// Done. Bye!
WebUI.closeBrowser()
```

When I execute it on Chrome browser, I saw a bunch of messages in the console. At first, I saw the long list of all computed CSS properties of the "header" element.

```
2021-11-28 08:39:59.092 DEBUG testcase.TC1                             - 5: result1.each({ java.lang.Object item -> ... })
accent-color
align-content
align-items
align-self
alignment-baseline
animation-delay
animation-direction
animation-duration
animation-fill-mode
animation-iteration-count
animation-name
animation-play-state
animation-timing-function
app-region
appearance
backdrop-filter
backface-visibility
background-attachment
background-blend-mode
background-clip
background-color
background-image
background-origin
background-position
background-repeat
background-size
baseline-shift
block-size
border-block-end-color
border-block-end-style
border-block-end-width
border-block-start-color
border-block-start-style
border-block-start-width
border-bottom-color
border-bottom-left-radius
border-bottom-right-radius
border-bottom-style
border-bottom-width
border-collapse
border-end-end-radius
border-end-start-radius
border-image-outset
border-image-repeat
border-image-slice
border-image-source
border-image-width
border-inline-end-color
border-inline-end-style
border-inline-end-width
border-inline-start-color
border-inline-start-style
border-inline-start-width
border-left-color
border-left-style
border-left-width
border-right-color
border-right-style
border-right-width
border-start-end-radius
border-start-start-radius
border-top-color
border-top-left-radius
border-top-right-radius
border-top-style
border-top-width
bottom
box-shadow
box-sizing
break-after
break-before
break-inside
buffered-rendering
caption-side
caret-color
clear
clip
clip-path
clip-rule
color
color-interpolation
color-interpolation-filters
color-rendering
column-count
column-gap
column-rule-color
column-rule-style
column-rule-width
column-span
column-width
contain-intrinsic-block-size
contain-intrinsic-height
contain-intrinsic-inline-size
contain-intrinsic-size
contain-intrinsic-width
content
cursor
cx
cy
d
direction
display
dominant-baseline
empty-cells
fill
fill-opacity
fill-rule
filter
flex-basis
flex-direction
flex-grow
flex-shrink
flex-wrap
float
flood-color
flood-opacity
font-family
font-kerning
font-optical-sizing
font-size
font-stretch
font-style
font-variant
font-variant-caps
font-variant-east-asian
font-variant-ligatures
font-variant-numeric
font-weight
grid-auto-columns
grid-auto-flow
grid-auto-rows
grid-column-end
grid-column-start
grid-row-end
grid-row-start
grid-template-areas
grid-template-columns
grid-template-rows
height
hyphens
image-orientation
image-rendering
inline-size
inset-block-end
inset-block-start
inset-inline-end
inset-inline-start
isolation
justify-content
justify-items
justify-self
left
letter-spacing
lighting-color
line-break
line-height
list-style-image
list-style-position
list-style-type
margin-block-end
margin-block-start
margin-bottom
margin-inline-end
margin-inline-start
margin-left
margin-right
margin-top
marker-end
marker-mid
marker-start
mask-type
max-block-size
max-height
max-inline-size
max-width
min-block-size
min-height
min-inline-size
min-width
mix-blend-mode
object-fit
object-position
offset-distance
offset-path
offset-rotate
opacity
order
orphans
outline-color
outline-offset
outline-style
outline-width
overflow-anchor
overflow-clip-margin
overflow-wrap
overflow-x
overflow-y
overscroll-behavior-block
overscroll-behavior-inline
padding-block-end
padding-block-start
padding-bottom
padding-inline-end
padding-inline-start
padding-left
padding-right
padding-top
paint-order
perspective
perspective-origin
pointer-events
position
r
resize
right
row-gap
ruby-position
rx
ry
scroll-behavior
scroll-margin-block-end
scroll-margin-block-start
scroll-margin-inline-end
scroll-margin-inline-start
scroll-padding-block-end
scroll-padding-block-start
scroll-padding-inline-end
scroll-padding-inline-start
scrollbar-gutter
shape-image-threshold
shape-margin
shape-outside
shape-rendering
speak
stop-color
stop-opacity
stroke
stroke-dasharray
stroke-dashoffset
stroke-linecap
stroke-linejoin
stroke-miterlimit
stroke-opacity
stroke-width
tab-size
table-layout
text-align
text-align-last
text-anchor
text-decoration
text-decoration-color
text-decoration-line
text-decoration-skip-ink
text-decoration-style
text-indent
text-overflow
text-rendering
text-shadow
text-size-adjust
text-transform
text-underline-position
top
touch-action
transform
transform-origin
transform-style
transition-delay
transition-duration
transition-property
transition-timing-function
unicode-bidi
user-select
vector-effect
vertical-align
visibility
white-space
widows
width
will-change
word-break
word-spacing
writing-mode
x
y
z-index
zoom
-webkit-border-horizontal-spacing
-webkit-border-image
-webkit-border-vertical-spacing
-webkit-box-align
-webkit-box-decoration-break
-webkit-box-direction
-webkit-box-flex
-webkit-box-ordinal-group
-webkit-box-orient
-webkit-box-pack
-webkit-box-reflect
-webkit-font-smoothing
-webkit-highlight
-webkit-hyphenate-character
-webkit-line-break
-webkit-line-clamp
-webkit-locale
-webkit-mask-box-image
-webkit-mask-box-image-outset
-webkit-mask-box-image-repeat
-webkit-mask-box-image-slice
-webkit-mask-box-image-source
-webkit-mask-box-image-width
-webkit-mask-clip
-webkit-mask-composite
-webkit-mask-image
-webkit-mask-origin
-webkit-mask-position
-webkit-mask-repeat
-webkit-mask-size
-webkit-print-color-adjust
-webkit-rtl-ordering
-webkit-tap-highlight-color
-webkit-text-combine
-webkit-text-decorations-in-effect
-webkit-text-emphasis-color
-webkit-text-emphasis-position
-webkit-text-emphasis-style
-webkit-text-fill-color
-webkit-text-orientation
-webkit-text-security
-webkit-text-stroke-color
-webkit-text-stroke-width
-webkit-user-drag
-webkit-user-modify
-webkit-writing-mode

```

I could get access to the values of all these computed CSS properties. For example, I could get the value of `background-color` property of the "header" element.

```
2021-11-28 08:39:59.305 DEBUG testcase.TC1                             - 8: println(backgroundColor is $rgb)
backgroundColor is rgb(23, 121, 222)
```

As you see, the `rgb` variable contained a string `"rgb(23, 121, 222)"`.

Next, I want to further verify the value of each of RGB factors.

1. verify if the Red factor is "23"
2. verify if the Greenn factor is "121"
3. verify if the Blue factor is "222"

Then I needed to parse a String value of the `rgb` variable. I employed the Regular Expuression support in Java/Groovy (`java.util.regex.Patter` and `java.util.regex.Matcher`).

## Usability

This code is useful, but as you see, it is long and complicated. You would like to make it a Custom Keyword. Please try it for yourself.

This approach is version-independent. It should work with all versions of Katalo Studio since v7.x.

## How to download the demo

You can download the zip of this project from the [Releases](https://github.com/kazurayam/ks_verify_CSS_property_computed/releases) page, download it, open it using your local Katalon Studio.
