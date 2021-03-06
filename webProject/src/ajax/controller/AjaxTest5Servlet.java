package ajax.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;

/**
 * Servlet implementation class AjaxTest5Servlet
 */
@WebServlet(name = "AjaxTest5", urlPatterns = { "/ajaxTest5" })
public class AjaxTest5Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AjaxTest5Servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		ArrayList<User> list = new ArrayList<User>();
		list.add(new User(1, "김용준1", "인천1"));
		list.add(new User(2, "김용준2", "인천2"));
		list.add(new User(3, "김용준3", "인천3"));
		list.add(new User(4, "김용준4", "인천4"));
		list.add(new User(5, "김용준5", "인천5"));
		
		int userNum = Integer.parseInt(request.getParameter("userNum"));
		User u = list.get(userNum-1);
		JSONObject result = new JSONObject();
		result.put("userNo", u.getUserNo());
		result.put("userName", URLEncoder.encode(u.getUserName(), "UTF-8"));
		result.put("userAddr", URLEncoder.encode(u.getUserAddr(), "UTF-8"));
		response.setContentType("application/json");
		PrintWriter out = response.getWriter();
		
		out.print(result);
		out.flush(); // 버퍼링 되어서 아직 기록되지 않은 데이터를 출력스트림에 모두 출력 
		out.close(); // 출력되지 않은 데이터가 있으면 먼저 출력하고 스트림 종료
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
