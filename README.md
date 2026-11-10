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

This setup aligns the build tools only. The threading and account
lockout requirements have not been implemented yet. Once argument handling
is added, the application can receive `n` and `t` through Maven with:

```powershell
mvn javafx:run "-Djavafx.args=3 30"
```
