package es.burke.tests;

import static org.mockito.Mockito.mock;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import es.burke.gotta.ErrorGotta;
import es.burke.gotta.Evaluador;
import es.burke.gotta.FechaGotta;
import es.burke.gotta.Motor;
import es.burke.gotta.Util;

public class EvaluadorTest extends TestCase{
	Evaluador ev;
	Motor mockMotor;
	
	@Before
	public void setUp() throws Exception {
		super.setUp();
		
		mockMotor=mock(Motor.class);
		ev=new Evaluador(mockMotor);
		}
	@After
	public void tearDown() throws Exception {
		super.tearDown();
		
		ev=null; mockMotor=null;
		}
    public EvaluadorTest(String name) {
        super(name);
        }
    
	public static ArrayList<Object> creaLista(Object...p){
		return Util.creaLista(p);
		}
    public static void print(Object s){
    	System.out.println(s);
    	}

////////////////////////////////////////
	@Test
	public void test_Fechas() throws ErrorGotta {
    	Mockito.when(mockMotor.getValorSimbolo("@F_Vencimiento")).thenReturn(creaLista(new FechaGotta("23/08/2012 12:12:12")));
    	Mockito.when(mockMotor.esNombreVariable("@F_Vencimiento")).thenReturn(true);
    	
    	assertEquals("13/08/2012 12:12:12", ev.evalua("@F_Vencimiento-20+10"));
    	
    ////////////
    	Mockito.when(mockMotor.getValorSimbolo("@F_Vencimiento")).thenReturn(creaLista(new FechaGotta("23/08/2012")));
    	Mockito.when(mockMotor.getValorSimbolo("@F_Ejecucion")).thenReturn(creaLista(new FechaGotta("20/08/2012")));
    	Mockito.when(mockMotor.esNombreVariable("@F_Ejecucion")).thenReturn(true);
    	
    	assertEquals("3", ev.evalua("@F_Vencimiento-@F_Ejecucion"));
    	
	////////////	  	
	  	assertEquals(true, ev.evalua("@F_Vencimiento>@F_Ejecucion"));
	  	assertEquals(false, ev.evalua("@F_Vencimiento<@F_Ejecucion"));
	 
	////////////  
	  	String exp="(@F_Vencimiento & '' # '')";
		assertEquals(true, ev.evalua(exp));
		
		////////////////
    		Mockito.when(mockMotor.getValorSimbolo("@F_Vencimiento")).thenReturn(creaLista((Object)null));
    		assertEquals(false, ev.evalua(exp));
	
	////////////
		exp="(@F_Vencimiento # '')"; //El nulo, en el evaluador, se trata como cadena vacía
		assertEquals(false, ev.evalua(exp));
    	}
    
//////////////////////////////////////////
	@Test
	public void test_ExpresionesLógicas() throws ErrorGotta {
		String exp="(*.REL_EXPvsINT.CD_Estado # 1)*(*.REL_EXPvsINT.CD_Estado # 2)*(*.REL_EXPvsINT.CD_Estado # 3)*(*.REL_EXPvsINT.CD_Estado # 4)";
		
			/////////////
	    	Mockito.when(mockMotor.getValorSimbolo("*.REL_EXPvsINT.CD_Estado")).thenReturn(creaLista(5));
	    	Mockito.when(mockMotor.existeCampo("*.REL_EXPvsINT.CD_Estado", false)).thenReturn(true);
	    	assertEquals(true, ev.evalua(exp));
			
	    	//////////////
	    	Mockito.when(mockMotor.getValorSimbolo("*.REL_EXPvsINT.CD_Estado")).thenReturn(creaLista(1));
	    	assertEquals(false, ev.evalua(exp));
     
	    //////////////    	
    	exp="(CD_ClaseContrato.CD_TipoContrato= 'OBR')*(ImporteLiquidoLIC>199999.99)*(ImporteLiquidoLIC<1000000)";
	    	Mockito.when(mockMotor.existeCampo("CD_ClaseContrato.CD_TipoContrato", false)).thenReturn(true);
	    	Mockito.when(mockMotor.existeCampo("ImporteLiquidoLIC", false)).thenReturn(true);

	    	//////////////	    	
	    	Mockito.when(mockMotor.getValorSimbolo("CD_ClaseContrato.CD_TipoContrato")).thenReturn(creaLista("OBR"));
	    	Mockito.when(mockMotor.getValorSimbolo("ImporteLiquidoLIC")).thenReturn(creaLista(300000));
	    	assertEquals(true, ev.evalua(exp));
	    	
	    	//////////////
	    	Mockito.when(mockMotor.getValorSimbolo("ImporteLiquidoLIC")).thenReturn(creaLista(1000));
	    	assertEquals(false, ev.evalua(exp));
	    	
	    	//////////////
	    	Mockito.when(mockMotor.getValorSimbolo("CD_ClaseContrato.CD_TipoContrato")).thenReturn(creaLista("SUM"));
	    	Mockito.when(mockMotor.getValorSimbolo("ImporteLiquidoLIC")).thenReturn(creaLista(200000));
	    	assertEquals(false, ev.evalua(exp));
	    
		//////////////
		exp="(CD_ClaseContrato.CD_TipoContrato= 'OBR')+(CD_ClaseContrato.CD_TipoContrato= 'SUM')";
			//////////////
	    	Mockito.when(mockMotor.getValorSimbolo("CD_ClaseContrato.CD_TipoContrato")).thenReturn(creaLista("SUM"));
	    	assertEquals(true, ev.evalua(exp));
	    	
	    	//////////////
	    	Mockito.when(mockMotor.getValorSimbolo("CD_ClaseContrato.CD_TipoContrato")).thenReturn(creaLista("OTROS"));
	    	assertEquals(false, ev.evalua(exp));

	    //////////////////
	    	Mockito.when(mockMotor.existeCampo("*.DAT_EXPEDIENTES.CD_TipoAdjudicacion", false)).thenReturn(true);
	    	Mockito.when(mockMotor.getValorSimbolo("*.DAT_EXPEDIENTES.CD_TipoAdjudicacion")).thenReturn(creaLista("50"));
	    	assertEquals(true, ev.evalua("(*.DAT_EXPEDIENTES.CD_TipoAdjudicacion = 50)"));
	    	
	    //////////////////
	    	Mockito.when(mockMotor.getValorSimbolo("*.DAT_EXPEDIENTES.CD_TipoAdjudicacion")).thenReturn(creaLista("01"));
	    	assertEquals(true, ev.evalua("(*.DAT_EXPEDIENTES.CD_TipoAdjudicacion = 01)"));
	    	//-> se comparan como números porque se pasa antes por esNumérico, y esto provoca, por ejemplo, que 001=01
	    //////////////////
	    	assertEquals(true, ev.evalua("(*.DAT_EXPEDIENTES.CD_TipoAdjudicacion = '01')"));
			}

}


