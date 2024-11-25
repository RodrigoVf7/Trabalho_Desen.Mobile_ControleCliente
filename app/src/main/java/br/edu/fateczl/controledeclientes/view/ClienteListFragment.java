package br.edu.fateczl.controledeclientes.view;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import br.edu.fateczl.controledeclientes.R;
import br.edu.fateczl.controledeclientes.adapter.ClienteAdapter;
import br.edu.fateczl.controledeclientes.model.Cliente;
import br.edu.fateczl.controledeclientes.persistencia.ClienteDAO;
import java.util.List;

public class ClienteListFragment extends Fragment {

    private RecyclerView recyclerViewClientes;
    private ClienteAdapter clienteAdapter;
    private ClienteDAO clienteDAO;
    private List<Cliente> listaClientes;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_cliente_list, container, false);
        // Inicializa o DAO e a lista de clientes
        clienteDAO = new ClienteDAO(getContext());
        listaClientes = clienteDAO.listar(); // Obtém a lista de clientes do DAO
        // Acessa o RecyclerView a partir do layout inflado
        recyclerViewClientes = rootView.findViewById(R.id.recyclerViewClientes);
        // Configura o LayoutManager
        recyclerViewClientes.setLayoutManager(new LinearLayoutManager(getContext()));
        // Configura o Adapter para o RecyclerView
        clienteAdapter = new ClienteAdapter(listaClientes, clienteDAO);
        recyclerViewClientes.setAdapter(clienteAdapter);
        return rootView;
    }
    @Override
    public void onResume() {
        super.onResume();
        // Atualiza a lista de clientes
        listaClientes.clear();
        listaClientes.addAll(clienteDAO.listar());
        clienteAdapter.notifyDataSetChanged(); // Atualiza o RecyclerView
    }
}


