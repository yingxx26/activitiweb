package com.imooc.activitiweb.mapper;


import com.imooc.activitiweb.pojo.TestMiaoShaBean;
import com.imooc.activitiweb.pojo.UserInfoBean;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Component;


@Mapper
@Component
public interface TestMiaoShaBeanMapper {

    @Select("select * from testmiaosha where id = #{id}")
    TestMiaoShaBean selectById(@Param("id") Long id);

    @Update("UPDATE testmiaosha SET stock = #{stock}  WHERE id = #{id}")
    Integer updateById(@Param("id") Long id, @Param("stock") Integer stock);
}
