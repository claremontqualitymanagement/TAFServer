# TAF Backend Server

This is the TAF test automation solution backend. It's not needed to use TAF, but can be used for easy access to test run results, and to keep track of relevant key performance indicators for test automation status and progress.

For more information about TAF, visit the [TAF GitHub repo](https://github.com/claremontqualitymanagement/TestAutomationFramework "TAF on GitHub").

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

# How it works
The *TAF Backend Server* is a java program that has a built in web server that hosts a number of REST services. After a test execution with TAF the test run results are posted to a REST service in *TAF Backend Server* where it is added to statistics and stored for viewing.

![TafBackendServerSchematicOverview](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendSchematicOverview.PNG "Schematic overview")

The *TAF Backend Server* then hosts a web interface where statistics and test run information can be viewed.

# Getting started
## Pre-requisites
To ger going with the *TAF Backend Server* you must first make sure you perform the following steps:
1. Obtain the **TafBackend.jar** file.
2. Make sure your TAF tests are running a TAF version of at least 2.5.24. 
3. Make sure you have a Java JRE of at least version 1.8 since *Taf Backend Server* is implemented with Java language level 8.
4. Start your *TAF Backed Server*
4. Tell your TAF tests to report results to *TAF Backend Server*.

Don't worry. We'll walk you through the steps.

## 1. Obtaining the executable file
Contact sales@claremont.se about *TAF Backend Server*. 

## 2. Check your TAF version
In your test automation project, open the `pom.xml` file and look for a section like
```pom
        <dependency>
            <groupId>com.github.claremontqualitymanagement</groupId>
            <artifactId>TestAutomationFramework</artifactId>
            <version>2.5.24</version>
        </dependency>
```

Make sure the version is at least 2.5.24.

If your are running tests from the command line interface the TAF version is also visible in HTML summary reports for test runs.


## 3. Check your java version
Open a command prompt and write
```
java -version
```
When you press **enter** you'll be presented with the java version installed in your operating system. If you don't, either your java installation is not in your operating system path variable (this variable tells your operating system where to look for files when they are not in the current folder) or maybe you don't have java installed at all. Java is free of charge, and can be downloaded.

Make sure your java is at least version 1.8.

## 4. Starting the server
The server is started as a normal Java program, from the command line. 

### Usage example
```
java -jar TafBackend.jar port=8080 store=C:\temp\TafBackend.db
```
`port` number is the web server port number for the server to enable access to *http://mytafbackendserver:8080/taf*.
`store`parameter is the local storage file for registered test run data.

Default `port`is `80` and default storage is a filed called `TafBackend.db` in the same folder as the jar file is run from. The name or file extension for the store file are irrelevant.

## 5. Preparing your TAF test automation to post results to *TAF Backend Server*
The TAF Backend Server TestRunReporter is engaged when the TAF test run setting called `URL_TO_TAF_BACKEND` is changed from its default value. There are three ways of doing this, since there are three ways of updating a run settings variable for TAF. 
One way of doing this is from the command line when starting your TAF test run.

### a). Via command line interface at test run execution
```
java -jar TafFull.jar MyTestClasses URL_TO_TAF_BACKEND=http://localhost:81/taf
```

Of course the address and port of your backend server should replace the `localhost:81`in the example above.

### b). Programatically in your TAF tests
Another way is programatically, by adding the Testlink adapter, like in the example below.
```java
    @BeforeClass
    public static void setup(){
        TestRun.setSettingsValue(Settings.SettingParameters.URL_TO_TESTLINK_ADAPTER, 
               "http://127.0.0.1:2221/taftestlinkadapter");
   }
```
### c). Editing the `runSettings.properties` file
Test run settings can be set in a TAF run file. Setting the value of `URL_TO_TAF_BACKEND` in this file will engage the TafBackendTestRunReporter.

Either way of updating the `URL_TO_TAF_BACKEND` settings value will engage the reporting to this adapter.

# Running the server
While running the server it will continuously produce output. Received test run data will be stored in *TAF Backend Server* storage.

The server also incorporates an about page where version information can be accessed and links to relevant sources can be found.
![AboutPage](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerAboutPage.png "The TAF Backend Server About page")

gl:hf

![ClaremontLogo](http://46.101.193.212/TAF/images/claremontlogo.gif "Claremont logo")

