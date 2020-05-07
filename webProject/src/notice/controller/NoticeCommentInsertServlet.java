package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import notice.model.vo.noticeComment;
import notice.service.noticeService;

/**
 * Servlet implementation class NoticeCommentInsertServlet
 */
@WebServlet(name = "NoticeCommentInsert", urlPatterns = { "/noticeCommentInsert" })
public class NoticeCommentInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NoticeCommentInsertServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//1.인코딩
		request.setCharacterEncoding("utf-8");
		//2. 변수에 값 저장
		noticeComment nc = new noticeComment();
		nc.setNoticeCommentLevel(Integer.parseInt(request.getParameter("noticeCommentLevel")));
		nc.setNoticeCommentWriter(request.getParameter("noticeCommentWriter"));
		nc.setNoticeCommentContent(request.getParameter("noticeCommentContent"));
		nc.setNoticeRef(Integer.parseInt(request.getParameter("noticeRef")));
		nc.setNoticeCommentRef(Integer.parseInt(request.getParameter("noticeCommentRef")));
		//3. 비지니스 로직
		int result = new noticeService().noticeCommentInsert(nc);
		//4. 결과처리
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		
		if(result>0) {
			request.setAttribute("msg", "댓글등록성공");
		}else {
			request.setAttribute("msg", "댓글등록실패");
		}
		request.setAttribute("loc", "/noticeView?noticeNo="+nc.getNoticeRef());
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
