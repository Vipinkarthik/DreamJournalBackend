@echo off
set "JAVA_HOME=C:\Program Files\Java\jdk-21"
set "MAVEN_PROJECTBASEDIR=D:\DreamJournal\dreamjournalbackend\dreamjournalbackend"
echo Building and running Spring Boot application...
echo.

REM Try to build with Maven wrapper first
echo Attempting to build with Maven wrapper...
call mvnw.cmd clean package -DskipTests
if %errorlevel% equ 0 (
    echo Build successful! Starting application...
    call mvnw.cmd spring-boot:run
) else (
    echo Maven wrapper failed. Please install Maven or fix the wrapper.
    echo You can also run the application from your IDE.
    pause
)
