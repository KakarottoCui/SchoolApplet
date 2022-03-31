package com.kbz1121.MySchool.service;

import com.kbz1121.MySchool.entity.ScComment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

/**
 * (ScComment)表服务接口
 *
 * @author wzq
 * @since 2022-03-21 21:04:43
 */
public interface ScCommentService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScComment queryById(Integer id);

    /**
     * 分页查询
     *
     * @param scComment 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    Page<ScComment> queryByPage(ScComment scComment, PageRequest pageRequest);

    /**
     * 新增数据
     *
     * @param scComment 实例对象
     * @return 实例对象
     */
    ScComment insert(ScComment scComment);

    /**
     * 修改数据
     *
     * @param scComment 实例对象
     * @return 实例对象
     */
    ScComment update(ScComment scComment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Integer id);

}
