package com.coderandyli.dynamic.thread.pool.admin.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
  import java.io.Serializable;
import java.util.Date;

/**
 * 线程任务执行记录
 *
 * @author lizhenzhen
 * @date 2021-08-31 15:36:39
 */
@Data
@EqualsAndHashCode(callSuper = true)
@TableName("thread_task_exec_record")
public class ThreadTaskExecRecord extends Model<ThreadTaskExecRecord> {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 线程池业务Id
	 */
	private String tpId;
	/**
	 * 任务名称
	 */
	private String taskName;
	/**
	 * 任务开始执行时间
	 */
	private Long startTime;
	/**
	 * 响应时间
	 */
	private Double responseTime;
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
