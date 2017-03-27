# TAF Backend Server

This is the TAF test automation solution backend. It's not needed to use TAF, but can be used for easy access to test run results, and to keep track of relevant key performance indicators for test automation status and progress.

## What it does
The *TAF Backend Server* has a REST API listening for TAF test run details to be posted to it. The *TAF Backend Server* enables tracking of test runs, analyzing trends and ease of access and communicating test run results throughout the organization (and beyond).

### The landing page
![LandingPage](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerLandingPage.png "TAF Backend Server landing page example")
*The landing page*

The landing page displays the last five test runs with some graphs. This view enables swift tracking of if test case count increases or decreases, whan percentage of test cases are passing or failing, or failing with known errors - and emailing possibilities with direct links to test run results. Test runs with new errors are marked red. Test runs with encountered known errors are marked with a exclamation mark.
Totally failed test cases can be removed.

### Test run listings page
![TestRunListingsPage](http://46.101.193.212/TAF/images/TafBackendServer/TafBackendServerTestRunListingsPage.png "TAF Backend Server test run listings page example")
*Test run listings page*

The test run listings page displays a full list of test runs registered. Colors and symbols help for quick overview of run results. Direct links to specific test runs can be emailed from here, and test run removal can be performed. Test runs with new errors are marked red. Test runs with encountered known errors are marked with a exclamation mark.

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
