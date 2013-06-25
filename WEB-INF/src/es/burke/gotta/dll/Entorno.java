package es.burke.gotta.dll;
import java.io.IOException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Variable;

public class Entorno extends DLLGotta {
	@Override
	public String accionesValidas() {
		return "entorno lee escribe aplicacion";
		}
	
	@Override
	public Object ejecuta(Accion accion, Object valor) throws ErrorGotta {
		String xvalor=(valor+"");
		if (accion.accion.equalsIgnoreCase("entorno"))
			return "java";
		if (accion.accion.equalsIgnoreCase("aplicacion"))
			return accion.motor.getApli().getCd();
		
		if(accion.accion.equalsIgnoreCase("lee")){
			if (xvalor==null || xvalor.equals(Constantes.CAD_VACIA))
				throw new ErrorAccionDLL("Se ha producido un error al hacer DLL.Entorno.lee: no se ha indicado la variable de la que leer. Puede que falten las comillas en la expresión del parámetro 3");
			Variable ret=mMotor.usuario.getVariable(xvalor);
			if (ret==null)
				return null;
			return ret.getValor();
			}
		if(accion.accion.equalsIgnoreCase("escribe")){
			int espacio=xvalor.indexOf(Constantes.ESPACIO);
			String nombre=xvalor.substring(0, espacio);
			
			String nuevoValor=xvalor.substring(espacio+1);
			return mMotor.usuario.setVariable(nombre,nuevoValor);
			}
		throw new ErrorAccionDLL("Debería saltar en verifica");
		}

	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req,
			HttpServletResponse res, HttpSession sesion, String numprod)
			throws IOException, ErrorGotta {
		// pass

	}

	@Override
	public void verifica(Motor motor, String accion, String nombre) throws ErrorGotta {
		verificaAccionValida(accion);
		this.mMotor=motor;
		this.usr=motor.usuario;
		}
	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}
