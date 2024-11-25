package br.edu.fateczl.controledeclientes.view;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.fragment.app.Fragment;
import br.edu.fateczl.controledeclientes.R;
import br.edu.fateczl.controledeclientes.model.Cliente;
import br.edu.fateczl.controledeclientes.persistencia.ClienteDAO;

public class ClienteDetailFragment extends Fragment {

    private EditText edtNome, edtTelefone, edtEmail, edtObservacoes;
    private Button btnSalvar;
    private ClienteDAO clienteDAO;
    private Cliente clienteAtual;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cliente_detail, container, false);
        // Inicializando componentes
        edtNome = rootView.findViewById(R.id.edtNome);
        edtTelefone = rootView.findViewById(R.id.edtTelefone);
        edtEmail = rootView.findViewById(R.id.edtEmail);
        edtObservacoes = rootView.findViewById(R.id.edtObservacoes);
        btnSalvar = rootView.findViewById(R.id.btnSalvar);

        clienteDAO = new ClienteDAO(getContext());
        // Verificando se um cliente foi passado
        if (getArguments() != null) {
            int clienteId = getArguments().getInt("clienteId");
            carregarCliente(clienteId);
        }
        // Configurar o botão de salvar
        btnSalvar.setOnClickListener(v -> salvarCliente());
        return rootView;
    }
    private void carregarCliente(int id) {
        // Carregar o cliente do banco de dados
        clienteDAO.abrir();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            clienteAtual = clienteDAO.listar().stream()
                    .filter(cliente -> cliente.getId() == id)
                    .findFirst()
                    .orElse(null);
        }
        clienteDAO.fechar();
        if (clienteAtual != null) {
            // Exibir os dados do cliente nos campos
            edtNome.setText(clienteAtual.getNome());
            edtTelefone.setText(clienteAtual.getTelefone());
            edtEmail.setText(clienteAtual.getEmail());
            edtObservacoes.setText(clienteAtual.getObservacoes());
        }
    }
    private void salvarCliente() {
        // Validar os dados
        String nome = edtNome.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String email = edtEmail.getText().toString();
        String observacoes = edtObservacoes.getText().toString();
        if (TextUtils.isEmpty(nome)) {
            Toast.makeText(getContext(), "Nome é obrigatório", Toast.LENGTH_SHORT).show();
            return;
        }
        // Atualizar os dados do cliente
        if (clienteAtual != null) {
            clienteAtual.setNome(nome);
            clienteAtual.setTelefone(telefone);
            clienteAtual.setEmail(email);
            clienteAtual.setObservacoes(observacoes);
            // Atualizar cliente no banco de dados
            clienteDAO.abrir();
            clienteDAO.atualizar(clienteAtual);
            clienteDAO.fechar();
            Toast.makeText(getContext(), "Cliente atualizado com sucesso!", Toast.LENGTH_SHORT).show();
            // Voltar para a lista de clientes (ClienteListFragment)
            getFragmentManager().popBackStack();  // Isso vai retornar ao fragment anterior na pilha
        }
    }
}
