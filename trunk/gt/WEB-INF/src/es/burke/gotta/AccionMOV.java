package es.burke.gotta;


public class AccionMOV extends Accion{
	//private ColumnaDef campo;
	private String movimiento;
	private String valor; //para el movimiento To
	
	public AccionMOV(Tramite tram){
		super(tram);}
	@Override
	public void verificar(Fila fila) throws ErrorEvaluador, ErrorTablaNoExiste, ErrorCampoNoExiste, ErrorAccionMOV {
		ITablaDef tdef = this.sacaTablaDef(this.p1);

		if (tdef==null) 
			throw new ErrorTablaNoExiste("No existe la tabla: "+p1);
		this.p2=this.p2.toLowerCase();
		
		if ( Util.en(p2, "last", "first", "next" , "previous")  ) {
			//pass
			}
		else if (p2.equalsIgnoreCase("to")) {
			if (tdef.getCampo(p3)==null)
				throw new ErrorAccionMOV("El dato indicado en p3 '"+p3+"' no es un campo o columna válido.");
			
			if (! this.verificaExpresion(p4)) 
				throw new ErrorEvaluador("Error en parametro4: "+p4);
			} 
		else 
			throw new ErrorAccionMOV("Movimiento MOV desconocido " + p2);
		movimiento=p2;
		valor=p4;
		}
	
	@Override
	public Motor.Resultado ejecutar() throws ErrorArrancandoAplicacion, ErrorGotta {	
		ITabla tabla=this.sacaTabla(this.p1);
		if (movimiento.equals("last"))
			tabla.setRegistroActual(tabla.getNumRegistrosCargados()-1);
		else if (movimiento.equals("first"))
			tabla.setRegistroActual(0);
		else if (movimiento.equals("next"))
			tabla.setRegistroActual(tabla.getRegistroActual()+1);
		else if (movimiento.equals("previous"))
			tabla.setRegistroActual(tabla.getRegistroActual()-1);
		else if (movimiento.equals("to")) {
			if (tabla.getNumRegistrosCargados() <= 0)
				throw new ErrorAccionMOV("ERROR (operacion cancelada): "+tabla.tdef.getCd()+": No hay registros cargados");
						
			String valorBuscado=this.evaluaExpresion(valor);
			if (tabla.findFirst(p3, valorBuscado)==-1)
				throw new ErrorAccionMOV("ERROR (operacion cancelada): "+tabla.tdef.getCd()+": EOF");
			}

		if (tabla.getRegistroActual()>=tabla.getNumRegistrosCargados())
			throw new ErrorAccionMOV("ERROR (operacion cancelada): "+tabla.tdef.getCd()+": EOF");
		else if (tabla.getRegistroActual()<0)
			throw new ErrorAccionMOV("ERROR (operacion cancelada): "+tabla.tdef.getCd()+": BOF");
		
		return Motor.Resultado.OK;
	}
//	private static ArrayList<String> pasaACadenas(ArrayList<Object> valores){
//		ArrayList<String>ret= new ArrayList<String>();
//		for (Object valor:valores)
//			ret.add(valor.toString());
//		return ret;
//	}
//	private static boolean mismoValor(){
//		
//		}
}
