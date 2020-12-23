package com.hyxt.domain.behavior.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;

import java.beans.Transient;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;
import org.springframework.transaction.annotation.Transactional;

/**
 * 粉丝足迹
 *
 * @author wuzh@hyxt.com
 * @since JDK 1.7
 */
@Data
@TableName("fans_goods_behavior")
public class FansGoodsBehavior implements Serializable {
    private static final long serialVersionUID = 2127233100288850547L;

    /**
     * 业务编号
     */

    @TableId
    private String id;
    /**
     * 商家编号
     */
    private String shopId;
    /**
     * 门店id
     */
    private String storeId;
    /**
     * 员工id
     */
    private String empId;
    /**
     * 粉丝hyId
     */
    private String hyId;
    /**
     * openId
     */
    private String openId;
    /**
     * 微信开放平台unionId
     *
     * @since V1.2.6
     */
    private String wxUnionId;
    /**
     * 粉丝昵称
     */
    private String nickName;
    /**
     * 粉丝头像
     */
    private String photo;
    /**
     * 商品ID  或  活动id
     */
    private String goodsId;
    /**
     * 商品名称  或  活动名称
     */
    private String goodsName;
    /**
     * 商品链接  或  活动链接
     */
    private String goodsUrl;
    /**
     * 商品图片链接  或  活动图片
     */
    private String goodsPicUrl;
    /**
     * 浏览状态:0=正在浏览1=已浏览
     */
    private Integer viewStatus;
    /**
     * 是否购买:0=否1=是
     */
    private Boolean isBuy;
    /**
     * 是否收藏:0=否1=是
     */
    private Boolean isKeep;
    /**
     * 停留时长(秒)
     */
    private Integer liveTime;
    /**
     * 浏览次数
     */
    private Integer visitTotal;

    /**
     * 浏览日期:yyyy-MM-dd
     */
    private String viewDate;
    /**
     * 浏览时间:HH:mm:ss
     */
    private String viewTime;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 数据来源：1=公众号（默认）,2=小程序
     */
    private Integer dataSource = 2;

    /**
     * 0：活动广场  1：活动首页  2：商城首页  3：商品详情  4：商品列表 7.文章分享 ，6.文章阅读
     */
    private Integer type;

    private String redirectUrl;//跳转路径 type 为 7、6时使用

    private String activityId;

    /**
     * 信息来源 1 微商城（默认） 2 零推跟服
     */
    private int appcode;

    /**
     * 前端页面的UUID
     */
    private String uuid;






}
