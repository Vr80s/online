@echo off
echo 请选择打包环境
set /p env=1.dev 2.test 3.prod 0.local-dev:
if %env% == 0 ( set envName=你选择的是：本地开发环境) else if %env% == 1 (set envName= 你选择的是：开发环境) else if %env% == 2 (set envName= 你选择的是：测试环境) else if %env% == 3 (set envName= 你选择的是：生产环境) else (echo 环境变量选择有误)
echo %envName%
call wechat-api\install-wechat-api.bat
cd ..\
echo 当前路径：%cd%
call wechat-provider\install-wechat-provider.bat
cd ..\
echo 当前路径：%cd%
call wechat\install-wechat.bat
cd ..\
echo 当前路径：%cd%
pause