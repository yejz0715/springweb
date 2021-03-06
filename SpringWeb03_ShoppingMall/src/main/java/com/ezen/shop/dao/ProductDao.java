package com.ezen.shop.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.ezen.shop.dto.ProductVO;
import com.mchange.v2.c3p0.ComboPooledDataSource;

@Repository
public class ProductDao {

	private JdbcTemplate template;
	// 데이터베이스 운영 객체이며, 이전에 사용하던  con, pstmt, rs  가 모두 존재합니다
	
	// 사용하면 com.mchange.v2.c3p0.ComboPooledDataSource 객체를 꺼내와서
	// template 에 초기화해줘야 합니다.
	
	// 스프링 컨테이너에 수동으로 넣어놓은  DBCP 클래스를 꺼내옵니다.
	//@Autowired
	//ComboPooledDataSource dataSource;
	
	// 생성자 메서드와 꺼내온 DBCP  빈을 이용하여 탬플릿을 초기화합니다
	//public ProductDao() {
	//	this.template = new JdbcTemplate(dataSource);
	//}
	// 이로써 연결을 이용한 데이터베이스 운용이 가능해졌습니다.
	
	//아래는 위 코드를 좀 더 간략하게 표현한 코드입니다
	@Autowired // Autowired로 빈을 꺼내와서 담는 동작은 생성자의 전달인수에도 가능합니다
	public ProductDao(ComboPooledDataSource dataSource) {
		this.template = new JdbcTemplate(dataSource);
	}
	//스프링컨테이너의 dataSource -> 생성자의 전달인수로 -> jdbcTemplate 의 생성자의 전달인수로..
	
	
	public List<ProductVO>getNewList(){
		String sql= "select * from new_pro_view";
		
		List<ProductVO>list=template.query(sql, new RowMapper<ProductVO>(){

			@Override
			public ProductVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductVO pvo = new ProductVO();
				pvo.setPseq(rs.getInt("pseq"));
				pvo.setName(rs.getString("name"));
				pvo.setPrice2(rs.getInt("price2"));
				pvo.setImage(rs.getString("image"));
				return pvo;
			}//pvo는 list로 리턴되어 하나씨 하나씩 쌓임
			
		}); 
		return list;
	}
	
	public List<ProductVO>getBestList(){
		String sql= "select * from best_pro_view";
		List<ProductVO>list=template.query(sql, new RowMapper<ProductVO>(){

			@Override
			public ProductVO mapRow(ResultSet rs, int rowNum) throws SQLException {
				ProductVO pvo = new ProductVO();
				pvo.setPseq(rs.getInt("pseq"));
				pvo.setName(rs.getString("name"));
				pvo.setPrice2(rs.getInt("price2"));
				pvo.setImage(rs.getString("image"));
				return pvo;
			}
			
		});
		return list;
	}
}//이 내용은 service로..
