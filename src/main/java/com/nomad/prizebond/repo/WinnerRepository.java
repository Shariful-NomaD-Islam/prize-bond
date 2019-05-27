package com.nomad.prizebond.repo;

import com.nomad.prizebond.core.Winner;
import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;

@Path("winner")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class WinnerRepository implements PanacheRepositoryBase<Winner, Integer> {

    private static final Logger logger = LogManager.getLogger(WinnerRepository.class);

    @GET
    public List<Winner> get() {
        try {
            List<Winner> winnerList = Winner.listAll(Sort.by("pk_id"));
            logger.info("Winner - Getting data");
            return winnerList;
        } catch (Exception ex) {
            logger.error("Error - Winner - getting winner data", ex);
            return null;
        }
    }

    @GET
    @Path("{id}")
    public Winner getSingle(@PathParam Long id) {
        Winner entity = Winner.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Winner - with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Winner winner) {
        if (winner.id != null) {
            throw new WebApplicationException("Winner - Id was invalidly set on request.", 422);
        }

        winner.persist();
        return Response.ok(winner).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Winner update(@PathParam Long id, Winner winner) {

        Winner entity = Winner.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Winner - with id of " + id + " does not exist.", 404);
        }

        entity.status = winner.status;
        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        Winner entity = Winner.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Winner - with id of " + id + " does not exist.", 404);
        }
        entity.delete();
        return Response.status(204).build();
    }

    @Provider
    public static class ErrorMapper implements ExceptionMapper<Exception> {

        @Override
        public Response toResponse(Exception exception) {
            int code = 500;
            if (exception instanceof WebApplicationException) {
                code = ((WebApplicationException) exception).getResponse().getStatus();
            }
            return Response.status(code)
                    .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
                    .build();
        }

    }



}
