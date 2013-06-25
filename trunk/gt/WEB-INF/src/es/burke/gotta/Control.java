package es.burke.gotta;

public abstract class Control  {
	public ControlDef def;
	Usuario usr;
	public static Control getInstancia(ControlDef con, Usuario usuario) throws ErrorGotta {
		if (con.tc.equals(Constantes.LVW))return new ControlLVW(con,usuario);
		if (con.tc.equals(Constantes.PAR))return new ControlPAR(con,usuario);
		if (con.tc.equals(Constantes.BSP))return new ControlBSP(con,usuario);
		if (con.tc.equals(Constantes.BSM))return new ControlBSM(con,usuario);
		if (con.tc.equals(Constantes.DESP))return new ControlDESP(con,usuario);
		if (con.tc.equals(Constantes.SUBGRID))return new ControlLVW(con,usuario);
		
		throw new ErrorRellenandoControl("¡Este tipo de control no se cachea! ("+con.tc+")");
		}
	@Override
	public String toString()
		{return "Control["+def.tc+Constantes.ESPACIO+def.getCaption(usr)+"]";}
}

