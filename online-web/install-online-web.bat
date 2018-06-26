@echo off

echo build online-web
cd online-web
if %env% == 0 ( mvn clean package -Pdev-local) else if %env% == 1 ( mvn clean package -Pdev) else if %env% == 2 ( mvn clean package -Ptest) else if %env% == 3 ( mvn clean package -Pprod) else (echo env error)