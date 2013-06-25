/*
 * Generated by JasperReports - 28/11/05 11:53
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
public class rpt_InvAlm54 extends JRCalculator
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

    private JRFillField field_TOTAL1 = null;
    private JRFillField field_ALMACEN1 = null;
    private JRFillField field_TOTALES = null;
    private JRFillField field_NIVEL = null;
    private JRFillField field_TOTAL2 = null;
    private JRFillField field_CODIGO = null;
    private JRFillField field_ALMACEN2 = null;
    private JRFillField field_TITULO = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_PUBLICACION_COUNT = null;


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
        field_TOTAL1 = (JRFillField)fm.get("TOTAL1");
        field_ALMACEN1 = (JRFillField)fm.get("ALMACEN1");
        field_TOTALES = (JRFillField)fm.get("TOTALES");
        field_NIVEL = (JRFillField)fm.get("NIVEL");
        field_TOTAL2 = (JRFillField)fm.get("TOTAL2");
        field_CODIGO = (JRFillField)fm.get("CODIGO");
        field_ALMACEN2 = (JRFillField)fm.get("ALMACEN2");
        field_TITULO = (JRFillField)fm.get("TITULO");
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
        variable_PUBLICACION_COUNT = (JRFillVariable)vm.get("PUBLICACION_COUNT");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 608 : // 608
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 618 : // 618
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTAL2.getValue()));
                break;
            }
            case 611 : // 611
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 609 : // 609
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 613 : // 613
            {
                value = (java.lang.Object)(((java.lang.String)field_CODIGO.getValue()));
                break;
            }
            case 604 : // 604
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 607 : // 607
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 606 : // 606
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 605 : // 605
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 614 : // 614
            {
                value = (java.lang.String)(((java.lang.String)field_CODIGO.getValue()));
                break;
            }
            case 617 : // 617
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTALES.getValue()));
                break;
            }
            case 612 : // 612
            {
                value = (java.lang.String)("");
                break;
            }
            case 615 : // 615
            {
                value = (java.lang.String)(((java.lang.String)field_TITULO.getValue()));
                break;
            }
            case 610 : // 610
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 620 : // 620
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 622 : // 622
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 621 : // 621
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 623 : // 623
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 616 : // 616
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_NIVEL.getValue()));
                break;
            }
            case 619 : // 619
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTAL1.getValue()));
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
            case 608 : // 608
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 618 : // 618
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTAL2.getOldValue()));
                break;
            }
            case 611 : // 611
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 609 : // 609
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 613 : // 613
            {
                value = (java.lang.Object)(((java.lang.String)field_CODIGO.getOldValue()));
                break;
            }
            case 604 : // 604
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 607 : // 607
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 606 : // 606
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 605 : // 605
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 614 : // 614
            {
                value = (java.lang.String)(((java.lang.String)field_CODIGO.getOldValue()));
                break;
            }
            case 617 : // 617
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTALES.getOldValue()));
                break;
            }
            case 612 : // 612
            {
                value = (java.lang.String)("");
                break;
            }
            case 615 : // 615
            {
                value = (java.lang.String)(((java.lang.String)field_TITULO.getOldValue()));
                break;
            }
            case 610 : // 610
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 620 : // 620
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 622 : // 622
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 621 : // 621
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 623 : // 623
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 616 : // 616
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_NIVEL.getOldValue()));
                break;
            }
            case 619 : // 619
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTAL1.getOldValue()));
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
            case 608 : // 608
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 618 : // 618
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTAL2.getValue()));
                break;
            }
            case 611 : // 611
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 609 : // 609
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 613 : // 613
            {
                value = (java.lang.Object)(((java.lang.String)field_CODIGO.getValue()));
                break;
            }
            case 604 : // 604
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 607 : // 607
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 606 : // 606
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 605 : // 605
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 614 : // 614
            {
                value = (java.lang.String)(((java.lang.String)field_CODIGO.getValue()));
                break;
            }
            case 617 : // 617
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTALES.getValue()));
                break;
            }
            case 612 : // 612
            {
                value = (java.lang.String)("");
                break;
            }
            case 615 : // 615
            {
                value = (java.lang.String)(((java.lang.String)field_TITULO.getValue()));
                break;
            }
            case 610 : // 610
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 620 : // 620
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 622 : // 622
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 621 : // 621
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 623 : // 623
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 616 : // 616
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_NIVEL.getValue()));
                break;
            }
            case 619 : // 619
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_TOTAL1.getValue()));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}