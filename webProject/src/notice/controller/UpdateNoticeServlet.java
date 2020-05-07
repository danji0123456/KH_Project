package notice.controller;

import java.io.File;
import java.io.IOException;
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
 * Servlet implementation class UpdateNoticeServlet
 */
@WebServlet(name = "UpdateNotice", urlPatterns = { "/updateNotice" })
public class UpdateNoticeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpdateNoticeServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		
		if(!ServletFileUpload.isMultipartContent(request)) {
			request.setAttribute("msg", "[enctype]확인");
			request.setAttribute("loc", "/");
			request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
			return;
		}
		
		//파일 저장 준비
		//1)업로드 경로
		String root = getServletContext().getRealPath("/");
		String saveDirectory = root+"upload/notice";
		//2)파일크기 지정
		int maxSize = 10*1024*1024;
		MultipartRequest mRequest = new MultipartRequest(request, saveDirectory, maxSize, "UTF-8", new DefaultFileRenamePolicy());
		notice n = new notice();
		n.setNoticeNo(Integer.parseInt(mRequest.getParameter("noticeNo")));
		n.setNoticeTitle(mRequest.getParameter("noticeTitle"));
		n.setNoticeContent(mRequest.getParameter("noticeContent"));
		//파일이 새로 들어왔을 때와 그렇지 않을 때
		n.setFilename(mRequest.getOriginalFileName("filename"));
		n.setFilepath(mRequest.getFilesystemName("filename"));
		//기존파일관리를 위한 값 읽어오기
		String status = mRequest.getParameter("status");
		String oldFilepath = mRequest.getParameter("oldFilepath");
		String oldFilename = mRequest.getParameter("oldFilename");
		//기존파일값을 유지하기위한 설정
		if(n.getFilename() == null) {
			//파일네임이 새로 들어오지 않았을 경우
			if(status.equals("stay")) {
				//예전 파일을 그대로 유지
				n.setFilename(oldFilename);
				n.setFilepath(oldFilepath);
			}
		}
		int result = new noticeService().updateNotice(n);
		
		if(result>0) {
			if(status.equals("delete")) {
				//새파일이 들어옴
				File delFile = new File(saveDirectory+"/"+oldFilepath);
				delFile.delete();
			}else {
				if(status.equals("delete")) {
					File delFile = new File(saveDirectory+"/"+oldFilename);
					delFile.delete();
				}
			}
			request.setAttribute("msg", "수정성공");
		}else {
			request.setAttribute("msg", "수정실패");
		}
		request.setAttribute("loc", "/noticeView?noticeNo="+n.getNoticeNo());
		request.getRequestDispatcher("/WEB-INF/views/common/msg.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
