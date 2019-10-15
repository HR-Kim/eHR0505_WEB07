package kr.co.ehr.cmn.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import kr.co.ehr.user.service.User;

public class LoginInterceptor extends HandlerInterceptorAdapter {
	Logger LOG = LoggerFactory.getLogger(this.getClass());
	//preHandle : Controller가 호출되기 전 수행
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		HttpSession session = request.getSession();
        User loginVO = (User) session.getAttribute("user");
 
        LOG.debug("loginVO:"+loginVO);
        //session이 없으면 
        if(null == loginVO ){
        	String context = request.getContextPath();
            response.sendRedirect(context+"/login/login.jsp");
//            ModelAndView modelAndView = new ModelAndView("forward:/NotLogged.do");
//            throw new ModelAndViewDefiningException(modelAndView);
        }

        
        return true;
	}

	//postHandle : Controller가 완료된 이후에 수행
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub
		super.postHandle(request, response, handler, modelAndView);
	}

	//afterCompletion : Controller 수행 후 view단 작업까지 완료 된 후 호출 
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	}

	@Override
	public void afterConcurrentHandlingStarted(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterConcurrentHandlingStarted(request, response, handler);
	}

}
