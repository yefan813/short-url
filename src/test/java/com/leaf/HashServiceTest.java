package com.leaf;

import com.leaf.utils.MathUtils;
import org.apache.commons.codec.digest.MurmurHash3;
import org.junit.Test;

public class HashServiceTest {

    @Test
    public void generateHash() {
        String url = "https://yefan813.github.io/";
        long hash = MurmurHash3.hash32x86(url.getBytes());
        System.out.println(hash);
        System.out.println(MathUtils._10_to_62(hash));
    }
}
