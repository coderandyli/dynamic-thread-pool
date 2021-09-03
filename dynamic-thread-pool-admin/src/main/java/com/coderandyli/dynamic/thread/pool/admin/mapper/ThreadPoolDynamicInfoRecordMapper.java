package com.coderandyli.dynamic.thread.pool.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolDynamicInfoRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 线程池动态信息记录表
 *
 * @author lizhenzhen
 * @date 2021-08-31 15:36:22
 */
@Mapper
public interface ThreadPoolDynamicInfoRecordMapper extends BaseMapper<ThreadPoolDynamicInfoRecord> {

    @Select("SELECT * FROM thread_pool_dynamic_info_record WHERE tp_id = #{tpId} ORDER BY id DESC LIMIT 1;")
    ThreadPoolDynamicInfoRecord queryLastOneByTpId(@Param("tpId") String tpId);
}
