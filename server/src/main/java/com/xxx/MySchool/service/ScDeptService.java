package com.kbz1121.MySchool.service;

import com.kbz1121.MySchool.entity.ScDept;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ScDept)表服务接口
 *
 * @author wzq
 * @since 2022-03-21 21:04:56
 */
public interface ScDeptService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScDept queryById(Integer id);

    /**
     * 分页查询
     *
     * @param scDept 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ScDept> queryByPage(ScDept scDept, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param scDept 实例对象
     * @return 实例对象
     */
    ScDept insert(ScDept scDept);

    /**
     * 修改数据
     *
     * @param scDept 实例对象
     * @return 实例对象
     */
    ScDept update(ScDept scDept);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
