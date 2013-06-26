package es.burke.alfresco.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class Constantes {
	
	public static final String UTF8 = "UTF-8";//$NON-NLS-1$
	public static final String ISO885915 = "ISO-8859-15";//$NON-NLS-1$
	public static final String ISO88591 = "ISO-8859-1";//$NON-NLS-1$
	public final static String CODIF = ISO885915;//$NON-NLS-1$
	
	public final static String SEPARADOR="\\";//$NON-NLS-1$
	
	public static final String COMILLA = "'"; //$NON-NLS-1$
	public static final String COMILLAS = "\""; //$NON-NLS-1$
	public static final String COMA = ","; //$NON-NLS-1$
	public final static String PIPE="|" ;  //$NON-NLS-1$
	public final static String GUION_BAJO="_" ;  //$NON-NLS-1$
	public final static String PUNTO="." ; //$NON-NLS-1$
	public final static String PUNTO3="·" ;//$NON-NLS-1$
	public final static String ESPACIO=" ";//$NON-NLS-1$
	public final static String NBSP="\u00a0";//$NON-NLS-1$
	public final static String CAD_VACIA="";//$NON-NLS-1$
	public final static String CERO="0";  //$NON-NLS-1$
	public final static String DOSPUNTOSESP=": ";  //$NON-NLS-1$
	
	public final static String vbCrLf = "\r\n";//$NON-NLS-1$
	
	public final static String SesionCaducadaRaw="Sesión caducada";
	public final static String SesionCaducadaCookiesRaw="Sesión caducada. Verifique que tiene los cookies activados en el navegador";
	public static String SesionCaducada;
	public final static String ARB = "arb";//$NON-NLS-1$
	public final static String TAB = "tab";//$NON-NLS-1$
	public final static String CMD = "cmd";//$NON-NLS-1$
	public final static String TXT = "txt";//$NON-NLS-1$
	public final static String LVW = "lvw";//$NON-NLS-1$
	public final static String LBL = "lbl";//$NON-NLS-1$
	public final static String REC = "rec";//$NON-NLS-1$
	public final static String PAR = "par";//$NON-NLS-1$
	public final static String IMG = "img";//$NON-NLS-1$
	public final static String PIC = "pic";//$NON-NLS-1$
	public final static String WWW = "www";//$NON-NLS-1$
	public final static String BSP = "bsp";//$NON-NLS-1$
	public final static String BSM = "bsm";//$NON-NLS-1$
	public final static String BSC = "bsc";//$NON-NLS-1$
	public final static String GRA = "gra";//$NON-NLS-1$
	final public static String RD  = "rd"; //$NON-NLS-1$
	final public static String DESP  = "desp"; //$NON-NLS-1$
	
	public final static String[] controlesModif={
		ARB, LVW, GRA, RD,
		PAR, BSP, BSM,
		BSC, TXT,
		PIC, WWW, DESP,
		TAB // para mostrar/ocultar pestañas
		};
	
	final public static String EOF  = "_EOF"; //$NON-NLS-1$
	final public static String BOF  = "_BOF"; //$NON-NLS-1$
	final public static String REG  = "_REG"; //$NON-NLS-1$
	final public static String COUNT  = "_COUNT"; //$NON-NLS-1$
	final public static String DS  = "_DS"; //$NON-NLS-1$
	final public static String PK  = "_PK"; //$NON-NLS-1$
	public final static String[] camposMagicos={
		EOF,BOF,REG,COUNT,DS,PK
		};
	
	public final static String GUION_BAJO_NUM = "_num";//$NON-NLS-1$
	
	public final static String CURRENCY = "currency";	//$NON-NLS-1$
	public final static String DOUBLE 	= "double";	 	//$NON-NLS-1$
	public final static String BOOLEAN 	= "boolean";	//$NON-NLS-1$
	public final static String MEMO 	= "memo";		//$NON-NLS-1$
	public final static String STRING 	= "string";		//$NON-NLS-1$
	public final static String LONG 	= "long";		//$NON-NLS-1$
	public final static String INTEGER 	= "integer";	//$NON-NLS-1$
	public final static String SINGLE 	= "single";		//$NON-NLS-1$
	public final static String DATE 	= "date";		//$NON-NLS-1$
	public final static String FILE		= "file";		//$NON-NLS-1$
	public final static String OPTION 	= "option";		//$NON-NLS-1$
	public final static String LIST 	= "list";		//$NON-NLS-1$
	public final static String CONSEJO="consejo"; //$NON-NLS-1$
	public final static String AUTO 	= "auto";		//$NON-NLS-1$
	
	public final static String[] tiposNumericos={CURRENCY, DOUBLE, 
										  BOOLEAN, LONG,
										  INTEGER , SINGLE, AUTO};
	public final static String[] tiposEnteros={ LONG, INTEGER , AUTO};
	public final static String[] tiposGotta={CURRENCY, DOUBLE, 
									  BOOLEAN, LONG,
									  INTEGER , SINGLE,
									  STRING, MEMO, DATE, 
									  OPTION, LIST, 
									  FILE, AUTO};
	public final static String[] tiposAlineadosIzquierda={
		STRING, BOOLEAN, DATE, MEMO
	};
	public final static String[] tiposTexto={
		STRING, MEMO
	};
	private Constantes(){
		try {
			SesionCaducada=URLEncoder.encode(SesionCaducadaRaw,CODIF);
		} catch (UnsupportedEncodingException e) {
			// pass
		}

	}
	public static final String ContacteAdministrador = ". <BR><b>Contacte con el administrador del sistema.</b>";
	
	final public static String SUMA="+";		//$NON-NLS-1$
	final public static String RESTA="-";		//$NON-NLS-1$
	final public static String MULTIPLICACION="*";//$NON-NLS-1$
	final public static String DIVISION="/";	//$NON-NLS-1$
	final public static String POTENCIA="^";	//$NON-NLS-1$
	final public static String CONCATENAR="&";	//$NON-NLS-1$
	
	final public static String MAYOR=">";		//$NON-NLS-1$
	final public static String MENOR="<";		//$NON-NLS-1$
	final public static String IGUAL="=";		//$NON-NLS-1$
	final public static String DISTINTO="#";	//$NON-NLS-1$
	final public static String NO="!";	//$NON-NLS-1$
	
	final public static String PORCENTAJE="%";	//$NON-NLS-1$
	
	public final static String[] OperadoresEvaluador ={
		SUMA ,
		RESTA,
		MULTIPLICACION,
		DIVISION,
		POTENCIA,
		CONCATENAR,
		
		MAYOR,MENOR, IGUAL, DISTINTO
		};
	public static final CharSequence CARACTER_NO_SOPORTADO = "\uFFFD";//"�" //$NON-NLS-1$
	
	public static final String icoGotta = "./fijo/"; //$NON-NLS-1$
	
	public static final int TramiteIniciado=0;
	public static final int TramitePreparadoYVerificado=2;
	public static final int TramiteAccionesEjecutadas=4;
	public static final int TramiteDatosListosParaDescarga = 6;
	public static final int TramiteDatosFirmados=8;
	public static final int TramiteDatosDescargados=10;
	
	public static final int TramiteFinalizado=12;
	
	public final static int[] FaseEjecucionTramite = {TramiteIniciado,
										TramitePreparadoYVerificado,
										TramiteAccionesEjecutadas,
										TramiteDatosListosParaDescarga,
										TramiteDatosDescargados,
										TramiteDatosFirmados,
										TramiteFinalizado};
	
	public enum MarcaBaseDatos{
		Oracle,
		MSSQL,
		SinEspecificar,
		Informix,
		MSAccess, 
		sqlite
		}
	public enum Subtipos {
		CorreoElectronico,
		Positivo,
		Negativo,
		Url,
		SoloNumeros		
		}
	public enum TipoMensajeGotta {
		info, msg, alerta, critico,
		
		sql,  sqlEscribir,
		
		errorSQL, tramite,
		
		archivos, correoe
		
		}
	public enum ColisionFicheros {
		sobreEscribir,
		renombrar,
		descartar	
	}
}
