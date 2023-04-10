package my.example.security.filter;


import lombok.extern.slf4j.Slf4j;
import my.example.security.exception.JWTException;
import my.example.security.exception.RestApiErrorCode;
import my.example.security.utils.ErrorResponseUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Component
public class JWTExceptionHandlerFilter extends OncePerRequestFilter {

    /**
     * Same contract as for {@code doFilter}, but guaranteed to be
     * just invoked once per request within a single request thread.
     * See {@link #shouldNotFilterAsyncDispatch()} for details.
     * <p>Provides HttpServletRequest and HttpServletResponse arguments instead of the
     * default ServletRequest and ServletResponse ones.
     *
     * @param request
     * @param response
     * @param filterChain
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try{
            filterChain.doFilter(request,response);
        } catch (JWTException ex){
            log.error("JWTException", ex);
            ErrorResponseUtils.setErrorResponse(response,ex.getErrorCode());
        }  catch (Exception ex) {
            log.error("JWTException - unknown", ex);
            ErrorResponseUtils.setErrorResponse(response, RestApiErrorCode.AUTH_ERROR);
        }
    }

}
