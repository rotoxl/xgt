package es.burke.gotta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Aplicacion.DIC_Configuracion;

public class ControlLVW extends Control {
	public String nombreNivel=null;
	INivelDef nivelDef; 
	
	public ControlLVW(ControlDef con,Usuario usuario){
		this.usr=usuario;
		this.def=con;
		String caption=this.def.getCaption(this.usr);
		if (caption==null)
			return;
		this.nombreNivel=Util.split(caption, Constantes.PIPE).get(0);
		}

	public JSONObject JSON(NodoActivo nodo, String nivel, int cuantos) throws JSONException, ErrorGotta{
		Filas filas=cargaDatos(nodo, nivel, cuantos);
		JSONObject ret = new JSONObject()
			.put("lvw", this.def.numControl)
			.put("maxElementos", this.usr.maxElementos)
			.put("datos", filas.JSON());
		
		
		//toda el siguiente follón es para meter columnas de búsqueda individuales, según expresa orden de Ayda 
		if (nivel.equals("-") && busquedaPorColumnasIndividuales()){
			ArrayList<Object> v=NodoActivo.nodoALista(nodo.nodoActivo);
			TablaDef tman = (TablaDef) usr.getApli().getEsquema().getTablaDef(v.get(0).toString());
			ArrayList< HashMap<String, Object>> listaNombresVariables=new ArrayList< HashMap<String, Object>>();
			
			for (int i=0; i<tman.getColumnas().size(); i++){
				ColumnaDef col=tman.getColumnas().get(i);
				
				String nombreVar="fil_busqueda_"+col.getCd();
				if (!usr.variables.containsKey(nombreVar) )
					usr.setVariable(nombreVar, (String)null);
					
				String idControl="control_____"+col.getCd();
				if (! usr.getApli().controles.containsKey(idControl) ){
					ControlDef cdef=new ControlDef(this.usr.aplicacion);
					cdef.___setCaption("String "+nombreVar);
					cdef.tc=Constantes.PAR;
					
					this.usr.getApli().controles.put(idControl, cdef);
					}
				
				HashMap<String, Object> el=Util.creaDic("n", idControl, "v", usr.getVariable(nombreVar).getValor() );
				listaNombresVariables.add(el);
				}
			
			ret.put("añadirCamposBúsqueda", listaNombresVariables);
			}
		
		return ret;
		}
	
	private boolean busquedaPorColumnasIndividuales(){;
		boolean ret=false;
		String dc=usr.getApli().getDatoConfig(DIC_Configuracion.tipoMantenimientoTablas);
		if (dc!=null && dc.equals("1"))
			ret=true;
		return ret;
		}
	
	Filas cargaDatos(NodoActivo nodo, String nombreNivel, int limite) throws ErrorRellenandoControl, ErrorDiccionario{	
		INivel nivel=null;
		
		if (nombreNivel.equals("*") || nombreNivel.equals(""))
			nivelDef=usr.getApli().getNivelDef("nivelVacio");	
		else {
			if (nombreNivel.equals("-")) {//Mantenimiento de tablas
				ArrayList<Object> v=NodoActivo.nodoALista(nodo.nodoActivo);
				TablaDef tman = (TablaDef) usr.getApli().getEsquema().getTablaDef(v.get(0).toString());
				try {
					tman.columnasFisicas(this.usr.getConexion());
					} 
				catch (SQLException e) {
					throw new ErrorTablaNoExiste("Error al acceder a las cols físicas de "+tman);
					}
				
				if (busquedaPorColumnasIndividuales()){				
					Coleccion<Object> paramManteTablas=new Coleccion<Object>(true);
					for (int i=0; i<tman.getColumnas().size(); i++){
						ColumnaDef col=tman.getColumnas().get(i);
						String nombrePar="fil_busqueda_"+col.getCd();
						paramManteTablas.put(nombrePar, usr.variables.containsKey(nombrePar)? usr.getVariable(nombrePar).getValor():null);
						}
					
					String sql=tman.sqlMantenimientoTablas(this.usr.getConexion(), paramManteTablas);
					nivelDef=new NivelDef("mantablas", sql,"");
					for (int i=0; i<tman.getColumnas().size(); i++){
						ColumnaDef col=tman.getColumnas().get(i);
						if (paramManteTablas.get("fil_busqueda_"+col.getCd()) !=null)
							nivelDef.colParametros.add( new Parametro( "fil_busqueda_"+col.getCd(), Constantes.STRING));
						}
					}
				else {					
					String fil_busqueda="fil_busqueda";
					Boolean existeCampoBusqueda=this.usr.getVariable(fil_busqueda)!=null;
					String sql=tman.sqlMantenimientoTablas(this.usr.getConexion(), existeCampoBusqueda);

					nivelDef=new NivelDef("mantablas", sql, "");
					for (int i=0; i<Util.cuentaCadena(sql, "?"); i++)
						nivelDef.colParametros.add( new Parametro(fil_busqueda, Constantes.STRING));
					}
				
				}
			else 
				nivelDef=usr.getApli().getNivelDef(nombreNivel);	
			}
		
		nivel=nivelDef.obtenerNivel();
		Filas filas;
		try
			{filas=nivel.lookUp(usr.getConexion(usr.maxElementos), nodo, limite);}
		catch (NumberFormatException e)
			{throw new ErrorRellenandoControl("error rellenando lvw "+nombreNivel+" - ",e);} 
		catch (ErrorGotta e)
			{throw new ErrorRellenandoControl("error rellenando lvw "+nombreNivel+" - ",e);} 
		
		if (!nombreNivel.equals("-")) {//Mantenimiento de tablas
			ArrayList<String> cabecerasTraducidas=usr.getApli().getNivelDef(nombreNivel).cabecera(usr.getIdioma());
			if (cabecerasTraducidas!=null){
				for (int i=0; i<cabecerasTraducidas.size(); i++)
					filas.cambiaNombreColumna(filas.orden.get(i), cabecerasTraducidas.get(i));
				}
			}
		return filas;
		}
}
