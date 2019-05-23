package com.pesquisa.censo;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.pesquisa.censo.PesquisaContrato.*;

import java.util.ArrayList;
import java.util.List;


public class PesquisaDbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "MinhaPesquisa.db";
    private static final int DATABASE_VERSION = 1;

    private static PesquisaDbHelper instance;

    private SQLiteDatabase db;

    private PesquisaDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized PesquisaDbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new PesquisaDbHelper(context.getApplicationContext());
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        this.db = db;


        final String SQL_CREATE_PerguntaS_TABLE = "CREATE TABLE " +
                PerguntasTable.TABLE_NAME + " ( " +
                PerguntasTable._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                PerguntasTable.COLUMN_PERGUNTA + " TEXT, " +
                PerguntasTable.COLUMN_OPCAO1 + " TEXT, " +
                PerguntasTable.COLUMN_OPCAO2 + " TEXT, " +
                PerguntasTable.COLUMN_OPCAO3 + " TEXT, " +
                PerguntasTable.COLUMN_ANSWER_NR + " INTEGER " +
                   ")";

        db.execSQL(SQL_CREATE_PerguntaS_TABLE);
        fillPerguntasTable();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + PerguntasTable.TABLE_NAME);
        onCreate(db);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true);
    }

    private void fillPerguntasTable() {
        Pergunta q1 = new Pergunta("ESPÉCIES DE DOMICÍLIO OCUPADO","DOMICÍLIO PARTICULAR PERMANENTE OCUPADO","DOMICÍLIO PARTICULAR IMPROVISADO OCUPADO","DOMICÍLIO COLETIVO COM MORADOR",1);
        insertPergunta(q1);

        Pergunta q2 = new Pergunta("TIPO","CASA DE VILA OU EM CONDOMÍNIO","APARTAMENTO","OUTRO",2);
        insertPergunta(q2);

        Pergunta q3 = new Pergunta("ESTE DOMICÍLIO É:","PRÓPRIO DE ALGUM MORADOR","ALUGADO","CEDIDO",3);
        insertPergunta(q3);

        Pergunta q4 = new Pergunta("QUAL A QUANTIDADE DE MORADORES?","1","2 OU 3","4 OU MAIS",4);
        insertPergunta(q4);

        Pergunta q5 = new Pergunta("QUANTOS BANHEIROS DE USO EXCLUSIVO DOS MORADORES EXISTEM NESTE DOMICÍLIO?","1","2","3 OU MAIS",5); insertPergunta(q5);

        Pergunta q6 = new Pergunta("O ESGOTO DO BANHEIRO OU SANITÁRIO É LANÇADO (JOGADO) EM:","REDE GERAL DE ESGOTO OU PLUVIAL","FOSSA SÉPTICA OU FOSSA RUDIMENTAR","OUTRO",6);
        insertPergunta(q6);

        Pergunta q7 = new Pergunta("A FORMA DE ABASTECIMENTO DE ÁGUA UTILIZADA NESTE DOMICÍLIO É:","REDE GERAL DE DISTRIBUIÇÃO","POÇO","OUTRO",7);
        insertPergunta(q7);

        Pergunta q8 = new Pergunta("O LIXO DESTE DOMICÍLIO É:","COLETADO DIRETAMENTE POR SERVIÇO DE LIMPEZA","COLOCADO EM CAÇAMBA DE SERVIÇO DE LIMPEZA","JOGADO EM TERRENO BALDIO OU LOGRADOURO",8);
        insertPergunta(q8);

        Pergunta q9 = new Pergunta("EXISTE ENERGIA ELÉTRICA NO DOMICÍLIO?","SIM, DE USO EXCLUSIVO","SIM, DE USO COMUM","NÃO TEM MEDIDOR OU RELÓGIO",9);
        insertPergunta(q9);

        Pergunta q10 = new Pergunta("SEXO","MASCULINO","FEMININO","OUTRA",10);
        insertPergunta(q10);

        Pergunta q11 = new Pergunta("IDADE","18 A 25 ANOS","25 A 50 ANOS","MAIS DE 50 ANOS",11);
        insertPergunta(q11);

        Pergunta q12 = new Pergunta("A SUA COR OU RAÇA É:","BRANCA","NEGRA","OUTRA",12);
        insertPergunta(q12);

        Pergunta q13 = new Pergunta("QUAL É O GRAU DE ALFABETIZAÇÃO?","ENSINO FUNDAMENTAL/ENSINO MÉDIO","ENSINO SUPERIOR","NÃO ALFABETIZADO",13);
        insertPergunta(q13);

        Pergunta q14 = new Pergunta("QUAL A RENDA FAMILIAR?","UM SALÁRIO MINIMO","MAIOR QUE SALARIO MINIMO","SEM RENDA(DESEMPREGADOS)",14);
        insertPergunta(q14);

    }

    public void addPergunta(Pergunta Pergunta) {
        db = getWritableDatabase();
        insertPergunta(Pergunta);
    }

    public void addPerguntas(List<Pergunta> Perguntas) {
        db = getWritableDatabase();

        for (Pergunta Pergunta : Perguntas) {
            insertPergunta(Pergunta);
        }
    }

    private void insertPergunta(Pergunta Pergunta) {
        ContentValues cv = new ContentValues();
        cv.put(PerguntasTable.COLUMN_PERGUNTA, Pergunta.getPergunta());
        cv.put(PerguntasTable.COLUMN_OPCAO1, Pergunta.getOpcao1());
        cv.put(PerguntasTable.COLUMN_OPCAO2, Pergunta.getOpcao2());
        cv.put(PerguntasTable.COLUMN_OPCAO3, Pergunta.getOpcao3());
        cv.put(PerguntasTable.COLUMN_ANSWER_NR, Pergunta.getAnswerNr());
        db.insert(PerguntasTable.TABLE_NAME, null, cv);
    }



    public ArrayList<Pergunta> getTodasPerguntas() {
        ArrayList<Pergunta> PerguntaList = new ArrayList<>();
        db = getReadableDatabase();
        Cursor c = db.rawQuery("SELECT * FROM " + PerguntasTable.TABLE_NAME, null);

        if (c.moveToFirst()) {
            do {
                Pergunta Pergunta = new Pergunta();
                Pergunta.setId(c.getInt(c.getColumnIndex(PerguntasTable._ID)));
                Pergunta.setPergunta(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_PERGUNTA)));
                Pergunta.setOpcao1(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_OPCAO1)));
                Pergunta.setOpcao2(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_OPCAO2)));
                Pergunta.setOpcao3(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_OPCAO3)));
                Pergunta.setAnswerNr(c.getInt(c.getColumnIndex(PerguntasTable.COLUMN_ANSWER_NR)));
                PerguntaList.add(Pergunta);
            } while (c.moveToNext());
        }

        c.close();
        return PerguntaList;
    }

    public ArrayList<Pergunta> getPerguntas() {
        ArrayList<Pergunta> PerguntaList = new ArrayList<>();
        db = getReadableDatabase();


        Cursor c = db.query(
                PerguntasTable.TABLE_NAME,
                null,
                null,
                null,
                null,
                null,
                null
        );

        if (c.moveToFirst()) {
            do {
                Pergunta Pergunta = new Pergunta();
                Pergunta.setId(c.getInt(c.getColumnIndex(PerguntasTable._ID)));
                Pergunta.setPergunta(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_PERGUNTA)));
                Pergunta.setOpcao1(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_OPCAO1)));
                Pergunta.setOpcao2(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_OPCAO2)));
                Pergunta.setOpcao3(c.getString(c.getColumnIndex(PerguntasTable.COLUMN_OPCAO3)));
                Pergunta.setAnswerNr(c.getInt(c.getColumnIndex(PerguntasTable.COLUMN_ANSWER_NR)));
                PerguntaList.add(Pergunta);
            } while (c.moveToNext());
        }

        c.close();
        return PerguntaList;
    }
}
