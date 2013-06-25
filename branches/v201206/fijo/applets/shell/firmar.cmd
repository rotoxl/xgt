#!/bin/sh

#para regenenerar el keystore
#del keystore
#keytool -genkey -alias mycert -keystore keystore -validity 9000
#keytool -delete -alias mycert -keystore keystore



jarsigner -storepass tequila -keystore keystore shell_applet.jar mycert
#jarsigner -verify shell_applet.jar #PARA VERIFICAR