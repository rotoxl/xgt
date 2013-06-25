package es.burke.gotta;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.dll.DLLGotta;
import es.burke.gotta.dll.ErrorGenerandoProducto;

public class ServletGenInf extends HttpServlet {
	private static final long serialVersionUID = -8850076125590940952L;

	@Override
	public void doPost(HttpServletRequest req, HttpServletResponse res)	throws ServletException, IOException {
		req.setCharacterEncoding( Constantes.CODIF );
		HttpSession sesion = req.getSession(false);
		String numprod=req.getParameter("contadoc");
		
		try  {
			crearDocumento(this, req, res, sesion, numprod);} 
		catch (ErrorGotta e) {
			sesion.setAttribute("mensaje", e);
			Util.sendRedirect(req,res,"errorInforme.jsp");
			}
	
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws IOException, ServletException
		{doPost(req,res);}
	
	private void crearDocumento(ServletGenInf inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod) throws ErrorGotta, ServletException {
		Usuario usr = Usuario.desdeSesion(req);
		try
			{
			DLLGotta inf = usr.productosEnviados.get(numprod);
			inf.sacaProducto(inf2, req, res, sesion, numprod);
			//usr.productosEnviados.remove(numprod);
			}
		catch (IOException e){
			throw new ErrorGenerandoProducto("Error generando el informe:" + e.getMessage());
			}
		catch (ErrorGotta e){
			throw new ErrorGenerandoProducto("Error generando el informe:" + e.getMessage());
			}
		
	}	
}
