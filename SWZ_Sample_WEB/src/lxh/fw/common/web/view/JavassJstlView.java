package lxh.fw.common.web.view;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.view.JstlView;

/**
 * 添加对于JavassContentNegotiatingViewResolver.java 的content-type支持
 * @author horus
 *
 * @version 1.0, 2012-11-11
 */
public class JavassJstlView extends JstlView {
    
    @Override
    protected void exposeHelpers(HttpServletRequest request) throws Exception {
        super.exposeHelpers(request);
    }
    
}
