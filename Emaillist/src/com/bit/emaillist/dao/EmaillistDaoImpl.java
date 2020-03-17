package com.bit.emaillist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.bit.emaillist.vo.EmailVO;

public class EmaillistDaoImpl implements EmaillistDao {
	// 데이터베이스 사용자 정보
	private String dbuser = null;
	private String dbpass = null;

		//생성자
	public EmaillistDaoImpl (
				String dbuser,
				String dbpass) {
			this.dbuser = dbuser;
			this.dbpass = dbpass;
	}
	//커넥션 받아오는 메서드
	private Connection  getConnection() throws SQLException {
		Connection conn = null;
		
		try {
			//드라이버 로드
			Class.forName("oracle.jdbc.driver.OracleDriver");
			//db 접속 URL
			String dbur1 = "jdbc:oracle:thin:@localhost:1521:xe";
			//db connect
			conn = DriverManager.getConnection(dbur1,
												dbuser,
												dbpass);
		}catch (ClassNotFoundException e) {
			//드라이버가 없을 경우 예외처리
			e.printStackTrace();
		}
		return conn;
	}
	@Override
	public List<EmailVO> getList() {
		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		List<EmailVO> list = new ArrayList<>();
		
		try {
			conn = getConnection();
			// query 작성
			String sql = "SELECT no, last_name, first_name, email, created_at FROM emaillist "+
					"ORDER BY created_at DESC";
			stmt = conn.createStatement();
			rs = stmt.executeQuery(sql);
			
			//resultSet 루프
			while(rs.next()) {
				// ResultSet으로부터 컬럼 데이터의 추출
				Long no = rs.getLong(1);
				String lastName = rs.getString(2);
				String firstName = rs.getString(3);
				String email = rs.getString(4);
				Date createdAt = rs.getDate(5);
				
				//emailVO 생성
				EmailVO vo = new EmailVO(no,
										lastName,
										firstName,
										email,
										createdAt);
				//list에 추가
				list.add(vo);
			}
		}catch(SQLException e) {
			e.printStackTrace();
		}
		
		return list;
	}
	@Override
	public boolean insert(EmailVO vo) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int insertedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "INSERT INTO emaillist (no, last_name, first_name, email) " +
						"VALUES(seq_emaillist_pk.nextval, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, vo.getlastName());
			pstmt.setString(2, vo.getfirstName());
			pstmt.setString(3, vo.getEmail());
			
			insertedCount = pstmt.executeUpdate();
			
		}catch (SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch(Exception e) {}
		}
		
		
		return insertedCount == 1;
	}
	@Override
	public boolean delete(Long no) {
		Connection conn = null;
		PreparedStatement pstmt = null;
		int deletedCount = 0;
		
		try {
			conn = getConnection();
			String sql = "DELETE FROM emaillist WHERE no=?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setLong(1, no);
			deletedCount = pstmt.executeUpdate();
		}  catch(SQLException e) {
			e.printStackTrace();
		}finally {
			try {
				pstmt.close();
				conn.close();
			}catch (Exception e) {}
		}
		
		
		
		return deletedCount == 1;
	}
}
