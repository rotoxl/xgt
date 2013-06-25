package es.burke.gotta;

import java.util.List;

public class TablaTemporal extends ITabla {

	public TablaTemporal(ITablaDef tabla, Usuario usuario) throws ErrorUsuarioNoValido {
		super(tabla, usuario);
		}

	@Override
	Filas cargarRegistrosPrimitivo(CampoDef campo, List<Object> valor, CampoDef orderBy) throws ErrorCargandoTabla {
		return null;
		}

}
