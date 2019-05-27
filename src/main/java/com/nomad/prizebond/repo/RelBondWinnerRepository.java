package com.nomad.prizebond.repo;

import com.nomad.prizebond.core.Bond;
import com.nomad.prizebond.core.RelBondWinner;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import io.quarkus.panache.common.Sort;
import org.jboss.resteasy.annotations.jaxrs.PathParam;

import javax.enterprise.context.ApplicationScoped;
import javax.json.Json;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Path("bond-winner")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class RelBondWinnerRepository implements PanacheRepositoryBase<RelBondWinner, Integer> {

    private static final Logger logger = LogManager.getLogger(RelBondWinnerRepository.class);

    @GET
    public List<RelBondWinner> get() {

        try {
            List<RelBondWinner> relBondWinners = RelBondWinner.listAll(Sort.by("pk_id"));
            logger.info("RelBondWinnerRepository - Getting data");
            return relBondWinners;
        } catch (Exception ex) {
            logger.error("Error - RelBondWinnerRepository - getting bond-winner data", ex);
        }
        return null;
    }

    @GET
    @Path("{id}")
    public RelBondWinner getSingle(@PathParam Long id) {
        RelBondWinner entity = RelBondWinner.findById(id);
        if (entity == null) {
            throw new WebApplicationException("RelBondWinnerRepository - with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(RelBondWinner relBondWinner) {
        if (relBondWinner.id != null) {
            throw new WebApplicationException("RelBondWinnerRepository - Id was invalidly set on request.", 422);
        }

        relBondWinner.persist();
        return Response.ok(relBondWinner).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public RelBondWinner update(@PathParam Long id, RelBondWinner relBondWinner) {

        RelBondWinner entity = RelBondWinner.findById(id);

        if (entity == null) {
            throw new WebApplicationException("RelBondWinnerRepository - with id of " + id + " does not exist.", 404);
        }

        entity.status = relBondWinner.status;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        RelBondWinner entity = RelBondWinner.findById(id);
        if (entity == null) {
            throw new WebApplicationException("RelBondWinnerRepository - with id of " + id + " does not exist.", 404);
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
