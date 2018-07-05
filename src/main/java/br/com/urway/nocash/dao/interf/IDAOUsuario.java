/**
 * Interfaces de objetos de acesso a dados
 */
package br.com.urway.nocash.dao.interf;


import br.com.urway.nocash.model.Usuario;

/**
 * Define um DAO de Usuários
 */
public interface IDAOUsuario extends IDAO<Usuario> {

	/**
	 * Atualiza nome do usuario
	 * 
	 * @param usuarioID
	 *            id do usuario
	 * @param nome
	 *            novo nome
	 * @throws Exception
	 */
	public void atualizarNome(int usuarioID, String nome) throws Exception;

	/**
	 * Altera a senha do usuário
	 * 
	 * @param usuarioID
	 *            id do usuario
	 * @param passwordNew
	 *            nova senha
	 * @throws Exception
	 */
	public void alterarSenha(int usuarioID, String passwordNew) throws Exception;

	/**
	 * Reseta a senha do usuário
	 * 
	 * @param usuarioID
	 *            id do usuario
	 * @throws Exception
	 */
	public void resetarSenha(int usuarioID) throws Exception;

	/**
	 * Insere novo usuário
	 * 
	 * @param Usuario
	 * @throws Exception
	 */
	public long inserirUsuario(Usuario elemento) throws Exception;

	/**
	 * Procura usuário pelo username
	 * 
	 * @param username
	 * @throws Exception
	 */
	public Usuario procurarPorUsername(String username) throws Exception;
        
        public Usuario Login(Usuario usuario) throws Exception;

}
