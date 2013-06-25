package es.burke.gotta.dll;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import es.burke.gotta.CampoDef;
import es.burke.gotta.Conexion;
import es.burke.gotta.Constantes;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorConexionPerdida;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Filas;
import es.burke.gotta.INivelDef;
import es.burke.gotta.Motor;
import es.burke.gotta.Parametro;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;

public class ServletDatosWordApplet extends HttpServlet {
	private static final long serialVersionUID = -5446796987041841783L;

	Usuario usr;
	Motor motor;
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException  {	
		Object rs;
		try {
			rs = respuesta(req);
			}
		catch (ErrorGotta e) {
			ArrayList<Object> rse=new ArrayList<Object>();

			rse.add(e.toString());
			rs=rse;
			}
		ObjectOutputStream outputToApplet;
		outputToApplet = new ObjectOutputStream(res.getOutputStream());
		outputToApplet.writeObject(rs);
		outputToApplet.flush();
		outputToApplet.close();
		}
	private Object respuesta(HttpServletRequest req) throws ErrorGotta{
		//para pedir las filas de un nivel o una tabla
		String nombreNivel = req.getParameter("nivel");
		
		//para pedir el valor de un campo o variable, con su formato
		String expresion = req.getParameter("expresion");
		String formato = req.getParameter("formato");
		
		//para añadir algo al monitor de la aplicación
		String log=Util.obtenValorOpcional(req, "log");
		
		String contadoc = req.getParameter("contadoc");
        
		String parametro = req.getParameter("parametro");
		String valor = req.getParameter("valor");
		
		try	{
			this.usr = Usuario.desdeSesion(req);
			}
		catch (ErrorConexionPerdida e){
			return getModoPruebaFusion(nombreNivel, expresion);
			}
		catch (ErrorArrancandoAplicacion e){
			return getModoPruebaFusion(nombreNivel, expresion);
			}
		
		WordCliente producto = (WordCliente)usr.productosEnviados.get(contadoc);
		this.motor = producto.mMotor;

        if (parametro!=null){
			motor.lote.setVariable(parametro, Constantes.STRING, valor);
			return null;
			}

        if (log!=null)
        	this.añadeMSG(log, null);
        
		if (nombreNivel!=null)
			return getFilas(nombreNivel);
		else if (expresion!=null && formato!=null) 
			return getExpresion(expresion, formato);
		
		throw new ErrorGotta("Petición desconocida");
		}
	
	private Object getModoPruebaFusion(String nombreNivel, String expresion){
		// MODO PRUEBA DE FUSIÓN
		ArrayList<Object> resultadoSimple=new ArrayList<Object>();
		ArrayList<HashMap<String,Object>> resultadoComplejo=new ArrayList<HashMap<String,Object>>();
		
		if (expresion!=null){
			resultadoSimple.add("##"+expresion+"##");
			return resultadoSimple;
			}
		else if (nombreNivel!=null){
			HashMap<String, Object> fila = new HashMap<String,Object>();
			fila.put("cd", "##1##");
			fila.put("ds", "##uno##");
			resultadoComplejo.add(fila);
			fila = new HashMap<String,Object>();
			fila.put("cd", "##2##");
			fila.put("ds", "##dos##");
			resultadoComplejo.add(fila);
			return resultadoComplejo;
			}
		else
			return null;
	}
	private Object getFilas(String nombreNivel) throws ErrorGotta {	
		if (nombreNivel.contains(".")){
			CampoDef campodef = motor.tablaCampo(nombreNivel);
			Object ret=motor.tablas.get(campodef.getTabla()).datos.toList();
			this.añadeMSG("Valor devuelto: "+ret.toString(), nombreNivel);
			return ret;
			}
		else {
			INivelDef nivelDef = usr.getApli().getNivelDef(nombreNivel);
			if (nivelDef==null) nivelDef=usr.getApli().getNivelDef("dic_configuracion");
			Conexion con = usr.getConexion();
			ArrayList<Object> params = new ArrayList<Object>();
			for (Parametro par : nivelDef.getColParams()){
				String nombre = par.getNombre();
				params.add(motor.getValorSimbolo(nombre).get(0));
				}
			Filas filas=null;
			try {
				filas = nivelDef.obtenerNivel().lookUp(con, params);
				}
			catch (Exception e) {
				String sparams="";
				for (Object p:params)
					sparams+=", "+p.toString();
				sparams=sparams.substring(1);
				
				String msg=e.getMessage()+". El error se ha producido al calcular el nivel '"+nombreNivel+"' con los parámetros ("+sparams+")";
				this.añadeMSG(msg, nombreNivel);
				throw new ErrorGotta(msg);
				}
			return filas.toList();
			}
	}
	private Object getExpresion(String expresion, String formato) {
		String valor; 
		ArrayList<Object> resultadoSimple=new ArrayList<Object>();
		
		try {
			Object exp;
			if (motor.esCampo_Variable(expresion)){
				String tipoDato;
				ArrayList<Object> vs = motor.getValorSimbolo(expresion);
				exp=vs.get(vs.size()-1);
				if (exp == null)
					valor = "";
				else {
					if (motor.esCampo(expresion))
						tipoDato=motor.tablaCampo(expresion).getColumna(0).getTipo();
					else
						tipoDato=motor.lote.getVariable(expresion).getTipo();
					
					if (formato.equals("NLF") || formato.equals("NLE") || formato.equals("NLM")){
						//pass
						}
					else if (tipoDato.equals(Constantes.CURRENCY))
						exp=Util.formatearMoneda(exp.toString());
					else if (tipoDato.equals(Constantes.INTEGER))
						exp=Util.formatearNumero(exp.toString(), 0);
					else if (Util.esTipoNumerico(tipoDato))
						exp=Util.formatearNumero(exp.toString(), 2);
					}
				}
			else 
				exp = motor.eval.evalua(expresion);
			valor = Util.formateaWord(exp,formato);				
			
			this.añadeMSG("Valor devuelto: "+exp, expresion);
			}
		catch (Exception e) {
			valor=e.toString();
			
			this.añadeMSG("Error: "+valor, expresion);
			}
		
		resultadoSimple.add(valor);
		return resultadoSimple;
		}
	private void añadeMSG(String msg, String objetoSQL){
		if (msg.contains("::")){
			ArrayList<String> temp=Util.split(msg, "::");
			objetoSQL=temp.get(0);
			msg=temp.get(1);
			}
		usr.añadeMSG(msg, TipoMensajeGotta.informe, objetoSQL);
		}
	}

