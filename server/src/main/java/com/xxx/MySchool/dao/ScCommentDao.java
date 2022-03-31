package com.kbz1121.MySchool.dao;

import com.kbz1121.MySchool.entity.ScComment;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (ScComment)表数据库访问层
 *
 * @author wzq
 * @since 2022-03-29 06:40:05
 */
@Mapper
public interface ScCommentDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScComment queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param scComment 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ScComment> queryAllByLimit(@Param("scComment") ScComment scComment, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param scComment 查询条件
     * @return 对象列表
     */
    List<ScComment> queryAll(ScComment scComment);

    List<ScComment> queryByParam(ScComment scComment);

    List<ScComment> queryByAritcleParam(ScComment scComment);

    /**
     * 统计总行数
     *
     * @param scComment 查询条件
     * @return 总行数
     */
    long count(ScComment scComment);

    /**
     * 新增数据
     *
     * @param scComment 实例对象
     * @return 影响行数
     */
    int insert(ScComment scComment);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScComment> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ScComment> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScComment> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ScComment> entities);

    /**
     * 修改数据
     *
     * @param scComment 实例对象
     * @return 影响行数
     */
    int update(ScComment scComment);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

