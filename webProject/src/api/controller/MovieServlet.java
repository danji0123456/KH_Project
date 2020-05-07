package api.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jsoup.Jsoup;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import jdk.nashorn.internal.parser.JSONParser;

/**
 * Servlet implementation class MovieServlet
 */
@WebServlet(name = "Movie", urlPatterns = { "/movie" })
public class MovieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetDt = request.getParameter("targetDt");
		//자신의 키값
		String key = "430156241533f1d058c603178cc3ca0e";
		String url = "http://www.kobis.or.kr/kobisopenapi/webservice/rest/boxoffice/searchDailyBoxOfficeList.json";
		String result = Jsoup.connect(url)
						.data("key",key)
						.data("targetDt",targetDt)
						.userAgent("Mozilla")
						//컨텐트타입을 상관없이 다 가져 오겠다는 뜻
						.ignoreContentType(true)
						.execute().body();
		
		System.out.println(result);
		JsonObject movieInfo1 = new JsonObject();
		JsonParser parser = new JsonParser();
		movieInfo1 = (JsonObject)parser.parse(result);
		JsonObject movieInfo2 = (JsonObject)movieInfo1.get("boxOfficeResult");
		JsonArray arr = (JsonArray)movieInfo2.get("dailyBoxOfficeList");
		response.setContentType("application/json;charset=utf-8");
		PrintWriter out = response.getWriter();
		out.print(arr);
		out.flush();
		out.close();
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
