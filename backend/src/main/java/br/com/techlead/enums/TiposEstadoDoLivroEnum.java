package br.com.techlead.enums;
public enum TiposEstadoDoLivroEnum {
    PERDIDO("Perdido"),
    DANIFICADO("Danificado"),
    BOM("Bom");

    private String estado;

    TiposEstadoDoLivroEnum(String estado) {
        this.estado = estado;
    }

    public String getEstado() {
        return estado;
    }

}