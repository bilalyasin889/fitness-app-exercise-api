package com.tier2.exerciseapi.config

import com.tier2.exerciseapi.service.TokenService
import com.tier2.exerciseapi.service.UserDetailsServiceImpl
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterConfig(
    private val tokenService: TokenService,
    private val userDetailsService: UserDetailsServiceImpl
) {

    @Bean
    fun customHeaderFilterRegistrationBean(): FilterRegistrationBean<AddResponseHeaderFilter> {
        val registrationBean = FilterRegistrationBean<AddResponseHeaderFilter>()
        registrationBean.filter = AddResponseHeaderFilter(tokenService, userDetailsService)
        registrationBean.addUrlPatterns("/*")
        return registrationBean
    }
}