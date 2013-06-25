package es.burke.gotta.dll;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.CampoDef;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ITablaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.Tabla;
import es.burke.gotta.TablaDef;
import es.burke.gotta.Util;

public class PBS_Numeradores_SEC extends DLLGotta {

	@Override
	public String accionesValidas() {
		return "auto";
	}

	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		String nombreTabla;
		Tabla tabla; ITablaDef tdef;
		CampoDef clave ;
	    String parametro=valor.toString();
		   
        if (parametro.contains(".") ){	   
            ArrayList<String>sclave = Util.split(parametro, ".") ;	   
            tdef = mMotor.sacaTablaDef(sclave.get(0));
            nombreTabla=((TablaDef)tdef).getEscritura();
            tabla=(Tabla)mMotor.tablas.get(sclave.get(0));
            clave = tdef.getCampo(sclave.get(1));
        	}
        else{	   
            if (mMotor.sacaTablaDef(parametro)==null){	   
                String msg="No se ha encontrado la tabla "+ parametro+" en el diccionario"; 
                throw new ErrorGotta( msg);
            	}
			tdef = mMotor.sacaTablaDef(parametro);	
			nombreTabla=((TablaDef)tdef).getEscritura();
			tabla = (Tabla)mMotor.sacaTabla(parametro);	   
			clave = tabla.getTablaDef().getCampoClave();
        	}
        
	    String sql_contador, sql_update, sql_insert, sql_selectmax;
	    ArrayList<Object> param=new ArrayList<Object>();
	    ColumnaDef col;
		if (clave.numColumnas()==1) {//clave simple
			col = clave.getColumna(0);
			
			sql_contador = "select contador from contadores where nombretabla =?";
			sql_update   = "update contadores set contador=contador+1 where nombretabla=?";
			sql_insert   = "insert into contadores (nombretabla, contador) values (?,?)";
			sql_selectmax="select max("+col.getCd()+") as contador from "+ nombreTabla;
			
			param.add( nombreTabla );
			}
		else { //clave compuesta
			ArrayList<String> where=new ArrayList<String>();
			ArrayList<String> columnas=new ArrayList<String>();
			ArrayList<Object> valores =new ArrayList<Object>();
			
			for (int i=0; i<clave.numColumnas()-1 ;i++ ){
				col = clave.getColumna(i);
				columnas.add(col.getCd());
				valores.add( tabla.getValorCol(col.getCd()) );
				where.add(col.getCd()+" = ?");
				param.add( tabla.getValorCol(col.getCd()) ) ;
				}
			
			col = clave.getColumna(-1);
			String sWhere=Util.join(" AND ", where);
			sql_contador= "select contador from "+nombreTabla+"_SEC where "+sWhere;
			sql_update =  "update "+nombreTabla+"_SEC set contador = contador+1 where "+sWhere;
			sql_insert =  "insert into "+nombreTabla+"_SEC ("+Util.join(",", columnas)+",contador) values("+listaInterrogantes(columnas.size())+",?)";
			sql_selectmax="select max("+col.getCd()+") as contador from "+nombreTabla+" where "+sWhere;
			}
	        	   
	    Object num1 =null;
	    try {
			num1 =mMotor.getUsuario().getConexion().lookUpSimple(sql_contador, param);
			if (num1==null) { //obtengo el max e inserto en contadores
				num1 =mMotor.getUsuario().getConexion().lookUpSimple(sql_selectmax);
				if (num1==null) 
					num1=1;
				else 
					num1 = Long.parseLong(num1.toString())+ 1;

				param.add(num1);
				mMotor.getUsuario().getConexion().ejecuta(sql_insert, param);
				}
			
			mMotor.getUsuario().getConexion().ejecuta(sql_update, param);
			} 
	    catch (SQLException e) {
			throw new ErrorAccionDLL("Error en PBS_Numeradores_SEC:"+e.getMessage());
			}	   
			   
	    col = clave.getColumna(-1);	   
	    tabla.setValorCol(col.getCd(), num1);	  
	    return num1;
			
	}
	private ArrayList<String> listaInterrogantes(int num){
		ArrayList<String> ret=new ArrayList<String>();
		for (int i=0; i<num; i++)
			ret.add("?");
			
		return ret;
		} 
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta {
		// pass
	}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
	}

	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}

}
