package es.burke.gotta;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public final class Constantes {
	public static final String EURO="€";//$NON-NLS-1$
	public static final String UTF8 = "UTF-8";//$NON-NLS-1$
	public static final String ISO885915 = "ISO-8859-15";//$NON-NLS-1$
	public static final String ISO88591 = "ISO-8859-1";//$NON-NLS-1$
	public final static String CODIF = UTF8;//$NON-NLS-1$
//////////////////////////////
	
	
	public final static String SEPARADOR="\\";//$NON-NLS-1$
	public final static String ARROBA="@";//$NON-NLS-1$
	
	public static final String COMILLA = "'"; //$NON-NLS-1$
	public static final String COMILLAS = "\""; //$NON-NLS-1$
	public static final String COMA = ","; //$NON-NLS-1$
	public final static String PIPE="|" ;  //$NON-NLS-1$
	public final static String GUIÓN_BAJO="_" ;  //$NON-NLS-1$
	public final static String PUNTO="." ; //$NON-NLS-1$
	public final static String PUNTO3="·" ;//$NON-NLS-1$
	public final static String ESPACIO=" ";//$NON-NLS-1$
	public final static String NBSP="\u00a0";//$NON-NLS-1$
	public final static String CAD_VACIA="";//$NON-NLS-1$
	public final static String CERO="0";  //$NON-NLS-1$
	public final static String DOSPUNTOSESP=": ";  //$NON-NLS-1$
	
	public final static String vbCrLf = "\r\n";//$NON-NLS-1$
	public final static String vbCr = "\n";//$NON-NLS-1$
//////////////////////////////
	
	public final static String SesionCaducadaRaw="Sesión caducada";
	public final static String SesionCaducadaCookiesRaw="Sesión caducada. Verifique que tiene los cookies activados en el navegador";
	public static String SesionCaducada;
	
	public static String objetoJSVacio="{}";//$NON-NLS-1$
	
	public final static String ARB = "arb";//$NON-NLS-1$
	public final static String TAB = "tab";//$NON-NLS-1$
	public final static String PAG = "pag";//$NON-NLS-1$
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
	public final static String RD  = "rd"; //$NON-NLS-1$
	public final static String DESP  = "desp"; //$NON-NLS-1$
	public final static String SUBGRID = "subgrid"; //$NON-NLS-1$
	
	public final static String[] controlesModif={
		ARB, LVW, GRA, RD,
		PAR, BSP, BSM,
		BSC, TXT,
		PIC, WWW, DESP,
		SUBGRID,
		TAB, PAG // para mostrar/ocultar pestañas
		};
	
//////////////////////////////	
	public final static String key="key";//$NON-NLS-1$
	public final static String cd="cd";//$NON-NLS-1$
	public final static String ds="ds";//$NON-NLS-1$
	public final static String md="mododetalle";//$NON-NLS-1$
	public final static String mds="mododetallesiguiente";//$NON-NLS-1$
	public final static String cdplus="cdplus";//$NON-NLS-1$
	public final static String icono="icono";//$NON-NLS-1$
	public final static String activar="_activar";//$NON-NLS-1$
	public final static String expandir="_expandir";//$NON-NLS-1$
	public final static String expandido="_expandido";//$NON-NLS-1$
	
	public final static String[] colsGotta={key, cd, ds, md, mds, cdplus, icono, activar, expandir};
//////////////////////////////
	
	final public static String EOF  = "_EOF"; //$NON-NLS-1$
	final public static String BOF  = "_BOF"; //$NON-NLS-1$
	final public static String REG  = "_REG"; //$NON-NLS-1$
	final public static String COUNT  = "_COUNT"; //$NON-NLS-1$
	final public static String DS  = "_DS"; //$NON-NLS-1$
	final public static String PK  = "_PK"; //$NON-NLS-1$
	public final static String[] camposMagicos={
		EOF,BOF,REG,COUNT,DS,PK
		};
//////////////////////////////
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
//////////////////////////////
	
	private Constantes(){
		try {
			SesionCaducada=URLEncoder.encode(SesionCaducadaRaw,CODIF);
		} catch (UnsupportedEncodingException e) {
			// pass
		}

	}
	public static String tipoGotta(String tipoJava) {
		if (tipoJava==null || tipoJava.equals(CAD_VACIA))
			return CAD_VACIA;
		else if (tipoJava.equalsIgnoreCase("java.lang.Integer") )//$NON-NLS-1$
			return INTEGER;
		else if (tipoJava.equalsIgnoreCase("java.lang.Short") )//$NON-NLS-1$
			return INTEGER;
		else if (tipoJava.equalsIgnoreCase("java.lang.boolean") )//$NON-NLS-1$
			return BOOLEAN;
		else if (tipoJava.equalsIgnoreCase("java.lang.string") )//$NON-NLS-1$
			return STRING;		
		else if (tipoJava.equalsIgnoreCase("date") || tipoJava.equalsIgnoreCase("java.sql.Timestamp") )//$NON-NLS-1$
			return DATE;
		else if (tipoJava.equalsIgnoreCase("java.lang.Double"))//$NON-NLS-1$
			return DOUBLE;
		else if (tipoJava.equalsIgnoreCase("java.math.BigDecimal"))//$NON-NLS-1$
			return CURRENCY;
		else if (Util.en(tipoJava, tiposGotta) )
			return tipoJava.toLowerCase();
		else 
			return STRING;
		
		}
	
	public final static String CD_Tramite ="cd_tramite"; //$NON-NLS-1$
	final public static String ADD="ADD"; //$NON-NLS-1$
	final public static String CAN="CAN"; //$NON-NLS-1$
	final public static String CAR="CAR"; //$NON-NLS-1$
	final public static String CTP="CTP"; //$NON-NLS-1$
	final public static String DEL="DEL"; //$NON-NLS-1$
	final public static String DLL="DLL"; //$NON-NLS-1$
	final public static String EJE="EJE"; //$NON-NLS-1$
	final public static String FOR="FOR"; //$NON-NLS-1$
	final public static String FRM="FRM"; //$NON-NLS-1$
	final public static String GTO="GTO"; //$NON-NLS-1$
	final public static String JPP="JPP"; //$NON-NLS-1$
	final public static String JPR="JPR"; //$NON-NLS-1$
	final public static String LBL_AC="LBL"; //$NON-NLS-1$
	final public static String MOV="MOV"; //$NON-NLS-1$
	final public static String MSG="MSG"; //$NON-NLS-1$
	final public static String PEN="PEN"; //$NON-NLS-1$
	final public static String PSS="PSS"; //$NON-NLS-1$
	final public static String REP="REP"; //$NON-NLS-1$
	final public static String SUB="SUB";  //$NON-NLS-1$
	final public static String Xno="X!"; //mostrar sin permitir edición //$NON-NLS-1$
	final public static String Xsi="X!?";//mostrar permitiendo edición //$NON-NLS-1$
	final public static String X_X="X>X";//mover //$NON-NLS-1$
	
	public static final String ContacteAdministrador = ". <BR><b>Contacte con el administrador del sistema.</b>";
	
	final public static String SUMA="+";		//$NON-NLS-1$
	final public static String RESTA="-";		//$NON-NLS-1$
	final public static String MULTIPLICACIÓN="*";//$NON-NLS-1$
	final public static String DIVISIÓN="/";	//$NON-NLS-1$
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
		MULTIPLICACIÓN,
		DIVISIÓN,
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
		MySQL,
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
		
		archivos, correoe, idioma, informe, proceso, 
		
		login, logout, loginIncorrecto,
		
		buscador,
		cache,
		
		tareaProgramada
		}
	public enum ColisionFicheros {
		sobreEscribir,
		renombrar,
		descartar	
		}
}
