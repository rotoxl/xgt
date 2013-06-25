package es.burke.gotta;


class ErrorAccionDesconocida  extends ErrorAccion{
	private static final long serialVersionUID = 6597208510987655742L;
	public ErrorAccionDesconocida(String msg)
		{super(msg);}
}

class ErrorCambiandoContrasenha extends ErrorGotta	{
	private static final long serialVersionUID = 4505212827630249254L;
	public ErrorCambiandoContrasenha(String msg)
		{super(msg);}
	}

class ErrorDiccionario extends ErrorGotta	{
	private static final long serialVersionUID = 3907443418752074826L;
	public ErrorDiccionario(String msg)
		{super(msg);}
	public ErrorDiccionario(String msg, Exception e) {
		super(msg, e);
		}
	}
class ErrorCampoNoExiste extends ErrorDiccionario	{
	private static final long serialVersionUID = -416414298908009209L;
	public ErrorCampoNoExiste(String msg)
		{super(msg);}
	}
class ErrorTablaNoExiste extends ErrorDiccionario{
private static final long serialVersionUID = -8006077438336351457L;
public ErrorTablaNoExiste(String msg)
	{super(msg);}
}

class ErrorFilaNoExiste extends ErrorGotta{
private static final long serialVersionUID = -8006077438336351457L;
public ErrorFilaNoExiste(String msg)
	{super(msg);}
}

class ErrorTramite extends ErrorGotta	{
	private static final long serialVersionUID = 5219831594443689949L;
	public ErrorTramite(String msg)
		{super(msg);}
	}
class ErrorTramiteNoExiste extends ErrorTramite	{
	private static final long serialVersionUID = 1077872532227516185L;
	public ErrorTramiteNoExiste(String msg)
		{super(msg);}
	}
class ErrorTramPendienteNoEncontrado extends ErrorTramite	{
	private static final long serialVersionUID = -5181471032757458778L;

	public ErrorTramPendienteNoEncontrado(String msg)
		{super(msg);}
	}
class ErrorLBLNoExiste extends ErrorAccionLBL	{
	private static final long serialVersionUID = -2503620405926615133L;
	public ErrorLBLNoExiste(String msg)
		{super(msg);}
	}


//-----------------------------------------------------------
class ErrorAccionADD extends ErrorAccion{
private static final long serialVersionUID = 4561021875381664005L;
public ErrorAccionADD(String msg)
{super(msg);}
}
class ErrorAccionCAN extends ErrorAccion{
private static final long serialVersionUID = -5684793052254860741L;
public ErrorAccionCAN(String msg)
{super(msg);}
}
class ErrorAccionCAR extends ErrorAccion{
private static final long serialVersionUID = -1512697004990786009L;
public ErrorAccionCAR(String msg)
{super(msg);}
}
class ErrorAccionCTP extends ErrorAccion	{
	private static final long serialVersionUID = 3548511492855005234L;
	public ErrorAccionCTP(String msg)
	{super(msg);}
	}
class ErrorAccionDEL extends ErrorAccion{
private static final long serialVersionUID = -7528833276001286600L;
public ErrorAccionDEL(String msg)
{super(msg);}
}
class ErrorAccionEJE extends ErrorAccion{
private static final long serialVersionUID = -179496224144855546L;
public ErrorAccionEJE(String msg)
{super(msg);}
}
class ErrorAccionFOR extends ErrorAccion{
private static final long serialVersionUID = 2667168977672414186L;
public ErrorAccionFOR(String msg)
{super(msg);}
}
class ErrorAccionFRM extends ErrorAccion{
private static final long serialVersionUID = -5923775509630149421L;
public ErrorAccionFRM(String msg)
{super(msg);}
}
class ErrorAccionGTO extends ErrorAccion	{
	private static final long serialVersionUID = -4504183695905668159L;
	public ErrorAccionGTO(String msg)
	{super(msg);}
	}
class ErrorAccionJPP extends ErrorAccion	{
	private static final long serialVersionUID = 7060963326335971494L;

	public ErrorAccionJPP(String msg)
	{super(msg);}
	}
class ErrorAccionJPR extends ErrorAccion{
	private static final long serialVersionUID = -5613905645810232562L;
	public ErrorAccionJPR(String msg)
	{super(msg);}
	}
class ErrorAccionLBL extends ErrorAccion{
	private static final long serialVersionUID = -7460151910371532439L;

	public ErrorAccionLBL(String msg)
	{super(msg);}
	}
class ErrorAccionMOV extends ErrorAccion{
	private static final long serialVersionUID = 5517812618745991317L;
	public ErrorAccionMOV(String msg)
	{super(msg);}
	}
class ErrorAccionMSG extends ErrorAccion{
private static final long serialVersionUID = -7035074919379755964L;
public ErrorAccionMSG(String msg)
{super(msg);}
}
class ErrorAccionPEN extends ErrorAccion{
private static final long serialVersionUID = -6574628654330342775L;
public ErrorAccionPEN(String msg)
{super(msg);}
}
class ErrorAccionPSS extends ErrorAccion{
private static final long serialVersionUID = -8820742034386167839L;
public ErrorAccionPSS(String msg)
{super(msg);}
}
class ErrorAccionREP extends ErrorAccion{
private static final long serialVersionUID = -8135931838252385863L;
public ErrorAccionREP(String msg)
{super(msg);}
}
class ErrorAccionSUB extends ErrorAccion	{
	private static final long serialVersionUID = -2183879295843756797L;

	public ErrorAccionSUB(String msg)
	{super(msg);}
	}
class ErrorAccionX_X extends ErrorAccion{
private static final long serialVersionUID = 6886993508180483131L;
public ErrorAccionX_X(String msg)
{super(msg);}
}
class ErrorAccionX extends ErrorAccion{
private static final long serialVersionUID = 1948106572568635841L;
public ErrorAccionX(String msg)
{super(msg);}
}

class ErrorFU_Tramite extends ErrorTramite{
	private static final long serialVersionUID = -8949987876706611596L;
	public ErrorFU_Tramite(String msg)
	{super(msg);}
}
//class ErrorTablasBaseNoCoinciden extends ErrorTramite
//{
//	private static final long serialVersionUID = 8742335023421200215L;
//
//	public ErrorTablasBaseNoCoinciden(String msg)
//	{super(msg);}
//}

//class ErrorInformeNoTienePáginas extends ErrorGenerandoInforme
//{
//	private static final long serialVersionUID = -8362241671982732444L;
//	public ErrorInformeNoTienePáginas(String msg)
//		{super(msg);}
//}

class ErrorTiposNoCoinciden extends ErrorGotta{
	private static final long serialVersionUID = -61458806252678902L;
	public ErrorTiposNoCoinciden(String msg)
	{super(msg);}
}


class ErrorCargandoTabla extends ErrorDiccionario	{
	private static final long serialVersionUID = -8949987876706611596L;
	public ErrorCargandoTabla(String msg)
	{super(msg);}
	public ErrorCargandoTabla(String msg, Exception e) {
		super(msg, e);
		}
	}
class ErrorNoImplementado extends ErrorGotta	{
	private static final long serialVersionUID = -7564793587289810601L;
	public ErrorNoImplementado(String msg)
	{super(msg);}
	}
class ErrorVolcandoDatos extends ErrorGotta{
	private static final long serialVersionUID = -3725076175771314017L;
	public ErrorVolcandoDatos(String msg)
		{super(msg);}
}
class ErrorRellenandoControl extends ErrorGotta{
	private static final long serialVersionUID = 2434605839610539399L;
	public ErrorRellenandoControl(String msg)
	{super(msg);}
	public ErrorRellenandoControl(String causa, Throwable e)
		{super(causa,e);}
}

class ErrorNumeroIncorrecto extends ErrorGotta{
	private static final long serialVersionUID = -6174089498853549874L;
	public ErrorNumeroIncorrecto(String msg)
		{super(msg);}
}

class ErrorSQLoParametrosIncorrectos extends ErrorGotta{
	private static final long serialVersionUID = -4656602845460502463L;
	public ErrorSQLoParametrosIncorrectos(String msg)
		{super(msg);}
}
//------------------------------------------------
class ErrorHayQuePincharFilas extends ErrorGotta{
	private static final long serialVersionUID = 4375132275016953491L;
	public ErrorHayQuePincharFilas(String msg)
		{super(msg);}
	}
class ErrorListaDeTramitesMalHecha extends ErrorGotta{
	private static final long serialVersionUID = -155756323406810347L;
	public ErrorListaDeTramitesMalHecha(String msg)
		{super(msg);}
	}
class ErrorTramiteActivoHaCambiado extends ErrorGotta {
	private static final long serialVersionUID = -7980656714067924820L;
	public ErrorTramiteActivoHaCambiado(String msg)
		{super(msg);}
}
class ErrorEmitiendoInforme extends ErrorAccionDLL{
	private static final long serialVersionUID = 4721082500605312677L;
	public ErrorEmitiendoInforme(String msg){
		super(msg);
		}
}
