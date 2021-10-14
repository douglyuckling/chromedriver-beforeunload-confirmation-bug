This project demonstrates a bug observed with ChromeDriver when interacting with the confirmation dialog triggered by
a `beforeunload` event in Chrome. The bug is that the dialog remains visible even after it has been accepted or
dismissed.

Due to the nature of the bug, it can't easily be demonstrated with an actual test that passes or fails. So instead, this
project runs a simple application that demonstrates it. It's a simple Spring Boot application, and it uses the Gradle
Wrapper so it has minimal prerequisites to be able to run it.

## How to use this project

### Prerequisites

* A local clone of this repository
* Chrome and ChromeDriver installed and available on your `PATH`
* Java 11 or newer installed and on your `PATH`

### Procedure

Open a shell in this directory and run `./gradlew bootRun` (or `gradlew.bat bootRun` if on Windows). This starts a web
server that hosts a simple page, then starts a ChromeDriver that navigates to the page and demonstrates the problem.
After demonstrating the problem, the application pauses for 5 seconds and then automatically quits the driver and exits.

### Cleanup

The Gradle build will leave a copy of Gradle and this projects dependencies in you home directory in a sub-directory
called `.gradle`, so if you do not normally use Gradle then you may wish to delete that directory when you are done with
this project.
