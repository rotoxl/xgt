package es.burke.gotta.dll;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Aplicacion;
import es.burke.gotta.Archivo;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.Motor;
import es.burke.gotta.TablaTemporal;
import es.burke.gotta.TablaTemporalDef;
import es.burke.gotta.Util;

public class DLLArchivos extends DLLGotta {
	String rutaDescarga;
	Accion accion;
	
	@Override
	public String accionesValidas() {
		return "copiar mover borrar descargar descargaryborrar existe separaNombreyRuta leeArchivoTexto";
	}

	@Override
	public Object ejecuta(Accion accion, Object vValor) throws ErrorGotta {
		Aplicacion apli=this.mMotor.getApli();
		
		this.accion=accion;
		
		String valor=vValor.toString();
		if (accion.accion.equalsIgnoreCase("mover")||accion.accion.equalsIgnoreCase("copiar")){
			// p3=rutaOrigen|rutaDestino
			String rutaOrigen, rutaDestino;
			ArrayList<String> temp=Util.split(valor, Constantes.PIPE);
			rutaOrigen=temp.get(0);
			rutaDestino=temp.get(1);
			try {
				Archivo.copiaArchivo(apli,rutaOrigen, rutaDestino);
				if (accion.accion.equalsIgnoreCase("mover"))
					Archivo.borraArchivo(apli, rutaOrigen);
				usr.añadeMSG("Archivo "+
							(accion.accion.equalsIgnoreCase("mover")?"movido":"copiado")+
							" de '"+rutaOrigen+"' a '"+rutaDestino+"' OK", Constantes.TipoMensajeGotta.archivos);
				} 
			catch (Exception e) {
				throw new ErrorAccionDLL("Error al " + accion + " el archivo '"+rutaOrigen+"' a '"+rutaDestino+"': ",e);
				}
			}
		else if (accion.accion.equalsIgnoreCase("borrar")){
			//	p3=ruta
			try {
				Archivo.borraArchivo(apli, valor);
				usr.añadeMSG("Archivo '"+valor+"' borrado", Constantes.TipoMensajeGotta.archivos);
				}
			catch (Exception e) {
				throw new ErrorAccionDLL("Error al borrar el archivo '"+valor+"': "+e.getMessage());
				}
			}
		else if (accion.accion.equalsIgnoreCase("existe")){
			String ruta=Util.rutaFisicaRelativaOAbsoluta(valor.toString());
			return Archivo.existeArchivo(ruta);
			}
		else if (accion.accion.equalsIgnoreCase("descargar") || accion.accion.equalsIgnoreCase("descargaryborrar")){
			this.rutaDescarga=Util.rutaFisicaRelativaOAbsoluta(valor.toString());
			if (!Archivo.existeArchivo(this.rutaDescarga))
				throw new ErrorGotta("No existe el archivo '"+this.rutaDescarga+"'");
			usr.productosGenerados.put(""+this.hashCode(), this);
			}
		else if (accion.accion.equalsIgnoreCase("separaNombreyRuta")){
			ArrayList<String> n=Archivo.separaNombreyRuta(valor);
			
			ArrayList<String> camposVariables=Util.split(this.accion.p4, Constantes.ESPACIO);
			for (int i=0; i<camposVariables.size(); i++){
				if (i>=2)break;
				
				String campoVariable=camposVariables.get(i);
				String valorAAsignar = n.get(i);
				
				if (this.mMotor.esCampo_Variable(campoVariable))
					this.mMotor.setValorSimbolo(campoVariable, valorAAsignar);
				}
			}
		else if (accion.accion.equalsIgnoreCase("leeArchivoTexto")){//#tablaDestino|ruta
			ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
			
			this.generaTablaTemporalParaArchivo(temp.get(0), temp.get(1));
			}
		return Motor.Resultado.OK;
		}
	private void generaTablaTemporalParaArchivo(String nombreTabla, String r) throws ErrorGotta {
		String ruta=Util.rutaFisicaRelativaOAbsoluta(r);
		Archivo ar=new Archivo(usr, ruta);
		if (!ar.existeArchivo())
			throw new ErrorAccionDLL("El archivo '"+ar.rutaFisica+"' no existe.");
		
		TablaTemporalDef tdef=montaTabla(nombreTabla, ruta); 
		TablaTemporal t = (TablaTemporal)tdef.newTabla(usr);
		this.mMotor.tablas.put(nombreTabla, t);
		
		Filas filas= t.datos;

		String texto=ar.leeArchivoTexto();
		ArrayList<String> temp=Util.split(texto, Constantes.vbCrLf);
		for (int i=0; i<temp.size(); i++){
			Fila fila=new Fila(filas);

			fila.put("_ruta", ruta);
			fila.put("_num", i);
			fila.put("texto", temp.get(i));
			
			filas.add(fila);
			}
		}
	private TablaTemporalDef montaTabla(String nombreTabla, String ruta){
		TablaTemporalDef tdef=new TablaTemporalDef(nombreTabla, ruta);
		
		tdef.columnas.put("_ruta", new ColumnaDef("_ruta", "_ruta", Constantes.STRING, 2500, null));
		tdef.columnas.put("_num", new ColumnaDef("_num", "_num", Constantes.LONG, 10, null));
		tdef.columnas.put("texto", new ColumnaDef("texto", "texto", Constantes.STRING, 2500, null));
		
		tdef.generaCamposDesdeColumnas();
		
		return tdef;
		}	
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		String miArchivo=Util.nombreWeb(this.rutaDescarga);
		
		InputStream fis=new FileInputStream(this.rutaDescarga);
		Archivo.descargaFichero(res, miArchivo, fis);
		
		if (this.accion.accion.equalsIgnoreCase("descargarYborrar")){
			Archivo.borraArchivo(this.mMotor.getApli(), this.rutaDescarga);
			this.añadeMSG("Archivo descargado desde ["+this.rutaDescarga+"] y borrado", Constantes.TipoMensajeGotta.archivos);
			}
		else
			this.añadeMSG("Archivo descargado desde ["+this.rutaDescarga+"]", Constantes.TipoMensajeGotta.archivos);
		}	

	@Override
	public void verifica(Motor motor, String accion, String nombre)throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}
