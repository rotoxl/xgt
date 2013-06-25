package es.burke.gotta;

enum TipoDato {
	NombreTramite,
	TituloFormulario,
	Dato,
	Separador
}
public class DatoFirmado {
	private TipoDato tipo;
	private String texto;
	private String valor;
	
	@Override
	public String toString(){
		if (tipo==TipoDato.Separador){
			return "=== "+this.getTexto()+" ===" + Constantes.vbCrLf;
		}
		else if (tipo==TipoDato.Dato){
			return this.getTexto()+": "+ getValor() + Constantes.vbCrLf;
		}
		else if (tipo==TipoDato.TituloFormulario){
			return "== "+this.getTexto()+" ==" + Constantes.vbCrLf;
		}
		else if (tipo==TipoDato.NombreTramite){
			return "= "+this.getTexto()+" =" + Constantes.vbCrLf;
		}
		throw new java.lang.AssertionError();
	}

	void setTipo(TipoDato tipo) {
		this.tipo = tipo;
	}
	public String getStrTipo() {
		return this.tipo.toString();
	}
	void setTexto(String texto) {
		this.texto = texto;
	}
	public String getTexto() {
		return texto;
	}
	void setValor(String valor) {
		this.valor = valor;
	}
	public String getValor() {
		return valor;
	}
}
