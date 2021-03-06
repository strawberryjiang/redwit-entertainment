package com.redwit.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 书籍表
 * </p>
 *
 * @author yangjiang
 * @since 2022-06-06
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Book implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    @ExcelProperty("书的中文名")
    private String bookCnName;

    @ExcelProperty("书的原名")
    private String bookOriginName;

    @ExcelProperty("作者的中文名")
    private String authorCnName;

    @ExcelProperty("作者的原名")
    private String authorOriginName;

    @ExcelProperty("原版书籍（作者）国家")
    private String country;

    @ExcelProperty("原版语言")
    private String bookLanguage;

    @ExcelProperty("类别（一级）")
    private String category;

    @ExcelProperty("类型（二级）")
    private String bookType;

    @ExcelProperty("出版社")
    private String publishingHouse;

    @ExcelProperty("ISBN")
    private String isbn;

    @ExcelProperty("出版时间")
    private String publishingTime;

    @ExcelProperty("页数")
    private String pageSize;

    @ExcelProperty("字数")
    private String textSize;

    @ExcelProperty("售价")
    private String price;

    @ExcelProperty("实际购买价格")
    private String purchasePrice;

    @ExcelProperty("是否已经购买")
    private String isPurchase;

    @ExcelProperty("是否已经看完")
    private String isRead;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;


}
