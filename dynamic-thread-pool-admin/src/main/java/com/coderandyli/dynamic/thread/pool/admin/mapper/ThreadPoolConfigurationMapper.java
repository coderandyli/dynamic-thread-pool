package com.coderandyli.dynamic.thread.pool.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolConfiguration;
import org.apache.ibatis.annotations.Mapper;

;

/**
 * 线程池配置信息
 *
 * @author lizhenzhen
 * @date 2021-09-03 17:32:07
 */
@Mapper
public interface ThreadPoolConfigurationMapper extends BaseMapper<ThreadPoolConfiguration> {

}
