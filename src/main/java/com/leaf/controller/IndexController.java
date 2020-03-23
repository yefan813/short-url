package com.leaf.controller;

import com.leaf.manager.ShortUrlManager;
import com.leaf.response.Response;
import com.leaf.response.ShortUrlVO;
import com.sun.deploy.net.HttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 欢迎页
 * @author yefan
 */
@Controller
public class IndexController {

    @Autowired
    private ShortUrlManager shortUrlManager;

    @PostMapping("/generateShortUrl")
    @ResponseBody
    public Response<String> generateShortUrl(@NotNull  String url) {
        if(!shortUrlManager.isValidUrl(url)){
           return Response.failed("-1", "无效的url");
        }
        return shortUrlManager.generateShortUrl(url);
    }


    @RequestMapping("/{hash}")
    public String redirect(HttpServletResponse httpServletResponse,@PathVariable("hash") String hash) throws IOException {
        ShortUrlVO shortUrlVO = shortUrlManager.getRealUrlByHash(hash);
        if(null == shortUrlVO){
            return "短链接不存在!";
        }
        return "redirect:" + shortUrlVO.getUrl();
    }



}
