package es.burke.gotta;

public class AccionSUB extends Accion  {
	public AccionSUB(Tramite tram) {
		super(tram);}
	@Override
	public void verificar(Fila fila) throws ErrorGotta {
		Long camino=null;
		if (p1 == null)
			camino=tramActivo.camino;
		else { 
			try {
				if (!motor.esCampo_Variable(p1))
					camino=Long.parseLong(p1);
				} 
			catch (NumberFormatException e) {
				throw new ErrorAccionSUB("El parámetro 1 es incorrecto, debería indicar el camino del trámite a llamar:"+e.getMessage());
				}
			}
		
		if (p2==null)
			throw new ErrorAccionSUB("p2 debería indicar un trámite");	
		else if (!motor.esCampo_Variable(p2)) {//sólo podemos verificar trámite si es un literal
			String tramite = p2;
			
			if (camino!=null){
				Tramite tramSUB;
				//Verificamos el trámite
				try{
					tramSUB = new Tramite(this.lote, camino, tramite, this.tramActivo, CD_Operacion);
					}
				catch (StackOverflowError e) {
					throw new ErrorAccionSUB(e.getMessage());
					}
				tramSUB.verificarTramite();
				}
			}
		}
	@Override
	public Motor.Resultado ejecutar() throws ErrorGotta {
		Long camino;
		if (p1 == null)
			camino=tramActivo.camino;
		else if (motor.esCampo_Variable(p1))
			camino=Long.parseLong( this.evaluaExpresion(p1) );
		else
			camino=Long.parseLong(p1);		
		
		String tramite;
		try	{
			tramite=this.evaluaExpresion(p2);
			}
		catch (ErrorEvaluador e) {
			tramite=p2;
			}
		
		Tramite tramSUB = new Tramite(this.lote, camino, tramite, this.tramActivo, CD_Operacion);
		tramSUB.verificarTramite();
		this.tramActivo.lote.devolverDepuradorCompleto=true;
		this.lote.setTramActivo(tramSUB);
		return this.lote.seguir(); // seguimos con el SUB
		}
}
