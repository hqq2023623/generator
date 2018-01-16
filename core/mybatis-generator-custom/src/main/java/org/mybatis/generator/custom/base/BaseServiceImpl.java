package org.mybatis.generator.custom.base;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/15
 */
public class BaseServiceImpl<T extends BaseEntity> implements IBaseService<T> {

    private BaseMapper<T> baseMapper;

    public BaseMapper<T> getBaseMapper() {
        return baseMapper;
    }

    public void setBaseMapper(BaseMapper<T> baseMapper) {
        this.baseMapper = baseMapper;
    }

    @Override
    public int insert(T entity) throws Exception {
        return baseMapper.insert(entity);
    }

    @Override
    public int delete(Long id) throws Exception {
        return baseMapper.delete(id);
    }

    @Override
    public int update(T entity) throws Exception {
        return baseMapper.update(entity);
    }

    @Override
    public List<T> selectAll() {
        return baseMapper.selectAll();
    }

    @Override
    public T selectById(Long id) {
        return baseMapper.selectById(id);
    }

    @Override
    public T selectOne(T entity) {
        return baseMapper.selectOne(entity);
    }

    @Override
    public T searchOne(T entity) {
        return baseMapper.searchOne(entity);
    }

    @Override
    public List<T> selectList(T entity) {
        return baseMapper.selectList(entity);
    }

    @Override
    public PageResult<T> pageSelect(T entity) {
        List<T> rows = baseMapper.selectList(entity);
        Integer count = baseMapper.selectCount(entity);
        return new PageResult(count, rows);
    }

    @Override
    public int selectCountByParam(T entity) {
        return baseMapper.selectCount(entity);
    }

    @Override
    public List<T> searchByParam(T entity) {
        return baseMapper.searchList(entity);
    }

    @Override
    public PageResult<T> searchPage(T entity) {
        List<T> rows = baseMapper.searchList(entity);
        Integer count = baseMapper.searchCount(entity);
        return new PageResult(count, rows);
    }

    @Override
    public int searchCountByParam(T entity) {
        return baseMapper.searchCount(entity);
    }
}
