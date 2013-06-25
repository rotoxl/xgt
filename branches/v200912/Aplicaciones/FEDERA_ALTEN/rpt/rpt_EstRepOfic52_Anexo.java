/*
 * Generated by JasperReports - 8/09/08 12:51
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
public class rpt_EstRepOfic52_Anexo extends JRCalculator
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

    private JRFillField field_CLAVE = null;
    private JRFillField field_EMITIDOS = null;
    private JRFillField field_PUBLICACION = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
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
        field_CLAVE = (JRFillField)fm.get("CLAVE");
        field_EMITIDOS = (JRFillField)fm.get("EMITIDOS");
        field_PUBLICACION = (JRFillField)fm.get("PUBLICACION");
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
            case 159 : // 159
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 172 : // 172
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_EMITIDOS.getValue()));
                break;
            }
            case 171 : // 171
            {
                value = (java.lang.String)(((java.lang.String)field_PUBLICACION.getValue()));
                break;
            }
            case 160 : // 160
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 162 : // 162
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 167 : // 167
            {
                value = (java.lang.Object)(((java.lang.String)field_CLAVE.getValue()));
                break;
            }
            case 164 : // 164
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 166 : // 166
            {
                value = (java.lang.String)("");
                break;
            }
            case 169 : // 169
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 168 : // 168
            {
                value = (java.lang.String)(((java.lang.String)field_CLAVE.getValue()));
                break;
            }
            case 158 : // 158
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 174 : // 174
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 163 : // 163
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 170 : // 170
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 173 : // 173
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 161 : // 161
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 165 : // 165
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
            case 159 : // 159
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 172 : // 172
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_EMITIDOS.getOldValue()));
                break;
            }
            case 171 : // 171
            {
                value = (java.lang.String)(((java.lang.String)field_PUBLICACION.getOldValue()));
                break;
            }
            case 160 : // 160
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 162 : // 162
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 167 : // 167
            {
                value = (java.lang.Object)(((java.lang.String)field_CLAVE.getOldValue()));
                break;
            }
            case 164 : // 164
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 166 : // 166
            {
                value = (java.lang.String)("");
                break;
            }
            case 169 : // 169
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 168 : // 168
            {
                value = (java.lang.String)(((java.lang.String)field_CLAVE.getOldValue()));
                break;
            }
            case 158 : // 158
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 174 : // 174
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 163 : // 163
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 170 : // 170
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 173 : // 173
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 161 : // 161
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 165 : // 165
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
            case 159 : // 159
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 172 : // 172
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_EMITIDOS.getValue()));
                break;
            }
            case 171 : // 171
            {
                value = (java.lang.String)(((java.lang.String)field_PUBLICACION.getValue()));
                break;
            }
            case 160 : // 160
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 162 : // 162
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 167 : // 167
            {
                value = (java.lang.Object)(((java.lang.String)field_CLAVE.getValue()));
                break;
            }
            case 164 : // 164
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 166 : // 166
            {
                value = (java.lang.String)("");
                break;
            }
            case 169 : // 169
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 168 : // 168
            {
                value = (java.lang.String)(((java.lang.String)field_CLAVE.getValue()));
                break;
            }
            case 158 : // 158
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 174 : // 174
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 163 : // 163
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 170 : // 170
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 173 : // 173
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 161 : // 161
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 165 : // 165
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