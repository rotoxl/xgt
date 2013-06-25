#!jython
# coding: utf-8 -*-.
from java.io import File
from java.util import Date
from com.healthmarketscience.jackcess import TableBuilder, ColumnBuilder, Database, DataType
from es.burke.gotta import Fila, Filas, ITablaDef, ITabla, ColumnaDef, CampoDef, FechaGotta
conversionTipos = {
    DataType.SHORT_DATE_TIME:'date',
    DataType.MEMO:'memo',
    DataType.LONG:'long',
    DataType.DOUBLE:'double',
    DataType.INT:'integer',
    DataType.TEXT:'string',
    DataType.NUMERIC:'currency',
    }

def exportarAccess(nombreMDB, nombreTabla, columnas, datos):                                                                                                                                                                                                    
    mdb = File(nombreMDB)                                                                                                                                                                                                                                       
    if not mdb.exists():                                                                                                                                                                                                                                        
        db = Database.create(mdb, False)                                                                                                                                                                                                                        
    else:                                                                                                                                                                                                                                                       
        db = Database.open(mdb, False, False)                                                                                                                                                                                                                   
    tb = TableBuilder(nombreTabla)                                                                                                                                                                                                                              
    for ncol, tipo in columnas:                                                                                                                                                                                                                                 
        cb = ColumnBuilder(ncol, tipo)                                                                                                                                                                                                                           
        tb.addColumn(cb.toColumn())                                                                                                                                                                                                                             
    newTable = tb.toTable(db)                                                                                                                                                                                                                                   
    for d in datos:                                                                                                                                                                                                                                             
        newTable.addRow([adapta(i) for i in d])                                                                                                                                                                                                                 
    db.close()                                                                                                                                                                                                                                                  
def mapear(j):                                                                                                                                                                                                                                                  
    if j == 'java.math.BigDecimal':                                                                                                                                                                                                                               
        return DataType.NUMERIC                                                                                                                                                                                                                                 
    elif j == 'java.lang.String':                                                                                                                                                                                                                                 
        return DataType.MEMO                                                                                                                                                                                                                                    
    elif j == 'java.sql.Timestamp':                                                                                                                                                                                                                               
        return DataType.SHORT_DATE_TIME                                                                                                                                                                                                                         
    else:                                                                                                                                                                                                                                                       
        return DataType.MEMO                                                                                                                                                                                                                                    

def adapta(i):                                                                                                                                                                                                                                                  
	if type(i) is FechaGotta:                                                                                                                                                                                                                                     
		return i.timeInMillis                                                                                                                                                                                                                                       
	return i                                                                                                                                                                                                                                                      

def verifica(motor, accion, parametro):
    assert accion in ['exportar', 'importar']
def ejecuta(motor, accion, parametro):
    if accion == 'exportar':
        nombreMDB, nombreTabla, nombreNivel = parametro.split()
        nivelDef = motor.apli.getNivelDef(nombreNivel)
        nivel = nivelDef.obtenerNivel()
        params = [motor.lote.getVariable(p.nombre).valor for p in nivelDef.colParams]
        filas = nivel.lookUp(motor.usuario.conexion, params)
        columnas=zip(filas.orden, [(mapear(t)) for t in filas.tipos])
        datos = [f.toList().toArray() for f in filas]
        exportarAccess(nombreMDB, nombreTabla, columnas, datos)
    elif accion == 'importar':
        importarAccess(parametro, motor)

class TablaAccess(ITabla):
    pass

class TablaDefAccess(ITablaDef):
    def __init__(self, cd):
        self.cd = self.ds = cd

def importarAccess(fn, motor):
    fich = File(fn)
    db = Database.open(fich)

    for tbn in db.tableNames:
        tb = db.getTable(tbn)
        tbdg = TablaDefAccess('#' + limpia(tbn))
        tipos = []
        for col in tb.columns:
            colName = limpia(col.name)
            col = ColumnaDef(colName, colName, conversionTipos[col.type], 1000, None)
            tbdg.putColumna(colName, col)
            tipos.append(col.tipo)
            cam = CampoDef(colName, colName, tbdg.cd)
            cam.putColumna(col)
            tbdg.putCampo(cam.cd, cam)
        filas = Filas(tbdg.columnas.orden, tipos)
        for f in tb:
            fila = Fila(filas)
            for col in tb.columns:
                colName = limpia(col.name)
                dato = f[col.name]
                if isinstance(dato, Date):
                    dato = FechaGotta.comoFechaGotta(dato)
                fila.put(colName, dato)
            filas.add(fila)
        tbg = TablaAccess(tbdg, motor.usuario)
        tbg.datos = filas
        tbg.registroActual = 0
        tbg.untouch()
        motor.tablas.put(tbdg.cd, tbg)
def limpia(nombre):
        return nombre.replace('.', '_').replace(' ', '_').replace('-', '_').replace(u'º', '_').replace(u'/', '_')

