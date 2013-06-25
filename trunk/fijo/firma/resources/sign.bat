CLS

@ECHO OFF

:: El parametro storetype hace referencia al tipo de keystore utilizado para
:: firmar. Para extensiones p12 y pfx debe valer 'pkcs12' y en el caso de usar
:: un keystore de java 'JKS'.
:: Para más información consultar el comando jarsigner.exe

SET JARSIGNER="C:\Archivos de programa\Java\jdk1.5.0_08\bin\jarsigner.exe"
SET OP1=-keystore "certificadoDesarrollo.pfx"
SET OP2=-storepass "desarrollo"
SET OP3=-keypass "desarrollo"
SET OP4=-storetype pkcs12
SET ALIAS="{A8D8EDF2-83C1-46A3-B2EA-63C9072F8131}"

SET OPTIONS=%OP1% %OP2% %OP3% %OP4%

::COPY applet_firma.jar plataformafirma-applet.jar

@ECHO Firmando plataformafirma-applet...
%JARSIGNER% %OPTIONS% ..\plataformafirma-applet.jar %ALIAS%

@ECHO Firmando plataformafirma-applet-thirdparty...
%JARSIGNER% %OPTIONS% ..\plataformafirma-applet-thirdparty.jar %ALIAS%

:: @ECHO Verificando...
:: %JARSIGNER% -verify -certs plataformafirma-applet.jar
:: %JARSIGNER% -verify -certs plataformafirma-applet-thirdparty.jar
