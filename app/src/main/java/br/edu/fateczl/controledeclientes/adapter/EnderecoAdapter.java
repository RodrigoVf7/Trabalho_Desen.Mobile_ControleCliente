package br.edu.fateczl.controledeclientes.adapter;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import br.edu.fateczl.controledeclientes.R;
import br.edu.fateczl.controledeclientes.model.Endereco;

public class EnderecoAdapter extends RecyclerView.Adapter<EnderecoAdapter.EnderecoViewHolder> {
    private List<Endereco> enderecos;

    public EnderecoAdapter(List<Endereco> enderecos) {
        this.enderecos = enderecos;
    }

    @NonNull
    @Override
    public EnderecoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.endereco_item, parent, false);
        return new EnderecoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EnderecoViewHolder holder, int position) {
        // Obtém o endereço da lista de acordo com a posição
        Endereco endereco = enderecos.get(position);

        // Atualiza as views com os dados do endereço
        holder.ruaTextView.setText(endereco.getRua());
        holder.cidadeTextView.setText(endereco.getCidade());
        holder.estadoTextView.setText(endereco.getEstado());
        holder.cepTextView.setText(endereco.getCep());
    }

    @Override
    public int getItemCount() {
        return enderecos.size(); // Retorna a quantidade de itens na lista
    }

    // ViewHolder para os itens da lista
    public static class EnderecoViewHolder extends RecyclerView.ViewHolder {
        TextView ruaTextView, cidadeTextView, estadoTextView, cepTextView;

        public EnderecoViewHolder(View itemView) {
            super(itemView);
            // Inicializa as views
            ruaTextView = itemView.findViewById(R.id.rua);
            cidadeTextView = itemView.findViewById(R.id.cidade);
            estadoTextView = itemView.findViewById(R.id.estado);
            cepTextView = itemView.findViewById(R.id.cep);
        }
    }
}


