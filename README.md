# TAF Backend Server

This is the TAF test automation solution backend. It's not needed to use TAF, but can be used for easy access to test run results, and to keep track of relevant key performance indicators for test automation status and progress.

## What it does
The *TAF Backend Server* has a REST API listening for TAF test run details to be posted to it. The *TAF Backend Server* enables tracking of test runs, analyzing trends and ease of access and communicating test run results throughout the organization (and beyond).

### The landing page
![LandingPage](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerLandingPage.png "TAF Backend Server landing page example")
*The landing page*

The landing page displays the last five test runs with some graphs. This view enables swift tracking of if test case count increases or decreases, whan percentage of test cases are passing or failing, or failing with known errors - and emailing possibilities with direct links to test run results. Test runs with new errors are marked red. Test runs with encountered known errors are marked with a exclamation mark.
Totally failed test cases can be removed.

The landing page is intended to get more dashboard graphs, and is meant to be able to be used in kiosk mode on a screen visible in an office landscape, so it refreshes itself every 60 seconds.

### Test run listings page
![TestRunListingsPage](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerTestRunListingsPage.png "TAF Backend Server test run listings page example")
*Test run listings page*

The test run listings page displays a full list of test runs registered. Colors and symbols help for quick overview of run results. Direct links to specific test runs can be emailed from here, and test run removal can be performed. Test runs with new errors are marked red. Test runs with encountered known errors are marked with a exclamation mark.

The run listings page is meant to be able to be used in kiosk mode on a screen visible in an office landscape, so it refreshes itself every 60 seconds.

### Test run details view
![TestRunDetailsView](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerTestRunDetailsPage.png "Test run details view")
*Test run details view*

The test run details view offers an overview of the test run performed. Any encounterd known error is displayed, as well as new errors. A list of test cases executed is displayed, with test set name and run results. The run settings during the test run is listed as well as some statistics regarding the test. 
Any non-encountered registered known errors are presented in a section of their own, since those might have been fixed and calls for attention.
Direct links to test case logs are provided.

### Test case details view
![TestCaseDetails](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerTestCaseDetailsView.png "Test case details view")
*Test case details view*

Test case details are listed in the same display as regular TAF test case logs. Some general test case details, a section with the test data used during the test run to ease debugging, sections derived from the test steps in the test - and any test step with registered failures expanded. Screenshots displayed in the log, and HTML source saved. Suggestions for registering known errors are displayed upon errors. For further level of details the checkbox about verbose debugging info can be checked.

A thin line in the bar above each test step indicates how long time was spent in that test step.

Known errors are displayed separately in this view too.

# Getting started
## Pre-requisites
To run the *TAF Backend Server* you must first make sure you meet the following pre-requisites:
* Obtain the **TafBackend.jar** file.
* Make sure your TAF tests are running a TAF version of at least 2.5.24. 
* Make sure you have a Java JRE of at least version 1.8 since *Taf Backend Server* is implemented with Java language level 8.
* Tell your TAF tests to report results to *TAF Backend Server*.

Don't worry. We'll walk you through the steps.

## Obtaining the executable file
Contact sales@claremont.se about *TAF Backend Server*. 

## Check your TAF version
In your test automation project, open the `pom.xml` file and look for a section like
```maven
        <dependency>
            <groupId>com.github.claremontqualitymanagement</groupId>
            <artifactId>TestAutomationFramework</artifactId>
            <version>2.5.24</version>
        </dependency>
```

Make sure the version is at least 2.5.24

## Check your java version
Open a command prompt and write
```
java -version
```
When you press **enter** you'll be presented with the java version installed in your operating system. If you don't, either your java installation is not in your operating system path variable (this variable tells your operating system where to look for files when they are not in the current folder) or maybe you don't have java installed at all. Java is free of charge, and can be downloaded.

## Starting the server
The server is started as a normal Java program, from the command line. 

### Usage example
```
java -jar TafBackend.jar port=8080 store=C:\temp\TafBackend.db
```
`port` number is the web server port number for the server to enable access to *http://mytafbackendserver:8080/taf*.
`store`parameter is the local storage file for registered test run data.

Default `port`is `80` and default storage is a filed called `TafBackend.db` in the same folder as the jar file is run from. The name or file extension for the store file are irrelevant.

## Running the server
While running the server it will continuously produce output.
