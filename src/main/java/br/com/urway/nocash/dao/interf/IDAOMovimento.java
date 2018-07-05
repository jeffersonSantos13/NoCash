package br.com.urway.nocash.dao.interf;

import br.com.urway.nocash.model.Movimento;
import java.util.List;

public interface IDAOMovimento extends IDAO<Movimento> {
    
    public List<Movimento> procurarCarteira(String acao, String data) throws Exception;
    public List<Movimento> getOrigem(int id) throws Exception;
    public List<Movimento> getDestino(int id) throws Exception;
    public void cargaCarteira(Movimento mov) throws Exception;

    public List<Movimento> getByCliente(int id) throws Exception;
    
}
