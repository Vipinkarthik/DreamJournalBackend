@echo off
echo Starting Dream Journal Backend...

REM Set JAVA_HOME if not set
if "%JAVA_HOME%"=="" (
    set "JAVA_HOME=C:\Program Files\Java\jdk-21"
)

REM Check if Java exists
if not exist "%JAVA_HOME%\bin\java.exe" (
    echo Java not found at %JAVA_HOME%
    echo Please install Java 21 or set JAVA_HOME correctly
    pause
    exit /b 1
)

REM Check if MySQL is running
netstat -an | findstr :3306 >nul
if errorlevel 1 (
    echo MySQL is not running on port 3306
    echo Please start MySQL service
    pause
    exit /b 1
)

echo Java found: %JAVA_HOME%
echo MySQL is running

REM Try to run with Maven wrapper first
echo Attempting to run with Maven wrapper...
call mvnw.cmd spring-boot:run
if %errorlevel% equ 0 goto :success

echo Maven wrapper failed, trying alternative method...

REM Alternative: Try to run the compiled classes directly
echo Checking for compiled classes...
if not exist "target\classes\com\example\dreamjournalbackend\DreamJournalBackendApplication.class" (
    echo Compiled classes not found. Please compile the project first.
    echo You can do this by opening the project in an IDE like IntelliJ IDEA or Eclipse
    pause
    exit /b 1
)

echo Found compiled classes, attempting to run...
echo Note: This may fail due to missing dependencies

"%JAVA_HOME%\bin\java" -cp "target\classes" com.example.dreamjournalbackend.DreamJournalBackendApplication

:success
echo Backend started successfully!
pause
