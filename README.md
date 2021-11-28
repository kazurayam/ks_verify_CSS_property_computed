# How to verify value of CSS property in a web page

This is a [Katalon Studio](https://www.katalon.com/katalon-studio/) project for demostration purpose. You can download the zip of this project from the [Releases](https://github.com/kazurayam/ks_verify_CSS_property_computed/releases) page, download it, open it using your local Katalon Studio.

This project was developed with the version 8.2.0 but is version-independent. This should work with all versions since v7.x.

I made this project to propse a solution to a question raised in the User Forum of Katalon Studio.

https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/12

## Problem to solve

Here is an usual web page.

- https://forum.katalon.com/t/verifyelementhasattribute-should-return-boolean-but-instead-forces-error/36726/

It has nothing exeptional. I want to write a Test Case script in Katalon Studio, that verifies the background-color of the page header is `#1779de`.

![screenshot](docs/images/screenshot.png)

Katalon Studio does not provide any Keyword that solves my problem out of box. I need to develop my own solution. But how?



https://developer.mozilla.org/en-US/docs/Web/API/Window/getComputedStyle
