package com.redwit.controller;


import com.redwit.dto.BookDTO;
import com.redwit.service.BookService;
import com.redwit.vo.BookVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>
 * 书籍表 前端控制器
 * </p>
 *
 * @author yangjiang
 * @since 2022-06-06
 */
@RestController
@RequestMapping("/book")
public class BookController {

    @Autowired
    private BookService bookService;

    @PostMapping("/listBooks")
    public List<BookVO> listBooks(@RequestBody BookDTO book) {
        return bookService.listBooks(book);
    }

    @PostMapping("/addBook")
    public int addBook(@RequestBody BookDTO book) {
        return bookService.addBook(book);
    }
}

