package es.burke.gotta;

import java.util.List;

public class Tabla extends ITabla {
	
	protected Tabla(ITablaDef tabla, Usuario usuario) throws ErrorUsuarioNoValido {
		super(tabla, usuario);
		}
	
	public TablaDef getTablaDef(){
		return (TablaDef)tdef;
		}


	@Override
	Filas cargarRegistrosPrimitivo(CampoDef campo, List<Object> valor, CampoDef orderBy) throws ErrorCargandoTabla{
		try {
			String sql = getTablaDef().sqlCargarTabla(campo, valor, orderBy);

			//si cargamos por lectura1 y sobran interrogantes se rellenan con el usuario
			if (getTablaDef().getLectura1()!=null) {
				int numInterrogantes=Util.cuentaCadena(sql, "?");
				for (int i=valor.size(); i<numInterrogantes; i++) 
					valor.add(this.usuario.getLogin());
				}

			return this.usuario.getConexion().lookUp(sql, valor);
			}
		catch (Exception e){
			throw new ErrorCargandoTabla(tdef.cd+Constantes.ESPACIO,e);
			}
		}
}
