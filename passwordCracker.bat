@echo off
REM Lanceur pour Windows.
cd /d "%~dp0"
if not exist out (
    javac -d out src\cracker\*.java
    if errorlevel 1 (
        echo Echec de compilation
        exit /b 1
    )
)
java -cp out cracker.Main %*
