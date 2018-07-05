/**
 * Classes de serviço, versão 1.0
 */
package br.com.urway.nocash.service;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


//import br.com.urway.nocash.constant.MainConstants;
import br.com.urway.nocash.dao.DAOFactory;
import br.com.urway.nocash.dao.interf.*;
import br.com.urway.nocash.model.Cliente;
import javax.ws.rs.DELETE;

/**
 * Contém serviços de usuários
 */
//@Path(MainConstants.SERVICES_V1 + "/usuarios")
@Path("/clientes")
public class ClienteService {
    /** Instância do logger **/
    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class.getName());
    
    
    @Path("/obter/{param}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obter(@PathParam("param")int id) throws Exception {
        
        Cliente cliente;
        
        try{
            IDAOCliente dao = DAOFactory.getClienteDAO();
            cliente = dao.obter(id);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Carteira", ex);
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Usuario", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao buscar usuarios. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
        
        return Response.ok(cliente).build();
        
    }
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClientes(){
        List<Cliente> clientes;
        try{
            IDAOCliente dao = DAOFactory.getClienteDAO();
            clientes = dao.procurar();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Usuario", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao buscar usuarios. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
        
        return Response.ok(clientes).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirCliente(Cliente cliente) throws Exception {
        try{
            IDAOCliente dao = DAOFactory.getClienteDAO();
            dao.inserir(cliente);
            return Response.ok().build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCliente(Cliente cliente) throws Exception {
        try {
            IDAOCliente dao = DAOFactory.getClienteDAO();
            dao.atualizar(cliente);
            return Response.ok().build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteCliente(int id) throws Exception {
        try {
            IDAOCliente dao = DAOFactory.getClienteDAO();
            dao.excluir(id);
            return Response.ok().build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(Cliente cliente) {
        try {
            IDAOCliente dao = DAOFactory.getClienteDAO();
            cliente = dao.Login(cliente);
            return Response.ok(cliente).build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @POST
    @Path("/email")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response verificaEmail(String email ) {
        try {
            IDAOCliente dao = DAOFactory.getClienteDAO();
            boolean retorno = dao.verificaEmail(email);
            return Response.ok(retorno).build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
}
