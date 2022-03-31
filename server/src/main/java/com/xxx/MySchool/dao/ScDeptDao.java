package com.kbz1121.MySchool.dao;

import com.kbz1121.MySchool.entity.ScDept;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (ScDept)表数据库访问层
 *
 * @author wzq
 * @since 2022-03-19 21:59:53
 */
@Mapper
public interface ScDeptDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScDept queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param scDept 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ScDept> queryAllByLimit(@Param("scDept") ScDept scDept, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param scDept 查询条件
     * @return 对象列表
     */
    List<ScDept> queryAll(ScDept scDept);

    /**
     * 统计总行数
     *
     * @param scDept 查询条件
     * @return 总行数
     */
    long count(ScDept scDept);

    /**
     * 新增数据
     *
     * @param scDept 实例对象
     * @return 影响行数
     */
    int insert(ScDept scDept);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScDept> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ScDept> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScDept> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ScDept> entities);

    /**
     * 修改数据
     *
     * @param scDept 实例对象
     * @return 影响行数
     */
    int update(ScDept scDept);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

