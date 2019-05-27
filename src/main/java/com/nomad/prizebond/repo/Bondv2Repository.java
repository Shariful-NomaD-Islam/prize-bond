package com.nomad.prizebond.repo;

import com.nomad.prizebond.core.Bondv2;

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

@Path("bondv2s")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class Bondv2Repository implements PanacheRepositoryBase<Bondv2, Integer> {

    private static final Logger logger = LogManager.getLogger(Bondv2Repository.class);

    @GET
    public List<Bondv2> get() {

        try {
            List<Bondv2> bondv2List = Bondv2.listAll(Sort.by("pk_id"));
            logger.info("Bond - Getting data");
            return bondv2List;
        } catch (Exception ex) {
            logger.error("Error - Bond - getting bond data", ex);
        }
        return null;
    }

    @GET
    @Path("{id}")
    public Bondv2 getSingle(@PathParam Long id) {
        Bondv2 entity = Bondv2.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Bondv2 - with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Bondv2 bondv2) {
        if (bondv2.id != null) {
            throw new WebApplicationException("Bondv2 - Id was invalidly set on request.", 422);
        }

        bondv2.persist();
        return Response.ok(bondv2).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Bondv2 update(@PathParam Long id, Bondv2 bondv2) {

        Bondv2 entity = Bondv2.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Bondv2 - with id of " + id + " does not exist.", 404);
        }

        entity.status = bondv2.status;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        Bondv2 entity = Bondv2.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Bondv2 - with id of " + id + " does not exist.", 404);
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
