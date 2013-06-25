package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorEvaluador;
import es.burke.gotta.ErrorFaltanDatos;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ErrorLDAP;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class GottaLDAP extends DLLGotta{	
	@Override
	public String accionesValidas() {
		return "creaUsuario modificaUsuario eliminaUsuario validaUsuario extraeDato";
		}
	public static String evaluaONulo(Accion accion, String letra){
		try {
			String ret= accion.evaluaExpresion(letra);
			if (ret.equals(""))
				return null;
			else
				return ret;
			} 
		catch (ErrorEvaluador e) {
			return null;
			}
		}
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorLDAP, ErrorFaltanDatos{
		LDAP gl= new LDAP(this.usr);
		String acc=accion.accion;
		
		if (	acc.equalsIgnoreCase("creaUsuario") ||
				acc.equalsIgnoreCase("modificaUsuario")  ) {
			//	p=idUsuario, q=DS_Usuario, r=email, s=telf., t=[grupo, grupo,]
			//		u=contraseña
			
			ArrayList<String> ao=new ArrayList<String>();
			
			String CD_Usuario=evaluaONulo(accion, "p"); //cmolina
			String DS_Usuario=evaluaONulo(accion, "q"); //Carlos Molina Carrón
			String email=evaluaONulo(accion, "r"); // 
			String telefono=evaluaONulo(accion, "s");
			
			String grupos=evaluaONulo(accion, "t");
			String contraseña=evaluaONulo(accion, "u");
			for (String gr : grupos.split(Constantes.COMA)) 
				ao.add(gr);
			
			if (acc.equalsIgnoreCase("creaUsuario") )
				gl.insertaUsuario(CD_Usuario, contraseña, DS_Usuario, email, telefono, ao);
			else
				gl.modificaUsuario(CD_Usuario, contraseña, DS_Usuario, email, telefono, ao);
			}
		else if (acc.equalsIgnoreCase("eliminaUsuario")){
			String CD_Usuario=evaluaONulo(accion, "p"); //cmolina
			gl.eliminaUsuario(CD_Usuario);
			}
		else if (acc.equalsIgnoreCase("extraeDato")){
			ArrayList<String>temp= Util.split(valor.toString(), Constantes.PIPE);
			String uid=temp.get(0), idProp=temp.get(1);
			return gl.extraeDato(uid, idProp);
			}
		else {//validaUsuario 
			String usuario=evaluaONulo(accion, "p"); //cmolina
			String contraseña=evaluaONulo(accion, "q"); //contraseña
			
			return gl.validaUsuario(usuario, contraseña);
			}
		return true;
		}
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		// pass	
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}