@echo off
cd /d "%~dp0"
cd ./PunchLiteDemo
start cmd /k "mvn spring-boot:run"