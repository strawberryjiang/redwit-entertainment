package com.redwit.controller;


import com.redwit.dto.BookDTO;
import com.redwit.entity.Book;
import com.redwit.service.BookService;
import com.redwit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
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
@Slf4j
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

    /**
     * @author yangjiang
     * @creed: 上传文件
     * @date 2022/6/6 16:13
     */
    @PostMapping("/addBooksByUpload")
    public void addBooksByUpload( @RequestParam(value = "file",required = false) MultipartFile file) {
        try {
            log.debug("disputeImport upload file:{},time:{}", file.getOriginalFilename(),System.currentTimeMillis());
            BookDTO bookDTO = new BookDTO();
            bookDTO.setFileInputStream(file.getInputStream());
            bookDTO.setFileFullName(file.getOriginalFilename());
            bookService.importFile(bookDTO);
        } catch (IOException | IllegalAccessException e) {
            log.error(e.getMessage());
        }
    }

    public static void main(String[] args) throws Exception {
        Field isbn = Book.class.getDeclaredField("isbn");
        Book book = new Book();
        isbn.setAccessible(true);
        isbn.set(book,"20");
        System.out.println(book);
    }
}

