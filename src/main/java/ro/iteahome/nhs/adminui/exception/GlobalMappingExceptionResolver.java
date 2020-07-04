//package ro.iteahome.nhs.adminui.exception;
//
//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.servlet.ModelAndView;
//import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
//@ControllerAdvice
//public class GlobalMappingExceptionResolver extends SimpleMappingExceptionResolver {
//
//    public GlobalMappingExceptionResolver() {
//        setWarnLogCategory(GlobalMappingExceptionResolver.class.getName());
//    }
//
//    @Override
//    protected String buildLogMessage(Exception ex, HttpServletRequest request) {
//        return "EXCEPTION IN MVC APPLICATION: " + ex.getLocalizedMessage();
//    }
//
//    @Override
//    protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
//        ModelAndView exceptionMV = super.doResolveException(request, response, handler, ex);
//        exceptionMV.addObject("url", request.getRequestURL());
//        return exceptionMV;
//    }
//}
