package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Aplicacion;
import es.burke.gotta.CampoDef;
import es.burke.gotta.Captura;
import es.burke.gotta.Coleccion;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.Constantes.ColisionFicheros;
import es.burke.gotta.ControlFrm;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.Motor;
import es.burke.gotta.PoolAplicaciones;
import es.burke.gotta.TablaDef;
import es.burke.gotta.Util;

//sube ficheros

public class DLLSubirFicheros extends DLLGotta implements IDLLSubirFicheros {
	
	public String retornoNombre=null; 		//para subir 1 sólo 
	public String retornoRuta=null;   		//para subir 1 sólo
	
	public String literal="Adjuntar fichero";
	public String obligatorio=Constantes.CERO;
	private ColisionFicheros colision=ColisionFicheros.sobreEscribir;
	private String ruta="";
	
	public boolean subidaMultiple=false;
	private ITabla tablaRetorno;
	 
	public boolean getSubidaMultiple() {return subidaMultiple;}
	public String getRuta(){return this.ruta;}
	public ColisionFicheros getColision(){return this.colision;}
	public void setColision(ColisionFicheros colision){this.colision=colision;}
	public String getRetornoNombre() {return this.retornoNombre;}
	public String getRetornoRuta() {return this.retornoRuta;}
	
	@Override
	public String accionesValidas() {
		return "subir captura subirN";
		}

	@Override
	public Object ejecuta(Accion accion, Object vvalor) throws ErrorGotta {
		if (accion.accion.equalsIgnoreCase("subir")) { 
			//literal del X!?|obligatorio|colisión|ruta donde dejarlo
			// 	y en el p4 vendrá @nombreFich|@rutaFich
			analizaP3(accion, vvalor);
			return this.mMotor.presentarPedirCampo(Constantes.Xsi, this.retornoNombre, this.literal, this.obligatorio, Constantes.FILE);
			}
		else if (accion.accion.equalsIgnoreCase("subirN")) {
			//literal del X!?|obligatorio|colisión|ruta donde dejarlo|#tablaTemporal (para hacer después un FOR)
			analizaP3(accion, vvalor);
			Object temp=this.mMotor.presentarPedirCampo(Constantes.Xsi, null, this.literal, this.obligatorio, Constantes.FILE);
			ControlFrm x=this.mMotor.frmDinamico.controles.get(this.mMotor.frmDinamico.controles.size()-1);
			x.esSubirFicherosMultiple=true;
			return temp;
			}
		else if (accion.accion.equalsIgnoreCase("captura")) {
			this.literal="capturando...";
			analizaP3(accion, vvalor);
			Captura cap= new Captura(this.mMotor,literal);
			this.mMotor.cap=cap;
			return Motor.Resultado.CAPTURA;
			}
		
		return null;
		}
	public Motor.Resultado revivir(Accion accion, Object vvalor) throws ErrorGotta {
		if (accion.accion.equalsIgnoreCase("subir")) { 
			//literal del X!?|obligatorio|colisión|ruta donde dejarlo
			// 	y en el p4 vendrá @nombreFich|@rutaFich
			analizaP3(accion, vvalor);
			return this.mMotor.presentarPedirCampo(Constantes.Xno, this.retornoNombre, this.literal, this.obligatorio, Constantes.STRING);
			}		
		return null;
		}
	
	public void analizaP3(Accion accion, Object vvalor) throws ErrorAccionDLL, ErrorArrancandoAplicacion {
		String valor=vvalor.toString();
		Aplicacion apli = mMotor.getApli();
		if(!valor.equals(Constantes.CAD_VACIA)) {//p3 relleno
			ArrayList<String> v=Util.splitTrim(valor, Constantes.PIPE, false);
			this.literal=v.get(0);
			this.obligatorio=v.get(1);
			if (this.obligatorio.equalsIgnoreCase("obligatorio"))
				this.obligatorio=null;
			
			this.colision=ColisionFicheros.sobreEscribir;
			String colis=v.get(2).toLowerCase() ;
			if (Util.en(colis, "s", "sobreescribir", "sobrescribir"))
				this.colision=ColisionFicheros.sobreEscribir;
			else if (Util.en(colis, "r", "renombrar"))
				this.colision=ColisionFicheros.renombrar;
			else if (Util.en(colis, "d", "descartar", "c", "cancelar"))
				this.colision=ColisionFicheros.descartar;
			else
				throw new ErrorAccionDLL("El valor '"+colis+"' no es válido para indicar la política en caso de colisión de ficheros:"+
						"los valores válidos son 'R' para renombrar, 'D' para descartar y 'S' para sobrescribir.");
			
			this.ruta=v.get(3);
			this.ruta=Util.replaceTodos(this.ruta, "\\", "/");
			if (this.ruta.equals(""))
				this.ruta=PoolAplicaciones.dirInstalacion+apli.getDatoConfig("docWeb");
			
			if (accion.accion.equalsIgnoreCase("subirN")){
				this.subidaMultiple=true;
				
				if (v.size()<5)
					throw new ErrorAccionDLL("Parámetros incorrectos: para la subida múltiple hay que indicar un nombre de tabla temporal.");
				this.tablaRetorno=this.ponTablaRetorno(v.get(4));
				}
			}
		else {//p3 vacio: se hace como en GottaWeb5
			this.ruta=PoolAplicaciones.dirInstalacion+apli.getDatoConfig("docWeb");
			}
		
		this.ruta=Util.rutaFisicaRelativaOAbsoluta(this.ruta);
			
		if (!accion.accion.equalsIgnoreCase("subirN")) {
			ArrayList<String> temp=Util.split(accion.p4);
			try {
				this.retornoNombre=temp.get(0);
				this.retornoRuta=temp.get(1);
				}
			catch(IndexOutOfBoundsException e) 
				{/**pass**/}
			}
		
		this.mMotor.tramActivo().getListaFicheros().add(this);
	}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req,
			HttpServletResponse res, HttpSession sesion, String numprod)
			throws IOException, ErrorGotta {/*pass*/}

	@Override
	public void verifica(Motor motor, String accion, String nombre)throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
	}
	public void setRuta(String ruta) {
		this.ruta=ruta;
	}
	
	public ITabla ponTablaRetorno(String nombreTabla){
		
		Coleccion<ITabla> tablas = usr.getMotor().tablas;
		if (tablas.containsKey(nombreTabla))
			return tablas.get(nombreTabla);
		
		TablaDef tdef=new TablaDef(nombreTabla, null, null, null, null, null, null, null,null,null);
		
		String[] columnas={"nombre", "ruta", "nombreOriginal", "haSidoRenombrado", "haSidoSobreescrito"};
		String[] tipos= {Constantes.STRING, Constantes.STRING, Constantes.STRING, Constantes.BOOLEAN, Constantes.BOOLEAN};
		String nc, td;
		for (int i=0; i<columnas.length; i++){
			nc=columnas[i];
			td=tipos[i];
			
			ColumnaDef col=new ColumnaDef(nc, nc, td, 250, null);
			tdef.putColumna(nc, col);
			//	
			CampoDef cam = new CampoDef(nc, nc, tdef.getCd());
			cam.putColumna(col);
			tdef.putCampo(nc, cam);	
			}
		ITabla t= tdef.newTabla(this.usr);
		tablas.put(nombreTabla, t);
		return t;
		}
	public ITabla getTablaRetorno() {
		return this.tablaRetorno;
	}
}
