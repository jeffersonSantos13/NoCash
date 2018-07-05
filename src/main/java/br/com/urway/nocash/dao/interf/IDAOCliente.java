package br.com.urway.nocash.dao.interf;

import br.com.urway.nocash.model.Cliente;

public interface IDAOCliente extends IDAO<Cliente> {

    public Cliente Login(Cliente cliente) throws Exception;
    
    public boolean verificaEmail(String email) throws Exception;
}
