package es.burke.gotta;

import java.util.HashMap;

import org.json.JSONArray;

public class ControlDef {
	private String md, ml;
	private String apli;
	public String numControl;
	public String tc;
	
	private String contenedor, pestanha;
	public int  alto, ancho;
	private int tope, izq;
	
	private String captionDef;
	private HashMap<String, String> caption=new HashMap<String, String>();
	
	String tipoLetra, estiloLetra, colorLetra;
	String claseCSS;
	Double tamLetra;
	
	String borde;
	
	String tipoDato, subTipoDato; int longitud;
	
	public ControlDef(String apli){
		this.apli=apli;
	}
	public ControlDef(Fila reg, String apli){
		this.apli=apli;
		this.captionDef = reg.gets("caption");
		this.numControl = reg.gets("numcontrol");
		this.tc = reg.gets("tipocontrol");	
		
		this.izq= new Double(reg.gets("izq")).intValue();
		this.tope= new Double(reg.gets("tope" )).intValue();
		this.ancho= new Double(reg.gets("ancho")).intValue();
		this.alto = new Double(reg.gets("alto" )).intValue();
		
		String tCont=reg.getns("contenedor");
		this.contenedor=(tCont==null?"0":tCont);
		this.pestanha=reg.getns("pestanha");
		
		this.tipoLetra=reg.getns("tipoLetra");
		this.estiloLetra=reg.getns("neg_cur_sub_tach");
		this.colorLetra=reg.getns("colorLetra");
		
		String tTamLetra=reg.getns("tamanhoLetra");
		this.tamLetra=tTamLetra==null?new Double(8.0):Double.parseDouble(tTamLetra);
		
		this.borde= reg.gets("borde");
		
		this.claseCSS=reg.getns("claseCSS");
		
		this.tipoDato=reg.getns("tipodato");
		this.subTipoDato=reg.getns("subtipodato");
		
		this.md=reg.gets("mododetalle");
		this.ml=reg.gets("modolista");
		
		for (String idioma:this.getApli().idiomas.claves)
			this.anhadeLenguaje(idioma, Util.noVacía(reg.get("caption_"+idioma)));
		}

	
	public Aplicacion getApli() throws ErrorArrancandoAplicacion {
		return PoolAplicaciones.sacar(apli);}
	
	
	public void ___setCaption(String caption){
		//Basura para el mantenimiento de tablas ordenado por Ayda
		this.captionDef=caption;
		}
	
	public String getCaption(Usuario usr){
		return getCaption(usr.getIdioma());
		}
	public String getCaption(String idioma){
		if (!this.caption.containsKey(idioma))
			return this.captionDef;
		return this.caption.get(idioma);
		}
	
	public void anhadeLenguaje(String CD_Idioma, String caption){
		if (caption==null || caption.equals(Constantes.CAD_VACIA))
			return;
		this.caption.put(CD_Idioma, caption);
		}
	@Override
	public String toString()
		{return "("+this.tc+")"+this.caption+"["+this.numControl+"]";}

	public static JSONArray JSONColumnas(Aplicacion apli){
		if (apli.cacheNombresColumnas==null){
			JSONArray ret=new JSONArray()
				.put("md")
				.put("ml")
				.put("claseCSS")
				.put("numControl")
				.put("tipoControl")
				.put("izq")
				.put("tope")
				.put("ancho")
				.put("alto");
			
			for (String idioma: apli.idiomas)
				ret.put("caption_"+idioma);
				
			ret.put("contenedor")
				.put("pestanha")
				.put("TIPOLETRA")
				.put("tamanhoLetra")
				.put("neg_cur_sub_tach")
				.put("colorLetra")
				.put("borde")
				.put("tipoDato")
				.put("subTipoDato")
				.put("longitud");
			
			apli.cacheNombresColumnas=ret;
			}
		return apli.cacheNombresColumnas;
	}
	
	private JSONArray cache=null;
	public JSONArray JSON(Usuario usr) {
		if (cache==null){
			JSONArray ret=new JSONArray()
				.put(this.md)
				.put(this.ml)
				.put(this.claseCSS)
				.put(this.numControl)
				.put(this.tc)
				.put(this.izq)
				.put(this.tope)
				.put(this.ancho)
				.put(this.alto);
			
			for (String idioma: this.getApli().idiomas)
				ret.put(this.getCaption(idioma));
				
			ret.put(this.contenedor)
				.put(this.pestanha)
				.put(this.tipoLetra)
				.put(this.tamLetra)
				.put(this.estiloLetra)
				.put(this.colorLetra)
				.put(this.borde)
				.put(this.tipoDato)
				.put(this.subTipoDato)
				.put(this.longitud)
				;
			cache=ret;
			}
		return cache;
	}
}
