package com.kbz1121.MySchool.service;

import com.kbz1121.MySchool.entity.ScLogin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (ScLogin)表服务接口
 *
 * @author wzq
 * @since 2022-03-21 21:05:06
 */
public interface ScLoginService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScLogin queryById(Integer id);

    List<ScLogin> queryAll(ScLogin scLogin);

    /**
     * 分页查询
     *
     * @param scLogin 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ScLogin> queryByPage(ScLogin scLogin, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param scLogin 实例对象
     * @return 实例对象
     */
    ScLogin insert(ScLogin scLogin);

    /**
     * 修改数据
     *
     * @param scLogin 实例对象
     * @return 实例对象
     */
    ScLogin update(ScLogin scLogin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
