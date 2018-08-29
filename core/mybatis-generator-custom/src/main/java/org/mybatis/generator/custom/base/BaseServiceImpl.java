package org.mybatis.generator.custom.base;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/15
 */
public abstract class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {

    public abstract BaseMapper<T> getBaseMapper();


    @Override
    public int insert(T entity) throws Exception {
        return getBaseMapper().insert(entity);
    }

    @Override
    public int delete(Long id) throws Exception {
        return getBaseMapper().delete(id);
    }

    @Override
    public int update(T entity) throws Exception {
        return getBaseMapper().update(entity);
    }

    @Override
    public List<T> selectAll() {
        return getBaseMapper().selectAll();
    }

    @Override
    public T selectById(Long id) {
        return getBaseMapper().selectById(id);
    }

    @Override
    public T selectOne(T entity) {
        return getBaseMapper().selectOne(entity);
    }

    @Override
    public T searchOne(T entity) {
        return getBaseMapper().searchOne(entity);
    }

    @Override
    public List<T> selectList(T entity) {
        return getBaseMapper().selectList(entity);
    }

    @Override
    public PageResult<T> pageSelect(T entity) {
        List<T> rows = getBaseMapper().selectList(entity);
        Integer count = getBaseMapper().selectCount(entity);
        return new PageResult(count, rows);
    }

    @Override
    public int selectCountByParam(T entity) {
        return getBaseMapper().selectCount(entity);
    }

    @Override
    public List<T> searchByParam(T entity) {
        return getBaseMapper().searchList(entity);
    }

    @Override
    public PageResult<T> searchPage(T entity) {
        List<T> rows = getBaseMapper().searchList(entity);
        Integer count = getBaseMapper().searchCount(entity);
        return new PageResult(count, rows);
    }

    @Override
    public int searchCountByParam(T entity) {
        return getBaseMapper().searchCount(entity);
    }
}
