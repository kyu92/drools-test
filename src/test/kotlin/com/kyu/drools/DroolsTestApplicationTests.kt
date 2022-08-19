package com.kyu.drools

import com.kyu.drools.controller.TestController
import com.kyu.drools.entity.Order
import org.junit.jupiter.api.Test
import org.kie.api.runtime.KieSession
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
import org.springframework.boot.test.context.SpringBootTest
import javax.annotation.Resource

@SpringBootTest(classes = [DroolsTestApplicationTests::class])
@SpringBootApplication(exclude = [ DataSourceAutoConfiguration::class, HibernateJpaAutoConfiguration::class ])
class DroolsTestApplicationTests {

    @Resource
    private lateinit var kieSession: KieSession
    @Resource
    private lateinit var testController: TestController

    @Test
    fun contextLoads() {
    }

    @Test
    fun testDrools() {
        val order = Order("123456")
        order.loanAmount = 10000

        kieSession.setGlobal("testController", testController)

        kieSession.insert(order)

        kieSession.fireAllRules { match ->
            match.rule.packageName == "order.rules"
//            true
        }

        kieSession.dispose()

        println("order 积分为: ${order.score}")
    }
}
