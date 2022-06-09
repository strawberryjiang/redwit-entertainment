package com.redwit.entity;

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

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 书的中文名
     */
    private String bookCnName;

    /**
     * 书的原名
     */
    private String bookOriginName;

    /**
     * 作者的中文名
     */
    private String authorCnName;

    /**
     * 作者的原名
     */
    private String authorOriginName;

    /**
     * 原版书籍（作者）国家
     */
    private String country;

    /**
     * 原版语言
     */
    private String bookLanguage;

    /**
     * 类别（一级）
     */
    private String category;

    /**
     * 类型（二级）
     */
    private String bookType;

    /**
     * 出版社
     */
    private String publishingHouse;

    /**
     * ISBN
     */
    private String isbn;

    /**
     * 出版时间
     */
    private String publishingTime;

    /**
     * 页数
     */
    private String pageSize;

    /**
     * 字数
     */
    private String textSize;

    /**
     * 售价
     */
    private String price;

    /**
     * 实际购买价格
     */
    private String purchasePrice;

    /**
     * 是否已经购买
     */
    private String isPurchase;

    /**
     * 是否已经看完
     */
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
