package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.CampoDef;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.ITablaDef;
import es.burke.gotta.Motor;
import es.burke.gotta.TablaDef;
import es.burke.gotta.Util;

public class PBS_Numeradores extends DLLGotta {

	@Override
	public String accionesValidas() {
		return "auto 1 2 3 4 5";
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		ITablaDef tdef ;
		ITabla tabla;
		CampoDef clave ;
		String parametro=valor.toString();
		Object num1 =null;
		String sql=Constantes.CAD_VACIA;
		String where=Constantes.CAD_VACIA;
		try {
			if (parametro.contains(Constantes.PUNTO) ){	   
				ArrayList<String>sclave = Util.split(parametro, Constantes.PUNTO) ;	   
				tdef = mMotor.sacaTablaDef(sclave.get(0));	   
				tabla=mMotor.tablas.get(sclave.get(0))	   ;
				clave = tdef.getCampo(sclave.get(1));
				}
			else {
				if (mMotor.sacaTablaDef(parametro)==null){
					String msg="No se ha encontrado la tabla "+ parametro	   +" en el diccionario"; 
					throw new ErrorGotta( msg);
					}
				tdef = mMotor.sacaTablaDef(parametro);	   
				tabla = mMotor.sacaTabla(parametro);	   
				clave = tabla.tdef.getCampoClave();
				}
			int colNumerada;
			if (accion.accion.equalsIgnoreCase( "auto"))
				colNumerada=clave.numColumnas()-1;
			else
				colNumerada=Integer.parseInt(accion.accion)-1;
			
			if (clave.numColumnas()<=colNumerada)
				throw new ErrorAccionDLL("Se está intentando sacar el numerador de la columna número "+accion.accion+
						" y el campo "+clave.getCd()+" de la tabla "+tdef.getCd()+" sólo tiene "+clave.numColumnas()+" columna(s)");
			
			ArrayList<Object> params = new ArrayList<Object>();
			for (int i=0;i<clave.numColumnas();i++){	   
				ColumnaDef col = clave.getColumna(i);
				if (i == colNumerada)//en la última vuelta...	   
					sql = "select max("+col.getCd()+") from "+((TablaDef)tdef).getEscritura();	   
				else {   
					where += " and "+col.getCd()+" = ?";
					params.add(tabla.getValorCol(col.getCd()));
					}
				}
			if (!where.equals(Constantes.CAD_VACIA))
				sql+=" where " + where.substring(5);
			
			num1 = mMotor.getUsuario().getConexion().lookUpSimple(sql,params);
			if (num1==null)	   
				num1=0; 
			num1 = Long.parseLong(num1.toString() )+ 1;
			
			ColumnaDef col = clave.getColumna(colNumerada);	
			if (tabla.getNumRegistrosCargados()==0){   
				tabla.setValorCol(col.getCd(), num1); 
				return num1;
				}
			else {
				ArrayList<Object>paramsConResultado=(ArrayList<Object>)params.clone();
				paramsConResultado.add(num1);
				
				while (tabla.findKey(paramsConResultado, clave.getColumnas().getOrden(), false)){
					num1=(Long)num1+ 1;
					
					paramsConResultado.remove(params.size());
					paramsConResultado.add(num1);
					}
				tabla.setValorCol(col.getCd(), num1); 
				return num1;
				}
			}
		catch(Exception e){
			throw new ErrorAccionDLL("Error en PBS_Numeradores ("+sql+") "+e.getMessage()+" :"+num1);
			}
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
