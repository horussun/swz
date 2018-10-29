package lxh.fw.demo.rest;

import java.util.HashMap;
import java.util.Map;

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

import org.springframework.stereotype.Service;

import lxh.fw.demo.rest.bean.Poi;
import lxh.fw.demo.rest.server.PoiServer;

@Path("/poi")
@Service
public class PoiRestServer {
	@Resource(name = "poiServerImpl")
	private PoiServer poiServer;

	/**
	 * @param num
	 * @return
	 */
	@GET
	@Path("/list")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String, Object> getList(@QueryParam("limit") int limit, @QueryParam("currentPage") int currentPage) {
		Map<String, Object> poiList = poiServer.getList(limit, currentPage);
		return poiList;
	}

	@GET
	@Path("/get/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Poi getById(@PathParam("id") String id) {
		Poi poi = poiServer.getById(id);
		return poi;
	}

	@DELETE
	@Path("/delete/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean deleteById(@PathParam("id") String id) {
		return poiServer.deleteById(id);
	}

	@PUT
	@Path("/update")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean updatePoi(Poi poi) {

		return poiServer.updatePoi(poi);
	}

	@POST
	@Path("/add")
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addPoi(Poi poi) {
		return poiServer.addPoi(null);
	}
}
