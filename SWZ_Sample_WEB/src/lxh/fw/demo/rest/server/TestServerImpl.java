package lxh.fw.demo.rest.server;

import org.springframework.stereotype.Service;

@Service
public class TestServerImpl implements TestServer {
	
	public String sayHello(String name) {
		return "hello：" + name;
	}

}
