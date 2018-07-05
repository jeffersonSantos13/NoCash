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

import br.com.urway.nocash.dao.DAOFactory;
import br.com.urway.nocash.dao.interf.*;
import br.com.urway.nocash.model.Movimento;

/**
 * Contém serviços do movimento
 */
@Path("/movimento")
public class MovimentoService {
    
    private static final Logger LOGGER = Logger.getLogger(MovimentoService.class.getName());    
    
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovimentos(){
        List<Movimento> mov;
        try{
            IDAOMovimento dao = DAOFactory.getMovimentoDAO();
            mov = dao.procurar();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Movimento", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao buscar movimentos de extrato. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
        
        return Response.ok(mov).build();
    }
    
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserirMovimento(Movimento mov) throws Exception {
        try{
            IDAOMovimento dao = DAOFactory.getMovimentoDAO();
            dao.inserir(mov);
            return Response.ok().build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    } 
    
    @Path("/carga")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response cargaMovimento(Movimento mov) {
        try{
            IDAOMovimento dao = DAOFactory.getMovimentoDAO();
            dao.cargaCarteira(mov);
            return Response.ok().build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    } 
    
    @Path("/destino/{param}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response destino(@PathParam("param") int id) {
        
        try{
            List<Movimento> mov;
            IDAOMovimento dao = DAOFactory.getMovimentoDAO();
            mov = dao.getDestino(id);
            return Response.ok(mov).build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    } 
    
    @Path("/origem/{param}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response origem(@PathParam("param") int id){
        try{
            List<Movimento> mov;
            IDAOMovimento dao = DAOFactory.getMovimentoDAO();
            mov = dao.getOrigem(id);
            return Response.ok(mov).build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    } 
    
    @Path("/cliente/{param}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response byCliente(@PathParam("param") int id){
        try{
            List<Movimento> mov;
            IDAOMovimento dao = DAOFactory.getMovimentoDAO();
            mov = dao.getByCliente(id);
            return Response.ok(mov).build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
}
