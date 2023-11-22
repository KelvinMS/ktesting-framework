<h1 align="center">ktesting-framework</h1>
<h2 align="center">POC - Test automation using, TestNG + ExtentReport</h2>
 
<br>

To execute tests

<br>

- ### Clone projec
  ```
  git clone <endereco_do_repositorio>
  ```
- ### Test web - running with mvn

  ```
  $ mvn clean test -Dsurefire.suiteXmlFiles=src/main/resources/suites/suiteTeste.xml
  ```

- ###After run a test
it'll generate a extentReport.html file, that should be open at browser, and test results will be displayed.


<br><br>
🚧 Mobile suite execution is still in construction 🚧

<br>

🚧 API suite execution is still in construction 🚧

<br>

### Technologies used:

<table>
<tr>
        <td>JDK</td>
        <td>Node</td>
        <td>Appium Server</td>
        <td>Maven</td>
</tr>

<tr>
    <td>1.8.0</td>
    <td>14.*</td>
    <td>^1.8</td>
    <td>3.6.*</td>
</tr>
</table>
