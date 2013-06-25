"C:\Archivos de programa\java\jdk1.6.0_21\bin\jar.exe" cfvm shell_applet.jar MANIFEST java.policy es\burke\gotta\*.class es\burke\gotta\dll\*.class es\burke\misc\*.class

copy shell_applet.jar ..\v200912\fijo\applets\shell
cd ..\v200912\fijo\applets\shell
call firmarWin.cmd
cd ..\..\..\..\shell_applet

REM ~ copy shell_applet.jar ..\gotta\fijo\applets\shell
REM ~ cd ..\gotta\fijo\applets\shell
REM ~ call firmarWin.cmd
REM ~ cd ..\..\..\..\shell_applet
