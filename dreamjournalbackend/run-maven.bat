@echo off
setlocal enabledelayedexpansion

echo Fixing Maven wrapper for username with spaces...

REM Set proper JAVA_HOME with quotes
set "JAVA_HOME=C:\Program Files\Java\jdk-21"

REM Create a temporary batch file to handle the space issue
echo @echo off > temp_maven.bat
echo set "JAVA_HOME=C:\Program Files\Java\jdk-21" >> temp_maven.bat
echo set "MAVEN_PROJECTBASEDIR=%CD%" >> temp_maven.bat

REM Try to find Maven in common locations
if exist "%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.10" (
    for /d %%i in ("%USERPROFILE%\.m2\wrapper\dists\apache-maven-3.9.10\*") do (
        if exist "%%i\apache-maven-3.9.10\bin\mvn.cmd" (
            echo Found Maven at: %%i\apache-maven-3.9.10
            echo "%%i\apache-maven-3.9.10\bin\mvn.cmd" spring-boot:run >> temp_maven.bat
            goto :runmaven
        )
    )
)

echo Maven not found in wrapper cache
echo Please install Maven manually or use an IDE
pause
exit /b 1

:runmaven
echo Running Maven...
call temp_maven.bat
del temp_maven.bat

pause
