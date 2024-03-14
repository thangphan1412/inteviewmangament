package fa.training.spring_security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public IMSUserDetailsService userDetailsService() {
        return new IMSUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(authenticationProvider());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/Candidate/candidateList", "/Candidate/viewDetails").hasAnyAuthority("Interviewer", "Recruiter", "Manager")
                .antMatchers("/Candidate/**").hasAnyAuthority("Recruiter", "Manager")
                .antMatchers("/User").hasAnyAuthority("Admin")
                .antMatchers("/Job/jobList", "Job/").hasAnyAuthority("Interviewer", "Recruiter", "Manager")
                .antMatchers("/Job/**").hasAnyAuthority("Recruiter", "Manager")
                .antMatchers("/offer/view").hasAnyAuthority("Interviewer", "Recruiter", "Manager")
                .antMatchers("/offer/**").hasAnyAuthority("Recruiter", "Manager")
                .antMatchers("/Interview/**").hasAnyAuthority("Interviewer", "Recruiter", "Manager")
                .antMatchers("/forgot_password").permitAll()
                .antMatchers("/reset_password").permitAll()
                .antMatchers("/assets/**").permitAll()
                .antMatchers("/register").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .and()
                .logout().permitAll()
                .and().httpBasic()
                .and().csrf().disable();
    }
}
