package member.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import common.JDBCTemplate;
import member.model.vo.Member;

public class MemberDao {

	public Member selectOneMember(Connection conn, String memberId, String memberPw) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member m = null;
		String query = "select * from member where member_id=? and member_pw=?";


		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			pstmt.setString(2, memberPw);
			rset = pstmt.executeQuery();
			if (rset.next()) {
				m = new Member();
				m.setAddress(rset.getString("address"));
				m.setPhone(rset.getString("phone"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				m.setMemberId(rset.getString("member_id"));
				m.setMemberName(rset.getString("member_name"));
				m.setMemberPw(rset.getString("member_pw"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return m;
	}

	public int insertMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "insert into member values(?,?,?,?,?,?,?,sysdate)";

		try {
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, m.getMemberId());
			pstmt.setString(index++, m.getMemberPw());
			pstmt.setString(index++, m.getMemberName());
			pstmt.setInt(index++, m.getAge());
			pstmt.setString(index++, m.getEmail());
			pstmt.setString(index++, m.getPhone());
			pstmt.setString(index++, m.getAddress());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public Member selectOneMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		Member m = null;

		String query = "select * from member where member_id=?";

		try {
			pstmt = conn.prepareStatement(query);

			pstmt.setString(1, memberId);

			rset = pstmt.executeQuery();

			if (rset.next()) {
				m = new Member();
				m.setAddress(rset.getString("address"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				m.setMemberId(rset.getString("member_id"));
				m.setMemberPw(rset.getString("member_pw"));
				m.setMemberName(rset.getString("member_name"));
				m.setPhone(rset.getString("phone"));
			}

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return m;
	}

	public int updateMember(Connection conn, Member m) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "update member set member_pw=?,member_name=?,age=?,email=?,phone=?,address=? where member_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			int index = 1;
			pstmt.setString(index++, m.getMemberPw());
			pstmt.setString(index++, m.getMemberName());
			pstmt.setInt(index++, m.getAge());
			pstmt.setString(index++, m.getEmail());
			pstmt.setString(index++, m.getPhone());
			pstmt.setString(index++, m.getAddress());
			pstmt.setString(index++, m.getMemberId());

			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public int deleteMember(Connection conn, String memberId) {
		PreparedStatement pstmt = null;
		int result = 0;

		String query = "delete member where member_id=?";

		try {
			pstmt = conn.prepareStatement(query);
			pstmt.setString(1, memberId);
			result = pstmt.executeUpdate();

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			JDBCTemplate.close(pstmt);
		}

		return result;
	}

	public ArrayList<Member> selectAllMember(Connection conn) {
		PreparedStatement pstmt = null;
		ResultSet rset = null;
		ArrayList<Member> list = new ArrayList<Member>();

		String query = "select * from member";

		try {
			pstmt = conn.prepareStatement(query);
			rset = pstmt.executeQuery();

			while (rset.next()) {
				Member m = new Member();
				m.setAddress(rset.getString("address"));
				m.setAge(rset.getInt("age"));
				m.setEmail(rset.getString("email"));
				m.setEnrollDate(rset.getDate("enroll_date"));
				m.setMemberId(rset.getString("member_id"));
				m.setMemberPw(rset.getString("member_pw"));
				m.setMemberName(rset.getString("member_name"));
				m.setPhone(rset.getString("phone"));
				list.add(m);
			}

			

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			JDBCTemplate.close(rset);
			JDBCTemplate.close(pstmt);
		}
		return list;
	}

}
