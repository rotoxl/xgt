package es.burke.gotta;

public abstract class Accion {
	//public static enum tipoAccion
	//	{ADD,CAN,CAR,CTP,DEL,DLL,EJE,FRM,GTO,JPP,JPR,LBL,
	//	MOV,MSG,PEN,PSS,REP,SUB, Xno, Xsi, X_X, FOR;}
	
	public static Accion getInstancia(String accion, Tramite tram) throws ErrorAccionDesconocida
	{
		if (accion.equals(Constantes.ADD))
			return new AccionADD(tram);
		else if (accion.equals(Constantes.CAN))
			return new AccionCAN(tram);
		else if (accion.equals(Constantes.CAR))
			return new AccionCAR(tram);
		else if (accion.equals(Constantes.CTP))
			return new AccionCTP(tram);
		else if (accion.equals(Constantes.DEL))
			return new AccionDEL(tram);
		else if (accion.equals(Constantes.DLL))
			return new AccionDLL(tram);
		else if (accion.equals(Constantes.EJE))
			return new AccionEJE(tram);
		else if (accion.equals(Constantes.FRM))
			return new AccionFRM(tram);
		else if (accion.equals(Constantes.FOR))
			return new AccionFOR(tram);
		else if (accion.equals(Constantes.GTO))
			return new AccionGTO(tram);
		else if (accion.equals(Constantes.JPP))
			return new AccionJPP(tram);
		else if (accion.equals(Constantes.JPR))
			return new AccionJPR(tram);
		else if (accion.equals(Constantes.LBL_AC))
			return new AccionLBL(tram);
		else if (accion.equals(Constantes.MOV))
			return new AccionMOV(tram);
		else if (accion.equals(Constantes.MSG))
			return new AccionMSG(tram);
		else if (accion.equals(Constantes.PEN))
			return new AccionPEN(tram);
		else if (accion.equals(Constantes.PSS))
			return new AccionPSS(tram);
		else if (accion.equals(Constantes.REP))
			return new AccionREP(tram);
		else if (accion.equals(Constantes.SUB))
			return new AccionSUB(tram);
		else if (accion.equals(Constantes.Xno))
			return new AccionX(tram);
		else if (accion.equals(Constantes.Xsi))
			return new AccionX(tram);
		else if (accion.equals(Constantes.X_X))
			return new AccionX_X(tram);
		else
			throw new ErrorAccionDesconocida(accion);
	}
	public abstract void verificar(Fila fila) throws ErrorGotta;
	public abstract Motor.Resultado ejecutar() throws ErrorGotta;
	
	public Motor motor;
	public Accion(Tramite tram)	{
		this.tramActivo=tram;
		this.lote=tram.lote;
		this.motor=this.lote.motor;
		}
	
	public long CD_Operacion;
	
	public Object resultadoCondicion;
	public boolean fueEjecutada=false;
	
	Tramite tramActivo;
	
	public String p1;
	public String p2;
	public String p3;
	public String p4;
	public String accion;
	public Lote lote;
	
	public String msgError=null;
	
	/**
	 * @throws ErrorGotta  
	 */
	public void montaTodo(Fila fila) throws ErrorGotta {
		this.accion=fila.get("cd_accion").toString(); //$NON-NLS-1$
		CD_Operacion=Long.parseLong(fila.get("cd_operacion").toString());//$NON-NLS-1$
		p1= Util.toString( fila.get("parametro1") );//$NON-NLS-1$
		p2= Util.toString( fila.get("parametro2") );//$NON-NLS-1$
		p3= Util.toString( fila.get("parametro3") );//$NON-NLS-1$
		p4= Util.toString( fila.get("parametro4") );//$NON-NLS-1$
		}
	public void montaTodo(String acc, String op, String par1, String par2, String par3, String par4){
		this.accion=acc;
		this.CD_Operacion=Long.parseLong(op);
		this.p1= par1;
		this.p2= par2;
		this.p3= par3;
		this.p4= par4;
		}
	public Conexion conn()
		{return this.motor.getUsuario().getConexion();}
	
	public String evaluaExpresion(String q) throws ErrorEvaluador {
		try {
			if(q==null)
				return null;
			return this.motor.getEvaluador().evalua(q).toString();
			}
		catch (NumberFormatException e){
			throw new ErrorEvaluador(e.getMessage());
			} 
		}
	public boolean verificaExpresion(String q)
		{return this.motor.getEvaluador().verifica(q);} 
	
	public ITabla sacaTabla(String n) throws ErrorArrancandoAplicacion, ErrorGotta {
		return motor.ponTabla(n);
		}
	public ITablaDef sacaTablaDef(String n) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion
		{return motor.sacaTablaDef(n);}
	public CampoDef campoTabla(String tabla, String campo) throws ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorArrancandoAplicacion{
		ITablaDef tdef  = sacaTablaDef(tabla);
		return tdef.getCampo(campo);
		}
	public boolean existeTabla(String n) throws ErrorTablaNoExiste, ErrorArrancandoAplicacion{
		if(this.motor.esTablaTemporal(n))
			return true;
		ITablaDef tdef  = sacaTablaDef(n);
		return existeTabla(tdef);
		}
	public boolean existeTabla(ITablaDef tdef) {
		return tdef!=null;
		}
	@Override
	public String toString(){
		return this.accion+'\t'+this.p1+'\t'+this.p2+'\t'+this.p3+'\t'+this.p4;
	}
	}
