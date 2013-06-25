package es.burke.gotta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TablaDef extends ITablaDef{
	private String lectura1;
	private String lecturaN;
	private String escritura;
	private String colDesc;
	private String colBúsq;

	private String usp;

	private ArrayList<String> colFisicas=null;

	public TablaDef(String cd, String ds, String lectura1, String lecturaN, String escritura, 
			String clave, String usp, Integer caminoMantenimiento, String colDesc, String colBúsq) {
		this.cd=cd;
		this.ds=ds;
		this.caminoMantenimiento=caminoMantenimiento;
		this.lectura1=lectura1;
		this.escritura=escritura;
		this.lecturaN=lecturaN;
		this.colDesc=colDesc;
		this.colBúsq=colBúsq;
		this.usp=usp;
		this.clave=clave;
		}
	public ArrayList<String> columnasFisicas(Conexion conex) throws SQLException {
		if (this.colFisicas == null){
			synchronized (this) {
				if (this.colFisicas == null) {//por si las conds de carrera
					ArrayList<String> ret = new ArrayList<String>();
					if (this.escritura==null||this.escritura.equals(Constantes.CAD_VACIA)){
						/* pass */ }
					else {
						String sql="select * from "+escritura+" where 1=0";
						Filas rs=conex.lookUp(sql);
						for (String s : rs.getOrden()) {
							if ( this.columnas.containsKey(s) )
								ret.add( s.toLowerCase() ); 
							}
						}
					this.colFisicas=ret;
					}
				}
			}
		return this.colFisicas;		
		}
	
	@SuppressWarnings("unused")
	public void arreglaLectura1() {
		if (lectura1 != null && !lectura1.contains("?")) {
			String temp=lectura1;
			ArrayList<String> i=new ArrayList<String>();
			try {			
				for (String col : this.getCampoClave().getColumnas())
					i.add("?");
				lectura1="{call " + temp + "(" + Util.join(",", i) + ")}";
				}
			catch (Exception e) {
				System.out.println("Error arreglando lectura1("+temp+") de la tabla "+this.cd);}
			}
		}
	public boolean esColumnaFisica(String col) {
		assert colFisicas != null;
		return colFisicas.contains(col.toLowerCase());
		}

	public String sqlMantenimientoTablas(Conexion con) throws ErrorCampoNoExiste {
		return sqlMantenimientoTablas(con, false);
		}
	public String sqlMantenimientoTablas(Conexion con, Boolean campoBusqueda) throws ErrorCampoNoExiste {
		String tempclave="";
		
		CampoDef cDefClave=this.getCampoClave();
		
		if (cDefClave.numColumnas()>1) {			
			ArrayList<String> total = new ArrayList<String>();
			ColumnaDef colDef = null;
			for (int i=0;i<cDefClave.numColumnas() ;i++ ) {
				colDef = cDefClave.getColumna(i);
				total.add( con.fnCastAsChar(colDef.getCd(), colDef.getTipo()) );
				if(i < cDefClave.numColumnas()-1)
					total.add("'·'");
				}
			String [] lista = new String[total.size()];
			for(int i=0; i<total.size(); i++) {
				lista[i]=total.get(i);
				}
			tempclave=con.fnConcat(lista)+" as \"key\"";
			}
		String columnastabla="";
		String where="";
		Coleccion<ColumnaDef> eColumnas=this.getColumnas();
		eColumnas.reOrdenarClaves(this.colFisicas);
		
		ColumnaDef cDef=null;
		String nomColumna=null;
		for (int i=0; i<eColumnas.size(); i++) {
			String formatoSalida=Constantes.CAD_VACIA;
			cDef=eColumnas.get(i);
			nomColumna=cDef.getCd();
			
			if (!this.esColumnaFisica(nomColumna))
				continue;
			
			if (cDef.getTipo().equals(Constantes.BOOLEAN))
				formatoSalida="\\b";
			else if (Util.en(cDef.getTipo(), Constantes.tiposEnteros))
				formatoSalida="\\0";
			
			columnastabla+=Constantes.ESPACIO+nomColumna+" AS \""+cDef.getDs()+formatoSalida+"\",";
			where+=Constantes.ESPACIO+con.fnCastAsChar(nomColumna)+" LIKE "+ con.fnConcat("'%'", "?", "'%'")+" OR";
			}
		String sql = "Select "+columnastabla+" 'K·' as mododetalle, ";
		where+= " ? IS NULL";
		
		if (cDefClave.numColumnas()==1)
			sql+=cDefClave.getCd()+" as \"key\" from ("+this.getLecturaN()+") zz";
		else
			sql+=tempclave+" from ("+this.getLecturaN()+") zz";
		
		if (campoBusqueda)
			sql+=" WHERE "+where;
		return sql;
		}
	public String sqlMantenimientoTablas(Conexion con, Coleccion<Object>listaParam) throws ErrorCampoNoExiste {
		String tempclave="";
		
		CampoDef cDefClave=this.getCampoClave();
		
		if (cDefClave.numColumnas()>1) {			
			ArrayList<String> total = new ArrayList<String>();
			ColumnaDef colDef = null;
			for (int i=0;i<cDefClave.numColumnas() ;i++ ) {
				colDef = cDefClave.getColumna(i);
				total.add( con.fnCastAsChar(colDef.getCd(), colDef.getTipo()) );
				if(i < cDefClave.numColumnas()-1)
					total.add("'·'");
				}
			String [] lista = new String[total.size()];
			for(int i=0; i<total.size(); i++) {
				lista[i]=total.get(i);
				}
			tempclave=con.fnConcat(lista)+" as \"key\"";
			}
		
		String columnastabla="";
		String where="";
		Coleccion<ColumnaDef> eColumnas=this.getColumnas();
		eColumnas.reOrdenarClaves(this.colFisicas);
		
		ColumnaDef cDef=null;
		String nomColumna=null;
		for (int i=0; i<eColumnas.size(); i++) {
			String formatoSalida=Constantes.CAD_VACIA;
			cDef=eColumnas.get(i);
			nomColumna=cDef.getCd();
			
			if (!this.esColumnaFisica(nomColumna))
				continue;
			
			if (cDef.getTipo().equals(Constantes.BOOLEAN))
				formatoSalida="\\b";
			else if (Util.en(cDef.getTipo(), Constantes.tiposEnteros))
				formatoSalida="\\0";
			
			columnastabla+=Constantes.ESPACIO+nomColumna+" AS \""+cDef.getDs()+formatoSalida+"\",";
			
			Object s=listaParam.get("fil_busqueda_"+nomColumna);
			if (s!=null)
				where+=Constantes.ESPACIO+"("+con.fnCastAsChar(nomColumna)+" LIKE "+ con.fnConcat("'%'", "?", "'%'")+") AND";
			}
		String sql = "SELECT "+columnastabla+" 'K·' as mododetalle, ";
		
		if (cDefClave.numColumnas()==1)
			sql+=cDefClave.getCd()+" AS \"key\" FROM ("+this.getLecturaN()+") zz";
		else
			sql+=tempclave+" FROM ("+this.getLecturaN()+") zz";
		
		if (! (where.equals(Constantes.CAD_VACIA)))
			sql+=" WHERE "+ where.substring(0, where.length()-3);
		return sql;
		}

//////////////////////////	
	public String sqlCargarTabla(CampoDef pcampo, List<Object> valor) throws ErrorCampoNoExiste{
		return sqlCargarTabla(pcampo,valor,null);
		}
	public String sqlCargarTabla(CampoDef pcampo, List<Object> valor, CampoDef camOrderBy) throws ErrorCampoNoExiste{
		CampoDef campo=pcampo;
		if (campo==null)
			campo=this.getCampoClave();
		
		//lectura1: sólo cuando es con el campo clave
		if (campo==this.getCampoClave()	&& this.lectura1!=null && campo.getColumnas().size() == valor.size())
			return this.lectura1;
		
		ArrayList<String> wh=new ArrayList<String>();
		
		int limite;
		if (campo.getColumnas().size()>valor.size())
			limite=valor.size();
		else
			limite=campo.getColumnas().size();
		
		for(int i=0; i<campo.getColumnas().size(); i++){
			if (i<limite)
				wh.add(campo.getColumna(i).getCd() + " = ? ");
			}
		
		if(camOrderBy==null)camOrderBy=campo;
		String sql = "select * from ("+this.lecturaN+") n ";
		String sOrderBy;
		int indexOfOrderBy = sql.toLowerCase().indexOf("order by");
        if (indexOfOrderBy == -1){    	
			ArrayList<String> orderBy=new ArrayList<String>();
			for(int i=0; i<camOrderBy.getColumnas().size(); i++) 
				orderBy.add(camOrderBy.getColumna(i).getCd());
			sOrderBy=" ORDER BY "+Util.join(", ", orderBy);
			}
		else{
			sOrderBy=" "+sql.substring(indexOfOrderBy);
        	sql=sql.substring(0,indexOfOrderBy);
			}
		sql+=" WHERE " + Util.join(" AND ", wh);
		sql+=sOrderBy;
		return sql;
	}

////////////////////////////	
	public String getLectura1(){
		return this.lectura1;
		}
	public INivelDef getNivelLectura1(Usuario usuario) throws ErrorDiccionario{
        String sql = this.getLectura1();

        //TODO sería más correcto no haberlo "arreglado" en la lectura del esquema
        //		para no tener que "procesar" y "desprocesar"
        //desarreglamos lectura1 para ver si es un nivel
        String nombrenivel=sql;
        if (sql.startsWith("{call")) nombrenivel =sql.substring("{call ".length(),sql.indexOf("(")); 
        return usuario.getApli().getNivelDef(nombrenivel);
        
	}
	public String getLecturaN(){
		return this.lecturaN;
		}
	public String getEscritura() {
		return this.escritura;
		}
	public String getUsp(){
		return this.usp;
		}
	public String getColBúsq() {
		return colBúsq;
	}
	public boolean getColBúsqAuto() {
		CampoDef clave = this.getCampoClave();
		Coleccion<ColumnaDef> cols = clave.getColumnas();
		return cols.get(cols.size()-1).getCd().equalsIgnoreCase(this.colBúsq);
	}
	void setColBúsq(String v){
		this.colBúsq=v;
	}
	
	public String getColDesc() {
		return colDesc;
	}
//////////////////////////////	
	@Override
	public String toString(){
		return "TablaDef "+this.cd+ "("+this.clave+")";
		}
	@Override
	public ITabla newTabla(Usuario usr) throws ErrorUsuarioNoValido {
		return new Tabla(this,usr);
	}
}
