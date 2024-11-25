package br.edu.fateczl.controledeclientes.view;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import br.edu.fateczl.controledeclientes.R;
import br.edu.fateczl.controledeclientes.persistencia.ClienteDAO;
import br.edu.fateczl.controledeclientes.model.Cliente;
import br.edu.fateczl.controledeclientes.model.Endereco;

public class FormularioClienteActivity extends AppCompatActivity {

    private EditText editNome, editTelefone, editEmail, editObservacoes, editLogradouro, editNumero, editBairro, editCidade, editEstado, editCep;
    private Button btnSalvar;
    private ClienteDAO clienteDAO;
    private Cliente clienteAtual;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_cliente);

        // Inicializar campos
        editNome = findViewById(R.id.editNome);
        editTelefone = findViewById(R.id.editTelefone);
        editEmail = findViewById(R.id.editEmail);
        editObservacoes = findViewById(R.id.editObservacoes);
        editLogradouro = findViewById(R.id.editLogradouro);
        editNumero = findViewById(R.id.editNumero);
        editBairro = findViewById(R.id.editBairro);
        editCidade = findViewById(R.id.editCidade);
        editEstado = findViewById(R.id.editEstado);
        editCep = findViewById(R.id.editCep);  // Inicializando o EditText para o CEP
        btnSalvar = findViewById(R.id.btnSalvar);

        clienteDAO = new ClienteDAO(this);
        // Verificar se é edição
        if (getIntent().hasExtra("cliente")) {
            clienteAtual = (Cliente) getIntent().getSerializableExtra("cliente");
            preencherFormulario(clienteAtual);
        }
        btnSalvar.setOnClickListener(v -> salvarCliente());
    }
    private void preencherFormulario(Cliente cliente) {
        editNome.setText(cliente.getNome());
        editTelefone.setText(cliente.getTelefone());
        editEmail.setText(cliente.getEmail());
        editObservacoes.setText(cliente.getObservacoes());
        // Preencher os campos de endereço, se o cliente tiver um endereço
        if (cliente.getEndereco() != null) {
            editLogradouro.setText(cliente.getEndereco().getRua());
            editNumero.setText(cliente.getEndereco().getNumero());
            editBairro.setText(cliente.getEndereco().getBairro());
            editCidade.setText(cliente.getEndereco().getCidade());
            editEstado.setText(cliente.getEndereco().getEstado());
            editCep.setText(cliente.getEndereco().getCep());  // Preencher o campo de CEP
        }
    }
    private void salvarCliente() {
        String nome = editNome.getText().toString();
        String telefone = editTelefone.getText().toString();
        String email = editEmail.getText().toString();
        String observacoes = editObservacoes.getText().toString();
        // Campos de endereço
        String logradouro = editLogradouro.getText().toString();
        String numero = editNumero.getText().toString();
        String bairro = editBairro.getText().toString();
        String cidade = editCidade.getText().toString();
        String estado = editEstado.getText().toString();
        String cep = editCep.getText().toString();
        // Validação dos campos obrigatórios
        if (nome.isEmpty() || telefone.isEmpty()) {
            Toast.makeText(this, "Nome e telefone são obrigatórios!", Toast.LENGTH_SHORT).show();
            return;
        }
        // Criar o endereço com todos os parâmetros
        Endereco endereco = new Endereco(0L, logradouro, numero, bairro, cidade, estado, cep);

        Cliente novoCliente = new Cliente(nome, telefone, email, observacoes, endereco);
        // Abrir o banco de dados antes de realizar a operação
        clienteDAO.abrir();
        if (clienteAtual == null) {
            // Novo cliente
            clienteDAO.inserir(novoCliente);
            Toast.makeText(this, "Cliente salvo com sucesso!", Toast.LENGTH_SHORT).show();
        } else {
            // Atualizar cliente existente
            clienteAtual.setNome(nome);
            clienteAtual.setTelefone(telefone);
            clienteAtual.setEmail(email);
            clienteAtual.setObservacoes(observacoes);
            clienteAtual.setEndereco(endereco);

            // Atualizar cliente
            clienteDAO.atualizar(clienteAtual);
            Toast.makeText(this, "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
        }
        // Fechar o banco de dados depois de salvar
        clienteDAO.fechar();
        finish();  // Finalizar a activity e voltar à tela anterior
    }
}




