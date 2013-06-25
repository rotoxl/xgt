package es.burke.gotta.dll;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.mpxj.Duration;
import net.sf.mpxj.MPXJException;
import net.sf.mpxj.ProjectFile;
import net.sf.mpxj.Rate;
import net.sf.mpxj.Relation;
import net.sf.mpxj.RelationType;
import net.sf.mpxj.Resource;
import net.sf.mpxj.ResourceAssignment;
import net.sf.mpxj.Task;
import net.sf.mpxj.TimeUnit;
import net.sf.mpxj.mspdi.MSPDIReader;
import net.sf.mpxj.mspdi.MSPDIWriter;
import es.burke.gotta.Accion;
import es.burke.gotta.Archivo;
import es.burke.gotta.Coleccion;
import es.burke.gotta.ColumnaDef;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Fila;
import es.burke.gotta.Filas;
import es.burke.gotta.INivelDef;
import es.burke.gotta.Motor;
import es.burke.gotta.NodoActivo;
import es.burke.gotta.TablaTemporal;
import es.burke.gotta.TablaTemporalDef;
import es.burke.gotta.Util;

public class MSProject extends DLLGotta {
	private String ruta;
	private TablaTemporalDef tdef;
	private TablaTemporal t;
	ProjectFile project;
	private NodoActivo nodoActivo;
	private String nivelExportar;
	
	@Override
	public String accionesValidas() {return "leeRecursos escribeRecursos leeTareas escribeTareas";}
	@Override
	public void verifica(Motor xMotor, String xAccion, String nombre) throws ErrorGotta {
		verificaAccionValida(xAccion);
		this.mMotor=xMotor;
		this.usr=mMotor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {return null;}

	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
        if (accion.accion.toLowerCase().startsWith("lee")) {
        	ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);
        	String nombreTabla=temp.get(0);
        	ruta=Util.rutaFisicaRelativaOAbsoluta(temp.get(1));
        	
        	this.project=leeMSProject(ruta);        	
        	this.tdef=new TablaTemporalDef(nombreTabla, ruta);
        	
        	if (accion.accion.equalsIgnoreCase("leeRecursos")) {
	        	montaEstructura_recursos();
	        	this.t = (TablaTemporal)tdef.newTabla(usr);        	
	        	lee_recursos();
	        	}
        	else if (accion.accion.equalsIgnoreCase("leeTareas")) {
	        	montaEstructura_tareas();
	        	this.t = (TablaTemporal)tdef.newTabla(usr);        	
	        	lee_tareas();
	        	}
        	this.mMotor.tablas.put(nombreTabla, this.t);
        	this.t.setRegistroActual(0);
        	
        	}
        else if (accion.accion.toLowerCase().startsWith("escribe")) {
        	ArrayList<String>temp=Util.split(valor.toString(), Constantes.PIPE);

        	this.ruta=Util.rutaFisicaRelativaOAbsoluta(temp.get(1));
			this.nivelExportar=temp.get(0);
			
        	this.project=recuperaSiExiste(ruta);
        	
        	Filas filas=ejecutaNivel(this.nivelExportar);
        	if (accion.accion.equalsIgnoreCase("escribeRecursos")) {
        		genera_recursos(filas);
    			}
        	else if (accion.accion.equalsIgnoreCase("escribeTareas")) {
        		genera_tareas(filas);
    			}
        	escribeMSProject(this.project, this.ruta);
        	}
        return null;
		}
	private Filas ejecutaNivel(String nombreNivel) throws ErrorGotta{
		INivelDef nivel=this.usr.getApli().getNivelDef(nombreNivel);
		Filas filas = nivel.obtenerNivel().lookUp(this.usr.getConexion(), this.mMotor.tramActivo().nodoActivo, -1);
		return filas;
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws IOException, ErrorGotta, ServletException {

	}
////////////////////////////////
	public static ProjectFile leeMSProject(String ruta) throws ErrorAccionDLL{
		MSPDIReader reader = new MSPDIReader();
		
		ProjectFile project;
		try {
			project = reader.read(ruta);
			} 
		catch (MPXJException e) {
			throw new ErrorAccionDLL(e);
			}
		return project;
		}
	public void escribeMSProject(ProjectFile projectFile, String ruta) throws ErrorGotta {
		MSPDIWriter writer = new MSPDIWriter();
		try {
			writer.write(projectFile, ruta);
			} 
		catch (IOException e) {
			throw new ErrorGotta(e);
			}
		}
	public static ProjectFile recuperaSiExiste(String ruta) throws ErrorGotta{
		if (Archivo.existeArchivo(ruta))
			return leeMSProject(ruta);
		else {
			Archivo.generaEstructuraDirectorios(ruta);
			
			ProjectFile projectFile=new ProjectFile();
			projectFile.getProjectHeader().setCurrencySymbol(Constantes.EURO);
			projectFile.getProjectHeader().setCurrencyCode("EUR");
//			projectFile.getProjectHeader().setDefaultStartTime();
			

			return projectFile;			
			}
		}
	private void anhadeCampoEstructura(String nc, String tipo, int longitud){
		ColumnaDef cd=new ColumnaDef(nc, nc, tipo, longitud, null);
		this.tdef.columnas.put(cd.getCd(), cd);
		}
//////////////////////////////	
	private void montaEstructura_recursos(){
		anhadeCampoEstructura("_ruta", Constantes.STRING, 500);
		
		anhadeCampoEstructura("posicionRecurso", Constantes.INTEGER, 9);
		anhadeCampoEstructura("ID_Recurso", Constantes.INTEGER, 9);
		anhadeCampoEstructura("nombre", Constantes.STRING, 250);
		anhadeCampoEstructura("iniciales", Constantes.STRING, 250);
		anhadeCampoEstructura("email", Constantes.STRING, 250);
		anhadeCampoEstructura("tasa", Constantes.CURRENCY, 9);
		
		this.tdef.generaCamposDesdeColumnas();
		}
	private void lee_recursos(){
		Filas filas= this.t.datos;
		
		for (Resource r: this.project.getAllResources()){
			if (r.getUniqueID()==0) continue;
			Fila fila=new Fila(filas);
			
			fila.put("_ruta", this.ruta);
			
			fila.put("posicionRecurso", r.getID());
			fila.put("ID_Recurso", r.getUniqueID());
			fila.put("nombre", r.getName());
			fila.put("iniciales", r.getInitials());
			fila.put("email", r.getEmailAddress());
			fila.put("tasa", r.getStandardRate().getAmount());
			
			filas.add(fila);
			}
		}
//////////////////////////////
	private void montaEstructura_tareas(){
		anhadeCampoEstructura("_ruta", Constantes.STRING, 500);
		
		anhadeCampoEstructura("posicionTarea", Constantes.STRING, 500);
		
		anhadeCampoEstructura("DS_Tarea", Constantes.STRING, 500);
		anhadeCampoEstructura("ID_Tarea", Constantes.INTEGER, 9);
		anhadeCampoEstructura("ID_TareaPadre", Constantes.INTEGER, 9);
		anhadeCampoEstructura("ID_TareasPredecesoras", Constantes.STRING, 30);
		
		anhadeCampoEstructura("duracion", Constantes.STRING, 30);
		
		anhadeCampoEstructura("F_Inicio", Constantes.DATE, 19);
		anhadeCampoEstructura("F_Fin", Constantes.DATE, 19);
		anhadeCampoEstructura("porcentajeCompletado", Constantes.CURRENCY, 9);
		
		anhadeCampoEstructura("ID_Recurso", Constantes.INTEGER, 9);
		
		this.tdef.generaCamposDesdeColumnas();
		}
	private void lee_tareas(){
		Filas filas= this.t.datos;
		
		lee_tareashijas(filas, this.project.getChildTasks(), 0);
		}
	private void lee_tareashijas(Filas filas, List<Task> hijos, int ID_TareaPadre){
		for (Task t:hijos){
			if (t.getUniqueID()==0) {
				lee_tareashijas(filas, t.getChildTasks(), t.getUniqueID());
				continue;
				}
			if (t.getResourceAssignments().size()==0)
				lee_infoTarea(filas, t, null, ID_TareaPadre);
			else {				
				for (ResourceAssignment rc: t.getResourceAssignments())
					lee_infoTarea(filas, t, rc, ID_TareaPadre);
				}
			lee_tareashijas(filas, t.getChildTasks(), t.getUniqueID());
			}
		}
	private List<Task> getPredecesores(Task t){
		List<Task> ret= new ArrayList<Task>();
		if (t.getPredecessors()==null)
			return ret;
		for (Relation r: t.getPredecessors()){
			if (r.getType()==RelationType.FINISH_START)
				ret.add(this.project.getTaskByUniqueID(r.getTargetTask().getUniqueID() ) );
			}
		return ret;
		}
	private String sacaUniqueIDs(List<Task> l){
		if (l.size()==0)
			return null;
		String s="";
		for (Task t:l){
			s+=t.getUniqueID()+";";
			}
		return s.substring(0, s.length()-1);
	}
	private void lee_infoTarea(Filas filas, Task t, ResourceAssignment rc, int ID_TareaPadre){
		Fila fila=new Fila(filas);
		filas.add(fila);
		
		fila.put("_ruta", this.ruta);
		fila.put("posicionTarea", t.getID());
		fila.put("DS_Tarea", t.getName());
		fila.put("ID_Tarea", t.getUniqueID());
		fila.put("ID_TareasPredecesoras", sacaUniqueIDs(getPredecesores(t)));
		if (ID_TareaPadre!=0) 
			fila.put("ID_TareaPadre", ID_TareaPadre);
		fila.put("duracion", t.getDuration().toString());
		
		fila.put("porcentajeCompletado", t.getPercentageComplete());
		
		if (rc!=null){
			fila.put("ID_Recurso", rc.getResource()!=null ? rc.getResource().getUniqueID(): null);
			fila.put("F_Inicio", new FechaGotta(rc.getStart()));
			fila.put("F_Fin", new FechaGotta(rc.getFinish()));	
			}
		}
//////////////////////////////
	private void genera_recursos(Filas filas){
		for (Fila fila:filas){
			Resource r=this.project.addResource();
			
			r.setName( fila.gets("nombre"));
			r.setID( Integer.parseInt( fila.gets("posicionRecurso") ) );
			r.setUniqueID( Integer.parseInt( fila.gets("id_Recurso") ) );
			
			r.setInitials(fila.gets("iniciales"));
			if (fila.gets("email")!=null && !fila.gets("email").equals(Constantes.CAD_VACIA)) 
				r.setEmailAddress(fila.gets("email"));
			
			if (fila.gets("tasa")!=null && !fila.gets("tasa").equals(Constantes.CAD_VACIA)){
				Double t=Double.parseDouble(fila.gets("tasa"));
				if (t!=0)
					r.setStandardRate(new Rate(t, TimeUnit.HOURS));
				}
			
			}
		}
////////////////////////////
	private void genera_tareas(Filas filas){
		Coleccion<Task> tareas= new Coleccion<Task>();
		
		for (Fila fila:filas){
			Task t;
			int id_tarea=Integer.parseInt( fila.gets("id_tarea"));
			
			if (this.project.getTaskByID(id_tarea)==null){
				t=this.project.addTask();
				
				t.setName( fila.gets("ds_tarea"));
				t.setID( Integer.parseInt( fila.gets("posicionTarea") ) );
				t.setUniqueID( id_tarea );
				
				FechaGotta f_ini, f_fin;
				
				if (fila.get("f_inicio")!=null){
					f_ini=(FechaGotta)fila.get("f_inicio");
					t.setStart(f_ini.getTime()); 
					}
				if (fila.get("f_fin")!=null){
					f_fin=(FechaGotta)fila.get("f_fin");
					t.setFinish(f_fin.getTime()); 
					}
				
				tareas.put("x"+fila.gets("id_tarea"), t);
				}
			else
				t=this.project.getTaskByID(id_tarea);
			
			añade_tareasHijas(tareas, fila, t);
			añade_tareasPredecesoras(tareas, fila, t);
			añade_rc(fila, t);
			
			}
		}
	private void añade_tareasHijas(Coleccion<Task> tareas, Fila fila, Task hija){
		if (fila.gets("id_tareapadre")==null || fila.gets("id_tareapadre").equals(Constantes.CAD_VACIA))
			return;
		
		ArrayList<String> listaPadres=Util.split(fila.gets("id_tareapadre"), ";");
		for (int i=0; i<listaPadres.size(); i++){
			String id_padre=listaPadres.get(i);
			
			tareas.get("x"+id_padre).addChildTask(hija);
			}
		}
	private void añade_tareasPredecesoras(Coleccion<Task> tareas, Fila fila, Task hija){
		if (fila.gets("id_tareaspredecesoras")==null || fila.gets("id_tareaspredecesoras").equals(Constantes.CAD_VACIA))
			return;
		
		ArrayList<String> listaPadres=Util.split(fila.gets("id_tareaspredecesoras").trim(), ";");
		for (int i=0; i<listaPadres.size(); i++){
			String id_padre=listaPadres.get(i);
			
			hija.addPredecessor(tareas.get("x"+id_padre), RelationType.FINISH_START, Duration.getInstance(0, TimeUnit.HOURS));
			}
		}
	private void añade_rc(Fila fila, Task tarea){
		ResourceAssignment rc=this.project.newResourceAssignment(tarea);
		FechaGotta f_ini, f_fin;
		
		if (fila.get("f_inicio")!=null){
			f_ini=(FechaGotta)fila.get("f_inicio");
			rc.setStart(f_ini.getTime()); 
			}
		if (fila.get("f_fin")!=null){
			f_fin=(FechaGotta)fila.get("f_fin");
			rc.setFinish(f_fin.getTime()); 
			}
		
		if (fila.get("id_recurso")!=null){
			rc.setResourceUniqueID((Integer)fila.get("id_recurso"));
			tarea.addResourceAssignment(rc);	
			}
		}
}
