package com.coderandyli.dtp.admin.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 线程池动态信息记录表
 *
 * @author lizhenzhen
 * @date 2021-08-31 15:36:22
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("thread_pool_dynamic_info_record")
public class ThreadPoolDynamicInfoRecord extends Model<ThreadPoolDynamicInfoRecord> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(type = IdType.AUTO)
	private Long id;
	/**
	 * 线程池业务Id
	 */
	private String tpId;
	/**
	 * 活跃线程数（workers中工作线程数）
	 */
	private Integer activeCount;
	/**
	 * 完成任务数
	 */
	private Integer completedTaskCount;
	/**
	 * 核心线程数
	 */
	private Integer corePoolSize;
	/**
	 * 存活时间
	 */
	private Long keepAliveTime;
	/**
	 * 线程池中工作线程数增加达到的最大值
	 */
	private Integer largestPoolSize;
	/**
	 * 最大线程数
	 */
	private Integer maximumPoolSize;
	/**
	 * 当前线程池中的线程数(当线程状态大于等于TIDYING时，等于0；否则等于workers.size())
	 */
	private Integer poolSize;
	/**
	 * 任务数（已完成的任务 + 正在进行中的任务）
	 */
	private Integer taskCount;
	/**
	 * 任务拒绝次数
	 */
	private Integer rejectCount;
	/**
	 * 记录时间
	 */
	private Date recordTime;

  /**
   * primary key
   */
  @Override
  protected Serializable pkVal() {
    return this.id;
  }
}
