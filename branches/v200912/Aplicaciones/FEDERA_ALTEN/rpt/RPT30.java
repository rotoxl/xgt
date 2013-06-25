/*
 * Generated by JasperReports - 28/12/10 15:01
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;

import java.util.*;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.*;


/**
 *
 */
public class RPT30 extends JRCalculator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillParameter parameter_P1 = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;

    private JRFillField field_T�tulo32del32Trabajo = null;
    private JRFillField field_NOMBRE = null;
    private JRFillField field_�mbito32del32trabjo = null;
    private JRFillField field_C�digo32del32Proyecto = null;
    private JRFillField field_Titulo32Informe = null;
    private JRFillField field_Procedencia32del32trabajo = null;
    private JRFillField field_ESTADO = null;
    private JRFillField field_fecha32de32recepci�n = null;
    private JRFillField field_Comunidad32Aut�noma = null;
    private JRFillField field_fecha32de32t�tulo = null;
    private JRFillField field_Tem�tica32del32Trabajo = null;
    private JRFillField field_Observaciones32Internas = null;
    private JRFillField field_CD_PROYECTO = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;


    /**
     *
     */
    public void customizedInit(
        Map pm,
        Map fm,
        Map vm
        )
    {
        initParams(pm);
        initFields(fm);
        initVars(vm);
    }


    /**
     *
     */
    private void initParams(Map pm)
    {
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)pm.get("REPORT_RESOURCE_BUNDLE");
        parameter_P1 = (JRFillParameter)pm.get("P1");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
    }


    /**
     *
     */
    private void initFields(Map fm)
    {
        field_T�tulo32del32Trabajo = (JRFillField)fm.get("T�tulo del Trabajo");
        field_NOMBRE = (JRFillField)fm.get("NOMBRE");
        field_�mbito32del32trabjo = (JRFillField)fm.get("�mbito del trabjo");
        field_C�digo32del32Proyecto = (JRFillField)fm.get("C�digo del Proyecto");
        field_Titulo32Informe = (JRFillField)fm.get("Titulo Informe");
        field_Procedencia32del32trabajo = (JRFillField)fm.get("Procedencia del trabajo");
        field_ESTADO = (JRFillField)fm.get("ESTADO");
        field_fecha32de32recepci�n = (JRFillField)fm.get("fecha de recepci�n");
        field_Comunidad32Aut�noma = (JRFillField)fm.get("Comunidad Aut�noma");
        field_fecha32de32t�tulo = (JRFillField)fm.get("fecha de t�tulo");
        field_Tem�tica32del32Trabajo = (JRFillField)fm.get("Tem�tica del Trabajo");
        field_Observaciones32Internas = (JRFillField)fm.get("Observaciones Internas");
        field_CD_PROYECTO = (JRFillField)fm.get("CD_PROYECTO");
    }


    /**
     *
     */
    private void initVars(Map vm)
    {
        variable_PAGE_NUMBER = (JRFillVariable)vm.get("PAGE_NUMBER");
        variable_COLUMN_NUMBER = (JRFillVariable)vm.get("COLUMN_NUMBER");
        variable_REPORT_COUNT = (JRFillVariable)vm.get("REPORT_COUNT");
        variable_PAGE_COUNT = (JRFillVariable)vm.get("PAGE_COUNT");
        variable_COLUMN_COUNT = (JRFillVariable)vm.get("COLUMN_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 1 : // 1
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 19 : // 19
            {
                value = (java.lang.String)(((java.lang.String)field_Observaciones32Internas.getValue()));
                break;
            }
            case 8 : // 8
            {
                value = (java.lang.Integer)(new Integer("140"));
                break;
            }
            case 11 : // 11
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_REPORT_COUNT.getValue()));
                break;
            }
            case 16 : // 16
            {
                value = (java.lang.String)(((java.lang.String)field_Comunidad32Aut�noma.getValue()));
                break;
            }
            case 10 : // 10
            {
                value = (java.lang.String)(((java.lang.String)field_Titulo32Informe.getValue()));
                break;
            }
            case 23 : // 23
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 6 : // 6
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 9 : // 9
            {
                value = (java.lang.String)(((java.lang.String)field_fecha32de32t�tulo.getValue()));
                break;
            }
            case 20 : // 20
            {
                value = (java.lang.String)(((java.lang.String)field_ESTADO.getValue()));
                break;
            }
            case 17 : // 17
            {
                value = (java.lang.String)(((java.lang.String)field_C�digo32del32Proyecto.getValue()));
                break;
            }
            case 7 : // 7
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 0 : // 0
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 5 : // 5
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 18 : // 18
            {
                value = (java.lang.String)(((java.lang.String)field_Tem�tica32del32Trabajo.getValue()));
                break;
            }
            case 12 : // 12
            {
                value = (java.lang.String)(((java.lang.String)field_T�tulo32del32Trabajo.getValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 21 : // 21
            {
                value = (java.lang.String)(((java.lang.String)field_NOMBRE.getValue()));
                break;
            }
            case 14 : // 14
            {
                value = (java.lang.String)(((java.lang.String)field_Procedencia32del32trabajo.getValue()));
                break;
            }
            case 2 : // 2
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 15 : // 15
            {
                value = (java.lang.String)(((java.lang.String)field_�mbito32del32trabjo.getValue()));
                break;
            }
            case 3 : // 3
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 4 : // 4
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 13 : // 13
            {
                value = (java.lang.String)(((java.lang.String)field_fecha32de32recepci�n.getValue()));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateOld(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 1 : // 1
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 19 : // 19
            {
                value = (java.lang.String)(((java.lang.String)field_Observaciones32Internas.getOldValue()));
                break;
            }
            case 8 : // 8
            {
                value = (java.lang.Integer)(new Integer("140"));
                break;
            }
            case 11 : // 11
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_REPORT_COUNT.getOldValue()));
                break;
            }
            case 16 : // 16
            {
                value = (java.lang.String)(((java.lang.String)field_Comunidad32Aut�noma.getOldValue()));
                break;
            }
            case 10 : // 10
            {
                value = (java.lang.String)(((java.lang.String)field_Titulo32Informe.getOldValue()));
                break;
            }
            case 23 : // 23
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 6 : // 6
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 9 : // 9
            {
                value = (java.lang.String)(((java.lang.String)field_fecha32de32t�tulo.getOldValue()));
                break;
            }
            case 20 : // 20
            {
                value = (java.lang.String)(((java.lang.String)field_ESTADO.getOldValue()));
                break;
            }
            case 17 : // 17
            {
                value = (java.lang.String)(((java.lang.String)field_C�digo32del32Proyecto.getOldValue()));
                break;
            }
            case 7 : // 7
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 0 : // 0
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 5 : // 5
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 18 : // 18
            {
                value = (java.lang.String)(((java.lang.String)field_Tem�tica32del32Trabajo.getOldValue()));
                break;
            }
            case 12 : // 12
            {
                value = (java.lang.String)(((java.lang.String)field_T�tulo32del32Trabajo.getOldValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 21 : // 21
            {
                value = (java.lang.String)(((java.lang.String)field_NOMBRE.getOldValue()));
                break;
            }
            case 14 : // 14
            {
                value = (java.lang.String)(((java.lang.String)field_Procedencia32del32trabajo.getOldValue()));
                break;
            }
            case 2 : // 2
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 15 : // 15
            {
                value = (java.lang.String)(((java.lang.String)field_�mbito32del32trabjo.getOldValue()));
                break;
            }
            case 3 : // 3
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 4 : // 4
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 13 : // 13
            {
                value = (java.lang.String)(((java.lang.String)field_fecha32de32recepci�n.getOldValue()));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


    /**
     *
     */
    public Object evaluateEstimated(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 1 : // 1
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 19 : // 19
            {
                value = (java.lang.String)(((java.lang.String)field_Observaciones32Internas.getValue()));
                break;
            }
            case 8 : // 8
            {
                value = (java.lang.Integer)(new Integer("140"));
                break;
            }
            case 11 : // 11
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_REPORT_COUNT.getEstimatedValue()));
                break;
            }
            case 16 : // 16
            {
                value = (java.lang.String)(((java.lang.String)field_Comunidad32Aut�noma.getValue()));
                break;
            }
            case 10 : // 10
            {
                value = (java.lang.String)(((java.lang.String)field_Titulo32Informe.getValue()));
                break;
            }
            case 23 : // 23
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 6 : // 6
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 9 : // 9
            {
                value = (java.lang.String)(((java.lang.String)field_fecha32de32t�tulo.getValue()));
                break;
            }
            case 20 : // 20
            {
                value = (java.lang.String)(((java.lang.String)field_ESTADO.getValue()));
                break;
            }
            case 17 : // 17
            {
                value = (java.lang.String)(((java.lang.String)field_C�digo32del32Proyecto.getValue()));
                break;
            }
            case 7 : // 7
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 0 : // 0
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 5 : // 5
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 18 : // 18
            {
                value = (java.lang.String)(((java.lang.String)field_Tem�tica32del32Trabajo.getValue()));
                break;
            }
            case 12 : // 12
            {
                value = (java.lang.String)(((java.lang.String)field_T�tulo32del32Trabajo.getValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 21 : // 21
            {
                value = (java.lang.String)(((java.lang.String)field_NOMBRE.getValue()));
                break;
            }
            case 14 : // 14
            {
                value = (java.lang.String)(((java.lang.String)field_Procedencia32del32trabajo.getValue()));
                break;
            }
            case 2 : // 2
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 15 : // 15
            {
                value = (java.lang.String)(((java.lang.String)field_�mbito32del32trabjo.getValue()));
                break;
            }
            case 3 : // 3
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 4 : // 4
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 13 : // 13
            {
                value = (java.lang.String)(((java.lang.String)field_fecha32de32recepci�n.getValue()));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}
