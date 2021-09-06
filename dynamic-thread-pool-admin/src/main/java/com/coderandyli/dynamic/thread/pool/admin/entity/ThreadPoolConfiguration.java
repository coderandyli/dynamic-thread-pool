package com.coderandyli.dynamic.thread.pool.admin.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 线程池配置信息
 *
 * @author lizhenzhen
 * @date 2021-09-03 17:32:07
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("thread_pool_configuration")
public class ThreadPoolConfiguration extends Model<ThreadPoolConfiguration> {
    private static final long serialVersionUID = 1L;

    /**
     *
     */
    @TableId(type = IdType.AUTO)
    private Long id;
    /**
     * 应用名称
     */
    private String application;
    /**
     * 线程池业务Id
     */
    private String tpId;
    /**
     * 核心线程数
     */
    private Integer corePoolSize;
    /**
     * 最大线程数
     */
    private Integer maximumPoolSize;
    /**
     * 存活时间
     */
    private Long keepAliveTime;
    /**
     * 记录时间
     */
    private Date createTime;
    /**
     * 是否已执行
     */
    @TableField(value = "is_exec")
    private Boolean exec;
    /*
     * 执行时间
     */
    private Date execTime;


    /**
     * primary key
     */
    @Override
    protected Serializable pkVal() {
        return this.id;
    }
}
