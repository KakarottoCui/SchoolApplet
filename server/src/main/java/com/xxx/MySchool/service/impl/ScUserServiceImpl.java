package com.kbz1121.MySchool.service.impl;

import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.dao.ScUserDao;
import com.kbz1121.MySchool.service.ScUserService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ScUser)表服务实现类
 *
 * @author wzq
 * @since 2022-03-29 02:58:05
 */
@Service("scUserService")
public class ScUserServiceImpl implements ScUserService {
    @Resource
    private ScUserDao scUserDao;

    /**
     * 通过ID查询单条数据
     *
     * @param userNo 主键
     * @return 实例对象
     */
    @Override
    public ScUser queryById(String userNo) {
        return this.scUserDao.queryById(userNo);
    }

    /**
     * 查询全部数据
     *
     * @param userNo 主键
     * @return 实例对象
     */
    @Override
    public List<ScUser> queryAll(ScUser scUser) {
        return this.scUserDao.queryAll(scUser);
    }


    /**
     * 分页查询
     *
     * @param scUser 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<ScUser> queryByPage(ScUser scUser, PageRequest pageRequest) {
        long total = this.scUserDao.count(scUser);
        return new PageImpl<>(this.scUserDao.queryAllByLimit(scUser, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param scUser 实例对象
     * @return 实例对象
     */
    @Override
    public ScUser insert(ScUser scUser) {
        this.scUserDao.insert(scUser);
        return scUser;
    }

    /**
     * 修改数据
     *
     * @param scUser 实例对象
     * @return 实例对象
     */
    @Override
    public ScUser update(ScUser scUser) {
        this.scUserDao.update(scUser);
        return this.queryById(scUser.getUserNo());
    }

    /**
     * 通过主键删除数据
     *
     * @param userNo 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(String userNo) {
        return this.scUserDao.deleteById(userNo) > 0;
    }
}
