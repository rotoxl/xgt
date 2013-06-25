package es.burke.gotta;

public class AccionFOR extends Accion {
	public Long camino;
	public String tramite;
	private Tramite tramSUB;
	public ITabla tabla;
	public Integer volverA;
	
	public AccionFOR(Tramite tram){
		super(tram);}

	@Override
	public Motor.Resultado ejecutar() throws  ErrorGotta{
		if (this.tabla==null)
			this.tabla=motor.tablas.get(this.p1);
			
		if (tabla.datos.size()>0){
			volverA=tabla.registroActual;
			
			String idxDepuracion=(tramSUB!=null? tramSUB.idxDepuracion : null);
			
			tramSUB = new Tramite(lote, camino, tramite, tramActivo, this.CD_Operacion, tabla, volverA);
			if (idxDepuracion!=null) {
				tramSUB.idxDepuracion=idxDepuracion;
				lote.devolverDepuradorCompleto=true;
				}
			
			tramSUB.verificarTramite();
				
			this.lote.setTramActivo(tramSUB);
			tabla.setRegistroActual(0);
			motor.getApli().println(" >> Empieza el FOR, tabla "+tabla.tdef.getCd()+" (fila "+tabla.registroActual+" de "+(tabla.getNumRegistrosCargados()-1)+")");
			Motor.Resultado ret = this.lote.seguir(); // seguimos con el SUB
			return ret;	
			}
		return Motor.Resultado.OK;
		}
	
	@Override
	public void verificar(Fila fila) throws ErrorGotta {			
		//parametro 1- una tabla
		if (p1.equals(Constantes.CAD_VACIA))
			throw new ErrorTablaNoExiste("El p1 debería indicar una nombre de tabla");
		
		if (this.motor.esNombreVariable(this.p1)){
			//pass
			}
		else if (this.motor.esTablaTemporal(this.p1)){
			//pass
			}
		else {
			tabla=this.sacaTabla(this.p1);
			if (tabla==null) 
				throw new ErrorTablaNoExiste("La tabla '"+p1+"', especificada en el parámetro 1, no existe");
			}
		//parametro 2- un camino o nada
		if (p2 == null)
			camino=tramActivo.camino;
		else
			camino=Long.parseLong(p2);
		
		if (p3==null) 
			throw new ErrorAccionFOR("p3 debería indicar un trámite");	
		else if (!motor.esCampo_Variable(p3)) {//sólo podemos verificar trámite si es un literal
			tramite=p3;
			
			//Verificamos el trámite
			tramSUB = new Tramite(tramActivo.lote, camino, tramite, this.tramActivo, this.CD_Operacion) ;
			tramSUB.verificarTramite();
			}
		}
}
