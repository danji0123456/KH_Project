package common.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

import org.apache.catalina.servlet4preview.http.HttpServletRequest;

/**
 * Servlet Filter implementation class CharsetEncodingFilter
 */
@WebFilter("/CharsetEncodingFilter")
public class CharsetEncodingFilter implements Filter {

    /**
     * Default constructor. 
     */
    public CharsetEncodingFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		System.out.println("필터객체 소멸");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		//서블릿 도착 전에 처리할 사전작업
		//System.out.println("사전작업 시작");
		request.setCharacterEncoding("utf-8");
		HttpServletRequest req = (HttpServletRequest)request;
		System.out.println("요청 url :"+req.getRequestURI());
		//다음 필터를 요청하고 없으면 다음 서블릿을 요청함.
		chain.doFilter(request, response);
		//System.out.println("사후작업");
		//서블릿에 클라이언트로 응답 나가기 전 사후작업
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("필터객체 생성");
	}

}
