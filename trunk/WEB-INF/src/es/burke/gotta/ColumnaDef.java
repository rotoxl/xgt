package es.burke.gotta;

public final class ColumnaDef { 
	private String cd;
	private	String ds;
	private	String tipo;
	private	int longitud;
	private String subtipo;
	
	public ColumnaDef(String cd, String ds, String tipo, int longitud, String subtipo) { 
		this.cd=cd;
		this.ds=ds;
		this.tipo=tipo.toLowerCase();
		this.longitud=longitud;
		this.subtipo=subtipo;
		}

	public String getCd() {return cd;}
	public String getDs() {return ds;}

	public String getTipo() {return tipo;}
	public String getSubtipo() {return subtipo;}
	public int getLongitud() {return this.longitud;}

	@Override
	public String toString() {return "Columna "+this.cd+ " (" + this.tipo + ")";}

	public void setTipo(String nt){
		this.tipo=nt;
		}
}
