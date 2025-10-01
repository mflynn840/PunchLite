@echo off
cd /d "%~dp0"
cd ./PunchLite-backend
start cmd /k "mvn spring-boot:run"