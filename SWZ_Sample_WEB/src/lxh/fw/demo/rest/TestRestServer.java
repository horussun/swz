package lxh.fw.demo.rest;

import javax.annotation.Resource;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import lxh.fw.demo.rest.server.TestServer;

import org.springframework.stereotype.Service;

@Path("/test")
@Service
public class TestRestServer{
	
	@Resource(name="testServerImpl")
	private TestServer testServer;

	@GET
	@Path("/hello")
	@Produces(MediaType.APPLICATION_JSON)
	public String sayHello(@QueryParam("name") String name)
	{
		return testServer.sayHello(name);
	}
}
