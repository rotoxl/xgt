package es.burke.gotta.dll;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lawson.lawsec.authen.httpconnection.LHCConstants;
import com.lawson.lawsec.authen.httpconnection.LHCException;
import com.lawson.lawsec.authen.httpconnection.LawsonHttpConnection;
import com.lawson.lawsec.authen.httpconnection.ResponseObject;

import es.burke.gotta.Accion;
import es.burke.gotta.Conexion;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Filas;
import es.burke.gotta.Fila;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;
import es.burke.misc.AbstractCatalinaTask;

public class Gotta_Lawson extends DLLGotta{
	public String servidor;
	public String usuario;
	public String password;
//	private static String AGS="ags.exe";
//	private static String JOBRUN="jobrun.exe";
	public String tipo=new String();
	public MyTask st=new MyTask();
	
	@Override
	public String accionesValidas() {
		return "ags agsXml dme ida job";
		}

	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta  {
		this.servidor=this.mMotor.getApli().getDatoConfig("servidorlawson");
		if (!this.servidor.toLowerCase().startsWith("http"))
			this.servidor="http://"+servidor;
		this.usuario=this.mMotor.getApli().getDatoConfig("usuariolawson");
		this.password=this.mMotor.getApli().getDatoConfig("passwordlawson");
		this.mMotor.getApli().log.info("Gotta_Lawson servidor="+this.servidor+"; usuario="+usuario+"; password="+password);
		if (this.usuario==null)
			this.usuario=this.mMotor.usuario.getLogin();
		
		if (this.password==null)
			this.password=this.mMotor.usuario.getPassword();
		
		st.setPath("");
		st.setUsername(this.usuario);
		st.setPassword(this.password);
		return this.accionLawson(accion.accion, valor.toString());
        }
	public String accionLawson(String accion, String cgi) throws ErrorAccionDLL{
		String miurl=servidor;
		if (!servidor.toLowerCase().startsWith("http"))
			miurl ="http://"+servidor;
		
		cgi=Util.replaceTodos(cgi," ","%20");
		String result = "-1";
		this.tipo = "string";
		LawsonHttpConnection lhc=null;
		try{
			lhc = new LawsonHttpConnection();
			result = lhc.doLogin(miurl, this.usuario, this.password.toCharArray());
			InputStream i = null;
			String codigo="";
			String msg="";
			
			if (result.equals(LHCConstants.SSO_STATUS_LOGIN_SUCCESS)){
				if (accion.equalsIgnoreCase("ags") && !cgi.startsWith("ags.exe")){
					this.mMotor.getApli().log.info("Gotta_Lawson Lotes");
					result=ejecutaLoteAgs(cgi, lhc);
				}
				else{
					//9-3-2011 vbb504x
						//resto de parametros los consideramos como valores a recoger del xml de vuelta y devolverlos.
						ArrayList<Object> valoresADevolver=new ArrayList<Object>();
						ArrayList<String>params=Util.split(cgi,"|");
						cgi=params.get(0);
						for (int ind=1; ind<params.size(); ind++)
						{
							valoresADevolver.add(params.get(ind));
						}
                   	 this.mMotor.getApli().log.info("Gotta_Lawson ags valores a devolver: "+valoresADevolver.toString());
					//--
					ResponseObject response = null;
					this.mMotor.getApli().log.info("Gotta_Lawson ags="+miurl+cgi);
					response = lhc.doTransaction(miurl+cgi, i, "GET", 60000);
					result = response.getResponseBodyAsString();	
					
					if (accion.equalsIgnoreCase("job")){
						this.mMotor.getApli().log.info("Gotta_Lawson job");
		                 try{
		                     msg = result.substring(result.indexOf("<MESSAGE>")+9,result.indexOf("</MESSAGE>"));
		                     codigo= "000";
		                 }catch(Exception e){
		                     msg = result.substring(result.indexOf("<ERROR>")+7, result.indexOf("</ERROR>"));
		                     codigo= "-1";
		                 }
					}
					else if (accion.equalsIgnoreCase("agsXml")){
						//actualizarVariablesMotor(valoresADevolver, result);
						return result;
						}
					else {
		                int type_out = cgi.indexOf("_OUT=");
		                if (cgi.substring(type_out+5, type_out+8).equalsIgnoreCase("XML")){
		                    codigo = result.substring(result.indexOf("<MsgNbr>")+8, result.indexOf("</MsgNbr>"));
		                    msg = result.substring(result.indexOf("<Message>")+9, result.indexOf("</Message>"));
		                     //9-3-2011 vbb504x
		                    actualizarVariablesMotor(valoresADevolver, result);
		                     //--
		                    
		                } else if (cgi.substring(type_out+5, type_out+9).equalsIgnoreCase("TEXT")){
		                    ArrayList<String> result_list = Util.split(result,",");
		                    int msg_index = 0;
		                    try{
		                        codigo = result_list.get(result_list.size()-2).toString();
		                        msg = result_list.get(result_list.size()-1).toString();
		                        for (int k=0; k<result_list.size(); k++){
		                        	String objetos=result_list.get(k).toString();
		                        	this.mMotor.getApli().log.info("Gotta_Lawson objetos="+objetos);
		                            ArrayList<String> obj = Util.split(objetos," ");
		                            for (int j=0; j<obj.size(); j++){
		                            	String objeto=obj.get(j).toString();
			                        	this.mMotor.getApli().log.info("Gotta_Lawson objeto="+objeto);
		                                if (objeto.equals("000")){
		                                    msg_index = result_list.indexOf(objetos);
		                                    codigo = result_list.get(msg_index).toString();
		                                    msg = result_list.get(msg_index+1).toString();
		                                }
		                            }
		                        }

		                    } catch (Exception e){
		                        codigo = "-1";
		                        msg = "Error obteniendo el mensaje devuelto";
		                    }
		                }
						//codigo=result.substring(result.indexOf("<MsgNbr>")+8,result.indexOf("</MsgNbr>"));
						//msg=result.substring(result.indexOf("<Message>")+9,result.indexOf("</Message>"));
					}
					if(codigo.equals("000"))
						result=codigo;
					else result=codigo+msg;
				}
			}
			
			//lhc.doLogout();
			//return result;
			
		}catch(LHCException e){
			result = "Lawson produjo un error: "+e.getMessage();
			throw new ErrorAccionDLL("Lawson produjo un error: "+e.getMessage()); 
		}
		catch(Exception e){
			e.printStackTrace();
			result="Gotta_Lawson produjo un error: "+e.getMessage();
			throw new ErrorAccionDLL("Gotta_Lawson produjo un error: "+e.getMessage());
		}
		finally {
			if (lhc!=null)
				try{
					lhc.doLogout();
				} catch(Exception e){}
		}
		return result;
	}

	private void actualizarVariablesMotor(ArrayList<Object>valoresADevolver, String result){
        this.mMotor.getApli().log.info("Gotta_Lawson ags Devuelvo valores..."+valoresADevolver.size());
        for (int ind=0; ind<valoresADevolver.size(); ind++){
       	 String nodoIni="<"+valoresADevolver.get(ind).toString()+">";
       	 String nodoFin="</"+valoresADevolver.get(ind).toString()+">";
       	 String nombrevar ="@"+valoresADevolver.get(ind).toString();
       	 String s=result.substring(result.indexOf(nodoIni)+nodoIni.length(),result.indexOf(nodoFin));
       	 this.mMotor.getApli().log.info("Gotta_Lawson ags Devuelvo valor:"+nodoIni+s+nodoFin);
       	 mMotor.tramActivo().lote.setVariable(nombrevar, "string", s);
        }
	}
	
	public String ejecutaLoteAgs(String parametros, LawsonHttpConnection lhc) throws ErrorAccionDLL{
		String miurl=servidor;
		ArrayList<String> params=Util.split(parametros, "|");
		if (params.size()<1) //faltan parámetros
			throw new ErrorAccionDLL("Error en los parámetros. Se han indicado menos parámetros de los esperados");
		String p_documento=params.get(0).toString();
		String p_fecha="";
		if (params.size()==2) p_fecha=params.get(1).toString();
		
		String result="";
		String cd_documento="";
		String ags="";
		String cgi="";
		String documentoActual="";
		ResponseObject respuesta = null;
		boolean errores=false;
		@SuppressWarnings("unused")
		String error="";
		String codigo="";
		String msg="";

		Conexion con= usr.getConexion();
		try {
			this.mMotor.getApli().log.info("Gotta_Lawson Lotes Ejecuto procedimiento...");
			Filas resultadoAGS=con.lookUp("{call JSP_CONTABILIZACION_MASIVA(?,?,?)}",Util.creaLista(p_documento,p_fecha,usr.getLogin()));
			//desde
			for (Fila fila:resultadoAGS)
		  	{
				try{
					cd_documento = fila.get("CD_DOCUMENTO").toString();
					this.mMotor.getApli().log.info("Gotta_Lawson Lotes CD_Documento="+cd_documento);
					ags = fila.get("AGS").toString();
					this.mMotor.getApli().log.info("Gotta_Lawson Lotes AGS="+ags);
				} catch (Exception e){
					//throw new ErrorAccionDLL("Se produjo un error al intentar obtener y lanzar los AGS. "+e.getMessage());
					result="Se produjo un error al intentar obtener y lanzar los AGS. El procedimiento no devolvió los datos esperados";
					break;
				} 
				/* Reemplazamos los espacios por %20 para que no falle el AGS */
				cgi=Util.replaceTodos(ags," ","%20");
				
				/* Si hay errores, se lanza la siguiente fila */
				if (documentoActual.equals(cd_documento) && errores == true) continue;
				else{
					/* Establecemos el documento del procedimiento como documento actual*/
					documentoActual=cd_documento;
					/* Vaciamos las variables de errores*/
					errores = false;
					//error = "";
				}
				
				//--ejecución ags
				try{
					InputStream i = null;
					/* Lanzamos el AGS*/
					respuesta = lhc.doTransaction(miurl+cgi, i, "GET", 60000);
					/* Obtenemos el resultado y lo pasamos a String */
					result = respuesta.getResponseBodyAsString();
					/* Buscamos el codigo en la respuesta del AGS */
					if (result.indexOf("<MsgNbr>") != -1) {
						codigo=result.substring(result.indexOf("<MsgNbr>")+8,result.indexOf("</MsgNbr>"));
						/* Buscamos el mensaje en la respuesta del AGS */
						msg=result.substring(result.indexOf("<Message>")+9,result.indexOf("</Message>"));
						/* Lawson devuelve 000 en el codigo cuando todo ha ido bien 
						¡¡OJO!! Excepto en el JOB	*/
					}
					else{
						//System.out.println("lawson.jsp llamada="+miurl+cgi);
						//System.out.println("lawson.jsp resultado="+result);
	    				if (result.toUpperCase().indexOf("<JOB>GL190</JOB>")!=-1){
							//System.out.println("lawson.jsp job");
							codigo="JOB";
							msg=result.substring(result.toLowerCase().indexOf("<message>")+9,result.toLowerCase().indexOf("</message>"));
						}
					}

					if(codigo.equals("000")){
						result="000";
					}
					else if (codigo.equals("JOB")){
						result="000";
					}
					else{
						/* Si el codigo no es 000, montamos la cadena de error, con un enlace al AGS (para comprobarlo) */
						result="Error ("+codigo+" -- "+msg+") Documento="+cd_documento;
						errores = true;
						error = "Documento="+cd_documento+" Error="+ codigo+": "+msg; //+" http://aries2/cgi-lawson/"+ags;
						break;
						//throw new ErrorAccionDLL("Error en DLL Gotta_Lawson: "+codigo+": "+msg+" http://aries2/cgi-lawson/"+ags);
					}

				} catch( Exception e){
					//result = miurl+cgi+".."+e.getMessage();
					
					throw new ErrorAccionDLL("Error en DLL Gotta_Lawson: ("+miurl+cgi+
							"). El error que se produjo fue:"+e.getMessage());
				}
				//fin ejecución ags
				
		  	}/*for*/		
			/*if (errores){
				throw new ErrorAccionDLL("Error ejecutando DLL Gotta_Lawson: "+error);
			}*/
		} catch (SQLException e) {
			result="Se produjo un error al intentar obtener y lanzar los AGS. "+e.getMessage();
			//throw new ErrorAccionDLL("Se produjo un error al intentar obtener y lanzar los AGS. "+e.getMessage());
		}
		return result;
	} 
	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		/*pass*/
		}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	
	class MyTask extends AbstractCatalinaTask{
		private static final long serialVersionUID = 1L;
		protected String path;
		String datos="";
		
	    public MyTask() {
	        path = null;
	    	}
	    public void setPath(String path) {
	        this.path = path;
	    	}
	    @Override
		public void execute()  {
	        datos=super.execute("");	        
	    	}
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}

}
