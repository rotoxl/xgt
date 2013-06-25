/*
 * Generated by JasperReports - 26/08/08 12:58
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
public class RPT_AREA_PROVEEDOR extends JRCalculator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillParameter parameter_P4 = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_P1 = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_P2 = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_P3 = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;

    private JRFillField field_CD_EJERCICIO = null;
    private JRFillField field_DS_AREA = null;
    private JRFillField field_IMPORTE_TOTAL = null;
    private JRFillField field_NOMBRE = null;
    private JRFillField field_CD_PROVEEDOR = null;
    private JRFillField field_CD_DOCFISCAL = null;
    private JRFillField field_AREA = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_areas_COUNT = null;


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
        parameter_P4 = (JRFillParameter)pm.get("P4");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_P1 = (JRFillParameter)pm.get("P1");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_P2 = (JRFillParameter)pm.get("P2");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_P3 = (JRFillParameter)pm.get("P3");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
    }


    /**
     *
     */
    private void initFields(Map fm)
    {
        field_CD_EJERCICIO = (JRFillField)fm.get("CD_EJERCICIO");
        field_DS_AREA = (JRFillField)fm.get("DS_AREA");
        field_IMPORTE_TOTAL = (JRFillField)fm.get("IMPORTE_TOTAL");
        field_NOMBRE = (JRFillField)fm.get("NOMBRE");
        field_CD_PROVEEDOR = (JRFillField)fm.get("CD_PROVEEDOR");
        field_CD_DOCFISCAL = (JRFillField)fm.get("CD_DOCFISCAL");
        field_AREA = (JRFillField)fm.get("AREA");
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
        variable_areas_COUNT = (JRFillVariable)vm.get("areas_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 23 : // 23
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 28 : // 28
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 38 : // 38
            {
                value = (java.lang.String)(((java.lang.String)field_DS_AREA.getValue()));
                break;
            }
            case 37 : // 37
            {
                value = (java.lang.String)(java.util.Calendar.getInstance().get(java.util.Calendar.DATE)
+ "/" +
((java.util.Calendar.getInstance().get(java.util.Calendar.MONTH))+1)
+ "/" +
java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
                break;
            }
            case 36 : // 36
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_CD_EJERCICIO.getValue()));
                break;
            }
            case 31 : // 31
            {
                value = (java.lang.Integer)(new Integer ("2008"));
                break;
            }
            case 34 : // 34
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 43 : // 43
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 42 : // 42
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 26 : // 26
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 41 : // 41
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMPORTE_TOTAL.getValue()));
                break;
            }
            case 33 : // 33
            {
                value = (java.lang.Object)(((java.lang.String)field_DS_AREA.getValue()));
                break;
            }
            case 24 : // 24
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 27 : // 27
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 29 : // 29
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 32 : // 32
            {
                value = (java.lang.Integer)(new Integer ("0"));
                break;
            }
            case 25 : // 25
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 40 : // 40
            {
                value = (java.lang.String)(((java.lang.String)field_NOMBRE.getValue()));
                break;
            }
            case 39 : // 39
            {
                value = (java.lang.String)(((java.lang.String)field_CD_DOCFISCAL.getValue()));
                break;
            }
            case 30 : // 30
            {
                value = (java.lang.String)("Admin");
                break;
            }
            case 35 : // 35
            {
                value = (java.lang.Integer)(new Integer(0));
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
            case 23 : // 23
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 28 : // 28
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 38 : // 38
            {
                value = (java.lang.String)(((java.lang.String)field_DS_AREA.getOldValue()));
                break;
            }
            case 37 : // 37
            {
                value = (java.lang.String)(java.util.Calendar.getInstance().get(java.util.Calendar.DATE)
+ "/" +
((java.util.Calendar.getInstance().get(java.util.Calendar.MONTH))+1)
+ "/" +
java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
                break;
            }
            case 36 : // 36
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_CD_EJERCICIO.getOldValue()));
                break;
            }
            case 31 : // 31
            {
                value = (java.lang.Integer)(new Integer ("2008"));
                break;
            }
            case 34 : // 34
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 43 : // 43
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 42 : // 42
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 26 : // 26
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 41 : // 41
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMPORTE_TOTAL.getOldValue()));
                break;
            }
            case 33 : // 33
            {
                value = (java.lang.Object)(((java.lang.String)field_DS_AREA.getOldValue()));
                break;
            }
            case 24 : // 24
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 27 : // 27
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 29 : // 29
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 32 : // 32
            {
                value = (java.lang.Integer)(new Integer ("0"));
                break;
            }
            case 25 : // 25
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 40 : // 40
            {
                value = (java.lang.String)(((java.lang.String)field_NOMBRE.getOldValue()));
                break;
            }
            case 39 : // 39
            {
                value = (java.lang.String)(((java.lang.String)field_CD_DOCFISCAL.getOldValue()));
                break;
            }
            case 30 : // 30
            {
                value = (java.lang.String)("Admin");
                break;
            }
            case 35 : // 35
            {
                value = (java.lang.Integer)(new Integer(0));
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
            case 23 : // 23
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 28 : // 28
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 38 : // 38
            {
                value = (java.lang.String)(((java.lang.String)field_DS_AREA.getValue()));
                break;
            }
            case 37 : // 37
            {
                value = (java.lang.String)(java.util.Calendar.getInstance().get(java.util.Calendar.DATE)
+ "/" +
((java.util.Calendar.getInstance().get(java.util.Calendar.MONTH))+1)
+ "/" +
java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));
                break;
            }
            case 36 : // 36
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_CD_EJERCICIO.getValue()));
                break;
            }
            case 31 : // 31
            {
                value = (java.lang.Integer)(new Integer ("2008"));
                break;
            }
            case 34 : // 34
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 43 : // 43
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 42 : // 42
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 26 : // 26
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 41 : // 41
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMPORTE_TOTAL.getValue()));
                break;
            }
            case 33 : // 33
            {
                value = (java.lang.Object)(((java.lang.String)field_DS_AREA.getValue()));
                break;
            }
            case 24 : // 24
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 27 : // 27
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 29 : // 29
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 32 : // 32
            {
                value = (java.lang.Integer)(new Integer ("0"));
                break;
            }
            case 25 : // 25
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 40 : // 40
            {
                value = (java.lang.String)(((java.lang.String)field_NOMBRE.getValue()));
                break;
            }
            case 39 : // 39
            {
                value = (java.lang.String)(((java.lang.String)field_CD_DOCFISCAL.getValue()));
                break;
            }
            case 30 : // 30
            {
                value = (java.lang.String)("Admin");
                break;
            }
            case 35 : // 35
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}