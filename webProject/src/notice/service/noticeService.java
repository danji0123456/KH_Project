package notice.service;

import java.sql.Connection;
import java.util.ArrayList;

import common.JDBCTemplate;
import notice.model.dao.NoticeDao;
import notice.model.vo.NoticePageData;
import notice.model.vo.notice;
import notice.model.vo.noticeComment;
import notice.model.vo.noticeViewData;

public class noticeService {

	public NoticePageData selectList(int reqPage) {
		Connection conn = JDBCTemplate.getConnection();
		
		int numPerPage = 10;// 한페이지당 게시물 수
		
		//총 게시물 수를 구하는 dao 호출
		int totalCount = new NoticeDao().totalCount(conn);
		
		//총페이지 수를 연산
		int totalPage = 0;
		// 10, 72 -> 8개
		// 10, 85 -> 9개
		
		if(totalCount%numPerPage == 0) {
			totalPage = totalCount/numPerPage;
		}else {
			totalPage = totalCount/numPerPage+1;
		}
		
		//조회해 올 게시물 시작번호와 끝번호연산
		int start = (reqPage-1)*numPerPage+1;
		int end = reqPage*numPerPage;
		//해당페이지에 게시물들 조회
		ArrayList<notice> list = new NoticeDao().selectList(conn, start, end);
		
		//페이지 네비게이션 작성시작
		String pageNavi = "";
		//페이지 네비게이션 길이
		int pageNaviSize = 5; 
		int pageNo = ((reqPage-1)/pageNaviSize)*pageNaviSize+1;
		
		if(pageNo != 1) {
			pageNavi += "<a class='btn' href='/noticeList?reqPage="+(pageNo-pageNaviSize)+"'>이전</a>";
		}
		for(int i=0; i<pageNaviSize; i++) {
			if(reqPage == pageNo) {
				pageNavi += "<span class='selectPage'>"+pageNo+"</span>";
			}else {
				pageNavi += "<a class='btn' href='/noticeList?reqPage="+pageNo+"'>"+pageNo+"</a>";			
				}
			pageNo++;
			if(pageNo>totalPage) {
				break;
			}
		}
		
		if(pageNo <= totalPage) {
			pageNavi += "<a class='btn' href='/noticeList?reqPage="+pageNo+"'>다음</a>";
		}
		
		NoticePageData pd = new NoticePageData(list, pageNavi);		
		JDBCTemplate.close(conn);
		
		return pd;
	}

	public int insertNotice(notice n) {
		Connection conn = JDBCTemplate.getConnection();
		
		int result = new NoticeDao().insertNotice(conn, n);
		
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else{
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}

	public noticeViewData selectOneNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		notice n = new NoticeDao().selectOneNotice(conn, noticeNo);
		ArrayList<noticeComment> list = new NoticeDao().selectCommentList(conn,noticeNo);
		
		noticeViewData nvd = new noticeViewData(n, list);
		JDBCTemplate.close(conn);
		return nvd;
	}

	public int deleteNotice(int noticeNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new NoticeDao().deleteNotice(conn,noticeNo);
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
	public int deleteNoticeCommentNo(int noticeCommentNo) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new NoticeDao().deleteNoticeCommentNo(conn,noticeCommentNo);
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	

	public int updateNotice(notice n) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new NoticeDao().updateNotice(conn,n);
		if(result>0) {
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}
	
	

	public int noticeCommentInsert(noticeComment nc) {
		Connection conn = JDBCTemplate.getConnection();
		int result = new NoticeDao().noticeCommentInsert(conn,nc);
		
		if(result>0) {	
			JDBCTemplate.commit(conn);
		}else {
			JDBCTemplate.rollback(conn);
		}
		JDBCTemplate.close(conn);
		return result;
	}


}
