package org.mybatis.generator.custom.base;

import java.util.List;

/**
 * @author lzj
 * @date 2018/1/7
 */
public interface BaseMapper<T extends BaseEntity> {

    int insert(T entity);

    int delete(Long id);

    int update(T entity);

    List<T> selectAll();

    T selectById(Long id);

    List<T> selectByParam(T entity);

    Integer selectCountByParam(T entity);



}
