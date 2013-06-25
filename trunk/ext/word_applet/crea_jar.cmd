"C:\Archivos de programa\java\jdk1.6.0_21\bin\jar.exe" cfvm word_applet.jar MANIFEST WEB-INF/lib/jacob-1.14.1-x64.dll WEB-INF/lib/jacob-1.14.1-x86.dll WEB-INF/lib/jacob.jar java.policy es\burke\gotta\*.class es\burke\gotta\dll\*.class es\burke\misc\*.class

copy word_applet.jar ..\v200912\fijo\applets\word
cd ..\v200912\fijo\applets\word
call firmarWin.cmd
cd ..\..\..\..\word_applet

copy word_applet.jar ..\gotta\fijo\applets\word
cd ..\gotta\fijo\applets\word
call firmarWin.cmd
cd ..\..\..\..\word_applet
