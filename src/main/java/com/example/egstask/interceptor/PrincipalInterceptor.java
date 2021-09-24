package com.example.egstask.interceptor;

import com.example.egstask.component.Global;
import com.example.egstask.exception.UnauthorizedException;
import com.example.egstask.model.entity.EgsUser;
import com.example.egstask.model.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.AsyncHandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.example.egstask.common.ErrorMessages.UNAUTHORIZED;

@Component
public class PrincipalInterceptor implements AsyncHandlerInterceptor {

    private UserService userService;

    @Autowired
    public void setUserService(@Lazy UserService userService) {
        this.userService = userService;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)  {
        checkUserIsLocked();
        return true;
    }

    private void checkUserIsLocked() {
        if (Global.isAnonymousUser())
            return;
        EgsUser userInfo = Global.getUserInfo();
        userInfo = userService.findById(userInfo.getId());
        if (userInfo.getLocked())
            throw new UnauthorizedException(UNAUTHORIZED);
    }

}
