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
import br.edu.fateczl.controledeclientes.model.Cliente;
import br.edu.fateczl.controledeclientes.model.Endereco;

public class ClienteDAO implements CrudOperations<Cliente> {

    private SQLiteDatabase db;
    private DBHelper dbHelper;
    Cliente cliente = new Cliente();

    public ClienteDAO(Context context) {
        dbHelper = new DBHelper(context); // Instância do DBHelper
    }

    // Abrir banco de dados
    public void abrir() {
        try {
            if (db == null || !db.isOpen()) {
                db = dbHelper.getWritableDatabase();
                if (db == null) {
                    Log.e("ClienteDAO", "Falha ao abrir o banco de dados.");
                }
            }
        } catch (SQLException e) {
            Log.e("ClienteDAO", "Erro ao abrir o banco de dados", e);
        }
    }

    // Fechar banco de dados
    public void fechar() {
        try {
            if (db != null && db.isOpen()) {
                db.close();  // Fecha o banco de dados
                db = null;   // Limpa a referência
            }
            dbHelper.close();  // Fecha o SQLiteOpenHelper
        } catch (SQLException e) {
            Log.e("ClienteDAO", "Erro ao fechar o banco de dados", e);
        }
    }

    @Override
    public void inserir(Cliente cliente) {
        try {
            if (cliente == null || cliente.getNome() == null || cliente.getNome().isEmpty()) {
                Log.e("ClienteDAO", "Cliente ou nome inválido.");
                return; // Previne a inserção de dados inválidos
            }

            abrir(); // Garantir que o banco de dados está aberto antes de qualquer operação

            ContentValues values = new ContentValues();
            values.put("nome", cliente.getNome());
            values.put("telefone", cliente.getTelefone());
            values.put("email", cliente.getEmail());
            values.put("observacoes", cliente.getObservacoes());

            long result = db.insert("clientes", null, values);
            if (result == -1) {
                Log.e("ClienteDAO", "Erro ao inserir cliente");
            } else {
                Log.d("ClienteDAO", "Cliente inserido com sucesso.");
            }

        } catch (SQLException e) {
            Log.e("ClienteDAO", "Erro ao inserir cliente", e);
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro geral ao inserir cliente", e);
        } finally {
            fechar(); // Garante o fechamento do banco
        }
    }

    @Override
    public void atualizar(Cliente cliente) {
        try {
            abrir(); // Garantir que o banco de dados está aberto antes de qualquer operação

            ContentValues values = new ContentValues();
            values.put("nome", cliente.getNome());
            values.put("telefone", cliente.getTelefone());
            values.put("email", cliente.getEmail());
            values.put("observacoes", cliente.getObservacoes());

            int rowsUpdated = db.update("clientes", values, "id = ?", new String[]{String.valueOf(cliente.getId())});
            if (rowsUpdated > 0) {
                Log.d("ClienteDAO", "Cliente atualizado com sucesso.");
            } else {
                Log.e("ClienteDAO", "Erro ao atualizar cliente: nenhum registro atualizado.");
            }

        } catch (SQLException e) {
            Log.e("ClienteDAO", "Erro ao atualizar cliente", e);
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro geral ao atualizar cliente", e);
        } finally {
            fechar();
        }
    }

    @Override
    public void excluir(int id) {
        try {
            abrir(); // Garantir que o banco de dados está aberto antes de qualquer operação

            int rowsDeleted = db.delete("clientes", "id = ?", new String[]{String.valueOf(id)});
            if (rowsDeleted > 0) {
                Log.d("ClienteDAO", "Cliente excluído com sucesso.");
            } else {
                Log.e("ClienteDAO", "Erro ao excluir cliente: nenhum registro excluído.");
            }

        } catch (SQLException e) {
            Log.e("ClienteDAO", "Erro ao excluir cliente", e);
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro geral ao excluir cliente", e);
        } finally {
            fechar();
        }
    }

    @Override
    public List<Cliente> listar() {
        List<Cliente> clientes = new ArrayList<>();
        Cursor cursor = null;

        try {
            abrir(); // Garantir que o banco de dados está aberto antes de qualquer operação

            // Consulta para listar os clientes e seus endereços
            String query = "SELECT c.id, c.nome, c.telefone, c.email, c.observacoes, e.rua, e.numero, e.bairro, e.cidade, e.estado, e.cep " +
                    "FROM clientes c " +
                    "LEFT JOIN endereco e ON c.id = e.cliente_id";  // Relaciona clientes com endereços

            cursor = db.rawQuery(query, null);

            if (cursor.moveToFirst()) {
                do {
                    // Extraindo os dados do cursor, verificando se o índice da coluna é válido
                    long id = -1;
                    String nome = null;
                    String telefone = null;
                    String email = null;
                    String observacoes = null;
                    String rua = null;
                    String numero = null;
                    String bairro = null;
                    String cidade = null;
                    String estado = null;
                    String cep = null;

                    // Verifica se as colunas estão presentes no cursor
                    int idIndex = cursor.getColumnIndex("id");
                    if (idIndex >= 0) id = cursor.getLong(idIndex);

                    int nomeIndex = cursor.getColumnIndex("nome");
                    if (nomeIndex >= 0) nome = cursor.getString(nomeIndex);

                    int telefoneIndex = cursor.getColumnIndex("telefone");
                    if (telefoneIndex >= 0) telefone = cursor.getString(telefoneIndex);

                    int emailIndex = cursor.getColumnIndex("email");
                    if (emailIndex >= 0) email = cursor.getString(emailIndex);

                    int observacoesIndex = cursor.getColumnIndex("observacoes");
                    if (observacoesIndex >= 0) observacoes = cursor.getString(observacoesIndex);

                    // Dados de endereço
                    int ruaIndex = cursor.getColumnIndex("rua");
                    if (ruaIndex >= 0) rua = cursor.getString(ruaIndex);

                    int numeroIndex = cursor.getColumnIndex("numero");
                    if (numeroIndex >= 0) numero = cursor.getString(numeroIndex);

                    int bairroIndex = cursor.getColumnIndex("bairro");
                    if (bairroIndex >= 0) bairro = cursor.getString(bairroIndex);

                    int cidadeIndex = cursor.getColumnIndex("cidade");
                    if (cidadeIndex >= 0) cidade = cursor.getString(cidadeIndex);

                    int estadoIndex = cursor.getColumnIndex("estado");
                    if (estadoIndex >= 0) estado = cursor.getString(estadoIndex);

                    int cepIndex = cursor.getColumnIndex("cep");
                    if (cepIndex >= 0) cep = cursor.getString(cepIndex);

                    // Criando o objeto Endereco
                    Endereco endereco = new Endereco(id, rua, numero, bairro, cidade, id);

                    // Criando o cliente
                    Cliente cliente = new Cliente();
                    cliente.setId((int) id); // Atribuindo o id do cliente
                    cliente.setNome(nome);
                    cliente.setTelefone(telefone);
                    cliente.setEmail(email);
                    cliente.setObservacoes(observacoes);
                    cliente.setEndereco(endereco); // Associa o endereço ao cliente

                    // Adicionando o cliente à lista
                    clientes.add(cliente);

                } while (cursor.moveToNext());
            }

        } catch (SQLException e) {
            Log.e("ClienteDAO", "Erro ao listar clientes", e);
        } catch (Exception e) {
            Log.e("ClienteDAO", "Erro geral ao listar clientes", e);
        } finally {
            if (cursor != null) {
                cursor.close(); // Fecha o cursor após a leitura
            }
            fechar(); // Fecha o banco de dados
        }

        return clientes; // Retorna a lista de clientes
    }

}




