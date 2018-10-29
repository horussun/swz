package lxh.fw.demo.rest.server;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import lxh.fw.demo.rest.bean.Poi;

@Service
public class PoiServerImpl implements PoiServer {

	public PoiServerImpl() {
	}

	@Override
	public Map<String, Object> getList(int limit, int currentPage) {
		Map<String, Object> result = null;
		return result;
	}

	@Override
	public Poi getById(String id) {
		Poi poi = new Poi();
		return poi;
	}

	@Override
	public boolean addPoi(Poi poi) {

		return false;
	}

	@Override
	public boolean updatePoi(Poi poi) {

		return false;
	}

	@Override
	public boolean deleteById(String id) {

		return false;
	}
}
