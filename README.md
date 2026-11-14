# Software Engineering Labs

The `lab3` branch starts from the JavaFX login application on `lab2`.

## Development setup

- JDK 25 (select it as the project SDK in IntelliJ IDEA)
- Maven 3.9.9, or a compatible Maven 3.9 installation
- JavaFX 25.0.5, downloaded automatically by Maven

Open this directory's `pom.xml` as a Maven project in IntelliJ IDEA.
Set the project SDK and Maven runner JRE to JDK 25, then reload Maven.
Run commands from the repository root so the app can find `Users.txt`.

```powershell
mvn clean verify
mvn javafx:run
```

For IntelliJ, the Maven tool window's `Plugins > javafx > javafx:run` goal
uses the same dependency and native-access configuration as the command line.
The existing Java `Launcher` run configuration is also available, but it
requires the JavaFX dependencies to be configured by Maven import.

## Lab 3 starting point

The application validates `n` (maximum failed attempts) and `t` (block duration
in seconds) before opening the login screen. Both must be positive integers.
Missing or invalid arguments prompt for replacements in the console.
Login attempts run in background threads. Two daemon workers process failed
attempts through a blocking queue. A known email is locked after `n` failures
for `t` seconds, with a countdown in the login window. Unknown emails receive
a generic error without tracking. Closing the window interrupts the workers
and any active login or countdown task.

Use **Run Concurrency Test** on the login screen to start 20 threads that each
record 499 failures for the same test email. The result shows the total and
whether the account locked on the 10,000th failure.

Pass the arguments through Maven with:

```powershell
mvn javafx:run "-Djavafx.args=3 30"
```
