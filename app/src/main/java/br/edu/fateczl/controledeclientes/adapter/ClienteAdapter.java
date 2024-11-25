package br.edu.fateczl.controledeclientes.adapter;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/

import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.edu.fateczl.controledeclientes.R;
import br.edu.fateczl.controledeclientes.model.Cliente;
import br.edu.fateczl.controledeclientes.view.FormularioClienteActivity;
import br.edu.fateczl.controledeclientes.persistencia.ClienteDAO;

public class ClienteAdapter extends RecyclerView.Adapter<ClienteAdapter.ClienteViewHolder> {

    private List<Cliente> clientes;
    private ClienteDAO clienteDAO;

    // Construtor para aceitar o ClienteDAO
    public ClienteAdapter(List<Cliente> clientes, ClienteDAO clienteDAO) {
        this.clientes = clientes;
        this.clienteDAO = clienteDAO;  // Inicializando o clienteDAO
    }

    @NonNull
    @Override
    public ClienteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_cliente, parent, false);
        return new ClienteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ClienteViewHolder holder, int position) {
        Cliente cliente = clientes.get(position);

        holder.nome.setText(cliente.getNome());
        holder.telefone.setText(cliente.getTelefone());

        if (cliente.getEndereco() != null) {
            holder.endereco.setText(cliente.getEndereco().getRua() + ", " +
                    cliente.getEndereco().getNumero() + ", " +
                    cliente.getEndereco().getCidade());
        } else {
            holder.endereco.setText("Endereço não disponível");
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), FormularioClienteActivity.class);
            intent.putExtra("cliente", (Parcelable) cliente); // Passa o cliente para edição
            v.getContext().startActivity(intent);
        });

        // Configurar o botão de exclusão
        holder.btnExcluir.setOnClickListener(v -> {
            if (clienteDAO != null) {
                // Exibir o AlertDialog para confirmar exclusão
                new AlertDialog.Builder(v.getContext())
                        .setTitle("Confirmar exclusão")
                        .setMessage("Tem certeza que deseja excluir este cliente?")
                        .setPositiveButton("Sim", (dialog, which) -> {
                            // Excluir cliente
                            clienteDAO.excluir(cliente.getId());  // Usando clienteDAO
                            clientes.remove(position);  // Remove o cliente da lista
                            notifyItemRemoved(position); // Atualiza o RecyclerView
                            Toast.makeText(v.getContext(), "Cliente excluído", Toast.LENGTH_SHORT).show();
                        })
                        .setNegativeButton("Não", null)
                        .show();
            } else {
                Log.e("ClienteAdapter", "clienteDAO é null!");
            }
        });
    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    // Método para atualizar a lista de clientes
    public void atualizarLista(List<Cliente> novosClientes) {
        this.clientes = novosClientes;
        notifyDataSetChanged();
    }

    // ViewHolder para referenciar as views de cada item
    public static class ClienteViewHolder extends RecyclerView.ViewHolder {
        TextView nome, telefone, endereco;
        Button btnExcluir; // Botão de exclusão

        public ClienteViewHolder(@NonNull View itemView) {
            super(itemView);
            nome = itemView.findViewById(R.id.textNome);
            telefone = itemView.findViewById(R.id.textTelefone);
            endereco = itemView.findViewById(R.id.textEndereco);
            btnExcluir = itemView.findViewById(R.id.btnExcluir);
        }
    }
}



