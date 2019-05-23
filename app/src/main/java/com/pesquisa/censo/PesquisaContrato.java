package com.pesquisa.censo;

import android.provider.BaseColumns;

public final class PesquisaContrato {

    private PesquisaContrato() {
    }


    public static class PerguntasTable implements BaseColumns {
        public static final String TABLE_NAME = "Pesquisa_Perguntas";
        public static final String COLUMN_PERGUNTA = "pergunta";
        public static final String COLUMN_OPCAO1 = "opcao1";
        public static final String COLUMN_OPCAO2 = "opcao2";
        public static final String COLUMN_OPCAO3 = "opcao3";
        public static final String COLUMN_ANSWER_NR = "answer_nr";
    }
}