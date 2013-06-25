import es.burke.gotta as gt
from java.sql import SQLException

accionesValidas='registro eliminar editar modificar'
def ejecuta(mMotor, accion, valor):
	td=mMotor.tramActivo().tb
	if not td:
		raise gt.ErrorAccionDLL('Para utilizar el mantenimiento de tablas es necesario que indiques una tabla base en el camino.')
	nombreTabla=td.getValorCol('nombre')
	tabla = mMotor.ponTabla(nombreTabla)
	try:
		if accion.lower()=='registro':
			return registro(mMotor, tabla)
		if accion.lower()=='eliminar':
			return eliminar(mMotor,tabla, valor)
		if accion.lower() in ('editar', 'modificar'):
			return editar(mMotor, tabla, valor)

	except SQLException, e:
		raise ErrorGotta(e)
	
def eliminar(mMotor,tabla, valor):
	tabla.cargarRegistros(None, valor[1:].split(gt.Constantes.PUNTO3))
	tabla.delete()
	return gt.Motor.Resultado.OK

def registro(mMotor, tabla):
	tabla.addNew()
	return montaFRM(mMotor, tabla.tdef)
		
def editar(mMotor,tabla, valor):
	tabla.cargarRegistros(None, valor[1:].split(gt.Constantes.PUNTO3))
	return montaFRM(mMotor, tabla.tdef)
	
def montaFRM(mMotor, tdef): 
		mMotor.accionFRM('principio', 'Mantenimiento de %s' %(tdef.ds,))
		
		columnas=tdef.columnas.claves
		if isinstance(tdef, gt.TablaDef):
			columnas=tdef.columnasFisicas(mMotor.usuario.conexion)
		for col in columnas:
			colDef=tdef.getColumna(col)
			ds=colDef.ds or colDef.cd
			
			asteriscoTablaCampo='*.'+tdef.cd+'.'+col
			if tdef.campoClave.getColumna(col):
				if tdef.campoClave.getColumna(col).tipo==gt.Constantes.AUTO:
					pass
				else:
					mMotor.presentarPedirCampo('X!?', asteriscoTablaCampo, ds, '', None)
				
			else:
				mMotor.presentarPedirCampo('X!?', asteriscoTablaCampo, ds, '*', None)
			
		return mMotor.accionFRM('fin', None)
	
def verifica(motor, accion, nombre):
	assert accion.lower() in accionesValidas.split()
