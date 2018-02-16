@echo off
@setlocal

set ERROR_CODE=0

if exist "pom.xml" goto begin
echo pom.xml not found!! exit >&2
goto error

:begin
if not "%JAVA_HOME%"=="" goto OkJHome
for %%i in (java.exe) do set "JAVACMD=%%~$PATH:i"
goto checkJCmd

:OkJHome
set "JAVACMD=%JAVA_HOME%\bin\java.exe"

:checkJCmd
if exist "%JAVACMD%" goto checkMCmd

echo The JAVA_HOME environment variable is not defined correctly >&2
echo This environment variable is needed to run this program >&2
echo NB: JAVA_HOME should point to a JDK not a JRE >&2
goto error

:checkMCmd

if exist "%MAVEN_HOME%\bin\mvn.cmd" goto init
echo The MAVEN_HOME environment variable is not defined correctly >&2
echo This environment variable is needed to run this program >&2
goto error

:error
set ERROR_CODE=1
exit /b

:init
echo type 'run' if you want to run the app, type 'test' to run tests
set /p action=""

if "%action%"=="run" "%MAVEN_HOME%\bin\mvn.cmd" jetty:run
if "%action%"=="test" "%MAVEN_HOME%\bin\mvn.cmd" test