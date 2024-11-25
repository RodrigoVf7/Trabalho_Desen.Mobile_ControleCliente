package br.edu.fateczl.controledeclientes.view;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import br.edu.fateczl.controledeclientes.R;
import br.edu.fateczl.controledeclientes.model.Cliente;

public class ItemFragment extends Fragment {

    private TextView nomeTextView;
    private TextView telefoneTextView;
    private TextView emailTextView;
    private TextView enderecoTextView;

    private Cliente cliente;

    public ItemFragment() {
        // Requer um construtor vazio
    }

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Infla o layout para este Fragment
        View rootView = inflater.inflate(R.layout.fragment_item, container, false);

        // Inicializa os componentes de UI com os IDs corretos
        nomeTextView = rootView.findViewById(R.id.textNome);  // Alterei para "textNome"
        telefoneTextView = rootView.findViewById(R.id.textTelefone);  // Alterei para "textTelefone"
        enderecoTextView = rootView.findViewById(R.id.textEndereco);  // Alterei para "textEndereco"

// Verifica se há um cliente passado como argumento
        if (getArguments() != null) {
            cliente = getArguments().getParcelable("cliente");  // Exemplo de como passar dados entre Fragments
            if (cliente != null) {
                // Preenche os campos com os dados do cliente
                nomeTextView.setText(cliente.getNome());
                telefoneTextView.setText(cliente.getTelefone());
                enderecoTextView.setText((CharSequence) cliente.getEndereco());
            }
        }


        // Verifica se há um cliente passado como argumento
        if (getArguments() != null) {
            cliente = getArguments().getParcelable("cliente");  // Exemplo de como passar dados entre Fragments
            if (cliente != null) {
                // Preenche os campos com os dados do cliente
                nomeTextView.setText(cliente.getNome());
                telefoneTextView.setText(cliente.getTelefone());
                emailTextView.setText(cliente.getEmail());
                enderecoTextView.setText((CharSequence) cliente.getEndereco());
            }
        }

        return rootView;
    }

    // Método para criar uma nova instância do fragmento com um cliente
    public static ItemFragment newInstance(Cliente cliente) {
        ItemFragment fragment = new ItemFragment();
        Bundle args = new Bundle();
        args.putParcelable("cliente", (Parcelable) cliente);  // Passando o cliente como argumento
        fragment.setArguments(args);
        return fragment;
    }
}

