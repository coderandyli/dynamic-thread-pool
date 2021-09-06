package com.coderandyli.dynamic.thread.pool.admin.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.coderandyli.dynamic.thread.pool.admin.entity.ThreadPoolConfiguration;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

;import java.util.List;

/**
 * 线程池配置信息
 *
 * @author lizhenzhen
 * @date 2021-09-03 17:32:07
 */
@Mapper
public interface ThreadPoolConfigurationMapper extends BaseMapper<ThreadPoolConfiguration> {

    @Select("SELECT tpc.tp_id \n" +
            "FROM thread_pool_configuration tpc\n" +
            "WHERE tpc.is_exec = 0 AND application = #{application}\n" +
            "GROUP BY tpc.tp_id")
    List<String> queryAllUnExecutedTpIdssByApplication(@Param("application") String application);

    @Select("SELECT * \n" +
            "FROM thread_pool_configuration tpc\n" +
            "WHERE tpc.is_exec = 0 AND tp_id = #{tpId}\n" +
            "ORDER BY tpc.create_time DESC \n" +
            "LIMIT 1")
    ThreadPoolConfiguration queryLastUnexecutedOneByTpId(@Param("tpId") String tpId);
}
