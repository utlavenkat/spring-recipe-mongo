package venkat.org.springframework.springrecipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    private static final String VIEW_NAME_ERROR_PAGE_400 = "errorpages/400";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(NumberFormatException.class)
    public ModelAndView handleNumberFormatException(Exception ex) {
        log.error("Exception occurred while processing your request. Root cause::" + ex.getMessage());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName(VIEW_NAME_ERROR_PAGE_400);
        modelAndView.addObject("exception", ex);
        return modelAndView;
    }
}
