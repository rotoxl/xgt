package es.burke.gotta;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.Motor.Resultado;
import es.burke.gotta.dll.IDLLSubirFicheros;

public class Tramite {
	private String etiquetaVerificada=Constantes.CAD_VACIA;//para que no entre en bucle al verificar etiqueta: del padre al hijo y del hijo al padre
	
	private static final int MAX_NIVEL_RECURSION = 20;
	
	private FirmaTramite firma=null;
	public Motor motor;
	public Lote lote;
	
	public static enum TipoEjecucion {Ejecucion, DejarPen, Reemitir, Revivir}
	private static enum TipoVagon {FOR, SUB, EJE}
	
	public boolean verificado=false; 
	public int fase=Constantes.TramiteIniciado;

	public ArrayList<Archivo> archivosDescargables=new ArrayList<Archivo>();//para guardar Streams de plantillas o cualquier recurso que pueda hacer falta
	private Object fechaUltimoTramite; //objeto que guarda la fecha último trámite.
	
	private boolean interrumpirEnLinea0=true;
	private boolean interrumpirEnLineaFinal=true;
	
	TipoVagon tipoVagon; 
	public Long camino;
	public String tramite;
	boolean cumple;    //indica el resultado de la última condición
	ArrayList<Object> claveObjetoBase;
	ArrayList<Object> claveObjetoPte;
	
	public Tramite tramPadre=null;
	int tramEjecutado=-1;
	public TipoEjecucion tipoEjecucion = TipoEjecucion.Ejecucion;
	String tipoTram=""; // lt, tp, te, tl
	
	public Coleccion<Variable> variablesExplorador;
	public Coleccion<Accion> mapaAcciones=new Coleccion<Accion>(false);
	ITabla acciones; //acciones del tramite principal
	
	public FirmaTramite getFirma(){
		return this.tramiteInicial().firma;
		}
	public Accion getAccionActual(){
		String idx=acciones.datos.get(acciones.registroActual-1).gets("CD_Operacion")+this.idxDepuracion;
		return this.mapaAcciones.get(idx);
	}
	//subrutinas, EJE, FOR
	public HashMap<String, Tramite> tramitesSUB=new HashMap<String, Tramite>(); // son idénticos,sólo se distinguen por
	public ArrayList<Tramite> tramitesEJE; // 		   el momento en q se ejecutan
	//public ArrayList<Tramite> tramitesFOR; 
	public ITabla tablaFOR;
	public int volverA;
	
	private ArrayList<IDLLSubirFicheros> listaFicheros;
	public String idxDepuracion;
	
	//que la tabla base de tramitación es igual que la tabla base del modo detalle
	public boolean hayQueCargarTB()  {
		if (this.claveObjetoBase==null || this.claveObjetoBase.size()==0)
			return false;
		
		String tbTram=null;
		if (this._tb!=null)
			tbTram=this._tb.tdef.getCd();
		
		if (tbTram == null)
			return false;  
		if (this.tipoVagon==TipoVagon.EJE)
			return true;

		if (this.tramiteInicial().nodoActivo.md==null)
			return false;
		
		if (this.tipoTram.equals("lt") && this.claveObjetoBase.size()>0)
			return true;
		if (this.tipoTram.equals("pb") && this.claveObjetoBase.size()>0)
			return true;
		
		String tbMD=this.nodoActivo.nombretb;
		if (tbMD == null || tbMD.equals("") )
			return false;
		else if (tbTram.equalsIgnoreCase(tbMD)) 
			return true;
		

		return false;
	}

	/* Constructor para los trámites por lotes, tp, te*/
	public Tramite(Lote lote, 
				Long camino, 
				String tramite, 
				ArrayList<Object>claveTB,
				ArrayList<Object>claveTT,
				Coleccion<Variable> variablesExplorador, String modo) throws ErrorDiccionario, ErrorCargandoTabla, ErrorTiposNoCoinciden, ErrorFechaIncorrecta, ErrorNumeroIncorrecto, ErrorGotta 
		{//this();
		if ( modo.equals("te") )
			this.tipoEjecucion=Tramite.TipoEjecucion.Revivir;
		ConstructorDeVerdad(lote, camino, tramite, claveTB, claveTT, variablesExplorador, null, null);  
		this.tipoTram=modo;
		
		this.tipoEjecucion = Tramite.TipoEjecucion.Ejecucion;
		
		}

	/* Constructor para los trámites de botón*/
	public Tramite(Lote lote, 
				Long camino, 
				String tramite, 
				ArrayList<Object>claveObjetoBase,
				Coleccion<Variable> variablesExplorador) throws ErrorDiccionario, ErrorCargandoTabla, ErrorTiposNoCoinciden, ErrorFechaIncorrecta, ErrorNumeroIncorrecto, ErrorGotta 
		{//this();
		ConstructorDeVerdad(lote, camino, tramite, claveObjetoBase, null, variablesExplorador, null, null);  
		this.tipoEjecucion=TipoEjecucion.Ejecucion;}
	/* Constructor para los SUB */
	public Tramite(Lote lote, Long camino, String tramite, Tramite tramPadre, Long numLinea) throws ErrorGotta {
		//this();
		ConstructorDeVerdad(lote, camino,tramite, 
							tramPadre.claveObjetoBase, null,
							new Coleccion<Variable>(), 
							tramPadre, TipoVagon.SUB);
		tramPadre.tramitesSUB.put("x"+numLinea, this);
		this.tipoEjecucion=tramPadre.tipoEjecucion;
		}
	
	/* Constructor para los FOR */
	public Tramite(Lote lote, Long camino, String tramite, Tramite tramPadre, Long numLinea, ITabla tablaFOR, int volverA) throws ErrorGotta {
		ConstructorDeVerdad(lote, camino,tramite, 
							tramPadre.claveObjetoBase, null,
							tramPadre.variablesExplorador, 
							tramPadre, TipoVagon.FOR); 
		tramPadre.tramitesSUB.put("x"+numLinea, this);
		this.tipoEjecucion=tramPadre.tipoEjecucion;
		this.tablaFOR=tablaFOR;
		this.volverA=volverA;
		}
	/* Constructor para los trámites 'vagón' de la acción EJE*/
	public Tramite(Lote lote, 
			Long caminoEJE, 
			String tramiteEJE, 
			ArrayList<Object>claveObjetoBaseEJE, 
			String parametrosEJE, // a15\bEXPEDIENTE
			Tramite tramPadre) throws ErrorDiccionario, ErrorTiposNoCoinciden, ErrorFechaIncorrecta, ErrorNumeroIncorrecto, ErrorGotta {
		Coleccion<Variable> param= new Coleccion<Variable>();
		if (parametrosEJE != null) {
			ArrayList<String> temp=Util.split(parametrosEJE,Constantes.SEPARADOR);
			for (String todo:temp) {
				String letra=todo.substring(0, 1);
				String[] x;
				String valor;
				if(letra.equals("@")){
					x=todo.split("=");
					letra=x[0];
					//java mola
					if(x.length==1)
						valor=null;
					else
						valor=x[1];
				}
				else{
					valor=todo.substring(1);
				}
				Variable var=new Variable(letra,Constantes.STRING, valor);
				param.put(letra, var);
				}
			}
		ConstructorDeVerdad(lote, caminoEJE, tramiteEJE, claveObjetoBaseEJE, null, param, tramPadre, TipoVagon.EJE);
		}
	/*Constructor para trámites automáticos (generado en manteTablas2)*/
	public Tramite(Lote lote1, ITabla acciones, Long camino1, String tramite1) throws ErrorGotta{
		this.lote=lote1;
		this.motor=lote1.motor;
		this.camino=camino1;
		this.tramite=tramite1;
		
		this.idxDepuracion=Constantes.GUIÓN_BAJO+tramite1+Constantes.GUIÓN_BAJO+camino1+Constantes.GUIÓN_BAJO+this.hashCode();	
		this.variablesExplorador=new Coleccion<Variable>();; 
		tramitesEJE=new ArrayList<Tramite>();
		
		motor.tablas.put(acciones.tdef.getCd(), acciones);
		this.acciones=acciones;
		this.preparaYVerifica();
	}
	private void ConstructorDeVerdad(Lote lote1, 
						Long camino1, 
						String tramite1, 
						ArrayList<Object>claveTB, 
						ArrayList<Object>claveTT, 
						Coleccion<Variable> variablesExplorador1, 
						Tramite tramPadre1, TipoVagon tipo) throws ErrorDiccionario, ErrorTiposNoCoinciden, ErrorGotta{
		this.lote=lote1;
		this.motor=lote1.motor;
		this.camino=camino1;
		this.tramite=tramite1;
		
		if (tramPadre1==null){
			motor.getUsuario().añadeMSG("Preparamos trámite: "+camino1+", "+tramite1, Constantes.TipoMensajeGotta.tramite);
			this.listaFicheros= new ArrayList<IDLLSubirFicheros>();		
			}
		this.idxDepuracion=Constantes.GUIÓN_BAJO+tramite1+Constantes.GUIÓN_BAJO+camino1+Constantes.GUIÓN_BAJO+this.hashCode();
			
		//metemos las tablas en la colección del motor
		if ( 	this.tramite.equalsIgnoreCase("gtlogin") ||
				this.tramite.equalsIgnoreCase("autoexec") ||
				this.tramite.equalsIgnoreCase("gtusuario") 
				) {
			//pass
			}
		else {
			this.ponTablas();
			this.claveObjetoBase=claveTB;
			this.claveObjetoPte=claveTT;
			if (claveObjetoPte!=null && claveObjetoPte.size()==0)
				claveObjetoPte=null;
			}
		
		if (variablesExplorador1==null)
			variablesExplorador1=new Coleccion<Variable>();
		this.variablesExplorador=variablesExplorador1; 
		//tramitesSUB=new ArrayList<Tramite>();
		tramitesEJE=new ArrayList<Tramite>();
		//tramitesFOR=new ArrayList<Tramite>();
		this.tipoVagon=tipo;
		if (tramPadre1 != null) {
			if (tipo==TipoVagon.EJE)
				tramPadre1.tramiteInicial().tramitesEJE.add(this);
			else{ //trámites SUB
				this.tramPadre=tramPadre1;
				int nivelRecursion=0;
				while (tramPadre1.tramPadre!=null){
					tramPadre1=tramPadre1.tramPadre;
					if(nivelRecursion++>MAX_NIVEL_RECURSION)
						throw new StackOverflowError("Exceso de anidación de trámites");
					}
				this._tb=this.tramPadre._tb;
				this._tt=this.tramPadre._tt;
				}
			}
		
		try {
			leeAcciones();
			} 
		catch (ErrorFilaNoExiste e) {
			//pass
			}
		
		if (this.tramPadre==null)
			this.firma=new FirmaTramite(this);
		}
	
	Filas cacheAPP_tramites=null;
	Filas APP_Tramites(Long camino1, String tramite1, Aplicacion apli) throws ErrorGotta{
		if (cacheAPP_tramites!=null)
			return cacheAPP_tramites;
		
		if (!apli.niveles.containsKey("APP_Tramites"))
			return null;
		INivel nivel=apli.niveles.get("APP_Tramites").obtenerNivel();
		try {
			cacheAPP_tramites=nivel.lookUp(motor.getUsuario().getConexion(), Util.creaLista( motor.getUsuario().getLogin(), camino1, tramite1)) ;
			
			if (cacheAPP_tramites.size()>1){
				throw new ErrorGotta("APP_Tramites mal formado: debe devolver obligatoriamente una única fila");
				}
			
			} 
		catch (ErrorConexionPerdida e) {
			throw new ErrorGotta("Error al leer APP_Tramites:"+e.getMessage());
			} 
		catch (SQLException e) {
			throw new ErrorGotta("Error al leer APP_Tramites:"+e.getMessage());
			}
		return cacheAPP_tramites;
		}

	ITabla _tb, _tt, _tf;

	public NodoActivo nodoActivo;

	private boolean cargarTodosLosTramites=false;
	
	public ITabla getTb()
		{return _tb;}
	public ITabla getTt()
		{return _tt;}
	public ITabla getTf()
		{return _tf;}
	
	private void ponTablas() throws ErrorDiccionario, ErrorCreandoTabla, ErrorArrancandoAplicacion{
		Fila fila = this.lote.cacheTRA_Caminos(camino);
		if (fila!=null) {
			String xtb, xtt;
			
			xtb=fila.gets("tablabase");
			xtt=fila.gets("tablatramites");
			
			_tb=motor.ponTabla(motor.sacaTablaDef(xtb));
			
			_tt=motor.ponTabla(motor.sacaTablaDef(xtt));
			
			if (fila.containsKey("tablafirmas")) {
				String xtf=fila.gets("tablafirmas");
				_tf=motor.ponTabla(motor.sacaTablaDef(xtf));
				}
			}
		}
	public ITabla getAcciones() {
		return this.acciones;
		}
	void leeAcciones() throws ErrorArrancandoAplicacion, ErrorGotta {
		acciones=lote.cacheTRA_Acciones(this);
		motor.tablas.put(acciones.tdef.getCd(), acciones);
		}

	static void montaTramiteEliminar(ITabla acciones, String tablaBase, Long camino, String tramite) throws ErrorCampoNoExiste, ErrorTiposNoCoinciden, ErrorFilaNoExiste{
		Util.creaNuevaFila(acciones, camino, tramite, 10, "", "MSG", "'¿Desea eliminar el elemento seleccionado?'", "19", "z", "");
		Util.creaNuevaFila(acciones, camino, tramite, 20, "z", "DEL", tablaBase, "", "", "");
		}
	static void montaTramiteCambiarContraseña(ITabla acciones, Long camino, String tramite) throws ErrorCampoNoExiste, ErrorTiposNoCoinciden, ErrorFilaNoExiste{
		Util.creaNuevaFila(acciones, camino, tramite, 25,	null,	"DLL",	"Gotta",	"Usuario",	null, "u");
		Util.creaNuevaFila(acciones, camino, tramite, 50,	null,	"CAR",	"Usuarios",	"u", 		null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 80,	null,	"LBL",	"inicio", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 100,	null,	"FRM",	"Principio",	"Cambiar contraseña", null,null);
		Util.creaNuevaFila(acciones, camino, tramite, 150,	null,	"X!",	"*.Usuarios.CD_Usuario", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 200,	null,	"X!?",	"*.Usuarios.DS_Usuario", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 250,	null,	"X!?",	null, "Rellene los siguientes campos únicamente se desea cambiar su contraseña", null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 300,	null,	"X!?",	"@contraAntigua", "Contraseña actual", "1", "String");
		Util.creaNuevaFila(acciones, camino, tramite, 400,	null,	"X!?",	"@contraNueva1", "Nueva contraseña", "1", "String");
		Util.creaNuevaFila(acciones, camino, tramite, 500,	null,	"X!?",	"@contraNueva2", "Confirmar nueva contraseña", "1", "String");
		Util.creaNuevaFila(acciones, camino, tramite, 1000,null,	"FRM",	"Fin", null, null, null);
		
		Util.creaNuevaFila(acciones, camino, tramite, 1010,null,	"X>X",	"0", "@hayCambioPassword", "boolean", null);
		Util.creaNuevaFila(acciones, camino, tramite, 1020,"(@contraNueva1 & @contraAntigua & '') # ''",	"X>X",	"1", "@hayCambioPassword", null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 1050,"@hayCambioPassword = 0",					"GTO",	"Fin",null, null, null);
		
		Util.creaNuevaFila(acciones, camino, tramite, 1100,"'x' & @contraNueva1 # 'x' & @contraNueva2",	"MSG",	"Los campos \"Nueva contraseña\" y \"Confirmar nueva contraseña\" no coinciden", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 1150,"\"",	"GTO",	"inicio", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 1200,null,	"DLL",	"General",	"embrolla",	"@contraAntigua",	"@contraAntigua");
		Util.creaNuevaFila(acciones, camino, tramite, 1250,"'x' & @contraAntigua # 'x' & *.Usuarios.contrasenha",	"MSG",	"La contraseña actual introducida no es válida", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 1300,"\"",	"GTO",	"inicio", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 1400,null,	"DLL",	"General",	"embrolla",	"@contraNueva1",	"*.Usuarios.contrasenha");
		Util.creaNuevaFila(acciones, camino, tramite, 1500,null,	"LBL",	"fin", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 2000,null,	"LBL",	"doc", null, null, null);
		Util.creaNuevaFila(acciones, camino, tramite, 2010,null,	"MSG",	"La información se ha actualizado correctamente", "comunicacion|Aceptar", null, null);
		}
	public void verificarTramite() throws ErrorGotta  {
		if (acciones.getNumRegistrosCargados()==0)
			throw new ErrorTramiteNoExiste("El trámite "+camino+":"+tramite+" no está implementado");
		
		String idx;
		this.cumple = false;

		while (acciones.getRegistroActual()<acciones.getNumRegistrosCargados()) {
			idx = acciones.getValorCol("CD_Operacion")+this.idxDepuracion;
			motor.getApli().println("verificando"+idx);
			
			String condicion = Util.noNulo( acciones.getValorCol("condicion") );
			if (!condicion.equals(Constantes.CAD_VACIA)){
				if (condicion.equals("''") || condicion.equals("\"") || condicion.equals("else")) {
					/* pass */}
				else 
					motor.eval.verifica(condicion);
				}
			String accion=acciones.getValorCol("CD_Accion").toString();
			Fila linea = acciones.datos.get(acciones.getRegistroActual());
			Accion accver = Accion.getInstancia(accion, this);
			
			mapaAcciones.put(idx, accver);
			accver.montaTodo(linea);
			
			try	{
				accver.verificar(linea);
				}
			catch (ErrorGotta e)  {
				if (motor.getUsuario().tipoDepuracion.equals(Motor.TDepuracion.SIN_DEPURACION))
					throw new ErrorVerificandoAccion(e.getClass().getName() +": línea "+idx+" - "+e.getMessage());
				else 
					accver.msgError=e.getMessage();
				}
			acciones.setRegistroActual(acciones.getRegistroActual()+1);
			}
		this.acciones.setRegistroActual(0);
		this.fase=Constantes.TramitePreparadoYVerificado;
	}		
	
	public boolean seDebeEjecutar(String cond, Accion acc) throws ErrorEvaluador{
		boolean seCumple=this.cumple;
		
		if (cond==null || cond.equals(Constantes.CAD_VACIA)) { 
			seCumple = true;
			acc.resultadoCondicion="";
			}
		else if (cond.equals("\"")) { /* Misma condición */
			acc.resultadoCondicion=this.cumple;
			}
		else if (cond.equals("else")) {
			seCumple = !this.cumple;
			acc.resultadoCondicion=seCumple;
			}
		else {
			acc.resultadoCondicion=motor.eval.evalua(cond, true);
			if (acc.resultadoCondicion.equals(true)  || acc.resultadoCondicion.equals("1")) 
				seCumple=true; //debe devolver true o false
			else 
				seCumple=false;
			}
		return seCumple;
	}
	
	private Boolean hayQuePararEnLinea0(){	
		if (	this.tramPadre==null &&
				acciones.getRegistroActual()==0 && 
				this.interrumpirEnLinea0 && 
				!motor.getUsuario().tipoDepuracion.equals(Motor.TDepuracion.SIN_DEPURACION) )
			return true;
		else
			return false;
		}
	private Boolean hayQuePararEnLineaFinal(){	
		if (	acciones.getRegistroActual()==acciones.getNumRegistrosCargados() && 
				this.interrumpirEnLineaFinal && 
				!motor.getUsuario().tipoDepuracion.equals(Motor.TDepuracion.SIN_DEPURACION) )
			return true;
		if (	this.motor.etiquetaDOC && 
				this.interrumpirEnLineaFinal && 
				!motor.getUsuario().tipoDepuracion.equals(Motor.TDepuracion.SIN_DEPURACION) )
			return true;
		else
			return false;
		}
	private Boolean hayQueHacerPausaDepuracion(String siguienteIdx){
		if ((motor.getUsuario().tipoDepuracion==Motor.TDepuracion.PLAY && motor.getUsuario().existePuntoParo(siguienteIdx) ) ||
		 		  motor.getUsuario().tipoDepuracion==Motor.TDepuracion.PAUSA)
			return true;
		else
			return false;
		}
	
	public Resultado seguir() throws ErrorGotta  {	
		if (this.fase<Constantes.TramitePreparadoYVerificado) this.preparaYVerifica();
		
		Resultado retornar=Resultado.OK;
		String idx;
		if (this.fase<Constantes.TramiteAccionesEjecutadas) {
			while (acciones.getRegistroActual()<acciones.getNumRegistrosCargados()) {
				//interrumpimos en la linea 0 del trámite, si es que la depuración está enchufada
				if ( this.hayQuePararEnLinea0() ) {
					this.interrumpirEnLinea0=false;
					
					//evaluamos la siguiente para saber si va a pasar por ahí o no
					String siguienteIdx=acciones.getValorCol("CD_Operacion")+this.idxDepuracion;
					String siguienteCond=Util.noNulo( acciones.getValorCol("Condicion") );
					this.seDebeEjecutar( siguienteCond, this.mapaAcciones.get(siguienteIdx) );
					
					return Resultado.DEPURACION_PAUSA; 
					}
				
				String cond = Util.noNulo( acciones.getValorCol("Condicion") );
				idx = acciones.getValorCol("CD_Operacion")+this.idxDepuracion;

				Accion acc = this.mapaAcciones.get(idx);
				this.cumple=this.seDebeEjecutar(cond, acc);				
				motor.getApli().println("línea "+idx + " ¿hay que ejecutar? ("+cond+") -> "+this.cumple);
				
				//EMC cambio esto para los sub, para que al volver aquí estemos detrás de la llamada al SUB
				acciones.setRegistroActual(acciones.getRegistroActual()+1);
				acc.fueEjecutada=true;
				
				if (this.cumple) {
					try {
						retornar = 	acc.ejecutar();
						} 
					catch (ErrorUsuarioNoValido e){
						throw e;
						}
					catch (ErrorGotta e) {
						throw new ErrorGotta("Se ha producido un error al ejecutar la línea "+idx+": ",e);
						}
					}
				if (motor.etiquetaDOC && this.tipoEjecucion == TipoEjecucion.Ejecucion) {
					retornar=this.finalizar();
					
					//con esto se marca para que no haya un segundo volcado de datos
					this.tipoEjecucion=TipoEjecucion.Reemitir; 
					if (retornar != Resultado.OK) return retornar;
					break;
					}
				if (motor.etiquetaDOC && this.tipoEjecucion == TipoEjecucion.Revivir) {
					motor.etiquetaDOC=false;
					break;
					}
				
				if (retornar == Resultado.OK) {
					if ( acciones.getRegistroActual()<acciones.getNumRegistrosCargados() ){
						String siguienteIdx=acciones.getValorCol("CD_Operacion")+this.idxDepuracion;
						if (this.hayQueHacerPausaDepuracion(siguienteIdx) ) { 
							//evaluamos la siguiente para saber si va a pasar por ahí o no
							String siguienteCond=Util.noNulo( acciones.getValorCol("Condicion") );
							this.seDebeEjecutar( siguienteCond, this.mapaAcciones.get(siguienteIdx) );	
							return Resultado.DEPURACION_PAUSA; 
							}
						}
					}
				else if (retornar == Resultado.KO)
					return motor.salirEjecutaTramite(Resultado.KO);
				else
					return retornar; 
				}
			}
		
		if (this.tramPadre == null && !Util.en(this.tipoVagon, TipoVagon.SUB, TipoVagon.FOR, TipoVagon.EJE) )  {
			if (this.fase<Constantes.TramiteAccionesEjecutadas) 
				this.fase=Constantes.TramiteAccionesEjecutadas;
			Resultado ret = this.finalizar();
			if (ret!=Resultado.OK)
				return ret;
			if(motor.etiquetaDOC){
				motor.etiquetaDOC=false;
				this.fase=Constantes.TramiteIniciado;
				this.tipoEjecucion=TipoEjecucion.Reemitir;
				return this.seguir();
				}
			if(this.tipoEjecucion==TipoEjecucion.Revivir)
				ret=Resultado.SALIR;
			return ret;

			}
		return retornar;
	}
	
	public void hazCommit() throws ErrorCampoNoExiste, ErrorVolcandoDatos{
		/* ¡OJO! este commit es distinto del habitual en trámites 
		 * 			porque NO DESCARGA LAS FILAS EN MEMORIA ni 
		 * 			tampoco lanza los trámites EJE
		 * */
		motor.usuario.BeginTrans();
		try {
			DescargaFisica df = new DescargaFisica(this.motor);
			df.descargarTablas(motor.tablas);	
			motor.usuario.CommitTrans();
			this.untouchTablas(motor.tablas);
			} 
		catch (ErrorVolcandoDatos e)  {
			motor.usuario.añadeMSG("¡Se ha producido un error!", TipoMensajeGotta.errorSQL);
	
			if (this.motor.usuario.permisoDepuracion || this.motor.usuario.permisoMonitorSQL) 
				throw e; 
			else 
				throw new ErrorVolcandoDatos("Se ha producido un error al intentar guardar la información en la base de datos"); 
			}
		}
	public Resultado finalizar() throws ErrorGotta {
		motor.usuario.hayQueRefrescar=motor.hayQueRefrescar;
		
		if (tipoEjecucion==TipoEjecucion.Reemitir || tipoEjecucion==TipoEjecucion.Revivir)
			return motor.salirEjecutaTramite(Resultado.OK);

		if (this.fase<Constantes.TramiteDatosListosParaDescarga){
			ITabla tt=getTt();
			if (tt != null && this.tramEjecutado!=-1) {
				//comprobamos que la tarea sigue pendiente, por si varios han arrancado el trám a la vez
				
				tt.setRegistroActual(this.tramEjecutado);
				if (!tt.borrados.contains(tt.registroActual) )
					tt.setValorCol("InfoEjec", motor.montaInfo());//rellena la columna infoEjec de tabla tramites
				}
			//comprobar FU_Tramite
			ITabla tb=getTb();
			if (tb !=null) {
				motor.compruebaFU_Tramite(tb, tb.tdef.getCampoClave(), claveObjetoBase, fechaUltimoTramite);
				
				if (!tb.modificados.isEmpty() && tb.tdef.getColumnas().containsKey("fu_tramite"))
					tb.setValorCol("FU_Tramite", motor.fechaHoy());
				}
			this.fase=Constantes.TramiteDatosListosParaDescarga;
			}
		
		if (this.hayQuePararEnLineaFinal() && !this.motor.etiquetaDOC){
			this.interrumpirEnLineaFinal=false;
			//	NO HAY QUE EVALUAR la siguiente para saber si va a pasar (no hay más)
			return Resultado.DEPURACION_PAUSA; 
			}
		
		if (this.tramPadre==null){
			if (this.fase<Constantes.TramiteDatosFirmados) {
				this.fase=Constantes.TramiteDatosFirmados;
				if (this.lote.tramActivo().getFirma().archivosFirmar.size()>0 || this.firma.hayFirma() ) 
					return Motor.Resultado.FIRMA_ELECTRONICA_DATOS;
				}
			if (this.fase<Constantes.TramiteDatosDescargados){
				motor.usuario.BeginTrans();
				try {
					DescargaFisica df = new DescargaFisica(this.motor);
					df.descargarTablas(motor.tablas);
					if (this.tramitesEJE.size()==0) { //el de toda la vida, sin EJE
						motor.usuario.CommitTrans();
						this.untouchTablas(motor.tablas);
						}
					else if (this.tramitesEJE.size()>0) { //con EJE
						Conexion conex=motor.usuario.getConexion();
						Connection conn = conex.obtenerConexion();
						
						conex.conexionPreferente=conn;
						motor.usuario.conexionPreferente=conex;
						
						try {
							motor.usuario.ejecutaLoteSinCommit(conn);
							motor.usuario.SqlPendientes.clear();
							this.untouchTablas(motor.tablas); // si no pongo esto, se descargan varias veces los reg nuevos ¿?
							
							for (Tramite trEje: this.tramitesEJE) {
								this.lote.setTramActivo(trEje);
								
								trEje.acciones.setRegistroActual(0);//EMC 01/12/2008
								trEje.seguir();
								
								df.descargarTablas(motor.tablas);
								motor.usuario.ejecutaLoteSinCommit(conn);
								motor.usuario.SqlPendientes.clear();
								this.untouchTablas(motor.tablas);
								}
							
							conn.commit();
							motor.usuario.RollbackTrans();
							} 
						catch (SQLException e) {
							motor.usuario.añadeMSG("Error: "+e.getMessage(), TipoMensajeGotta.alerta);
							throw new ErrorVolcandoDatos(e.getMessage());
							}
						finally {
							try {
								conn.rollback();
								conex.conexionPreferente=null;
								motor.usuario.conexionPreferente=null;
								} 
							catch (SQLException e) {
								motor.usuario.getApli().println("ERROR muy grave"+e.getMessage());
								}
							conex.liberarConexion(conn);
							}
						}
					this.fase=Constantes.TramiteDatosDescargados;
					} 
				catch (ErrorVolcandoDatos e)  {
					borrarFicheros();

					motor.usuario.añadeMSG("¡Se ha producido un error!", TipoMensajeGotta.errorSQL);
					throw e;
					}
				}
				
			// recuperar el nodo nuevo
			if (this.fase<Constantes.TramiteFinalizado) {
				this.fase=Constantes.TramiteFinalizado;
				
				if (tramite.toLowerCase().startsWith("registro") && lote.recuperarNodo) {
					String nodoML = "nodo"+motor.usuario.arbol.ml;
					
					if ( getTb().tdef.getColumnas().containsKey( nodoML )) {
						CampoDef clave = getTb().tdef.getCampoClave();
						ArrayList<Object> valorClave = getTb().getValorCam( clave.getCd() );
						this._tb.cargarRegistros(clave, valorClave);
						
						String valor = getTb().getValorCam( nodoML ).get(0).toString();
						ArrayList<String>temp=Util.split(valor, Constantes.SEPARADOR);
						ArrayList<String> ret=new ArrayList<String>();
						for (String trozo :  temp) {
							if (!trozo.startsWith("="))
								ret.add(trozo);
							}
						
						motor.getUsuario().arbol.nodoActivo= Util.join(Constantes.SEPARADOR, Util.reverse(ret) ) ;
						motor.usuario.añadeMSG("Nodo nuevo:"+ret, TipoMensajeGotta.info);
						}
					}
				}
			}
		return Resultado.OK;
	}
	private void untouchTablas(HashMap<String,ITabla> tablas){
		//una vez que la transacción ha funcionado bien...
		for (ITabla t: tablas.values()){
			t.untouch();
			}
		}
	private void preparaYVerifica() throws ErrorGotta{
		motor.getUsuario().añadeMSG("Arrancamos trámite "+(lote.numTramite+1)+" de "+lote.tramites.size()+": "+this.camino+", "+this.tramite+" (objeto base:"+this.claveObjetoBase+")", Constantes.TipoMensajeGotta.tramite);
		if (this.tramite.startsWith("registr") || this.tramite.startsWith("eliminar")) 
			motor.hayQueRefrescar=true;

		lote.setVariable("~", Constantes.INTEGER, this.lote.tramites.size()); //total tramites
		lote.setVariable("$", Constantes.INTEGER, this.lote.numTramite); //tramite inicial
		for (Variable v : this.variablesExplorador.values())
			lote.setVariable(v.getNombre().toUpperCase(), v.getTipo(), v.getValor()).setModificada(false);
		
		this.lote.setVariable(Motor.CD_ERROR, Constantes.STRING, null);
		this.lote.setVariable(Motor.DS_ERROR, Constantes.STRING, null);
		
		// marca las tablas a cargar (las añade en tablas)
		this.verificarTramite();
		
		ITabla tb=this.getTb();
		if (tb != null )  {
			try {
				tb.limpiarRegistros(); //por si acaso es el segundo trámite de un lote
				if ( hayQueCargarTB() ) { // tabla base
					this.cargarTablas(tb, tb.tdef.getCampoClave(), this.claveObjetoBase);

					if (tb.getNumRegistrosCargados()!=1 && 
							this.claveObjetoBase!=null && 
							this.claveObjetoBase.size() != 0)  {
						motor.getApli().println("---------------No se ha cargado exactamente 1 registro");
						motor.getApli().println(tb.tdef.getCd() + ": " + this.claveObjetoBase);
						
						throw new ErrorTramite(tb.tdef.getCd() + ": " + this.claveObjetoBase +"\nNo se ha cargado exactamente 1 registro: verifique que las letras del detalle apuntan a una única fila de la tabla base.");
						}
					}
				}
			catch (ErrorCargandoTabla e) {
				/*pass*/}
			catch (ErrorCampoNoExiste e){
				/*pass*/}
								
			//Recoge la fecha último trámite.
			if (this.tipoEjecucion==Tramite.TipoEjecucion.Ejecucion && tb.tdef.getColumnas().containsKey("fu_tramite")) 
				this.fechaUltimoTramite=tb.getValorCol("FU_Tramite");
			
			// tabla de trámites
			ITabla tt=this.getTt();
			if (tt!=null)  {
				tt.limpiarRegistros(); //por si acaso es el segundo trámite de un lote
				if (this.getCargarTodosLosTramites())  {
					CampoDef camAux = tb.tdef.getCampoClave();
					ArrayList<Object> vAux = tb.getValorCam(camAux.getCd() );
					tt.cargarRegistros(camAux, vAux );
					}
	
				if ( this.claveObjetoPte == null ) {
					// En teoría, no tenemos que cargar ningún registro.
					// Sin embargo, ejecutamos el método en cualquier caso para porsi.
					if (this.tipoEjecucion == TipoEjecucion.Ejecucion || 
						this.tipoEjecucion==TipoEjecucion.DejarPen) {
						if ( !this.getCargarTodosLosTramites() && this.claveObjetoPte!= null) 
							tt.cargarRegistros(null, this.claveObjetoPte );
						
						this.dejarPendiente(this.tramite, this.camino, null, false);
						tramEjecutado=tt.registroActual;
						} 
					} 
				else  {
					// La clave pendiente no es nula, con lo cual hay que cargar
					// un sólo registro que se busca por la clave pendiente.
					if ( !this.getCargarTodosLosTramites()) {
						try{
							tt.cargarRegistros(null, this.claveObjetoPte);
							tramEjecutado=tt.registroActual;
							}
						catch (ErrorGotta e) {
							throw new ErrorCargandoTabla("No se ha podido cargar la tabla " + tt + " con los valores " + this.claveObjetoPte +". El error ha sido:\n" + e.getMessage());
							}
						compruebaTramiteSiguePendiente();
						}
					else  {
						compruebaSiTramiteEstaEjecutado(this.tipoEjecucion!=TipoEjecucion.Ejecucion);

						}
					}
				if (this.tipoEjecucion==TipoEjecucion.Ejecucion) {
					tt.setValorCol("f_ejecucion", motor.fechaHoy());
					tt.setValorCol("cd_usuario_ejecucion", motor.usuario.getLogin());
					}
				motor.interpretaInfo();
				motor.tablas.put(tt.tdef.getCd(), tt);
				}
			}
		if (this.tramPadre== null && this.tipoEjecucion == TipoEjecucion.Ejecucion)
			this.firma.activaFirmaSiProcede(this.camino, this.tramite, motor.getApli());
		else
			this.firma.setFirmaElectrónicaActivable(false);
		
		if (this.tipoEjecucion != TipoEjecucion.DejarPen)  {
			if (this.tipoEjecucion== TipoEjecucion.Reemitir) {
				try
					{this.verificaEtiqueta("DOC", true);}
				catch (ErrorLBLNoExiste e)
					{throw new ErrorLBLNoExiste("El trámite seleccionado no emite documentos");} 
				} 
			}		
	}
	private void compruebaTramiteSiguePendiente() throws ErrorTramPendienteNoEncontrado{
		if ( this._tt.getNumRegistrosCargados()!=1 ) 
			throw new ErrorTramPendienteNoEncontrado("No se ha encontrado el tramite pendiente: "+this.claveObjetoPte.toString());

		String f_eje=Util.noNulo( this._tt.getValorCol("F_Ejecucion"));
		if (!f_eje.equals("") && this.tipoEjecucion==TipoEjecucion.Ejecucion)
			throw new ErrorTramPendienteNoEncontrado("Esta tarea ya no está pendiente: "+this.claveObjetoPte.toString());
	}
	private void compruebaSiTramiteEstaEjecutado(boolean ejecutada) throws ErrorTramPendienteNoEncontrado, ErrorFechaIncorrecta, ErrorDiccionario{
		CampoDef camAux=this._tt.tdef.getCampoClave();
		if (!this._tt.tdef.getCampoClave().getColumnas().containsKey("F_Pendiente")){
			throw new ErrorDiccionario("La tabla de tramitación está mal definida: debe tener como clave las mismas columnas que la tabla base + F_Pendiente");
			}
		if ( !this._tt.findKey(this.claveObjetoPte, camAux.getCd(), true) ) 
			throw new ErrorTramPendienteNoEncontrado("No se ha encontrado el tramite pendiente: "+this.claveObjetoPte.toString());
		String f_eje=Util.noNulo( this._tt.getValorCol("F_Ejecucion"));
		if (f_eje.equals(Constantes.CAD_VACIA)==ejecutada)
			throw new ErrorTramPendienteNoEncontrado("Esta tarea no está ejecutada: "+this.claveObjetoPte.toString());
		this.tramEjecutado=this._tt.getRegistroActual();
	}
	private void borrarFicheros() throws ErrorEvaluador {
		for(int i = 0; i<this.getListaFicheros().size(); i++) {
			IDLLSubirFicheros d = this.getListaFicheros().get(i);
			File f = new File(this.motor.eval.evalua(d.getRetornoRuta()).toString());
			f.delete();
		}
	}
	private void cargarTablas(ITabla t, CampoDef cam, ArrayList<Object> valor) throws ErrorCargandoTabla, ErrorCampoNoExiste {
		//si no viene valor no cargamos registros
		if (valor==null || valor.isEmpty())
			return;
			
		//cargamos la tabla 	
		try {
			t.cargarRegistros(cam, valor);} 
		catch (ErrorCargandoTabla e1){
			/*no pasa nada*/} 
		catch (NumberFormatException e){
			throw new ErrorCargandoTabla("Error cargando tabla "+t.tdef.getCd()+ ":"+e.getMessage());} 
		
		for (ITabla tabla : motor.tablas.values()){
			if (!tabla.marcadaParaNoCargar){
			for (CampoDef campoDef:tabla.tdef.campos.values()){
				CampoDef ref=campoDef.getCampoReferencia() ;
				if (ref!=null && ref.getTabla()==t.tdef.getCd()) {
					if (this._tt!=null && tabla.tdef.equals(this._tt.tdef)) {
						//la tabla de trámites tiene una carga especial
						}
					else if (ref.getTabla().equalsIgnoreCase(t.tdef.getCd())) {
						//¿la tabla base? parece que está relacionada consigo misma
						}
					else
						tabla.cargarRegistros(campoDef, t.getValorCam(campoDef.getCampoReferencia().getCd()));
					break;
					}
				}
			}
		}
	}
	

	//Añade un nuevo registro en la tabla de tramites, sin rellenar la fecha de ejecución.
	public void dejarPendiente(String codtram, Long camino1, FechaGotta fVencimiento, boolean critico) throws ErrorCampoNoExiste, ErrorTiposNoCoinciden, ErrorArrancandoAplicacion {
		if (this.lote.fpen==null)
			this.lote.fpen = this.motor.fechaHoy();
		else {
			this.lote.fpen=(FechaGotta) this.lote.fpen.clone();
			if (this.motor.getApli().getUsarMilis())
				this.lote.fpen.add(Calendar.MILLISECOND,1);
			else
				this.lote.fpen.add(Calendar.SECOND,1);
			}
		try {
			_tt.addNew();

			CampoDef camAux=_tb.tdef.getCampoClave();
			for (int i=0; i<camAux.numColumnas(); i++){
				ColumnaDef col = _tt.tdef.getCampoClave().getColumna(i);
				String nombrecol =  col.getCd();
				Object valorcol =  _tb.getValorCol(nombrecol);
				_tt.setValorCol(nombrecol, valorcol);
				}
			_tt.setValorCol(Constantes.CD_Tramite, codtram);
			_tt.setValorCol("CD_Usuario_Pendiente", motor.getUsuario().getLogin());
			_tt.setValorCol("F_Pendiente",this.lote.fpen.clone());
			_tt.setValorCol("CD_Camino", camino1);
			_tt.setValorCol("critico", critico);
			_tt.setValorCol(Constantes.CD_Tramite, codtram);
			if (fVencimiento!=null)
				_tt.setValorCol("F_vencimiento", fVencimiento);
			}
		catch (ErrorFilaNoExiste e){
			//pass
			} 
		catch (ErrorTiposNoCoinciden e) {
			throw new ErrorTiposNoCoinciden("Error al dejar pendiente: Los tipos de datos no coinciden en alguna columna de la tabla de tramitación '"+_tt.tdef.getCd()+"': "+e.getMessage());
			}
		}
	
	public void verificaEtiqueta(String lbl, boolean saltar) throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado {
		//reseteamos etiquetas
		this.etiquetaVerificada=Constantes.CAD_VACIA;
		for (Tramite tr : this.tramitesSUB.values()){
			tr.etiquetaVerificada=Constantes.CAD_VACIA;
			}
		Tramite temp=this;
		while (temp!=null && temp.tramPadre!=null){
			temp=temp.tramPadre;
			temp.etiquetaVerificada=Constantes.CAD_VACIA;
			}
		
		_verificaEtiqueta(lbl, saltar);
		}
	// verifica la existencia de una etiqueta y salta a ella	
	private void _verificaEtiqueta(String lbl, boolean saltar) throws ErrorLBLNoExiste, ErrorTramiteActivoHaCambiado {
		if (lbl==null) 
			throw new ErrorLBLNoExiste("Falta etiqueta");
		
		if (this.etiquetaVerificada.equals(lbl) )
			throw new ErrorLBLNoExiste("ya verificada");
		this.etiquetaVerificada=lbl;
				
		ITabla t=getAcciones();
		int posAnterior=t.getRegistroActual();
		int n=0;
		boolean encontrada=false;
		String accion;
		String parametro;
		
		while (n < t.getNumRegistrosCargados()) {
			t.setRegistroActual(n);
			accion = (String)t.getValorCol("cd_accion");
			parametro = Util.noNulo(t.getValorCol("parametro1"));

			if (accion.equalsIgnoreCase("LBL") && parametro.equalsIgnoreCase(lbl))  {
				encontrada=true;
				break;
				}
			n++;
			}
		
		if (encontrada) {
			if (saltar)
				{/*en realidad ya estamos en el registro buscado*/}
			else
				t.setRegistroActual(posAnterior);
			
			motor.lote.devolverDepuradorCompleto=true;
			return;
			}
		else
			t.setRegistroActual(posAnterior);
		
		/*como no se ha encontrado, buscamos en el padre del trámite actual*/
		if (this.tramPadre!=null){
			try {
				this.tramPadre._verificaEtiqueta(lbl, saltar);
				if (saltar) {
					this.lote.setTramActivo(this.tramPadre);
					throw new ErrorTramiteActivoHaCambiado("");
					}
				return;
				} 
			catch (ErrorLBLNoExiste e) {
				//pass
				}
			}
		
		/*como no se ha encontrado, buscamos en los SUB asociados*/
		for (String stram : this.tramitesSUB.keySet()){
			try{
				Tramite tram=this.tramitesSUB.get(stram);
				try {
					tram._verificaEtiqueta(lbl, saltar);
					if (saltar){
						this.lote.setTramActivo(tram); //'activamos' el trámite
						tram.fase=Constantes.TramitePreparadoYVerificado;
						
						//y dejamos el trámite padre justo en esa línea
						//	stram='x'+CD_Operacion
						String CD_Operacion=stram.substring(1, stram.length());
						int numfila=t.findFirst("CD_Operacion", Integer.parseInt(CD_Operacion) );
						t.setRegistroActual(numfila+1);
						
						throw new ErrorTramiteActivoHaCambiado("");
						}
					}
				catch (ErrorTramiteActivoHaCambiado ee){
					if (saltar){
						//y dejamos el trámite padre justo en esa línea
						//	stram='x'+CD_Operacion
						String CD_Operacion=stram.substring(1, stram.length());
						int numfila=t.findFirst("CD_Operacion", Integer.parseInt(CD_Operacion) );
						t.setRegistroActual(numfila+1);
						}
					throw ee;
					}
				return;
				}
			catch (ErrorLBLNoExiste e){
				//pass
				}
			}
		
		throw new ErrorLBLNoExiste("La etiqueta "+lbl+" no existe");
	}
	
/////////////////////////////////////////////////////////////////
	// + info para el depurador de trámites

///////////////////////////////////////////////////////////////////////////////
	private String infoExtendida(){ 
		return "Tabla base: "+ (this._tb!=null ? this._tb.tdef.getCd(): "(no hay)")+
			(this._tt!=null ? " | Tabla de tram: "+ this._tt.tdef.getCd(): "")+
			" | Objeto base:"+ (this.claveObjetoBase!=null ? Util.join(Constantes.SEPARADOR, this.claveObjetoBase): "")+
			(this.claveObjetoPte!=null ? " | Objeto pend:"+ Util.join(Constantes.SEPARADOR, this.claveObjetoPte): "");		
		}
	
	public JSONObject JSON() throws JSONException{
		JSONObject ret = new JSONObject() ;
		
			ret.put("idx", this.idxDepuracion)
				.put("tramite", this.tramite)
				.put("camino", this.camino)
				.put("datosListosParaDescarga", this.fase==Constantes.TramiteDatosListosParaDescarga)			
				.put("datosDescargados", this.fase>=Constantes.TramiteDatosDescargados)
				.put("infoExtendida", this.infoExtendida())
				
				.put("tablaBase",     this._tb!=null? this._tb.tdef.cd : null)
				.put("tablaTramites", this._tt!=null? this._tt.tdef.cd : null)
				.put("tablaFirmas",   this._tf!=null? this._tf.tdef.cd : null)
				
				.put("filaActiva", acciones.getValorCol("CD_Operacion")+this.idxDepuracion )
				.put("acciones",listaAccionesJSON() );
		return ret;
		}
	public JSONObject evolucionJSON() throws JSONException{
		JSONObject ret = new JSONObject() ;
		ret.put("idx", this.idxDepuracion) ;
		ret.put("tramite", this.tramite) ;
		ret.put("camino", this.camino) ;
		ret.put("datosListosParaDescarga", this.fase==Constantes.TramiteDatosListosParaDescarga);			
		ret.put("datosDescargados", this.fase>=Constantes.TramiteDatosDescargados);
		if(acciones.getRegistroActual()<acciones.getNumRegistrosCargados())
			ret.put("filaActiva", this.acciones.getValorCol("CD_Operacion")+this.idxDepuracion);
		JSONArray jsacciones = new JSONArray();
		for (Fila f : this.acciones.datos) {
			String idx = f.get("CD_Operacion")+this.idxDepuracion;
			Accion acc =this.mapaAcciones.get(idx);
			JSONObject jsacc = new JSONObject();
			if (acc != null){
				jsacc.put("idx", idx);
				jsacc.put("ejecutada", acc.fueEjecutada);
				if(acc.resultadoCondicion==null)
					jsacc.put("resultCond", "") ;
				else
					jsacc.put("resultCond", acc.resultadoCondicion);
				ret.put("msgError", acc.msgError);
				}
			else{
				jsacc.put("idx", idx) ;
				jsacc.put("ejecutada", "") ;
				jsacc.put("resultCond", "") ;
				}
			jsacciones.put(jsacc);
			}
		ret.put("acciones",jsacciones);
		return ret;
		}
	private JSONArray listaAccionesJSON( ) throws JSONException {
		JSONArray ret = new JSONArray() ;
		
//		int i=0;
		for (Fila f : this.acciones.datos) {	
			String idx = f.get("CD_Operacion")+this.idxDepuracion;
			Accion acc =this.mapaAcciones.get(idx);
			ret.put(AccionJSON(acc, f) ); 
//			i++;
			}
		
		return ret;
		}
	
	private JSONObject AccionJSON(Accion acc, Fila f) throws JSONException {
		return AccionJSON(acc, this, 
				this.camino, this.tramite, f.gets("CD_Operacion"), 
				f.gets("condicion"), f.gets("CD_Accion"), 
				f.gets("Parametro1"), f.gets("Parametro2"), f.gets("Parametro3"), f.gets("Parametro4"));
		}
	public static JSONObject AccionJSON(Accion acc, Tramite tram,  
			Long CD_Camino, String CD_Tramite, String CD_Operacion,
			String condicion, String CD_Accion,
			String Parametro1, String Parametro2, String Parametro3, String Parametro4) throws JSONException{
		
		JSONObject ret=new JSONObject();
		String idx=CD_Operacion+tram.idxDepuracion;
		
		String accion=CD_Accion;
			ret.put("idx", idx);
			ret.put("camino",  CD_Camino) ;
			ret.put("tramite", CD_Tramite) ;
			
			ret.put("op", 		 CD_Operacion) ;
			ret.put("condicion", condicion)  ;
			ret.put("resultCond",(acc==null? "": acc.resultadoCondicion)) ;
			
			ret.put("ejecutada", (acc==null? false : acc.fueEjecutada)) ;
			ret.put("tienePausa", tram.motor.getUsuario().existePuntoParo(idx));
		
		if ( accion.equals(Constantes.SUB) || accion.equals(Constantes.FOR)) {
			Tramite tSUB=tram.tramitesSUB.get("x"+CD_Operacion);
			if (tSUB!=null)
				ret.put("comprime", tSUB.idxDepuracion ) ;
			}
		ret.put("accion", accion);
			
		ret.put("p1", Parametro1) ;
		ret.put("p2", Parametro2) ;
		ret.put("p3", Parametro3) ;
		ret.put("p4", Parametro4) ; 
		
		ret.put("msgError", (acc==null? null : acc.msgError));
		return ret;
		}
	
//	public void setNumeroLinea(String idx) {
//		String temp=idx.substring(0,idx.indexOf(Constantes.GUIÓN_BAJO));
//		long CD_Operacion=Long.parseLong(temp);
//		
//		//long idxActual=this.acciones.getRegistroActual();
//		/*int encontrado = */this.acciones.findFirst("CD_Operacion", CD_Operacion);
//
//	}
	public Tramite tramiteInicial(){
		Tramite tr=this;
		while (tr.tramPadre!=null)
			tr=tr.tramPadre;
		return tr;
		}
	
	@Override
	public String toString(){		
		String ret = this.tramite + "-" + this.camino+ " "+acciones.getRegistroActual()+"/"+acciones.getNumRegistrosCargados()+" - ";
		if(this.tramPadre!=null)
			ret+=">"+this.tramPadre.toString();
		return ret;
		}
	
///////////////////////////////////////////////////////////////
	public ArrayList<IDLLSubirFicheros> getListaFicheros() {
		return this.tramiteInicial().listaFicheros;
	}
	public void setCargarTodosLosTramites(boolean cargarTodosLosTramites) {
		this.tramiteInicial().cargarTodosLosTramites = cargarTodosLosTramites;
	}
	public boolean getCargarTodosLosTramites() {
		return this.tramiteInicial().cargarTodosLosTramites;
	}
	public void recargarAcciones() throws ErrorArrancandoAplicacion, ErrorGotta {
		int accActiva=this.acciones.getRegistroActual();
		this.leeAcciones();
		
		try {
			this.verificarTramite();
			}
		catch (ErrorGotta e){
			//pass
			}
		
		if (accActiva<=this.acciones.getNumRegistrosCargados())
			this.acciones.setRegistroActual(accActiva);
	}
}
