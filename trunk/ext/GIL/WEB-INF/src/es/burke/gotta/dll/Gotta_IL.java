package es.burke.gotta.dll;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class Gotta_IL extends DLLGotta {

	@Override
	public String accionesValidas() {
		return "recortar redimensionar escalaDeGrises";
		}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		this.mMotor=motor;
		
		if(!(Constantes.ESPACIO+accionesValidas().toLowerCase()+Constantes.ESPACIO).contains(Constantes.ESPACIO+accion.toLowerCase()+Constantes.ESPACIO))
			throw new ErrorAccionDLL("No existe la accion "+accion+ " en la clase "+this.getClass().toString()+". Las acciones disponibles son: "+accionesValidas());
		}
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		String acc=accion.accion;
		
		ArrayList<String> temp = Util.split(valor.toString(), Constantes.PIPE);
		String rutaOrigen=temp.get(0);
		String rutaDestino=temp.get(1);
		
		ArrayList<String> nOrigen = Archivo.separaNombreyRuta(rutaOrigen);
		if (!rutaDestino.contains(Constantes.SEPARADOR))//si el destino no lleva ruta, entonces al mismo directorio
			rutaDestino=nOrigen.get(1)+Constantes.SEPARADOR+rutaDestino;
		
		GIL gil=new GIL(mMotor.usuario);
		BufferedImage bin=gil.leerImagen(rutaOrigen);
		BufferedImage bout=null;
		
		ArrayList<Integer> pos, tam;
		
		if (acc.equalsIgnoreCase("escalaDeGrises")){//original|resultado 	
			bout=gil.escalaDeGrises(bin);
			}
		else if (acc.equalsIgnoreCase("recortar")){//original | resultado | 0x0 | 100x200
			pos=separaTam(temp.get(2));
			tam=separaTam(temp.get(3));
			
			bout=gil.recortar(bin, pos.get(0), pos.get(1), tam.get(0), tam.get(1));
			}
		else if (acc.equalsIgnoreCase("redimensionar")){//original | resultado | 100x200
			tam=separaTam(temp.get(2));
			
			ArrayList<Integer>dimensiones=gil.calculaAnchoAltoProporcional(bin, tam.get(0), tam.get(1));
			bout=gil.cambiarTamanho(bin, dimensiones.get(0), dimensiones.get(1));
			}
		
		try {
			gil.guardarImagen(bout, rutaDestino);
			} 
		catch (IOException e) {
			throw new ErrorAccionDLL(e.getMessage());
			}
		return null;
		}
	private ArrayList<Integer> separaTam(String tam){
		ArrayList<Integer> ret= new ArrayList<Integer>();
		ArrayList<String> temp = Util.split(tam.toLowerCase(), "x");
		
		for (int i=0;i<2; i++){
			if (temp.get(i).equals(Constantes.CAD_VACIA))
				ret.add((Integer)null);
			else
				ret.add( Integer.parseInt(temp.get(i)));
			}
		
		return ret;
		}
	
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {
	}

}
