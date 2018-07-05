/**
 * Classes de serviço, versão 1.0
 */
package br.com.urway.nocash.service;

import br.com.urway.nocash.dao.DAOFactory;
import br.com.urway.nocash.dao.interf.IDAOCarteira;
import br.com.urway.nocash.model.Carteira;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Contém serviços de Carteira
 */
@Path("/carteira")
public class CarteiraService {
    /** Instância do logger **/
    private static final Logger LOGGER = Logger.getLogger(CarteiraService.class.getName());
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCarteira(){
        List<Carteira> carteiras = null;
        try{
            IDAOCarteira dao = DAOFactory.getCarteiraDAO();
            carteiras = dao.procurar();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Carteira", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao buscar a carteira. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
        
        return Response.ok(carteiras).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirCarteira(Carteira carteira) throws Exception {
        try{
            IDAOCarteira dao = DAOFactory.getCarteiraDAO();
            dao.inserir(carteira);
            return Response.ok().build();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Carteira", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao inserir a carteira. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCarteira(Carteira carteira) throws Exception {
        try{
            IDAOCarteira dao = DAOFactory.getCarteiraDAO();
            dao.atualizar(carteira);
            return Response.ok().build();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Carteira", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao atualizar a carteira. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @DELETE
    public Response updateCarteira(int id) throws Exception {
        try {
            IDAOCarteira dao = DAOFactory.getCarteiraDAO();
            dao.excluir(id);
            return Response.ok().build();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Carteira", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao deletar a carteira. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
    
    @Path("/obter/{param}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response obter(@PathParam("param")int id) throws Exception {
        
        Carteira carteira = new Carteira();
        try{
            IDAOCarteira dao = DAOFactory.getCarteiraDAO();
            carteira = dao.obter(id);
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Carteira", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao buscar a carteira. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
        
        return Response.ok(carteira).build();
        
    }
    
    
}
