package com.zhongcheng.jenkins.javajenkins.common.interceptor;

import com.zhongcheng.jenkins.javajenkins.common.exception.BaseException;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static com.zhongcheng.jenkins.javajenkins.model.ErrorEnum.SessionError;


public class AuthInterceptor implements HandlerInterceptor {
    public static AuthInterceptor authInterceptor;
    final Logger logger = LoggerFactory.getLogger(getClass());
    /**
     * 在请求处理之前进行调用（Controller方法调用之前）
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        //统一拦截（查询当前session是否存在user）(这里user会在每次登陆成功后，写入session)
        Long userId = (Long) request.getSession().getAttribute("userId");
        String type = (String) request.getSession().getAttribute("type");
        request.setAttribute("user", userId);
        logger.info("AuthInterceptor is running ...");
        if (type == null) {
            throw new BaseException(SessionError);
        }
        else if(!type.equals("admin") && userId == null) {
//            setReturn(response, -2, "请先登录！");
//            return false;
            throw new BaseException(SessionError);
        }

        return true;   //如果设置为false时，被请求时，拦截器执行到此处将不会继续操作,如果设置为true时，请求将会继续执行后面的操作
    }

    /**
     * 请求处理之后进行调用，但是在视图被渲染之前（Controller方法调用之后）
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
    }

    /**
     * 在整个请求结束之后被调用，也就是在DispatcherServlet 渲染了对应的视图之后执行（主要是用于进行资源清理工作）
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {

    }

//    private static void setReturn(HttpServletResponse response, int status, String msg) throws IOException {
//        HttpServletResponse httpResponse = (HttpServletResponse) response;
//        httpResponse.setHeader("Access-Control-Allow-Credentials", "true");
////        httpResponse.setHeader("Access-Control-Allow-Origin", HttpContextUtil.getOrigin());
//        httpResponse.setCharacterEncoding("UTF-8");
//        httpResponse.setStatus(200);
//        response.setContentType("application/json;charset=utf-8");
//        CommonResult build = new CommonResult(status, msg, null);
//        String json = JSON.toJSONString(build);
//        httpResponse.getWriter().print(json);
//    }
}
