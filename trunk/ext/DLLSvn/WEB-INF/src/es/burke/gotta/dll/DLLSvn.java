// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   DLLSvn.java

package es.burke.gotta.dll;

import com.burke.SubversionAccess.*;
import es.burke.gotta.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.http.*;

// Referenced classes of package es.burke.gotta.dll:
//            DLLGotta, ErrorGenerandoProducto

public class DLLSvn extends DLLGotta
{

    public DLLSvn()
    {
    }

    public String accionesValidas()
    {
        return "descargar subir borrar existe";
    }

    public Object ejecuta(Accion aaccion, Object ovalor)
        throws ErrorGotta
    {
        String valor = ovalor.toString();
        String accion = aaccion.accion;
        ArrayList arg = Util.split(valor, "|");
        Object resp = new Object();
        if(accion.equals("descargar"))
        {
            DownloadDocument clase = new DownloadDocument();
            resp = clase.download((String)arg.get(0), (String)arg.get(1), (String)arg.get(2), (String)arg.get(3), (String)arg.get(4));
        } else
        if(accion.equals("subir"))
        {
            UploadDocument clase = new UploadDocument();
            resp = clase.upload((String)arg.get(0), (String)arg.get(1), (String)arg.get(2), (String)arg.get(3), (String)arg.get(4), (String)arg.get(5));
        }
        if(accion.equals("borrar"))
        {
            RemoveDocument clase = new RemoveDocument();
            resp = clase.remove((String)arg.get(0), (String)arg.get(1), (String)arg.get(2), (String)arg.get(3), (String)arg.get(4));
        }
        if(accion.equals("existe"))
        {
            CheckDocument clase = new CheckDocument();
            resp = clase.check((String)arg.get(0), (String)arg.get(1), (String)arg.get(2));
        }
        return resp;
    }

    public void sacaProducto(HttpServlet httpservlet, HttpServletRequest httpservletrequest, HttpServletResponse httpservletresponse, HttpSession httpsession, String s)
        throws IOException, ErrorGenerandoProducto
    {
    }

    public void verifica(Motor motor, String accion, String nombre)
        throws ErrorGotta
    {
        verificaAccionValida(accion);
        mMotor = motor;
        usr = motor.usuario;
    }
    	@Override
	public Object revivir(Accion accion, Object valor) throws ErrorGotta {
		return null;
	}
}
