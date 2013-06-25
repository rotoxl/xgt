package es.burke.gotta;

public class NivelDef extends INivelDef {
	public	String sql; //sentencia sql asociada al nivel, suelen ser consultas tipo APP_a

	public NivelDef(String cd, String sql, String parametros) { 
		this._cd=cd;
		this.sql=sql;
		rellenaColParametros(parametros);
		}
	@Override
	public String getTexto() {
		return this.sql;
		}
	@Override
	public INivel obtenerNivel() {
		return new Nivel(this);
		}
}

