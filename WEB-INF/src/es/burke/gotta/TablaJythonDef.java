package es.burke.gotta;

public class TablaJythonDef extends TablaDef {

	public TablaJythonDef(String cd, String ds, String lectura1, String lecturaN, String escritura, String clave, String usp, Integer caminoMantenimiento, String colDesc, String colBúsq) {
		super(cd, ds, lectura1, lecturaN, escritura, clave, usp, caminoMantenimiento, colDesc, colBúsq);
		}
	@Override
	public ITabla newTabla(Usuario usr) throws ErrorUsuarioNoValido {
		return new TablaJython(this,usr);
		}

}
