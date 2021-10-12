package com.kolesnichenko.restcontrollers_js.security;

import com.kolesnichenko.restcontrollers_js.security.oauth2.CustomOAuth2User;
import com.kolesnichenko.restcontrollers_js.service.MyUserDetailService;
import com.kolesnichenko.restcontrollers_js.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@Component
public class LoginSuccessHandler implements AuthenticationSuccessHandler {

    private UserService userService;
    private MyUserDetailService myUserDetailService;
    @Autowired
    public void setMyUserDetailService(MyUserDetailService myUserDetailService) {
        this.myUserDetailService = myUserDetailService;
    }

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
                                        HttpServletResponse httpServletResponse,
                                        Authentication authentication) throws IOException, ServletException {
        if (userService.findByEmail(authentication.getName()) == null) {
            CustomOAuth2User oAuthUser = (CustomOAuth2User) authentication.getPrincipal();
            userService.processOAuthPostLogin(oAuthUser.getName(), oAuthUser.getAttributes());


        }


        Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());
        if (roles.contains("USER")||roles.contains("ROLE_USER")) {
            httpServletResponse.sendRedirect("/user");
        } else if (roles.contains("ADMIN")) {
            httpServletResponse.sendRedirect("/admin");
        } else {
            httpServletResponse.sendRedirect("/login");
        }
    }
}
