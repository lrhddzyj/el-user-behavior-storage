package com.hyxt.dto;


import lombok.Data;
import lombok.ToString;

/**
 * 电商用户浏览商品行为
 *
 * @author wuzh@hyxt.com
 */
@Data
@ToString
public class FansGoodsBehaviorMessageDTO {

    /**
     * 业务编号
     */
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
    private Integer viewStatus = 0;
    /**
     * 是否购买:0=否1=是
     */
    private Boolean isBuy = false;
    /**
     * 是否收藏:0=否1=是
     */
    private Boolean isKeep = false;
    /**
     * 停留时长(秒)
     */
    private Integer liveTime = 0;
    /**
     * 浏览日期:yyyy-MM-dd
     */
    private String viewDate;
    /**
     * 浏览时间:HH:mm:ss
     */
    private String viewTime;
    /**
     * 浏览次数
     */
    private Integer viewCount;
    /**
     * 浏览平均时长
     */
    private Integer liveTimeAvg;
    /**
     * 创建时间
     *
     * @since V1.2.4
     */
    private String createTime;
    /**
     * 数据来源：1=公众号（默认）,2=小程序
     *
     * @since V1.2.4
     */
    private Integer dataSource = 1;

    /**
     * 0：活动广场  1：活动首页  2：商城首页  3：商品详情  4：商品列表 5.文章分享 ，6.文章阅读
     */
    private Integer type;

    /**
     * App来源 1 -> 微商城, 2 -> 零推跟服
     */
    private Integer appSource;


    /**
     * 跳转路径 type 为 5、6时使用
     */
    private String redirectUrl;
}
