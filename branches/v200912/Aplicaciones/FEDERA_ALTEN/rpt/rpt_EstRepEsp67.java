/*
 * Generated by JasperReports - 25/11/05 14:27
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
public class rpt_EstRepEsp67 extends JRCalculator
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

    private JRFillField field_DS_MSALIDA = null;
    private JRFillField field_SALIDAS = null;
    private JRFillField field_MSALIDA = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_SALIDASMOTIVO = null;
    private JRFillVariable variable_MOTIVOSALIDA_COUNT = null;


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
        field_DS_MSALIDA = (JRFillField)fm.get("DS_MSALIDA");
        field_SALIDAS = (JRFillField)fm.get("SALIDAS");
        field_MSALIDA = (JRFillField)fm.get("MSALIDA");
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
        variable_SALIDASMOTIVO = (JRFillVariable)vm.get("SALIDASMOTIVO");
        variable_MOTIVOSALIDA_COUNT = (JRFillVariable)vm.get("MOTIVOSALIDA_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 1472 : // 1472
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1485 : // 1485
            {
                value = (java.lang.String)(((java.lang.String)field_DS_MSALIDA.getValue()));
                break;
            }
            case 1484 : // 1484
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMOTIVO.getValue()));
                break;
            }
            case 1477 : // 1477
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1478 : // 1478
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1482 : // 1482
            {
                value = (java.lang.Object)(((java.lang.Integer)field_MSALIDA.getValue()));
                break;
            }
            case 1473 : // 1473
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1476 : // 1476
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1486 : // 1486
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1487 : // 1487
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1488 : // 1488
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 1479 : // 1479
            {
                value = (java.lang.String)("");
                break;
            }
            case 1481 : // 1481
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1489 : // 1489
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 1483 : // 1483
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.String)field_DS_MSALIDA.getValue()).toUpperCase()!=null));
                break;
            }
            case 1475 : // 1475
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1471 : // 1471
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1480 : // 1480
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1474 : // 1474
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
            case 1472 : // 1472
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1485 : // 1485
            {
                value = (java.lang.String)(((java.lang.String)field_DS_MSALIDA.getOldValue()));
                break;
            }
            case 1484 : // 1484
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMOTIVO.getOldValue()));
                break;
            }
            case 1477 : // 1477
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1478 : // 1478
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1482 : // 1482
            {
                value = (java.lang.Object)(((java.lang.Integer)field_MSALIDA.getOldValue()));
                break;
            }
            case 1473 : // 1473
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1476 : // 1476
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1486 : // 1486
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1487 : // 1487
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1488 : // 1488
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 1479 : // 1479
            {
                value = (java.lang.String)("");
                break;
            }
            case 1481 : // 1481
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1489 : // 1489
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 1483 : // 1483
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.String)field_DS_MSALIDA.getOldValue()).toUpperCase()!=null));
                break;
            }
            case 1475 : // 1475
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1471 : // 1471
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1480 : // 1480
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getOldValue()));
                break;
            }
            case 1474 : // 1474
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
            case 1472 : // 1472
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1485 : // 1485
            {
                value = (java.lang.String)(((java.lang.String)field_DS_MSALIDA.getValue()));
                break;
            }
            case 1484 : // 1484
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMOTIVO.getEstimatedValue()));
                break;
            }
            case 1477 : // 1477
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1478 : // 1478
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1482 : // 1482
            {
                value = (java.lang.Object)(((java.lang.Integer)field_MSALIDA.getValue()));
                break;
            }
            case 1473 : // 1473
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1476 : // 1476
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1486 : // 1486
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1487 : // 1487
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1488 : // 1488
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 1479 : // 1479
            {
                value = (java.lang.String)("");
                break;
            }
            case 1481 : // 1481
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1489 : // 1489
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 1483 : // 1483
            {
                value = (java.lang.Boolean)(new Boolean(((java.lang.String)field_DS_MSALIDA.getValue()).toUpperCase()!=null));
                break;
            }
            case 1475 : // 1475
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1471 : // 1471
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1480 : // 1480
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1474 : // 1474
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
