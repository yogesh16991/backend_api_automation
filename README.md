
## This file contains details to import the project, steps to execute it and view the reports

## Technology Used 
 1. Programming language - Java
 2. IDE - Intellij
 3. API Automtion tool - RestAssured
 4. Build Management tool - Maven
 5. Testing framework - TestNG
 6. Reporting - Extent Reports


 *This project contains multiple scenarios written for 3 services namely users, posts, comments.*
  1. All the test cases are written in testng format and are stored in a respective test classes
   <br/>a. users API call - UsersTest.java
   <br/>b. posts API call - PostsTest.java
   <br/>c. comments API call - CommentTest.java
  2. Have Used TestNg Framework to write the testcases
 
## Framework overview 
 1. src/main/Java - 
  <br/>a. It contains utils package having BaseData class which acts as a parent class to all our test classes mentioned above, Services class having all the resuable methods required for validation and ConfigLoader for file and data loading activity
  <br/>b. It has DataProvider class under dataProvider package used for supplying test data to our test cases
  <br/>c. It contains a ExtentReports classes and Listeners to generate our Test report file

  
 2. src/test/java - 
  <br/>a. It contains tests package which has a test class for each of our API
  <br/>b. It contains resources package which has our configuration properties and JSON Schema stored

 
## Steps to Import and Run the project 
 1. Import project in IDE as Maven project 
 2. Right click pom.xml -> Maven -> Update project -> OK
 3. Project will resolve all the dependencies.
 4. Once above all are resolved, we are good to go on "how to run part".
 5. Execution is by using 2 ways:
  <br/>a. Using testng.xml file - right click on testng.xml and run testng.xml
  <br/>b. Using pom.xml file - right click on pom.xml file > run as maven test.
 6. For automatic execution on circleCI it will run as maven test using pom.xml


## To view execution reports 
 1. Go to TestReport folder un ser root directory -> check for "Test-Automaton-Report.html"

## Code has been pushed to my public git repository and also triggered build on circleCI
 - Git Repo URL: https://github.com/yogesh16991/backend_api_automation
 - CircleCI Dashboard: https://app.circleci.com/pipelines/github/yogesh16991/backend_api_automation/12/workflows/ffe69bfe-9cf3-4df4-b5d4-2e9dae84e78f/jobs/14


## **Our prominent task given for Automation** ##
 > Given: The task is to perform the validations for the comments for the post made by a specific username “Samantha”: 
 * You can find this scenario under test class CommentTest.java class with testcase named "verifyEmailIdsInCommentsAPITest" (*In Project source code*)
 * Documentation of this testcase can be found under Scenarios Covered section > 3. comments > pointer e (*In README file*)


## Additional cases:
 - Few addditional test has been added for each service which has both positive and negative scenarios and have validated the status code along with the JSON Schema.

 ##  *Scenarions covered (Common assertions: Status check and Schema validation)*
 1. users (UsersTest.java):
  <br/>a. Verify response for all users have uniquie usernames - TC name "verifyUserAPITest"
  <br/>b. Verify response by passing a valid user name in query params - TC name "verifyValidUserTest"
  <br/>c. Verify response by passing a Invalid user name in query params - TC name "verifyInvalidUserTest"

 2. posts (PostsTest.java) 
  <br/>a. Verify response for all posts data - TC name "verifyPostsAPITest"
  <br/>b. Verify response by passing a Invalid postId in query params - TC name "verifyInValidPostsAPITest"
  <br/>c. Verify response by passing a Invalid userId and valid postId in query params - TC name "verifyInvalidUserWithValidPostIdPostsAPITest"
  <br/>d. Verify response for all posts data if all users are valid by checking the mappping against users json response - TC name "verifyAllUserAreValidInPostsAPITest"


 3. comments (ComentTest.java) 
  <br/>a. Verify response for all comments data - TC name "verifyCommentAPITest"
  <br/>b. Verify response by passing a Invalid commentId in query params - TC name "verifyInvalidIdCommentAPITest"
  <br/>c. Verify response by passing a Invalid postId and valid commentId in query params - TC name "verifyValidCommentIdWithInvalidPostIdAPITest"
  <br/>d. Verify response for all comments data if all posts are valid by checking the mappping against posts json response - TC name "verifyAllPostsAgainstCommentsAPITest"
  <br/>e. Verify email Ids for the users who has commented on Samantha's posts (Prominent Tesk given as challenge) - TC name "verifyEmailIdsInCommentsAPITest"


## Potential for future work
 1. Could work on more granular commits as I was not able to do so due to my office workload :( 
 2. Better reporting with more logs in reports
 3. Assertions could be better
 4. More scenarios could be added
 5. Logging can be improved


-- by Yogesh Kamble
