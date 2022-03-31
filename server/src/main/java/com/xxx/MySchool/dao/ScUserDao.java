package com.kbz1121.MySchool.dao;

import com.kbz1121.MySchool.entity.ScUser;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (ScUser)表数据库访问层
 *
 * @author wzq
 * @since 2022-03-19 22:02:08
 */
@Mapper
public interface ScUserDao {

    /**
     * 通过ID查询单条数据
     *
     * @param userNo 主键
     * @return 实例对象
     */
    ScUser queryById(String userNo);

    /**
     * 查询指定行数据
     *
     * @param scUser 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ScUser> queryAllByLimit(@Param("scUser") ScUser scUser, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param scUser 查询条件
     * @return 对象列表
     */
    List<ScUser> queryAll(ScUser scUser);

    /**
     * 统计总行数
     *
     * @param scUser 查询条件
     * @return 总行数
     */
    long count(ScUser scUser);

    /**
     * 新增数据
     *
     * @param scUser 实例对象
     * @return 影响行数
     */
    int insert(ScUser scUser);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScUser> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ScUser> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScUser> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ScUser> entities);

    /**
     * 修改数据
     *
     * @param scUser 实例对象
     * @return 影响行数
     */
    int update(ScUser scUser);

    int updateByUid(ScUser scUser);
    /**
     * 通过主键删除数据
     *
     * @param userNo 主键
     * @return 影响行数
     */
    int deleteById(String userNo);

}

