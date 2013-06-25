package es.burke.gotta;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;
import es.burke.gotta.Constantes.TipoMensajeGotta;

public abstract class ITabla {
	public ITablaDef tdef;

	public boolean usarCache=true;
	
	public int registroActual;
	public Filas datos;
	public HashSet<Integer> borrados;
	public List<Integer> 	nuevos;
	public HashSet<Integer> modificados;

	public HashSet<String> 	colsModif;
	public Usuario usuario;
	
	public boolean marcadaParaNoCargar=true;
	
	public ITabla(ITablaDef tabla, Usuario usuario) throws ErrorUsuarioNoValido {
		if (usuario==null)
			throw new ErrorUsuarioNoValido("El usuario no puede venir nulo");
		this.registroActual = -1;
		this.tdef = tabla;
		if (this.tdef!=null) 
			this.limpiarRegistros();
		this.usuario=usuario;
		this.colsModif=new HashSet<String>();
		}
	
	public void addNew() throws ErrorCampoNoExiste, ErrorTiposNoCoinciden {
		addNew(null,null);	
		}
	public void addNew(CampoDef campo, ArrayList<Object> valor) throws ErrorCampoNoExiste, ErrorTiposNoCoinciden {
		Fila fila = new Fila(datos,datos.getOrden().size());
		this.datos.add(fila);
		this.registroActual=(datos.size()-1);
		if (!nuevos.contains(registroActual))
			this.nuevos.add(registroActual);
	
		//meter valores de la clave
		if (valor !=null) {
			String nombrecol = null;
			int tam_valor = valor.size();
			for (int i = 0; i < tam_valor; i++ ) {
				nombrecol=campo.getColumna(i).getCd(); 
				if (fila.get(nombrecol) == null){
					try {
						setValorCol(nombrecol, valor.get(i));
						} 
					catch (ErrorFilaNoExiste e) {
						//pass
						}
					}
				}
			}
		}
	public void delete() {
		Integer obRegistroActual=registroActual; //Necesario para que busque por contenido, no por índice
		if (nuevos.contains(obRegistroActual))
			nuevos.remove(obRegistroActual);
		else {
			if (modificados.contains(obRegistroActual))
				modificados.remove(obRegistroActual);

			borrados.add(obRegistroActual);
			}
		}
	//////////////////////////////////
	public static Object validaTipoDato(Object valor, String ptipo)  {
		String tipo=ptipo;
		tipo = tipo.toLowerCase();
		
		if (valor!=null && valor.equals("null"))
			valor=null;
		if (valor == null || valor.equals(Constantes.CAD_VACIA))
			return null;
		
		if (tipo.equals( Constantes.STRING ))
			return valor.toString();
		if (Util.en(tipo, "decimal", Constantes.CURRENCY))
			if (valor instanceof BigDecimal) 
				return valor;
			else {
				BigDecimal ret = new BigDecimal(valor.toString()); 
				return ret.setScale(2, BigDecimal.ROUND_HALF_UP); 
				}
			
		if (tipo.equals( Constantes.DOUBLE ))
			return Double.parseDouble(valor.toString());
		if (Util.en(tipo, Constantes.INTEGER, Constantes.LONG)) {
			if (valor instanceof Boolean)
				return   ((Boolean)valor ? 1 : 0);
			if(valor instanceof Byte)
				return valor;
			if(valor instanceof Short)
				return valor;
			if(valor instanceof Long)
				return valor;
			if(valor instanceof BigInteger)
				return valor;
			else {
				//#TODO revisar esto
				String svalor=valor.toString();
				if (svalor.endsWith(".00"))
					svalor=svalor.substring(0, svalor.lastIndexOf(Constantes.PUNTO));
				
				return new BigInteger(valor.toString()).longValue();
				}
			}
		if (tipo.equals( Constantes.DATE )){
			FechaGotta ret = FechaGotta.comoFechaGotta(valor);
			if (ret==null)
				throw new ErrorFechaIncorrecta(valor.toString());
			return ret;
			}
		if (tipo.equals(Constantes.BOOLEAN)) {
			if (valor instanceof Boolean)
				return valor;
			String vvalor=valor.toString().toLowerCase();
			if (vvalor.equals("0") )
				return false;
			else if (vvalor.equals("true") || vvalor.equals("false") )
				return Boolean.parseBoolean(vvalor);
			else
				return true;
			}
		return valor;
		}
//////////////////////////////////	
	public void setValorCol(String nombre, Object pvalor) throws ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden {
		Object valor=pvalor;
		ColumnaDef col = tdef.getColumna(nombre);

		String tipo = col.getTipo().toLowerCase();
		try {
			valor=validaTipoDato(valor, tipo);
			}
		catch (NumberFormatException e) {
			throw new ErrorTiposNoCoinciden("Los tipos de datos no coinciden: '"+valor+"' no es del tipo esperado '"+tipo+"' (columna "+tdef.cd+"."+nombre+") "+(e.getMessage()!=null?": "+e.getMessage():""));
			} 
		catch (ErrorFechaIncorrecta e) {
			throw new ErrorTiposNoCoinciden("Los tipos de datos no coinciden: '"+valor+"' no es del tipo esperado '"+tipo+"' (columna "+tdef.cd+"."+nombre+") "+(e.getMessage()!=null?": "+e.getMessage():""));
			}
		
		if(this.registroActual<0 || this.registroActual>=this.datos.size())
			throw new ErrorFilaNoExiste("No existe el registro actual");
		Fila ht = this.datos.get(registroActual);
		
		ht.put(nombre, valor);
		if (! nuevos.contains(registroActual) && !modificados.contains(registroActual) )
			modificados.add(registroActual);
		this.colsModif.add(nombre.toLowerCase());
		}
	public Object getValorCol(String nombre){
		try {
			Object ret=datos.get(registroActual).get(nombre);
			String tipo=this.tdef.getColumna(nombre).getTipo();
			ret=validaTipoDato(ret, tipo);

			return ret;
			}
		catch(IndexOutOfBoundsException e){
			return null;}
		catch(ErrorCampoNoExiste e){
			return null;	
			} 
		catch (ErrorFechaIncorrecta e){
			return null;
			}
		}
/////////////////////////
	public ArrayList<Object> getValorCam(String nombre) throws ErrorCampoNoExiste {
		if (Util.en(nombre , Constantes.camposMagicos)){
			ArrayList<Object> ret = new ArrayList<Object>();
			if (nombre.equalsIgnoreCase(Constantes.DS))
				ret.add(this.tdef.cd);
			else if (nombre.equalsIgnoreCase(Constantes.REG))
				ret.add(this.getRegistroActual());
			else if (nombre.equalsIgnoreCase(Constantes.BOF))
				ret.add(this.getRegistroActual()==1);
			else if (nombre.equalsIgnoreCase(Constantes.EOF))
				ret.add(this.getRegistroActual()+1>=this.getNumRegistrosCargados());
			else if (nombre.equalsIgnoreCase(Constantes.COUNT))
				ret.add(this.getNumRegistrosCargados());
			return ret;
			}
		CampoDef campo = getCampo(nombre);
		ArrayList<Object> arr = new ArrayList<Object>();
		if (campo != null) {
			for(String col:campo)
				arr.add( getValorCol(col) );
			}
		return arr;
		}
	public void setValorCam(String nombre, ArrayList<Object> valor) throws ErrorCampoNoExiste, ErrorFilaNoExiste, ErrorTiposNoCoinciden {
		CampoDef campo=this.tdef.getCampo(nombre);

		int hasta = campo.numColumnas();
		if (valor.size()<hasta)
			hasta=valor.size();

		int i=0;
		for (String c:campo) {
			setValorCol(c, valor.get(i));
			i++;
			}
		}
	public CampoDef getCampo(String cd) throws ErrorCampoNoExiste {
		return tdef.getCampo(cd);
		}
///////////////////////
	public int getRegistroActual(){
		return this.registroActual;
		}
	public void setRegistroActual(int n){
		this.registroActual=n;
		}
	public int getNumRegistrosCargados(){
		return datos.size();}
/////////////////////////	
	public int findFirst(String nombre, Object valor) {
		for(int i=0;i<datos.size();i++){
			Fila tmp=datos.get(i);
			Object v = tmp.get(nombre);
			if ((valor==null && v==null)||(v !=null && valor !=null && v.toString().equalsIgnoreCase(valor.toString()))){
				this.registroActual=i;
				return i;
				}
			}
		return -1;
		}
	public int findNext(String nombre, Object valor) {
		int i = registroActual+1;
		String b=Util.toString(valor, "");
		int tam_datos = datos.size();
		
		while(i < tam_datos) {
			Fila tmp=datos.get(i);
			String a=Util.toString(tmp.get(nombre), "");
			if (a.equals(b)) {
				registroActual=i;
				return registroActual;
				}
			i++;
			}
		return -1;
		}
	public boolean findKey(ArrayList<Object> valor, String campo) throws ErrorCampoNoExiste {
		return findKey(valor, campo, false);
		}
	public boolean findKey(ArrayList<Object> valor, String campo, boolean situarme) throws ErrorCampoNoExiste {
		CampoDef cam = this.tdef.getCampo(campo);
		return findKey(valor, cam.getColumnas().getOrden(), situarme);
		}
	public boolean findKey(ArrayList<Object> valor, ArrayList<String> listaCols, boolean situarme) throws ErrorCampoNoExiste {
		if (valor==null)
			return false;
		if (valor.isEmpty())
			return false;
		if (listaCols.size()==0)
			return false;
		
		boolean encontrado = false;
		int numreg=0;
		
		while (numreg < datos.size() && !encontrado) {
			Fila fila = datos.get(numreg);
			
			if (!this.borrados.contains(numreg)){
				for (int j=0; j < listaCols.size(); j++ ) {
					ColumnaDef c = this.tdef.getColumna( listaCols.get(j) );
					String clave = c.getCd();
					String contenido = Util.noNulo(valor.get(j));
					String valorAComparar = Util.noNulo(fila.get(clave));
					
					if (c.getTipo().equals( Constantes.DATE )) {
						valorAComparar=new FechaGotta(valorAComparar).toString();
						contenido = new FechaGotta(contenido).toString();
						}
					if (!valorAComparar.equalsIgnoreCase(contenido)) {
						encontrado=false;
						break;
						}
					encontrado=true;
					}
				}
			if (!encontrado) //continúo buscando
				numreg++;
			}

		if (situarme && encontrado)
			this.registroActual=numreg;

		return encontrado;
		}
///////////////////////	
	public void limpiarRegistros() {
		ArrayList<String> cols = new ArrayList<String>();
		ArrayList<String> tipos = new ArrayList<String>();
		for (String s:this.tdef.getColumnas()){
			cols.add(s);
			try {
				tipos.add(this.tdef.getColumna(s).getTipo().toLowerCase());
				}
			catch (ErrorCampoNoExiste e) {
				//pass
				}
			}
		this.datos=new Filas(cols,tipos);
		untouch();
		}
	public void untouch() {
		this.nuevos=new ArrayList<Integer>();
		this.borrados=new HashSet<Integer>();
		this.modificados=new HashSet<Integer>();
		}
///////////////////////	
	@Override
	public String toString(){
		return (marcadaParaNoCargar?"NO ":"SI ")+ "Tabla "+this.tdef.cd+ " ["+this.datos.size()+" fila"+(datos.size()>1? "s": "")+" cargadas]";
		}
	
	public void cargarRegistros(CampoDef campo, List<Object> valor) throws ErrorCargandoTabla {
		cargarRegistros(campo, valor, false, null);
		}
///////////////////////
	public JSONObject JSON() throws JSONException {
		JSONObject ret= new JSONObject();
		ret.put( "idx",this.tdef.cd);
		ret.put( "filaActiva",this.registroActual);
		ret.put( "colsModif" , ( this.colsModif));
		ret.put( "nuevos" , this.nuevos);
		ret.put( "modificados",	this.modificados) ;
		ret.put( "borrados",this.borrados) ;

		CampoDef clave=this.tdef.getCampoClave();
		if (clave!=null)
			ret.put( "clave",Util.JSON(	clave.getColumnas().getOrden() )) ;
		
		ret.put( "datos",this.datos.JSON(true) );

		return ret;
		}
	public void cargarRegistros(CampoDef campo, List<Object> valor, Boolean mantenerDatosAntiguos, CampoDef orderBy) throws ErrorCargandoTabla {
		if (campo==null)
			campo=tdef.getCampoClave();

		if ( valor.size()>0 ){
			for (int i=0; i<valor.size() ; i++){
				Object valorI;
				if (valor.get(i)==null)
					valorI=null;
				else
					try {
						valorI=Util.ponTipoCorrecto( campo.getColumna(i), valor.get(i) );
						}
					catch (NumberFormatException e){
						throw new ErrorCargandoTabla("Error al convertir el dato "+valor.get(i) ,e );
						}
					catch (Exception e) {
						throw new ErrorCargandoTabla("Error al convertir el dato "+valor.get(i) ,e );
					}

				valor.set(i, valorI);
				}
			}

		if (mantenerDatosAntiguos){
			Filas xDatos = this.cargarRegistrosPrimitivo(campo, valor, orderBy);
			this.incorporaDatos(xDatos, mantenerDatosAntiguos);
			}
		else if (this.usarCache && this.datos.size()==1){
			//comprobamos si está cargada
			boolean hayQueCargar=false;
			for (int i=0; i<campo.numColumnas(); i++){
				String k=campo.getColumnas().getOrden().get(i);
				
				Object a=valor.get(i);
				Object b=this.getValorCol(k);
				
				if (a==null || b==null){
					hayQueCargar=true;
					break;
					}
				else if (! (a.equals(b))){
					hayQueCargar=true;
					break;
					}
			
				}
			if (hayQueCargar){
				Filas xDatos = this.cargarRegistrosPrimitivo(campo, valor, orderBy);
				this.incorporaDatos(xDatos, mantenerDatosAntiguos);		
				}
			else
				this.usuario.añadeSQL("(recuperado de caché)",  valor, (Long)null, (Long)null, "Tabla "+this.tdef.cd, 1, TipoMensajeGotta.cache);
			}
		else {
			Filas xDatos = this.cargarRegistrosPrimitivo(campo, valor, orderBy);
			this.incorporaDatos(xDatos, mantenerDatosAntiguos);		
			}
		}
	private void incorporaDatos(Filas xDatos, boolean mantenerDatosAntiguos){
		//Arreglamos los tipos de datos
		for (ColumnaDef col : tdef.getColumnas().values() ) {
			if (!xDatos.containsKey(col.getCd()))
				xDatos.addColumna(col.getCd(), col.getTipo());
			int num=xDatos.nombreAPosicion(col.getCd());
			xDatos.getTipos().set(num, col.getTipo());
			}
		// y pasamos los datos
		if (mantenerDatosAntiguos && datos!=null) {
			Integer totalFilasInicial=this.getNumRegistrosCargados();
			for (Fila fila:xDatos){
				this.datos.add(fila);
				}
			this.setRegistroActual(totalFilasInicial);
			}
		else {
			this.datos=xDatos;
			registroActual=0; //me posiciono al principio de la tabla
			untouch();
			}
		}

	abstract Filas cargarRegistrosPrimitivo(CampoDef campo, List<Object> valor, CampoDef orderBy) throws ErrorCargandoTabla;

	public void ordenar(String col) {
		ArrayList<Integer>xnuevos, xmodif, xborrados;
		xnuevos=new ArrayList<Integer>();xmodif=new ArrayList<Integer>();xborrados=new ArrayList<Integer>();
		for (int i : this.nuevos)
			xnuevos.add( this.datos.get(i).hashCode());
		for (int i : this.modificados)
			xmodif.add( this.datos.get(i).hashCode());
		for (int i : this.borrados)
			xborrados.add( this.datos.get(i).hashCode());
		
		this.datos.ordena(col, false);
		this.nuevos.clear(); this.modificados.clear(); this.borrados.clear();
		
		for (int i=0; i<this.getNumRegistrosCargados(); i++){
			Fila fila=this.datos.get(i);
			int hc=fila.hashCode();
			
			if (xnuevos.contains(hc))
				this.nuevos.add(i);
			if (xborrados.contains(hc))
				this.borrados.add(i);
			if (xmodif.contains(hc))
				this.modificados.add(i);	
			}
		}
	@SuppressWarnings("unchecked")
	public ITabla deepCopy() {
		ITabla old=this;
		ITabla ret=new Tabla(old.tdef, old.usuario);
		ret.datos=(Filas) old.datos.clone();
		ret.registroActual=old.registroActual;
		ret.borrados=(HashSet<Integer>) old.borrados.clone();
		ret.nuevos=(List<Integer>) ((ArrayList<Integer>) old.nuevos).clone();
		ret.modificados=(HashSet<Integer>) old.modificados.clone();
		
		return ret;
		}
}
