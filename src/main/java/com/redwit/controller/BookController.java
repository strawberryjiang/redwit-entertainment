package com.redwit.controller;


import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.redwit.dto.BookDTO;
import com.redwit.entity.Book;
import com.redwit.entity.TestNum;
import com.redwit.service.BookService;
import com.redwit.service.TestNumService;
import com.redwit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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

    @Autowired
    private TestNumService testNumService;

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
    public void addBooksByUpload(@RequestParam(value = "file", required = false) MultipartFile file) throws IOException {
        List<Book> list = new ArrayList<>();
        EasyExcel.read(file.getInputStream(), Book.class, new AnalysisEventListener<Book>() {
                    //重写子类方法
                    @Override
                    public void invoke(Book book, AnalysisContext analysisContext) {
                        TestNum testNum = new TestNum();
                        testNum.setCourtCode(1);
                        testNum.setOnlineMediationCount(1);
                        testNum.setBeforeCaseMediationCount(1);
                        testNum.setInCaseMediationCount(1);
                        testNum.setDetailMediationCount(1);
                        testNum.setSkipMediationCourtCount(1);
                        testNum.setSuccessTotal(1);
                        testNumService.save(testNum);
                        list.add(book);
                    }

                    //重写子类方法
                    @Override
                    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

                    }

                    @Override
                    public void invokeHeadMap(Map headMap, AnalysisContext context) {
                        System.out.println(headMap);
                    }
                }
        ).doReadAll();
        bookService.saveBatch(list);
    }

    public static void main(String[] args) throws Exception {
        Field isbn = Book.class.getDeclaredField("isbn");
        Book book = new Book();
        isbn.setAccessible(true);
        isbn.set(book, "20");
        System.out.println(book);
    }
}

