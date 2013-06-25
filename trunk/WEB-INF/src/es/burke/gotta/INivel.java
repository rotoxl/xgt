package es.burke.gotta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public abstract class INivel {
	public String cd;
	public INivelDef nivelDef; // nivelSQL, es.burke.gotta.Nivel, por defecto 
	public Usuario usuario;
	
	//para ejecutar niveles del árbol, sacando la info de un nodo
	public Filas lookUp(Conexion con, Usuario usr, Nodo nodo, int cuantas) throws ErrorGotta{
		return lookUp(con, usr, nodo!=null?nodo.cd:null, cuantas, 1);
		}
	public Filas lookUp(Conexion con, NodoActivo na, int cuantas) throws ErrorGotta{
		return lookUp(con, na.usr, "@"+na.ml.getCd()+Constantes.SEPARADOR+(na.nodoActivo!=null?na.nodoActivo:Constantes.CAD_VACIA), cuantas, na.numPagina);
		}
	private Filas lookUp(Conexion con, Usuario usr, String nodo, int cuantas, int numPag) throws ErrorGotta{
		this.usuario=usr;
		HashMap<String,String> leVan=Util.nodoAHash(nodo);
		
		String par="@numPagina";
		for (Parametro xp : this.nivelDef.colParametros){
			if (xp.nombre.equalsIgnoreCase(par)){
				leVan.put(par, ""+numPag);
				break;
				}
			}
		List<Object> valores=this.getValores(null, leVan);

		try {
			return lookUpBase(con, valores, cuantas);
			}
		catch (Exception e) {
			throw new ErrorGotta("Error al ejecutar nivel",e);
			} 
	}
	//para ejecutar niveles del "sistema", tipo APP_ControlesModoDetalle
	public Filas lookUp(ConexionLight con, List<Object> valores) throws ErrorGotta, SQLException{
		return lookUpBase(con, valores, 0);
		}
	
	//	función genérica
	public abstract Filas lookUpBase(ConexionLight con, List<Object> valores, int limite) throws SQLException, ErrorGotta;

	public List<Object> getValores(HashMap<String,Variable> letrasArbol, HashMap<String,String> leVienen) {
		ArrayList<Object> listaValores=new ArrayList<Object>();
		List<Parametro> colParams = this.nivelDef.getColParams();//keys();
		if (colParams==null)
			return null;
		Iterator<Parametro> i=colParams.iterator();
		Object valor;	
			
		while (i.hasNext()) {
			Parametro param = i.next();
			String nombrepar = param.getNombre();

			if (letrasArbol!=null && letrasArbol.containsKey(nombrepar) )
				valor=letrasArbol.get(nombrepar).getValor();
			else if (leVienen!=null && leVienen.get(nombrepar)!=null){
				String contenido = leVienen.get(nombrepar).toString();
				ArrayList<Object> a = Util.splitObjetos(contenido,"\\");
				valor = a.get(0);
				}
			else if (usuario.getVariable(nombrepar)!=null){
				Variable var = usuario.getVariable(nombrepar);
				 if (var.getValor()!=null) {
					Object oValor;
					if (var.getValor() instanceof ArrayList<?>) {
						ArrayList<?> lValor=(ArrayList<?>) var.getValor();
						if (lValor.size()!= 0)
							oValor=lValor.get(0);
						else
							oValor=null;
						}
					else
						oValor=var.getValor();
					
					valor = oValor;										
					}
				else valor = null;
				}
			else
				valor = null;
			listaValores.add(valor);
		}
		return listaValores;
	}
}