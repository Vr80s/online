@echo off
echo ��ѡ��������
set /p env=1.dev 2.test 3.prod 0.local-dev:
if %env% == 0 ( set envName=��ѡ����ǣ����ؿ�������) else if %env% == 1 (set envName= ��ѡ����ǣ���������) else if %env% == 2 (set envName= ��ѡ����ǣ����Ի���) else if %env% == 3 (set envName= ��ѡ����ǣ���������) else (echo ��������ѡ������)
echo %envName%
call wechat-api\install-wechat-api.bat
cd ..\
echo ��ǰ·����%cd%
call wechat-provider\install-wechat-provider.bat
cd ..\
echo ��ǰ·����%cd%
call wechat\install-wechat.bat
cd ..\
echo ��ǰ·����%cd%
pause