package venkat.org.springframework.springrecipe.controllers;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.support.WebExchangeBindException;

@ControllerAdvice
@Slf4j
public class ExceptionHandlerController {
    private static final String VIEW_NAME_ERROR_PAGE_400 = "errorpages/400";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException.class)
    public String handleNumberFormatException(Exception ex, Model model) {
        log.error("Exception occurred while processing your request. Root cause::" + ex.getMessage());
        model.addAttribute("exception", ex);
        return VIEW_NAME_ERROR_PAGE_400;
    }
}
