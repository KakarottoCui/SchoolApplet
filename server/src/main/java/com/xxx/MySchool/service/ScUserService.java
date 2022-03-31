package com.kbz1121.MySchool.service;

import com.kbz1121.MySchool.entity.ScUser;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.util.List;

/**
 * (ScUser)表服务接口
 *
 * @author wzq
 * @since 2022-03-21 21:05:22
 */
public interface ScUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param userNo 主键
     * @return 实例对象
     */
    ScUser queryById(String userNo);

    /**
     * 分页查询
     *
     * @param scUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ScUser> queryByPage(ScUser scUser, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param scUser 实例对象
     * @return 实例对象
     */
    ScUser insert(ScUser scUser);

    /**
     * 修改数据
     *
     * @param scUser 实例对象
     * @return 实例对象
     */
    ScUser update(ScUser scUser);

    /**
     * 通过主键删除数据
     *
     * @param userNo 主键
     * @return 是否成功
     */
    boolean deleteById(String userNo);

    List<ScUser> queryAll(ScUser scUser);
}
