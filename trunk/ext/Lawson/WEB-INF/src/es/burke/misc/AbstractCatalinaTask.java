package es.burke.misc;
import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.tools.ant.BuildException;
import org.apache.tools.ant.Task;

public abstract class AbstractCatalinaTask extends Task implements Serializable {

	private static final long serialVersionUID = -2977997923461447765L;
	private boolean usuarioValido;

	public boolean isUsuarioValido() {
		return this.usuarioValido;
		}
	
    public AbstractCatalinaTask() {
        password = null;
        url = "";
        username = null;
        usuarioValido=false;
    	}
   
    public void setPassword(String password) {
        this.password = password;
    	}
    public void setUrl(String url) {
        this.url = url;
    	}
    public void setUsername(String username) {
        this.username = username;
    	}
    @Override
	public void execute() throws BuildException {
        if(username == null || password == null || url == null)
            throw new BuildException("Debes especificar 'username', 'password', y 'url'");
		return;
    	}
    public String execute(String command) throws BuildException{
        return execute(command, null, null, -1);
   		}

	public String execute(String command, InputStream istream, String contentType, int contentLength)
        throws BuildException {
        String Salida="";
        java.net.URLConnection conn = null;
        InputStreamReader reader = null;
        HttpURLConnection hconn=null; 
        try {
            conn = (new URL(url + command)).openConnection();
            hconn = (HttpURLConnection)conn;
            hconn.setAllowUserInteraction(false);
            hconn.setDoInput(true);
            hconn.setUseCaches(false);
            if(istream != null) {
                hconn.setDoOutput(true);
                hconn.setRequestMethod("PUT");
                if(contentType != null)
                    hconn.setRequestProperty("Content-Type", contentType);
                if(contentLength >= 0)
                    hconn.setRequestProperty("Content-Length", "" + contentLength);
            	} 
            else {
                hconn.setDoOutput(false);
                hconn.setRequestMethod("GET");
            	}
            hconn.setRequestProperty("User-Agent", "Catalina-Ant-Task/1.0");
            String input = username + ":" + password;
            String output = new String(Base64.encode(input.getBytes()));
            hconn.setRequestProperty("Authorization", "Basic " + output);
            
            hconn.connect();
            if(istream != null) {
                BufferedOutputStream ostream = new BufferedOutputStream(hconn.getOutputStream(), 1024);
                byte buffer[] = new byte[1024];
                do {
                    int n = istream.read(buffer);
                    if(n < 0)
                        break;
                    ostream.write(buffer, 0, n);
                	} while(true);
                ostream.flush();
                ostream.close();
                istream.close();
            }
            reader = new InputStreamReader(hconn.getInputStream());
            this.usuarioValido=true;
            StringBuffer buff = new StringBuffer();
            String error = null;
            boolean first = true;
           
            do {
                int ch = reader.read();
                if(ch < 0)
                    break;
                if(ch == 13 || ch == 10) {
                    String line = buff.toString();
                    buff.setLength(0);
                    if(first) {
                        if(!line.startsWith("OK -"))
                            error = line;
                        first = false;
                    	}    
                	}
                else {
                    buff.append((char)ch);
                	Salida+=(char)ch; 
                	}
            	} 
            while(true);
            
            if(buff.length() > 0) log(buff.toString(), 2);
            if (error != null) throw new BuildException(error);
            hconn.disconnect();
        	}
        catch(Throwable t) {
        	if(hconn!=null)
        		hconn.disconnect();
            throw new BuildException(t);
        	}
        finally {
        	cierraTodo(reader, istream);
        	if(hconn!=null)
        		hconn.disconnect();
        	}
        return Salida;
    	}
    
    private void cierraTodo(InputStreamReader reader, InputStream istream){
    	 try {
             if (reader!=null) reader.close();
         	}
         catch(Throwable u) {
         	/*pass*/ }
         
         try {
          	 if(istream != null) istream.close();
         	}
         catch(Throwable u) {
             	/*pass*/}
    	}
    protected String password;
    protected String url;
    protected String username;
}
