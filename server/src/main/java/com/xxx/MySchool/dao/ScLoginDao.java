package com.kbz1121.MySchool.dao;

import com.kbz1121.MySchool.entity.ScLogin;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (ScLogin)表数据库访问层
 *
 * @author wzq
 * @since 2022-03-19 22:01:37
 */
@Mapper
public interface ScLoginDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScLogin queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param scLogin 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ScLogin> queryAllByLimit(@Param("scLogin") ScLogin scLogin, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param scLogin 查询条件
     * @return 对象列表
     */
    List<ScLogin> queryAll(ScLogin scLogin);

    /**
     * 统计总行数
     *
     * @param scLogin 查询条件
     * @return 总行数
     */
    long count(ScLogin scLogin);

    /**
     * 新增数据
     *
     * @param scLogin 实例对象
     * @return 影响行数
     */
    int insert(ScLogin scLogin);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScLogin> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ScLogin> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScLogin> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ScLogin> entities);

    /**
     * 修改数据
     *
     * @param scLogin 实例对象
     * @return 影响行数
     */
    int update(ScLogin scLogin);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

