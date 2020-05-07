package notice.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;

import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

import notice.model.vo.notice;
import notice.service.noticeService;

/**
 * Servlet implementation class InsertNoticeServlet
 */
@WebServlet(name = "InsertNotice", urlPatterns = { "/insertNotice" })
public class InsertNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public InsertNoticeServlet() {
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
		
		//멀티파트설정이 들어있는지 아닌지를 확인
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "공지사항 타입오류[enctype]");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		//파일업로드 준비
		//1). 파일업로드 경로 지정
		String root = getServletContext().getRealPath("/");
		String saveDirectory = root+"upload/notice";
		System.out.println(saveDirectory);
		//2). 업로드 파일 크기 지정
		int maxSize = 10*1024*1024;
		//request -> MultipartRequest
		// 1.request 2.파일저장 경로 3.최대크기 4.인코딩 5.타입 파일명이 같을 때 해결 로직
		MultipartRequest mRequest = new MultipartRequest(request, saveDirectory, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		
		notice n = new notice();
		n.setNoticeTitle(mRequest.getParameter("noticeTitle"));
		n.setNoticeWriter(mRequest.getParameter("noticeWriter"));
		n.setNoticeContent(mRequest.getParameter("noticeContent"));
		//사용자가 올린 파일명
		n.setFilename(mRequest.getOriginalFileName("filename"));
		//서버에 저장된 파일명
		n.setFilepath(mRequest.getFilesystemName("filename"));
		
		System.out.println("content : "+n.getNoticeContent());
		System.out.println("writer : "+n.getNoticeWriter());
		System.out.println("title : "+n.getNoticeTitle());
		System.out.println("filename : "+n.getFilename());
		System.out.println("filepath : "+n.getFilepath());
		//3. 비지니스로직
		int result = new noticeService().insertNotice(n);
		//4. 결과처리
		RequestDispatcher rd = request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp");
		if(result>0) {
			request.setAttribute("msg", "등록성공");
		}else {
			request.setAttribute("msg", "등록실패");
		}
		request.setAttribute("loc", "/noticeList?reqPage=1");
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
