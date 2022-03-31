package com.kbz1121.MySchool.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (ScPic)实体类
 *
 * @author wzq
 * @since 2022-03-20 08:53:05
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScPic implements Serializable {
    private static final long serialVersionUID = -41379737281251994L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 类型，0文章配图，1头像,3页面
     */
    private Integer type;
    /**
     * 文章id
     */
    private Integer artFk;
    /**
     * 学生学号
     */
    private String userFk;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 图片base64
     */
    private String base64;


}

