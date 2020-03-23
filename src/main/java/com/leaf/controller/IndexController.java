package com.leaf.controller;

import com.leaf.manager.ShortUrlManager;
import com.leaf.request.GenerateShortUrlRequest;
import com.leaf.response.Response;
import com.leaf.response.ShortUrlVO;
import com.sun.deploy.net.HttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.io.IOException;

/**
 * 欢迎页
 * @author yefan
 */
@Controller
@RequestMapping("/url")
@Slf4j
public class IndexController {

    @Autowired
    private ShortUrlManager shortUrlManager;

    @PostMapping("/generateShortUrl")
    @ResponseBody
    public Response<String> generateShortUrl(@RequestBody @Valid GenerateShortUrlRequest request) {
        if(!shortUrlManager.isValidUrl(request.getUrl())){
            log.error("无效的url:[{}]",request.getUrl());
           return Response.failed("-1", "无效的url");
        }
        return shortUrlManager.generateShortUrl(request.getUrl());
    }


}
