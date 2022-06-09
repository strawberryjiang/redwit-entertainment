package com.redwit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.redwit.dto.BookDTO;
import com.redwit.entity.Book;
import com.redwit.mapper.BookMapper;
import com.redwit.service.BookService;
import com.redwit.vo.BookVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.util.IOUtils;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
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
@Slf4j
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

    @Override
    public void importFile(BookDTO bookDTO) throws IOException, IllegalAccessException {
        InputStream fileInputStream = bookDTO.getFileInputStream();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            IOUtils.copy(fileInputStream, baos, fileInputStream.available());
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        InputStream firstClone = new ByteArrayInputStream(baos.toByteArray());
        InputStream secondClone = new ByteArrayInputStream(baos.toByteArray());

        bookDTO.setFileInputStream(firstClone);

        //校验excel内容并构造 excelWorkBook
        Workbook workbook = checkFileAndBuildWorkBook(bookDTO);
        List<Book> books;
        // 3 解析封装案件详情
        books = parseFile(workbook);
        books.forEach(book -> {
            book.setCreateTime(new Date());
            bookMapper.insert(book);
        });
        System.out.println(books);


    }


    private List<Book> parseFile(Workbook workbook) throws IllegalAccessException {
        // 解析文件
        Sheet sheet;
        Row row;
        String cellData;
        List<Book> books = null;
        if (workbook != null) {
            //用来存放表中数据
            books = new ArrayList();
            //获取第一个sheet
            sheet = workbook.getSheetAt(0);
            //获取最大行数
            int rowNum = sheet.getPhysicalNumberOfRows();
            //获取第一行
            row = sheet.getRow(1);
            Row tagRow = sheet.getRow(0);
            //获取最大列数
            int column = row.getPhysicalNumberOfCells();
            // 遍历每一行,一行就是一个book对象
            for (int i = 1; i < rowNum; i++) {
                Book book = new Book();
                row = sheet.getRow(i);
                Field[] fields = Book.class.getDeclaredFields();
                if (row != null) {
                    // 遍历每一列,cellData就是每一列的值
                    for (int j = 0; j < column; j++) {
                        Field field = fields[j + 2];
                        String titleTag = tagRow.getCell(j).getRichStringCellValue().getString();
                        // 获取单元格内容
                        cellData = (String) getCellFormatValue(row.getCell(j));

                        // 如果字段为空
                        if (StringUtils.isBlank(cellData)) {
                        }
                        cellData = cellData.trim();
                        field.setAccessible(true);
                        field.set(book, cellData);
                    }
                }
                books.add(book);
            }
        }
        return books;

    }


    private Object getCellFormatValue(Cell cell) {
        Object cellValue = null;
        if (cell != null) {
            //判断cell类型
            switch (cell.getCellType()) {
                case NUMERIC: {
                    String tempValue = String.valueOf(cell.getNumericCellValue());
                    if (StringUtils.isNotBlank(tempValue) && tempValue.indexOf(".") > 0) {
                        String[] split = tempValue.split("\\.");
                        cellValue = split[0];
                    }
                    break;
                }
                case FORMULA: {
                    //判断cell是否为日期格式
                    if (DateUtil.isCellDateFormatted(cell)) {
                        //转换为日期格式YYYY-mm-dd
                        cellValue = cell.getDateCellValue();
                    } else {
                        //数字
                        cellValue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING: {
                    cellValue = cell.getRichStringCellValue().getString();
                    break;
                }
                default:
                    cellValue = "";
            }
        } else {
            cellValue = "";
        }
        return cellValue;
    }

    private Workbook checkFileAndBuildWorkBook(BookDTO bookDTO) {
        String fileFullName = bookDTO.getFileFullName();
        String extString = fileFullName.substring(fileFullName.lastIndexOf("."));
        InputStream inputStream = bookDTO.getFileInputStream();
        Workbook workbook = null;
        // 文件
        if (Objects.isNull(inputStream)) {
            throw new RuntimeException("文件为空");
        }

        // 校验文件大小
        try {
            int available = inputStream.available();
            if (available > 1024 * 1024 * 10) {
                throw new RuntimeException("文件大小超过10M");
            }
        } catch (IOException e) {
            throw new RuntimeException("文件规格校验异常");
        }

        // 文件格式
        try {
            if (".xls".equals(extString)) {
                workbook = new HSSFWorkbook(inputStream);
            } else if (".xlsx".equals(extString)) {
                workbook = new XSSFWorkbook(inputStream);
            } else {
                throw new RuntimeException("文件名后缀名非法");
            }
        } catch (IOException e) {
            throw new RuntimeException("文件流损坏解析异常");
        }

        // 获取第一个sheet
        Sheet firstSheet = workbook.getSheetAt(0);
        // 获取最大行数
        int rowNum = firstSheet.getPhysicalNumberOfRows();
        // 校验业务数据是否为空
        if (rowNum <= 1) {
            throw new RuntimeException("文件内容为空");
        }
        // 校验sheet首行row表头是否合法
        Row firstRow = firstSheet.getRow(0);
        // 对第一行表头进行数量校验
//        DisputeExcelItemEnum[] disputeExcelcColumns = DisputeExcelItemEnum.values();
//        int columnNum = firstRow.getPhysicalNumberOfCells();
//        if (columnNum != disputeExcelcColumns.length) {
//            throw new RuntimeException("文件表头长度非法");
//        }
//        // 对第一行表头文字进行校验(表头单元格需要与columns一一对应)
//        for (int j = 0; j < columnNum; j++) {
//            String cellValueString = firstRow.getCell(j).getRichStringCellValue().getString();
//            if (!StringUtils.equals(cellValueString, disputeExcelcColumns[j].getDesc())) {
//               throw new RuntimeException("文件表头字段非法");
//            }
//        }
        return workbook;
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
            int bufferSize = 16, readLens;
            byte[] byteContext = sql.getBytes();
            ByteBuffer byteBuffer = ByteBuffer.allocate(bufferSize);
            for (int i = 0; i < byteContext.length; i += bufferSize) {
                if (byteContext.length > i + bufferSize) {
                    readLens = bufferSize;
                } else {
                    readLens = byteContext.length - i;
                }
                byteBuffer.clear();
                byteBuffer.put(byteContext, i, readLens);
                byteBuffer.flip();
                fileChannel.write(byteBuffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}

