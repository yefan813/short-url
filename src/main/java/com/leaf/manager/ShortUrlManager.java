package com.leaf.manager;

import com.leaf.domain.ShortUrl;
import com.leaf.filter.ShortUrlBloomFilter;
import com.leaf.response.Response;
import com.leaf.response.ShortUrlVO;
import com.leaf.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author yefan
 */
@Service
@Slf4j
public class ShortUrlManager {
    @Autowired
    private ShortUrlService shortUrlService;

    private static final String URL_DUPLICATED = "[DUPLICATED]";

    @Value("${url.protocol:https}")
    private String URL_PREFIX;

    @Value("${domain}")
    private String DOMAIN;

    @Autowired
    private ShortUrlBloomFilter shortUrlBloomFilter;

    @Transactional(rollbackFor = Exception.class)
    public Response<String> generateShortUrl(String url) {
        //判断 url 是否是Http https 开头
        if(StringUtils.isBlank(url)){
            throw new RuntimeException("参数错误");
        }
        url = StringUtils.trim(url).toLowerCase();
        if(!isStartWithHttpOrHttps(url)){
            url = appendHttp2Head(url,URL_PREFIX);
        }
        String hash = shortUrlService.generateShortUrl(url);
        //计算多差次拼接才能生成不重复的 hash value
        int count = 0;
        //TODO 这里多台机器可能出现并发问题，可以使用分布式锁解决

        while(true){
            if(count > 5){
                throw new RuntimeException("重试拼接url 超过限制次数");
            }
            //从 BloomFilter 查看是否存在
            boolean mightContain = shortUrlBloomFilter.mightContain(hash);
            if(!mightContain){
                log.info("============生成短链接，判断短链接不存在,可以生成对应关系!===============");
                break;
            }

            //hash 相同且长链接相同
            ShortUrl dbShortUrl = shortUrlService.findByHashValueFromLocalCache(hash);
            if(dbShortUrl.getUrl().equals(url)){
                log.info("============短链接已存在!===============");
                return Response.success(DOMAIN + hash);
            }else{
                log.warn("=======hashValue:[{}],DBUrl:[{}],currentUrl:[{}]",
                        hash,dbShortUrl.getUrl(),url);
                url = url + URL_DUPLICATED;
                hash = shortUrlService.generateShortUrl(url);
                log.warn("=======重新拼接hash:[{}],currentUrl:[{}]", hash, url);
            }
            count++;
            log.info("===========================url重复拼接字符串，次数:[{}]",count);
        }

        //入库
        ShortUrl saveBean = new ShortUrl();
        saveBean.setHashValue(hash);
        saveBean.setUrl(url);
        shortUrlService.saveShortUrl(saveBean);
        return Response.success(DOMAIN + hash);
    }


    public ShortUrlVO getRealUrlByHash(String hash) {
        //get Url by hash
        ShortUrl shortUrl = shortUrlService.findByHashValueFromLocalCache(hash);
        if(null == shortUrl){
            return null;
        }
        String realUrl = shortUrl.getUrl().replace(URL_DUPLICATED,"");
        return new ShortUrlVO(shortUrl.getHashValue(), realUrl);
    }

    public static boolean isStartWithHttpOrHttps(String url) {
       String regex = "^((https|http)?://)";
        Pattern p = Pattern.compile(regex);
        Matcher matcher = p.matcher(url);
        return matcher.find();
    }

    /**
     * url 开头拼接 http
     * @param url
     * @return
     */
    private String appendHttp2Head(String url, String prefix) {
        StringBuilder stringBuilder = new StringBuilder(prefix).append("://");
        stringBuilder.append(url);
        return stringBuilder.toString();
    }


    /**
     * 是否是有效的 url
     * @param urls
     * @return
     */
    public boolean isValidUrl(String urls) {
        boolean isurl = false;
        String regex = "(((https|http)?://)?([a-z0-9]+[.])|(www.))"
                + "\\w+[.|\\/]([a-z0-9]{0,})?[[.]([a-z0-9]{0,})]+((/[\\S&&[^,;\u4E00-\u9FA5]]+)+)?([.][a-z0-9]{0,}+|/?)";//设置正则表达式
        //对比
        Pattern pat = Pattern.compile(regex.trim());
        Matcher mat = pat.matcher(urls.trim());
        //判断是否匹配
        isurl = mat.matches();
        if (isurl) {
            isurl = true;
        }
        return isurl;
    }
}
