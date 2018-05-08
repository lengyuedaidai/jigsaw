package com.dai.jigsaw.core.aspect;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.dai.jigsaw.core.entity.ErrorResult;
import com.dai.jigsaw.core.util.StringUtil;
import com.dai.jigsaw.web.token.Token;

@Aspect  
@Component  
public class AccessTokenAspect {  
  
    @Autowired  
    private Token tokenManager; 
    
    private ErrorResult tokenError = new ErrorResult(ErrorResult.TOKEN_VERIFY_FAILD,"Token失效!");  
  
    @Around("@annotation(com.dai.jigsaw.core.aspect.annotation.AccessToken)")  
    public Object doAccessCheck(ProceedingJoinPoint pjp) throws Throwable{  
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        String token = tokenManager.getToken(request);  
        if(StringUtil.isBlank(token)){
        	 return tokenError;
        }
        boolean verify = tokenManager.validate(token);  
        if(verify){  
            Object object = pjp.proceed(); //执行连接点方法  
            //获取执行方法的参数  
            return object;  
        }else {  
            return tokenError;
        }  
    }

    
}  