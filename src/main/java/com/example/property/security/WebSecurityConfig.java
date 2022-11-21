package com.example.property.security;

import com.example.property.entity.Mail;
import com.example.property.service.RegistrationService;
import com.example.property.service.UserServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;


@Configuration
@EnableWebSecurity
@AllArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private UserServiceImpl userServiceImpl;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private PasswordEncoder bCryptPasswordEncoder;
  //  private FileSystem FileSystem;

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        getHttp()
                .csrf().disable()
                .authorizeHttpRequests()
                .antMatchers("api/v1/registration", "/post", "/blog*", "/forgot-password**",
                        "/reset-password**", "/resource/**").permitAll()
                .antMatchers("/users").authenticated()
                .and()
                .formLogin().loginPage("/login").permitAll()
                .usernameParameter("email")
                .defaultSuccessUrl("http://localhost:3000")
                .permitAll()
                .and()
                .logout().invalidateHttpSession(true)
                .logoutSuccessUrl("http://localhost:3000")
                .clearAuthentication(true)
                .permitAll();
        // getHttp().csrf().disable();
    }
    @Bean
    public RegistrationService registrationService() {
        return new RegistrationService() {

        };
    }

   /* @Bean
    public BlogPostRequestService blogPostRequestService() {
        return new BlogPostRequestService() {

        };
    }*/



    @Bean
    public String string() {
        return new String();
    }

   /* @Bean
    public FileSystem fileSystem() {
        return new FileSystem() {
            @Override
            public FileSystemProvider provider() {
                return null;
            }

            @Override
            public void close() throws IOException {

            }

            @Override
            public boolean isOpen() {
                return false;
            }

            @Override
            public boolean isReadOnly() {
                return false;
            }

            @Override
            public String getSeparator() {
                return null;
            }

            @Override
            public Iterable<Path> getRootDirectories() {
                return null;
            }

            @Override
            public Iterable<FileStore> getFileStores() {
                return null;
            }

            @Override
            public Set<String> supportedFileAttributeViews() {
                return null;
            }

            @Override
            public Path getPath(String first, String... more) {
                return null;
            }

            @Override
            public PathMatcher getPathMatcher(String syntaxAndPattern) {
                return null;
            }

            @Override
            public UserPrincipalLookupService getUserPrincipalLookupService() {
                return null;
            }

            @Override
            public WatchService newWatchService() throws IOException {
                return null;
            }
        };
    }*/

   /* @Bean
    public Path path() {
        return new Path() {
            @Override
            public FileSystem getFileSystem() {
                return FileSystem;
            }

            @Override
            public boolean isAbsolute() {
                return false;
            }

            @Override
            public Path getRoot() {
                return null;
            }

            @Override
            public Path getFileName() {
                return null;
            }

            @Override
            public Path getParent() {
                return null;
            }

            @Override
            public int getNameCount() {
                return 0;
            }

            @Override
            public Path getName(int index) {
                return null;
            }

            @Override
            public Path subpath(int beginIndex, int endIndex) {
                return null;
            }

            @Override
            public boolean startsWith(Path other) {
                return false;
            }

            @Override
            public boolean endsWith(Path other) {
                return false;
            }

            @Override
            public Path normalize() {
                return null;
            }

            @Override
            public Path resolve(Path other) {
                return null;
            }

            @Override
            public Path relativize(Path other) {
                return null;
            }

            @Override
            public URI toUri() {
                return null;
            }

            @Override
            public Path toAbsolutePath() {
                return null;
            }

            @Override
            public Path toRealPath(LinkOption... options) throws IOException {
                return null;
            }

            @Override
            public WatchKey register(WatchService watcher, WatchEvent.Kind<?>[] events, WatchEvent.Modifier... modifiers) throws IOException {
                return null;
            }

            @Override
            public int compareTo(Path other) {
                return 0;
            }

            @Override
            public boolean equals(Object other) {
                return false;
            }

            @Override
            public int hashCode() {
                return 0;
            }

            @Override
            public String toString() {
                return null;
            }
        };
    }
*/
  /*  @Bean
    public StorageProperties storageProperties() {
        return new StorageProperties();
    }
*/
    @Bean
    public Model model() {
        return new Model() {
            @Override
            public Model addAttribute(String attributeName, Object attributeValue) {
                return null;
            }

            @Override
            public Model addAttribute(Object attributeValue) {
                return null;
            }

            @Override
            public Model addAllAttributes(Collection<?> attributeValues) {
                return null;
            }

            @Override
            public Model addAllAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public Model mergeAttributes(Map<String, ?> attributes) {
                return null;
            }

            @Override
            public boolean containsAttribute(String attributeName) {
                return false;
            }

            @Override
            public Object getAttribute(String attributeName) {
                return null;
            }

            @Override
            public Map<String, Object> asMap() {
                return null;
            }
        };
    }

    @Bean
    public Mail mail() {
        return new Mail();
    }
    




    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider provider =
                new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder);
        provider.setUserDetailsService(userServiceImpl);
        return provider;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return  source;
    }


    protected void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        auth
                .userDetailsService(userServiceImpl)
                .passwordEncoder(bCryptPasswordEncoder);
    }



}
