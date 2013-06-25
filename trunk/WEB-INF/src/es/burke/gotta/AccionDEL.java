package es.burke.gotta;

import java.util.ArrayList;

public class AccionDEL extends Accion{
	public String mensaje;
	
	private Boolean borraSolo1=false;
	private String nTabla;
	private String nCampo;
	private String claveOB;
	private String separador = Constantes.SEPARADOR;
	
	public AccionDEL(Tramite tram){
		super(tram);}
	@Override
	public void verificar(Fila fila) throws ErrorEvaluador, ErrorCampoNoExiste, ErrorTablaNoExiste, ErrorFechaIncorrecta, ErrorAccionCAR, ErrorArrancandoAplicacion {
		String tp1=p1;
		
		if (p2!=null  ) { //p1=tabla.campo
			borraSolo1=false;
			ArrayList<String> temp=Util.split(tp1+Constantes.PUNTO, Constantes.PUNTO);

			nTabla=temp.get(0);
			nCampo=temp.get(1);
			ITablaDef tAux=this.sacaTablaDef(nTabla);
			
			if (!this.existeTabla(tAux)) 
				throw new ErrorTablaNoExiste("La tabla \""+nTabla+"\", especificada en el parámetro 1 no existe");
			
			CampoDef camAux;
			if (nCampo==null || nCampo.equals("")) {
				camAux=tAux.getCampoClave();
				nCampo=camAux.getCd();
				}
			else {
				camAux=tAux.getCampo(nCampo);
				if (camAux==null) 
					throw new ErrorCampoNoExiste("El campo \""+nCampo+"\", especificado en el parámetro 1 no existe");
				}
			
			if (this.p3!=null) {
				if (this.p3.length()==1 && !this.motor.esNombreVariable(p3))
					separador=this.p3;
				else
					separador=this.evaluaExpresion(this.p3);
				}
			
			this.claveOB=p2;
			} 
		else  { //p1=tabla
			borraSolo1=true;
			nTabla=tp1;
			if (!this.existeTabla(tp1)) 
				throw new ErrorTablaNoExiste("La tabla \""+nTabla+"\", especificada en el parámetro 1 no existe");
			}
		}
	@Override
	public Motor.Resultado ejecutar() throws ErrorArrancandoAplicacion, ErrorGotta {
		ITabla t=this.sacaTabla(nTabla);
		if (borraSolo1){
			t.delete();
			}
		else {
			ArrayList<Object> valor = Util.splitObjetos( this.evaluaExpresion(claveOB), separador);
			Boolean existe=t.findKey(valor, nCampo, true);
			while (existe==true){
				t.delete();
				existe=t.findKey(valor, nCampo, true);	
				}
			}
		t.usarCache=false;
		return Motor.Resultado.OK;
		}
}
