package es.burke.gotta.dll;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.Motor;
import es.burke.gotta.Nodo;
import es.burke.gotta.NodoActivo;
import es.burke.gotta.URL_Gotta;
import es.burke.gotta.Util;

public class Gotta extends DLLGotta{
	@Override
	public String accionesValidas() {
		return "round sinhora año mes dia día diasemana count reg in sumames sumasegundos modolista mododetalle fecha fechahora hora usuario eof tramite camino finde urlNodo urlNodoPadre urlInternaNodo urlInternaNodoPadre urlAplicacion rutaFisicaAplicacion decimales reset navegar navegarFlotante cerrarFlotantes";
		}
	@Override
	public Object ejecuta(Accion aaccion, Object valor) throws ErrorGotta {
		String accion = aaccion.accion;
		
		if (Util.en(accion, "modoDetalle", "urlInternaNodo", "urlInternaNodoPadre", "urlNodo", "urlNodoPadre")){
			NodoActivo nodoActivo=this.usr.getMotor().tramActivo().tramiteInicial().nodoActivo;
			Nodo nodo=this.usr.arbol.getNodo(nodoActivo.nodoActivo);
			
			if (accion.equalsIgnoreCase("modoDetalle")) 
				return nodoActivo.md.getCd();
			
			if (nodo==null)
				throw new ErrorGotta("No se puede obtener la URL porque este nodo no está en el árbol ¿puede que estes llamando a este trámite desde un flotante?");
				        
			if (accion.equalsIgnoreCase("urlInternaNodo"))
				return nodo.generaURLInterna(usr);
			if (accion.equalsIgnoreCase("urlInternaNodoPadre"))
				return this.usr.arbol.getNodoActivo().rama.nodoPadre.generaURLInterna(usr);
			if (accion.equalsIgnoreCase("urlNodo"))
				return nodo.generaURL(usr);
			if (accion.equalsIgnoreCase("urlNodoPadre"))
				return nodo.rama.nodoPadre.generaURL(usr);
			}
		else if (accion.equalsIgnoreCase("tipoEjecucion"))
			return this.mMotor.tramActivo().tramiteInicial().tipoEjecucion.toString().toLowerCase();
		
		else if (accion.equalsIgnoreCase("urlAplicacion"))
			return usr.getApli().getUrlAplicacion()+"?aplicacion="+usr.getApli().getCd();
		else if (accion.equalsIgnoreCase("rutaFisicaAplicacion"))
			return usr.getApli().getRutaFisicaAplicacion();

		if (accion.equalsIgnoreCase("navegar")){
			if (valor==null) valor=Constantes.CAD_VACIA;
			URL_Gotta nodoArranque = URL_Gotta.crea(valor.toString());
			if (nodoArranque.ml==null)
				nodoArranque.ml=usr.arbol.ml;
			mMotor.lote.setVariable("@", Constantes.STRING, nodoArranque.ml);
			
			usr.arbol.nodoDestino=new NodoActivo(usr, nodoArranque.md, nodoArranque.ml, nodoArranque.letras);
			usr.arbol.nodoDestino.controles=nodoArranque.controles;
			
			usr.getMotor().hayQueRefrescar=true;
			return null;
			}
		if ( accion.equalsIgnoreCase("navegarFlotante")){
			usr.getMotor().lote.listaFlotantes.add(valor.toString());
			return null;
			}
		if (accion.equalsIgnoreCase("cerrarFlotantes")) {
			if (valor==null) 
				valor=-1;
			usr.getMotor().lote.cerrarFlotantes= Integer.parseInt(valor.toString());
			return null;
			}
		
		if (accion.equalsIgnoreCase("round")) {
    		String vvalor=valor.toString();
        	String par4 = aaccion.p4;
    		BigDecimal d = new BigDecimal( mMotor.getEvaluador().evalua(par4).toString() );
    		int ndecimales;
    		
    		if (!vvalor.equals(Constantes.CAD_VACIA))
    			ndecimales = Integer.parseInt(vvalor);
    		else 
    			ndecimales = 0;
    		return Util.redondeo (d, ndecimales);
        	}
        if (accion.equalsIgnoreCase("sinHora")) 
        	return Util.split(valor.toString()).get(0);
        if (accion.equalsIgnoreCase("año")) 
        	return new FechaGotta(valor.toString()).get(Calendar.YEAR);
        	
        if (accion.equalsIgnoreCase("mes")) {
        	FechaGotta gc = new FechaGotta(valor.toString());
        	return gc.get(Calendar.MONTH)+1;
        	}
        if (accion.equalsIgnoreCase("dia") || accion.equalsIgnoreCase("día")) 
    		return new FechaGotta(valor.toString()).get(Calendar.DATE);
        if (accion.equalsIgnoreCase("fecha")) {
        	FechaGotta gc =this.mMotor.fechaHoy();
    		String f = gc.toString();
    		return Util.split(f).get(0).toString();
        	}
        if (accion.equalsIgnoreCase("fechaHora")) {
        	FechaGotta gc =this.mMotor.fechaHoy();
        	if (usr.getApli().getUsarMilis())
            	return gc;
            else {
            	//quitamos milisegundos
            	if ( gc.toString().contains(Constantes.PUNTO) ){
            		String sinMilis=""+gc.toString();
            		sinMilis=sinMilis.substring(0, sinMilis.indexOf(Constantes.PUNTO));
            		gc=new FechaGotta(sinMilis);
            		}
            	return gc;
            	}
        	}
        if (accion.equalsIgnoreCase("hora")) {
        	FechaGotta gc =this.mMotor.fechaHoy();
        	String fecha = gc.toString();
    		return Util.split(fecha).get(1).toString();
        	}
        if (accion.equalsIgnoreCase("usuario")) 
            return mMotor.getUsuario().getLogin();
        if (accion.equalsIgnoreCase("camino")) 
        	return mMotor.tramActivo().tramiteInicial().camino;
        if (accion.equalsIgnoreCase("tramite")) 
            return mMotor.tramActivo().tramiteInicial().tramite;

        if (accion.equalsIgnoreCase("reg")) {
    		ITabla tb = mMotor.tablas.get(valor.toString());
        	return tb.getRegistroActual();
        	}
        if (accion.equalsIgnoreCase("count")) {
        	ITabla tb = mMotor.tablas.get(valor.toString());
        	return tb.getNumRegistrosCargados();
        	}
        if (accion.equalsIgnoreCase("eof")) {
        	ITabla t = mMotor.tablas.get(valor.toString());
        	return new Boolean(t.getRegistroActual()+1>=t.getNumRegistrosCargados());
        	}
        if (accion.equalsIgnoreCase("diasemana")) {
    		FechaGotta gc = new FechaGotta(valor.toString());
        	int dia = gc.get(Calendar.DAY_OF_WEEK)-1;
    		if (dia==0)
    			return 7;
			return dia;
        	}
        if (accion.equalsIgnoreCase("in")) {    		
    		ArrayList<String> v= Util.split(valor.toString().toUpperCase(), Constantes.PIPE);
    		String val = v.get(0);
    		ArrayList<String> valores =Util.split(v.get(1), ",");
    		return valores.contains(val);
        	}
        if (accion.equalsIgnoreCase("sumaMes")){    		
    		ArrayList<String> temp = Util.split(valor.toString(),Constantes.PIPE);
    		FechaGotta f = new FechaGotta(temp.get(0));
    		int numMeses=Integer.parseInt(temp.get(1));
    		
    		f.add(Calendar.MONTH, numMeses);
    		return f;
        	}
        if(accion.equalsIgnoreCase("decimales")||accion.equalsIgnoreCase("reset")) {
        	return "";
        	}
        if (accion.equalsIgnoreCase("sumaSegundos")){
        	ArrayList<String> temp = Util.split(valor.toString(),Constantes.PIPE);
    		FechaGotta f = new FechaGotta(temp.get(0));
    		int numSegs=Integer.parseInt(temp.get(1));
    		
    		f.add(Calendar.SECOND,numSegs);
    		return f;	
        	}
        if (accion.equalsIgnoreCase("finde")) {
    		FechaGotta gc = new FechaGotta(valor.toString());
        	int dia = gc.get(Calendar.DAY_OF_WEEK);
    		return dia==Calendar.SUNDAY||dia==Calendar.SATURDAY;
        	}
        throw new ErrorAccion("Error DLL Gotta, no debería llegar aquí");
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		//
		}
	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return ejecuta(accion, valor);
		}
}
