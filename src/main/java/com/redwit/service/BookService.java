package com.redwit.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.redwit.dto.BookDTO;
import com.redwit.entity.Book;
import com.redwit.vo.BookVO;

import java.util.List;

/**
 * <p>
 * 书籍表 服务类
 * </p>
 *
 * @author yangjiang
 * @since 2022-06-06
 */
public interface BookService extends IService<Book> {

    /**
     * @author yangjiang
     * @creed: 根据条件查询书籍
     * @date 2022/6/6 15:24
     */
    List<BookVO> listBooks(BookDTO book);

    int addBook(BookDTO book);
}
