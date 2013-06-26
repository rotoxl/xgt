REM ~ rem quito los archivos de la firma: PBCBHandler, rampart.properties,
REM ~ "C:\Archivos de programa\java\jdk1.6.0_21\bin\jar.exe" cvf SWfirmado.jar WebContent/META-INF/*.MF WebContent/WEB-INF/lib/*.jar -C src log4j.properties -C src rampart.properties -C src scsp-keystore.jks src/axis2/axis2.xml src/axis2/modules/* -C build/classes PWCBHandler.class build/classes/sw

REM ~ copy SWfirmado.jar ..\v200912\WEB-INF\lib



"C:\Archivos de programa\java\jdk1.5.0_15\bin\jar.exe" cvf SWfirmado.jar WebContent/WEB-INF/lib/*.jar -C build/classes .

copy SWfirmado.jar ..\v200912\WEB-INF\lib