@echo off
cd /d "%~dp0"
cd ./punchlite-frontend
start cmd /k "npm run dev"