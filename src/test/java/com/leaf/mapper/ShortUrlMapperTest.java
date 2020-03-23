package com.leaf.mapper;

import com.leaf.domain.ShortUrl;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.FileNotFoundException;

public class ShortUrlMapperTest {
    private static ShortUrlMapper mapper;

    @BeforeClass
    public static void setUpMybatisDatabase() {
        SqlSessionFactory builder = new SqlSessionFactoryBuilder().build(ShortUrlMapperTest.class.getClassLoader().getResourceAsStream("mybatisTestConfiguration/ShortUrlMapperTestConfiguration.xml"));
        //you can use builder.openSession(false) to not commit to database
        mapper = builder.getConfiguration().getMapper(ShortUrlMapper.class, builder.openSession(true));
    }

    @Test
    public void testFindByHashValue() throws FileNotFoundException {
        mapper.findByHashValue("123");
    }

    @Test
    public void testSaveShortUrl() {
        ShortUrl shortUrl = new ShortUrl();
        shortUrl.setHashValue("111");
        shortUrl.setUrl("wwww.baidu.com");
        mapper.insert(shortUrl);
    }
}
