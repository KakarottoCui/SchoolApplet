package com.kbz1121.MySchool.dao;

import com.kbz1121.MySchool.entity.ScPic;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.domain.Pageable;
import java.util.List;

/**
 * (ScPic)表数据库访问层
 *
 * @author wzq
 * @since 2022-03-20 08:53:00
 */
@Mapper
public interface ScPicDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    ScPic queryById(Integer id);

    /**
     * 查询指定行数据
     *
     * @param scPic 查询条件
     * @param pageable         分页对象
     * @return 对象列表
     */
    List<ScPic> queryAllByLimit(@Param("scPic") ScPic scPic, @Param("pageable") Pageable pageable);
    
    /**
     * 查询指定行数据
     *
     * @param scPic 查询条件
     * @return 对象列表
     */
    List<ScPic> queryAll(ScPic scPic);

    /**
     * 统计总行数
     *
     * @param scPic 查询条件
     * @return 总行数
     */
    long count(ScPic scPic);

    /**
     * 新增数据
     *
     * @param scPic 实例对象
     * @return 影响行数
     */
    int insert(ScPic scPic);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScPic> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<ScPic> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<ScPic> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<ScPic> entities);

    /**
     * 修改数据
     *
     * @param scPic 实例对象
     * @return 影响行数
     */
    int update(ScPic scPic);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Integer id);

}

