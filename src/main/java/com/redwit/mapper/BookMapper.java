package com.redwit.mapper;

import com.redwit.entity.Book;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 * 书籍表 Mapper 接口
 * </p>
 *
 * @author yangjiang
 * @since 2022-06-06
 */
@Mapper
public interface BookMapper extends BaseMapper<Book> {

}
