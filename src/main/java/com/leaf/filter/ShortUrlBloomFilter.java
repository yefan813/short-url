package com.leaf.filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;
import com.leaf.service.ShortUrlService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.List;

/**
 * 这里使用BloomFilter，每次生成短链接从 BloomFilter 判断是否存在，
 * 不存在就插入数据库，减少一次查询数据库的操作
 * 1，这里虽然可以减少数据，但是如果数据量大，容器启动时从数据库查找大量数据加载到BloomFilter
 * 可能会造成内存问题
 *
 * @author yefan
 */
@Service
@Slf4j
public class ShortUrlBloomFilter implements InitializingBean{
    private static BloomFilter<String> bloomFilter = BloomFilter.create(
            Funnels.stringFunnel(Charset.defaultCharset()), 1000000);

    @Autowired
    private ShortUrlService shortUrlService;

    public boolean mightContain(String hashValue) {
        return bloomFilter.mightContain(hashValue);
    }

    public void put(String hashValue) {
        bloomFilter.put(hashValue);
    }

    @Override
    public void afterPropertiesSet() {
        log.info("===============初始化BloomFilter......==============");
        long startTime = System.currentTimeMillis();
        long count = 0;
        //从数据加载数据到  BloomFilter
        List<String> allHashValue = shortUrlService.findAllHashValue();
        for (String hash : allHashValue) {
            bloomFilter.put(hash);
        }
        count = allHashValue.size();
        long costTime = System.currentTimeMillis() - startTime;
        log.info("===============初始化BloomFilter完成,costTime:[{}],Count:[{}]==============", costTime, count);
    }
}
