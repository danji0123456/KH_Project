package notice.controller;

import java.io.File;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.vo.notice;
import notice.model.vo.noticeViewData;
import notice.service.noticeService;

/**
 * Servlet implementation class DeleteNoticeServlet
 */
@WebServlet(name = "DeleteNotice", urlPatterns = { "/deleteNotice" })
public class DeleteNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1. 인코딩
		request.setCharacterEncoding("utf-8");
		//2. 변수에 값저장
		int noticeNo = Integer.parseInt(request.getParameter("noticeNo"));
		//3. 비지니스로직
		noticeViewData nvd = new noticeService().selectOneNotice(noticeNo);
		notice n = nvd.getN();
		int result = new noticeService().deleteNotice(noticeNo);
		//4. 결과처리
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if(result>0) {
			if(n.getFilepath() != null) {
				String saveDirectory = getServletContext().getRealPath("/upload/notice/");
				File delFile = new File(saveDirectory+n.getFilepath());
				boolean bool = delFile.delete();
				System.out.println("삭제여부 : "+bool);
			}
			request.setAttribute("msg", "삭제완료");
			request.setAttribute("loc", "/noticeList?reqPage=1");
			
		}else {
			request.setAttribute("msg", "삭제실패");
			request.setAttribute("loc", "/noticeView?noticeNo="+noticeNo);
		}
		rd.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
