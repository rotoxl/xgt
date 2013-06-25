import net.sf.jasperreports.engine.*;


public class MiScriptlet extends JRDefaultScriptlet {
public Integer NumPag=1;    
public Integer ContPag=0;    
/** Creates a new instance of JRIreportDefaultScriptlet */
public MiScriptlet() {
	//
} 

public Boolean SetVar(String Variable, Boolean Valor, Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable de informe que se va a inicializar
	      Valor que se le asigna a la variable
	      Devuelve	la variable Boolean Salida
	*/ 
       this.setVariableValue(Variable, Valor);
       return Salida;
}
public Boolean SetVar(String Variable, String Valor, Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable de informe que se va a inicializar
	      Valor que se le asigna a la variable
	      Devuelve	la variable Boolean Salida
	*/ 
       this.setVariableValue(Variable, Valor);
       return Salida;
}
public Boolean SetVar(String Variable, Integer Valor, Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable de informe que se va a inicializar
	      Valor que se le asigna a la variable
	      Devuelve	la variable Boolean Salida
	*/ 
       this.setVariableValue(Variable, Valor);
       return Salida;
}
public Boolean SetVar(String Variable, Double Valor, Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable de informe que se va a inicializar
	      Valor que se le asigna a la variable
	      Devuelve	la variable Boolean Salida
	*/ 
       this.setVariableValue(Variable, Valor);
       return Salida;
}
public Object InicVarInt(String Variable, Integer Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicializar
	      Valor  entero con el que se va a inicializar
	*/ 
       this.setVariableValue(Variable, Valor);
       return this.getVariableValue(Variable);
}
public Object InicVarInt(String Variable, Integer Valor,Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicializar
	      Valor  entero con el que se va a inicializar
	*/ 
       this.setVariableValue(Variable, Valor);
       return Salida;
}
public Object InicVar(String Variable, String Valor)throws JRScriptletException
{
	/**   variable contiene una variable de informe que se va a inicializar
	      Valor nombre de la variable con al que se va a inicializar
	*/ 
       this.setVariableValue(Variable, this.getVariableValue(Valor));
       return this.getVariableValue(Variable);
}
public Boolean InicVar(String Variable, String Valor, Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable de informe que se va a inicializar
	      Valor nombre de la variable con al que se va a inicializar
	      Devuelve	la variable Boolean Salida
	*/ 
       this.setVariableValue(Variable, this.getVariableValue(Valor));
       return Salida;
}
public Object ContVar(String Variable, Integer Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrmentat
	      con Valor 
	*/
       this.setVariableValue(Variable,(Integer)this.getVariableValue(Variable)+Valor);
       return this.getVariableValue(Variable);
}
public Object ContVar(String Variable, Integer Valor, Boolean Salida)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrmentat
	      con Valor 
	*/
       this.setVariableValue(Variable,(Integer)this.getVariableValue(Variable)+Valor);
       return Salida;
}
public Object SumVarInt(String Variable, String Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrmentar
	      con variable del informe Valor 
	*/
       this.setVariableValue(Variable,((Integer)this.getVariableValue(Variable)+(Integer)this.getVariableValue(Valor)));
       return this.getVariableValue(Variable);
}
public Object SumVarDbl(String Variable, String Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrementar
	      con variable del informe Valor 
	*/
       this.setVariableValue(Variable,((Double)this.getVariableValue(Variable)+(Double)this.getVariableValue(Valor)));
       return this.getVariableValue(Variable);
}
public Object SumVarFlt(String Variable, String Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrementar
	      con variable del informe Valor 
	*/
       this.setVariableValue(Variable,((Float)this.getVariableValue(Variable)+(Float)this.getVariableValue(Valor)));
       return this.getVariableValue(Variable);
}
public Object SumVarLng(String Variable, String Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrementar
	      con variable del informe Valor 
	*/
       this.setVariableValue(Variable,((Long)this.getVariableValue(Variable)+(Long)this.getVariableValue(Valor)));
       return this.getVariableValue(Variable);
}
public Object SumVarShr(String Variable, String Valor)throws JRScriptletException
{
	/**   variable contiene una variable del informe que se va a inicrementar
	      con variable del informe Valor 
	*/
       this.setVariableValue(Variable,((Short)this.getVariableValue(Variable)+(Short)this.getVariableValue(Valor)));
       return this.getVariableValue(Variable);
}
public Object ValVar(String Variable)throws JRScriptletException
{
       return this.getVariableValue(Variable);
}
public Object Var(String Variable)throws JRScriptletException
{
       return this.getVariableValue(Variable);
}
public Integer iniciapag()
{
  NumPag=1; 
  ContPag=0;
  return NumPag;
}
public Integer nuevapag()
{
  NumPag=NumPag+1; 
  ContPag=0;	
  return NumPag;
}
public Integer pagina()
{
  return NumPag;
}
public Integer pagina(Integer maximo )
{
	ContPag=ContPag+1;
	if (ContPag>maximo-1){
		NumPag=NumPag+1; 
		ContPag=0;	
		return maximo;	
	}
	return ContPag;
}



/** Begin EVENT_AFTER_COLUMN_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void afterColumnInit() throws JRScriptletException
{
	super.beforeColumnInit();
}
/** End EVENT_AFTER_COLUMN_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_AFTER_DETAIL_EVAL This line is generated by iReport. Don't modify or move please! */
@Override
public void afterDetailEval() throws JRScriptletException
{
	super.afterDetailEval();
}
/** End EVENT_AFTER_DETAIL_EVAL This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_AFTER_GROUP_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void afterGroupInit(String groupName) throws JRScriptletException
{
	super.afterGroupInit(groupName);
}
/** End EVENT_AFTER_GROUP_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_AFTER_PAGE_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void afterPageInit() throws JRScriptletException
{
	super.afterPageInit();
}
/** End EVENT_AFTER_PAGE_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_AFTER_REPORT_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void afterReportInit() throws JRScriptletException
{
	//
}
/** End EVENT_AFTER_REPORT_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_BEFORE_COLUMN_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void beforeColumnInit() throws JRScriptletException
{
	//	
}
/** End EVENT_BEFORE_COLUMN_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_BEFORE_DETAIL_EVAL This line is generated by iReport. Don't modify or move please! */
@Override
public void beforeDetailEval() throws JRScriptletException {
	//
}
/** end EVENT_BEFORE_DETAIL_EVAL Please don't touch or move this comment*/

/** End EVENT_BEFORE_DETAIL_EVAL This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_BEFORE_GROUP_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void beforeGroupInit(String groupName) throws JRScriptletException {
	//
}
/** End EVENT_BEFORE_GROUP_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_BEFORE_PAGE_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void beforePageInit() throws JRScriptletException {
//	
}
/** End EVENT_BEFORE_PAGE_INIT This line is generated by iReport. Don't modify or move please! */
/** Begin EVENT_BEFORE_REPORT_INIT This line is generated by iReport. Don't modify or move please! */
@Override
public void beforeReportInit() throws JRScriptletException {
	//
}

/** End EVENT_BEFORE_REPORT_INIT This line is generated by iReport. Don't modify or move please! */

}