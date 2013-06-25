package es.burke.gotta.dll;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import es.burke.gotta.Aplicacion;
import es.burke.gotta.Archivo;
import es.burke.gotta.ErrorArrancandoAplicacion;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;

public abstract class IFirma extends DLLGotta{
	abstract JSONObject JSON(Archivo ar, int numerador) throws JSONException, ErrorGotta;
	
	public JSONArray JSONlistaObj(List<Archivo> lista) throws JSONException, ErrorGotta{
		JSONArray ret=new JSONArray();
		for (int i=0;i<lista.size();i++){
			Archivo ar =lista.get(i);
			ret.put(JSON(ar,i));
			}
		return ret;
		}

	public String completarFirma(Usuario usr, String urlDocOriginal, String signature) throws ErrorArrancandoAplicacion {
		usr.getMotor().tramActivo().lote.setVariable("@SIGNATURE","string", signature);
		return signature;
	}
	
	public abstract Map<String,Object> validaFirma(Usuario usr, String rutaFirma) throws ErrorFirmaNoValida;
	
	public String obtenerDigest(Usuario usr, String nombreFich) throws Exception {
		File f = new File(nombreFich);
		byte[] a = Util.getFileContentsAsByteArray(f);
		
		String alg=usr.getApli().getDatoConfig("hash_firma");
		if (alg==null) alg="SHA1";
			
		String digest=Util.toHexString(Util.getMessageHash(a, alg));
		return digest;
		}
	
	public static IFirma getClassFirma(Aplicacion apli) {
		IFirma dllfirma=null;
		String dll=apli.getDatoConfig("claseFirma");
		if (dll==null) dll="Firma";
    	try {
			Class<?> cls = Class.forName("es.burke.gotta.dll."+dll);
			Class<? extends IFirma> clsG=cls.asSubclass(IFirma.class);
			dllfirma=clsG.newInstance();
			}
		catch (ClassNotFoundException e) {
			//throw new ErrorAccionDLL("Guión/Acción no implementada:" + dll + "/" + dll,e);
			} 
		catch (InstantiationException e) {
			//throw new ErrorAccionDLL("Guión/Acción no implementada:" + dll + "/" + dll,e);
			} 
		catch (IllegalAccessException e) {
			//throw new ErrorAccionDLL("Guión/Acción no implementada:" + dll + "/" + dll,e);
			}
		return dllfirma;
	}
	
	public abstract Map<String, Object> validarFirma(Usuario usr, String rutaFirma, String id_signature);
}
