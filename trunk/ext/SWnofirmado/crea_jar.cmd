rem quito los archivos de la firma: PBCBHandler, rampart.properties,
"C:\Archivos de programa\java\jdk1.6.0_21\bin\jar.exe" cvf SWnofirmado.jar WebContent/META-INF/MANIFEST.MF WebContent/WEB-INF/lib/*.jar log4j.properties -C src src/axis2/axis2.xml src/axis2/modules/* -C build/classes sw


copy SWnofirmado.jar ..\v200912\WEB-INF\lib


rem "C:\Archivos de programa\java\jdk1.6.0_21\bin\jar.exe" cfvm SWnofirmado.jar WebContent/META-INF/MANIFEST.MF WEB-INF/lib/* build/classes/rampart.properties build/classes/log4j.properties build/classes/scsp-keystore.jks build/classes/PWCBHandler.class build/classes/sw/*.class build/classes/axis2/axis2.xml build/classes/axis2/modules/*
