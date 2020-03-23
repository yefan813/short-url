package com.leaf.manager;

import com.leaf.domain.ShortUrl;
import com.leaf.response.Response;
import com.leaf.response.ShortUrlVO;
import com.leaf.service.ShortUrlService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

public class ShortUrlManagerTest {

   @Test
    public void test_isStartWithHttpOrHttps() {
       boolean start = ShortUrlManager.isStartWithHttpOrHttps("http://www.yefan813.github.io");
       Assert.assertTrue(start);
   }
}
