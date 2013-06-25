package es.burke.gotta;
 
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import es.burke.gotta.Constantes.MarcaBaseDatos;
import es.burke.gotta.Constantes.TipoMensajeGotta;

public class Buscador {
	protected Usuario usuario;

	public TablaDef tabla;
	public String ds_tabla;
	public int bloqueadas;
	
	private boolean esProcedure=false;
	private INivelDef nivel=null;
	
	public ArrayList<String> listaCols=null;
	public ArrayList<String> listaCabeceras=null;
	
	public Buscador(Usuario usuario, String nombretabla, int bloqueadas) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion {
		Esquema esquema = usuario.getApli().getEsquema();
		this.tabla = (TablaDef) esquema.getTablaDef(nombretabla);
		this.bloqueadas=bloqueadas; 
		this.usuario = usuario;
	}
	public void rellenaListaCols(){
		listaCols=new ArrayList<String>();
		
		for (String nc: this.tabla.getCampoClave().getColumnas())
			listaCols.add(nc);
		
		listaCols.add(this.getNombrecoldes());
		}
		
	protected String generaSqlClave(int valoresQueVienen, int numValores, boolean usarColBusqueda) throws ErrorDiccionario, ErrorArrancandoAplicacion{
//		String sqlSelect="";
        String sqlWhere = "";
        String sqlRet="";
        
        this.bloqueadas=valoresQueVienen;
		esProcedure=false;
        
        CampoDef campoClave = tabla.getCampoClave();
		
        int numColumnas=campoClave.numColumnas();
        	
        Conexion conexion = this.usuario.getConexion();
        
    	listaCols=new ArrayList<String>();
//    	listaCabeceras=new ArrayList<String>();
        ArrayList<String> aWhere=new ArrayList<String>();
        for (int i=0;i<numColumnas;i++ ) {
        	ColumnaDef col;
    		col = campoClave.getColumna(i);
        	listaCols.add(col.getCd());
//        	listaCabeceras.add(col.getDs());
			if(usarColBusqueda && i==numColumnas-1){
        		col=tabla.getColumna(tabla.getColBúsq());
	        	listaCols.add(col.getCd());
//	        	listaCabeceras.add(col.getDs());
        		}
        	else
        		col = campoClave.getColumna(i);
			
    		if ( !(this instanceof BuscadorMultiple )&& Util.en (col.getTipo(),Constantes.tiposTexto) && i==campoClave.numColumnas()-1)
    			//Buscador listillo
    			if(Util.en(this.usuario.getApli().getMarcaBaseDatos(), MarcaBaseDatos.MSSQL,MarcaBaseDatos.MSAccess))
        			aWhere.add(col.getCd()+" like ?");
    			else
    				aWhere.add(conexion.ucase(col.getCd())+" like "+ conexion.ucase("?"));
    		else
    			aWhere.add(col.getCd()+" = ?");
        	}
        
        String nombrecoldes = this.getNombrecoldes();
		CampoDef campo = this.tabla.campos.get(nombrecoldes);
    	if(campo==null)
    		throw new ErrorCampoNoExiste("Rellenando buscador. No existe el campo "+nombrecoldes + " en la tabla "+ this.tabla.getCd());
    	listaCols.add(nombrecoldes);
//		listaCabeceras.add(campo.getColumna(0).getDs());
        
        ArrayList<String> sqlWhereParcial = new ArrayList<String>();
        if (aWhere.size()>0){
            for(int i = 0;i<numValores;i++)
            	sqlWhereParcial.add(Util.join(" AND ", aWhere));
        	sqlWhere="(("+Util.join(") OR (", sqlWhereParcial)+"))";
        	}
        if (tabla.getLectura1()==null){
        	sqlRet="select * from ( "+tabla.getLecturaN()+") xx where "+sqlWhere;
        	}
        else {//usa lectura1
            String sql = this.tabla.getLectura1();
            nivel=this.tabla.getNivelLectura1(usuario);
            if (nivel!=null) {//se trata de un nivel
                sqlRet=nivel.getTexto();
                esProcedure=true;
            	}
            if(!esProcedure) {
	            StringBuffer params=new StringBuffer("");
	            if (!sql.toLowerCase().startsWith("{call "))  {
	            	int hasta = Math.min(campoClave.numColumnas(),valoresQueVienen);
	                for(int j = 0; j < hasta; j++) {
	                    if (j!=0) 
	                        params.append(", ");
	                    params.append("?");
	                }
	                sqlRet="{call " + sql + "(" + params + ")}";
	                }
	            else
	            	sqlRet=sql;	            
        	}
        }
     return sqlRet;
		}
	protected String generaSqlDesc(int numValores) throws ErrorDiccionario, ErrorGotta{
        
//        this.bloqueadas=valoresQueVienen;
		esProcedure=false;
        
        CampoDef campoClave = tabla.getCampoClave();
		
        int numColumnas=0;
    	if (this.bloqueadas==-1)
    		numColumnas=0;
    	else if (this.bloqueadas>-1)
    		numColumnas=this.bloqueadas;
    	else
    		numColumnas=campoClave.numColumnas()-1;
    	
    	this.ds_tabla = tabla.getDs();
    	
        Conexion conexion = this.usuario.getConexion();
        
		String sqlSelect;
		if (tabla.getUsp() != null) {
        	listaCols=null;
        	getTraducciones();
			sqlSelect=tabla.getUsp();
            nivel=usuario.getApli().getNivelDef(tabla.getUsp());
            if (nivel!=null) {//se trata de un nivel
                sqlSelect=nivel.getTexto();
            	}
            esProcedure=true;
            return sqlSelect;
            }
		
    	listaCols=new ArrayList<String>();
    	listaCabeceras=new ArrayList<String>();
        ArrayList<String> aWhere=new ArrayList<String>();
        for (int i=0;i<campoClave.numColumnas();i++ ) {
        	ColumnaDef col = campoClave.getColumna(i);
        	listaCols.add(col.getCd());
        	listaCabeceras.add(col.getDs());
        	if (i<numColumnas){
        		if ( !(this instanceof BuscadorMultiple )&& Util.en (col.getTipo(),Constantes.tiposTexto) && i==campoClave.numColumnas()-1)
        			//Buscador listillo
        			if(Util.en(this.usuario.getApli().getMarcaBaseDatos(), MarcaBaseDatos.MSSQL,MarcaBaseDatos.MSAccess))
            			aWhere.add(col.getCd()+" like ?");
        			else
        				aWhere.add(conexion.ucase(col.getCd())+" like "+ conexion.ucase("?"));
        		else
        			aWhere.add(col.getCd()+" = ?");
        		}
        	} 
    	String nombrecol = this.getNombrecolbus();
    	CampoDef campo = this.tabla.campos.get(nombrecol);
    	if(!listaCols.contains(nombrecol)){
	    	listaCols.add(nombrecol);
			listaCabeceras.add(campo.getColumna(0).getDs());
	    	}
    	nombrecol = this.getNombrecoldes();
		campo = this.tabla.campos.get(nombrecol);
    	if(campo==null)
    		throw new ErrorCampoNoExiste("Rellenando buscador. No existe el campo "+nombrecol + " en la tabla "+ this.tabla.getCd());
    	if(!listaCols.contains(nombrecol)){
	    	listaCols.add(nombrecol);
			listaCabeceras.add(campo.getColumna(0).getDs());
	    	}
        
    	getTraducciones();
    	
        ArrayList<String> sqlWhereParcial = new ArrayList<String>();
        String sqlWhere="";
		if (aWhere.size()>0){
            for(int i = 0;i<numValores;i++)
            	sqlWhereParcial.add(Util.join(" AND ", aWhere));
        	sqlWhere="("+Util.join(") OR (", sqlWhereParcial)+")";
        	}
		
        sqlSelect = "select * from ("+tabla.getLecturaN()+") xx ";
		if (sqlWhere !="")
			sqlWhere=" AND " +sqlWhere;
        sqlWhere= "("+conexion.ucase(getNombrecoldes())+" LIKE ? OR " + conexion.ucase(getNombrecolbus())+" LIKE ? )" + sqlWhere ;  
       
        return sqlSelect+" where "+sqlWhere;
		}

	public boolean esDeColumnaClave(){
		return tabla.getColBúsqAuto();
	}
	private ArrayList<Object> getValores(String campobsc, ArrayList<Object> valores, String sql) throws ErrorFechaIncorrecta, ErrorNumeroIncorrecto{
        ArrayList<Object> ret=new ArrayList<Object>();
    	String paramBsc = "%" + Util.toString(campobsc, Constantes.CAD_VACIA).toUpperCase() + "%";
		ret.add(paramBsc );    
    	if (!this.esProcedure)
    		ret.add(paramBsc );    
    	if (this.bloqueadas >0) {
    		try {
    			for (int i=0; i<this.bloqueadas; i++){
    				if (valores.size()>=this.bloqueadas)
    					ret.add( Util.ponTipoCorrecto(this.tabla.getCampoClave().getColumna(i), valores.get(i)) );
    				else
    					ret.add(null);
    				}
    			
                }
    		catch (ErrorCampoNoExiste e)
    			{/*pass*/}
            }
    	//por último, tantas veces la letra u como haga falta
    	int numInterrogantes=Util.cuentaCadena(sql, "?");
    	
    	if (nivel==null) {
    		while (ret.size()<numInterrogantes)
    			ret.add(this.usuario.getLogin());

    		}
    	else {//si es nivel, los valores salen de las variables (parametros)
    		List<Parametro> cpar= nivel.getColParams();
    		HashMap<String,String> leVan;
    		if(this.usuario.getMotor()!=null && this.usuario.getMotor().tramActivo()!=null && this.usuario.getMotor().tramActivo().nodoActivo!=null)
				// dentro de un trám, el nodo activo es el del  md (flot) que ha lanzado el trám
    			leVan=Util.nodoAHash(this.usuario.getMotor().tramActivo().nodoActivo.nodoActivo);
    		else
    			leVan=Util.nodoAHash(this.usuario.arbol.nodoActivo);
    		for (int i=1+this.bloqueadas;i<numInterrogantes;i++) {
    			Parametro par = cpar.get(i);
    			if(this.usuario.getMotor()!=null && this.usuario.getMotor().lote!=null && this.usuario.getMotor().lote.getVariable(par.nombre)!=null)
    				ret.add(this.usuario.getMotor().lote.getVariable(par.nombre).getValor());
    			else if(this.usuario.getVariable(par.nombre)!=null) 
    				ret.add(this.usuario.getVariable(par.nombre).getValor());
    			else
    				ret.add(leVan.get(par.nombre));
    			}
    		}
        return ret;
	}	
	
	//cuando escriben algo en el campo de busqueda y hacen click en buscar
	public Filas buscarPorDesc(String valorBusqueda, ArrayList<Object> casillasBloqueadas) throws ErrorGotta{	
		String sqlBSC=generaSqlDesc(1);
		ArrayList<Object> parametros=getValores(valorBusqueda, casillasBloqueadas, sqlBSC);
		int numInterrogantes=Util.cuentaCadena(sqlBSC, "?");
		
		while (parametros.size() > numInterrogantes){
			parametros.remove( parametros.size()-1 );
			}
		Filas filas=ejecutar(sqlBSC, parametros,500);
		return filas;
		}

	public Coleccion<String> buscarPorClave(ArrayList<Object> valores) throws ErrorGotta {	
		boolean usarColBusqueda;
		
	//1º Busca convencional
		usarColBusqueda=false;
		CampoDef campoClave = tabla.getCampoClave();
		ITabla t= this.tabla.newTabla(usuario);
		
		try	{
			this.usuario.añadeMSG("intentando cargar por la clave", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			t.cargarRegistros(campoClave, valores);
			if (t.datos.size()==1){
				this.usuario.añadeMSG("1 fila encontrada!", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
				return getValoresBBDD(t.datos);
				}
			}
		catch (ErrorCargandoTabla e){
			this.usuario.añadeMSG("error al intentar cargar por la clave", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			Util.getLog().debug(e.getMessage());
			}
		
	//si no funciona, búsqueda por dic_tablas.colBusq
		try	{
			usarColBusqueda=true;
			CampoDef campoBus;
			if (valores.size()==1)
				campoBus=this.tabla.getCampo(this.getNombrecolbus());
			else {
				String nombreUltimaColumna=campoClave.getColumna(campoClave.numColumnas()-1).getCd();
				
				if (this.getNombrecolbus().equalsIgnoreCase(nombreUltimaColumna) )
					campoBus=campoClave;
				else {
					campoBus = new CampoDef("_", "_", tabla.cd);
					int numColumnas = campoClave.numColumnas();
					for (int i=0;i<numColumnas;i++){
						if(i<numColumnas-1)
							campoBus.putColumna(campoClave.getColumna(i));
						else
							campoBus.putColumna(tabla.getColumna(this.getNombrecolbus()));
						}
					}
				}
			
			this.usuario.añadeMSG("intentando cargar por columna de búsqueda '"+this.getNombrecolbus()+"'", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			t.cargarRegistros(campoBus, valores);				
			
			if (t.datos.size()==1){
				if (this.tabla.getLectura1()==null){
					this.usuario.añadeMSG("1 fila encontrada!", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
					return getValoresBBDD(t.datos);
					}
				
				this.usuario.añadeMSG("1 fila encontrada! obtenemos la clave y buscamos por lectura1", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
				//Cargamos por clave si hay lectura1 por seguridad
				t.cargarRegistros(campoClave, t.getValorCam(this.tabla.clave));
				if (t.datos.size()==1)
					return getValoresBBDD(t.datos);		
				}
			}
		catch (ErrorCargandoTabla e){
			this.usuario.añadeMSG("error al intentar cargar por columna de búsqueda: "+
					e.getCause()!=null?e.getCause().getMessage():e.getMessage(), 
					TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			Util.getLog().debug(e.getMessage());
			}
		
		// si no se encuentra nada, a buscar con mays/minus, coincidencias parciales...
		String sqlBSC = generaSqlClave(valores.size(), 1, usarColBusqueda);//false -> búsqueda por clave
		ArrayList<Object>variables=new ArrayList<Object>();
		String nombreCol = null;
		try {
			int f=Math.max(valores.size(), campoClave.numColumnas());
			for (int i=0 ; i<f ; i++) {
				ColumnaDef columna = (i<campoClave.numColumnas()?campoClave.getColumna(i):null);
				Object valor = valores.get(i);
				if (valor==null)
					return null;
				if (i==valores.size()-1 && usarColBusqueda){
					columna=tabla.getColumna(this.getNombrecolbus());
					}
				if (i >= valores.size()-1){
					if ( i<campoClave.numColumnas() && Util.en( columna.getTipo(),Constantes.tiposTexto) )
						nombreCol=columna.getCd();
//					if (!valor.equals(Constantes.CAD_VACIA))
//						valor=valor.toString()+"%";
					}
				if ( i==campoClave.numColumnas()-1 && Util.en( columna.getTipo(),Constantes.tiposTexto) ){
					if (!valor.equals(Constantes.CAD_VACIA))
						valor=valor.toString()+"%";
					}
					
				variables.add( Util.ponTipoCorrecto( columna, valor ) );
				}
			}
		catch (NumberFormatException e){
			return null;
			}
		
		int numInterrogantes=Util.cuentaCadena(sqlBSC, "?");
		for (int i=valores.size(); i<numInterrogantes; i++)
			variables.add(usuario.getLogin());
			
		Filas filas=null;
		try {
			this.usuario.añadeMSG("intentando buscar coincidencias parciales", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			filas = usuario.getConexion(2).lookUp(sqlBSC, variables,true);
			} 
		catch (SQLException e) {
			this.usuario.añadeMSG("error al buscar coincidencias parciales", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			Util.getLog().debug(e.getMessage());
			}
		if (filas==null){
			//pass
			}
		else if (filas.size()>1 && nombreCol!=null){
			this.usuario.añadeMSG(filas.size()+" filas encontradas! filtramos para devolver 1", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			filas = filas.filtrar(nombreCol, valores.get(valores.size()-1).toString());
			if (filas.size()>0)
				return getValoresBBDD(filas);
			}	
		else if (filas.size()==1||(filas.size()>1 && nombreCol==null)){
			if (filas.size()==1)
				this.usuario.añadeMSG("1 fila encontrada!", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			else
				this.usuario.añadeMSG(filas.size()+" filas encontradas! No hay manera de filtrar, devolvemos la primera", TipoMensajeGotta.buscador, "Tabla "+t.tdef.cd);
			return getValoresBBDD(filas);
			}
			
		return null;
		}

	protected Filas ejecutar(String sql, ArrayList<Object> valores, int cuantos) throws ErrorGotta {
		Filas rs;
		try {
			if(this.nivel!=null){
				INivel n=nivel.obtenerNivel();
				rs=n.lookUp(usuario.getConexion(cuantos),valores);
				}
			else
				rs=usuario.getConexion(cuantos).lookUp(sql, valores,true);
			}
		catch (SQLException e){
			throw new ErrorGotta(e);}
		return rs;
		}

	/*devuelve los valores de la clave+descripcion*/
	protected Coleccion<String> getValoresBBDD(Filas reg) throws ErrorCargandoTabla	{
		String idColumna=null;
		try {
			CampoDef crefe=tabla.getCampoClave();
			Coleccion<String> ret=new Coleccion<String>();
			Fila fila = reg.get(0);
	
			for (int i=0;i<crefe.numColumnas();i++) {
				idColumna=crefe.getColumnas().getOrden().get(i);
				ret.put(idColumna ,Util.formatoColumna(crefe.getColumna(idColumna), fila.get(idColumna)));
				}
			String nombrecoldes = this.getNombrecoldes();
			if(!ret.containsKey(nombrecoldes))
				ret.put(nombrecoldes, fila.gets(nombrecoldes));
			String nombrecolbus = this.getNombrecolbus();
			if(!ret.containsKey(nombrecolbus))
				ret.put(nombrecolbus, fila.gets(nombrecolbus));
			return ret;
			}
		catch (IndexOutOfBoundsException e){
			throw new ErrorCargandoTabla("Se ha producido un error al sacar los valores para rellenar el buscador,"+
					" verifica que de tu usp/lectura1 vengan como mínimo todas las columnas de la clave + la descripción "+
					"(para este caso concreto parece que falta la columna '"+idColumna+"')");
			}
	}
	protected String getNombrecoldes() {
		return this.tabla.getColDesc();
		}
	protected String getNombrecolbus() {
		return this.tabla.getColBúsq();
		}

	private void getTraducciones() throws ErrorGotta {
        // El nivel APP_Buscadores devuelve una fila con dos columnas: 
        //    - la primera contiene la descripción de la tabla
        //    - la segunda, los nombres de las columnas, separadas por "pipes"
        if (usuario.getApli().getEsquema().existeTabla("LEN_Columnas")) {
        	Filas filas=GestorMetaDatos.leeLEN_Columnas(usuario.getConexion(), usuario.getApli().getEsquema(), tabla.getCd(), usuario.getIdioma());
			if (filas.size()==0) return;
        	Fila fila=filas.get(0);
        	listaCabeceras = Util.split(fila.gets("columnas"), Constantes.PIPE);
			this.ds_tabla = fila.gets("DS_Tabla");
        	}	
		}
}
