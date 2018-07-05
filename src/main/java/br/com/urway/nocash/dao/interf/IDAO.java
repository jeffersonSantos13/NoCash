/**
 * Interfaces de objetos de acesso a dados
 */
package br.com.urway.nocash.dao.interf;

import java.util.List;


/**
 * Define um DAO
 */
public interface IDAO<T> {

    /**
     * Procura por elementos que atendam aos critérios
     * 
     * @param Object...criterios
     *            os critérios de pesquisa
     * @return List<T> os objetos encontrados
     */
    public List<T> procurar(Object... criterios) throws Exception;

    /**
     * Insere um objeto no banco de dados
     * 
     * @param T
     *            elemento o elemento a inserir
     */
    public void inserir(T elemento) throws Exception;

    /**
     * Atualiza o elemento com o id informado
     * @param idPlanoSemestre 
     * 
     * @param T
     *            elemento com o id a ser atualizado e os novos dados
     */
    public void atualizar(T elemento) throws Exception;

    /**
     * Exclui o elemento indicado
     * 
     * @param id
     *            o id do elemento a excluir
     */
    public void excluir(int id) throws Exception;

    /**
     * Obtém um elemento pelo ID
     * 
     * @param id
     *            o id do elemento a excluir
     */
    public T obter(int id) throws Exception;
}