package br.com.tecgosorteios.tecgosorteios.model;

public enum EnumStatus {
	DISPONÍVEL, RESERVADO, PAGO;
	
	@Override
	public String toString() {
		return Character.toUpperCase(name().charAt(0)) + name().toLowerCase().substring(1);
	}
}
