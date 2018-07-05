/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.urway.nocash.service;

import br.com.urway.nocash.dao.DAOFactory;
import br.com.urway.nocash.dao.interf.IDAOMovimento;
import br.com.urway.nocash.model.Movimento;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Mo
 */
@Path("/parceiro")
public class ParceiroService {
    private static final Logger LOGGER = Logger.getLogger(MovimentoService.class.getName());    
    /*
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getMovimentos(){
        List<Movimento> mov;
        try{
            IDAOParceiro dao = DAOFactory.getParceiroDAO();
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
    */
}
