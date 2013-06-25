package es.burke.alfresco.resources.i18n;

import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import org.apache.axis2.i18n.MessageBundle;
import org.apache.axis2.i18n.MessagesConstants;

/** Clase que permite acceder a los properties del fichero general.properties */
public class GeneralProperties {

		private static Class<GeneralProperties> thisClass;
		private static final String 		projectName;
		private static final String 		resourceName;
		private static final Locale 		locale;
		private static final String 		packageName;
		private static final ClassLoader 	classLoader;
		private static final ResourceBundle parent;
		private static final MessageBundle 	messageBundle;

		static 
		{
			thisClass 		= es.burke.alfresco.resources.i18n.GeneralProperties.class;
			projectName 	= "es.burke.alfresco.resources.i18n".intern();
			resourceName 	= "general".intern();
			locale 			= MessagesConstants.locale;
			packageName 	= getPackage(thisClass.getName());
			classLoader 	= thisClass.getClassLoader();
			parent 			= "es.burke.alfresco.resources.i18n".intern().equals(packageName) ? null : MessagesConstants.rootBundle;
			messageBundle   = new MessageBundle(projectName, packageName, resourceName, locale, classLoader, parent);
		}


		public static String getNotNullString(String value)
		throws MissingResourceException
		{
			if (value==null) return "";
			return value;
		}

		public static String getNotNullString(String value, String defaultValue)
		throws MissingResourceException
		{
			if (value==null) return defaultValue;
			return value;
		}
		
		
		public static String getProperty(String key)
		throws MissingResourceException
		{
			return messageBundle.getMessage(key);
		}

		public static String getProperty(String key, String arg0)
		throws MissingResourceException
		{
			return messageBundle.getMessage(key, arg0);
		}

		public static String getProperty(String key, String arg0, String arg1)
		throws MissingResourceException
		{
			return messageBundle.getMessage(key, arg0, arg1);
		}

		public static String getProperty(String key, String arg0, String arg1, String arg2)
		throws MissingResourceException
		{
			return messageBundle.getMessage(key, arg0, arg1, arg2);
		}

		public static String getProperty(String key, String arg0, String arg1, String arg2, String arg3)
		throws MissingResourceException
		{
			return messageBundle.getMessage(key, arg0, arg1, arg2, arg3);
		}

		public static String getProperty(String key, String arg0, String arg1, String arg2, String arg3, String arg4)
		throws MissingResourceException
		{
			return messageBundle.getMessage(key, arg0, arg1, arg2, arg3, arg4);
		}

		public static String getProperty(String key, String args[])
		throws MissingResourceException
		{
			return messageBundle.getMessage(key, args);
		}

		public static ResourceBundle getResourceBundle()
		{
			return messageBundle.getResourceBundle();
		}

		public static MessageBundle getMessageBundle()
		{
			return messageBundle;
		}

		private static String getPackage(String name)
		{
			return name.substring(0, name.lastIndexOf('.')).intern();
		}
}
