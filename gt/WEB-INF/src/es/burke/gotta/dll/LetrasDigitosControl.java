package es.burke.gotta.dll;
import es.burke.gotta.Util;
import java.io.IOException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import es.burke.gotta.Accion;
import es.burke.gotta.ErrorAccionDLL;
import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Motor;

public class LetrasDigitosControl extends DLLGotta {

	@Override
	public String accionesValidas() {
		return "NIF DC chequeaCIF chequeaNIF chequeaNIE";
	}
	@Override
	public Object ejecuta(Accion aaccion, Object valor) throws ErrorGotta {
		String accion=aaccion.accion;
		String v=valor.toString();
		
		if(accion.equalsIgnoreCase("NIF"))
			return _letraNif(v);
		else if (accion.equalsIgnoreCase("DC"))
			return sacaDC(v);
		else if(accion.equalsIgnoreCase("chequeaCIF"))
			 return chequeaCIF(v);
		else if (accion.equalsIgnoreCase("chequeaNIF") || accion.equalsIgnoreCase("chequeaNIE"))
			return chequeaNIF_NIE(v);		
		throw new ErrorAccionDLL("Debería saltar en verifica");
		}
	@Override
	public void sacaProducto(HttpServlet inf2, HttpServletRequest req, HttpServletResponse res, HttpSession sesion, String numprod)	throws IOException, ErrorGotta {
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
//////////////
	private String sacaDC(String valor) throws ErrorGotta{
		String v = valor;
		String es=v.substring(0, 8);
		String cu=v.substring(8);
		cu=Util.completaPorLaIzquierda(cu,"0",10);
		String dc="";
		try{
			int [] valores = {4,8,5,10,9,7,3,6};
			int [] valoresCu = {1,2,4,8,5,10,9,7,3,6};
			int control = 0;
			for (int i=0; i<=7; i++)
				control += Character.getNumericValue(es.charAt(i)) * valores[i];
			control = 11 - (control % 11);
			if (control == 11) control = 0;
			else if (control == 10) control = 1;
			dc=new Integer(control).toString();
		
			if(!cu.equals("")){
				int controlCu = 0;
				for (int i=0; i<=9; i++)
					controlCu += Character.getNumericValue(cu.charAt(i)) * valoresCu[i];
				controlCu = 11 - (controlCu % 11);
				if (controlCu == 11) controlCu = 0;
				else if (controlCu == 10) controlCu = 1;
				dc=dc+new Integer(controlCu).toString();
				}
			return dc;
			}
		catch(Exception e){
			throw new ErrorGotta("Error en DLL.sCadenaDc: "+valor);
			}
		}
	private String chequeaCIF(String valor){
		//Incluye validaciones según la ORDEN EHA/451/2008 del BOE, en la que se incluyeron nuevas letras válidas y otras consideraciones en el cálculo del CIF.
		//http://www.aeat.es/AEAT/Contenidos_Comunes/La_Agencia_Tributaria/Segmentos_Usuarios/Empresas_y_profesionales/NIF/NIFEntidades99.pdf
		//Ejemplo CIF J64746043
		try  {
			 String cif = valor;
			 String primletra=cif.substring(0,1);
		 	 String letrasiniciales="ABCDEFGHKLMNPQS"+"JUVWR";
			 String ultimaletra=cif.substring(cif.length()-1,cif.length());
			 if(cif.length()==9 && !Util.isNumeric(primletra) && letrasiniciales.contains(primletra.toUpperCase())){
			 	 String letras = "JABCDEFGHI";
				 String codigo=cif.substring(1,cif.length()-1);
				 int sumapar=0;
				 int sumaimpar=0;
				 String total="";
				 int letra=0;
				 for(int i=1;i<=5;i+=2)
					 sumapar=sumapar+Integer.parseInt(codigo.substring(i,i+1));
				 for(int i=0;i<=6;i+=2){
					 String param=""+Integer.parseInt(codigo.substring(i,i+1))*2;
					 int sumaparam=0;
		
					 for(int j=0;j<param.length();j++)
						 sumaparam=sumaparam+Integer.parseInt(param.substring(j,j+1));
					 sumaimpar=sumaimpar+sumaparam;
				 	}
				 total=new Integer(sumapar+sumaimpar).toString();
				 total=total.substring(total.length()-1,total.length());
				 letra=10-new Integer(total).intValue();
				 if(letra==10)
				 	letra=0;
				 if(ultimaletra.toUpperCase().equals(letras.substring(letra,letra+1)) || ultimaletra.equals(new Integer(letra).toString()))
					 return "1";				
				return "-1";			
			 	}
			return "-1";
			} 
		catch (Exception e) {
			return "-1";
			}
		}
	private String chequeaNIF_NIE(String valor){
		try {
    		String temp=valor.toString();
            String primero = temp.substring(0, 1);
            String letraFinal = temp.substring(temp.length() - 1, temp.length());
            
            int inicio = 0;//dni nacional
            if (!Util.isNumeric(primero)) 
            	inicio = 1;//dni extranjero
            String base=valor.substring(inicio, valor.length() - 1);
            
            if (primero.equalsIgnoreCase("X")) 
            	base="0"+base;
            else if (primero.equalsIgnoreCase("Y")) 
            	base="1"+base;
            else if (primero.equalsIgnoreCase("Z")) 
            	base="2"+base;
            
            String letra=_letraNif(base);

            if (letraFinal.equalsIgnoreCase(letra)) 
                return "1";
            
			return "-1";
    		}
    	catch (Exception e) {
    		return "-1";
    		}
		}
	private String _letraNif(String dni){
		String letras = "TRWAGMYFPDXBNJZSQVHLCKET";
		try {
			String ultima=dni.substring(dni.length()-1);
			if (!Util.isNumeric(ultima))
				dni=dni.substring(0, dni.length()-1);
				
			int numero = (new Integer(dni).intValue())%23;
			String letraCorrecta = letras.substring(numero,(numero+1));
			return letraCorrecta;
			}
		catch (Exception e) {return "";}
		}
}
