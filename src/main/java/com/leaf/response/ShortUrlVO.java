package com.leaf.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author yefan
 */
@Data
@AllArgsConstructor
public class ShortUrlVO {
    private String hashValue;

    private String url;
}
