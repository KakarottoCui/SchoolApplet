package com.kbz1121.MySchool.entity;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Data;
/**
 * (ScDept)实体类
 *
 * @author wzq
 * @since 2022-03-19 21:59:53
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ScDept implements Serializable {
    private static final long serialVersionUID = -59978798957587680L;
    /**
     * 自增主键
     */
    private Integer id;
    /**
     * 部门名
     */
    private String name;
    /**
     * 部门编码
     */
    private String deptNo;
    /**
     * 部门等级，0校级 1学院级 2社团级
     */
    private Integer level;
    /**
     * 上级部门ID，用于确定部门从属关系
     */
    private Integer deptFk;
    /**
     * 有效标识
     */
    private Integer validFlag;


}

