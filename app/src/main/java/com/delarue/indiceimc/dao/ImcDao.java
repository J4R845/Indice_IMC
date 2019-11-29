package com.delarue.indiceimc.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.delarue.indiceimc.model.Imc;

import java.util.ArrayList;

public class ImcDao extends SQLiteOpenHelper {

    private static final String NOME_BANCO = "DBImc.db";
    private static final int VERSION = 1;
    private static final String TABELA = "imc";
    //Dados a armazenar
    private static final String ID = "id";
    private static final String DATAREG = "datareg";
    private static final String PESO = "peso";
    private static final String ALTURA = "altura";
    private static final String RESULTADO = "resultado";
    private static final String DIAGNOSTICO = "diagnostico";


    public ImcDao(Context context) {

        super(context, NOME_BANCO, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sql = "CREATE TABLE " + TABELA + " ( "
                + ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + DATAREG + " TEXT, " + PESO + " TEXT, " + ALTURA + " TEXT," + RESULTADO + " TEXT,"
                + DIAGNOSTICO + " TEXT ) ;";

        db.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        String sql = "DROP TABLE IF EXISTS " + TABELA;

        db.execSQL(sql);

        onCreate(db);
    }

    public long salvarImc(Imc i) {

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(DATAREG, i.getDatareg());
        values.put(PESO, i.getPeso());
        values.put(ALTURA, i.getAltura());
        values.put(RESULTADO, i.getResultado());
        values.put(DIAGNOSTICO, i.getDiagnostico());

        retornoDB = getWritableDatabase().insert(TABELA, null, values);

        return retornoDB;
    }

    public long alterarImc(Imc i) {

        ContentValues values = new ContentValues();
        long retornoDB;

        values.put(DATAREG, i.getDatareg());
        values.put(PESO, i.getPeso());
        values.put(ALTURA, i.getAltura());
        values.put(RESULTADO, i.getResultado());
        values.put(DIAGNOSTICO, i.getDiagnostico());

        String[] args = {String.valueOf(i.getId())};
        retornoDB = getWritableDatabase().update(TABELA,  values, "id=?",args);

        return retornoDB;

    }

    public long excluirImc(Imc i) {

        long retornoDB;

        String[] args = {String.valueOf(i.getId())};
        retornoDB = getWritableDatabase().delete(TABELA,ID+ "=?",args);

        return retornoDB;
    }

    // Metodos para fazer Select

    public ArrayList<Imc> selectAllImc() {

        // Metodo para carregar ArrayList

        String[] coluns = {ID, DATAREG, PESO, ALTURA, RESULTADO, DIAGNOSTICO};

        Cursor cursor = getWritableDatabase().query(TABELA, coluns, null,
                null, null, null, "upper(datareg)",
                null);

        ArrayList<Imc> listImc = new ArrayList<Imc>();

        while (cursor.moveToNext()) {
            Imc i = new Imc();

            i.setId(cursor.getInt(0));
            i.setDatareg(cursor.getString(1));
            i.setPeso(cursor.getString(2));
            i.setAltura(cursor.getString(3));
            i.setResultado(cursor.getString(4));
            i.setDiagnostico(cursor.getString(5));

            listImc.add(i);
        }

        return listImc;
    }
}
