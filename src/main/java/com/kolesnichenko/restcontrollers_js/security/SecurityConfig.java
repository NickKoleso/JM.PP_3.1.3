package com.kolesnichenko.restcontrollers_js.security;

import com.kolesnichenko.restcontrollers_js.security.oauth2.CustomOAuth2UserService;
import com.kolesnichenko.restcontrollers_js.service.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.expression.method.DefaultMethodSecurityExpressionHandler;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.core.GrantedAuthorityDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.persistence.PersistenceContext;
import java.security.Principal;


@Configuration
@EnableWebSecurity

public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private MyUserDetailService myUserDetailService;
    private LoginSuccessHandler loginSuccessHandler;
    private CustomOAuth2UserService customOAuth2UserService;

    @Autowired
    public SecurityConfig(MyUserDetailService myUserDetailService, LoginSuccessHandler loginSuccessHandler, CustomOAuth2UserService customOAuth2UserService) {
        this.myUserDetailService = myUserDetailService;
        this.loginSuccessHandler = loginSuccessHandler;
        this.customOAuth2UserService = customOAuth2UserService;
    }

    @Override
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.formLogin()
                // указываем страницу с формой логина
                .loginPage("/login")
                //указываем логику обработки при логине
                .successHandler(loginSuccessHandler)
                // указываем action с формы логина
                .loginProcessingUrl("/login")
                // Указываем параметры логина и пароля с формы логина
                .usernameParameter("email")
                .passwordParameter("password")
                // даем доступ к форме логина всем
                .permitAll()
                .and()
                .oauth2Login()
                .loginPage("/login")
                .userInfoEndpoint()
                .userService(customOAuth2UserService)
                .and()
                .successHandler(loginSuccessHandler);


        http.logout()
                // разрешаем делать логаут всем
                .permitAll()
                // указываем URL логаута
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                // указываем URL при удачном логауте
                .logoutSuccessUrl("/login?logout")
                //выклчаем кроссдоменную секьюрность (на этапе обучения неважна)
                .and().csrf().disable();

        http
                // делаем страницу регистрации недоступной для авторизированных пользователей
                .authorizeRequests()
                //страницы аутентификаци доступна всем
              //  .antMatchers("/", "/oauth2/**").permitAll()
                .antMatchers("/login").anonymous()
                // защищенные URL
                .antMatchers("/user/**").hasAnyAuthority("USER", "ADMIN");
        http.authorizeRequests()
                .antMatchers("/api/**").hasAnyAuthority("ADMIN")
                .antMatchers("/admin/**").hasAnyAuthority("ADMIN")
                .anyRequest().permitAll();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
