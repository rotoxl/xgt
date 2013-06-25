package es.burke.gotta;

import java.util.List;

public class TablaJython extends Tabla {
	public TablaJython(ITablaDef tabla, Usuario usuario) throws ErrorUsuarioNoValido {
		super(tabla, usuario);
		}
	@Override
	Filas cargarRegistrosPrimitivo(CampoDef campo, List<Object> valor, CampoDef orderBy) throws ErrorCargandoTabla {
		INivelDef nd;
		try {
			Aplicacion apli = this.usuario.getApli();
			TablaDef tablaDef = this.getTablaDef();
			INivelDef nivelLectura1 = tablaDef.getNivelLectura1(this.usuario);
			if (nivelLectura1!=null && campo==tablaDef.getCampoClave())
				nd=nivelLectura1;
			else
				nd = apli.getNivelDef(tablaDef.getLecturaN());
			INivel n = nd.obtenerNivel();
			Filas ret = n.lookUp(this.usuario.getConexion(), valor);
			return ret;
			}
		catch (Exception e) {
			throw new ErrorCargandoTabla("Error al ejecutar nivel jython para cargar tabla"+this.tdef.cd, e);
			}
		}
}
