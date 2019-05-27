package com.nomad.prizebond.repo;

import com.nomad.prizebond.core.AppUser;
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

@Path("app-users")
@ApplicationScoped
@Produces("application/json")
@Consumes("application/json")
public class AppUserRepository implements PanacheRepositoryBase<AppUser, Integer> {

    private static final Logger logger = LogManager.getLogger(AppUserRepository.class);

    @GET
    public List<AppUser> get() {

        try {
            List<AppUser> appUserList = AppUser.listAll(Sort.by("user_name"));
            appUserList.forEach(x -> x.password = "");
            logger.info("Getting app user data");
            return appUserList;
        } catch (Exception ex) {
            logger.error("Error getting app_user data", ex);
        }
        return null;
    }

    @GET
    @Path("{id}")
    public AppUser getSingle(@PathParam Long id) {
        AppUser entity = AppUser.findById(id);
        if (entity == null) {
            throw new WebApplicationException("AppUser with id of " + id + " does not exist.", 404);
        }
        return entity;
    }

    @POST
    @Transactional
    public Response create(AppUser appUser) {
        if (appUser.id != null) {
            throw new WebApplicationException("Id was invalidly set on request.", 422);
        }

        appUser.persist();
        return Response.ok(appUser).status(201).build();
    }

    @PUT
    @Path("{id}")
    @Transactional
    public AppUser update(@PathParam Long id, AppUser appUser) {
        if (appUser.userName == null) {
            throw new WebApplicationException("App User Name was not set on request.", 422);
        }

        AppUser entity = AppUser.findById(id);

        if (entity == null) {
            throw new WebApplicationException("AppUser with id of " + id + " does not exist.", 404);
        }

        entity.status = appUser.status;

        return entity;
    }

    @DELETE
    @Path("{id}")
    @Transactional
    public Response delete(@PathParam Long id) {
        AppUser entity = AppUser.findById(id);
        if (entity == null) {
            throw new WebApplicationException("AppUser with id of " + id + " does not exist.", 404);
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
