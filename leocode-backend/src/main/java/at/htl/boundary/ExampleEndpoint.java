package at.htl.boundary;

import at.htl.dto.ExampleDTO;
import at.htl.entity.Example;
import at.htl.repository.ExampleRepository;
import at.htl.repository.LeocodeFileRepository;
import org.jboss.logging.Logger;
import org.jboss.resteasy.plugins.providers.multipart.MultipartFormDataInput;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/example")
public class ExampleEndpoint {

    @Inject
    Logger log;

    @Inject
    ExampleRepository exampleRepository;

    @Inject
    LeocodeFileRepository leocodeFileRepository;

    @POST
    @Path("/newExample")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createExample(MultipartFormDataInput input) {
        try {
            Example example = exampleRepository.createExampleFromMultipart(input);
            System.out.println("createExample(" + example.toString() + ")");
            return Response.ok(example).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok("Something went wrong!").build();
        }
    }


    @GET
    @Path("/list")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listAll() {
        return Response.ok(exampleRepository.listAll()).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response getById(@PathParam("id") long id) {
        //try {
        Example example = exampleRepository.findById(id);
        //List<LeocodeFile> files = leocodeFileRepository.findByExample(example);
        return Response.ok(new ExampleDTO(example)).build();
        //} catch (Exception e) {
        //    return Response.noContent().build();
        //}
    }
}
