package tacos.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
// @EnableWebSecurity : 웹 보안 활성화
@EnableWebSecurity
// SecurityConfig 클래스 : 사용자의 HTTP 요청 경로에 대해 접근 제한과 같은 보안 관련 처리를 사용자가 원하는 대로 할 수 있게 해준다
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    // HTTP보안을 구성하는 메서드
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests().antMatchers("/design", "/orders")
                .access("hasRole('ROLE_USER')")
                .antMatchers("/", "/**").access("permitAll")
                .and().httpBasic();
    }

    @Autowired
    DataSource dataSource;

    @Override
    // 사용자 인증 정보를 구성하는 메서드
    // AuthenticationManagerBuilder : 인증 명세를 구성하기 위해 빌더 형태의 API를 사용
    public void configure(AuthenticationManagerBuilder auth) throws Exception {
        // inMemoryAuthentication() 메서드를 사용하여 보안 구성 자체에 사용자 정보를 직접 지정할 수 있다
       
        // 인메모리 기반 사용자 스토어
     /*   auth.inMemoryAuthentication().withUser("user1")
                .password("{noop}password1")
                .authorities("ROLE_USER").and()
                .withUser("usser2")
                .password("{noop}password2")
                .authorities("ROLE_USER");*/


        // 관계형 DB에 유지되는 사용자 정보를 인증하기 위해 jdbc 사용용
        //DB를 액세스하는 방법을 알 수 있도록 dataSource()호출
        // usersByUsernameQuery() , authoritiesByUsernameQuery() : 사용자 정보와 권한 쿼리만을 대체

        // JDBC 기반 사용자 스토어 코드
/*        auth.jdbcAuthentication().dataSource(dataSource)
                .usersByUsernameQuery(
                        "select username, password, enabled from users " +
                                "where username=?")
                .authoritiesByUsernameQuery(
                        "select username, authority from authorities " +
                                "where username=?")
                // passwordEncoder() : 스프링 시큐리티의 passwordEncoder 인터페이스를 구현하는 어떤 객체도 인자로 받을 수 있다
                // BCryptPasswordEncoder() : bcrypt를 해싱 암호화 한다
                *//*.passwordEncoder(new BCryptPasswordEncoder()); => 인코딩 방식 변경위해 삭제*//*
                .passwordEncoder(new NoEncodingPasswordEncoder());*/

        // LDAP 기반 사용자 스토어
        // ldapAuthentication() : LDAP 기반 인증으로 스프링 시큐리티를 구성하기 위해 사용
        auth.ldapAuthentication()
                
            // userSearchBase() : 사용자를 찾기 위한 기준점 쿼리 제공
            .userSearchBase("ou=people")
            .userSearchFilter("(uid={0})")

            // groupSearchBase() : 그룹을 찾기 위한 기준점 쿼리 지정
            .groupSearchBase("ou=groups")
            .groupSearchFilter("member={0}")
            .contextSource()
                
            // root() : 내장 LDAP 서버의 루트 경로를 지정
            .root("dc=tacocloud,dc=com")

            // classpath의 루트에서 users.ldif 파일을 찾아 LDAP 서버로 데이터를 로드하라고 요청한다
            .ldif("classpath:users.ldif")
            .and()

            // passwordCompare() : 비밀번호를 비교하는 방법으로 LDAP 인증을 하려고 할 때 사용하는 메서드
            .passwordCompare()
                
            // passwordEncoder() : 암호화에 사용할 인코더 지정
            .passwordEncoder(new BCryptPasswordEncoder())
                
            // passwordAttribute("userPasscode") : 비밀번호 속성의 이름 지정 => 전달된 비밀번호와 userPasscode 속성 값이 비교되어야 한다는것 지정
            // -> 비번 속성 이름 변경하지 않을 때 기본적으로 userPasscode가 된다
            .passwordAttribute("userPasscode");
    }
}
