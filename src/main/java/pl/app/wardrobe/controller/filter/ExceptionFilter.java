package pl.app.wardrobe.controller.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.app.wardrobe.controller.servlet.MainServlet;
import pl.app.wardrobe.controller.servlet.exception.HttpRequestException;

import java.io.IOException;

/* Filter responsible for catching exceptions */
@WebFilter(urlPatterns = {
        MainServlet.Paths.MAIN + "/*"
})
public class ExceptionFilter extends HttpFilter {

    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            super.doFilter(request, response, chain);
        } catch (HttpRequestException ex) {
            response.sendError(ex.getResponseCode());
        }
    }
}

