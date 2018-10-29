package lxh.fw.demo.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * 
 * @author jiesun
 * 
 * @RequestMapping(value = "/index")
 * @SessionAttributes("currUser")
 * @SessionAttributes(types = User.class)
 * @SessionAttributes(types = {User.class,Dept.class},value={“attr1”,”attr2”})
 */
@Controller
public class IndexController {
    
    @RequestMapping(value = "/index")
    public String index(HttpServletRequest request){
        return "index";
    }
    
    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    public String list(HttpServletRequest request, Model model) {
    	model.addAttribute("output", "spring MVC");
    	return "list";
    }
}
