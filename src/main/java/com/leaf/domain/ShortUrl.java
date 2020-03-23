package com.leaf.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName(value = "tb_short_url")
public class ShortUrl {
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @TableField(value = "hash_value")
    private String hashValue;

    @TableField(value = "url")
    private String url;

    /**
     * 创建时间
     */
    @TableField(value = "created")
    private Date created;

    /**
     * 更新时间
     */
    @TableField(value = "updated")
    private Date updated;

    /**
     * 是否删除 1 删除 0 未删除
     */
    @TableField(value = "yn")
    private Byte yn;

    public static final String COL_ID = "id";

    public static final String COL_HASH_VALUE = "hash_value";

    public static final String COL_URL = "url";

    public static final String COL_CREATED = "created";

    public static final String COL_UPDATED = "updated";

    public static final String COL_YN = "yn";
}