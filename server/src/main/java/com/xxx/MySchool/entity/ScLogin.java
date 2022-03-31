package com.kbz1121.MySchool.entity;

import java.util.Date;
import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (ScLogin)实体类
 *
 * @author wzq
 * @since 2022-03-19 22:01:37
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScLogin implements Serializable {
    private static final long serialVersionUID = -29800832120213209L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 微信唯一识别id
     */
    private String unionid;
    /**
     * 用户解密秘钥
     */
    private String sessionKey;
    /**
     * 用户token
     */
    private String token;
    /**
     * 自增主键
     */
    private String userFk;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 状态1有效，0过期
     */
    private Integer stat;
    /**
     * 用户数据
     */
    private String info;


}

