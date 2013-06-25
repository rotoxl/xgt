package es.burke.gotta;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

public class Motor {
	public static final String CD_ERROR = "@cd_error";
	public static final String DS_ERROR = "@ds_error";
	
	transient public ServletSubirArchivosProgress progresoSubida=null;
	
	public static enum Resultado {
		OK, KO, FRM, MSG, SALIR, 
		DEPURACION_PAUSA, 
		CAPTURA,
		FIRMA_ELECTRONICA_DATOS	;}
	
	public static enum TDepuracion {
		PLAY, PAUSA, SIN_DEPURACION;}
	
	public Usuario usuario;

	public FrmDinamico frmDinamico=null;
	public Evaluador eval;

	public Coleccion<ITabla> tablas;//
	
	boolean frm; /*si existe un formulario en activo*/
		
	public Lote lote;

	//datos del proceso de ejecución
	boolean errorCritico;
	boolean trans;
	boolean etiquetaDOC;
	String etiquetaOnError=null;
	
	public Object hayQueRefrescar=null;
	Object tc; //objeto que guarda la fecha último trámite.
	public Msg mensaje=null;
	public Captura cap=null;
	
	public boolean finalizado=false; //cuando se acaba su ciclo de vida, no se pueden borrar del todo hasta que no se hagan los refrescos

	public Motor(Usuario usuario) {
		this.usuario=usuario;

		this.tablas=new Coleccion<ITabla>();
		this.eval = new Evaluador(this);
		}
	public Aplicacion getApli() throws ErrorArrancandoAplicacion {
		return this.usuario.getApli();
		}
	public void sacaError(Object obj){
		try {
			this.getApli().log.error(obj);
			}
		catch (ErrorArrancandoAplicacion e) {
			//pass
			}
		}
	public Evaluador getEvaluador() {
		return this.eval;}
	public Usuario getUsuario() {
		return this.usuario;}
	private boolean soloContieneLetrasyNumeros(String s){
		for (int i=1; i<s.length(); i++){
			String letra=s.substring(i, i+1);
			if (esLetra(letra))
				continue;
			if (i>1 && !esDígito(letra))
				return false;
			}
		return true;
	}
	private boolean esLetra(String s){
		return ! s.toLowerCase().equals(s.toUpperCase()) || s.equals("_");
	}
	private boolean esDígito(String s){
		return "0123456789".contains(s);
	}
	
	public boolean esTablaTemporal(String s){
		if (s==null)
			return false;
		return s.startsWith("#");
		}
	public boolean esNombreVariable(String s) {
		if (s==null)
			return false;
		
		if (s.startsWith("@") && soloContieneLetrasyNumeros(s)) //hay que controlar si aquí viene una expresión
			return true;
		if (s.length()>1)
			return false;	
		if (s.equals("$")) 
			return true;
		if (s.equals("~")) 
			 return true;
		if (esLetra(s))  
			 return true;
		
		return false;
	}
	public boolean esCampo(String e) throws ErrorTablaNoExiste, ErrorCreandoTabla {
		try {
			return tablaCampo(e, false) != null;
			} 
		catch (ErrorCargandoTabla ee)  {
			return false;
			}	
		catch (ErrorCampoNoExiste ee) {
			return false;
			} 
		catch (ErrorArrancandoAplicacion ee) {
			return false;
			}	
	  }
	public boolean esCampo_Variable(String e) throws ErrorTablaNoExiste, ErrorCreandoTabla {
		if (e == null)
			return false;
		else if (esNombreVariable(e))
			return true;
		else if (esCampo(e))
			return true;
		else
			return false;	
		}
	public boolean existeCampo(String nombre) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion
		{return existeCampo(nombre, true);}
	public boolean existeCampo(String nombre, boolean car) throws ErrorTablaNoExiste {
		CampoDef cam;
		if (nombre.startsWith("*.#"))
			return true;
		try {
			cam = tablaCampo(nombre, car);
			this.sacaTabla(cam.getTabla());//.setSeDebeCargar(true);
			return true;
			} 
		catch (ErrorCargandoTabla e) 
			{return true;} 
		catch (ErrorCampoNoExiste e) 
			{return false;} 
		catch (ErrorCreandoTabla e) 
			{return false;}
		}

	public CampoDef tablaPuntoCampo(String tablaCampo) throws ErrorTablaNoExiste, ErrorCampoNoExiste, ErrorCreandoTabla, ErrorArrancandoAplicacion {
		ArrayList<String> v = Util.split(tablaCampo, ".");
		String t= v.get(0);
		String c= v.get(1);

		ITabla tRef = ponTabla(t);
		CampoDef cRef = tRef.tdef.getCampo(c);
		return cRef;
		}
	
	/*	Dado el nombre de un campo en alguno de los siguientes formatos:
			campo, .campo, *.tabla.campo, campo1.campo2, etc.
		Devuelve el objeto campo correspondiente al campo.	*/
	public CampoDef tablaCampo(String nombre) throws ErrorCargandoTabla, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorCreandoTabla
		{return tablaCampo(nombre, true);}
	
	public CampoDef tablaCampo(String nombre, boolean car) throws ErrorCargandoTabla, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorCreandoTabla {
		CampoDef campo = _tablaCampo(nombre, car);
	
		if (campo==null)
			throw new ErrorCampoNoExiste(nombre);
		return campo;
	}
	
	private CampoDef _tablaCampo(String nombre, boolean car) throws ErrorCargandoTabla, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorCreandoTabla{
		if (nombre==null || nombre.equals(Constantes.CAD_VACIA)) 
			return null;
		
		int comenzar = 0;
		ArrayList<String> v = Util.split(nombre, ".");
		ITablaDef tdef=null;

		if (v.get(0).equals(Constantes.CAD_VACIA)) {
			comenzar = 1;
			if (tramActivo().getTt() != null)
				tdef = tramActivo().getTt().tdef; //tabla tramites
			} 
		else if (v.get(0).equals("*")) {
			comenzar = 2;
			tdef = this.sacaTablaDef(v.get(1)); //tabla externa
			} 
		else {
			comenzar = 0;
			if (tramActivo().getTb()!=null)
				tdef = tramActivo().getTb().tdef; //tabla base
			}
		if (tdef==null) 
			return null;
		
		//si no está en lista de tablas la incluyo
		ITabla t = ponTabla(tdef);
		
		for (int i=comenzar; i<v.size()-1 ;i++ ) {
			String nombrecampo = v.get(i).toString();
			ArrayList<Object> valor = t.getValorCam(nombrecampo);
			CampoDef cRef=null;
			try	{
				cRef = t.getCampo(nombrecampo).getCampoReferencia();
				} 
			catch(Exception e){
				return null;
				}
			if (cRef != null) {
				tdef = t.getCampo(nombrecampo).getTablaReferencia();
				t = ponTabla(tdef);
				if(car){
					if (!t.findKey(valor,cRef.getCd())) 
						t.cargarRegistros(cRef, valor);
					}
				}
			}
		if (t!=null) {
			String tramo=v.get(v.size()-1);
			if (Util.en(tramo, Constantes.camposMagicos)){
				if (tramo.equalsIgnoreCase(Constantes.PK))
					return t.getCampo(t.tdef.getCampoClave().getCd());
				String tipo=Constantes.INTEGER;
				if (tramo.equalsIgnoreCase(Constantes.DS))
					tipo=Constantes.STRING;
				ColumnaDef col = new ColumnaDef(tramo,tramo,tipo,255,null);
				CampoDef cam = new CampoDef(tramo,tramo,t.tdef.getCd());
				cam.putColumna(col);
				return cam;
				}
			return t.getCampo(tramo);
			}
		return null;
		}
	
	public Tramite tramActivo()
		{return lote.tramActivo();}

	public boolean compruebaFU_Tramite(ITabla t, CampoDef c, ArrayList<Object> valor, Object fechaUltimoTramite) throws ErrorFU_Tramite, ErrorCampoNoExiste {
		if (t.modificados.isEmpty())
			return true;
		if (fechaUltimoTramite ==null)
			return true;

		String sql=((TablaDef)t.tdef).sqlCargarTabla(c, valor);
		
		int numInterrogantes=Util.cuentaCadena(sql, "?");
		if (numInterrogantes!=valor.size()){
			for (int i=valor.size();i<numInterrogantes;i++)
				valor.add(usuario.getLogin());
			}
		
		Conexion conn = usuario.getConexion();
		Filas v;
		try 
			{v = conn.lookUp(sql, valor);}
		catch (Exception e)
			{throw new ErrorFU_Tramite(e.getMessage());}
		
		if (v == null) return true;
		if (v.isEmpty())return true;

		Fila hs = v.get(0);
		Object fu = hs.get("FU_Tramite");

		if (fu==null)
			return true;
		if (fu.equals(Constantes.CAD_VACIA))
			return true;
		else if (fu.equals(fechaUltimoTramite))
			return true;
		else
			throw new ErrorFU_Tramite("Error de concurrencia: otro usuario ha modificado el registro que se iba a guardar.");
	}
	
	/*
	 * accion=X! ó X!?
	 * nombretablacampo=parametro1 (campo ó variable ó vacío)
	 * mensaje=parametro2 (etiqueta);
	 * opcional=parametro3 (si es opciona o no)
	 * tipo=parametro4
	*/
	public Motor.Resultado presentarPedirCampo( String accion, String pCampoVariable, String pMensaje, String pOpcional, String pTipo) throws ErrorGotta {
		//p1
		String campoVariable=pCampoVariable;
		if (campoVariable == "") 
			campoVariable = null;
		//p2
		String literal=pMensaje;
		if (literal=="")
			literal=null;
		
		//p3
		boolean opcional= false; JSONObject listaValores=null;
		if (pTipo!=null && pTipo.equalsIgnoreCase(Constantes.LIST)){
			//p3= {valor1:literal1, valor2:literal2...}
			//p3= nombreNivel
			//p3= @variable
			INivelDef nivelDef = this.getApli().getNivelDef(pOpcional.trim());
			if (nivelDef!=null) {
				List<Parametro> paramsDef = nivelDef.getColParams();
				ArrayList<Object> params = new ArrayList<Object>();
				for (Parametro pd:paramsDef){
					params.add(this.getValorSimbolo(pd.nombre).get(0));
					}
				Filas valores;
				try {
					valores = nivelDef.obtenerNivel().lookUp(usuario.getConexion(), params);
					} 
				catch (SQLException e1) {
					throw new ErrorGotta(e1);
					}
				listaValores=new JSONObject();
				for (Fila f:valores){
					try {
						listaValores.put(f.get(0).toString(),f.get(1));
						} 
					catch (JSONException e) {
						throw new ErrorAccionMSG(e.toString());
						}
					}
				}
			else {
				if (this.esCampo_Variable(pOpcional))
					pOpcional=eval.evalua(pOpcional).toString();
					
				try {
					listaValores=new JSONObject(pOpcional.trim());
					} 
				catch (JSONException e2) {
					throw new ErrorAccionMSG("'"+pOpcional+"' no es una expresión JSON válida ni un nivel existente ni un campo ni una variable");
					}
				}
			opcional=pOpcional.substring(0, 1).equals(Constantes.ESPACIO); 
			}
		else
			opcional=(pOpcional != null && !pOpcional.equals(""));
		//p4
		String tipo =null;
		String validacion = null;
		tipo = pTipo;
		int longitud = 0;
			
		if (tipo==null || tipo.equals(Constantes.CAD_VACIA)) 
			tipo = null;
		else {
			if (tipo.contains("*")) {//TipoDato*longitud
				ArrayList<String>tl=Util.split(tipo, "*");
				tipo=tl.get(0);
				if (Util.isNumeric( tl.get(1) ) )
					longitud=Integer.parseInt( tl.get(1) );
				}
			
			if (tipo.contains(Constantes.PUNTO)) {
                    ArrayList<String> tipoyval = Util.splitTrim(tipo, Constantes.PUNTO, true);
				// Para validaciones del tipo String.nif o 25.nif (en este caso 25 es el número de caracteres)
				if(Util.en(tipoyval.get(0).toString(),Constantes.tiposGotta) || Util.isNumeric(tipoyval.get(0).toString())) {
					validacion = tipoyval.get(1).toString();
					tipo = tipoyval.get(0).toString();
					}
               	}
			tipo=tipo.toLowerCase();
          	}

		boolean bloqueado = accion.equals("X!");
//		if (accion.equals("X!"))
//			opcional = false;


		ArrayList<Object> valores=new ArrayList<Object>();
		Object dondeLoGuardo=null;

		CampoDef cam =null, cRef=null;
		ITablaDef tRef=null;
		ColumnaDef colDes=null;

		if (this.esCampo(campoVariable))
			cam = tablaCampo(campoVariable);

		if (esNombreVariable(literal))
			literal = eval.evalua(literal).toString();
		else if (literal==null && cam != null)
			literal = cam.getDs();


		String infoBloqueo=null;
		if (campoVariable==null && tipo!=Constantes.FILE){
			tipo = Constantes.CAD_VACIA;
			opcional = true;
			}
		else {
			if (cam!=null){
				if (tipo==null || Util.isNumeric(tipo)){
					if ( cam.numColumnas()==1 ) {
						tipo = cam.getColumna(0).getTipo();
						longitud=(Util.isNumeric(tipo)?Integer.parseInt(tipo):cam.getColumna(0).getLongitud());
						validacion=cam.getColumna(0).getSubtipo();
						}
					else 
						tipo=Constantes.STRING;
					}
				else {
					if (cam.numColumnas()==1) 
						longitud=cam.getColumna(0).getLongitud();
					}
				}
			if (!frm){
				if (tipo!=null && tipo.equals(Constantes.BOOLEAN) && accion.equals(Constantes.Xsi)){ 
					//lo interpreto con una acción MSG
					if (this.esCampo_Variable(campoVariable)){		
						presentarMensaje(literal, "67", campoVariable);
						return Resultado.MSG;
						}
					}
				}

			if (campoVariable==null){
				//separador
				campoVariable="separador_"+new Double(Math.random()*10000);
				if (pMensaje==null)
					literal=Constantes.CAD_VACIA;
				else if(pMensaje.length()==1)
					literal=eval.evalua(pMensaje).toString();
				else
					literal=pMensaje;
				}
			else if (esNombreVariable(campoVariable)) {
				Variable vVar;
				if (tipo==null)	{ /*si la variable ya existe en el motor, 
									debería retomarla tal cual*/
					Variable vv=this.lote.getVariable(campoVariable);
					if (vv!=null){
						tipo=vv.getTipo();
						
						valores.add(vv.getValor());
						longitud=vv.getValor().toString().length();
						}
					} 
				else {
					ArrayList<String> v=Util.split(tipo);
					String tabla = v.get(0);
					if (getEsquema().existeTabla(tabla )||tipo.contains(Constantes.PUNTO)) {//"tabla.campo" o "tabla.campo 1 2"
								if(getEsquema().existeTabla(tabla)){
									tRef=getEsquema().getTablaDef(tabla);
									}
								else{
									CampoDef _campo = this.tablaPuntoCampo(v.get(0));
									tRef = this.tablas.get(_campo.getTabla()).tdef;
									colDes = _campo.getColumna(0);
									}
								if (v.size()>2 && Util.isNumeric(v.get(1)) && Util.isNumeric(v.get(2))){
									tipo=null;
									infoBloqueo=v.get(1) + Constantes.ESPACIO +v.get(2);
									}
								else if (v.size()>1)
									tipo=v.get(1);
								cRef=tRef.getCampoClave();
								if (!this.usuario.getApli().variablesFortuny || campoVariable.length()>1 ) {
									//variables catellana
									if (!lote.variables.containsKey(campoVariable))
										lote.setVariable(campoVariable,Constantes.STRING,Constantes.CAD_VACIA);
									vVar=lote.getVariable(campoVariable);
									valores=Util.splitObjetos(Util.noNulo(vVar.getValor()), Variable.SEPARADOR_VARIABLES_LARGAS);
									
									int val = valores.size(); //completamos el número de trozos
									for(int i=val; i<cRef.numColumnas(); i++) 
										valores.add(Constantes.CAD_VACIA);
									}
								else { //variables fortuny
									for (int i=0 ; i<cRef.numColumnas() ; i++ )	{
										String letra=Util.chr(Util.asc(campoVariable)+i);
										if(letra.equals("@"))letra=campoVariable;
										Object valor;
										if(lote.getVariable(letra)==null)
											valor=null;
										else
											valor=lote.getVariable(letra).getValor();
										lote.setVariable(letra,cRef.getColumna(i).getTipo(),valor);
										vVar=lote.getVariable(letra);
										valores.add(vVar.getValor());
										}
									}
							}
					
					else if (Util.en(tipo, Constantes.OPTION, Constantes.BOOLEAN)){
						
								if ( !lote.variables.containsKey(campoVariable) )
									lote.setVariable(campoVariable, Constantes.BOOLEAN);
								vVar = lote.getVariable(campoVariable);
								
								if ( vVar.getValor() instanceof String ){
									if ( vVar.getValor().equals("0") )
										valores.add(false);
									else if ( vVar.getValor().equals("1") )
										valores.add(true);
									}
								else if ( vVar.getValor() instanceof Boolean ){
									valores.add(vVar.getValor());
									}
								else
									valores.add(vVar.getValor()!=null);
								}
					else if (v.size()==1) { // Un tipo, la long máx de una cadena o los bloqueos //un valor numérico o un tipo de dato
							if (Util.isNumeric(v.get(0))) {
								tipo=Constantes.STRING;
								longitud = Integer.parseInt(v.get(0));
								}
							else {
								tipo=v.get(0);
								if (longitud==0) longitud=longitudPorTipo(tipo);
								}
							}

					else if (v.size()==2) //dos valores numéricos separados por espacios
							infoBloqueo=tipo;
						
					if (!lote.variables.containsKey(campoVariable))
						lote.setVariable(campoVariable, tipo);
					vVar = lote.getVariable(campoVariable);
					if (valores.size()==0)
						valores.add(vVar.getValor());
					dondeLoGuardo=vVar;
					}
				}
			else {// dondeLoGuardo es un CAMPO o constante
				if (bloqueado && cam == null){ //se trata de una cadena
					valores.add(eval.evalua(campoVariable).toString());
					tipo = Constantes.STRING;
					}
				else if (cam != null){
					ITabla t = ponTabla(sacaTablaDef(cam.getTabla()));
					valores=t.getValorCam(cam.getCd());

					if (cam.getTablaReferencia() != null){
						tRef = ponTabla(cam.getTablaReferencia()).tdef;
						cRef = cam.getCampoReferencia();
						
						if (cam.getColumnaDescripcion()==null)
							throw new ErrorDiccionario("Error en el diccionario: no se ha indicado el dato 'ColumnaDescripcion' para la tabla '"+cam.getTablaReferencia().getCd()+"'");
						colDes=cam.getColumnaDescripcion();
						}
					if (cam.numColumnas()==1 && cam.getColumna(0).getTipo().equals(Constantes.BOOLEAN) && valores.get(0)==null){ 
						 valores.set(0, false); 
						 } 
					dondeLoGuardo = cam;
					}
				if (tipo!=null && tipo.contains(Constantes.ESPACIO))
					infoBloqueo=tipo;
				}
			}
		
		if (!this.frm) this.frmDinamico=new FrmDinamico(usuario);
		this.frmDinamico.anadirDatoMD(usuario, campoVariable, tipo, longitud, infoBloqueo, 
					valores, literal, bloqueado, opcional, 
					dondeLoGuardo, tRef, cRef, colDes, validacion, listaValores);

		if (!this.frm)
			return Resultado.FRM;
		return Resultado.OK;
			}
	private int longitudPorTipo(String tipo)
	{
		if (tipo.equals(Constantes.MEMO))
			return 0;
		if (tipo.equals(Constantes.INTEGER))
			return 8;
		if (Util.en(tipo, Constantes.LONG, "Double", Constantes.CURRENCY, "Decimal"))
			return 30;
		
		return 250;
	}
	public Resultado salirEjecutaTramite(Resultado ret) {
		if (usuario.transaccionEnCurso)
			usuario.RollbackTrans();
		return ret;
		}

	public ArrayList<Object> getValorSimbolo(String nombre) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorArrancandoAplicacion, ErrorCreandoTabla {
		ArrayList<Object> v = new ArrayList<Object>();
		if (esNombreVariable(nombre)) {
			Variable var = lote.getVariable(nombre);
			if (var != null) {
				if (var.getValor()==null)
					v.add(Constantes.CAD_VACIA);
				else 
					v.add(var.getValor());
				}
			else {//si no existe la incluyo
				lote.setVariable(nombre, Constantes.STRING, Constantes.CAD_VACIA);
				v.add(Constantes.CAD_VACIA);
				}
		}
		else {
			//CampoDef c = Util.valorSimbolo(this, nombre, tramActivo().getTb(), tramActivo().getTt() );
			CampoDef c;
			try {
				c = _tablaCampo(nombre, true);
				if (c!=null) {
					ITabla t = sacaTabla(c.getTabla());
					v = t.getValorCam(c.getCd());
					}
				}
			catch (ErrorCargandoTabla e) {
				//pass
				}
			}
		return v;
		}
	public void setValorSimbolo(String campoVariable, String valor) throws ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden, ErrorTablaNoExiste, ErrorCargandoTabla, ErrorCreandoTabla, ErrorArrancandoAplicacion {
		if (campoVariable!=null && campoVariable!=Constantes.CAD_VACIA){
	        if (esNombreVariable(campoVariable)) 
	        	lote.setVariable(campoVariable, Constantes.STRING, valor);
	        else {//es un campo
	            CampoDef cam = tablaCampo(campoVariable);
	            ITabla t = ponTabla(cam.getTabla());
	            
	            ArrayList<Object> v = new ArrayList<Object>();
	            v.add(valor);
	            t.setValorCam(cam.getCd(), v);
	        	}
			}
		}
	public void presentarMensaje(Object vtexto, String tipoMensaje, Object var_retorno ) throws ErrorAccionMSG, ErrorArrancandoAplicacion {
		String texto;
		Variable varRetorno;
		if (vtexto instanceof Variable){
			Variable var = (Variable) vtexto;
			texto = var.getValor().toString();
			}
		else 
			texto = vtexto.toString();

		if (var_retorno !=null && !(var_retorno.equals(Constantes.CAD_VACIA)))
			varRetorno = lote.setVariable(var_retorno.toString(), Constantes.BOOLEAN);
		else 
			varRetorno = null;
		
		this.mensaje=new Msg(this, texto, tipoMensaje, varRetorno) ;
	}

	/*Rellena el contenido de la columna infoEjec de la tabla de trámites*/
	public String montaInfo() throws ErrorCampoNoExiste {
		if (tramActivo().getTt().tdef.getColumna("InfoEjec")==null) return Constantes.CAD_VACIA;

		int longitud=tramActivo().getTt().tdef.getColumna("InfoEjec").getLongitud();

		String infoEjec=Constantes.CAD_VACIA;
		Variable var;

		Iterator<Variable> e = lote.variables.values().iterator();
		while (e.hasNext()) {
			var = e.next();
			if (var !=null && !var.esDeSistema){
				infoEjec += var.getNombre()+Constantes.SEPARADOR+var.getTipo()+Constantes.SEPARADOR+var.getValor()+Constantes.PIPE;
				}
			}

		if (infoEjec.length()>longitud){
			infoEjec=infoEjec.substring(0,longitud-1);
			}
		return infoEjec;
		}

	/*Interpreta el contenido de la columna infoEjec de la tabla de trámites*/
	public void interpretaInfo() throws ErrorArrancandoAplicacion {
		Object colInfoEjec=tramActivo().getTt().getValorCol("InfoEjec");
		
		String infoEjec=Constantes.CAD_VACIA;
		if (colInfoEjec != null)
			infoEjec=colInfoEjec.toString();

		ArrayList<String> lineas = Util.split(infoEjec,Constantes.PIPE);
		ArrayList<String> partes;
		String tipo;

		for (int n=0;n<lineas.size();n++) {
			partes = Util.split(lineas.get(n),Constantes.SEPARADOR);
			if (partes.size()<3)
				continue;
			if (partes.get(1) != null)
				tipo = partes.get(1);
			else 
				tipo =Constantes.STRING;

			lote.setVariable(partes.get(0), tipo, partes.get(2));
			}
		}

	public ITablaDef sacaTablaDef(String n) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion{
		if (this.tablas.containsKey(n)) 
		 	return this.tablas.get(n).tdef; 
		return getEsquema().getTablaDef(n);
		}
	public ITabla sacaTabla(String nombretabla){
		return this.tablas.get(nombretabla);
		}
	
	public ITabla ponTabla(String nombre) throws ErrorCreandoTabla, ErrorTablaNoExiste, ErrorArrancandoAplicacion
		{return ponTabla(sacaTablaDef(nombre));}
	
	public ITabla ponTabla(ITablaDef tdef) throws ErrorCreandoTabla  {
		if (tdef==null) return null;
		ITabla t=tablas.get(tdef.getCd());
		if (t==null) {
			t = tdef.newTabla(usuario);
			tablas.put(tdef.getCd(), t);
			}
		return t;
	}	

	public Resultado accionFRM(String accion, String caption) throws ErrorAccionFRM{
		return accionFRM(accion, null, null, null, caption, null);
		}
	public Resultado accionFRM(String tipo, String modoDetalle, String url, String numControl, String caption, String etiquetasSalto) throws ErrorAccionFRM {
		String accion=tipo;
		accion=accion.toLowerCase();
		if (accion.equals("fin")) {
			if (!this.frm)
				throw new ErrorAccionFRM("Acciones FRM no coordinadas");
			this.frm=false;
			this.mensaje=null;
			return Resultado.FRM;
			}
		else {
			if (this.frm)
				throw new ErrorAccionFRM("Acciones FRM no coordinadas");

			this.frmDinamico=new FrmDinamico(usuario, modoDetalle, url, numControl, caption, etiquetasSalto);
			this.frm=true;
			return Resultado.OK;
			}
		}
	public FechaGotta fechaHoy() {
		try {
			return usuario.getConexion().fechaServidor();
			} 
		catch (ErrorConexionPerdida e) {
			usuario.sacaError(e);
			} 
		catch (ErrorArrancandoAplicacion e) {
			usuario.sacaError(e);
			} 
		catch (ErrorFechaIncorrecta e) {
			usuario.sacaError(e);
			}	
		return null;
		}
	
	public Esquema getEsquema() throws ErrorArrancandoAplicacion
		{return usuario.getApli().getEsquema();}

	public Msg getMensaje() {
		return mensaje;
	}
	public Captura getCaptura() {
		return cap;
	}
}
