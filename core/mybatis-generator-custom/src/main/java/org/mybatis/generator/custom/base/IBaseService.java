package org.mybatis.generator.custom.base;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/15
 */
public interface IBaseService<T extends BaseEntity> {

    int insert(T entity) throws Exception;

    int delete(Long id) throws Exception;

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
    T selectById(Long id);

    /**
     * 精确查询单条记录
     *
     * @param entity
     * @return
     */
    T selectOne(T entity);

    /**
     * 模糊查询单条记录
     *
     * @param entity
     * @return
     */
    T searchOne(T entity);

    /**
     * 列表精确查询
     *
     * @param entity
     * @return
     */
    List<T> selectList(T entity);

    /**
     * 分页精确查询
     *
     * @param entity
     * @return
     */
    PageResult<T> pageSelect(T entity);

    /**
     * 列表统计
     *
     * @param entity
     * @return
     */
    int selectCountByParam(T entity);

    /**
     * 模糊查询
     *
     * @param entity
     * @return
     */
    List<T> searchByParam(T entity);

    /**
     * 分页模糊查询
     *
     * @param entity
     * @return
     */
    PageResult<T> searchPage(T entity);

    /**
     * 模糊查询统计
     *
     * @param entity
     * @return
     */
    int searchCountByParam(T entity);


}
