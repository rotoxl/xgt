rem keytool.exe -genkey -alias mycert -keystore .keystore -validity 9000
rem keytool.exe -delete -alias mycert -keystore .keystore

"C:\Archivos de programa\java\jdk1.6.0_21\bin\jarsigner.exe" -storepass defcondos -keystore .keystore word_applet.jar MyCert
