package com.kbz1121.MySchool.service;

import com.kbz1121.MySchool.entity.ScPic;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ScPic)表服务接口
 *
 * @author wzq
 * @since 2022-03-21 21:05:14
 */
public interface ScPicService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScPic queryById(Integer id);

    /**
     * 分页查询
     *
     * @param scPic 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ScPic> queryByPage(ScPic scPic, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param scPic 实例对象
     * @return 实例对象
     */
    ScPic insert(ScPic scPic);

    /**
     * 修改数据
     *
     * @param scPic 实例对象
     * @return 实例对象
     */
    ScPic update(ScPic scPic);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
