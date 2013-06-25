package es.burke.gotta;

import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;

public class NodoActivo {//objeto base
	public ModoDetalleDef md;
	ModoListaDef ml;
	public String nodoActivo;
	String letras;
	
	public String nombretb;
	private ITablaDef tdef;
	ITabla tb;
	
	Usuario usr;
	public HashMap<String, String> controles=null;
		
	private Coleccion<ITabla> tablas=new Coleccion<ITabla>(false);
	public int numPagina=1;
	
	@Override
	public String toString(){
		String ret="";
		if (md!=null) ret+=md.getCd();
		
		return "=md-"+ret+"\\"+this.nodoActivo; 
		}
	
	public static NodoActivo desdeJSON(HttpServletRequest request, Usuario usr) throws ErrorGotta{
		String nodo=null, letras=null; 
		
		String nombre=Util.obtenValorOpcional(request, "md");
		ModoDetalleDef md=null; 
		ModoListaDef ml=null;
		String tbDef=null;
		
		if (nombre!=null)
			md=usr.getApli().getModoDetalleDef(nombre);
		if (md==null && usr.getVistas().containsKey(nombre))
			ml=usr.getModoListaDef(nombre);
		else if (usr.arbol==null){
			//pass
			}
		else
			ml=usr.getModoListaDef(usr.arbol.ml);
		
		if (nombre != null ) {
			nodo=Util.obtenValorOpcional(request, "nodo");
			if (nodo !=null && !nodo.equalsIgnoreCase(usr.arbol.nodoActivo))
				letras=Util.obtenValorOpcional(request, "letras");	
			}
		return new NodoActivo(usr, md, ml, nodo, letras, tbDef);
	}
	
	public ITabla getTB(){ 
		if (this.nombretb==null) 
			return null; 
		try { 
			return getTabla(this.nombretb); 
			}  
		catch (ErrorGotta e) { 
			return null; 
			}
		} 
	
	public NodoActivo(Usuario usr, String detalle, String ml, String nodo) throws ErrorGotta{
		ModoDetalleDef md=usr.getApli().getModoDetalleDef(detalle);
		
		this.md=md;
		this.ml=usr.getModoListaDef(ml);
		this.nodoActivo=nodo;
		this.usr=usr;
	}
	public NodoActivo(Usuario usr, ModoDetalleDef md, ModoListaDef ml, String nodo, String xletras, String xtb){
		this.md=md;
		this.ml=ml;
		this.nodoActivo=nodo;
		this.usr=usr;
		
		if (xletras==null && this.md!=null)
			xletras=this.md.getLetras();
		this.letras=xletras;
		if (xtb==null && this.md!=null)
			xtb=this.md.getTB();
		this.nombretb=xtb;
		}
	public NodoActivo(Usuario usr){
		this.usr=usr;
	}
	public ArrayList<Object> sacaClaveTablaBase() throws ErrorTablaNoExiste,  ErrorArrancandoAplicacion, ErrorCreandoTabla {
		ArrayList<Object> claveTB= new ArrayList<Object>();
		for ( Object s :  this.claveTB() )  {
			if (s==null) {
				claveTB=null;
				break;
				}
			claveTB.add(s);
			}
		return claveTB;
		}
	public ArrayList<Object> claveTB() throws ErrorTablaNoExiste, ErrorArrancandoAplicacion, ErrorCreandoTabla{
		añadeTablas();
		ArrayList<Object> clave = new ArrayList<Object>();		

		if (md==null || nombretb==null)
			return clave;	//no tiene sentido seguir, puesto que no tengo tb
		
		ITabla xtb=this.tb;
		
		//obtengo el campo clave de la tabla base	
		CampoDef campoclave=null;
		campoclave = xtb.tdef.getCampoClave();
		
		String valor = new String();
		if (letras!=null && !letras.equals(Constantes.CAD_VACIA)) {
			//construyo una hashtable con las claves del nodo
			HashMap<String,String> hsclaves=Util.nodoAHash(this.nodoActivo);
			
			//obtenemos la clave a partir de las letras
			int hasta=Math.min(letras.length(),campoclave.numColumnas());
			for (int i=0;i<hasta;i++ ){
				if (hsclaves.get(letras.substring(i,i+1))!=null){
					valor = hsclaves.get(letras.substring(i,i+1));
					clave.add(valor);
					}
				else 
					clave.add(null);
				}
		}
		else {
			//	obtenemos la clave a partir del propio nodo
			//	nos apañamos con la clave del nodo activo (cd del APP_?)
			//	a partir del nodo donde estoy recojo tantos valores como necesite,
			//	tantos como columnas tenga el campo clave de la tabla base, siguiendo
			//	la jerarquía hacia arriba
			//  Si nodoALista devuelve ['OF1','1',2004,1,208] y el número de
			//  columnas del campo clave es 3, cogemos los tres últimos elementos
			//  del arrayList: [2004, 1, 208]  

			ArrayList<Object> claves = nodoALista(this.nodoActivo);
			if (claves.size()>= campoclave.numColumnas()) {
				int contador=claves.size()-campoclave.numColumnas();
				//for (int i=0;i<campoclave.numColumnas();i++ )
				for (int i=contador;i<claves.size();i++ )
					clave.add (claves.get(i));
				}
			else 
				{/*no tenemos valores suficientes para cargar la tb*/}
			}
	
		return clave;	
		}

	//Convierto el nodo en una lista de valores
	static ArrayList<Object> nodoALista(String nodo){
		ArrayList<Object> claveret=new ArrayList<Object>();
		for (String valor:Util.split(nodo, Constantes.SEPARADOR)){
			if (valor.length()>1)
				claveret.add(valor.substring(1));
			}
		return claveret;
		}

	private void añadeTablas() throws ErrorTablaNoExiste, ErrorArrancandoAplicacion, ErrorCreandoTabla{
		if (this.tdef==null && nombretb!=null) {
			this.tdef=getTablaDef(this.nombretb);
			this.tb=tdef.newTabla(this.usr);
			this.putTabla(this.tb);
			}
		}
	public void cargaTablas() throws ErrorTablaNoExiste, ErrorArrancandoAplicacion, ErrorCreandoTabla {
		if (!this.nodoActivo.endsWith(Constantes.SEPARADOR)) {
			if (this.nombretb!=null) {
				ArrayList<Object> valores=this.claveTB();
				if (!valores.isEmpty())	{
					try {
						if (tb instanceof Tabla)
							tb.cargarRegistros(null, valores);
						} 
					catch (NumberFormatException e)
						{/*no pasa nada, faltan datos para rellenar el md*/} 
					catch (ErrorCargandoTabla e) 
						{/*no pasa nada, faltan datos para rellenar el md*/} 
					}
			 	}
			}
		}
	public ITabla getTabla(String cd) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion, ErrorUsuarioNoValido, ErrorCreandoTabla{
		ITablaDef xtdef=this.getTablaDef(cd);
		return getTabla(xtdef);
		}
	public ITabla getTabla(ITablaDef xtdef) throws ErrorUsuarioNoValido, ErrorCreandoTabla {
		if (xtdef==null) return null;
		ITabla t=tablas.get(xtdef.getCd());
		if (t==null) {
			t = xtdef.newTabla(this.usr);
			tablas.put(xtdef.getCd(), t);
			}
		return t;
		}
	public void putTabla(ITabla t){
		this.tablas.put(t.tdef.getCd(), t);
		}
	
	public ITablaDef getTablaDef(String xcd) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion{
		return this.usr.getApli().getEsquema().getTablaDef(xcd);
	}
}
