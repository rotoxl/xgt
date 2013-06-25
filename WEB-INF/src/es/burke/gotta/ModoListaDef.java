package es.burke.gotta;

import org.json.JSONException;
import org.json.JSONObject;

public class ModoListaDef {
	private String cd; //nombre modo lista. Coincide con el nombre del icono
	private String ds; //descripción
	private INivelDef nivelInicial; //nivel inicial -> sql que se ejecuta al pinchar sobre la vista
	
	public ModoDetalleDef md;
	
	private Boolean botonera;
	private String disposicion, tipoPanel1; 

	public boolean esDeSistema=false;
	public ModoListaDef(String apli, String cd, INivelDef nivelDef){
		constructorDeVerdad(apli, cd, null, nivelDef, true, "v", "a");
		}
	public ModoListaDef(String apli, String cd, String ds, INivelDef nivelDef, 
				Boolean botonera, String disposicion, String tipoPanel1) {
		constructorDeVerdad(apli, cd, ds, nivelDef, botonera, disposicion, tipoPanel1);
		}
	private void constructorDeVerdad(String apli, String _cd, String _ds, INivelDef nivelDef, 
			Boolean _botonera, String _disposicion, String _tipoPanel1) {
		this.cd=_cd;
		this.ds=Util.replaceTodos(_ds, "&", Constantes.CAD_VACIA);
		this.nivelInicial=nivelDef;		
		md=new ModoDetalleDef(apli, this.cd);
		
		this.botonera=_botonera;
		this.disposicion=_disposicion;
		this.tipoPanel1=_tipoPanel1;
		}
	
	public String getCd(){
		return this.cd;}
	public String getDs(){
		return this.ds;}
	public INivelDef getNivelInicial(){
		return this.nivelInicial;}
	public JSONObject JSON(Usuario usr) throws JSONException, ErrorGotta {
		if (md==null) 
			return new JSONObject();
		
		JSONObject ta=new JSONObject();
		return md.JSON(ta, usr);
		}
	public JSONObject JSON() throws JSONException{
		JSONObject ret = new JSONObject();
		ret.put("cd", this.cd);
		ret.put("ds", this.ds);
		
		ret.put("botonera", this.botonera);
		ret.put("disposicion", this.disposicion);
		ret.put("tipoPanel1", this.tipoPanel1);
		
		return ret;
		}
}
