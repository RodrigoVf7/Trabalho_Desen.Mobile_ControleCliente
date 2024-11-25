package br.edu.fateczl.controledeclientes.persistencia;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import java.util.ArrayList;
import java.util.List;
import br.edu.fateczl.controledeclientes.model.Endereco;

public class EnderecoDAO {
    private SQLiteDatabase database;
    private DBHelper dbHelper;
    public EnderecoDAO(Context context) {
        dbHelper = new DBHelper(context);
    }
    // Abrir o banco de dados
    public void abrir() {
        try {
            if (database == null || !database.isOpen()) {
                database = dbHelper.getWritableDatabase();
            }
        } catch (SQLException e) {
            Log.e("EnderecoDAO", "Erro ao abrir o banco de dados", e);
        }
    }
    // Fechar o banco de dados
    public void fechar() {
        try {
            if (database != null && database.isOpen()) {
                database.close();
                database = null;
            }
        } catch (SQLException e) {
            Log.e("EnderecoDAO", "Erro ao fechar o banco de dados", e);
        }
    }
    // Inserir um endereço
    public long inserir(Endereco endereco) {
        long result = -1;
        try {
            abrir();
            ContentValues values = new ContentValues();
            values.put("rua", endereco.getRua());
            values.put("cidade", endereco.getCidade());
            values.put("estado", endereco.getEstado());
            values.put("cep", endereco.getCep());
            values.put("cliente_id", endereco.getClienteId());

            result = database.insert("endereco", null, values);
            if (result == -1) {
                Log.e("EnderecoDAO", "Erro ao inserir endereço.");
            } else {
                Log.d("EnderecoDAO", "Endereço inserido com sucesso. ID: " + result);
            }
        } catch (SQLException e) {
            Log.e("EnderecoDAO", "Erro ao inserir endereço", e);
        } finally {
            fechar();
        }
        return result;
    }
    // Atualizar um endereço
    public void atualizar(Endereco endereco) {
        try {
            abrir();
            ContentValues values = new ContentValues();
            values.put("rua", endereco.getRua());
            values.put("cidade", endereco.getCidade());
            values.put("estado", endereco.getEstado());
            values.put("cep", endereco.getCep());
            values.put("cliente_id", endereco.getClienteId());

            int rowsAffected = database.update("endereco", values, "id = ?", new String[]{String.valueOf(endereco.getId())});
            if (rowsAffected > 0) {
                Log.d("EnderecoDAO", "Endereço atualizado com sucesso.");
            } else {
                Log.e("EnderecoDAO", "Nenhum endereço foi atualizado.");
            }
        } catch (SQLException e) {
            Log.e("EnderecoDAO", "Erro ao atualizar endereço", e);
        } finally {
            fechar();
        }
    }
    // Excluir um endereço
    public void excluir(long id) {
        try {
            abrir();
            int rowsDeleted = database.delete("endereco", "id = ?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Log.d("EnderecoDAO", "Endereço excluído com sucesso.");
            } else {
                Log.e("EnderecoDAO", "Nenhum endereço foi excluído.");
            }
        } catch (SQLException e) {
            Log.e("EnderecoDAO", "Erro ao excluir endereço", e);
        } finally {
            fechar();
        }
    }
    // Listar endereços por cliente
    public List<Endereco> listarPorCliente(long clienteId) {
        List<Endereco> enderecos = new ArrayList<>();
        Cursor cursor = null;
        try {
            abrir();
            String query = "SELECT id, rua, cidade, estado, cep, cliente_id FROM endereco WHERE cliente_id = ?";
            cursor = database.rawQuery(query, new String[]{String.valueOf(clienteId)});

            if (cursor.moveToFirst()) {
                do {
                    Endereco endereco = cursorToEndereco(cursor);
                    enderecos.add(endereco);
                } while (cursor.moveToNext());
            }
        } catch (SQLException e) {
            Log.e("EnderecoDAO", "Erro ao listar endereços por cliente", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            fechar();
        }
        return enderecos;
    }
    // Converte um Cursor para um objeto Endereco
    private Endereco cursorToEndereco(Cursor cursor) {
        long id = cursor.getLong(cursor.getColumnIndexOrThrow("id"));
        String rua = cursor.getString(cursor.getColumnIndexOrThrow("rua"));
        String cidade = cursor.getString(cursor.getColumnIndexOrThrow("cidade"));
        String estado = cursor.getString(cursor.getColumnIndexOrThrow("estado"));
        String cep = cursor.getString(cursor.getColumnIndexOrThrow("cep"));
        long clienteId = cursor.getLong(cursor.getColumnIndexOrThrow("cliente_id"));
        return new Endereco(id, rua, cidade, estado, cep, clienteId);
    }
}


