package com.kbz1121.MySchool.entity;

import java.util.Date;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Transient;

/**
 * (ScUser)实体类
 *
 * @author wzq
 * @since 2022-03-19 22:02:08
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScUser implements Serializable {
    private static final long serialVersionUID = 199474129910270901L;
    /**
     * 学号，主键
     */
    private String userNo;
    /**
     * 姓名
     */
    private String name;
    /**
     * 昵称
     */
    private String nick;
    /**
     * 所属部门id
     */
    private Integer deptFk;
    /**
     * 性别，0女1男
     */
    private Integer sex;
    /**
     * 专业名
     */
    private String project;
    /**
     * 有效标识
     */
    private Integer validFlag;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 0管理员1普通学生
     */
    private Integer type;
    /**
     * 头像图片id
     */
    private Integer picFk;
    /**
     * 微信用户唯一ID
     */
    @JsonIgnore
    private String unionid;

    @JsonIgnore
    @Transient
    private String token;

    @Transient
    private String pic;

}

