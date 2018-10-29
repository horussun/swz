package lxh.fw.demo.rest.bean;

import java.io.Serializable;

import org.apache.wink.common.annotations.Asset;

public class Poi implements Serializable {

	private static final long serialVersionUID = 5168683219205607726L;
	private String name;
	private String lx;
	private String id;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLx() {
		return lx;
	}

	public void setLx(String lx) {
		this.lx = lx;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Poi(String name, String lx, String id) {
		super();
		this.name = name;
		this.lx = lx;
		this.id = id;
	}

	public Poi() {
		super();
	}

}
