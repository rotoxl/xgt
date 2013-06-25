package es.burke.gotta;

import java.awt.Color;
import java.awt.Font;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.block.BlockBorder;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PiePlot3D;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.AbstractRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.category.IntervalCategoryDataset;
import org.jfree.data.gantt.Task;
import org.jfree.data.gantt.TaskSeries;
import org.jfree.data.gantt.TaskSeriesCollection;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.time.SimpleTimePeriod;
import org.jfree.util.Rotation;

import es.burke.gotta.Constantes.TipoMensajeGotta;

public class ServletGraficas extends HttpServlet {
	/* LAS INSTRUCCIONES PA MONTAR ESTO...
	 * esta primera que es un foro y hay baswtantes cosas interesantes
		http://www.jfree.org/phpBB2/index.php?sid=c79aa302569855a2e7e69dc24dff0188
	 *	Y esta otra donde estan los fuentes de los ejemplos de la pagina oficial
			http://www.koders.com/java/fid7EBF46B5E069056760DCA9D01F7439EC0299C349.aspx
	 * */
	private static final long serialVersionUID = 187975353103607054L;
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding( Constantes.CODIF );
		Jsp.setHeaders(res, "image/png"); //no-cache y demás
		HttpSession sesion=req.getSession(false);
		if (sesion!=null) {
			Usuario usr=null;
			try {
				usr = Usuario.desdeSesion(req);
				String numControl = req.getParameter("numControl");
				
				int ancho=600; int alto=600;
				ControlDef lvw=null;
				try {
					lvw=usr.getApli().getControlDef(numControl);
					ancho=lvw.ancho/15;
					alto=lvw.alto/15;
					}
				catch (NullPointerException e) {
					//Ocurre cuando es un gráfica que aún no hemos guardado en BD
					}
						
				ServletOutputStream out = res.getOutputStream();
				
				String nombreNivel=Util.obtenValor(req, "nivel");
				String titulo=Util.obtenValor(req, "titulo");
				String tipo=req.getParameter("tipo");
				String leyenda=req.getParameter("leyenda");
				String restoInfo=req.getParameter("restoInfo");
				
				INivelDef nivelDef = usr.getApli().getNivelDef(nombreNivel);
				INivel nivel=nivelDef.obtenerNivel();
				
				Filas filas=nivel.lookUp(usr.getConexion(), usr, usr.arbol.getNodoActivo(), -1);
					
				Graficas gra =new Graficas(usr, titulo, tipo, leyenda, ancho, alto, restoInfo);  
				gra.crearGrafica(out,filas);				
				} 
			catch (ErrorGotta e) 
				{if(usr!=null) usr.sacaError(e);} 
			
			} 
		else 
			Util.sendRedirect(req, res, ".",Constantes.SesionCaducada);
		}
}
class Graficas {
	String titulo;
	String tipo;
	boolean leyenda;
	int alto;
	int ancho;
	
	private final String GraficaBarras3d="0"; 	//$NON-NLS-1$
	private final String GraficaBarras2d="1"; 	//$NON-NLS-1$
	private final String GraficaBarras2d_a="2"; 	//$NON-NLS-1$
	private final String GraficaBarras2d_b="8"; 	//$NON-NLS-1$
	
	private final String GraficaLineas2d="3"; 	//$NON-NLS-1$
	private final String GraficaArea3d="5"; 	//$NON-NLS-1$
	private final String GraficaArea2d="4";		//$NON-NLS-1$
	private final String GraficaPastel2d="13"; 	//$NON-NLS-1$
	private final String GraficaPastel3d="14"; 	//$NON-NLS-1$
	private final String GraficaPastel3d_a="15"; 	//$NON-NLS-1$
	
	private final String GraficaGantt="gantt"; 	//$NON-NLS-1$
	private String restoInfo;
	private Usuario usr;
	
	public Graficas(Usuario usr, String titulo, String tipo, String leyenda, int ancho , int alto, String restoInfo){
		this.usr=usr;
		this.titulo = titulo;
		this.tipo = tipo;
		
		this.leyenda = leyenda.equals("1");
		
		this.ancho = ancho;
		this.alto = alto;
		
		this.restoInfo=restoInfo;
		}	
	private CategoryDataset sacaDatos(Filas d) {
		DefaultCategoryDataset datos = new DefaultCategoryDataset();
		float valor;
		String serie;
		String categoria;
	
		int idx=sacaUltimoNoOculto(d);
		for (Fila reg : d) {
			String xv=reg.get(idx).toString();
			try {
				valor = Float.parseFloat(xv);
				} 
			catch (NumberFormatException e) {
				this.usr.añadeMSG("Error al generar la gráfica: se esperaba un valor numérico en la última columna (viene '"+xv+"')", TipoMensajeGotta.alerta);
				throw e;
				}
			serie=reg.gets(idx-1);
			if (reg.size()>2)
				categoria=reg.gets(0);
			else
				categoria=titulo;
			
			datos.addValue(valor, serie, categoria);	
			}
		return datos;
		}
	private static int sacaUltimoNoOculto(Filas filas){
		for (int i=filas.getOrden().size()-1; i>0; i--){
			String cd=filas.getOrden().get(i);
			if (!Util.columnaInvisible(cd))
				return i;
			}
		return -1;
	}
	private PieDataset sacaDatosPastel(Filas d) {    
    	float valor;
    	String serie;
    	DefaultPieDataset cds=new DefaultPieDataset();
    	//Filas d=this.con.lookUpNivel(this.nivel, valores, false);
		for (int i=0; i<d.size(); i++) {
			Fila reg = d.get(i);
			valor = Float.parseFloat(reg.get(reg.size()-1).toString());
			serie=reg.get(0).toString();
			cds.setValue(serie, valor);
			}
    	return cds;
    }
    private IntervalCategoryDataset sacaDatosGantt(Filas d) {
    	TaskSeries s1=null; 
    	
    	TaskSeriesCollection colProyectos = new TaskSeriesCollection() ;
    	Coleccion<TaskSeries> proyectosExistentes=new Coleccion<TaskSeries>();
    	Coleccion<Task> tareasDelProyecto=new Coleccion<Task>();
    	
    	for (Fila fila : d) {
    		String proyecto=fila.gets("serie");
    		String tarea=fila.gets("tarea");
    		
    		if (!proyectosExistentes.containsKey(proyecto)) {
    			s1= new TaskSeries(proyecto);
    			colProyectos.add(s1);
    			proyectosExistentes.put(proyecto, s1);
    			}
    		else
    			s1=proyectosExistentes.get(proyecto);
    		
    		java.util.Date f1=((FechaGotta)fila.get("f1")).getTime();
    		java.util.Date f2=((FechaGotta)fila.get("f2")).getTime();
	        
    		Task t=null;
    		String idTareaProyecto=tarea+"+"+proyecto;
    		if (tareasDelProyecto.containsKey(idTareaProyecto)) {
    			t=tareasDelProyecto.get(idTareaProyecto);
    			Task subt=new Task(tarea, new SimpleTimePeriod(f1, f2) );
    			t.addSubtask(subt);
    			}
    		else {
    			t=new Task(tarea, new SimpleTimePeriod(f1, f2) );
    			tareasDelProyecto.put(idTareaProyecto, t);
    			s1.add(t);
    			}
	        
	        if (fila.containsKey("porcentaje") && fila.get("porcentaje")!=null) {
	        	BigDecimal porcentaje=new BigDecimal(fila.get("porcentaje")+"");
	        	t.setPercentComplete(porcentaje.doubleValue()/100);
	        	}
	    	}
        return colProyectos;   	
    	}	

    private void ponColoresTituloLeyenda(JFreeChart chart) {
    	chart.setBackgroundPaint(Color.white); //color de fondo
	    if (chart.getLegend()!=null) {
	    	chart.getLegend().setItemPaint(Color.gray); // color letra de la leyenda
	    	chart.getLegend().setFrame( new BlockBorder(Color.white));
	    	chart.getLegend().setItemFont(new Font("Tahoma", Font.PLAIN, 10));
	    	}
	    chart.setBorderPaint(Color.gray);
	    if (chart.getTitle()!=null)   chart.getTitle().setPaint(Color.gray);
    	}
    
    private void ponColoresSeries(AbstractRenderer r){
    	try {
			//this.restoInfo="#66ff33,#66cc00"
			ArrayList<String>lista=Util.split(this.restoInfo, Constantes.COMA); 
			for (int i=0; i< lista.size(); i++)
				r.setSeriesPaint(i, Color.decode("#"+ lista.get(i).trim() ));
			}
    	catch (RuntimeException e) {
			//pues nos quedamos sin series de colores personalizados
			}
    	}
    private void ponColoresSeries(PiePlot r){
    	try {
			//this.restoInfo="#66ff33,#66cc00"
			ArrayList<String>lista=Util.split(this.restoInfo, Constantes.COMA); 
			for (Integer i=0; i< lista.size(); i++)
				r.setSectionPaint(i, Color.decode("#"+ lista.get(i).trim() ));
			}
    	catch (RuntimeException e) {
			//pues nos quedamos sin series de colores personalizados
			}
    	}
    private CategoryPlot montaPlot(JFreeChart chart) {
	    CategoryPlot plot=chart.getCategoryPlot();
	    ponColoresTituloLeyenda(chart);
	    plot.setForegroundAlpha(0.75f);
	        
	    try {
			AbstractRenderer r = (AbstractRenderer) plot.getRenderer();	
			ponColoresSeries(r);
				
			CategoryAxis domainAxis = plot.getDomainAxis();
	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions.createUpRotationLabelPositions(Math.PI/6.0));
		    }
	    catch (Exception e) 
	    	{/*nos quedamos sin borde bonito*/}
	    
	    return plot; 
	    }
    private void tocaPiePlot(JFreeChart chart, PiePlot plot) {    
    	plot.setBaseSectionOutlinePaint(Color.white);
        ponColoresTituloLeyenda(chart);
        plot.setForegroundAlpha(0.75f);
        
        ponColoresSeries(plot);
        plot.setStartAngle(290);
        plot.setDirection(Rotation.CLOCKWISE);
    }
    void crearGrafica(OutputStream salida, Filas filas) throws ErrorGenerandoGrafica {	
		JFreeChart chart=null;
        
        String x="";
        String y="";
        
        if (tipo.equals(GraficaBarras2d_a) || tipo.equals(GraficaBarras2d_b))
        	tipo=GraficaBarras2d;
        else if (tipo.equals(GraficaPastel3d_a))
        	tipo=GraficaPastel3d;
        
		if (tipo.equals(GraficaBarras3d) ) {
            CategoryDataset cds=sacaDatos(filas);
			chart = ChartFactory.createBarChart3D(this.titulo, x, y, cds, PlotOrientation.VERTICAL, this.leyenda, true, true);
			montaPlot(chart);
			}
		else if (tipo.equals(GraficaBarras2d)) {
			CategoryDataset cds=sacaDatos(filas);
			chart = ChartFactory.createBarChart(this.titulo, x, y, cds, PlotOrientation.VERTICAL,this.leyenda,true,true);
			montaPlot(chart);
			}
		else if (tipo.equals(GraficaLineas2d)) {//línea 2d
			CategoryDataset cds=sacaDatos(filas);
			chart = ChartFactory.createLineChart(this.titulo, x, y, cds,PlotOrientation.VERTICAL,this.leyenda,true,true);
			montaPlot(chart);
//			CategoryPlot plot=montaPlot(chart);
			
//			LineAndShapeRenderer renderer=(LineAndShapeRenderer)plot.getRenderer();
//            renderer.setShapesFilled(true);
//            renderer.setShapesVisible(true);
			}
		else if (tipo.equals(GraficaArea3d)) {//Area3d
			CategoryDataset cds=sacaDatos(filas);
			chart = ChartFactory.createStackedAreaChart(this.titulo, x, y, cds, PlotOrientation.VERTICAL,this.leyenda,true, true /* Show legend */ );
			montaPlot(chart);
			}
		else if (tipo.equals(GraficaArea2d)){//Area2d
			CategoryDataset cds=sacaDatos(filas);
			chart = ChartFactory.createAreaChart(this.titulo, x, y, cds, PlotOrientation.VERTICAL,this.leyenda,true, true /* Show legend */ );
			montaPlot(chart) ;
			}
		else if (tipo.equals(GraficaPastel2d)){ //pastel 2d
			PieDataset cds = sacaDatosPastel(filas);
			chart = ChartFactory.createPieChart(this.titulo, cds,this.leyenda,true, true);
			PiePlot plot=(PiePlot)chart.getPlot();	
			
			tocaPiePlot(chart, plot);
			}
		else if (tipo.equals(GraficaPastel3d) ) {//pastel 3d
			PieDataset cds=sacaDatosPastel(filas);
			chart = ChartFactory.createPieChart3D(this.titulo, cds, this.leyenda,true,true );
			PiePlot3D plot=(PiePlot3D)chart.getPlot();
			
			tocaPiePlot(chart, plot);  
			}
		else if (tipo.equalsIgnoreCase(GraficaGantt)) {
			IntervalCategoryDataset cds=sacaDatosGantt(filas);
			chart = ChartFactory.createGanttChart(
		            this.titulo,  
		            null,              // domain axis label
		            null	,              // range axis label
		            cds,                 // data
		            this.leyenda,        // include legend
		            true,                // tooltips
		            true                // urls
		        	);    
			montaPlot(chart);
			}
		
		try 
	    	{ChartUtilities.writeChartAsPNG(salida, chart, this.ancho, this.alto);} 
	    catch (Throwable e) 
	    	{throw new ErrorGenerandoGrafica(e.getMessage());}	 	 
	}
}
