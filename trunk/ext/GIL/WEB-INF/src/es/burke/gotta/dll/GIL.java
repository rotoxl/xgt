package es.burke.gotta.dll;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Transparency;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import es.burke.gotta.Archivo;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Usuario;
import es.burke.gotta.Util;


// Tratamiento de imágenes
public class GIL {
	final static String jpg="jpg";//$NON-NLS-1$
	final static String png="png";//$NON-NLS-1$

	public final static String[] formatoIMG={
		jpg, png
		};
	private final Usuario usr;
	
	public GIL(Usuario usr){
		this.usr = usr;
		}
	
	GraphicsConfiguration conf = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDefaultConfiguration();
	
	public BufferedImage leerImagen(String ruta) throws ErrorGotta{
		Archivo ar = new Archivo(this.usr, ruta);
		
		//Carga la imagen
		Image ima=Toolkit.getDefaultToolkit().getImage(ar.rutaFisica);
		ima = new ImageIcon(ima).getImage(); //Para poder utilizar el getWidth y getHeight y asegurarse la carga de la imagen
		
		//Creación BufferedImage con la imagen
		BufferedImage bi =conf.createCompatibleImage(ima.getWidth(null), ima.getHeight(null), Transparency.OPAQUE);
		bi.getGraphics().drawImage(ima, 0, 0, null);
		
		return bi;
		}
	public File guardarImagen(BufferedImage bi, String ruta) throws IOException{
		ArrayList<String> img=separaRutayFormato(ruta);
		
		// Guardar la imagen final en la carpeta destino
		File fic=new File( img.get(0)+"."+img.get(1));
		ImageIO.write(bi, img.get(1), fic);
		return fic;
		}
	public ArrayList<String> separaRutayFormato(String ruta){
		String formato=null;
		
		for (String extension:formatoIMG){
			if (ruta.endsWith(extension)){
				formato=extension;
				ruta=ruta.substring(0, ruta.length()-extension.length());
				}
			}
		if (formato==null)
			formato=png;
		
		return Util.creaListaString(ruta, formato);
		}
	
	public File convertirImagen(String ruta, String nuevaExtension, boolean borrarAntigua) throws ErrorGotta, IOException{
		ArrayList<String> img=separaRutayFormato(ruta);
		BufferedImage bi=leerImagen(ruta);
		
		File bo=guardarImagen(bi, img.get(0)+"."+nuevaExtension);
		
		if (borrarAntigua)
			Archivo.borraArchivo(this.usr.getApli(), ruta);
		
		return bo;
		}
////////////////////////	
	public BufferedImage escalaDeGrises(BufferedImage bi){
		//Crea una copia del mismo tamaño que la imagen
		BufferedImage biDestino =conf.createCompatibleImage(bi.getWidth(), bi.getHeight(), Transparency.OPAQUE);
		//Recorre las píxeles y obtiene el color de la imagen original y la almacena en el destino
		for (int x=0;x < bi.getWidth();x++){
			for (int y=0;y < bi.getHeight();y++){
				//Obtiene el color
				Color c1=new Color(bi.getRGB(x, y));
				//Calcula la media de tonalidades
				int med=(c1.getRed()+c1.getGreen()+c1.getBlue())/3;
				//Almacena el color en la imagen destino
				biDestino.setRGB(x, y, new Color(med,med,med).getRGB());
				}
			}
		return biDestino;
		}
	public BufferedImage recortar(BufferedImage bi, int x, int y, int ancho, int alto){
		return bi.getSubimage(x, y, ancho, alto);
		}
	public BufferedImage cambiarTamanho(BufferedImage bi, int ancho, int alto){
		BufferedImage bdest =new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_RGB);
		Graphics2D g = bdest.createGraphics();
		AffineTransform at =AffineTransform.getScaleInstance((double)ancho/bi.getWidth(),
		    		  										(double)alto/bi.getHeight());
		g.drawRenderedImage(bi, at);
		return bdest;
		}
	
	public ArrayList<Integer> calculaAnchoAltoProporcional(BufferedImage bi, Object ancho, Object alto){
		double proporcion=bi.getWidth()/bi.getHeight();
		
		if (ancho==null)
			ancho=proporcion*(Integer)alto;
		else {
			alto=(Integer)ancho/proporcion;
			alto=((Double)alto).intValue();
			}
		ArrayList<Integer> ret= new ArrayList<Integer>();
		ret.add((Integer)ancho); ret.add((Integer)alto); 
		return ret;
		}
}

