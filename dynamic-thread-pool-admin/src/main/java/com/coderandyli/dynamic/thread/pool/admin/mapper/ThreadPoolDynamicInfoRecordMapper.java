package com.coderandyli.dynamic.thread.pool.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;
import org.apache.ibatis.annotations.Mapper;

/**
 * 线程池动态信息记录表
 *
 * @author lizhenzhen
 * @date 2021-08-31 15:36:22
 */
@Mapper
public interface ThreadPoolDynamicInfoRecordMapper extends BaseMapper<ThreadPoolDynamicInfoRecord> {

}
