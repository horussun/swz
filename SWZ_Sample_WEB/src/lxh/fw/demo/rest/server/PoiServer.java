package lxh.fw.demo.rest.server;

import java.util.Map;

import lxh.fw.demo.rest.bean.Poi;

public interface PoiServer {

	Map<String, Object> getList(int limit, int currentPage);

	Poi getById(String id);

	boolean addPoi(Poi poi);

	boolean updatePoi(Poi poi);

	boolean deleteById(String id);
}
