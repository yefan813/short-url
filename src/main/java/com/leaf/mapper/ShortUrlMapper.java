package com.leaf.mapper;
import java.util.List;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leaf.domain.ShortUrl;
import org.apache.ibatis.annotations.Param;

public interface ShortUrlMapper extends BaseMapper<ShortUrl> {

    ShortUrl findByHashValue(@Param("hashValue")String hashValue);

}