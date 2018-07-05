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

import org.codehaus.jackson.annotate.JsonRawValue;

//import br.com.urway.nocash.constant.MainConstants;
import br.com.urway.nocash.dao.DAOFactory;
import br.com.urway.nocash.dao.interf.*;
import br.com.urway.nocash.model.Cliente;
import br.com.urway.nocash.model.Usuario;

/**
 * Contém serviços de usuários
 */
//@Path(MainConstants.SERVICES_V1 + "/usuarios")
@Path("/usuarios")
public class UsuarioService {
    /** Instância do logger **/
    private static final Logger LOGGER = Logger.getLogger(UsuarioService.class.getName());

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUsuarios(){
        List<Usuario> usuarios = null;
        try{
            IDAOUsuario dao = DAOFactory.getUsuarioDAO();
            usuarios = dao.procurar();
        } catch(Exception ex){
            LOGGER.log(Level.SEVERE, "Falha na execução do DAO de Cliente", ex);
            Response.status(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode())
                            .entity("Ocorreu uma falha ao atualizar o usuario. "
                                            + "Verifique o log do servidor para maiores detalhes").build();
            return Response.serverError().entity(ex.getMessage()).build();
        }
        
        return Response.ok(usuarios).build();
    }
    
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response Login(Usuario usuario) {
        try {
            IDAOUsuario dao = DAOFactory.getUsuarioDAO();
            usuario = dao.Login(usuario);
            return Response.ok(usuario).build();
        } catch(Exception ex){
            return Response.serverError().entity(ex.getMessage()).build();
        }
    }
}
