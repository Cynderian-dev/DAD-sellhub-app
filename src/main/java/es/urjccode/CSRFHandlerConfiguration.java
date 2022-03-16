package es.urjccode;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CSRFHandlerConfiguration implements WebMvcConfigurer {
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new CSRFHandlerInterceptor());
	}
}

class CSRFHandlerInterceptor implements HandlerInterceptor {

	Logger logger = LoggerFactory.getLogger(CSRFHandlerInterceptor.class);

	@Override
	public void postHandle(final HttpServletRequest request,
		final HttpServletResponse response, final Object handler,
		final ModelAndView modelAndView) {
		CsrfToken token = (CsrfToken) request.getAttribute("_csrf");
		if (modelAndView != null) {
			modelAndView.addObject("token", token.getToken());
		} else {
			logger.warn("CSRFHandlerInterceptor no ha podido añadir el token al modelAndView porque es null");
		}

	}
}
