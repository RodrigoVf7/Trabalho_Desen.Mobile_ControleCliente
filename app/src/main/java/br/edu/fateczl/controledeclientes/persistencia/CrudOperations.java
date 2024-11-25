package br.edu.fateczl.controledeclientes.persistencia;
/*@author: RODRIGO VINICIUS FERRAZ DA SILVA
 *@RA: 1110482313043*/

import java.util.List;

public interface CrudOperations<T> {
    // inseri, exlui e atualiza objetos
    void inserir(T obj);
    void atualizar(T obj);
    void excluir(int id);
    List<T> listar(); // Lista todos os objetos
}

