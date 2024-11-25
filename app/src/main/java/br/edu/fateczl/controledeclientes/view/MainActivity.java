package br.edu.fateczl.controledeclientes.view;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import br.edu.fateczl.controledeclientes.R;

public class MainActivity extends AppCompatActivity {

    private Button btnAdicionarCliente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnAdicionarCliente = findViewById(R.id.btnAdicionarCliente);

        // Carregar o fragment de clientes
        if (savedInstanceState == null) {  // Carregar o fragment apenas na primeira vez
            ClienteListFragment clienteListFragment = new ClienteListFragment();
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            transaction.replace(R.id.container, clienteListFragment); // Substitui o FrameLayout pelo fragment
            transaction.commit();
        }

        // Adicionar um clique no botão para abrir a tela de formulário
        btnAdicionarCliente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Iniciar a tela de formulário para adicionar um novo cliente
                Intent intent = new Intent(MainActivity.this, FormularioClienteActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}




