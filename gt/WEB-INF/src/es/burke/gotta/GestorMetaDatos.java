package es.burke.gotta;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GestorMetaDatos {
	private static String esNuevo="esNuevo";//$NON-NLS-1$
	private static String esModif="esModif";//$NON-NLS-1$
	private static String esBorrado="esBorrado";//$NON-NLS-1$

	static public boolean existeTramite(ConexionLight conex, Esquema esquema, String tramite) throws ErrorDiccionario {
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		String sql = "select count(*) from ("+tra_acciones.getLecturaN()+") t where CD_Tramite = ?";
		ArrayList<Object> n=new ArrayList<Object>();
		
		n.add(tramite);
		try 
			{return (Integer.parseInt(conex.lookUpSimple(sql, n).toString()))>=1;} 
		catch (NumberFormatException e) 
			{throw new ErrorDiccionario("existeTramite ha fallado");}
		catch (SQLException e) {
			throw new ErrorDiccionario(e.getLocalizedMessage());
			}
	}

	public static String getDS_Camino(ConexionLight conex, Esquema esquema, Long camino) throws SQLException, ErrorTablaNoExiste {
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");
		return Util.toString(conex.lookUpSimple("select ds_camino from ("+tra_caminos.getLecturaN()+") t where CD_Camino=?", Util.creaLista(camino)));
		}
	public static String getTablaBase(ConexionLight conex, Esquema esquema, Long camino) throws SQLException, ErrorTablaNoExiste { 
	 	TablaDef tra_caminos=getTabla(esquema, "tra_caminos");  
	 	return Util.toString(conex.lookUpSimple("select tablabase from ("+tra_caminos.getLecturaN()+") t where CD_Camino=?", Util.creaLista(camino))); 
	 	} 
	static public Integer sacaCaminoMantenimiento(ConexionLight conex, Esquema esquema, String tablaBase) throws ErrorDiccionario, ErrorConexionPerdida {
		if (tablaBase.equals(""))
			return null;
		
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");
		String sql = "select CD_Camino from ("+tra_caminos.getLecturaN()+") t where tablaBase =? and DS_Camino like 'Mantenimiento%'";
		ArrayList<Object> n=new ArrayList<Object>();
		
		n.add(tablaBase);
		Filas rs=lee(conex, sql, n);
		if (rs.size()>0)
			return Integer.parseInt(rs.get(0).get("cd_camino").toString());
		return null;
	}
	static public Long sacaCaminoTramitacion(ConexionLight conex, Esquema esquema, String tablaBase) throws ErrorDiccionario, ErrorConexionPerdida {
		if (tablaBase.equals(""))
			return null;
		
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");
		String sql = "select CD_Camino from ("+tra_caminos.getLecturaN()+") t where tablaBase =? and tablatramites is not null";
		ArrayList<Object> n=new ArrayList<Object>();
		
		n.add(tablaBase);
		Filas rs=lee(conex, sql, n);
		if (rs.size()>0)
			return Long.parseLong(rs.get(0).get("cd_camino").toString());
		return null;
	}
	static public Filas leeTRA_Caminos(ConexionLight conex, Esquema esquema, Long camino) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");
		
		String sql = "select * from ("+tra_caminos.getLecturaN()+") t where CD_Camino =?";
		ArrayList<Object> n=new ArrayList<Object>();
		
		n.add(camino);
		return lee(conex, sql, n);
	}

	static public Filas leeTramiteEspecial(ConexionLight conex, String tramite, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef tra_acciones= getTabla(esquema, "TRA_Acciones");
		String sql = "select * from ("+tra_acciones.getLecturaN()+") t where " +  conex.ucase("CD_Tramite")+"=? order by CD_Operacion";
		ArrayList<Object> n=new ArrayList<Object>();
		
		n.add(tramite.toUpperCase());
		Filas ret = lee(conex, sql, n);
		if (ret.isEmpty())
			return null;
		return ret;
	}
	static public String leeRegistro(ConexionLight conex, Long camino, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef tra_acciones= getTabla(esquema, "TRA_Acciones");
		String sql = "select max(CD_Tramite) from ("+tra_acciones.getLecturaN()+") t where cd_camino=? and "+ conex.ucase("CD_Tramite") + "=?";
		
		ArrayList<Object> n=Util.creaLista(camino, "REGISTRO");
		
		Filas ret = lee(conex, sql, n);
		return ret.get(0).gets(0);
	}
	static public Filas leeAcciones(ConexionLight conex, Long camino, String tramite, Esquema esquema, String idioma) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef tra= getTabla(esquema, "TRA_Acciones");
		String sql; Filas ret;
		
		tramite=tramite.toUpperCase();
		if (esquema.existeTabla("len_acciones")){
			TablaDef len= getTabla(esquema, "LEN_Acciones");
			sql="select t.CD_Camino, t.CD_Tramite, t.CD_Operacion, t.condicion, t.CD_Accion, "+
					"t.parametro1, t.parametro2, t.parametro3, t.parametro4, "+
					"l.parametro1 as lparametro1, l.parametro2 as lparametro2, l.parametro3 as lparametro3, l.parametro4 as lparametro4 "+
				"from ( "+len.getLecturaN()+" where CD_Idioma=?) l RIGHT OUTER JOIN ("+tra.getLecturaN()+") t ON l.CD_Camino=t.CD_Camino AND l.CD_Tramite=t.CD_Tramite AND l.CD_Operacion=t.CD_Operacion "+
				"where t.cd_camino=? and "+ conex.ucase("t.CD_Tramite") + "=?";
			ret= lee(conex, sql, Util.creaLista(idioma, camino, tramite) );
			
			//arreglamos datos
			int i=0;
			for (Fila fila:ret){
				for (int nump=1; nump<5; nump++){
					String valor=fila.gets("lparametro"+nump);
					if (valor!=null && !valor.equals(Constantes.CAD_VACIA)){
						fila.__setitem__("parametro"+nump, valor);
						if (!ret.filasTraducidas.contains(i))
							ret.filasTraducidas.add(i);
						}
					}
				i++;
				}
			}
		else {
			sql= "select * from ("+tra.getLecturaN()+") t where cd_camino=? and "+ conex.ucase("CD_Tramite") + "=?";
			ret = lee(conex, sql, Util.creaLista(camino, tramite) );
			}
		return ret;
		}
//-------------------------------------------------------	
	static public Filas leeAPP_DIC_Configuracion(ConexionLight conex, Esquema esquema, String usuGotta) throws ErrorDiccionario {
		String sql="";
		
		if (esquema.existeTabla("dic_configuracion")){
			/* normalmente vendrá 
		  		1. select * from p.dic_configuracion
		  		2. {call p.app_dic_configuracion(?)}
				*/
				TablaDef tablaDef = (TablaDef) esquema.getTablaDef("dic_configuracion");
				sql = tablaDef.getLecturaN();
			}
		else
			sql = "select * from "+conex.getPrefijo()+"DIC_Configuracion";
		
		int n=Util.cuentaCadena(sql, "?");
		ArrayList<Object> param=Util.repiteArrayList(usuGotta, n);
		
		return lee(conex, sql, param);
		}

	static public Filas leeDIC_Configuracion(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		String tabla="";
		if (esquema.existeTabla("dic_configuracion")) {
			TablaDef tablaDef = (TablaDef) esquema.getTablaDef("dic_configuracion");
		
			if(tablaDef.getLecturaN().toLowerCase().startsWith("select * from"))
				/* normalmente vendrá 
						1. select * from p.dic_configuracion
						2. {call p.app_dic_configuracion(?)}
				 */
				tabla = Util.obtenNombreTabla(tablaDef.getLecturaN());
			}
		if(tabla.equals(""))
			tabla = conex.getPrefijo()+"DIC_Configuracion";
		
		String sql="select * from "+tabla;
		ArrayList<Object> n=new ArrayList<Object>();
		return lee(conex, sql, n);
		}

	static public Filas leeDLL_Guiones(ConexionLight conex, String guion) throws ErrorDiccionario, ErrorConexionPerdida, ErrorArrancandoAplicacion {
		try {
			String sql= "select fuente from "+conex.getPrefijo()+"dll_guionesweb where cd_guion = ? order by cd_operacion";
			ArrayList<Object> n=new ArrayList<Object>();
			n.add(guion);
			return lee(conex, sql, n);
			}
		catch (ErrorDiccionario e) {
			conex.getApli().getEsquema().existeDLL_Guiones=false;
			throw e;
			}
		}
	static public boolean existeUsuarioGotta(ConexionLight conex, String usuGotta, String pwd) throws ErrorUsuarioNoValido {
		String sql= "select CD_Usuario from "+conex.getPrefijo()+"usuarios where "+conex.ucase("CD_Usuario")+"= ? and Contrasenha=?" ;
		
		ArrayList<Object> n=new ArrayList<Object>();
		n.add(usuGotta.toUpperCase());
		n.add( Util.embrollar(pwd) );
		
		Filas rs;
		try {
			rs = lee(conex, sql, n);
			return rs.size()>0;
			} 
		catch (ErrorDiccionario e) {
			throw new ErrorUsuarioNoValido(e.getMessage());
			} 
		catch (ErrorConexionPerdida e) {
			throw new ErrorUsuarioNoValido(e.getMessage());
			}
		}
	
	static public Filas leeDatosUsuario(ConexionLight conex, String usuGotta) throws ErrorDiccionario, ErrorConexionPerdida {
		String sql="select * from "+conex.getPrefijo()+"usuarios where "+conex.ucase("CD_Usuario")+"=? ";
		ArrayList<Object> n=new ArrayList<Object>();
		n.add(usuGotta.toUpperCase());
		return lee(conex, sql, n);
		}
	static public String leeContraseñaUsuario(ConexionLight conex, String usuGotta) throws ErrorDiccionario, ErrorConexionPerdida{
		String sql= "select contrasenha from "+conex.getPrefijo()+"USUARIOS u where "+conex.ucase("CD_Usuario")+"=?";
		ArrayList<Object> n=new ArrayList<Object>();
		n.add(usuGotta.toUpperCase());
		Filas rs=lee(conex, sql, n);
		if (rs.size()>0)
			return rs.get(0).get("contrasenha").toString();
		return null;
		}
	static public void actualizaContraseñaUsuario(ConexionLight conex, Esquema esq, String usuGotta, String pswVieja, String pswNueva) throws ErrorVolcandoDatos, ErrorConexionPerdida, ErrorDiccionario, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion{
		TablaDef usuarios=getTabla(esq, "usuarios");
		String sql="update "+usuarios.getEscritura()+" set contrasenha=? where CD_Usuario=?  and contrasenha=?";
		ejecutaSQL(conex, sql, Util.creaLista(pswNueva, usuGotta, pswVieja));
		
		if (! pswNueva.equals( leeContraseñaUsuario(conex, usuGotta) ))
			throw new ErrorVolcandoDatos("No se ha podido cambiar la contraseña");
		}
	static public void actualizaIdiomaUsuario(ConexionLight conex, Esquema esq, String usuGotta, String idioma) throws ErrorVolcandoDatos, ErrorTablaNoExiste, ErrorCampoNoExiste, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion{
		TablaDef usuarios=getTabla(esq, "usuarios");
		String sql="update "+usuarios.getEscritura()+" set cd_idioma=? where CD_Usuario=?";
		ejecutaSQL(conex, sql, Util.creaLista(idioma, usuGotta));
		}
	
//-------------------------------------------------------
	static public Filas leeEXP_Niveles(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef EXP_Niveles = getTabla(esquema, "EXP_Niveles");
		String sql="select * from ("+EXP_Niveles.getLecturaN()+") cc";
		return lee(conex, sql);
	}
	static public Filas leeEXP_NivelesSQL(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef EXP_NivelesSQL = getTabla(esquema, "EXP_NivelesSQL");
		String sql="select * from ("+EXP_NivelesSQL.getLecturaN()+") cc order by CD_Nivel, numLinea";
		return lee(conex, sql);
	}
	static public Filas leeEXP_ModosDetalle(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef exp_mododetalle = getTabla(esquema, "EXP_ModoDetalle");
		String sql="SELECT * from ("+exp_mododetalle.getLecturaN()+") cc";
		return lee(conex, sql);
	}
	
	static private TablaDef getTabla(Esquema esquema, String nt) throws ErrorTablaNoExiste{
		return (TablaDef)esquema.getTablaDef(nt);
	}
	@SuppressWarnings("cast")
	static private TablaDef getTabla(Coleccion<TablaDef> col, String nt) {
		return (TablaDef)col.get(nt);
	}
	static public void guardarMD(ConexionLight conex, Esquema esquema, String md, String letras, String tb, ArrayList<Coleccion<Object>> datos) throws ErrorConexionPerdida, ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion, ErrorTablaNoExiste {
		ArrayList<String> sqls=new ArrayList<String>();
		ArrayList<ArrayList<Object>> params=new ArrayList<ArrayList<Object>>();
		
		TablaDef exp_mododetalle=getTabla(esquema, "exp_mododetalle");
		TablaDef exp_controlesmododetalle=getTabla(esquema, "exp_controlesmododetalle");
		TablaDef len_controlesmododetalle=esquema.existeTabla("len_controlesmododetalle")? getTabla(esquema, "len_controlesmododetalle"): null;
		//el md
		String sqlExisteModoDetalle="select * from ("+exp_mododetalle.getLecturaN()+") t where cd_modoDetalle=?";
		
		String sqlInsertMd="insert into "+exp_mododetalle.getEscritura()+" (CD_Tabla, Letras, CD_ModoDetalle) values (?,?,?)";				
		String sqlUpdateMd="update "+exp_mododetalle.getEscritura()+" set CD_Tabla=?, Letras=? where CD_ModoDetalle=?";
		
		Boolean existeMd=false;
		try {
			existeMd=conex.lookUpSimple( sqlExisteModoDetalle, creaLista(md))!=null;
			} 
		catch (SQLException e) {
			//pass
			}
		if (existeMd) 
			sqls.add(sqlUpdateMd);
		else
			sqls.add(sqlInsertMd);

		params.add( creaLista(tb, letras, md) );
		
		//los controles
		int numControl=-1;
		
		String sqlDeleteTraduccion=len_controlesmododetalle!=null?"delete from "+len_controlesmododetalle.getEscritura()+" where numcontrol=?":null;
		String sqlDelete="delete from "+exp_controlesmododetalle.getEscritura()+" where numcontrol=?";
		String sqlUpdate = "update "+exp_controlesmododetalle.getEscritura()+" set claseCSS=?, modolista=?, mododetalle=?, tipoControl=?,izq=?, tope=?, ancho=?, alto=?, caption=?, contenedor=?, pestanha=?, tipoletra=?,tamanholetra=?, neg_cur_sub_tach=?, colorletra=?, BORDE=? WHERE NUMCONTROL=?";
		String sqlInsert = "insert into "+exp_controlesmododetalle.getEscritura()+" (claseCSS, MODOLISTA, MODODETALLE, TIPOCONTROL,IZQ,TOPE,ANCHO,ALTO,CAPTION, CONTENEDOR, PESTANHA, TIPOLETRA,TAMANHOLETRA,NEG_CUR_SUB_TACH, COLORLETRA, BORDE, NUMCONTROL) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		
		for (Coleccion<Object> c :  datos) {
			numControl=((BigDecimal)c.get("numControl")).intValue();
			if ( (Boolean)c.get("borrado")==true ) {
				if ( (Boolean)c.get("nuevo")==false ) {
					
					
					if (len_controlesmododetalle!=null){
						sqls.add(sqlDeleteTraduccion);
						params.add(creaLista(numControl));
						}
					
					sqls.add(sqlDelete);
					}
				}
			else if ( (Boolean)c.get("nuevo")==true ) 
				sqls.add(sqlInsert);
				
			else 
				sqls.add(sqlUpdate);
				
			
			if ( (Boolean)c.get("borrado")==true ) {
				if ( (Boolean)c.get("nuevo")==false ) 
					params.add( creaLista(numControl) );
				}
			else {
				Object cont=c.get("cont");
				if (cont.toString().equals("0.0")||cont.toString().equals("0"))
					cont=null;
				Object pest=c.get("pestanha");
				if (cont==null)
					pest=null;
				
				
				params.add( creaLista(c.get("claseCSS"), c.get("ml"),c.get("md"),c.get("tc"),
						Util.parteEntera(c.get("izq")),Util.parteEntera(c.get("tope")),Util.parteEntera(c.get("ancho")),Util.parteEntera(c.get("alto")),
						c.get("caption"), cont, pest, c.get("nomLetra"),c.get("tamLetra"),c.get("estLetra"), c.get("colores"), c.get("borde"), 
						numControl ));
					
				}
			}
		
		conex.ejecutaLote(sqls, params);
		} 
	static private ArrayList<Object> creaLista(Object...p){
		return Util.creaLista(p);
		} 
	static int leeMaxNumControl(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida{
		TablaDef EXP_ControlesModoDetalle=getTabla(esquema, "EXP_ControlesModoDetalle");
		String sql="select max(numControl) as maximo from "+EXP_ControlesModoDetalle.getEscritura();
		Filas rs=lee(conex, sql);
		if (rs.size()>0) {
			Object valor=rs.get(0).gets("maximo");
			if (valor==null || valor.equals("") )
				valor="0";
			return Integer.parseInt(valor.toString())+1;
			}
		else
			return 1;
		
		}
	static public Filas leeEXP_Controles(ConexionLight conex, String cd, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef exp_controles = getTabla(esquema, "EXP_ControlesModoDetalle");
		String sql= "select "+
						"NUMCONTROL, MODOLISTA, MODODETALLE, TIPOCONTROL,IZQ,TOPE,ANCHO,ALTO,CAPTION, CONTENEDOR, PESTANHA, TIPOLETRA,"+
						"TAMANHOLETRA, NEG_CUR_SUB_TACH, COLORLETRA, BORDE"+
						(exp_controles.getColumnas().containsKey("clasecss")?", claseCSS":"")+
					" from ("+exp_controles.getLecturaN()+") cc where (modoDetalle=? or modolista=?) AND (tope>0 or izq>0 or ancho>0 or alto>0 ) order by tope, izq";
		
		return lee(conex, sql, Util.creaLista(cd, cd));
		}
	static public Filas leeLEN_Controles(ConexionLight conex, String cd, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef len, exp;
		len= getTabla(esquema, "LEN_ControlesModoDetalle");
		exp= getTabla(esquema, "EXP_ControlesModoDetalle");
		String sql= "select ll.* "+
					"from ("+len.getLecturaN()+") ll,"+
						 "("+exp.getLecturaN()+") cc "+
			       "where (cc.modoDetalle=? or cc.modolista=?)";
		return lee(conex, sql, Util.creaLista(cd, cd));
		}
	static public Filas leeEXP_menuDetalle(Usuario usr, String cd_mododetalle, String cd_modolista){
		try {
			INivelDef nivelDef = usr.getApli().getNivelDef("menuDetalle");
			if (nivelDef==null)
				return null;
			ArrayList<Object> params=Util.creaLista(usr.getLogin(), cd_mododetalle, cd_modolista);
			return nivelDef.obtenerNivel().lookUp(usr.getConexion(), params);
			}
		catch (ErrorDiccionario e) {
			return null;
			} 
		catch (ErrorConexionPerdida e) {
			return null;
			} 
		catch (SQLException e) {
			usr.sacaError(e);
			} 
		catch (ErrorGotta e) {
			usr.sacaError(e);
			}
		return null;
		
	}
	static public Filas leeLEN_menu(ConexionLight conex, Esquema esquema, String tipo) throws ErrorDiccionario{
		if (!esquema.existeTabla("len_menu")) return null;
		
		TablaDef len_menu=getTabla(esquema, "len_menu");
		TablaDef exp_menu=getTabla(esquema, "exp_menu");
		String sql="select l.* FROM ("+len_menu.getLecturaN()+") l, ("+exp_menu.getLecturaN()+") c where c.cd_boton=l.cd_boton and tipo=?";
		ArrayList<Object> param=Util.creaLista(tipo);
		return lee(conex, sql, param);
		}
	static public Filas leeEXP_Estilos(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		TablaDef exp_estilos=getTabla(esquema, "exp_estilos");
		String sql="select * FROM ("+exp_estilos.getLecturaN()+") t order by 1";
		ArrayList<Object> n=new ArrayList<Object>();
		return lee(conex, sql, n);
		}
//-------------------------------------------------------
	static public Filas leeDIC_TablasConCaminoMantenimiento(ConexionLight conex, Coleccion<TablaDef> esquema) throws ErrorDiccionario, ErrorConexionPerdida {
		/*Esta no se puede usar en la primera lectura del dic porque en ese momento no hay diccionario, se está creando*/
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");
		String dic_tablas=conex.getPrefijo()+"dic_tablas";
		
		String sql="select "+ 
				"(select max(c.CD_Camino) from ("+tra_acciones.getLecturaN()+") a, ("+tra_caminos.getLecturaN()+") c where "+
					" c.CD_Camino=a.CD_Camino "+
					" and c.tablaBase=t.nombre "+
					" and CD_Tramite='Registro') as CD_Camino, "+
				"t.* "+
			"from "+dic_tablas+" t order by nombre";
		return lee(conex, sql);
		}
	static public Filas leeDIC_Tablas(ConexionLight conex) throws ErrorDiccionario, ErrorConexionPerdida {
		String dic_tablas=conex.getPrefijo()+"dic_tablas";
		
		String sql="select * from "+dic_tablas+" t order by nombre";
		return lee(conex, sql);
		}
	static public Filas leeDIC_Columnas(ConexionLight conex, Coleccion<TablaDef> tablas) throws ErrorDiccionario, ErrorConexionPerdida {
		String sql;
		if (tablas!=null){
			TablaDef DIC_Columnas=getTabla(tablas, "DIC_Columnas");
			sql="select * from ("+DIC_Columnas.getLecturaN()+") cc order by NombreTabla";
			}
		else
			sql="select * from "+conex.getPrefijo()+"DIC_Columnas order by NombreTabla";
		return lee(conex, sql);
		}
	static public Filas leeDIC_Campos(ConexionLight conex, Coleccion<TablaDef> tablas) throws ErrorDiccionario, ErrorConexionPerdida{
		String sql;		
		if (tablas!=null){
			TablaDef DIC_Campos=getTabla(tablas, "DIC_Campos");
			sql="select NombreTabla,NombreCampo,NombreColumna,Descripcion from ("+DIC_Campos.getLecturaN()+") cc order by NombreTabla, NombreCampo, Orden";
			}
		else
			sql="select NombreTabla,NombreCampo,NombreColumna,Descripcion from "+conex.getPrefijo()+"DIC_Campos cc order by NombreTabla, NombreCampo, Orden";		
		return lee(conex, sql);
		}
	static public Filas leeDIC_Referencias(ConexionLight conex, Coleccion<TablaDef> tablas) throws ErrorDiccionario, ErrorConexionPerdida {
		String sql;		
		if (tablas!=null){
			TablaDef dic_referencias=getTabla(tablas, "dic_referencias");
			sql="select TablaPrincipal,CampoPrincipal,TablaReferencia from ("+dic_referencias.getLecturaN()+") cc";
			}
		else
			sql="select TablaPrincipal,CampoPrincipal,TablaReferencia from "+conex.getPrefijo()+"dic_referencias cc";		
		return lee(conex, sql);
		}		
//	-------------------------------------------------------
	static public Filas leeTiposControl(ConexionLight conex, Esquema esquema) throws ErrorDiccionario, ErrorConexionPerdida { //para sacar la lista de controles disponibles en el diseñador
		TablaDef dic_tiposControl=getTabla(esquema, "dic_tiposcontrol");
		String sql="SELECT * from ("+dic_tiposControl.getLecturaN()+") t order by CD_TipoControl";
		return lee(conex, sql);
	}
//	-------------------------------------------------------
	public static Filas sacaListaIdiomas(Conexion conex, Esquema esquema) throws ErrorDiccionario {
		TablaDef lan_idiomas=getTabla(esquema, "LEN_Idiomas");
		String sql=lan_idiomas.getLecturaN();
		return lee(conex, sql);
	}
//	-------------------------------------------------------
	static private void ejecutaSQL(ConexionLight conex, String sql, ArrayList<Object> param) throws ErrorVolcandoDatos, ErrorConexionPerdida, ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion{
		ArrayList<String> listaSQL=new ArrayList<String>();
		ArrayList< ArrayList<Object> > listaParam= new ArrayList< ArrayList<Object> >();
		
		listaSQL.add(sql); listaParam.add(param);
		conex.ejecutaLote(listaSQL, listaParam);
	} 
	static private Filas lee(ConexionLight conex, String sql) throws ErrorDiccionario
		{return lee(conex,sql,null);}
	static private Filas lee(ConexionLight conex, String sql, ArrayList<Object> ar) throws ErrorDiccionario{
		try {
			return conex.lookUp(sql, ar, false);
			} 
		catch (SQLException e) {
			throw new ErrorDiccionario(e.getMessage() + ". El error se produjo al ejecutar: "+sql);
			}								
		}

/* para el editor de niveles */
	static private String quitaCall(String callApp){
		int inicio=callApp.toLowerCase().indexOf("call")+4;
		int fin=callApp.indexOf("(");
		
		if (fin==-1) fin=callApp.indexOf("}");
		return callApp.substring(inicio, fin).trim();
	}
	static public String montaCadenaTestNivel(String nombre, int numParametros){
		return "{call "+nombre+" ("+Util.quita(Util.repite("?, ", numParametros), 2)+")}";
	}
	static public ArrayList<Coleccion<Object>> sacaCodigoProcAlmacenado(ConexionLight conex, String callApp) throws ErrorConexionPerdida, JSONException{
		ArrayList<Coleccion<Object>> ret= new ArrayList<Coleccion<Object>>();
		ret.add( creaObjetoRetorno(null, callApp, null, null, null) );
		
		if (callApp.toLowerCase().startsWith("{call"))
			callApp=quitaCall(callApp);
		 
		try	{
			if (conex.tipoBD==Constantes.MarcaBaseDatos.MSSQL)
				ret=sacaCodigoProcAlmacenadoSQLServer(conex, callApp);
			else if (conex.tipoBD==Constantes.MarcaBaseDatos.Oracle) {
				try {
					ret=sacaCodigoProcAlmacenadoOracle(conex, callApp);
					} 
				catch (SQLException e) {
					return ret;
					}
				}
			}
		catch (ErrorDiccionario e){
			return ret;
			}
		
		return ret;
		}
	static private ArrayList<Coleccion<Object>> sacaCodigoProcAlmacenadoSQLServer(ConexionLight conex, String nombreNivel) throws ErrorDiccionario, ErrorConexionPerdida, JSONException{
		String sql="exec sp_helptext ?";
		String texto=concatenaResultado(lee(conex, sql, Util.creaLista(nombreNivel)), Constantes.CAD_VACIA);
		texto=Util.replace(texto, "CREATE", "ALTER", 1);
		
		JSONObject datos=getF_Modificacion(conex, nombreNivel, null);
		FechaGotta lastModified=null; 
		Object temp=datos.get("f");
		if (temp!=null)
			lastModified=new FechaGotta(temp.toString());
		
		String tipoObjeto=datos.getString("t");
		
		ArrayList<Coleccion<Object>> ret= new ArrayList<Coleccion<Object>>();
		ret.add(creaObjetoRetorno(null, texto, lastModified, tipoObjeto, nombreNivel));
		return ret;
	}
	static private ArrayList<Coleccion<Object>> sacaCodigoProcAlmacenadoOracle(ConexionLight conex, String nombreNivel) throws ErrorConexionPerdida, SQLException, ErrorDiccionario{
		//el puto oracle
		//1º miramos a ver si es un sinónimo
		String nombreNivel_deverdad=Constantes.CAD_VACIA;
		String nombreObjeto=Constantes.CAD_VACIA;
		ArrayList<String>temp=Util.split(nombreNivel.toUpperCase(), Constantes.PUNTO);
		String propietario;
		if(temp.size()==1){
			nombreObjeto=temp.get(temp.size()-1); 
			String sqlSinónimo="select table_owner || '.' || table_name  from USER_synonyms where synonym_name=?";
			nombreNivel_deverdad=Util.noNulo(conex.lookUpSimple(sqlSinónimo, Util.creaLista(nombreObjeto)));
			}
		else{
			String propietarioOPackage=temp.get(0).toUpperCase();
			//primero miramos a ver si el primer trozo es un propietario o un package
			String existeUsuario="select * from all_users where username=?";
			Filas f=conex.lookUp(existeUsuario, Util.creaLista(propietarioOPackage));
			
			if (f.size()>0){
				propietario = propietarioOPackage;
				nombreObjeto=temp.get(1);
				}
			else
				nombreObjeto=propietarioOPackage;
			}
		
		if (!nombreNivel_deverdad.equals(Constantes.CAD_VACIA)) {
			temp=Util.split(nombreNivel_deverdad, Constantes.PUNTO);
			propietario=temp.get(0);
			nombreObjeto=temp.get(1);
			}
		else {
			propietario=Util.noNulo(conex.lookUpSimple("select user from dual"));
//			if (!propietario.equals(Constantes.CAD_VACIA))
//				propietario=propietario.substring(0, propietario.length()-1).toUpperCase();
			}
		
		//ahora miramos el tipo de objeto (si se trata de un package, habrá que obtener cabecera y cuerpo!)
		String sqlTipoObjeto="select last_ddl_time, object_type from all_objects where object_name=? AND OWNER=? AND object_type!='SYNONYM'";
		Filas listaObjetos=conex.lookUp(sqlTipoObjeto, Util.creaLista(nombreObjeto, propietario));
		ArrayList<Coleccion<Object>> ret=new ArrayList<Coleccion<Object>>();
		for (Fila fila : listaObjetos){
			FechaGotta lastModified=(FechaGotta)fila.get("last_ddl_time");
			String tipoObjeto=fila.gets("object_type");
			
			String texto=sacaCodigoObjetoOracle(conex, tipoObjeto, nombreObjeto, propietario);
			ret.add( creaObjetoRetorno(propietario, texto, lastModified, tipoObjeto, nombreObjeto) );
			}
		return ret;
		}
	public static JSONObject getF_Modificacion(ConexionLight conex, String idObjeto, String tipoObjeto) throws JSONException{
		try {
			Filas ret=null;
			if (conex.tipoBD==Constantes.MarcaBaseDatos.Oracle){
				String sql="select last_ddl_time as f,object_type as t from all_objects where object_name=? AND object_type=?";
				ret=conex.lookUp(sql, Util.creaLista(idObjeto.toUpperCase(), tipoObjeto.toUpperCase()));
				}
			else if (conex.tipoBD==Constantes.MarcaBaseDatos.MSSQL) {
				String sql="SELECT MODIFY_DATE as f, TYPE_DESC as t from sys.Objects WHERE object_id=object_id(?)";
				ret=conex.lookUp(sql, Util.creaLista(idObjeto));
				}
			
			if (ret.size()>0){
				return new JSONObject()	.put("f", ret.get(0).gets("f"))
										.put("t", ret.get(0).gets("t")) ;
				}
			}
		catch (SQLException e){
			//pass	
			} 
		catch (ErrorFechaIncorrecta e) {
			//pass
			} 
		return null;
		} 
	private static Coleccion<Object> creaObjetoRetorno(String propietario, String texto, FechaGotta lastModified, String tipoObjeto, String nombreObjeto){
		Coleccion<Object> ret= new Coleccion<Object>();
		ret.put("prop", propietario);
		ret.put("texto", texto);
		ret.put("f_modificacion", lastModified);
		ret.put("tipoObjeto", tipoObjeto);
		ret.put("nombreObjeto", nombreObjeto);
		return ret;
		}
	private static String sacaCodigoObjetoOracle(ConexionLight conex, String tipo, String nombre, String propietario) throws ErrorDiccionario, ErrorConexionPerdida{
		String sqlCodProc="select text from all_source where type=? and name=? and owner=? ";
		Filas filas=lee(conex, sqlCodProc, Util.creaLista(tipo, nombre, propietario));
			
		if (filas==null || filas.size()==0)
			return null;
		
		return "CREATE OR REPLACE "+concatenaResultado(filas, Constantes.CAD_VACIA);
		}
	public static String concatenaResultado(Filas filas, String separador){
		String ret=""; 
		for (Fila fila : filas)
			ret+= Util.noNulo(fila.get(0)) + separador;
		return ret;
		}
	/////////////////////////////////
	public static void eliminarNivel(ConexionLight conex, Esquema esquema, String idNivel) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion, ErrorTablaNoExiste {
		ArrayList<String> sqls=new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		
		TablaDef exp_niveles=getTabla(esquema, "exp_niveles");
		TablaDef exp_nivelessql=getTabla(esquema, "exp_nivelessql");
		
		String borraNiveles="delete from "+exp_niveles.getEscritura()+" where CD_Nivel=?";
		String borraNivelesSQL="delete from "+exp_nivelessql.getEscritura()+" where CD_Nivel=?";

		sqls.add(borraNivelesSQL); paramsSQL.add( creaLista(idNivel) );
		sqls.add(borraNiveles); paramsSQL.add( creaLista(idNivel) );
		
		conex.ejecutaLote(sqls, paramsSQL);
		}
	static public void guardaNivel(ConexionLight conex, Esquema esquema, String cdNivel, String sql, String param) throws ErrorGotta{
		ArrayList<String> sqls=new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		
		TablaDef exp_niveles=getTabla(esquema, "exp_niveles");
		TablaDef exp_nivelessql=getTabla(esquema, "exp_nivelessql");
				
		int maxLong=250;
		TablaDef t=(TablaDef) esquema.getTablaDef("EXP_Niveles");
		if (t!=null){
			if (t.getColumnas().containsKey("SQLParcial"))
				maxLong=esquema.getTablaDef("EXP_Niveles").getColumna("SQLParcial").getLongitud();		
			}
		
		String existeNivel="select CD_Nivel from "+exp_niveles.getEscritura()+" where CD_Nivel=?";
		String insertNiveles="insert into "+exp_niveles.getEscritura()+" (CD_Nivel, SQLParcial, params) values (?, ?, ?)";
		String insertNivelesSQL="insert into "+exp_nivelessql.getEscritura()+" (CD_Nivel, NumLinea, SQLParcial) values (?, ?, ?)";
		
		String modifNiveles="update "+exp_niveles.getEscritura()+" set SQLParcial=?, params=? where CD_Nivel=?";
		String borraNivelesSQL="delete from "+exp_nivelessql.getEscritura()+" where CD_Nivel=?";
		
		Boolean cabe= maxLong > sql.length();
		Boolean existe=true;
		try {
			existe=conex.lookUpSimple(existeNivel, creaLista(cdNivel))!=null;
			} 
		catch (SQLException e) {
			/*pass*/
			}
		
		if (cabe) {
			if (existe) {
				sqls.add(modifNiveles);
				paramsSQL.add( creaLista(sql, param, cdNivel) );
				sqls.add(borraNivelesSQL);  paramsSQL.add( creaLista(cdNivel));
				}
			else {
				sqls.add(insertNiveles);
				paramsSQL.add( creaLista(cdNivel, sql, param) );
				}
			}
		else { //no cabe: hay que hacerlo en EXP_NivelesSQL
			maxLong=exp_nivelessql.getColumna("SQLParcial").getLongitud();
			ArrayList<String> trozos=trocea( sql, maxLong );
			if (existe) {
				//borramos las filas en EXP_NivelesSQL
				sqls.add(borraNivelesSQL);  paramsSQL.add( creaLista(cdNivel));			
				sqls.add(modifNiveles);  paramsSQL.add( creaLista(null, param, cdNivel));
				}
			else {
				sqls.add(insertNiveles);  paramsSQL.add( creaLista(cdNivel, null, param));
				}
			// y ahora, la lista de inserts en EXP_NivelesSQL
			for (int i=0; i<trozos.size(); i++) {
				String trozo=trozos.get(i);
				sqls.add(insertNivelesSQL);  paramsSQL.add( creaLista(cdNivel, i*10, trozo));
				}
			}
		
		conex.ejecutaLote(sqls, paramsSQL);
	}
	private static ArrayList<String> trocea(String sql, int maxLong){
		maxLong-=10;
		ArrayList<String> trozos= new ArrayList<String>();
		while (sql.length()>0) {
			String trozo="";
			if (sql.length()>maxLong) {
				trozo=sql.substring(0, maxLong);
				sql=sql.substring(maxLong);
				}
			else{
				trozo=sql;
				sql="";
				}
			
			trozos.add( trozo);
			}
		return trozos;	
	}

// diccionario	
	public static void edDIC_Tablas(ConexionLight conex, JSONArray array) throws ErrorConexionPerdida, ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		ArrayList<String> sqls = new ArrayList<String>();
		ArrayList<String> sqlsBorrar = new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> paramsSQLBorrar=new ArrayList<ArrayList<Object>>();
		for (int i=0; i<array.length();i++){
				JSONObject fila = array.getJSONObject(i);
				preparaDIC_Tablas(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar, fila);
				
				JSONArray columnas = fila.getJSONArray("columnas");
				for (int ii=0;ii<columnas.length();ii++){
					JSONObject col = columnas.getJSONObject(ii);
					preparaDIC_Columnas(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar,  col);
					}
				JSONArray campos = fila.getJSONArray("campos");
				int indiceInicio=-1;
				for (int ii=0;ii<campos.length();ii++){
					JSONObject cam = campos.getJSONObject(ii);
					if (indiceInicio==-1)
						indiceInicio=sacaMaxOrden_Dic_Campos(conex, cam.getString("tabla"));
					preparaDIC_Campos(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar,  cam, indiceInicio);
					
					indiceInicio+=fila.getJSONArray("columnas").length();
					}
				
				JSONArray referencias = fila.getJSONArray("referencias");
				for (int ii=0;ii<referencias.length();ii++){
					JSONObject ref = referencias.getJSONObject(ii);
					preparaDIC_Referencias(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar,  ref);
					}
				if ( fila.getBoolean(esBorrado)){
					String sqlBorrarRefs="DELETE FROM "+conex.getPrefijo()+"DIC_REFERENCIAS WHERE TABLAREFERENCIA=? OR TABLAPRINCIPAL=?";
					sqlsBorrar.add(sqlBorrarRefs);paramsSQLBorrar.add( creaLista( fila.getString("nombre"),fila.getString("nombre") )) ;
					}
				}
		//ordenamos los sqls: primero los de borrar, en orden 'inverso', y luegos las modificaciones e inserciones
		ordenaEjecuta(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar);
	}
	public static void edDIC_Columnas(ConexionLight conex, JSONArray array) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		ArrayList<String> sqls = new ArrayList<String>();
		ArrayList<String> sqlsBorrar = new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> paramsSQLBorrar=new ArrayList<ArrayList<Object>>();

		for (int i=0; i<array.length();i++){
			JSONObject fila = array.getJSONObject(i);
			preparaDIC_Columnas(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar,  fila);				
			}		
		ordenaEjecuta(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar);
		}
	public static void edDIC_Campos(ConexionLight conex, JSONArray array) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		ArrayList<String> sqls = new ArrayList<String>();
		ArrayList<String> sqlsBorrar = new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> paramsSQLBorrar=new ArrayList<ArrayList<Object>>();
		
		int indiceInicio=-1;
		for (int i=0; i<array.length();i++){
			JSONObject fila = array.getJSONObject(i);
			if (indiceInicio==-1)
				indiceInicio=sacaMaxOrden_Dic_Campos(conex, fila.getString("tabla"));
			
			preparaDIC_Campos(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar, fila, indiceInicio);
			if (!fila.get("tablaRef").equals(null) )
				preparaDIC_Referencias(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar, fila);
			
			indiceInicio+=fila.getJSONArray("columnas").length();
			}
		
		ordenaEjecuta(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar);
		}
	public static void edDIC_Referencias(ConexionLight conex, JSONArray array) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, JSONException, ErrorArrancandoAplicacion {
		ArrayList<String> sqls = new ArrayList<String>();
		ArrayList<String> sqlsBorrar = new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		ArrayList<ArrayList<Object>> paramsSQLBorrar=new ArrayList<ArrayList<Object>>();
		
		for (int i=0; i<array.length();i++){
			JSONObject fila = array.getJSONObject(i);
			preparaDIC_Referencias(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar,  fila);				
			}
		ordenaEjecuta(conex, sqls, paramsSQL, sqlsBorrar, paramsSQLBorrar);
		}
	/////////////////
	public static void preparaDIC_Tablas(ConexionLight conex, ArrayList<String>sqls, ArrayList<ArrayList<Object>> paramsSQL, ArrayList<String>sqlsBorrar, ArrayList<ArrayList<Object>> paramsSQLBorrar, JSONObject fila) throws JSONException{
		String borrarT= "DELETE FROM "+conex.getPrefijo()+"DIC_TABLAS WHERE nombre=?";
		String nuevaT = "INSERT INTO "+conex.getPrefijo()+"DIC_TABLAS (nombre,campoclave,lecturan,lectura1,escritura,usp,descripcion, columnaDescripcion, columnaBusqueda) VALUES (?,?,?,?,?,?,?, ?,?)";
		String modifT = "UPDATE "+conex.getPrefijo()+"DIC_TABLAS SET campoclave=?,lecturan=?,lectura1=?,escritura=?,usp=?,descripcion=?, columnaDescripcion=?, columnaBusqueda=? WHERE nombre=?";
		
		String clave="--";
		if (fila.has("clave") && !fila.get("clave").equals(JSONObject.NULL)){
			clave=fila.getString("clave");
			}
		
		if (fila.getBoolean(esBorrado)){
			sqlsBorrar.add(borrarT); paramsSQLBorrar.add( creaLista( fila.getString("nombre") )) ;
			}
		else if (fila.getBoolean(esNuevo)){
			sqls.add(nuevaT); paramsSQL.add( creaLista(fila.getString("nombre"), clave, fila.getString("lecturaN"), fila.getString("lectura1"), fila.getString("escritura"),fila.getString("usp"),
					fila.getString("descripcion"), 
					fila.optString("columnadescripcion"), fila.optString("columnabusqueda")) );
			}
		else if (fila.getBoolean(esModif)){
			sqls.add(modifT); paramsSQL.add( creaLista(clave, fila.getString("lecturaN"), fila.getString("lectura1"), fila.getString("escritura"), fila.getString("usp"), fila.getString("descripcion"), 
					fila.optString("columnadescripcion"), fila.optString("columnabusqueda"),
					fila.getString("nombre")) );
			}
		}
	public static void preparaDIC_Columnas(ConexionLight conex, ArrayList<String>sqls, ArrayList<ArrayList<Object>> paramsSQL,  ArrayList<String>sqlsBorrar, ArrayList<ArrayList<Object>> paramsSQLBorrar, JSONObject fila) throws JSONException{
		String borrarCol= "DELETE FROM "+conex.getPrefijo()+"DIC_COLUMNAS WHERE nombretabla=? and nombreColumna=?";
		String nuevaCol = "INSERT INTO "+conex.getPrefijo()+"DIC_COLUMNAS (nombretabla,nombrecolumna,tipo,longitud,descripcion)VALUES (?,?,?,?,?)";
		String modifCol = "UPDATE "+conex.getPrefijo()+"DIC_COLUMNAS SET tipo=?,longitud=?,descripcion=? WHERE nombretabla=? and nombreColumna=?";
		
		if (!fila.has("longitud")) 
			fila.put("longitud", 1);
		else if (fila.getInt("longitud")>10000)
			fila.put("longitud", 10000);
		
		if ( fila.getBoolean(esBorrado)){
			sqlsBorrar.add(borrarCol); paramsSQLBorrar.add( creaLista(fila.getString("tabla"), fila.getString("nombre"))) ;
			}
		else if (fila.getBoolean( esNuevo)){
			sqls.add(nuevaCol); paramsSQL.add( creaLista(fila.getString("tabla"),fila.getString("nombre"), fila.getString("tipo"), fila.getInt("longitud"), fila.getString("descripcion")) );
			}
		else if (fila.getBoolean(esModif)){
			sqls.add(modifCol); paramsSQL.add( creaLista(fila.getString("tipo"), fila.getString("longitud"), fila.getString("descripcion"), fila.getString("tabla"), fila.getString("nombre")) );
			}
		}
	private static int sacaMaxOrden_Dic_Campos(ConexionLight conex, String tabla) throws ErrorConexionPerdida{
		int indiceInicio=0;
		try {
			Object res=conex.lookUpSimple("select max(orden) from "+conex.prefijo+"DIC_Campos where nombreTabla=?", Util.creaLista(tabla));
			if (res!=null)
				indiceInicio=1+Integer.parseInt(res.toString()); 
			} 
		catch (NumberFormatException e) {
			/*pass*/} 
		catch (SQLException e) {
			/*pass*/}
		return indiceInicio;
		}
	public static void preparaDIC_Campos(ConexionLight conex,  ArrayList<String>sqls, ArrayList<ArrayList<Object>> paramsSQL,  ArrayList<String>sqlsBorrar, ArrayList<ArrayList<Object>> paramsSQLBorrar, JSONObject fila, int indiceInicio) throws ErrorConexionPerdida, JSONException {		
		String borrarCam ="DELETE FROM "+conex.getPrefijo()+"DIC_CAMPOS WHERE nombretabla=? AND nombrecampo=?";
		String nuevoCam = "INSERT INTO "+conex.getPrefijo()+"DIC_CAMPOS (nombretabla,orden,nombrecampo,nombrecolumna,descripcion) VALUES (?,?,?,?,?)";
		
		if (fila.getBoolean(esBorrado) || fila.getBoolean(esModif) || fila.getBoolean(esNuevo)) {
			sqlsBorrar.add(borrarCam); paramsSQLBorrar.add( creaLista(fila.getString("tabla"), fila.getString("nombre")) );
			}
		
		if (fila.getBoolean(esBorrado)){
			//pass
			}
		else if ( fila.getBoolean(esModif) || fila.getBoolean(esNuevo)) {			
			JSONArray columnas = fila.getJSONArray("columnas");
			for (int i=0; i<columnas.length(); i++) {
				sqls.add(nuevoCam); paramsSQL.add( 
						creaLista(fila.getString("tabla"), 
						i+indiceInicio, 
						fila.getString("nombre"), 
						columnas.getString(i), 
						fila.getString("nombre")) );
				}
			}
		}
	private static void preparaDIC_Referencias(ConexionLight conex, ArrayList<String> sqls, ArrayList<ArrayList<Object>> paramsSQL,   ArrayList<String>sqlsBorrar, ArrayList<ArrayList<Object>> paramsSQLBorrar, JSONObject fila) throws JSONException {
		String borrarRef ="DELETE FROM "+conex.getPrefijo()+"DIC_REFERENCIAS WHERE tablaPrincipal=? and campoPrincipal=?";
		String nuevaRef = "INSERT INTO "+conex.getPrefijo()+"DIC_REFERENCIAS (tablaprincipal,campoprincipal,tablareferencia) VALUES (?,?,?)";
		String modifRef = "UPDATE "+conex.getPrefijo()+"DIC_REFERENCIAS SET tablareferencia=? WHERE tablaPrincipal=? and campoPrincipal=?";
		
		if (fila.getBoolean(esBorrado)){
			sqlsBorrar.add(borrarRef); paramsSQLBorrar.add( creaLista(fila.getString("tabla"), fila.getString("nombre"))) ;
			}
		else if (fila.getBoolean(esNuevo)){
			sqls.add(nuevaRef); paramsSQL.add( creaLista(fila.getString("tabla"), fila.getString("nombre"), fila.getString("tablaRef")) );
			}
		else if (fila.getBoolean(esModif)){
			sqls.add(modifRef); paramsSQL.add( creaLista(fila.getString("tablaRef"), fila.getString("tabla"), fila.getString("nombre")) );
			}
	}

	private static void ordenaEjecuta(ConexionLight conex, ArrayList<String>sqls, ArrayList<ArrayList<Object>> paramsSQL, ArrayList<String>sqlsBorrar, ArrayList<ArrayList<Object>> paramsSQLBorrar) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion{
		ArrayList<String> sqlsFinal=new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQLFinal= new ArrayList<ArrayList<Object>>();

		// primero los insert en el orden contrario al que vienen:
		for (int i=sqlsBorrar.size()-1; i>=0; i--){
			sqlsFinal.add( sqlsBorrar.get(i));
			paramsSQLFinal.add( paramsSQLBorrar.get(i));
			}
		
		// y luego el resto
		for (int i=0; i<sqls.size(); i++){
			sqlsFinal.add( sqls.get(i));
			paramsSQLFinal.add( paramsSQL.get(i));
			}
		conex.ejecutaLote(sqlsFinal, paramsSQLFinal);
		}

// depurador
	public static void guardaAccion(Conexion conex, Esquema esq,
			Long caminoViejo, String tramiteViejo, Long opVieja, 
			Long caminoNuevo, String tramiteNuevo, Long opNueva, String cond, 
			String accion, String p1, String p2, String p3, String p4,
			boolean esNueva, boolean esBorrar) throws ErrorVolcandoDatos, ErrorConexionPerdida, ErrorTablaNoExiste, ErrorArrancandoAplicacion {
		
		TablaDef tablaDef = (TablaDef) esq.getTablaDef("TRA_Acciones");
		String tra_acciones=tablaDef.getEscritura();
		//inicialmente sólo permitiremos modificar líneas ya existentes
		String sql; ArrayList<Object> params=new ArrayList<Object>();
		
		if (esBorrar){
			sql="delete from "+tra_acciones+" WHERE CD_Camino=? and CD_Tramite=? and CD_Operacion=?";
			params.add(caminoViejo); params.add(tramiteViejo); params.add(opVieja);
			}
		else {
			params.add(caminoNuevo); params.add(tramiteNuevo); params.add(opNueva);
			params.add(cond);
			params.add(accion);
			params.add(p1); params.add(p2); params.add(p3); params.add(p4);
			
			if (esNueva){
				sql="insert into "+tra_acciones+" (CD_Camino, CD_Tramite, CD_Operacion, Condicion, CD_Accion, Parametro1, Parametro2, Parametro3, Parametro4) "+
					" values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
				}
			else {
				sql="update "+tra_acciones+" SET "+
					"CD_Camino=?, CD_Tramite=?, CD_Operacion=?, Condicion=?, CD_Accion=?, Parametro1=?, Parametro2=?, Parametro3=?, Parametro4=?"+
					" WHERE CD_Camino=? and CD_Tramite=? and CD_Operacion=?";
				params.add(caminoViejo); params.add(tramiteViejo); params.add(opVieja);
				}
			}
		conex.ejecuta(sql, params);
		}

//mapa de tramitación
	public static Filas mapaTram_tramitesQueLlamanDLL(Conexion conexion, Esquema esq, Long camino) throws ErrorDiccionario {
		TablaDef t=getTabla(esq, "TRA_Acciones");
		String sql="select CD_Tramite, CD_Accion, {fn LCASE(Parametro1)}, Parametro3 from ("+t.getLecturaN()+") t"+ 
		  			" WHERE CD_Accion='DLL' AND CD_Camino =? order by CD_Tramite, CD_Accion, CD_Operacion";
		return lee(conexion, sql, creaLista(camino));
		}

	public static Filas mapaTram_tramitesSUB(Conexion conexion, Esquema esquema, Long cd) throws SQLException, ErrorTablaNoExiste {
		TablaDef t=getTabla(esquema, "TRA_Acciones");
		
		String sql="select CD_Tramite, CD_Accion, Parametro2 as nombreTramite from ("+t.getLecturaN()+") t where CD_Accion in ('SUB', 'EJE') and CD_Camino=? "+
		" union "+
		" select CD_Tramite, CD_Accion, Parametro3 as nombreTramite from ("+t.getLecturaN()+") t where CD_Accion = 'FOR' and CD_Camino=?";

		return conexion.lookUp(sql, Util.creaLista(cd, cd));
		}
	public static Filas mapaTram_sacaJerarquia(Conexion conexion, Esquema esquema, Long camino) throws ErrorDiccionario {
		TablaDef t=getTabla(esquema, "TRA_Acciones");
		
		String sql=
			//los de mantenimiento
			"Select distinct CD_Tramite, '' as CD_Accion, '' as Parametro1 from ("+t.getLecturaN()+") t Where CD_Camino=?"+
			//PEN
			" UNION " +
				"SELECT CD_Tramite, CD_Accion, Parametro1 FROM ("+t.getLecturaN()+") t WHERE CD_Accion='PEN' AND CD_Camino=?" +	
			//Trámites libres
			" UNION " +
				"SELECT CD_Tramite, CD_Accion, Parametro1 FROM ("+t.getLecturaN()+") t WHERE CD_Accion='PEN' AND Parametro1='Libre' AND CD_Camino=?";
	
		return lee(conexion, sql, Util.creaLista(camino, camino, camino));
		}
	public static Filas mapaTram_sacaDescripcionTramites(Conexion conexion, Esquema esquema, Long camino) throws ErrorDiccionario {
		TablaDef a=getTabla(esquema, "TRA_Acciones");
		TablaDef tt=getTabla(esquema, "TRA_TramitesObjetos");
		
		String sql = "SELECT t.CD_TRAMITE, Min(t.DS_Tramite) AS DS_Tramite From ("+a.getLecturaN()+") a,("+tt.getLecturaN()+") t" +
			" Where a.CD_Accion='PEN' AND a.Parametro1 = t.CD_TRAMITE AND a.CD_Camino =? Group By t.CD_TRAMITE, a.CD_Camino" + 
			" union " +
			"SELECT t.CD_TRAMITE, Min(t.DS_Tramite) AS DS_Tramite From ("+a.getLecturaN()+") a,("+tt.getLecturaN()+") t " +
			" Where a.CD_TRAMITE = t.CD_TRAMITE AND a.CD_Camino =? Group By t.CD_TRAMITE, a.CD_Camino" ;
		

		return lee(conexion, sql, Util.creaLista(camino, camino));
		}
	
	static public Filas leeListaCaminos(ConexionLight conex, Esquema esquema) throws ErrorTablaNoExiste{
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");
		
		String sql="select "+ 
						" c.CD_Camino, c.DS_Camino, c.tablatramites, "+
						"(select count(distinct cd_Tramite) from ("+tra_acciones.getLecturaN()+") t where t.cd_camino=c.cd_Camino) as cuenta "+
					" from ("+tra_caminos.getLecturaN()+") c "+
					" order by c.CD_Camino";
					
		try {
			return conex.lookUp(sql);
			} 
		catch (ErrorConexionPerdida e) {
			return null;
			} 
		catch (SQLException e) {
			return null;
			}
		}
	public static void guardaTRA_TramitesObjetos(Conexion conex, Esquema esquema, Long idcamino, String ncd, String nds, String cd) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorTablaNoExiste{
		guardaTRA_TramitesObjetos(conex, esquema, idcamino, ncd, nds, cd, true);
	}
	public static void guardaTRA_TramitesObjetos(Conexion conex, Esquema esquema, Long idcamino, String ncd, String nds, String cd, boolean crearFilaEnAcciones) throws ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorTablaNoExiste{
		ArrayList<SqlParam>ret= new ArrayList<SqlParam>();
		
		SqlParam sqlparam=generaInsertUpdateTRA_TramitesObjetos(conex, esquema, ncd, nds, cd);
		ret.add(sqlparam);
		
		if (crearFilaEnAcciones && tramEstáVacío(conex, esquema, idcamino, ncd)) //se trata de un tram vacío: metemos una acción LBL 
			ret.add(generaInsertTRA_Acciones(esquema, idcamino, ncd, new Long("10"), null, Constantes.LBL_AC, null, null, null, null));
		
		conex.ejecutaLote (ret);
		}
	
	private static boolean tramEstáVacío(Conexion conex, Esquema esquema, Long idcamino, String idtram) throws ErrorTablaNoExiste {
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		String sql="select count(*) from ("+tra_acciones.getLecturaN()+") t where cd_camino=? and cd_tramite=?";
		Integer ret;
		try {
			ret = Integer.parseInt(conex.lookUpSimple(sql, Util.creaLista(idcamino, idtram)).toString());
			return ret==0;
			} 
		catch (Throwable e) {
			return true;
			}
		}
	public static Filas leeListaTrámitesImplementados(Conexion conex, Esquema esquema, Long idcamino) throws ErrorConexionPerdida, SQLException, ErrorTablaNoExiste {
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		String sql="select "+ 
						"CD_Tramite "+ 
					" from ("+tra_acciones.getLecturaN()+") t"+
					" where "+
						" cd_camino=? AND CD_Accion not in ('LBL', 'PEN') "+
					"group by cd_tramite";
		return conex.lookUp(sql, Util.creaLista(idcamino));
	}
	
	public static void guardaTRA_Caminos(Conexion conex, Esquema esquema, String ncd, String ds, String cd, String tb, String tt) throws ErrorVolcandoDatos, ErrorConexionPerdida, ErrorTablaNoExiste {
		TablaDef tra_caminos=getTabla(esquema, "tra_caminos");

		String sqlModif="update "+tra_caminos.getEscritura()+" set CD_Camino=?, DS_Camino=?, tablaBase=?, tablaTramites=? where CD_Camino=?";
		String sqlNuevo="insert into "+tra_caminos.getEscritura()+" (CD_Camino, DS_Camino, tablaBase, tablaTramites) values (?, ?, ?, ?)";
		
		String sql; ArrayList<Object> params;
		if (cd==null) {
			sql=sqlNuevo;
			params=Util.creaLista(ncd, ds, tb, tt);
			}
		else {
			sql=sqlModif;
			params=Util.creaLista(ncd, ds, tb, tt, cd) ;
			}
		
		conex.ejecuta(sql, params);
		}
	public static void eliminaPEN(Conexion conex, Esquema esquema, Long camino, String tramite, String tramitePEN) throws ErrorGotta, SQLException{
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		
		String sqlBuscaPen="select CD_Operacion from ("+tra_acciones.getLecturaN()+") t where CD_Camino =? and CD_Tramite =? and CD_Accion = ? and Parametro1=?";
		Long CD_Operacion=Long.parseLong(conex.lookUpSimple(sqlBuscaPen, Util.creaLista(camino, tramite, Constantes.PEN, tramitePEN)).toString());
		
		String sqlBorraPEN = "delete from "+tra_acciones.getEscritura()+" where CD_Camino=? and CD_Tramite=? and CD_Operacion=?";
		conex.ejecuta(sqlBorraPEN, Util.creaLista(camino, tramite, CD_Operacion));
	}
	public static void creaPEN(Conexion conex, Esquema esquema, Long camino, String tramite, Long caminoPEN, String tramitePEN,
							String CD_TramiteExistente, String CD_TramiteNuevo, String DS_TramiteNuevo) throws ErrorGotta, SQLException{
		ArrayList<SqlParam>ret= new ArrayList<SqlParam>();
		
		//1º si no existe el tram, lo generamos
		if (CD_TramiteNuevo!=null)
			ret.add(generaInsertUpdateTRA_TramitesObjetos(conex, esquema, CD_TramiteNuevo, DS_TramiteNuevo, CD_TramiteExistente));
		
		Long cd_operacion=getMaxCD_Operacion(conex, esquema, camino, tramite);
		ret.add( generaInsertTRA_Acciones(esquema, camino, tramite, cd_operacion, null, Constantes.PEN, tramitePEN, null, null, caminoPEN+""));
		
		if (tramEstáVacío(conex, esquema, caminoPEN, tramitePEN))//le añadimos una acción LBL para que tenga un poco de chicha
			ret.add( generaInsertTRA_Acciones(esquema, caminoPEN, tramitePEN, new Long("10"), null, Constantes.LBL_AC, null, null, null, null));
		
		conex.ejecutaLote(ret);
		}
	private static Long getMaxCD_Operacion(Conexion conex, Esquema esquema, Long camino, String tramite) throws ErrorConexionPerdida, SQLException, ErrorTablaNoExiste{
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		
		String sqlCD_Operacion="select max(CD_Operacion) from ("+tra_acciones.getLecturaN()+") t where CD_Camino=? AND CD_Tramite=?";
		String temp=Util.toString( conex.lookUpSimple(sqlCD_Operacion, Util.creaLista(camino, tramite )) );
		Long cd_operacion=temp!=null? Long.parseLong(temp) : 0;
		cd_operacion+=10;
		
		return cd_operacion;
		}
	private static boolean existeTRA_TramitesObjetos(Conexion conex, Esquema esquema, String tramite) throws ErrorTablaNoExiste {
		TablaDef tra_tramitesObjetos=getTabla(esquema, "tra_tramitesObjetos");
		String sql="select CD_Tramite from ("+tra_tramitesObjetos.getLecturaN()+") t where CD_Tramite=?";
		Object ret;
		try {
			ret = conex.lookUpSimple(sql, Util.creaLista(tramite));
			} 
		catch (SQLException e) {
			return false;
			}
		return ret!=null;
		}
	private static SqlParam generaInsertUpdateTRA_TramitesObjetos(Conexion conex, Esquema esquema, String nuevoCD_Tramite, String nuevoDS_Tramite, String CD_Tramite) throws ErrorTablaNoExiste {
		TablaDef tra_tramitesobjetos=getTabla(esquema, "tra_tramitesobjetos");
		String sqlModif="update "+tra_tramitesobjetos.getEscritura()+" set CD_Tramite=?, DS_Tramite=? where CD_Tramite=?";
		String sqlNuevo="insert into "+tra_tramitesobjetos.getEscritura()+" (CD_Tramite,DS_Tramite) values (?,?)";
		
		SqlParam ret;
		if (!existeTRA_TramitesObjetos(conex, esquema, nuevoCD_Tramite)) 
			ret = new SqlParam(sqlNuevo, Util.creaLista(nuevoCD_Tramite, nuevoDS_Tramite));
		else
			ret= new SqlParam(sqlModif, Util.creaLista(nuevoCD_Tramite, nuevoDS_Tramite, nuevoCD_Tramite==null? CD_Tramite:nuevoCD_Tramite));
		
		return ret;
		}
	private static SqlParam generaInsertTRA_Acciones(Esquema esquema, Long cd_camino, String tramite, Long cd_operacion, String cond, String accion, String p1, String p2, String p3, String p4) throws ErrorTablaNoExiste{
		TablaDef tra_acciones=getTabla(esquema, "tra_acciones");
		String sqlInsert="insert into "+tra_acciones.getEscritura()+" (CD_Camino, CD_Tramite, CD_Operacion, condicion, CD_Accion, parametro1, parametro2, parametro3, parametro4) values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		SqlParam ret=new SqlParam(sqlInsert, Util.creaLista(cd_camino, tramite, cd_operacion, cond, accion, p1, p2, p3, p4));
		return ret;
		}

	public static void guardaEXP_Estilos(Usuario usr, Esquema esquema, ArrayList<String> eliminar, ArrayList<String> crear) throws ErrorConexionPerdida, ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion, ErrorTablaNoExiste {
		TablaDef exp_estilos=getTabla(esquema, "exp_estilos");
		
		Conexion conex=usr.getConexion();
		
		String sqlBorrar= "delete from "+exp_estilos.getEscritura()+" where CD_Estilo=?";
		String sqlCrear = "insert into "+exp_estilos.getEscritura()+" (CD_Estilo, DS_Estilo) values (?, ?) ";
		
		ArrayList<String>sqls= new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL= new ArrayList<ArrayList<Object>>();
		
		for (int i=0; i<eliminar.size(); i++){
			if (! eliminar.get(i).equals(Constantes.CAD_VACIA)){
				sqls.add(sqlBorrar); paramsSQL.add( Util.creaLista(eliminar.get(i)));
				}
			}
		for (int i=0; i<crear.size(); i++){
			if (! crear.get(i).equals(Constantes.CAD_VACIA)){
				sqls.add(sqlCrear); paramsSQL.add( Util.creaLista(crear.get(i), crear.get(i)));
				}
			}
		conex.ejecutaLote(sqls, paramsSQL);
		}

	public static Filas leeEXP_Menu(ConexionLight conex, Esquema esquema) throws ErrorDiccionario {
		TablaDef exp_menu=getTabla(esquema, "exp_menu");
		
		String sql= "select * from ("+exp_menu.getLecturaN()+") t";
		return lee(conex, sql);
		}
	public static void guardaMenu(Conexion conex, Esquema esquema, 
			Long CD_Boton,
			String CD_BarraHerramientas, String CD_MenuPadre, 
			String Imagen, String texto, String titulo,
			Object CD_Camino, String CD_Tramite, String CD_ModoLista,
			String disposicion, String tipoPanel1, String CD_NivelInicial,
			Boolean botonera, String URL, String tipo, 
			Boolean esNuevo, Boolean esModificar, Boolean esEliminar) throws ErrorVolcandoDatos, ErrorTablaNoExiste {
	
		TablaDef exp_menu=getTabla(esquema, "exp_menu");
		Long lngCD_Camino=null;
		if (CD_Camino!=null)
			lngCD_Camino=Long.parseLong(CD_Camino.toString());
		
		String sql = null;
		ArrayList<Object> param=Util.creaLista(CD_BarraHerramientas, CD_MenuPadre, Imagen, texto, titulo, lngCD_Camino, CD_Tramite, CD_ModoLista, URL, disposicion, tipoPanel1, CD_NivelInicial, botonera, tipo, CD_Boton);
		
		if (esEliminar)
			sql="delete from "+exp_menu.getEscritura()+" where CD_Boton=?";
		else if (esNuevo)
			sql="insert into "+exp_menu.getEscritura()+" (CD_BarraHerramientas, CD_MenuPadre, Imagen, texto, titulo, CD_Camino, CD_Tramite, CD_ModoLista, URL, disposicion, tipoPanel1, CD_NivelInicial, botonera, tipo, CD_Boton) "+
						  " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
		else if (esModificar)
			sql="update "+exp_menu.getEscritura()+" set CD_BarraHerramientas=?, CD_MenuPadre=?, Imagen=?, texto=?, titulo=?, CD_Camino=?, CD_Tramite=?, CD_ModoLista=?, URL=?, disposicion=?, tipoPanel1=?, CD_NivelInicial=?, botonera=?, tipo=? where cd_boton=?";
		
		conex.ejecuta(sql, param);
		}

// estadísticas de la página de recarga
	public static Long leeTotalVistasEXP_Menu(Conexion conexion, Esquema esquema) throws ErrorGotta {
		TablaDef exp_menu=getTabla(esquema, "exp_menu");
		return leeTotalSQL(conexion, "select count(*) from ("+exp_menu.getLecturaN()+") t where CD_ModoLista is not null");
		}
	public static Long leeTotalEXP_Menu(Conexion conexion, Esquema esquema) throws ErrorGotta {
		return leeTotalTabla(conexion, esquema, "EXP_Menu");
		}
	public static Long leeTotalEXP_ControlesModoDetalle(Conexion conexion, Esquema esquema) throws ErrorGotta {
		return leeTotalTabla(conexion, esquema, "EXP_ControlesModoDetalle");
		}
	public static Long leeTotalTRA_Caminos(Conexion conexion, Esquema esquema) throws ErrorGotta {
		return leeTotalTabla(conexion, esquema, "tra_caminos");
		}
	public static Long leeTotalTRA_Acciones(Conexion conexion, Esquema esquema) throws ErrorGotta {
		return leeTotalTabla(conexion, esquema, "tra_acciones");
		}
	public static Long leeTotalTramites(Conexion conexion, Esquema esquema) throws ErrorGotta {
		TablaDef acc=getTabla(esquema, "TRA_Acciones");
		
		String sql="select count(*) from (select distinct cd_camino, cd_tramite from ("+acc.getLecturaN()+") tt ) t";
		return leeTotalSQL(conexion, sql);
		}
	
	public static Long leeTotalUSUARIOS(Conexion conexion, Esquema esquema) throws ErrorGotta {
		return leeTotalTabla(conexion, esquema, "usuarios");
		}
	
	private static Long leeTotalSQL(Conexion conexion, String sql) throws ErrorGotta{
		Object r;
		try {
			r = conexion.lookUpSimple(sql);
			} 
		catch (SQLException e) {
			throw new ErrorGotta(e.getMessage());
			}
		return Long.parseLong(r.toString());
	}
	private static Long leeTotalTabla(Conexion conexion, Esquema esquema, String t) throws ErrorGotta{
		TablaDef td=getTabla(esquema, t);
		return leeTotalSQL(conexion, "select count(*) from ("+td.getLecturaN()+") t");	
	}

////////// guiones jython	
	public static void eliminarGuion(Conexion conex, Esquema esquema, String idObjeto) throws ErrorTablaNoExiste, ErrorCampoNoExiste, ErrorVolcandoDatos, ErrorConexionPerdida, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion {
		ArrayList<String> sqls=new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		
		TablaDef DLL_GuionesWeb=getTabla(esquema, "DLL_GuionesWeb");
		
		String sql="delete from "+DLL_GuionesWeb.getEscritura()+" where CD_Guion=?";
		sqls.add(sql); paramsSQL.add( creaLista(idObjeto) );
		
		conex.ejecutaLote(sqls, paramsSQL);
		}
	static public void guardaGuion(ConexionLight conex, Esquema esquema, String idObjeto, String texto) throws ErrorGotta{
		ArrayList<String> sqls=new ArrayList<String>();
		ArrayList<ArrayList<Object>> paramsSQL=new ArrayList<ArrayList<Object>>();
		
		TablaDef dll_guionesweb=getTabla(esquema, "dll_guionesWeb");
				
//		int maxLong=250;
//		if (dll_guionesweb!=null){
//			if (dll_guionesweb.getColumnas().containsKey("fuente"))
//				maxLong=dll_guionesweb.getColumna("fuente").getLongitud();		
//			}
		
		String insert="insert into "+dll_guionesweb.getEscritura()+" (CD_Guion, CD_Operacion, Fuente) values (?, ?, ?)";
		String delete="delete from "+dll_guionesweb.getEscritura()+" where CD_Guion=?";
		
		ArrayList<String> trozos=Util.splitTrim(texto, Constantes.vbCr, false);
		
		sqls.add(delete);  paramsSQL.add( creaLista(idObjeto));			
		// y ahora, la lista de inserts en EXP_NivelesSQL
		for (int i=0; i<trozos.size(); i++) {
			String trozo=trozos.get(i);
			sqls.add(insert);  paramsSQL.add( creaLista(idObjeto, i*10, trozo));
			}
		
		conex.ejecutaLote(sqls, paramsSQL);
		}

	public static Filas leeTrabajos(Conexion conex, Esquema esquema) throws ErrorGotta  {
		TablaDef tra_tareas=getTabla(esquema, "TRA_TrabajosProgramados");
		String sql="select *"+ 
					" from ("+tra_tareas.getLecturaN()+") c "+
					" where (c.F_Prevista > ? or c.F_Prevista = ?)"+
					" order by c.F_Prevista";
		try {
			return conex.lookUp(sql, Util.creaLista(new FechaGotta(), new FechaGotta("11/11/2011 11:11:11")));
			} 
		catch (ErrorConexionPerdida e) {
			throw new ErrorGotta(e);
			}
		catch (SQLException e) {
			throw new ErrorGotta(e);
			}
		}

	public static Filas leeLEN_Niveles(Conexion conex, Esquema esquema) throws ErrorDiccionario {
		TablaDef len_niveles=getTabla(esquema, "len_niveles");
		String sql="select l.* FROM ("+len_niveles.getLecturaN()+") l";
		ArrayList<Object> param=Util.creaLista();
		return lee(conex, sql, param);
	}

	
	public static Filas leeLEN_Columnas(Conexion conex, Esquema esquema, String cdTabla, String idioma) throws ErrorDiccionario {
		TablaDef LEN_Columnas=getTabla(esquema, "LEN_Columnas");
		String sql="select l.* FROM ("+LEN_Columnas.getLecturaN()+") l where "+conex.ucase("CD_Tabla")+"=? and CD_Idioma=?";
		ArrayList<Object> param=Util.creaLista(cdTabla.toUpperCase(), idioma);
		return lee(conex, sql, param);	}
	}