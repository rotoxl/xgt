/*
 * Generated by JasperReports - 16/12/10 12:39
 */
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.fill.*;

import java.util.*;
import java.math.*;
import java.text.*;
import java.io.*;
import java.net.*;

import net.sf.jasperreports.engine.*;
import java.util.*;
import net.sf.jasperreports.engine.data.*;


/**
 *
 */
public class RPT_CIERRE_C_EX_1292499569623_832332 extends JREvaluator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_TIME_ZONE = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_FILE_RESOLVER = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_P1 = null;
    private JRFillParameter parameter_REPORT_CLASS_LOADER = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_URL_HANDLER_FACTORY = null;
    private JRFillParameter parameter_P2 = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_REPORT_FORMAT_FACTORY = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;
    private JRFillParameter parameter_REPORT_TEMPLATES = null;
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillField field_CD_ELEMENTO_01 = null;
    private JRFillField field_INVERSION_EJE = null;
    private JRFillField field_IMP_TOTAL = null;
    private JRFillField field_DS_ELEMENTO_04 = null;
    private JRFillField field_DS_ELEMENTO_02 = null;
    private JRFillField field_DS_ELEMENTO_03 = null;
    private JRFillField field_DS_ELEMENTO_01 = null;
    private JRFillField field_CD_ELEMENTO_04 = null;
    private JRFillField field_CD_ELEMENTO_02 = null;
    private JRFillField field_MANT_EJE = null;
    private JRFillField field_CD_ELEMENTO_03 = null;
    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_Grupo_Programa_COUNT = null;
    private JRFillVariable variable_Grupo_Subprograma_COUNT = null;
    private JRFillVariable variable_Grupo_Espacio_COUNT = null;
    private JRFillVariable variable_SUM_IMP_MANT_03 = null;
    private JRFillVariable variable_SUM_IMP_MANT_04 = null;
    private JRFillVariable variable_SUM_IMP_MANT_05 = null;
    private JRFillVariable variable_SUM_IMP_INV_03 = null;
    private JRFillVariable variable_SUM_IMP_INV_04 = null;
    private JRFillVariable variable_SUM_IMP_INV_05 = null;
    private JRFillVariable variable_SUM_IMP_TOTAL_03 = null;
    private JRFillVariable variable_SUM_IMP_TOTAL_04 = null;
    private JRFillVariable variable_SUM_IMP_TOTAL_05 = null;
    private JRFillVariable variable_TOT_MANT_EJE = null;
    private JRFillVariable variable_TOT_INVERSION_EJE = null;
    private JRFillVariable variable_TOT_IMP_TOTAL = null;


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
        parameter_REPORT_LOCALE = (JRFillParameter)pm.get("REPORT_LOCALE");
        parameter_REPORT_TIME_ZONE = (JRFillParameter)pm.get("REPORT_TIME_ZONE");
        parameter_REPORT_VIRTUALIZER = (JRFillParameter)pm.get("REPORT_VIRTUALIZER");
        parameter_REPORT_FILE_RESOLVER = (JRFillParameter)pm.get("REPORT_FILE_RESOLVER");
        parameter_REPORT_SCRIPTLET = (JRFillParameter)pm.get("REPORT_SCRIPTLET");
        parameter_REPORT_PARAMETERS_MAP = (JRFillParameter)pm.get("REPORT_PARAMETERS_MAP");
        parameter_REPORT_CONNECTION = (JRFillParameter)pm.get("REPORT_CONNECTION");
        parameter_P1 = (JRFillParameter)pm.get("P1");
        parameter_REPORT_CLASS_LOADER = (JRFillParameter)pm.get("REPORT_CLASS_LOADER");
        parameter_REPORT_DATA_SOURCE = (JRFillParameter)pm.get("REPORT_DATA_SOURCE");
        parameter_REPORT_URL_HANDLER_FACTORY = (JRFillParameter)pm.get("REPORT_URL_HANDLER_FACTORY");
        parameter_P2 = (JRFillParameter)pm.get("P2");
        parameter_IS_IGNORE_PAGINATION = (JRFillParameter)pm.get("IS_IGNORE_PAGINATION");
        parameter_REPORT_FORMAT_FACTORY = (JRFillParameter)pm.get("REPORT_FORMAT_FACTORY");
        parameter_REPORT_MAX_COUNT = (JRFillParameter)pm.get("REPORT_MAX_COUNT");
        parameter_REPORT_TEMPLATES = (JRFillParameter)pm.get("REPORT_TEMPLATES");
        parameter_REPORT_RESOURCE_BUNDLE = (JRFillParameter)pm.get("REPORT_RESOURCE_BUNDLE");
    }


    /**
     *
     */
    private void initFields(Map fm)
    {
        field_CD_ELEMENTO_01 = (JRFillField)fm.get("CD_ELEMENTO_01");
        field_INVERSION_EJE = (JRFillField)fm.get("INVERSION_EJE");
        field_IMP_TOTAL = (JRFillField)fm.get("IMP_TOTAL");
        field_DS_ELEMENTO_04 = (JRFillField)fm.get("DS_ELEMENTO_04");
        field_DS_ELEMENTO_02 = (JRFillField)fm.get("DS_ELEMENTO_02");
        field_DS_ELEMENTO_03 = (JRFillField)fm.get("DS_ELEMENTO_03");
        field_DS_ELEMENTO_01 = (JRFillField)fm.get("DS_ELEMENTO_01");
        field_CD_ELEMENTO_04 = (JRFillField)fm.get("CD_ELEMENTO_04");
        field_CD_ELEMENTO_02 = (JRFillField)fm.get("CD_ELEMENTO_02");
        field_MANT_EJE = (JRFillField)fm.get("MANT_EJE");
        field_CD_ELEMENTO_03 = (JRFillField)fm.get("CD_ELEMENTO_03");
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
        variable_Grupo_Programa_COUNT = (JRFillVariable)vm.get("Grupo_Programa_COUNT");
        variable_Grupo_Subprograma_COUNT = (JRFillVariable)vm.get("Grupo_Subprograma_COUNT");
        variable_Grupo_Espacio_COUNT = (JRFillVariable)vm.get("Grupo_Espacio_COUNT");
        variable_SUM_IMP_MANT_03 = (JRFillVariable)vm.get("SUM_IMP_MANT_03");
        variable_SUM_IMP_MANT_04 = (JRFillVariable)vm.get("SUM_IMP_MANT_04");
        variable_SUM_IMP_MANT_05 = (JRFillVariable)vm.get("SUM_IMP_MANT_05");
        variable_SUM_IMP_INV_03 = (JRFillVariable)vm.get("SUM_IMP_INV_03");
        variable_SUM_IMP_INV_04 = (JRFillVariable)vm.get("SUM_IMP_INV_04");
        variable_SUM_IMP_INV_05 = (JRFillVariable)vm.get("SUM_IMP_INV_05");
        variable_SUM_IMP_TOTAL_03 = (JRFillVariable)vm.get("SUM_IMP_TOTAL_03");
        variable_SUM_IMP_TOTAL_04 = (JRFillVariable)vm.get("SUM_IMP_TOTAL_04");
        variable_SUM_IMP_TOTAL_05 = (JRFillVariable)vm.get("SUM_IMP_TOTAL_05");
        variable_TOT_MANT_EJE = (JRFillVariable)vm.get("TOT_MANT_EJE");
        variable_TOT_INVERSION_EJE = (JRFillVariable)vm.get("TOT_INVERSION_EJE");
        variable_TOT_IMP_TOTAL = (JRFillVariable)vm.get("TOT_IMP_TOTAL");
    }


    /**
     *
     */
    public Object evaluate(int id) throws Throwable
    {
        Object value = null;

        switch (id)
        {
            case 0 : 
            {
                value = (java.lang.String)(new String("Admin"));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer("2006"));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_01.getValue()));//$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_01.getValue()));//$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_03.getValue()));//$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_03.getValue()));//$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_03.getValue()));//$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_02.getValue()));//$JR_EXPR_ID=33$
                break;
            }
            case 34 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_02.getValue()));//$JR_EXPR_ID=34$
                break;
            }
            case 35 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_04.getValue()));//$JR_EXPR_ID=35$
                break;
            }
            case 36 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_04.getValue()));//$JR_EXPR_ID=36$
                break;
            }
            case 37 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_04.getValue()));//$JR_EXPR_ID=37$
                break;
            }
            case 38 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_03.getValue()));//$JR_EXPR_ID=38$
                break;
            }
            case 39 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_03.getValue()));//$JR_EXPR_ID=39$
                break;
            }
            case 40 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_05.getValue()));//$JR_EXPR_ID=40$
                break;
            }
            case 41 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_05.getValue()));//$JR_EXPR_ID=41$
                break;
            }
            case 42 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_05.getValue()));//$JR_EXPR_ID=42$
                break;
            }
            case 43 : 
            {
                value = (java.lang.String)(java.util.Calendar.getInstance().get(java.util.Calendar.DATE)//$JR_EXPR_ID=43$
+ "/" +//$JR_EXPR_ID=43$
((java.util.Calendar.getInstance().get(java.util.Calendar.MONTH))+1)//$JR_EXPR_ID=43$
+ "/" +//$JR_EXPR_ID=43$
java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));//$JR_EXPR_ID=43$
                break;
            }
            case 44 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_04.getValue()));//$JR_EXPR_ID=44$
                break;
            }
            case 45 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=45$
                break;
            }
            case 46 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=46$
                break;
            }
            case 47 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=47$
                break;
            }
            case 48 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_MANT_EJE.getValue()));//$JR_EXPR_ID=48$
                break;
            }
            case 49 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_INVERSION_EJE.getValue()));//$JR_EXPR_ID=49$
                break;
            }
            case 50 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_IMP_TOTAL.getValue()));//$JR_EXPR_ID=50$
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
            case 0 : 
            {
                value = (java.lang.String)(new String("Admin"));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer("2006"));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getOldValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getOldValue()));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getOldValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getOldValue()));//$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getOldValue()));//$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getOldValue()));//$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getOldValue()));//$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getOldValue()));//$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getOldValue()));//$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getOldValue()));//$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getOldValue()));//$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getOldValue()));//$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_01.getOldValue()));//$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_01.getOldValue()));//$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_03.getOldValue()));//$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_03.getOldValue()));//$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_03.getOldValue()));//$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_02.getOldValue()));//$JR_EXPR_ID=33$
                break;
            }
            case 34 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_02.getOldValue()));//$JR_EXPR_ID=34$
                break;
            }
            case 35 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_04.getOldValue()));//$JR_EXPR_ID=35$
                break;
            }
            case 36 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_04.getOldValue()));//$JR_EXPR_ID=36$
                break;
            }
            case 37 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_04.getOldValue()));//$JR_EXPR_ID=37$
                break;
            }
            case 38 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_03.getOldValue()));//$JR_EXPR_ID=38$
                break;
            }
            case 39 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_03.getOldValue()));//$JR_EXPR_ID=39$
                break;
            }
            case 40 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_05.getOldValue()));//$JR_EXPR_ID=40$
                break;
            }
            case 41 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_05.getOldValue()));//$JR_EXPR_ID=41$
                break;
            }
            case 42 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_05.getOldValue()));//$JR_EXPR_ID=42$
                break;
            }
            case 43 : 
            {
                value = (java.lang.String)(java.util.Calendar.getInstance().get(java.util.Calendar.DATE)//$JR_EXPR_ID=43$
+ "/" +//$JR_EXPR_ID=43$
((java.util.Calendar.getInstance().get(java.util.Calendar.MONTH))+1)//$JR_EXPR_ID=43$
+ "/" +//$JR_EXPR_ID=43$
java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));//$JR_EXPR_ID=43$
                break;
            }
            case 44 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_04.getOldValue()));//$JR_EXPR_ID=44$
                break;
            }
            case 45 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getOldValue()));//$JR_EXPR_ID=45$
                break;
            }
            case 46 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getOldValue()));//$JR_EXPR_ID=46$
                break;
            }
            case 47 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getOldValue()));//$JR_EXPR_ID=47$
                break;
            }
            case 48 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_MANT_EJE.getOldValue()));//$JR_EXPR_ID=48$
                break;
            }
            case 49 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_INVERSION_EJE.getOldValue()));//$JR_EXPR_ID=49$
                break;
            }
            case 50 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_IMP_TOTAL.getOldValue()));//$JR_EXPR_ID=50$
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
            case 0 : 
            {
                value = (java.lang.String)(new String("Admin"));//$JR_EXPR_ID=0$
                break;
            }
            case 1 : 
            {
                value = (java.lang.Integer)(new Integer("2006"));//$JR_EXPR_ID=1$
                break;
            }
            case 2 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=2$
                break;
            }
            case 3 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=3$
                break;
            }
            case 4 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=4$
                break;
            }
            case 5 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=5$
                break;
            }
            case 6 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=6$
                break;
            }
            case 7 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=7$
                break;
            }
            case 8 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=8$
                break;
            }
            case 9 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=9$
                break;
            }
            case 10 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=10$
                break;
            }
            case 11 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=11$
                break;
            }
            case 12 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=12$
                break;
            }
            case 13 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=13$
                break;
            }
            case 14 : 
            {
                value = (java.lang.Integer)(new Integer(1));//$JR_EXPR_ID=14$
                break;
            }
            case 15 : 
            {
                value = (java.lang.Integer)(new Integer(0));//$JR_EXPR_ID=15$
                break;
            }
            case 16 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=16$
                break;
            }
            case 17 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=17$
                break;
            }
            case 18 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=18$
                break;
            }
            case 19 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=19$
                break;
            }
            case 20 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=20$
                break;
            }
            case 21 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=21$
                break;
            }
            case 22 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=22$
                break;
            }
            case 23 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=23$
                break;
            }
            case 24 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=24$
                break;
            }
            case 25 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=25$
                break;
            }
            case 26 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=26$
                break;
            }
            case 27 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=27$
                break;
            }
            case 28 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_01.getValue()));//$JR_EXPR_ID=28$
                break;
            }
            case 29 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_01.getValue()));//$JR_EXPR_ID=29$
                break;
            }
            case 30 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_03.getEstimatedValue()));//$JR_EXPR_ID=30$
                break;
            }
            case 31 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_03.getEstimatedValue()));//$JR_EXPR_ID=31$
                break;
            }
            case 32 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_03.getEstimatedValue()));//$JR_EXPR_ID=32$
                break;
            }
            case 33 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_02.getValue()));//$JR_EXPR_ID=33$
                break;
            }
            case 34 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_02.getValue()));//$JR_EXPR_ID=34$
                break;
            }
            case 35 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_04.getEstimatedValue()));//$JR_EXPR_ID=35$
                break;
            }
            case 36 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_04.getEstimatedValue()));//$JR_EXPR_ID=36$
                break;
            }
            case 37 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_04.getEstimatedValue()));//$JR_EXPR_ID=37$
                break;
            }
            case 38 : 
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_ELEMENTO_03.getValue()));//$JR_EXPR_ID=38$
                break;
            }
            case 39 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_03.getValue()));//$JR_EXPR_ID=39$
                break;
            }
            case 40 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_MANT_05.getEstimatedValue()));//$JR_EXPR_ID=40$
                break;
            }
            case 41 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_INV_05.getEstimatedValue()));//$JR_EXPR_ID=41$
                break;
            }
            case 42 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_SUM_IMP_TOTAL_05.getEstimatedValue()));//$JR_EXPR_ID=42$
                break;
            }
            case 43 : 
            {
                value = (java.lang.String)(java.util.Calendar.getInstance().get(java.util.Calendar.DATE)//$JR_EXPR_ID=43$
+ "/" +//$JR_EXPR_ID=43$
((java.util.Calendar.getInstance().get(java.util.Calendar.MONTH))+1)//$JR_EXPR_ID=43$
+ "/" +//$JR_EXPR_ID=43$
java.util.Calendar.getInstance().get(java.util.Calendar.YEAR));//$JR_EXPR_ID=43$
                break;
            }
            case 44 : 
            {
                value = (java.lang.String)(((java.lang.String)field_DS_ELEMENTO_04.getValue()));//$JR_EXPR_ID=44$
                break;
            }
            case 45 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_MANT_EJE.getValue()));//$JR_EXPR_ID=45$
                break;
            }
            case 46 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_INVERSION_EJE.getValue()));//$JR_EXPR_ID=46$
                break;
            }
            case 47 : 
            {
                value = (java.lang.Double)(((java.lang.Double)field_IMP_TOTAL.getValue()));//$JR_EXPR_ID=47$
                break;
            }
            case 48 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_MANT_EJE.getEstimatedValue()));//$JR_EXPR_ID=48$
                break;
            }
            case 49 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_INVERSION_EJE.getEstimatedValue()));//$JR_EXPR_ID=49$
                break;
            }
            case 50 : 
            {
                value = (java.lang.Double)(((java.lang.Double)variable_TOT_IMP_TOTAL.getEstimatedValue()));//$JR_EXPR_ID=50$
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}
