package br.com.techlead.enums;

public enum StatusSolicitacaoEmprestimoEnum {
    PENDENTE("Pendente"),
    APROVADO("Aprovado"),
    RECUSADO("Recusado");

    private String descricao;

    StatusSolicitacaoEmprestimoEnum(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
