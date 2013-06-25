/*
 * Generated by JasperReports - 25/11/05 14:21
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
public class rpt_EstMesEjer49 extends JRCalculator
{


    /**
     *
     */
    private JRFillParameter parameter_REPORT_RESOURCE_BUNDLE = null;
    private JRFillParameter parameter_P1 = null;
    private JRFillParameter parameter_REPORT_CONNECTION = null;
    private JRFillParameter parameter_REPORT_PARAMETERS_MAP = null;
    private JRFillParameter parameter_IS_IGNORE_PAGINATION = null;
    private JRFillParameter parameter_P2 = null;
    private JRFillParameter parameter_REPORT_VIRTUALIZER = null;
    private JRFillParameter parameter_REPORT_LOCALE = null;
    private JRFillParameter parameter_REPORT_DATA_SOURCE = null;
    private JRFillParameter parameter_REPORT_SCRIPTLET = null;
    private JRFillParameter parameter_REPORT_MAX_COUNT = null;

    private JRFillField field_CD_MES = null;
    private JRFillField field_MES = null;
    private JRFillField field_SALIDAS = null;
    private JRFillField field_CODIGO = null;
    private JRFillField field_MOTIVO = null;
    private JRFillField field_DIA = null;
    private JRFillField field_EJERCICIO = null;

    private JRFillVariable variable_PAGE_NUMBER = null;
    private JRFillVariable variable_COLUMN_NUMBER = null;
    private JRFillVariable variable_REPORT_COUNT = null;
    private JRFillVariable variable_PAGE_COUNT = null;
    private JRFillVariable variable_COLUMN_COUNT = null;
    private JRFillVariable variable_SALIDASA�O = null;
    private JRFillVariable variable_SALIDASMOTIVO = null;
    private JRFillVariable variable_SALIDASMES = null;
    private JRFillVariable variable_EJERCICIO_COUNT = null;
    private JRFillVariable variable_MES_COUNT = null;
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
        parameter_P2 = (JRFillParameter)pm.get("P2");
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
        field_CD_MES = (JRFillField)fm.get("CD_MES");
        field_MES = (JRFillField)fm.get("MES");
        field_SALIDAS = (JRFillField)fm.get("SALIDAS");
        field_CODIGO = (JRFillField)fm.get("CODIGO");
        field_MOTIVO = (JRFillField)fm.get("MOTIVO");
        field_DIA = (JRFillField)fm.get("DIA");
        field_EJERCICIO = (JRFillField)fm.get("EJERCICIO");
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
        variable_SALIDASA�O = (JRFillVariable)vm.get("SALIDASA�O");
        variable_SALIDASMOTIVO = (JRFillVariable)vm.get("SALIDASMOTIVO");
        variable_SALIDASMES = (JRFillVariable)vm.get("SALIDASMES");
        variable_EJERCICIO_COUNT = (JRFillVariable)vm.get("EJERCICIO_COUNT");
        variable_MES_COUNT = (JRFillVariable)vm.get("MES_COUNT");
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
            case 1346 : // 1346
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1370 : // 1370
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 1348 : // 1348
            {
                value = (java.lang.Integer)(new Integer("2050"));
                break;
            }
            case 1360 : // 1360
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_MES.getValue()));
                break;
            }
            case 1356 : // 1356
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASA�O.getValue()));
                break;
            }
            case 1368 : // 1368
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1351 : // 1351
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1343 : // 1343
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1358 : // 1358
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1361 : // 1361
            {
                value = (java.lang.String)(((java.lang.String)field_MES.getValue())+" "+((java.lang.String)field_EJERCICIO.getValue()));
                break;
            }
            case 1363 : // 1363
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1364 : // 1364
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1345 : // 1345
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1353 : // 1353
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1354 : // 1354
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1352 : // 1352
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1367 : // 1367
            {
                value = (java.lang.String)(((java.lang.String)field_MOTIVO.getValue()));
                break;
            }
            case 1371 : // 1371
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getValue()));
                break;
            }
            case 1350 : // 1350
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1341 : // 1341
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1344 : // 1344
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1355 : // 1355
            {
                value = (java.lang.Object)(((java.lang.String)field_EJERCICIO.getValue()));
                break;
            }
            case 1369 : // 1369
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1349 : // 1349
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1362 : // 1362
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMES.getValue()));
                break;
            }
            case 1342 : // 1342
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1339 : // 1339
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1340 : // 1340
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1357 : // 1357
            {
                value = (java.lang.String)(((java.lang.String)field_EJERCICIO.getValue()));
                break;
            }
            case 1366 : // 1366
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMOTIVO.getValue()));
                break;
            }
            case 1359 : // 1359
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1365 : // 1365
            {
                value = (java.lang.Object)(((java.lang.String)field_MOTIVO.getValue()));
                break;
            }
            case 1347 : // 1347
            {
                value = (java.lang.Integer)(new Integer("1950"));
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
            case 1346 : // 1346
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1370 : // 1370
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 1348 : // 1348
            {
                value = (java.lang.Integer)(new Integer("2050"));
                break;
            }
            case 1360 : // 1360
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_MES.getOldValue()));
                break;
            }
            case 1356 : // 1356
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASA�O.getOldValue()));
                break;
            }
            case 1368 : // 1368
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1351 : // 1351
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getOldValue()));
                break;
            }
            case 1343 : // 1343
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1358 : // 1358
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1361 : // 1361
            {
                value = (java.lang.String)(((java.lang.String)field_MES.getOldValue())+" "+((java.lang.String)field_EJERCICIO.getOldValue()));
                break;
            }
            case 1363 : // 1363
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1364 : // 1364
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1345 : // 1345
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1353 : // 1353
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getOldValue()));
                break;
            }
            case 1354 : // 1354
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1352 : // 1352
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1367 : // 1367
            {
                value = (java.lang.String)(((java.lang.String)field_MOTIVO.getOldValue()));
                break;
            }
            case 1371 : // 1371
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getOldValue()));
                break;
            }
            case 1350 : // 1350
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1341 : // 1341
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1344 : // 1344
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1355 : // 1355
            {
                value = (java.lang.Object)(((java.lang.String)field_EJERCICIO.getOldValue()));
                break;
            }
            case 1369 : // 1369
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1349 : // 1349
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getOldValue()));
                break;
            }
            case 1362 : // 1362
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMES.getOldValue()));
                break;
            }
            case 1342 : // 1342
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1339 : // 1339
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1340 : // 1340
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1357 : // 1357
            {
                value = (java.lang.String)(((java.lang.String)field_EJERCICIO.getOldValue()));
                break;
            }
            case 1366 : // 1366
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMOTIVO.getOldValue()));
                break;
            }
            case 1359 : // 1359
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1365 : // 1365
            {
                value = (java.lang.Object)(((java.lang.String)field_MOTIVO.getOldValue()));
                break;
            }
            case 1347 : // 1347
            {
                value = (java.lang.Integer)(new Integer("1950"));
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
            case 1346 : // 1346
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1370 : // 1370
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 1348 : // 1348
            {
                value = (java.lang.Integer)(new Integer("2050"));
                break;
            }
            case 1360 : // 1360
            {
                value = (java.lang.Object)(((java.lang.String)field_CD_MES.getValue()));
                break;
            }
            case 1356 : // 1356
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASA�O.getEstimatedValue()));
                break;
            }
            case 1368 : // 1368
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1351 : // 1351
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1343 : // 1343
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1358 : // 1358
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1361 : // 1361
            {
                value = (java.lang.String)(((java.lang.String)field_MES.getValue())+" "+((java.lang.String)field_EJERCICIO.getValue()));
                break;
            }
            case 1363 : // 1363
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1364 : // 1364
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1345 : // 1345
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1353 : // 1353
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1354 : // 1354
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1352 : // 1352
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1367 : // 1367
            {
                value = (java.lang.String)(((java.lang.String)field_MOTIVO.getValue()));
                break;
            }
            case 1371 : // 1371
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_PAGE_NUMBER.getEstimatedValue()));
                break;
            }
            case 1350 : // 1350
            {
                value = (java.lang.Integer)(new Integer("0"));
                break;
            }
            case 1341 : // 1341
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1344 : // 1344
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1355 : // 1355
            {
                value = (java.lang.Object)(((java.lang.String)field_EJERCICIO.getValue()));
                break;
            }
            case 1369 : // 1369
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1349 : // 1349
            {
                value = (java.lang.Integer)(((java.lang.Integer)field_SALIDAS.getValue()));
                break;
            }
            case 1362 : // 1362
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMES.getEstimatedValue()));
                break;
            }
            case 1342 : // 1342
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1339 : // 1339
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1340 : // 1340
            {
                value = (java.lang.Integer)(new Integer(1));
                break;
            }
            case 1357 : // 1357
            {
                value = (java.lang.String)(((java.lang.String)field_EJERCICIO.getValue()));
                break;
            }
            case 1366 : // 1366
            {
                value = (java.lang.Integer)(((java.lang.Integer)variable_SALIDASMOTIVO.getEstimatedValue()));
                break;
            }
            case 1359 : // 1359
            {
                value = (java.lang.Integer)(new Integer(0));
                break;
            }
            case 1365 : // 1365
            {
                value = (java.lang.Object)(((java.lang.String)field_MOTIVO.getValue()));
                break;
            }
            case 1347 : // 1347
            {
                value = (java.lang.Integer)(new Integer("1950"));
                break;
            }
           default :
           {
           }
        }
        
        return value;
    }


}