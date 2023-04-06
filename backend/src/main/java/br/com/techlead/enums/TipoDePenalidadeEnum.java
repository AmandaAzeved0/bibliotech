package br.com.techlead.enums;

public enum TipoDePenalidadeEnum {
   INFERIOR_A_DEZ_DIAS(1,"Atraso inferior a 10 dias"),
   SUPERIOR_A_DEZ_DIAS(2,"Atraso superior a 10 dias e menor que 20"),
   SUPERIOR_A_VINTE_DIAS(3,"Atraso superior a 20 dias e menor que 30"),
   SUPERIOR_A_TRINTA_DIAS(4,"Atraso superior a 30 dias e inferior a 40"),
   SUPERIOR_A_QUARENTA_DIAS(5,"Atraso superior a 40 dias"),
   PERDA_OU_DANO(6,"Perda ou dano do livro");
    private int codigo;
    private String descricao;

    TipoDePenalidadeEnum(int codigo, String descricao) {
        this.codigo = codigo;
        this.descricao = descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public String getDescricao() {
        return descricao;
    }

    public static TipoDePenalidadeEnum obterPenalidadePorDiasDeAtraso(int diasAtraso) {
        for (TipoDePenalidadeEnum tipo : TipoDePenalidadeEnum.values()) {
            if (tipo.penalidadeAplicavel(diasAtraso)) {
                return tipo;
            }
        }
        return null;
    }


    public boolean penalidadeAplicavel(int diasAtraso) {
        if (this == PERDA_OU_DANO) {
            return false; // essa penalidade não depende dos dias de atraso
        }
        else if (this == INFERIOR_A_DEZ_DIAS) {
            return diasAtraso >= 0 && diasAtraso < 10;
        }
        else if (this == SUPERIOR_A_DEZ_DIAS) {
            return diasAtraso >= 10 && diasAtraso < 20;
        }
        else if (this == SUPERIOR_A_VINTE_DIAS) {
            return diasAtraso >= 20 && diasAtraso < 30;
        }
        else if (this == SUPERIOR_A_TRINTA_DIAS) {
            return diasAtraso >= 30 && diasAtraso < 40;
        }
        else if (this == SUPERIOR_A_QUARENTA_DIAS) {
            return diasAtraso >= 40;
        }
        return false; // caso padrão
    }

}
