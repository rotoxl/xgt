package es.burke.gotta;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import es.burke.gotta.Tramite.TipoEjecucion;
import es.burke.gotta.dll.DLLGotta;
import es.burke.gotta.dll.DLLJython;

public class AccionDLL extends Accion  {
	public String dll;

	public String parametro;

	//Sólo una de ellas se usa en cada caso
	private DLLGotta dllGotta=null;
	
	public DLLGotta getScript(){
		return this.dllGotta;
	}
    public AccionDLL(Tramite tramite) 
		{super(tramite);}
    
    //verifica la acción
    @Override
	public void verificar(Fila fila) throws ErrorGotta {
    	Aplicacion apli = this.motor.getApli();
    	String mapeo = apli.getDatoConfig(this.p1+'.'+this.p2);
    	if (mapeo!=null){
    		ArrayList<String> dll_accion = Util.split(mapeo,".");
    		dll=dll_accion.get(0);
    		accion=dll_accion.get(1);
    		}
    	else {
    		dll=this.p1;
    		accion=this.p2;
    		}
    	if(this.dll==null|| this.accion==null)
    		throw new ErrorAccionDLL("Hay que especificar los parámetros 1 y 2 (dll y acción)");
        parametro=this.p3;
        this.verificaExpresion(this.p3);
		this.verificaExpresion(parametro);

        if (this.motor.getEsquema().existeDLL_Guiones){
			DLLJython script = new DLLJython(this);
			if(script.comprueba())
				dllGotta=script;
			}
        if(dllGotta==null){
        	try {
				Class<?> cls = Class.forName("es.burke.gotta.dll."+dll);
				Class<? extends DLLGotta> clsG=cls.asSubclass(DLLGotta.class);
				dllGotta=clsG.newInstance();
				} 
    		catch (ClassNotFoundException e) {
				throw new ErrorAccionDLL("Guión/Acción no implementada:" + dll + "/" + accion,e);
				} 
    		catch (InstantiationException e) {
				throw new ErrorAccionDLL("Guión/Acción no implementada:" + dll + "/" + accion,e);
				} 
    		catch (IllegalAccessException e) {
				throw new ErrorAccionDLL("Guión/Acción no implementada:" + dll + "/" + accion,e);
				}
        	}
		dllGotta.verifica(motor, accion, parametro);

    	}

    //ejecuta la accion
    @Override
	public Motor.Resultado ejecutar() throws ErrorGotta  {	
    	Object valorRetorno ;
    	String valorParametro = this.evaluaExpresion(parametro);
    	try {
    		if (this.tramActivo.tipoEjecucion == TipoEjecucion.Revivir)
    			valorRetorno = dllGotta.revivir(this,valorParametro); 
    		else
    			valorRetorno = dllGotta.ejecuta(this,valorParametro); //el resto lo hace el servlet
    		}
    	catch (Exception e){
    		throw new ErrorGotta(e);
    		}
        String tipo = Constantes.STRING;
		if (valorRetorno!=null) {
			if (valorRetorno instanceof FechaGotta) 
		    	tipo = Constantes.DATE;
			else if (valorRetorno instanceof Integer)
		    	tipo=Constantes.LONG;
			else if (valorRetorno instanceof Boolean)
		    	tipo=Constantes.BOOLEAN;
			else if (valorRetorno instanceof BigDecimal){
				BigDecimal r=(BigDecimal)valorRetorno;
				if (r.scale()==0)
					tipo=Constantes.INTEGER;
				else if (r.scale()==2)
					tipo=Constantes.CURRENCY;
				else
					tipo=Constantes.LONG;
				}
		 	}
		
	    // retorno de las dll
		if (p4!=null && p4!=Constantes.CAD_VACIA && !(valorRetorno instanceof Motor.Resultado)){
			ArrayList<String>temp=Util.split(p4);
			
			for (int i=0; i<temp.size(); i++){
				String campoOVariable=temp.get(i);
				
				if (campoOVariable==null || campoOVariable.equals(Constantes.CAD_VACIA))
					continue;
				
				Object trozoValorRetorno=valorRetorno;
				if (valorRetorno instanceof List<?>)
					trozoValorRetorno=((List<?>)valorRetorno).get(i);
				
				if (motor.esNombreVariable(campoOVariable)) {
					//si ya tiene tipo, lo respetamos
					Variable v=motor.lote.getVariable(campoOVariable);
					if (v!=null){
						v.setValor(trozoValorRetorno);
						v.setModificada(true);
						}
					else
						motor.lote.setVariable(campoOVariable, tipo, trozoValorRetorno);
					}
		        else {//es un campo
		            CampoDef cam = motor.tablaCampo(campoOVariable);
		            ITabla t = this.sacaTabla(cam.getTabla());	            
		            ArrayList<Object> v = new ArrayList<Object>();
		            v.add(trozoValorRetorno);
		            t.setValorCam(cam.getCd(), v);
		            }
				}
			}
		if (valorRetorno!=null && valorRetorno instanceof Motor.Resultado)
			return (Motor.Resultado)valorRetorno;
	    return Motor.Resultado.OK;
    	}

}
