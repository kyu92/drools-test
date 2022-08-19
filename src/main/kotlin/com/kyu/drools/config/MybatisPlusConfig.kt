package com.kyu.drools.config

import com.baomidou.mybatisplus.annotation.DbType
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.BlockAttackInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor
import org.mybatis.spring.annotation.MapperScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@MapperScan("com.kyu.drools.mapper")
class MybatisPlusConfig {

    @Bean
    fun mybatisPlusInterceptor(): MybatisPlusInterceptor {
        val interceptor = MybatisPlusInterceptor()
        // 分页插件
        interceptor.addInnerInterceptor(PaginationInnerInterceptor(DbType.MYSQL));
        // 防止全表更新、删除插件
        interceptor.addInnerInterceptor(BlockAttackInnerInterceptor())
        // 乐观锁
        interceptor.addInnerInterceptor(OptimisticLockerInnerInterceptor())
        return interceptor
    }
}