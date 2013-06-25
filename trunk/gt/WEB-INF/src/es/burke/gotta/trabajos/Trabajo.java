package es.burke.gotta.trabajos;

import java.util.ArrayList;

import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
//import org.quartz.JobKey;

import es.burke.gotta.Aplicacion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.ITabla;
import es.burke.gotta.Lote;
import es.burke.gotta.PoolAplicaciones;
import es.burke.gotta.Util;
import es.burke.gotta.Constantes.TipoMensajeGotta;

public class Trabajo implements Job {
	public void execute(JobExecutionContext context) throws JobExecutionException {
//		JobKey jobKey = context.getJobDetail().getKey();
		JobDataMap m=context.getJobDetail().getJobDataMap();
		
		Object cd_trabajo=m.get("cd_trabajo");
		
		Object cd_camino=m.get("cd_camino");
		Object cd_tramite=m.get("cd_tramite").toString();
		String params=Util.toString(m.get("params"));
		
		String cd_aplicacion=m.getString("cd_aplicacion").toString();
		Aplicacion apli=PoolAplicaciones.sacar(cd_aplicacion);
		
		Lote nl=null;
		FechaGotta f_inicio=new FechaGotta();
		try {
			apli.añadeMSG("-1", "Arrancando tarea programada (id="+cd_trabajo+")", TipoMensajeGotta.tareaProgramada);
			
			nl=Lote.montaLote(apli.getUsuSistema(), Long.parseLong(cd_camino.toString()), cd_tramite.toString() );
			
			ArrayList<String> temp= Util.split( params, Constantes.SEPARADOR);
			for ( String trozo:temp ) {
				if (trozo.length()>1){
					String k=trozo.substring(0, 1);
					String o = trozo.substring(1);
					nl.setVariable(k, Constantes.STRING, o);
					}
				}

			nl.seguir();
			
			String msg=nl.variables.containsKey("@msg_retorno") ? nl.getVariable("@msg_retorno").getValor().toString() : null;
			actualizaEstadoTrabajo(nl, cd_trabajo, f_inicio, new FechaGotta(), msg);
			apli.añadeMSG("-1", "Tarea programada (id="+cd_trabajo+") finalizada", TipoMensajeGotta.tareaProgramada);
			} 
		catch (ErrorGotta e) {
			apli.añadeMSG("-1", "La tarea programada (id="+cd_trabajo+") ha dado errores: "+e.getMessage(), TipoMensajeGotta.tareaProgramada);
			actualizaEstadoTrabajo(nl, cd_trabajo, f_inicio, null, e.getMessage());
			return;
			} 
		}
	private void actualizaEstadoTrabajo(Lote lote, Object cd_trabajo, FechaGotta f_inicio, FechaGotta f_fin, String msg){
		try {
			//limpiamos (por si acaso ha petado)
			for (ITabla temp:lote.motor.tablas.values())
				temp.untouch();
				
			ITabla t=lote.motor.ponTabla("TRA_TrabajosProgramados");
			t.cargarRegistros(t.tdef.getCampoClave(), Util.creaLista(cd_trabajo) );
			
			t.setValorCol("f_inicio", f_inicio);
			t.setValorCol("f_fin", f_fin);
			t.setValorCol("mensaje", msg);
			
			lote.motor.tramActivo().hazCommit();
			} 
		catch (ErrorGotta e) {
			
			e.printStackTrace();
			}
		}
}