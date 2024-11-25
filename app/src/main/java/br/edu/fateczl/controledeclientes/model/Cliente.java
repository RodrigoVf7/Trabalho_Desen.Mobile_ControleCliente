package br.edu.fateczl.controledeclientes.model;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

public class Cliente extends Pessoa implements Parcelable {
    private String observacoes;
    private Endereco endereco; // Adicionando o campo de endereço

    public Cliente() {
        super();  // Chama o construtor da classe base (Pessoa)
        // Inicialize quaisquer outros campos se necessário (por exemplo, o endereco pode ser nulo)
    }

    public Cliente(String nome, String telefone, String email, String observacoes, Endereco endereco) {
        super();
    }

    // Construtor com parâmetros
    public Cliente(int id, String nome, String telefone, String email, String observacoes, Endereco endereco) {
        super(id, nome, telefone, email);
        this.observacoes = observacoes;
        this.endereco = endereco; // Atribuindo o endereço
    }

    protected Cliente(Parcel in) {
        observacoes = in.readString();
    }

    public static final Creator<Cliente> CREATOR = new Creator<Cliente>() {
        @Override
        public Cliente createFromParcel(Parcel in) {
            return new Cliente(in);
        }

        @Override
        public Cliente[] newArray(int size) {
            return new Cliente[size];
        }
    };

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    // Implementação do método abstrato
    @Override
    public String getDetalhes() {
        return "Cliente: " + getNome() + ", Telefone: " + getTelefone() + ", Email: " + getEmail();
    }

    // Sobrescrevendo o método toString
    @Override
    public String toString() {
        return "Cliente{" +
                "id=" + getId() +
                ", nome='" + getNome() + '\'' +
                ", telefone='" + getTelefone() + '\'' +
                ", email='" + getEmail() + '\'' +
                ", observacoes='" + observacoes + '\'' +
                ", endereco=" + endereco.toString() +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel parcel, int i) {
        parcel.writeString(observacoes);
    }
}



