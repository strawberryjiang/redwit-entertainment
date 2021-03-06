package com.redwit.dto;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.InputStream;
import java.io.Serializable;
import java.math.BigDecimal;
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
public class BookDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    private InputStream fileInputStream;
    private String fileFullName;

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
    private Integer isbn;

    /**
     * 出版时间
     */
    private Date publishingTime;

    /**
     * 页数
     */
    private Integer pageSize;

    /**
     * 字数
     */
    private Integer textSize;

    /**
     * 售价
     */
    private BigDecimal price;

    /**
     * 实际购买价格
     */
    private BigDecimal purchasePrice;

    /**
     * 是否已经购买
     */
    private boolean purchase;

    /**
     * 是否已经看完
     */
    private boolean read;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date lastUpdateTime;


}
