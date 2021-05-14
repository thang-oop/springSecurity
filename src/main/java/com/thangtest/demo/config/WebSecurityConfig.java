package com.thangtest.demo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration // Xác định lớp WebSecurityConfig là 1 lớp dùng để cấu hình
@EnableWebSecurity //sẽ kích hoạt việc tích hợp Spring Security với Spring MVC.
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService; // dùng để cấu hình

    @Bean // để sử dụng được PasswordEncoder;
    public PasswordEncoder passwordEncoder() { // thằng này dùng để mã hóa mật khẩu
        return new BCryptPasswordEncoder(); // Giúp chúng ta mã háo mật khẩu bằng thuật toán BCrypt
    }

    //Thực ra cái configureGlobal đặt tên là đ gì cũng được.
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception { // Cấu hình chi tiết về bảo mật
        http.authorizeRequests() //Thằng này dùng để phân quyền request
                //antMatchers: khai báo đường dẫn của request----permitAll(): cho phép tất cả user được login----hasRole----chỉ user trong này mới đc login
                .antMatchers("/register").permitAll()
                .antMatchers("/").hasRole("MEMBER")
                .antMatchers("/admin").hasRole("ADMIN")
                .and()
            .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("passsword")
                .defaultSuccessUrl("/")
                .failureUrl("/login?error")
                .and()
            .exceptionHandling()
                .accessDeniedPage("/403");
    }
}
