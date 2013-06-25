package es.burke.gotta.dll;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.activation.URLDataSource;
import javax.mail.internet.AddressException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import org.apache.commons.codec.EncoderException;
//import org.apache.commons.codec.net.URLCodec;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorEvaluador;
import es.burke.gotta.ErrorFaltanDatos;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;
import es.burke.gotta.Variable;
import es.burke.gotta.Aplicacion.DIC_Configuracion;
import es.burke.gotta.Motor.Resultado;

public final class DLLCorreo extends DLLGotta {
//    private static URLCodec urlCodec = new URLCodec("UTF-8");
	Accion accion;
	private String valor(String letra){
		Variable var=mMotor.lote.getVariable(letra);
		if (var==null)
			return null;
		Object valor = var.getValor();
		if (valor==null)
			return null;
		String str=valor.toString();
		if (!str.equals("")) 
			return str;
		return null;
	}

	@Override
	public Object ejecuta(Accion xAccion, Object valor) throws ErrorGotta  {
		this.accion=xAccion;
		
		try {
			return enviarMensaje();
			}
		catch (ErrorAccionDLL e){
			if (this.accion.p4!=null && this.accion.p4!=Constantes.CAD_VACIA )
				return e.getMessage();
			else
				throw e;
			}
		}
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}
	private boolean esVacioONulo(String dato){
		return (dato==null || dato.equals(Constantes.CAD_VACIA));
	}
	
	private ArrayList<String>arreglaYParte(String direcciones){
		direcciones=Util.replaceTodos(direcciones, Constantes.ESPACIO, Constantes.CAD_VACIA);
		direcciones=Util.replaceTodos(direcciones, ";", Constantes.COMA);
		
		ArrayList<String> vAux=Util.split(direcciones,",");
		return vAux;
	}
	public Object enviarMensaje() throws ErrorFaltanDatos, ErrorEvaluador, ErrorAccionDLL, ErrorArrancandoAplicacion{
		String host, port;
		String usu, pass;

		host = this.mMotor.getApli().getDatoConfig(DIC_Configuracion.smtp_server);	
		port = this.mMotor.getApli().getDatoConfig(DIC_Configuracion.smtp_port);//opcional	
		usu =this.mMotor.getApli().getDatoConfig(DIC_Configuracion.smtp_user);
		pass =this.mMotor.getApli().getDatoConfig(DIC_Configuracion.smtp_password);
		 
		if (esVacioONulo(host))
			throw new ErrorFaltanDatos("Error al enviar correo: hay que rellenar en DIC_Configuracion la información referente al servidor de envío de correos (smtp_server)");
		
		String param3=accion.evaluaExpresion(accion.p3);
		GottaMail mail=new GottaMail(host,port, usu,pass);
		
		ArrayList<String> vAux;
		
		boolean usarVariablesLargas=true;
		String from=valor("@DE");
		if (from==null) {
			usarVariablesLargas=false;
			from=valor("p");
			}
		if (from!=null)
			mail.setFrom(from);
		
		String direcciones=null, direccionesCCO=null, direccionesCC=null;
		try {
			direcciones=usarVariablesLargas?valor("@PARA"):valor("q");
			
			if (direcciones==null)
				throw new ErrorAccionDLL("No se ha indicado la dirección de destino");
			
			vAux=arreglaYParte(direcciones);
			for (int i=0;i<vAux.size();i++) {
				String direccion=vAux.get(i).trim();
				if (!direccion.equals("") ) 
					mail.addTo(direccion);
				}
			
			direccionesCCO=valor("@CCO");
			if (direccionesCCO!=null){
				vAux=arreglaYParte(direccionesCCO);
				for (int i=0;i<vAux.size();i++) {
					String direccion=vAux.get(i).trim();
					if (!direccion.equals("") ) 
						mail.addBCC(direccion);
					}
				}
			
			direccionesCC=valor("@CC");
			if (direccionesCC!=null){
				vAux=arreglaYParte(direccionesCC);
				for (int i=0;i<vAux.size();i++) {
					String direccion=vAux.get(i).trim();
					if (!direccion.equals("") ) 
						mail.addCC(direccion);
					}
				}
			
			String asunto=usarVariablesLargas?valor("@ASUNTO"):valor("r");
			if (asunto!=null) 
				mail.setSubject(asunto);
			
			String texto=usarVariablesLargas?valor("@TEXTO"):valor("s");
			if (texto!=null) 
				mail.setText(texto);
	
			String adjuntos=usarVariablesLargas?valor("@ADJUNTOS"):valor("t");

			if (adjuntos!=null) {
				for (String archivo : Util.split(adjuntos,",")) {
					archivo=archivo.trim();
					if (!archivo.equals("")) {
						ArrayList<String> trozo = Util.split(archivo,Constantes.PIPE);
						String adjunto = trozo.get(0);
							DataSource source;
							if(adjunto.startsWith("http")){
								URL url = new URL(adjunto);
								String filename = url.getFile();
//								adjunto=adjunto.replace(filename, urlCodec.encode(filename));
								adjunto=URLEncoder.encode(filename, Constantes.UTF8);
								adjunto=adjunto.replace("%2F", "/");
								url=new URL(adjunto);
								source= new URLDataSource(url);
								}
							else{
								adjunto=Util.rutaFisicaRelativaOAbsoluta(adjunto);
								source= new FileDataSource(adjunto);
								}
							mail.setAttachment(source);
							}
						}
					}
			if (param3==null) {
				/*pass*/}		
			else if (param3.equalsIgnoreCase("sms") || param3.equalsIgnoreCase("text/plain")) 
				mail.tipoCorreo=TipoCorreo.TextoPlano;
			else if (param3.equalsIgnoreCase("text/html")) 
				mail.tipoCorreo=TipoCorreo.HTML;
			else 
				mail.emailConfirmacion=param3;
		
			this.mMotor.getApli().println("enviando correo a ["+direcciones+"], esto puede tardar...");
			mail.enviar();
			this.mMotor.getApli().println("correo enviado");
			
			this.añadeMSG("Correo enviado a ["+direcciones+"]", Constantes.TipoMensajeGotta.correoe);
			return Resultado.OK;
			}
		catch (ErrorAccionDLL e) {
    		if (e.toString().equalsIgnoreCase("es.burke.gotta.ErrorAccionDLL: Illegal character in domain") )
    			throw new ErrorAccionDLL("Falló el envío: " + direcciones + ".");
    		else if (e.toString().equalsIgnoreCase("es.burke.gotta.ErrorAccionDLL: Invalid Addresses")) 
    			throw new ErrorAccionDLL("Falló el envío; dirección no válida: " + direcciones + ".");
    		else 
    			//   throw new ErrorAccionDLL(e.toString());
    			throw e;
		} catch (MalformedURLException e) {
    			throw new ErrorAccionDLL("Falló el envío: "+e.toString());
			} 
		catch (AddressException e) {
			throw new ErrorAccionDLL("Falló el envío; dirección no válida: " + direcciones + ".");
			} 
//		catch (EncoderException e) {
//			throw new ErrorAccionDLL("Falló el envío: "+e.toString());
//			}
		 catch (UnsupportedEncodingException e) {
			throw new ErrorAccionDLL("Falló el envío: "+e.toString());
			}
    	}
	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) 
			throws IOException, ErrorGenerandoProducto  {
		/**/
		}
	
	@Override
	public String accionesValidas() {		
		return "enviarMensaje";
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}

