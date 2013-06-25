package es.burke.gotta;

import java.util.ArrayList;

public class TablaTemporalDef extends ITablaDef {
	ITabla tabla;
	public TablaTemporalDef(String cd, String ds, ArrayList<ColumnaDef> cols){
		this.cd=cd;
		this.ds=ds;
		
		for (ColumnaDef col: cols)
			this.putColumna(col.getCd(), col);
		this.generaCamposDesdeColumnas();
		}
	public void generaCamposDesdeColumnas(){
		for (ColumnaDef col: this.columnas.values()) {
			CampoDef cam=new CampoDef(col.getCd(), col.getCd(), this.cd);
			cam.putColumna(col);
			
			this.putCampo(col.getCd(), cam);
			}
		}
	
	public TablaTemporalDef(String cd, String ds){
		this.cd=cd;
		this.ds=ds;
		}
	
	@Override
	public ITabla newTabla(Usuario usr) throws ErrorCreandoTabla {
		return new TablaTemporal(this, usr);
		}

}