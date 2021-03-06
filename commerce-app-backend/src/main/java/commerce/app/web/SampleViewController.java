package commerce.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author chanwook
 */
@Controller
public class SampleViewController {

    @Autowired
    Environment env;

    @RequestMapping("/sample")
    public String client(ModelMap model) {
        return "sample";
    }
}
