@echo off
echo 请选择打包环境
set /p env=1.dev 2.test 3.prod 0.local-dev:
if %env% == 0 ( set envName=你选择的是：本地开发环境) else if %env% == 1 (set envName= 你选择的是：开发环境) else if %env% == 2 (set envName= 你选择的是：测试环境) else if %env% == 3 (set envName= 你选择的是：生产环境) else (echo 环境变量选择有误)
echo %envName%
call pc\user-center-provider\install-user-center-provider.bat
cd ..\..\
echo 当前路径：%cd%
call pc\medical-provider\install-medical-provider.bat
cd ..\..\
echo 当前路径：%cd%
call pc\online-web\install-online-web.bat
cd ..\..\
echo 当前路径：%cd%
call pc\online-manager\install-online-manager.bat
cd ..\..\
echo 当前路径：%cd%
call course-provider\install-course-provider
cd ..\..\
echo 当前路径：%cd%
pause