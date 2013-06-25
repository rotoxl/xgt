package es.burke.gotta;

import java.util.ArrayList;

public class AccionADD extends Accion {
	protected ITablaDef tdef;
	public ITabla tabla;
	
	public AccionADD(Tramite tram)
		{super(tram);}

	@Override
	public void verificar(Fila fila) throws ErrorGotta {
		if (this.p1==null){
			throw new ErrorAccionADD("Hay que indicar una tabla en p1");
			}
		else if (this.motor.esTablaTemporal(this.p1)) {
			//pass
			}
		else {
			tdef = this.sacaTablaDef(this.p1);
			tabla = motor.ponTabla(tdef);
			tabla.marcadaParaNoCargar=true;
			if (!this.existeTabla(tdef))
				throw new ErrorAccionADD(p1);
			}
	}

	public Motor.Resultado _AccionADD_(Accion accion, String strAccion, String p1, String p2, String p3, String p4) throws ErrorGotta {
		if (tabla==null)
			tabla=motor.tablas.get(this.p1);
		
		tabla.usarCache=false;
		if (tramActivo.getTb()==null||tramActivo.getTb().getNumRegistrosCargados()==0)
			tabla.addNew();
		else {//si existe tb, le pasa el valor de su clave al nuevo registro
			if (tramActivo.getTb().tdef==tabla.tdef)
				motor.lote.recuperarNodo=true;								 

			String clavetb=tramActivo.getTb().tdef.getCampoClave().getCd();
			if (this.tabla.tdef.campos.containsKey(clavetb) ) {
				CampoDef camCl = tramActivo.getTb().tdef.getCampoClave();
				ITabla t=this.sacaTabla(camCl.getTabla());								
				ArrayList<Object> v = t.getValorCam(camCl.getCd());
				tabla.addNew(camCl, v);
				}
			else
				tabla.addNew();
			}
		return Motor.Resultado.OK;
		}
	@Override
	public Motor.Resultado ejecutar() throws ErrorGotta {
		_AccionADD_(this, Constantes.ADD, p1, p2, p3, p4);
		
		return Motor.Resultado.OK;
		}
}
