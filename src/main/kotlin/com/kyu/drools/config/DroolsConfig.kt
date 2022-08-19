package com.kyu.drools.config

import com.kyu.drools.annotation.Slf4j
import com.kyu.drools.annotation.Slf4j.Companion.log
import org.kie.api.KieBase
import org.kie.api.KieServices
import org.kie.api.builder.KieFileSystem
import org.kie.api.runtime.KieContainer
import org.kie.api.runtime.KieSession
import org.kie.internal.io.ResourceFactory
import org.kie.spring.KModuleBeanFactoryPostProcessor
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import java.nio.charset.StandardCharsets

@Configuration
@Slf4j
class DroolsConfig {

    companion object {
        private const val RULES_PATH = "rules"
    }

    private val kieServices: KieServices = KieServices.Factory.get()

    @Bean
    fun kieFileSystem(): KieFileSystem {
        val kieFileSystem = kieServices.newKieFileSystem()
        val resourcePatternResolver = PathMatchingResourcePatternResolver()
        val files = resourcePatternResolver.getResources("classpath*:$RULES_PATH/*.*")
        var path: String
        for (file in files) {
            path = "$RULES_PATH/${file.filename}"
            log.info("path: {}", path)
            kieFileSystem.write(ResourceFactory.newClassPathResource(path, StandardCharsets.UTF_8.displayName()))
        }
        return kieFileSystem
    }

    @Bean
    fun kieContainer(kieFileSystem: KieFileSystem): KieContainer {
        val kieRepository = kieServices.repository
        kieRepository.addKieModule { kieRepository.defaultReleaseId }
        val kieBuilder = kieServices.newKieBuilder(kieFileSystem)
        kieBuilder.buildAll()
        return kieServices.newKieContainer(kieRepository.defaultReleaseId)
    }

    @Bean
    fun kieBase(kieContainer: KieContainer): KieBase {
        return kieContainer.kieBase
    }

    @Bean
    fun kieSession(kieContainer: KieContainer): KieSession {
        return kieContainer.newKieSession()
    }

    @Bean
    fun kiePostProcessor(): KModuleBeanFactoryPostProcessor {
        return KModuleBeanFactoryPostProcessor()
    }
}