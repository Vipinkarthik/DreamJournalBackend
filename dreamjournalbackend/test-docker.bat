@echo off
echo Testing Docker build for Dream Journal Backend...

echo.
echo Building Docker image...
docker build -t dreamjournal-backend .

if %errorlevel% neq 0 (
    echo Docker build failed!
    pause
    exit /b 1
)

echo.
echo Docker build successful!
echo.
echo To run the container locally, use:
echo docker run -p 8080:8080 ^
echo   -e DATABASE_URL="jdbc:mysql://localhost:3306/dreamjournal?useSSL=true&serverTimezone=UTC" ^
echo   -e DATABASE_USERNAME="root" ^
echo   -e DATABASE_PASSWORD="your_password" ^
echo   dreamjournal-backend
echo.
pause
