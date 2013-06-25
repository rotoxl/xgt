package es.burke.gotta;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Msg {
	private final static int vbCritical = 16, vbExclamation = 48, vbInformation = 64;
	private final static int vbYesNoCancel=3, vbOkCancel=1;
	private final static String estiloCritico="critico", 
		estiloExclamacion="exclamacion",
		estiloInformacion="informacion" ,
		estiloProgreso="progreso",
		estiloComunicacion="comunicacion";
	private final static String[] EstilosBoton = {estiloCritico, estiloExclamacion, estiloInformacion, estiloProgreso, estiloComunicacion};

	
	private final static int BTN_FOCO1=0, BTN_FOCO2=256, BTN_FOCO3=512;
////////////////////////////////
	private String mensaje, tipo=null;
	private ArrayList<Boton> botones=null;
	Variable ret; //variable de retorno
	Motor motor=null;
	
	public Msg(){
		//pass
		}
	public Msg(Motor motor, String texto, String botones, Variable ret) throws ErrorAccionMSG {
		this.motor=motor;
		this.mensaje=Util.replaceTodos(texto, "\\n", Constantes.vbCrLf);
		this.mensaje=Util.replaceTodos(this.mensaje, "\\t", Constantes.NBSP+Constantes.NBSP+Constantes.NBSP+Constantes.NBSP);
		this.ret=ret;
	
		if (botones==null) botones=""+(vbInformation+vbOkCancel);
		
		if (Util.isNumeric(botones)) {
			int xbtn=Integer.parseInt(botones);
			this.botones=generaBotonesNumero(xbtn);
			this.tipo=sacaValorImagenNumero(xbtn);
			}
		else {
			this.botones=generaBotonesTexto(botones);
			
			String trozo0=Util.split(botones, Constantes.PIPE).get(0);
			this.tipo=sacaValorImagenTexto(trozo0);
			}
		}
	
	public void JSON(JSONObject jsret) throws JSONException{
		jsret.put("tipo", "msg"); //para decidir en el js que tipo de frm construir
		Object camino=null; Object tramite=null;
		
		Tramite tramActivo = this.motor.lote.tramActivo();
		if (tramActivo!=null) {
			camino=tramActivo.camino;
			tramite=tramActivo.tramite;
			}
		
		jsret.put("camino",  camino);
		jsret.put("tramite", tramite);
		
		if (this.ret!=null) 
			jsret.put("retorno", this.ret.getNombre() );
		
		jsret.put("texto", this.mensaje)
			.put("clase", this.tipo)
			.put("refrescarAlCancelar", motor.hayQueRefrescar)
			.put("botones", this.xJSON() );
	}
	public void okMD(Coleccion<String> param){
		if (this.ret!=null) {
			int valor=0;
			
			try {
				String nombre=this.ret.getNombre();
				if ( param.containsKey( nombre ) )
					valor=Integer.parseInt( param.get( nombre ) );
					}
			catch (NumberFormatException e){
				//pass
				}
			this.ret.setValor( valor );
		}
	}

	public static void verificaBotones(String valor) throws ErrorAccionMSG{
		if (valor==null) valor=""+(vbInformation+vbOkCancel);
		if (Util.isNumeric(valor)){
			Integer xvalor=Integer.parseInt(valor);
			
			generaBotonesNumero(xvalor);
			sacaValorImagenNumero(xvalor);
			}
		else {
			String trozo0=Util.split(valor, Constantes.PIPE).get(0);
			generaBotonesTexto(valor);
			sacaValorImagenTexto(trozo0);
			}
		}
	
	private static int quitaInfoFoco(int botones){
		//	determinando el foco
		if (botones >= BTN_FOCO3) 
			botones -= BTN_FOCO3;
		else if (botones >= BTN_FOCO2) 
			botones -= BTN_FOCO2;
		else  
			botones -= BTN_FOCO1;
		return botones;
	}
	private static String sacaValorImagenNumero(int valor){
		int botones=quitaInfoFoco(valor);

		//determinando el icono
		String icono;
		if (botones >= vbInformation) 
			icono= Msg.estiloInformacion;
		else if (botones >= vbExclamation)
			icono= Msg.estiloExclamacion;
			
		else if (botones >= vbCritical) //critico
			icono= Msg.estiloCritico;
		else
			icono=Msg.estiloInformacion;
		
		return icono;
		}
	private static String sacaValorImagenTexto(String valor) throws ErrorAccionMSG{
		valor=valor.toLowerCase();
		valor=Util.replaceUltimo(valor, "ón", "on");
		if (!Util.en(valor, Msg.EstilosBoton))
			throw new ErrorAccionMSG(Constantes.COMILLA+valor+Constantes.COMILLA+" no es un tipo de MSG válido");
		return valor;
		}
	private static int _sacaValorBotones(int valor){
		int botones=quitaInfoFoco(valor);

		//determinando el icono
		if (botones >= vbInformation) {
			//	icono= Msg.estiloInformacion;
			botones -= vbInformation;
			}
		else if (botones >= vbExclamation) {
			//	icono= Msg.estiloExclamacion;
			botones -= vbExclamation;
			}
		else if (botones >= vbCritical) {//critico
			//	icono= Msg.estiloCritico;
			botones -= vbCritical;
			}
		return botones;
		}
	private static ArrayList<Boton> generaBotonesNumero(int valor) {
		int xboton=_sacaValorBotones(valor);
		ArrayList<Boton> botones=new ArrayList<Boton>();
		
		if (xboton>=vbYesNoCancel){
			botones.add( new Msg().new Boton("Sí", null, "1"));
			botones.add( new Msg().new Boton("No", null, "0"));
			}
		else //vbOkCancel
			botones.add( new Msg().new Boton("Aceptar", null, "1"));
		
		return botones;
		}
	private static ArrayList<Boton> generaBotonesTexto(String valor) { // Informacion| TextoBoton1\imgBoton1\valorBoton1 | TextoBoton2\imgBoton2\valorBoton2
		ArrayList<Boton> botones=new ArrayList<Boton>();
		ArrayList<String> trozos= Util.split(valor, Constantes.PIPE);
		for (int i=0; i<trozos.size(); i++) {
			String trozo=trozos.get(i);
			if (i==0) { //en la 0 viene el icono
				//sacaValorImagenTexto(trozo);
				}
			else {
				ArrayList<String> temp=Util.split(trozo+Constantes.SEPARADOR+Constantes.SEPARADOR+Constantes.SEPARADOR, Constantes.SEPARADOR);
				
				botones.add(new Msg().new Boton(temp.get(0), temp.get(1), temp.get(2)));
				}
			}
		return botones;
		}
	
///////////////////////////////////
	private JSONArray xJSON() throws JSONException{
		JSONArray xret=new JSONArray();
		for (Boton btn : this.botones)
			xret.put(btn.JSON());
		return xret;
	}
///////////////////////////////////	
	private final class Boton  {
		String literal, img=null;
		int valor;
		
		public Boton(String literal, Object img, String valor){
			this.literal=literal;
			this.img=img!=null? img.toString(): null;
			try {
				this.valor=Integer.parseInt(valor);
				}
			catch (NumberFormatException e){
				this.valor=1;
				}
			}
		
		public JSONObject JSON() throws JSONException{
			JSONObject xret=new JSONObject();
			xret.put("texto", this.literal);
			xret.put("img", this.img);
			xret.put("valor", this.valor);
			return xret;
		}
	}
}
