package es.burke.gotta;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.math.BigDecimal;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;

import es.burke.gotta.ConexionLight.NuloConTipo;

public class DescargaFisica  {
	private ArrayList<String> orden;
	private Usuario usr;
	public DescargaFisica(Motor m) throws ErrorVolcandoDatos {
		try {
			this.orden = m.getEsquema().getOrdenTablas();
			this.usr = m.getUsuario();
			} 
		catch (ErrorArrancandoAplicacion e) {
			throw new ErrorVolcandoDatos(e.getMessage());
			}
		}

	public void descargarTablas(HashMap<String,ITabla> tablas) throws ErrorCampoNoExiste, ErrorVolcandoDatos {
		ITabla t;
		int numTablas = orden.size();
		
		try{
			for (int i=numTablas-1;i>=0;i--) {   
				if ((t = tablas.get(orden.get(i)))!=null){
					if (t instanceof Tabla) 
						descargarTablaBorrados((Tabla)t);
					}
				}
			
			for (int i=0;i<numTablas;i++){
				if ((t = tablas.get(orden.get(i)))!=null) {
					if (t instanceof Tabla){ 
						descargarTablaActualizados((Tabla)t);
						descargarTablaNuevos((Tabla)t);
						}
					}
				}
			}
			catch (SQLException e) {
				throw new ErrorVolcandoDatos(e.toString());
			}
		}
	
	void descargarTablaBorrados(Tabla t) throws SQLException {	
		int posactual=t.getRegistroActual();
		try {
			if (t.borrados.size()>0){
				t.getTablaDef().columnasFisicas(usr.getConexion());
				t.usarCache=false;
				}
			for (int posicion:t.borrados) {
				t.setRegistroActual(posicion);
				SqlParam sp=whereClave(t);
				sp.SQL = "delete from " +t.getTablaDef().getEscritura()+" where "+sp.tempSQL;
				usr.ejecutaDML( sp );
				}
			}
		finally {
			t.setRegistroActual(posactual);
			}
	}
	void descargarTablaActualizados(Tabla t) throws ErrorCampoNoExiste, SQLException {	
		int posactual=t.getRegistroActual();
		try {
			if (t.modificados.size()>0){
				t.getTablaDef().columnasFisicas(usr.getConexion());
				t.usarCache=false;
				}
			for (int posicion:t.modificados) {
				t.setRegistroActual(posicion);
				SqlParam sqlListaUpdates = listaUpdates(t); 
				SqlParam sqlWhereClave= whereClave(t);
				
				ArrayList<Object> param=sqlListaUpdates.params;
				String colsClave=sqlWhereClave.tempSQL; 
				ArrayList<Object> paramClave=sqlWhereClave.params;
				
				String sql = "update " + t.getTablaDef().getEscritura() + " set "+ sqlListaUpdates.tempSQL +" where "+colsClave;
				
				if (sqlListaUpdates.params.size()>0){
					param.addAll(paramClave);
					SqlParam retorno=new SqlParam(sql, param);
					usr.ejecutaDML(retorno);
					}
				}	
		}
		finally
			{t.setRegistroActual(posactual);}

		//return ret;
	}
	void descargarTablaNuevos(Tabla t) throws ErrorCampoNoExiste, SQLException {
		int posactual=t.getRegistroActual();
		try{
			try {
				if (t.nuevos.size()>0) 
					t.getTablaDef().columnasFisicas(usr.getConexion());
				} 
			catch (ErrorConexionPerdida e) {
				/*pass*/}
		
			for (int posicion:t.nuevos) {
				t.setRegistroActual(posicion);
				SqlParam n=listaInserts(t);
				if (n.params.size()>0){
					n.SQL= "insert into "+t.getTablaDef().getEscritura() +"("+n.tempSQL+")" + " VALUES ("+Util.join(",", n.listaInt)+")";
					usr.ejecutaDML(n);
					}
				}
		}
		finally
			{t.setRegistroActual(posactual);}
	}

	private SqlParam whereClave(Tabla t){
		SqlParam retorno=new SqlParam();
		CampoDef cam=t.tdef.getCampoClave();
		
		Iterator<ColumnaDef> eColumnas=cam.getColumnas().elements();
		ColumnaDef col = null;
		ArrayList<String> lista=new ArrayList<String>();
		while (eColumnas.hasNext())
			{
			col = eColumnas.next();
			if (t.getTablaDef().esColumnaFisica(col.getCd()))
				{lista.add(col.getCd()+" = ?");
				retorno.params.add(   t.getValorCol(col.getCd())   );
				}
			}
		retorno.tempSQL=Util.join(" AND ", lista);
		
		return retorno;
	}
	
	private SqlParam listaUpdates(Tabla t) throws ErrorCampoNoExiste{	
		SqlParam retorno=new SqlParam();
		CampoDef cam=t.tdef.getCampoClave();
		Coleccion<ColumnaDef> colsClave=cam.getColumnas();

		ColumnaDef col = null;
		
		ArrayList<String> lista=new ArrayList<String>();
		
		for (String nombre : t.tdef.getColumnas()){
			col=t.tdef.getColumna(nombre);
			if (! colsClave.contains(nombre)){
				if (t.getTablaDef().esColumnaFisica(nombre)){
					if (col.getTipo().equals(Constantes.AUTO)) {
						/*pass*/
						}
					else if (t.colsModif.contains(nombre)){
						lista.add(col.getCd()+" = ? ");
						retorno.params.add( apañaNulos(t.getValorCol(nombre), col.getTipo()) );
						}
					}
				}
			}
		retorno.tempSQL=Constantes.ESPACIO + Util.join(", ", lista);
		
		return retorno;
		}
	
	private SqlParam listaInserts(Tabla t) throws ErrorCampoNoExiste {
		SqlParam retorno=new SqlParam();
		ColumnaDef col = null;
		
		ArrayList<String> listaCols =new ArrayList<String>();

		for (String nombre:t.tdef.getColumnas()){
			col=t.tdef.getColumna(nombre);
			if ( t.getTablaDef().esColumnaFisica(col.getCd()) ) {
				if (col.getTipo().equals(Constantes.AUTO)) {
					retorno.tabla=t;
					retorno.colIdentidad=nombre;
					if(t.getValorCol(nombre)==null)
						t.colsModif.remove(nombre);
					}
				if (t.colsModif.contains(nombre)) {
					listaCols.add(col.getCd());
					retorno.listaInt.add("?");
					retorno.params.add( apañaNulos(t.getValorCol(nombre), col.getTipo()) ); //importeDivisa
					}
				}
			}
		retorno.tempSQL=Util.join(", ", listaCols);
		return retorno;
	}
	private Object apañaNulos(Object valor, String tipo){
		Object ret=valor;
		if ( Util.en(tipo, Constantes.CURRENCY, Constantes.DOUBLE, Constantes.LONG,
						Constantes.INTEGER , Constantes.SINGLE, Constantes.AUTO) ) {
			if ( valor==null || valor.toString().trim().equals(Constantes.CAD_VACIA))
				ret=new NuloConTipo(tipo);
			else{
				if ( !(ret instanceof BigDecimal) )
					return new BigDecimal(ret.toString());
				}
			}
		else if (valor==null || valor.toString().equals(Constantes.CAD_VACIA)) {
			ret = new NuloConTipo(tipo);
			}
		return ret;
	  	}

	private void readObject(ObjectInputStream ois) throws ClassNotFoundException, IOException 
		{ois.defaultReadObject();}
	private void writeObject(ObjectOutputStream oos) throws IOException 
		{oos.defaultWriteObject();}	
}
