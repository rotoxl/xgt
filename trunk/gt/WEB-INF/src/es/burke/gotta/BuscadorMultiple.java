package es.burke.gotta;

import java.util.ArrayList;
import java.util.List;

public class BuscadorMultiple extends Buscador {

	public BuscadorMultiple(Usuario usuario, String nombretabla, int bloqueadas) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion {
		super(usuario, nombretabla, bloqueadas);
	}
	@Override
	public Coleccion<String> buscarPorClave(ArrayList<Object> valores) throws ErrorGotta {
		ArrayList<Object>variables=new ArrayList<Object>();
		int numValores=1;
		try {
			for (int i=0 ; i<valores.size() ; i++) {
				String[] tuplas = Util.noNulo(valores.get(i)).split(",");
				numValores=tuplas.length;
				for (int j=0 ; j<numValores; j++) {
					variables.add( Util.ponTipoCorrecto( tabla.getCampoClave().getColumna(i), tuplas[j] ) );
					}
				}
			}
		catch (NumberFormatException e){
			return null;
			}
		
	  //comenzamos las búsquedas	
		boolean usarColBusqueda; 
		String sqlBSC; Filas filas; int numInterrogantes;
		
	  //1º búsqueda por clave
		usarColBusqueda=false;
		sqlBSC = generaSqlClave( valores.size(), numValores, usarColBusqueda);//false -> búsqueda por clave
		
		numInterrogantes=Util.cuentaCadena(sqlBSC, "?");
		for (int i=valores.size()*numValores; i<numInterrogantes; i++)
			variables.add(usuario.getLogin());

		filas=ejecutar(sqlBSC, variables,500);
		if (filas.size()>0)
			return getValoresBBDD(filas);
		
	  //si no funciona, búsqueda por dic_tablas.colBusq
		usarColBusqueda=false;
		sqlBSC = generaSqlClave( valores.size(), numValores, usarColBusqueda);//false -> búsqueda por clave
		
		filas=ejecutar(sqlBSC, variables,500);
		if (filas.size()>0)
			return getValoresBBDD(filas);

		return null;
		}

	public List<ArrayList<String>> buscarPorClaveMulti(ArrayList<Object> valores) throws ErrorGotta {
		ArrayList<Object>variables=new ArrayList<Object>();
		int numValores=1;
		try {
			for (int i=0 ; i<valores.size() ; i++) {
				Object object = valores.get(i);
				if (object==null)
					object=Constantes.CAD_VACIA;
				String[] tuplas = object.toString().split(",");
				numValores=tuplas.length;
				for (int j=0 ; j<numValores; j++) {
					variables.add( Util.ponTipoCorrecto( tabla.getCampoClave().getColumna(i), tuplas[j] ) );
					}
				}
			}
		catch (NumberFormatException e){
			return null;
			}
		
		//comenzamos las búsquedas	
		boolean usarColBusqueda; 
		String sqlBSC; Filas filas; int numInterrogantes;
		
	  //1º búsqueda por clave
		usarColBusqueda=false;
		sqlBSC = generaSqlClave( valores.size(), numValores, usarColBusqueda);//false -> búsqueda por clave
		
		numInterrogantes=Util.cuentaCadena(sqlBSC, "?");
		for (int i=valores.size()*numValores; i<numInterrogantes; i++)
			variables.add(usuario.getLogin());

		filas=ejecutar(sqlBSC, variables,500);
		if (filas.size()>0)
			return getValoresBBDDm(filas);
		
	  //si no funciona, búsqueda por dic_tablas.colBusq
		usarColBusqueda=false;
		sqlBSC = generaSqlClave( valores.size(), numValores, usarColBusqueda);//false -> búsqueda por clave
		
		filas=ejecutar(sqlBSC, variables,500);
		if (filas.size()>0)
			return getValoresBBDDm(filas);

		return null;
		}

	protected List<ArrayList<String>> getValoresBBDDm(Filas reg)	{
		ArrayList<ArrayList<String>> retLista=new ArrayList<ArrayList<String>>();
		CampoDef crefe=tabla.getCampoClave();
		for(Fila fila:reg){
			ArrayList<String> ret=new ArrayList<String>();
			for (String idColumna:crefe.getColumnas().getOrden()) {
				ret.add(Util.formatoColumna(crefe.getColumna(idColumna), fila.get(idColumna)));
				}
			if (!this.tabla.getColBúsqAuto())
				ret.add(fila.gets(this.getNombrecolbus()));
			ret.add(fila.gets(this.getNombrecoldes()));
			retLista.add(ret);
			}
		return retLista;
		}
	@Override
	protected Coleccion<String> getValoresBBDD(Filas reg)	{
		CampoDef crefe=tabla.getCampoClave();
		Coleccion<ArrayList<String>> ret=new Coleccion<ArrayList<String>>();
		for (String idColumna:crefe) {
			ret.put(idColumna,new ArrayList<String>());
			}
		String nombrecoldes = this.getNombrecoldes();
		ret.put(nombrecoldes,new ArrayList<String>());
		String nombrecolbus = this.getNombrecolbus();
		ret.put(nombrecolbus,new ArrayList<String>());
		for(Fila fila:reg){
			for (String idColumna:crefe) {
				ret.get(idColumna).add(Util.formatoColumna(crefe.getColumna(idColumna), fila.get(idColumna)));
				}
			if (!crefe.getColumnas().containsKey(nombrecoldes))
				ret.get(nombrecoldes).add(fila.gets(nombrecoldes));
			if (!crefe.getColumnas().containsKey(nombrecolbus))
				ret.get(nombrecolbus).add(fila.gets(nombrecolbus));
			}
		Coleccion<String> rett=new Coleccion<String>();
		for(String n:ret.getOrden()){
			rett.put(n, Util.join(",",ret.get(n)));
			}
		return rett;
		}
}
