package com.redwit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 角色表
 * </p>
 *
 * @author yangjiang
 * @since 2022-07-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TestNum implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键id
     */
      @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 角色码
     */
    private Integer courtCode;

    /**
     * 角色名(调解员、三进化解员)
     */
    private Integer onlineMediationCount;

    /**
     * 角色类型：0菜单，1功能点
     */
    private Integer beforeCaseMediationCount;

    /**
     * 0正常，1删除
     */
    private Integer inCaseMediationCount;

    /**
     * 0正常，1删除
     */
    private Integer detailMediationCount;

    /**
     * 0正常，1删除
     */
    private Integer skipMediationCourtCount;

    /**
     * 0正常，1删除
     */
    private Integer successTotal;

    /**
     * 0正常，1删除
     */
    private String skipId;


}
