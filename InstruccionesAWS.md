Amazón ws + jndi
  * Doy permisos al driver del tomcat y a tomcat-dbcp
  * Context.xml -> context.xml
  * context.xml: vacío parámetro path
  * añado el parámetro factory al context.xml (https://forums.aws.amazon.com/thread.jspa?messageID=333139&#333139)
  * añado tomcat-dbcp.jar al path
  * añado al path tomcat-jdbc.jar (http://stackoverflow.com/questions/13161747/jdbc-apache-tomcat-pooling-jar)
  * Cambio al nuevo endPoint de RDS (la instancia anterior murió)
  * Añado al path MySQL driver