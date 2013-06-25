package es.burke.gotta.dll;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.URLDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import es.burke.gotta.Constantes;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.FechaGotta;

/**
 * Esta clase facilita la utilizacion de javamail. Esta compuesta
 * por metodos set para rellenar los datos tipicos de un mensaje
 * de correo electronico. Y por ultimo un metodo enviar para mandar
 * los datos a traves de la red, encapsulados en un mensaje 
 * de correo electronico.
 */
final class AutentificacionGottaMail extends Authenticator {
	String user=null;
	String password = null;
	public AutentificacionGottaMail(String u, String p) {
		this.user=u;
		this.password=p;
	}
	@Override
	public PasswordAuthentication getPasswordAuthentication(){
		return new PasswordAuthentication(this.user,(this.password));  }
}

enum TipoCorreo{
	TextoPlano,
	HTML
	}

public class GottaMail implements Serializable{
	private static final long serialVersionUID = -7831840916961739185L;
	private String host;
    private String user;
    private String password;
    private int port=25;
    private String from;
    private String subject;
    private String text;
    private ArrayList<InternetAddress> to;
    private ArrayList<InternetAddress> cc;
    private ArrayList<InternetAddress> bcc;
    private ArrayList<DataSource> attachment;
    public String emailConfirmacion;
    public TipoCorreo tipoCorreo;
    
	public GottaMail(String host, String port, String u, String p) {
		this.user=u;
		this.password=p;
		this.host=host;
		
		if (port!=null)
			this.port=Integer.parseInt(port);
		
		this.from=null;
		this.emailConfirmacion=null;
		this.subject="";
		this.text="";
		this.to=new ArrayList<InternetAddress>();
		this.cc=new ArrayList<InternetAddress>();
		this.bcc=new ArrayList<InternetAddress>();
		this.attachment=new ArrayList<DataSource>();
		this.tipoCorreo=TipoCorreo.TextoPlano;
	}
    public void setFrom(String de) {
        this.from=de;
   		}
    public void setSubject(String asunto) {
        this.subject=asunto;
    	}
    public void setText(String texto) {
        this.text=texto;
    	}
    public void addTo(String v) throws AddressException {
        to.add(new InternetAddress(v));
    	}
    
    public void addCC(String v) throws AddressException {
        cc.add(new InternetAddress(v));
    	}
    public void addBCC(String v) throws AddressException {
        bcc.add(new InternetAddress(v));
    	}
    public void setAttachment(DataSource adjunto){
        attachment.add(adjunto);
    	}
    
    public void enviar() throws ErrorAccionDLL  {
    	try {
	    	//Coger propiedades del sistema y configurar el servidor mail
	        Properties props = System.getProperties();
	        props.put("mail.smtp.host", host);
	        props.put("mail.smtp.port", port);
	        
	    	Session session = null;
	    	if (this.user==null)
	    		session = Session.getInstance(props, null);
	    	else {
				props.put("mail.smtp.auth", "true");
	    		Authenticator auth = new AutentificacionGottaMail(this.user,this.password);
	    		session = Session.getInstance(props, auth);
	    		}
	    	
	    	//Configurar el mensaje        
	        MimeMessage msg = new MimeMessage(session);
	        
	        if (from != null)
	            msg.setFrom(new InternetAddress(from));
	        else
				throw new ErrorAccionDLL ("Debe indicar un remitente");
	
	        
	        if (to.isEmpty()) 
	        	throw new ErrorAccionDLL("Debe indicar un destinatario");
	        
	        Address[] x;
	        x=new Address[0];
            msg.setRecipients(Message.RecipientType.TO, to.toArray(x));
	        x=new Address[0];
	        msg.setRecipients(Message.RecipientType.CC, cc.toArray(x));
	        x=new Address[0];
	        msg.setRecipients(Message.RecipientType.BCC, bcc.toArray(x));
	
            if (subject!=null)
				msg.setSubject(subject);
	        
			if (attachment.isEmpty()) {
	            if (this.tipoCorreo.equals(TipoCorreo.HTML))
		            msg.setText(text, Constantes.UTF8,"html");
	            else
	            	msg.setText(text, Constantes.UTF8,"plain");
        		}
	        else {
				BodyPart mbp = new MimeBodyPart();
				mbp.setText(text);
				if (this.tipoCorreo.equals(TipoCorreo.HTML))
					mbp.setContent(text,"text/html; charset=UTF-8");
				Multipart multipart = new MimeMultipart();
				multipart.addBodyPart(mbp);
				msg.setContent(multipart);
				for(DataSource source:attachment){
					mbp = new MimeBodyPart();
					if(source instanceof URLDataSource){
						String path = ((URLDataSource)source).getURL().getPath();
						mbp.setFileName(path.substring(path.lastIndexOf('/')+1));
						}
					else{
						mbp.setFileName(source.getName());
						}
					mbp.setDataHandler(new DataHandler(source));
					
					multipart.addBodyPart(mbp);
					}
	        	}
			if (this.emailConfirmacion!=null)
				msg.setHeader("Disposition-Notification-To",this.emailConfirmacion);
			
			msg.setSentDate( new FechaGotta().getTime() );
			if (this.user==null)
				Transport.send(msg);	
			else {
				Transport transport = session.getTransport("smtp");
				transport.connect(host, this.user, this.password);
				msg.saveChanges();
				transport.sendMessage(msg, msg.getAllRecipients());
				transport.close();
				}
    	}
    	catch (AddressException e) {
    		throw new ErrorAccionDLL("Error en las direcciones del envío: ", e);
    		}
        catch (MessagingException e) {
    		throw new ErrorAccionDLL("Error en el envío: ", e);
    		}
    	}
    
}