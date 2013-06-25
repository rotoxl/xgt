/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package es.burke.alfresco.utils;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;

/**
 * Utilidad para recuperar beans del contexto de Spring
 * 
 * @author 
 * @date 
 *
 */
public class SpringBeanHelper {
	
	private ApplicationContext context = null;
	
	/**
	 * Contructor para recuperar el cotexto Spring mediante el contexto de aplicacion j2ee
	 * 
	 * @param applicationContext
	 * 
	 */
	public SpringBeanHelper (ApplicationContext applicationContext) {	
		this.context= applicationContext;	
	}
	/**
	 * Constructor para recuperar el contexto Spring a mediante la ruta absoluta al fichero de configuracion de Spring
	 * Con ClassPathXmlApplicationContext como factoría, se invocará automáticamente cualquier
	 * PropertyPlaceholderConfigurer definido en el contexto sólo con tenerlo declarado en el fichero de conf. de Spring.
	 *	 
	 * @param springAplicationContextPath
	 */
	public SpringBeanHelper (String springAplicationContextPath) {
		this.context = new FileSystemXmlApplicationContext(springAplicationContextPath);
	}

	/**
	 *  Metodo general para recuperar un bean del contexto Spring
	 *  
	 * @param beanName
	 * @return
	 * @throws Exception
	 */
	public Object getBeanInstance (String beanName) throws Exception {
		if (context == null) throw new Exception ("No se ha establecido el contexto. Es imposible recuperar el Objeto: "+ beanName);
		return context.getBean(beanName);
	}

	
}

