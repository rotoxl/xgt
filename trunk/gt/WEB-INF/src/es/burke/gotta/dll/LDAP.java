package es.burke.gotta.dll;



import java.util.Hashtable;

import javax.naming.AuthenticationException;
import javax.naming.Binding;
import javax.naming.CompositeName;
import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NameClassPair;
import javax.naming.NameNotFoundException;
import javax.naming.NameParser;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import es.burke.gotta.Aplicacion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorFaltanDatos;
import es.burke.gotta.ErrorLDAP;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;

public class LDAP{
	private DirContext ctx=null;
	
	private String provider_url;//"ldap://ron:389/"
	private String plantillaRuta;//"cn=$1,ou=personas,o=cej.justicia,o=proteus"
	private String filtroBusqueda;
	
	private String usuEscritura;//"cn=ernesto,ou=personas,o=cej.justicia,o=proteus" requiere permisos de escritura
	private String pwdEscritura;//"salta" 
	
	private Usuario usu;
	private Aplicacion apli;
	
	public LDAP(Usuario usu) throws ErrorFaltanDatos, ErrorArrancandoAplicacion{
		this.usu=usu;
		this.apli=usu.getApli();
		this.sacaDatosConfiguracionLDAP();
		}
	public LDAP(Aplicacion apli) throws ErrorFaltanDatos{
		this.apli=apli;
		this.sacaDatosConfiguracionLDAP();
		}
	private void sacaDatosConfiguracionLDAP() throws ErrorFaltanDatos {
		this.provider_url=apli.getDatoConfig("ldap_server");
		this.plantillaRuta=apli.getDatoConfig("ldap_plantilla_ruta");
		
		if (this.provider_url==null || this.plantillaRuta==null)
			throw new ErrorFaltanDatos("Faltan los datos de configuración de LDAP: ldap_server y ldap_plantilla_ruta");
		
		this.usuEscritura=apli.getDatoConfig("ldap_user"); //sólo para operaciones de escritura
		this.pwdEscritura=apli.getDatoConfig("ldap_password");  //sólo para operaciones de escritura
		//RAFA: añadimos un filtro para buscar en caso de que estén los usuarios en distintas ramas:
		this.filtroBusqueda=apli.getDatoConfig("ldap_filtro_busqueda");
		}
	private void conexionDirectorio(String usuario, String contraseña) throws ErrorLDAP {
		//Añadimos propiedad para evitar el PartialResultException 'Unprocessed Continuation Reference'
		
		Hashtable<String,String> env = new Hashtable<String, String>();

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.PROVIDER_URL, 			 this.provider_url); 

        env.put(Context.SECURITY_AUTHENTICATION,"simple");
        
        
        env.put(Context.SECURITY_PRINCIPAL, 	usuario); 	 //"cn=admin, o=proteus"
        env.put(Context.SECURITY_CREDENTIALS, 	contraseña); //"salta"
        //Añadimos para intentar evitar el javax.naming.PartialResultException: Unprocessed Continuation Reference(s)
        env.put(Context.REFERRAL, "follow" );
        
       // env.put(Context.SECURITY_PRINCIPAL, 	this.usuEscritura); 	 //"cn=admin, o=proteus"
       // env.put(Context.SECURITY_CREDENTIALS, 	this.pwdEscritura); //"salta"
		try {
			
			ctx = new InitialDirContext(env); // Create the initial context
			
			} 
		catch (NamingException e) {
			e.printStackTrace();
			this.apli.sacaError("creaContexto::LDAP::Error conectandose al directorio: "+e.getMessage());
			throw new ErrorLDAP(e.getMessage());
			}
		}
	private void cierraContexto() throws ErrorLDAP {
		try {
			if (ctx!=null)
				ctx.close(); // Close the context when we're done
			} 
		catch (NamingException e) {
			throw new ErrorLDAP(e.getMessage());
			} 	
		}
	/**
	 * Método común para validación por LDAP. Distingue, según DIC_CONFIGURACION,
	 * si buscará por patrón (OpenLDAP) o por filtro de busqueda (Directorio Activo Microsoft).
	 * No sólo depende del vendedor; creo que es configurable en todos los LDAP. Todo depende de si los usuarios
	 * están en la misma rama.
	 *  Para validar el usuario 'por filtro' hacemos un 'bind' con sus 
	 *  credenciales
	 * @param usuario
	 * @param contraseña
	 * @return Si el usuario es válido o no en el LDAP
	 */
	
	public boolean validaUsuario(String usuario, String contraseña) {
		boolean resultado = false;
		//Impedimos la password vacia:
		if(Constantes.CAD_VACIA.equals(contraseña)) return false;
		// Si existe el parámetro this.filtroBusqueda (DIC_CONFIGURACIÓN) es que
		// hay que buscar
		// por 'Search' y no por 'Pattern'
		this.apli.println("validaUsuario::LDAP::Inicio validación de': "+usuario+"'");
		try {
			if (this.filtroBusqueda != null	&&
					!this.filtroBusqueda.equals(Constantes.CAD_VACIA)) {
				//validacion 'Search'
				String[] attrIds = null;
				try {
					//Si el usaurio-password no son correctos no puede ni buscar
					//con los proporcionados
					//Me conecto con el usuario de dic_configuración (no todos los usuarios pueden buscar en subramas, creo)
					conexionDirectorio(this.usuEscritura, this.pwdEscritura);
				} catch (ErrorLDAP e) {
					e.printStackTrace();
					return false;
				}
				
				resultado = getUserBySearch(usuario, attrIds,contraseña);
				return resultado;
			} else {
				//validacion 'Pattern'
				String xUsu = null;
				xUsu = Util.sustituyePlantilla(this.plantillaRuta, usuario);
				try {
					conexionDirectorio(xUsu, contraseña);
				} catch (ErrorLDAP e) {
					return false;
				}
				// creaContexto(this.usuEscritura,this.pwdEscritura);
				ctx.lookup(xUsu); // añadimos búsqueda para constatar que el
									// objeto existe				
				resultado= true;
			}

		} catch (NamingException e) {
			this.apli.sacaError("validaUsuario::LDAP::Error en la busqueda del usuario: "+usuario);
			resultado=false;
		}finally{			
			try {
				cierraContexto();
			} catch (ErrorLDAP e) {				
				e.printStackTrace();
			}
		}
		return resultado;
	}

	private void verificaUsuEscrituraRelleno() throws ErrorFaltanDatos{
		if (this.usuEscritura==null || this.pwdEscritura==null)
			throw new ErrorFaltanDatos("Faltan los datos de configuración de LDAP: faltan el usuario y contraseña con permiso de escritura en el directorio (ldap_user y ldap_password)");
		}
	public void eliminaUsuario(String uid) throws ErrorLDAP, ErrorFaltanDatos{
		verificaUsuEscrituraRelleno();
		if (ctx==null) conexionDirectorio(this.usuEscritura, this.pwdEscritura);
			
		String ruta=Util.sustituyePlantilla(this.plantillaRuta, uid );
		try {
			ctx.unbind(ruta);
			} 
		catch (NamingException e) {
			throw new ErrorLDAP(e.getMessage());
			}
		finally {
			cierraContexto();
			}
		}
	public void insertaUsuario(String uid, String contraseña, String apellido, String email, String telefono, Iterable<String> ou) throws ErrorLDAP, ErrorFaltanDatos{
		verificaUsuEscrituraRelleno();
		if (ctx==null) conexionDirectorio(this.usuEscritura, this.pwdEscritura);
		
		InetOrgPerson xusr = new InetOrgPerson(uid, contraseña, uid, apellido, email, telefono, ou);
		String ruta=Util.sustituyePlantilla(this.plantillaRuta, uid );
		try {
			ctx.bind( ruta, xusr);
			} 
		catch (NamingException e) {
			throw new ErrorLDAP(e.getMessage());
			}
		finally {
			cierraContexto();
			}
		}
	public void modificaUsuario(String uid, String contraseña, String apellido, String email, String telefono, Iterable<String> ou) throws ErrorLDAP, ErrorFaltanDatos{
		verificaUsuEscrituraRelleno();
		
		if (ctx==null) conexionDirectorio(this.usuEscritura, this.pwdEscritura);
		
		InetOrgPerson xusr = new InetOrgPerson(uid, contraseña, uid, apellido, email, telefono, ou);
		String ruta=Util.sustituyePlantilla(this.plantillaRuta, uid );
		try {
			ctx.rebind( ruta, xusr);
			} 
		catch (NamingException e) {
			throw new ErrorLDAP(e.getMessage());
			}
		finally {
			cierraContexto();
			}
		}
	public String extraeDato(String ruta, String id) throws ErrorLDAP{
		if (ctx==null) {
			if (this.usuEscritura!=null && this.pwdEscritura!=null && !this.usuEscritura.equals(Constantes.CAD_VACIA))
				conexionDirectorio(this.usuEscritura, this.pwdEscritura);
			else {
				String rutaUsu=Util.sustituyePlantilla(this.plantillaRuta, usu.getLogin());
				conexionDirectorio(rutaUsu, usu.getPassword());
				}
			}
		
		String[] listaId= new String[1]; listaId[0]=id;
		try {
			Attributes resultado=ctx.getAttributes(ruta, listaId);
			Attribute attribute = resultado.get(id);
			if(attribute==null)
				return null;
			return attribute.get(0).toString();
			} 
		catch (NamingException e) {
			this.apli.sacaError(e.getStackTrace());
			}
		finally {
			cierraContexto();
			}
		return Constantes.CAD_VACIA;
	}
	/**
	 * Método que busca el usuario, en lugar de sacarlo de un patrón (Fusilado de org.apache.catalina.realm.JNDIRealm)
	 * TODO: permitir filtros con condiciones. Ahora sólo le podemos pasar UN campo por el que buscar. En la
	 * clase de Tomcat está implementado sintaxis OR, pero no AND.
	 */
	private boolean getUserBySearch(String username,
            String[] attrIds,
            String contraseña)
	 		 throws NamingException {
		//aplico varios cambios para evitar PartialResultException según:
		//		http://forums.sun.com/thread.jspa?messageID=10258094&#10258094
	        if (username == null )
	            return false;
	        // Formamos el filtro
	        String filter = Util.sustituyePlantilla(this.filtroBusqueda, username);
	        // Establecemos controles de búsqueda
	        SearchControls constraints = new SearchControls();
	        //Para que busque en subramas:
	        constraints.setSearchScope(SearchControls.SUBTREE_SCOPE);
	        // TODO Atributos a recuperar del LDAP
	        if (attrIds == null)
	            attrIds = new String[0];
	        constraints.setReturningAttributes(attrIds);
	        NamingEnumeration<SearchResult>results =
	            ctx.search(this.plantillaRuta, filter, constraints);
	        // Falla si no encuentra nada
	        if (results == null || !results.hasMoreElements()) {
	        	this.apli.sacaError("getUserBySearch::LDAP::Usuario "+username+" no encontrado con el filtro de busqueda "+this.filtroBusqueda);
	            return false;
	        }
	        // Get result for the first entry found
	        SearchResult result = results.next();
	        
	        // Check no further entries were found
	        if (results.hasMoreElements()) {	          
	        	this.apli.sacaError("getUserBySearch::LDAP::El usuario " + username + " aparece repetido en LDAP");
	            return false;
	        }
	        //Una vez comprobado que existe, vemos si se puede conectar con la password proporcionada.
	        //Es que al AD no tiene forma de devolver la password del usuario: de la siguiente forma petaba.
	        //bendita fundación apache
	       /* if (ctx!=null) ctx.close();
	        try {
				creaContexto(username, contraseña);
			} catch (ErrorLDAP e) {
				 System.err.println("getUserBySearch::LDAP::Contraseña incorrecta");
				 return false;
			}*/
	        //Con las siguientes lineas se obtiene el nombre distinguido del usuario, con el que intento conectar
	        //para ver si la pass es válida. Repito: bendita fundación apache
	        //
	        NameParser parser = ctx.getNameParser("");
	        Name contextName = parser.parse(ctx.getNameInNamespace());
	        Name baseName = parser.parse(this.plantillaRuta);
//	      Bugzilla 32269
	        Name entryName = parser.parse(new CompositeName(result.getName()).get(0));

	        Name name = contextName.addAll(baseName);
	        name = name.addAll(entryName);
	        String dn = name.toString();
	        
	        //invocamos al método que comprueba mediante 'ligado' al directorio
			boolean resultado=comprobarUsuario(dn, contraseña);
			if (!resultado)
				this.apli.sacaError("getUserBySearch::LDAP::Contraseña de '"+dn+"' incorrecta ");				 				        
	        return resultado;	    
	 }
	/**
     * Comprueba usuario y password intentando hacer un 'ligado' al directorio.
     *
     * 
     * @param Usuario
     * @param password
     *
     * @exception NamingException si hay algún error de LDAP
     */
     private boolean comprobarUsuario(
                                  String usuario,
                                  String password)
         throws NamingException {

         if (password == null || usuario == null)
             return (false);

        

        
         this.apli.println("  Validando credenciales haciendo un bind del usuario");
       

         // Variables de seguridad para este usuario:
        ctx.addToEnvironment(Context.SECURITY_PRINCIPAL, usuario);
        ctx.addToEnvironment(Context.SECURITY_CREDENTIALS, password);

        // Elicit an LDAP bind operation
        boolean validated = false;
        try {
           
        	this.apli.println("  binding de "  + usuario);
            
            ctx.getAttributes("", null);
            validated = true;
            this.apli.println("Usuario "+usuario+" validado contra LDAP");
        }
        catch (AuthenticationException e) {
           
        	this.apli.sacaError("  binding fallido: "+e.getMessage());
            
        }

        // Restore the original security environment
        if (this.usuEscritura != null) {
            ctx.addToEnvironment(Context.SECURITY_PRINCIPAL,
            		this.usuEscritura);
        } else {
            ctx.removeFromEnvironment(Context.SECURITY_PRINCIPAL);
        }

        if (this.pwdEscritura != null) {
            ctx.addToEnvironment(Context.SECURITY_CREDENTIALS,
            		this.pwdEscritura);
        }
        else {
            ctx.removeFromEnvironment(Context.SECURITY_CREDENTIALS);
        }

        return (validated);
     }

}

class InetOrgPerson implements DirContext{
	   String id;
	   String dn;
	   Attributes myAttrs = new BasicAttributes(true);
	   Attribute oc = new BasicAttribute("objectclass");
	   Attribute ouSet = new BasicAttribute("ou");
	public InetOrgPerson(String uid, String pwd, String givenname, String sn, String mail, String telf, Iterable<String> ou){
	      this.dn=uid;
	      id = uid;
	      
	      oc.add("inetOrgPerson");
	      oc.add("organizationalPerson");
	      oc.add("person");
	      oc.add("top");
	      
	      for (String it:ou)
	    	  ouSet.add(it);
	      
	      myAttrs.put(oc);
	      myAttrs.put(ouSet);

	      myAttrs.put("cn",uid);
	      myAttrs.put("sn",sn+"");
	      myAttrs.put("givenname",givenname+"");
	      
	      myAttrs.put("userPassword", pwd);
	      
	      if (mail!=null) myAttrs.put("mail",mail);
	      if (telf!=null) myAttrs.put("telephonenumber",telf);
	   }
	public Attributes getAttributes(String name) throws NamingException {
	      if (! name.equals("")){
	         throw new NameNotFoundException();
	      }
	      return myAttrs;
	   }
	public Attributes getAttributes(Name name) throws NamingException {
	      return getAttributes(name.toString());
	   }
	public Attributes getAttributes(String name, String[] ids) throws NamingException {
	      if(! name.equals(""))
	         throw new NameNotFoundException();
	      Attributes answer = new BasicAttributes(true);
	      Attribute target;
	      for (int i = 0; i < ids.length; i++){
	         target = myAttrs.get(ids[i]);
	         if (target != null){
	            answer.put(target);
	         }
	      }
	      return answer;
	   }

	public void bind(Name arg0, Object arg1, Attributes arg2) throws NamingException {
		/**/
		
	}
	public void bind(String arg0, Object arg1, Attributes arg2) throws NamingException {
		/**/
		
	}
	public DirContext createSubcontext(Name arg0, Attributes arg1) throws NamingException {
		/**/
		return null;
	}
	public DirContext createSubcontext(String arg0, Attributes arg1) throws NamingException {
		/**/
		return null;
	}
	
	public Attributes getAttributes(Name arg0, String[] arg1) throws NamingException {
		/**/
		return null;
	}
	public DirContext getSchema(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public DirContext getSchema(String arg0) throws NamingException {
		/**/
		return null;
	}
	public DirContext getSchemaClassDefinition(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public DirContext getSchemaClassDefinition(String arg0) throws NamingException {
		/**/
		return null;
	}
	public void modifyAttributes(Name arg0, ModificationItem[] arg1) throws NamingException {
		/**/
		
	}
	public void modifyAttributes(String arg0, ModificationItem[] arg1) throws NamingException {
		/**/
		
	}
	public void modifyAttributes(Name arg0, int arg1, Attributes arg2) throws NamingException {
		/**/
		
	}
	public void modifyAttributes(String arg0, int arg1, Attributes arg2) throws NamingException {
		/**/
		
	}
	public void rebind(Name arg0, Object arg1, Attributes arg2) throws NamingException {
		/**/
		
	}
	public void rebind(String arg0, Object arg1, Attributes arg2) throws NamingException {
		/**/
		
	}
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(Name arg0, Attributes arg1, String[] arg2) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(String arg0, Attributes arg1, String[] arg2) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1, SearchControls arg2) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(String arg0, String arg1, SearchControls arg2) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(Name arg0, String arg1, Object[] arg2, SearchControls arg3) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<SearchResult> search(String arg0, String arg1, Object[] arg2, SearchControls arg3) throws NamingException {
		/**/
		return null;
	}
	public Object addToEnvironment(String arg0, Object arg1) throws NamingException {
		/**/
		return null;
	}
	public void bind(Name arg0, Object arg1) throws NamingException {
		/**/
		
	}
	public void bind(String arg0, Object arg1) throws NamingException {
		/**/
		
	}
	public void close() throws NamingException {
		/**/
		
	}
	public Name composeName(Name arg0, Name arg1) throws NamingException {
		/**/
		return null;
	}
	public String composeName(String arg0, String arg1) throws NamingException {
		/**/
		return null;
	}
	public Context createSubcontext(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public Context createSubcontext(String arg0) throws NamingException {
		/**/
		return null;
	}
	public void destroySubcontext(Name arg0) throws NamingException {
		/**/
		
	}
	public void destroySubcontext(String arg0) throws NamingException {
		/**/
		
	}
	public Hashtable<?, ?> getEnvironment() throws NamingException {
		/**/
		return null;
	}
	public String getNameInNamespace() throws NamingException {
		/**/
		return null;
	}
	public NameParser getNameParser(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public NameParser getNameParser(String arg0) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<NameClassPair> list(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<NameClassPair> list(String arg0) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<Binding> listBindings(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public NamingEnumeration<Binding> listBindings(String arg0) throws NamingException {
		/**/
		return null;
	}
	public Object lookup(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public Object lookup(String arg0) throws NamingException {
		/**/
		return null;
	}
	public Object lookupLink(Name arg0) throws NamingException {
		/**/
		return null;
	}
	public Object lookupLink(String arg0) throws NamingException {
		/**/
		return null;
	}
	public void rebind(Name arg0, Object arg1) throws NamingException {
		/**/
		
	}
	public void rebind(String arg0, Object arg1) throws NamingException {
		/**/
		
	}
	public Object removeFromEnvironment(String arg0) throws NamingException {
		/**/
		return null;
	}
	public void rename(Name arg0, Name arg1) throws NamingException {
		/**/
		
	}
	public void rename(String arg0, String arg1) throws NamingException {
		/**/
		
	}
	public void unbind(Name arg0) throws NamingException {
		/**/
		
	}
	public void unbind(String arg0) throws NamingException {
		/**/
		
	}
	}
