/*
 * Generated by JasperReports - 22/12/05 18:17
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
public class prueba extends JRCalculator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;

    private JRFillField field_CD_PUBLICACION = null;

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
        field_CD_PUBLICACION = (JRFillField)fm.get("CD_PUBLICACION");
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
            case 26 : // 26
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_CD_PUBLICACION.getValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 21 : // 21
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 25 : // 25
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 20 : // 20
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 23 : // 23
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 24 : // 24
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 18 : // 18
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 19 : // 19
            {
                value = (java.lang.Integer)(new Integer(1));
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
            case 26 : // 26
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_CD_PUBLICACION.getOldValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 21 : // 21
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 25 : // 25
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 20 : // 20
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 23 : // 23
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 24 : // 24
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 18 : // 18
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 19 : // 19
            {
                value = (java.lang.Integer)(new Integer(1));
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
            case 26 : // 26
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_CD_PUBLICACION.getValue()));
                break;
            }
            case 22 : // 22
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 21 : // 21
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 25 : // 25
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 20 : // 20
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 23 : // 23
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 24 : // 24
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 18 : // 18
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 19 : // 19
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}