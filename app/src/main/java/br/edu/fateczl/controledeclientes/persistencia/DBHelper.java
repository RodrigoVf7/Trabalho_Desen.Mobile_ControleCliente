package br.edu.fateczl.controledeclientes.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;// Versão do banco de dados
    private static final String DATABASE_NAME = "controle_clientes.db";

    // Comandos para criar as tabelas
    private static final String CREATE_TABLE_CLIENTES = "CREATE TABLE clientes (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "nome TEXT, " +
            "telefone TEXT, " +
            "email TEXT, " +
            "observacoes TEXT);";

    private static final String CREATE_TABLE_ENDERECO = "CREATE TABLE endereco (" +
            "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "cliente_id INTEGER, " +
            "rua TEXT, " +
            "numero TEXT, " +
            "bairro TEXT, " +
            "cidade TEXT, " +
            "estado TEXT, " +
            "cep TEXT, " +
            "FOREIGN KEY(cliente_id) REFERENCES clientes(id));";

    // Construtor
    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método chamado para criar as tabelas
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CLIENTES);
        db.execSQL(CREATE_TABLE_ENDERECO);
    }

    // Método para atualizar o banco de dados, caso a versão seja alterada
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS endereco");
        db.execSQL("DROP TABLE IF EXISTS clientes");
        onCreate(db);
    }
}




