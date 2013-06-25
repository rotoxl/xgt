package es.burke.gotta.trabajos;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.TriggerBuilder.newTrigger;

import java.util.Calendar;

import org.quartz.JobDataMap;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;


import es.burke.gotta.Aplicacion;
import es.burke.gotta.Constantes;
import es.burke.gotta.Constantes.TipoMensajeGotta;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.GestorMetaDatos;

public class Trabajos {
	private Aplicacion apli;
	
	private StdSchedulerFactory sf;
	private Scheduler sched;
	
	public Trabajos(Aplicacion apli){
		this.apli=apli;
		}
	public void detenTrabajos(){
		try {
			this.sched.shutdown();
			} 
		catch (SchedulerException e) {
			//pass
			}
		}
	public void cargaTrabajos(int segundos) throws ErrorGotta{
		// estructura: Camino|Tramite|params  || F_Prevista    ||     F_Inicio | F_Fin | Mensaje
		// 				(la fecha de fin sólo se rellenará cuando haya finalizado con éxito)
		this.apli.añadeMSG("-1", "Arrancando programador de tareas", TipoMensajeGotta.tareaProgramada);
		Filas filas=null;
		try {
			filas=GestorMetaDatos.leeTrabajos(apli.getConexion(), apli.getEsquema());
			}
		catch (ErrorGotta e){
			this.apli.añadeMSG("-1", "No hay ninguna tarea, ni siquiera existe la tabla TRA_TrabajosProgramados", TipoMensajeGotta.tareaProgramada);
			return;
			}
		this.apli.añadeMSG("-1", "Hay "+filas.size()+" tarea(s) pendiente(s) en el programador", TipoMensajeGotta.tareaProgramada);
			
        // First we must get a reference to a scheduler
        this.sf = new StdSchedulerFactory();
        try {
			this.sched = sf.getScheduler();
			
			for (int i=0; i<filas.size(); i++){
				Fila fila=filas.get(i);
				
				JobDataMap m=new JobDataMap();
				
				String idx=fila.gets("CD_Trabajo");
				
				FechaGotta fp=(FechaGotta) fila.get("f_prevista");
				if (fp.equals( new FechaGotta("11/11/2011 11:11:11"))){ //truqui para lanzar trabajos inmediantamente
					fp=new FechaGotta();
					fp.add(Calendar.SECOND, segundos);
					}
				
				m.put("f_prevista", fp) ;
				m.put("cd_trabajo", idx);
				
				m.put("cd_camino", fila.get("cd_camino"));
				m.put("cd_tramite", fila.gets("cd_tramite"));
				
				String params=fila.gets("params");
				if (params.startsWith(Constantes.COMILLA) && params.endsWith(Constantes.COMILLA))
					params=params.substring(1, params.length()-1);
				m.put("params", params);
				
				m.put("cd_aplicacion", apli.getCd());
				
				iniciaTrabajo(fp, idx, m);
				}
			
			// Start up the scheduler (nothing can actually run until the scheduler has been started)
			sched.start();
			} 
        catch (SchedulerException e) {
        	this.apli.añadeMSG("-1", "Error al preparar tareas del programador: "+e.getMessage(), TipoMensajeGotta.tareaProgramada);
//        	throw new ErrorGotta("Error al iniciar el programador de tareas: "+e.getMessage());
			}
		}
	private void iniciaTrabajo(FechaGotta fg, String id, JobDataMap jobData) throws SchedulerException {
		String idGrupo="trabajos_"+this.apli.getCd();
		
        // define the job and tie it to our HelloJob class
        JobDetail job = newJob(Trabajo.class)
        	.usingJobData(jobData)
            .withIdentity("j"+id, idGrupo)
            .build();
        
        // Trigger the job to run on the next round minute
        Trigger trigger = newTrigger()
            .withIdentity("t"+id, idGrupo)
            .startAt(fg.getTime())
            .build();
        
        // Tell quartz to schedule the job using our trigger
        sched.scheduleJob(job, trigger);
        }
	}
