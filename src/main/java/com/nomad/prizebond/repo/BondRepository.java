package com.nomad.prizebond.repo;

import com.nomad.prizebond.core.Bond;

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

@Path("bonds")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class BondRepository implements PanacheRepositoryBase<Bond, Integer> {

    private static final Logger logger = LogManager.getLogger(BondRepository.class);

    @GET
    public List<Bond> get() {

        try {
            List<Bond> bondList = Bond.listAll(Sort.by("pk_id"));
            logger.info("Bond - Getting data");
            return bondList;
        } catch (Exception ex) {
            logger.error("Error - Bond - getting bond data", ex);
        }
        return null;
    }

    @GET
    @Path("{id}")
    public Bond getSingle(@PathParam Long id) {
        Bond entity = Bond.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Bond - with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(Bond bond) {
        if (bond.id != null) {
            throw new WebApplicationException("Bond - Id was invalidly set on request.", 422);
        }

        bond.persist();
        return Response.ok(bond).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public Bond update(@PathParam Long id, Bond bond) {

        Bond entity = Bond.findById(id);

        if (entity == null) {
            throw new WebApplicationException("Bond - with id of " + id + " does not exist.", 404);
        }

        entity.status = bond.status;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        Bond entity = Bond.findById(id);
        if (entity == null) {
            throw new WebApplicationException("Bond - with id of " + id + " does not exist.", 404);
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
