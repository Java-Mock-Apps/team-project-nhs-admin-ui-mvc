package ro.iteahome.nhs.adminui.exception;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import ro.iteahome.nhs.adminui.exception.business.GlobalNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler extends ResponseEntityExceptionHandler {

//    public static final String DEFAULT_ERROR_VIEW = "error";
//
//    @ExceptionHandler(Exception.class)
//    public ModelAndView handleException(HttpServletRequest req, Exception ex) throws Exception {
//        if (AnnotationUtils.findAnnotation(ex.getClass(), ResponseStatus.class) != null) {
//            throw ex;
//        }
//        ModelAndView exceptionMV = new ModelAndView(DEFAULT_ERROR_VIEW);
//        exceptionMV.addObject("exception", ex);
//        exceptionMV.addObject("url", req.getRequestURL());
//        return exceptionMV;
//    }

//  ENTITY EXCEPTIONS: -------------------------------------------------------------------------------------------------

//    @ExceptionHandler(GlobalNotFoundException.class)
//    public ResponseEntity<GlobalError> handleGlobalNotFoundException(GlobalNotFoundException ex) {
//        return new ResponseEntity<>(new GlobalError(ex.getRestEntity().substring(0, 3) + "-01", ex.getMessage()), HttpStatus.NOT_FOUND);
//    }

//    @ExceptionHandler(GlobalNotFoundException.class)
//    public ModelAndView handleGlobalNotFoundException(HttpServletRequest req, GlobalNotFoundException ex) {
//        ModelAndView globalNotFoundMV = new ModelAndView("error/not-found");
//        globalNotFoundMV.addObject("notFound", ex.getMessage());
//        globalNotFoundMV.addObject("url", req.getRequestURL());
//        return globalNotFoundMV;
//    }

//    @ExceptionHandler(GlobalAlreadyExistsException.class)
//    public ResponseEntity<GlobalError> handleGlobalAlreadyExistsException(GlobalAlreadyExistsException ex) {
//        return new ResponseEntity<>(new GlobalError(ex.getEntityName().substring(0, 3) + "-02", ex.getMessage()), HttpStatus.CONFLICT);
//    }
}
