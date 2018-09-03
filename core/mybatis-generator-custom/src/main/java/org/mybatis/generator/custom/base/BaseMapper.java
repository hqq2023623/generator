package org.mybatis.generator.custom.base;

import java.io.Serializable;
import java.util.List;

/**
 * @author lzj
 * @date 2018/1/7
 */
public interface BaseMapper<ID extends Serializable,T extends BaseEntity> {

    int insert(T entity) throws Exception;

    int delete(ID id) throws Exception;

    int update(T entity) throws Exception;

    /**
     * 查询所有
     *
     * @return
     */
    List<T> selectAll();

    /**
     * ID查询
     *
     * @param id
     * @return
     */
    T selectById(ID id);

    /**
     * 精确查询单条记录
     *
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * 列表查询
     *
     * @param entity
     * @return
     */
    List<T> selectList(T entity);

    /**
     * 列表统计
     *
     * @param entity
     * @return
     */
    int selectCount(T entity);

    /**
     * 模糊查询单条记录
     *
     * @param entity
     * @return
     */
    T searchOne(T entity);

    /**
     * 模糊查询
     *
     * @param entity
     * @return
     */
    List<T> searchList(T entity);

    /**
     * 模糊查询统计
     *
     * @param entity
     * @return
     */
    int searchCount(T entity);

}