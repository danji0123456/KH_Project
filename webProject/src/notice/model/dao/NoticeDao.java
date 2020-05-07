package notice.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import notice.model.vo.notice;
import notice.model.vo.noticeComment;





public class NoticeDao {

	public ArrayList<notice> selectLsit(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<notice> list = new ArrayList<notice>();
		
		String query="select * from notice";
		
		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				notice n = new notice();
				
				n.setNoticeNo(rset.getInt("notice_no"));
				n.setNoticeTitle(rset.getString("notice_title"));
				n.setNoticeWriter(rset.getString("notice_writer"));
				n.setNoticeContent(rset.getString("notice_content"));
				n.setNoticeDate(rset.getDate("notice_date"));
				n.setFilename(rset.getString("filename"));
				n.setFilepath(rset.getString("filepath"));
				list.add(n);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

	
	
	
	
	
	public int totalCount(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		int result = 0;
		
		String query="select count(*) as cnt from notice";
		try {
			pstmt = conn.prepareStatement(query);
			
			rset = pstmt.executeQuery();
			if(rset.next()) {
				result = rset.getInt("cnt");
			}
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		
		return result;
	}






	public ArrayList<notice> selectList(Connection conn, int start, int end) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<notice> list = new ArrayList<notice>();
		
		String query="select * from(select rownum as rnum, n.* from(select * from notice order by notice_no desc)n)"+
				"where rnum between ? and ?";
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, start);
			pstmt.setInt(2, end);
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				notice n = new notice();
				n.setNoticeNo(rset.getInt("notice_no"));
				n.setNoticeTitle(rset.getString("notice_title"));
				n.setNoticeWriter(rset.getString("notice_writer"));
				n.setNoticeContent(rset.getString("notice_content"));
				n.setNoticeDate(rset.getDate("notice_date"));
				n.setFilename(rset.getString("filename"));
				n.setFilepath(rset.getString("filepath"));
				list.add(n);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}






	public int insertNotice(Connection conn, notice n) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into notice values(seq_notice.nextval,?,?,?,sysdate,?,?)";
		
			try {
				pstmt = conn.prepareStatement(query);
				
				int index = 1;
				pstmt.setString(index++, n.getNoticeTitle());
				pstmt.setString(index++, n.getNoticeWriter());
				pstmt.setString(index++, n.getNoticeContent());
				pstmt.setString(index++, n.getFilename());
				pstmt.setString(index++, n.getFilepath());
				result = pstmt.executeUpdate();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally {
				JDBCTemplate.close(pstmt);
			}
		return result;
	}






	public notice selectOneNotice(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		notice n = null;
		
		String query="select * from notice where notice_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			rset = pstmt.executeQuery();
			
			if(rset.next()) {
				n = new notice();
				n.setNoticeNo(rset.getInt("notice_no"));
				n.setNoticeTitle(rset.getString("notice_title"));
				n.setNoticeContent(rset.getString("notice_content"));
				n.setNoticeWriter(rset.getString("notice_writer"));
				n.setNoticeDate(rset.getDate("notice_date"));
				n.setFilename(rset.getString("filename"));
				n.setFilepath(rset.getString("filepath"));
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return n;
	}






	public int deleteNotice(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query="delete from notice where notice_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	
	
	public int deleteNoticeCommentNo(Connection conn, int noticeCommentNo) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query="delete from notice_comment where notice_comment_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setInt(1, noticeCommentNo);
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			JDBCTemplate.close(pstmt);
		}
		return result;
	}
	
	
	






	public int updateNotice(Connection conn, notice n) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query="update notice set notice_title=?, notice_content=?, filename=?, filepath=? where notice_no=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setString(1, n.getNoticeTitle());
			pstmt.setString(2, n.getNoticeContent());
			pstmt.setString(3, n.getFilename());
			pstmt.setString(4, n.getFilepath());
			pstmt.setInt(5, n.getNoticeNo());
			
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}






	public int noticeCommentInsert(Connection conn, noticeComment nc) {
		PreparedStatement pstmt = null;
		int result = 0;
		String query = "insert into notice_comment values(seq_notice_comment.nextval,?,?,?,?,?,sysdate)";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, nc.getNoticeCommentLevel());
			pstmt.setString(2, nc.getNoticeCommentWriter());
			pstmt.setString(3, nc.getNoticeCommentContent());
			pstmt.setInt(4, nc.getNoticeRef());
			
			//pstmt.setInt(5, nc.getNoticeCommentRef());
			//null이 들어올 경우를 대비해서 삼항연산자로 표현해서 사용가능.
			pstmt.setString(5, nc.getNoticeCommentRef()==0?null:String.valueOf(nc.getNoticeCommentRef()));
			
			result = pstmt.executeUpdate();
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
		}
		return result;
	}






	public ArrayList<noticeComment> selectCommentList(Connection conn, int noticeNo) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<noticeComment> list = new ArrayList<noticeComment>();
		
		String query = "select * from notice_comment where notice_ref=?";
		
		try {
			pstmt = conn.prepareStatement(query);
			
			pstmt.setInt(1, noticeNo);
			
			rset = pstmt.executeQuery();
			
			while(rset.next()) {
				noticeComment nc = new noticeComment();
				nc.setNoticeCommentNo(rset.getInt("notice_comment_no"));
				nc.setNoticeCommentLevel(rset.getInt("notice_comment_level"));
				nc.setNoticeCommentWriter(rset.getString("notice_comment_writer"));
				nc.setNoticeCommentContent(rset.getString("notice_comment_content"));
				nc.setNoticeRef(rset.getInt("notice_ref"));
				nc.setNoticeCommentRef(rset.getInt("notice_comment_ref"));
				nc.setNoticeCommentDate(rset.getDate("notice_comment_date"));
				list.add(nc);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			JDBCTemplate.close(pstmt);
			JDBCTemplate.close(rset);
		}
		return list;
	}

}
