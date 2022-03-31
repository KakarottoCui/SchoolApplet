package com.kbz1121.MySchool.service.impl;

import com.kbz1121.MySchool.entity.ScLogin;
import com.kbz1121.MySchool.dao.ScLoginDao;
import com.kbz1121.MySchool.entity.ScUser;
import com.kbz1121.MySchool.service.ScLoginService;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.annotation.Resource;
import java.util.List;

/**
 * (ScLogin)表服务实现类
 *
 * @author wzq
 * @since 2022-03-29 03:01:57
 */
@Service("scLoginService")
public class ScLoginServiceImpl implements ScLoginService {
    @Resource
    private ScLoginDao scLoginDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public ScLogin queryById(Integer id) {
        return this.scLoginDao.queryById(id);
    }

    /**
     * 分页查询
     *
     * @param scLogin 筛选条件
     * @param pageRequest      分页对象
     * @return 查询结果
     */
    @Override
    public Page<ScLogin> queryByPage(ScLogin scLogin, PageRequest pageRequest) {
        long total = this.scLoginDao.count(scLogin);
        return new PageImpl<>(this.scLoginDao.queryAllByLimit(scLogin, pageRequest), pageRequest, total);
    }

    /**
     * 新增数据
     *
     * @param scLogin 实例对象
     * @return 实例对象
     */
    @Override
    public ScLogin insert(ScLogin scLogin) {
        this.scLoginDao.insert(scLogin);
        return scLogin;
    }

    /**
     * 修改数据
     *
     * @param scLogin 实例对象
     * @return 实例对象
     */
    @Override
    public ScLogin update(ScLogin scLogin) {
        this.scLoginDao.update(scLogin);
        return this.queryById(scLogin.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Integer id) {
        return this.scLoginDao.deleteById(id) > 0;
    }

    /**
     * 查询全部数据
     *
     *
     * @return 实例对象
     */
    @Override
    public List<ScLogin> queryAll(ScLogin scLogin) {
        return this.scLoginDao.queryAll(scLogin);
    }
}
