bbbbbbbrem OJO, la contraseña por defecto es changeit

rem importar el certificado de la página para que no dé la advertencia de ssl
rem keytool.exe -import -alias preprod_contrataciondelestado_es -keystore "scsp-keystore.jks" -storepass changeit -trustcacerts -file preprod-contrataciondelestado.es.crt
rem keytool.exe -import -alias publicacion_preprod_contrataciondelestado_es -keystore "scsp-keystore.jks" -storepass changeit -trustcacerts -file publicacion.preprod-contrataciondelestado.es.crt

rem importar el certificado de la autoridad de confianza, no estoy seguro de si hace falta
keytool.exe -import -alias CA -keystore "scsp-keystore.jks" -storepass changeit -trustcacerts -file ca.pem

rem http://cunning.sharp.fm/2008/06/importing_private_keys_into_a.html: al parecer keytool no es capaz de almacenar un certificado+su clave privada. A menos que se genere antes el pkcs12 (se supone que es equivalente)
rem generar, partiendo del usuB2B.crt y usuB2B.key, el certificado en formato pkcs12
rem c:\openssl\bin\openssl pkcs12 -export -in usuB2B.crt -inkey usuB2B.key -out usuB2Bcertificate.pfx -certfile ca.pem

rem el nuevo certificado creado a partir del usuB2B.crt y el usuB2B.key
rem keytool -importkeystore -deststorepass changeit -destkeypass changeit -destkeystore "scsp-keystore.jks" -srckeystore usuB2Bcertificate.pfx -srcstoretype PKCS12 -srcstorepass changeit -destalias usuCodice -srcalias 1

rem importar el certificado raíz de la fnmt
rem keytool.exe -import -alias fnmt -keystore "scsp-keystore.jks" -storepass changeit -trustcacerts -file FNMTClase2CA.cer

rem 16/09/2011 nuevo certificado de pruebas: borramos el anterior alias usuCodice, y metemos el nuevo
rem keytool.exe -delete -alias usuCodice -keystore "scsp-keystore.jks" -storepass changeit
rem keytool.exe -import -alias usuCodice -keystore "scsp-keystore.jks" -storepass changeit -trustcacerts -file "CAMERFIRMA APE SELLO Medio Activo.crt"
rem keytool -importkeystore -deststorepass changeit -destkeypass changeit -destkeystore "scsp-keystore.jks" -srckeystore "CAMERFIRMA APE SELLO Medio Activo.p12" -srcstoretype PKCS12 -srcstorepass 1111 -destalias usuCodice -srcalias 1


rem 20-09-2011 ¿ha cambiado el certificado de la página destino?
rem keytool.exe -import -alias publicacion_preprod_contrataciondelestado_es -keystore "scsp-keystore.jks" -storepass changeit -trustcacerts -file publicacion.preprod-contrataciondelestado.es.20.09.2011.crt
