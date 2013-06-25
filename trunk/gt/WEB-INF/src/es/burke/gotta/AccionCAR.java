package es.burke.gotta;

import java.util.ArrayList;

public class AccionCAR extends AccionADD {
	public String mensaje;
	
	private String nTabla;
	private String nCampo;
	private String nCampoOrderBy;
	private String claveOB;
	private String separador = Constantes.SEPARADOR;
	
	private Boolean mantenerDatosAntiguos=false;
	
	public AccionCAR(Tramite tram) {
		super(tram);}
	@Override
	public void verificar(Fila fila) throws ErrorEvaluador, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorFechaIncorrecta, ErrorAccionCAR, ErrorArrancandoAplicacion {
		String tp1;
		try {
			tp1 = this.evaluaExpresion(p1);
			} 
		catch (ErrorEvaluador e) {
			//debe tratarse de un nombre a pelo
			tp1=p1;
			}

		if (tp1==null)
			throw new ErrorAccionCAR("Hay que indicar el nombre de la tabla a cargar en el parámetro1");
		ITablaDef tAux;
		if (tp1.endsWith("+")){
			mantenerDatosAntiguos=true;
			tp1=tp1.substring(0, tp1.length()-1);
			}
			
		if (tp1.contains(Constantes.PUNTO))  {
			ArrayList<String> temp=Util.split(tp1, Constantes.PUNTO);

			nTabla=temp.get(0);
			nCampo=temp.get(1);
			tAux=this.sacaTablaDef(nTabla);
			
			if (!this.existeTabla(tAux)) 
				throw new ErrorTablaNoExiste("La tabla \""+nTabla+"\", especificada en el parámetro 1 no existe");

			CampoDef camAux=tAux.getCampo(nCampo);
			if (camAux==null) 
				throw new ErrorCampoNoExiste("El campo \""+nCampo+"\", especificado en el parámetro 1 no existe");
			} 
		else  {
			if (this.existeTabla(tp1)) {
				nTabla=tp1;
				tAux=this.sacaTablaDef(nTabla);
				nCampo=tAux.getCampoClave().getCd();
				}
			else
				throw new ErrorTablaNoExiste("La tabla \""+nTabla+"\", especificada en el parámetro 1 no existe");
			}
		if (this.p2==null) 
			throw new ErrorAccionCAR("El parámetro 2 no puede estar vacío");
					
		if (this.p3!=null) {
			if (this.p3.length()==1 && !this.motor.esNombreVariable(p3))
				separador=this.p3;
			else
				separador=this.evaluaExpresion(this.p3);
			}
		if (this.p4!=null) {
				CampoDef camAux=tAux.getCampo(this.p4);
				if (camAux==null) 
					throw new ErrorCampoNoExiste("El campo \""+this.p4+"\", especificado en el parámetro 4 no existe");
				nCampoOrderBy = this.p4;
			}
		
		this.claveOB=p2;
	}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorFechaIncorrecta, ErrorArrancandoAplicacion, ErrorGotta {
		ArrayList<Object> v = Util.splitObjetos( this.evaluaExpresion(claveOB), separador);

		ITabla tAux=this.sacaTabla(nTabla);
		CampoDef camAux=tAux.tdef.getCampo(nCampo);
		CampoDef camOrderBy = null;
		
		if(nCampoOrderBy!=null) 
			camOrderBy = tAux.tdef.getCampo(nCampoOrderBy);
		
		motor.getApli().println("CAR "+nTabla+" : "+v.toString());
		
		try {
			tAux.cargarRegistros(camAux, v, mantenerDatosAntiguos, camOrderBy);
			}
		catch (StringIndexOutOfBoundsException e){
			String idx = this.CD_Operacion+" "+this.tramActivo.idxDepuracion;
			throw new ErrorAccionCAR(e.getClass().getName() +": línea "+idx+" - "+e.getMessage());	
			}
		catch (NumberFormatException e){
			String idx = this.CD_Operacion+" "+this.tramActivo.idxDepuracion;
			throw new ErrorAccionCAR(e.getClass().getName() +": línea "+idx+" - "+e.getMessage());	
			}
		
		return Motor.Resultado.OK;
	}
}
