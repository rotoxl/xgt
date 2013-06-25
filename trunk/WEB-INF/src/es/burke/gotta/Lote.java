package es.burke.gotta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.Motor.Resultado;
import es.burke.gotta.Tramite.TipoEjecucion;
import es.burke.gotta.dll.DLLGotta;

public class Lote  {
	public Motor motor;
	ArrayList<Tramite> tramites=new ArrayList<Tramite>();
	public int numTramite;
	private Tramite tramActivo;
	
	public Variables variables= new Variables();

	boolean recuperarNodo =false;
	public String idxBloqueo=null;

	public FechaGotta fpen=null; /*ultima fecha pendiente*/
	
	public boolean devolverDepuradorCompleto=false;

	public Coleccion<String> guiones= new Coleccion<String>();
	
	//las filas pinchadas al iniciar el lote, para marcarlas de nuevo al acabar
	public ArrayList<String> filasPinchadas=new ArrayList<String>(); 
	public ArrayList<String> listaFlotantes=new ArrayList<String>();
	public HashMap<String, String> infoControles=new HashMap<String,String>();

	public Object cerrarFlotantes=null;
	
	public Filas archivosFirmados;
	
	public Tramite tramiteInicial() {
		return this.tramActivo.tramiteInicial();
		}
	
	private static ArrayList<ArrayList<String>> generaGruposFilasPinchadas(HttpServletRequest req, Lote lote) {
		ArrayList< ArrayList<String> > fp=new ArrayList< ArrayList<String> >();
		Enumeration<?> eParams=req.getParameterNames();
		while (eParams.hasMoreElements())  {
			String nomParam=(String)eParams.nextElement();
			if (nomParam.startsWith("checkbox")) {
				String valor=Util.obtenValor(req, nomParam);
				ArrayList<String> vvalor=creaAL( valor );
				
				for (String s : vvalor ) 
					lote.filasPinchadas.add(s);
					
				fp.add(vvalor);
				}
			}
		if (fp.size()>0)
			fp=Util.cartesiano(fp);
		
		return fp;
	}
	private static ArrayList<String> creaAL(String str){
		String sep="|-|-|"; //inicialmente el separador era una coma, pero a Alfredo eso no le vale
		ArrayList<String> ret= new ArrayList<String>();
		if (str.contains(sep)) 
			ret=Util.split(str, sep);
		else
			ret.add(str);
		
		return ret;
		}
	public static Lote montaLote(Usuario usr, Long camino, String tramite) throws ErrorCargandoTabla, ErrorDiccionario, ErrorTiposNoCoinciden, ErrorNumeroIncorrecto, ErrorGotta{//para trámites especiales, gtLogin y demás
		Motor nuevoMotor= new Motor(usr);
		usr.setMotor( nuevoMotor );
		nuevoMotor.hayQueRefrescar=null;
		
		Lote nl=new Lote(null, nuevoMotor, "-0");
		usr.getMotor().lote=nl;
		nl.tramites.add( new Tramite(nl, camino, tramite, new ArrayList<Object>(), new Coleccion<Variable>()));
		return nl;
	}
	public static Lote montaLoteFicticio(Usuario usr, Long camino, String tramite) throws ErrorCargandoTabla, ErrorDiccionario, ErrorTiposNoCoinciden, ErrorNumeroIncorrecto, ErrorGotta{//para trámites especiales, gtLogin y demás
		Motor nuevoMotor= new Motor(usr);
		Lote nl=new Lote(null, nuevoMotor, "-0");
		nuevoMotor.lote=nl;
		
		nl.tramites.add( new Tramite(nl, camino, tramite, new ArrayList<Object>(), new Coleccion<Variable>()));
		return nl;
	}
	public static Lote montaLote(HttpServletRequest req, Usuario usr, NodoActivo na) throws ErrorListaDeTramitesMalHecha, ErrorGotta, ErrorHayQuePincharFilas {
		boolean tramiteDeBotón=false;
		usr.hayQueRefrescar=null;
		String camino= Util.desformatearNumero(req.getParameter("camino")); // trámites de botón
		
		String tramite=Util.obtenValor(req, "tramite");
		String sTipoEjecucion=Util.obtenValorOpcional(req, "tipoEjecucion");
		TipoEjecucion tipoEjecucion = TipoEjecucion.Ejecucion;
		if (sTipoEjecucion==null)
			tipoEjecucion = TipoEjecucion.Ejecucion;
		 else if (sTipoEjecucion.equalsIgnoreCase("Reemitir"))
			tipoEjecucion = TipoEjecucion.Reemitir;
		 else if (sTipoEjecucion.equalsIgnoreCase("Revivir"))
			tipoEjecucion = TipoEjecucion.Revivir;
		
		String idxBloqueo=Util.obtenValor(req, "idxBloqueo");
		
		boolean altaDinamica =Util.obtenValorOpcionalBoolean(req, "altaDinamica");
		boolean tramiteVinculado =Util.obtenValorOpcionalBoolean(req,"tramiteVinculado"); 
		
		//Variables:
		Coleccion<Variable> variablesArbol=new Coleccion<Variable>(true);
		
		if (!altaDinamica && !tramiteVinculado)
			usr.borraMotores();
		
		Motor nuevoMotor= new Motor(usr);
		usr.setMotor( nuevoMotor );
		nuevoMotor.hayQueRefrescar=null;
		usr.productosGenerados = new Coleccion< DLLGotta>();
		usr.productosEnviados = new HashMap<String, DLLGotta>();
		
		if (tramite.equals(""))
			tramite=null;
		else if (tramite.equalsIgnoreCase("AutoExec") || tramite.equalsIgnoreCase("gtUsuario")) {
			Lote nl=new Lote(null, usr.getMotor(), idxBloqueo);
			if (usr.nodoArranque!=null && usr.nodoArranque.ml!=null)
				nl.setVariable("@", Constantes.STRING, usr.nodoArranque.ml);
			else if (usr.arbol!=null)
				nl.setVariable("@", Constantes.STRING, usr.arbol.ml);
			else
				nl.setVariable("@", Constantes.STRING, Constantes.CAD_VACIA);
			usr.getMotor().lote=nl;
			
			Filas tramEspecial = GestorMetaDatos.leeTramiteEspecial(usr.getConexion(), tramite, usr.getApli().getEsquema());
			Long cam=null;
			
			if (tramEspecial!=null) {
				cam=Long.parseLong(tramEspecial.get(0).gets("CD_Camino"));
				tramite=tramEspecial.get(0).gets("CD_Tramite");
				}
			nl.tramites.add( new Tramite(nl, cam, tramite, new ArrayList<Object>(), new Coleccion<Variable>()));
			return nl;
			}
		if ( tramite!= null )  {
			tramiteDeBotón=true;
			if (camino==null || camino.equals(Constantes.CAD_VACIA) ){
				String[] tramitesMantenimiento={"Registro", "Modificar", "Eliminar"}; 
				Boolean esDeMantenimiento=Util.en(tramite, tramitesMantenimiento);
				if (na.nombretb==null && esDeMantenimiento)
					throw new ErrorFaltanDatos("No se ha indicado tabla base para el detalle, así que no se puede encontrar el camino.");
				camino= Util.toString(GestorMetaDatos.sacaCaminoMantenimiento(usr.getConexion(), usr.getApli().getEsquema(), na.nombretb));
				
				if (camino==null && esDeMantenimiento)
					throw new ErrorTramiteNoExiste("No se ha encontrado el camino de mantenimiento (hay que definirlo con la descripción 'Mantenimiento ...' y la misma tabla base que el detalle).");
				}
			}
		
		ArrayList<Tramite> tramites=new ArrayList<Tramite>();
		HashMap<String, String> nodoActual = new HashMap<String, String>();
		nodoActual= Util.nodoAHash( na.nodoActivo );

		//Variables especiales
		//Nodo activo.
		Variable var; 
		for ( String k : nodoActual.keySet() ) {
			String o = nodoActual.get(k);
			var = new Variable(k, Constantes.STRING, o);
			variablesArbol.put(k, var);
			}
			
		if (altaDinamica) {
			tramite = GestorMetaDatos.leeRegistro(usr.getConexion(), Long.parseLong(camino), usr.getApli().getEsquema());

			int i=0;
			while (req.getParameterMap().containsKey("valor"+i)) {
				String valor=Util.obtenValor(req, "valor"+i);
				
				String letra=Util.chr(Util.asc("a")+i);
				variablesArbol.put( letra, new Variable(letra, Constantes.STRING, valor));
				i++;
				}
			}
		
		Lote nl=new Lote(null, usr.getMotor(), idxBloqueo);
		usr.getMotor().lote=nl;
		nl.setVariable("@", Constantes.STRING,na.ml!=null? na.ml.getCd():Constantes.CAD_VACIA);
		ArrayList< ArrayList<String> > gruposFilasPinchadas=generaGruposFilasPinchadas(req, nl);
		
		ArrayList<Object>claveTB; //para la clave de la tabla base (clave)
		ArrayList<Object>claveTT; //para la clave de la tabla de tramitación (clave pte)
		
		if (!tramiteDeBotón)  {
			if ( gruposFilasPinchadas.isEmpty() ) 
				throw new ErrorHayQuePincharFilas("Debe seleccionar alguna fila de la lista");			   
				
			boolean tramiteycamino=false;
			for (ArrayList<String> grupo : gruposFilasPinchadas) {// valor = 347134·16/06/2005 22:39:41\bOtrosValores
												// valor = tlAECF_BECF·510
				claveTB=new ArrayList<Object>(); // ojo, dentro del bucle
				claveTT=new ArrayList<Object>();
				
				String valor=null, tempValor;
				String modo=null;
				for (int i=0; i<grupo.size(); i++){
					tempValor=grupo.get(i);
					
					modo=tempValor.substring(0,2).toLowerCase();
					if ( Util.en(modo, "pb","tp","te","tl","lt") ){
						valor=tempValor.substring(2);
						break;
						}
					modo=null;
					}
					
				ArrayList<String> restoParametros=Util.split(valor, "\\");
				valor=restoParametros.get(0); 	// valor = 347134·16/06/2005 22:39:41
				restoParametros.remove(0);
				Coleccion<Variable> variablesFila = montaVariablesFila(variablesArbol, restoParametros);
				
				if ( modo==null ) {
					// aquí no debería llegar, se supone...
					}
				else if ( modo.equals("pb") )  { // pb · ClaveObjetoBase · Fecha_pendiente · Camino · Trámite
					claveTT=Util.splitObjetos(valor, Constantes.PUNTO3); 
					camino=claveTT.get(claveTT.size()-2).toString();
					tramite=claveTT.get(claveTT.size()-1).toString();
					claveTT=Util.getElementos(claveTT, claveTT.size()-2);//quita tramite y camino
					claveTB=Util.getElementos(claveTT, claveTT.size()-1);//quita fechapendiente
					}
				else if (modo.equals("lt"))  {
					if (!tramiteycamino) {
						tramiteycamino=true;
						
						ArrayList<Object> _nodo = NodoActivo.nodoALista( usr.arbol.nodoActivo );
						if (_nodo.size()<2) 
								throw new ErrorListaDeTramitesMalHecha("No es posible establecer el camino y el tramite para el nodo actual. Verifique que el nodo proporciona valor para dos letras.");
							
						if (_nodo.size() >= 2) {
							camino=(String)_nodo.get(1);								
							tramite=(String)_nodo.get(0);
							}
						}

					claveTT=Util.splitObjetos(valor, Constantes.PUNTO3); //ClaveObjetoBase · Fecha_pendiente · camino
					if (!FechaGotta.esFecha(claveTT.get(claveTT.size()-1).toString()))
						claveTT.remove(claveTT.size()-1);
					
					for (int i=0; i<claveTT.size()-1 ; i++)
						{claveTB.add(claveTT.get(i));}
					}
				else if ( modo.equals("tp") || modo.equals("te") ) {
					ArrayList<String>temp=Util.split(valor,Constantes.PUNTO3); 
					camino=temp.get(2);
					FechaGotta fpen = new FechaGotta( temp.get(1) );
					tramite=temp.get(0);
					claveTB=na.sacaClaveTablaBase();
					for (Object c : claveTB)
						claveTT.add(c);
					claveTT.add(fpen);
					}
				else if  ( modo.equals("tl") ) {// tramite·camino
					ArrayList<String>temp=Util.split(valor,Constantes.PUNTO3); 
					camino=temp.get(1);
					tramite=temp.get(0);
					claveTB=na.sacaClaveTablaBase();
					}
				
				if (camino.equals(Constantes.CAD_VACIA))
					camino=null;
				if (camino!=null)
					tramites.add( new Tramite(nl, Long.parseLong(camino), tramite, claveTB, claveTT, variablesFila, modo) );
				}
			}
		else  { //trámites de botón (pueden ser sobre una lista)
			claveTB=na.sacaClaveTablaBase();
			
			if (gruposFilasPinchadas.isEmpty()) //trámites de botón
				tramites.add(new Tramite(nl, Long.parseLong( Util.desformatearNumero(camino) ), tramite, claveTB, variablesArbol));
			else { //trámites de botón sobre listas normales
				for (ArrayList<String> grupo: gruposFilasPinchadas) {
					Coleccion<Variable> variablesFila =new Coleccion<Variable>();
					ArrayList<String> restoParametros;
					
					for (String valor : grupo) {
						restoParametros =Util.split(valor, "\\");
						Coleccion<Variable> colTemporal=montaVariablesFila(variablesArbol, restoParametros);
						
						for (String nombrevar:colTemporal) 
							variablesFila.put(nombrevar, colTemporal.get(nombrevar));
						}
					tramites.add(new Tramite(nl, Long.parseLong(camino), tramite, claveTB, variablesFila));					
					}
				}
			}
		for(Tramite t:tramites){
			t.tipoEjecucion=tipoEjecucion;
			t.nodoActivo=na;
			}
		nl.setTramites(tramites);
		return nl;
	}
		
	private static Coleccion<Variable> montaVariablesFila(Coleccion<Variable> variablesArbol, ArrayList<String> lasClaves) {
		Coleccion<Variable> variablesFila = new Coleccion<Variable>(true); // w2005\s18
		//1º copiamos las del árbol
		for (Variable v : variablesArbol.values())
			variablesFila.put(v.getNombre().toUpperCase(), v);
		//2º le añadimos sus propios valores
		//MSP interpretamos al verrés
		for (int i=lasClaves.size()-1;i>=0;i--) {
			String trozo =lasClaves.get(i);
			if ( !trozo.equals(Constantes.CAD_VACIA) ) {
				String nVar=trozo.substring(0,1);
				String vVar=trozo.substring(1);
				variablesFila.put(nVar.toUpperCase(), new Variable(nVar.toUpperCase(), Constantes.STRING, vVar));
				}
			}
		return variablesFila;
		} 
	public Lote(ArrayList<Tramite> lTramites, Motor motor, String idxBloqueo) {
		this.idxBloqueo=idxBloqueo;
		this.motor=motor;
		if (lTramites != null)
			setTramites(lTramites);
		}
	public void setTramites(ArrayList<Tramite> lTramites) {
		for (Tramite t : lTramites) {
		    t.lote=this;
	   		this.tramites.add(t);
			}
	   }

	public Variable getVariable(String k){
		if (k.length()==1)
			return variables.get(k);
		else {
			//primero probamos sin @
			if (k.startsWith("@"))
				k=k.substring(1);
			
			Variable ret=variables.get(k);
			if (ret==null) ret=variables.get("@"+k);
			return ret;
			}
		}
	public Variable setVariable(String k) throws ErrorArrancandoAplicacion
		{return setVariable(k, Constantes.STRING);}
	public Variable setVariable(String k, String tipo) throws ErrorArrancandoAplicacion
		{return setVariable(k, tipo, null);}
	public Variable setVariable(String k, String ptipo, Object valor) throws ErrorArrancandoAplicacion {
		String tipo=Constantes.STRING;
		if (ptipo==null){
			if (valor instanceof BigDecimal){
				int precision=((BigDecimal)valor).scale();
				if ( precision==2 )
					tipo=Constantes.CURRENCY;
				else if (precision==0)
					tipo=Constantes.INTEGER;
				}
			}
		else
			tipo=ptipo;
		
		Variable var = variables.get(k);
		if (var!=null)
			var.setValor(valor);
		else
			var=new Variable(k, tipo, valor);
		var.setModificada(true);
		variables.put(k, var);
		
		boolean variableLarga=false;
		char letra=k.toCharArray()[0];
		if(letra=='@' && k.length()>1)variableLarga=true;
		
		if (valor instanceof String && valor.toString().contains(Constantes.PUNTO3) && variableLarga) 
		 	valor=Util.split(valor.toString(), Constantes.PUNTO3);
		
		if (valor instanceof ArrayList<?> ){
			ArrayList<?> vvalor = (ArrayList<?>)valor;
			
			if (vvalor.size()==1) {
				var.setValor(vvalor.get(0));		
				}
			else {
				if ( !this.motor.getApli().variablesFortuny) //variables castellana
					var.setValor(Util.join(Constantes.PUNTO3, vvalor));//
				else {
					for (int i=0;i<vvalor.size();i++){
						String nombreVar = variableLarga?nombreVar=k+i:Constantes.CAD_VACIA+letra;
						var = new Variable(nombreVar, i==0?ptipo:Constantes.STRING, vvalor.get(i));
						variables.put(nombreVar, var);
						var.setModificada(true);
						var.setVariablePadre(k);
						
						if (i==0)
							variables.get(k).setVariablePadre(k);
						
						letra++;
						}
//					if (variableLarga) {
//						var = new Variable(k,Constantes.STRING,Util.join(Variable.SEPARADOR_VARIABLES_LARGAS, vvalor));
//						variables.put(k, var);
//						}
					}
				}
			}
		return var;
	}

////////////////////////////////////////////////////////////////	
	public boolean quedanTramites() {
		if (tramActivo.tramPadre != null) //trámites SUB
			return true;
		return (numTramite+1)<tramites.size();
		}
	public Tramite siguienteTramite() throws ErrorArrancandoAplicacion {
		if (tramActivo == null) {
			tramActivo=tramites.get(0);
			return tramActivo;
			}
		else if (tramActivo.tramPadre != null) {//trámites SUB o FOR
			if (tramActivo.tablaFOR!=null) { //trámites FOR
				if (tramActivo.tablaFOR.getRegistroActual()>=tramActivo.tablaFOR.getNumRegistrosCargados()-1) {
					tramActivo.tablaFOR.setRegistroActual(tramActivo.volverA);
					tramActivo=tramActivo.tramPadre; //sin correr el contador numTramite
					motor.getApli().println(" >> Se acabó el FOR, volvemos al trám "+tramActivo.tramite);
					}
				else {
					tramActivo.tablaFOR.setRegistroActual(tramActivo.tablaFOR.getRegistroActual()+1);
					tramActivo.getAcciones().setRegistroActual(0);
					motor.getApli().println(" >> Continúa el FOR, siguiente fila de "+tramActivo.tablaFOR.tdef.getCd()+
														" (fila "+tramActivo.tablaFOR.registroActual+
														" de "+(tramActivo.tablaFOR.getNumRegistrosCargados()-1)+")" );
					}
				return tramActivo;
				}
			else {
				//trámites SUB
				tramActivo=tramActivo.tramPadre; //sin correr el contador numTramite
				motor.getApli().println(" >> Se acabó el SUB, volvemos al trám "+tramActivo.tramite);
				return tramActivo;
				}
			}
		else {
			this.devolverDepuradorCompleto=true;
			// Si no se está depurando no acumulamos los trámites ya ejecutados
			if(motor.getUsuario().tipoDepuracion.equals(Motor.TDepuracion.SIN_DEPURACION))
				tramites.set(numTramite, null);
			numTramite++;
			if (numTramite<tramites.size()) 
				tramActivo=tramites.get(numTramite);
			else 
				tramActivo=null;
			return tramActivo;
			}
	}
	public void setTramActivo(Tramite t) {
		tramActivo=t;}
	public Tramite tramActivo(){
		return tramActivo;}
	
	public Resultado seguir() throws ErrorGotta {
		Motor.Resultado respuesta=Motor.Resultado.OK;
		
		if (this.tramActivo==null)
			this.siguienteTramite();
		
		try {
			respuesta=tramActivo.seguir();
			}
		catch (ErrorTramiteActivoHaCambiado e){
			tramActivo.tipoEjecucion=TipoEjecucion.Reemitir;
			respuesta=tramActivo.seguir();
			}
		
		while (this.quedanTramites() && respuesta==Motor.Resultado.OK){
			this.siguienteTramite();
			
			try {
				respuesta=tramActivo.seguir();
				}
			catch (ErrorTramiteActivoHaCambiado e){
				respuesta=tramActivo.seguir();
				}
			}
		return respuesta;
		}
	
	public void añadeTramite(Tramite tram){
		this.tramites.add(tram);
		}
//////////////////////////////////////////////////////////////////////
	
	private ArrayList<String> listaIdx(){
		ArrayList<String> ret = new ArrayList<String>();
		for (Tramite t : this.tramites)
			ret.add(t.idxDepuracion);
		return ret;
		}
	
	private void JSONsub(JSONArray ret, Tramite padre, ArrayList<String> tramIncluidos, boolean todo) throws JSONException{
		for (String sSUB : padre.tramitesSUB.keySet()) {
			Tramite tSUB=padre.tramitesSUB.get(sSUB);
			
			if ( !tramIncluidos.contains(tSUB.idxDepuracion) ){
				if (todo)
						ret.put(tSUB.JSON());
				else
						ret.put(tSUB.evolucionJSON());
				tramIncluidos.add( tSUB.idxDepuracion );
				}
			JSONsub(ret, tSUB, tramIncluidos, todo);
			}
		}
	
	private void JSONx(JSONObject ret, boolean todo) throws JSONException {
		if (this.devolverDepuradorCompleto) 
			todo=true;	
		
		ArrayList<String>tramIncluidos=new ArrayList<String>();
		String activo=this.tramActivo.idxDepuracion;
		
		JSONArray retTramites = new JSONArray();
		if (todo) {
			for (Tramite t : this.tramites) {
				if ( !tramIncluidos.contains(t.idxDepuracion) ){
					retTramites.put(t.JSON());
					tramIncluidos.add( t.idxDepuracion);
					}
				
				JSONsub(retTramites, t, tramIncluidos, todo);
				}
			ret.put("tramites",retTramites);
			}
		else { /*devolvemos una versión reducida del trámite, únicamente con 
				la línea activa y las líneas ejecutadas */
			for (Tramite t : this.tramites) {
				if ( !tramIncluidos.contains(t.idxDepuracion) ){
					retTramites.put(t.evolucionJSON());
					tramIncluidos.add( t.idxDepuracion);
					}
				
				JSONsub(retTramites, t, tramIncluidos, todo);
				}
			}
		ret.put("tramites", retTramites);	
		ret.put("tramActivo", activo);
		ret.put("tramRaiz", this.tramActivo.tramiteInicial().idxDepuracion);
		ret.put("tramitesDelLote", Util.JSON( this.listaIdx() ) );
		
		JSONArray retTablas = new JSONArray();
		for (ITabla t : this.motor.tablas.values()) {
			String id=t.tdef.getCd().toLowerCase();
			if (!id.equalsIgnoreCase("tra_acciones")) 
				retTablas.put(t.JSON());
			}
		ret.put("tablas", retTablas);
		JSONArray retVariables = new JSONArray();
		for (Variable v : this.variables.values())
			retVariables.put(v.JSON());
		ret.put("variables",retVariables);
		}
	
	public void JSON(JSONObject ret) throws JSONException {
		JSONx(ret, true);	
		}
	public void evolucionJSON(JSONObject ret) throws JSONException {
		/*forma un JSON reducido, para intentar actualizar 
			* en el cliente sólo las cosas que hayan cambiado */
		JSONx(ret, false);
		}


	private Coleccion<Fila> _cacheTRA_Caminos=new Coleccion<Fila>();
	public Fila cacheTRA_Caminos(Long camino) throws ErrorDiccionario, ErrorConexionPerdida, ErrorArrancandoAplicacion{
		String key="x"+camino;
		if (!_cacheTRA_Caminos.containsKey(key)){
			Filas rs = GestorMetaDatos.leeTRA_Caminos(motor.getUsuario().getConexion(), motor.getEsquema(), camino);
			if (rs.size()>=1)
				_cacheTRA_Caminos.put(key, rs.get(0));
			}
		return _cacheTRA_Caminos.get(key);
		}
	private Coleccion<ITabla> _cacheTRA_Acciones=new Coleccion<ITabla>();
	public ITabla cacheTRA_Acciones(Tramite t) throws ErrorGotta{
		Long camino=t.camino;
		String tramite=t.tramite;
		
		String key="x"+camino+"x"+tramite;
		if (!_cacheTRA_Acciones.containsKey(key)){
			Filas filas=t.APP_Tramites(camino, tramite, t.motor.getApli());
			
			if (filas!=null && filas.size()==1){
				Fila fila=filas.get(0);
				
				camino=fila.get("CD_Camino")==null?-1L:Long.parseLong(fila.gets("CD_Camino"));
				tramite=fila.gets("CD_Tramite");
				
				t.motor.usuario.añadeMSG("Leyendo APP_Tramites: el trámite que hay que ejecutar es "+camino+":"+tramite, Constantes.TipoMensajeGotta.info);
				}
			ITabla acciones=t.motor.sacaTablaDef("TRA_Acciones").newTabla(t.motor.usuario);
			
			if ( t.tipoEjecucion != TipoEjecucion.DejarPen )  {
				acciones.datos=GestorMetaDatos.leeAcciones(motor.usuario.getConexion(), camino, tramite, motor.getEsquema(), motor.usuario.getIdioma());
//				acciones.cargarRegistros(null, Util.creaLista(camino, tramite)); //de esta forma no se obtienen las traducciones!
				acciones.setRegistroActual(0);
				
				if (acciones.getNumRegistrosCargados()==0) {
					if (tramite.equalsIgnoreCase("Eliminar")) 
						Tramite.montaTramiteEliminar(acciones, t.getTb().tdef.getCd(), camino, tramite) ;
					else if (tramite.equalsIgnoreCase("gtUsuario"))
						Tramite.montaTramiteCambiarContraseña(acciones, camino, tramite);
					
					acciones.untouch();
					acciones.setRegistroActual(0);
						
					t._tt=null;
					t.motor.hayQueRefrescar=true;	
					}
				}
			
			_cacheTRA_Acciones.put(key, acciones);
			}
		else 
			motor.usuario.añadeSQL("(recuperado de caché)",  Util.creaLista(camino,tramite), (Long)null, (Long)null, "TRA_Acciones", 1, TipoMensajeGotta.cache);
		
		return _cacheTRA_Acciones.get(key).deepCopy();
		}	
}
