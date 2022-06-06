package com.redwit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redwit.dto.BookDTO;
import com.redwit.entity.Book;
import com.redwit.mapper.BookMapper;
import com.redwit.service.BookService;
import com.redwit.vo.BookVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 书籍表 服务实现类
 * </p>
 *
 * @author yangjiang
 * @since 2022-06-06
 */
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {

    @Autowired
    private BookMapper bookMapper;

    private static final String BASE_INSERT_SQL = "INSERT INTO book (book_cn_name,book_origin_name,author_cn_name,author_origin_name,country,book_language,category," +
            "book_type,publishing_house,isbn,publishing_time,page_size,text_size,price,purchase_price,is_purchase,is_read,create_time,last_update_time)" +
            " VALUES\n";

    @Override
    public List<BookVO> listBooks(BookDTO bookDTO) {
        List<Book> books = bookMapper.selectList(new QueryWrapper<>());
        return books.stream().map(book -> {
            BookVO bookVO = new BookVO();
            BeanUtils.copyProperties(book, bookVO);
            return bookVO;
        }).collect(Collectors.toList());
    }

    @Override
    public int addBook(BookDTO bookDTO) {
        Book book = new Book();
        BeanUtils.copyProperties(bookDTO, book);
        //生成sql，当做备份
        this.insertLog(book);
        return bookMapper.insert(book);
    }

    private void insertLog(Book book) {
        String sql = BASE_INSERT_SQL +
                "\t (provinceCode,'accessId',1,'com.xsy.mediation.component.strategy.process.conciliation.apply.WSTjApplyDocRequestStrategy','remarkValue申请出具调解书'),\n";
        this.writeFile(sql);
    }

    private void writeFile(String sql) {
        FileOutputStream fileOutputStream = null;
        try {
            fileOutputStream = new FileOutputStream("src/main/resources/db/insert.sql");
            FileChannel fileChannel = fileOutputStream.getChannel();
            int bufferSize = 16 , readLens;
            byte[] byteContext = sql.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            for (int i = 0; i < byteContext.length; i += bufferSize) {
                if (byteContext.length > i + bufferSize){
                    readLens = bufferSize;
                }else{
                    readLens = byteContext.length - i;
                }
                byteBuffer.clear();
                byteBuffer.put(byteContext,i,readLens);
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally{
            if(fileOutputStream!=null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

