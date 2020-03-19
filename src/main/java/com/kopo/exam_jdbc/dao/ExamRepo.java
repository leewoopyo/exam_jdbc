package com.kopo.exam_jdbc.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.kopo.exam_jdbc.domain.ExamRIO;

//해당 클래스가 Repository임을 선언함(annotation)
@Repository
public class ExamRepo {
	//해당 클래스의 로그를 가져온다
	private static final Logger logger = LoggerFactory.getLogger(ExamRepo.class);
	
	//Jdbc Template을 사용하기 위해서  Autowired(자동으로 묶어라 라는 의미) 어노테이션을 써서 
	//이 변수(Jdbc Template)는 스프링 관련 특수변수라고 선언
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	/**
	 * DB데이블을 생성하는 메소드
	 */
	public void createDB() throws Exception{
		//메소드 불러올 때 로그를 남김
		logger.info("Repo --- createDB");
		//실행하고자 하는 query문을 적어서 문자열 변수에 담는다.
		//sql : 이름 학번 각 점수의 데이터가 들어갈 테이블 생성(examtable)
		String query =  "create table examtable(" +
				"name varchar(20)," +
				"studentid int not null primary key," +
				"kor int," + 
				"eng int," +
				"mat int)" +
				"DEFAULT CHARSET=utf8;";
		//쿼리문을 실행한다(create는 execute)
		//excute명령에서 직업 문자를 넣어도 되지만 그럼 더 복잡해 보여서
		//sql문은 문자열 변수에 담고 실행을 변수로 하는 것이 훨씬 깔끔하다
		jdbcTemplate.execute(query);
	}
	
	/**
	 * DB데이블을 삭제하는 메소드
	 */
	public void dropDB() throws Exception{
		//메소드 불러올 때 로그를 남김
		logger.info("Repo --- dropDB");
		//실행하고자 하는 query문을 적고
		//sql : 해당 테이블 삭제(examtable)
		String query =  "drop table examtable";
		//쿼리문을 실행한다(drop은 execute).
		jdbcTemplate.execute(query);
	}
	
	/**
	 * 데이터를 insert하는 메소드
	 * @param u : ExanRIO형의 데이터(DB에 삽입하고자 하는 데이터) 
	 * @return int형인 status를 반환한다.(아마 성공 여부인듯 하다)
	 */
	public int insert(ExamRIO u) throws Exception{
		//메소드 불러올 때 로그를 남김
		logger.info("Repo --- insert");
		//쿼리문을 적고 실행하고 리턴한다.(insert는 update)
		//sql : 이름 학번 각 점수를  examtable에 넣음
		//insert 시  update메소드 안에 쿼리문을 적고 쿼리문에서 넣고자 하는 데이터는 ?로 처리한다 (preparedstatement 형식)
		//그리고 , 쿼리문 뒤에 ?에 해당하는 데이터를 적어준다.(template 형식)
		//ExamRIO형의 객체에 들어있는 데이터를 get메소드로 가져왔다.
		return jdbcTemplate.update(
				"insert into examtable(name,studentid,kor,eng,mat) values (?,?,?,?,?);"
				,u.getName(),u.getStudentid(),u.getKor(),u.getEng(),u.getMat());
	}
	
	/**
	 * 데이터를 update 하는 메소드
	 * @param u : ExanRIO형의 데이터(DB에서 수정하고자 하는 데이터) 
	 * @return int형인 status를 반환한다.
	 */
	public void update(ExamRIO u) throws Exception{
		//메소드 불러올 때 로그를 남김
		logger.info("Repo --- update");
		//쿼리문을 적고 실행하고 리턴한다.(update는 update)
		//sql : examtable안에서 해당하는 학번의 이름 학번 각 점수를 수정한다.  
		//update 시  update메소드 안에 쿼리문을 적고 쿼리문에서 넣고자 하는 데이터는 ?로 처리한다 (preparedstatement 형식)
		//그리고 , 쿼리문 뒤에 ?에 해당하는 데이터를 적어준다.(template 형식)
		//ExamRIO형의 객체에 들어있는 데이터를 get메소드로 가져왔다.
		jdbcTemplate.update(
				"update examtable set name = ?,studentid = ?, kor = ?, eng = ?, mat = ? where studentid = ?;"
				,u.getName(),u.getStudentid(),u.getKor(),u.getEng(),u.getMat(),u.getStudentid());	
	}
	
	/**
	 * 데이터를 delete 하는 메소드
	 * @param u : ExanRIO형의 데이터(DB에 삽입된 데이터) 
	 * @return int형인 status를 반환한다.
	 */
	public void delete(ExamRIO u) throws Exception{
		//쿼리문을 적고 실행하고 리턴한다.(delete는 update)
		//sql : examtable안에서 해당하는 학번 데이터를 지운다.
		//delete 시 문자열 변수에 sql문을 넣고, 넣고자 하는 데이터는 ?로 처리한다. (preparedstatement 형식)
		String query = "dalete from examtable where studentid = ?;";
		//update메소드에 쿼리문을 넣고,그 뒤에 ?에 넣을 데이터를 적어준다.(template 형식)
		//ExamRIO형의 객체에 들어있는 데이터를 get메소드로 가져왔다.
		jdbcTemplate.update(query,u.getStudentid());
	}
	
	/**
	 * 전체 데이터를 출력하는 메소드 
	 * @return 전체 데이터가 담긴 리스트를 리턴한다.
	 */
	public List<ExamRIO> getAllrecords() throws Exception{
		//로그를 남긴다.
		logger.info("Repo --- getAllrecords");
		//generic이 ExamRIO인 list를 선언하고  
		//jdbc Template의 query메소드를 통해서 전체 데이터 추출하고 list에 담는다. 
		List<ExamRIO> results = jdbcTemplate.query(
				//쿼리문을 쓰고
				"select * from examtable;",
				//콤마 뒤에 RowMapper 객체를 만든다.
				//해당 객체에서 ExamRIO형(u)을 반환하는 maprow메소드를 정의한다.(출력 데이터를 담는다)
				//그리고 해당 결과를 results에 담는다.
				new RowMapper<ExamRIO>() {
					//maprow메소드에 ResultSet이 매개변수로 있어서 각 데이터마다 하나씩 set해준다.
					@Override
					public ExamRIO mapRow(ResultSet rset, int rowNum) throws SQLException{
						ExamRIO u = new ExamRIO();
						u.setName(rset.getString("name"));
						u.setStudentid(rset.getInt("studentid"));
						u.setKor(rset.getInt("kor"));
						u.setEng(rset.getInt("eng"));
						u.setMat(rset.getInt("mat"));
						return u;
					}
				});
		//데이터를 담은 list를 반환한다.
		return results;
	}
		

	
	/**
	 * 해당 학번을 가진 학생만 출력한다.
	 * @param id : 학번(studentid)
	 * @return ExamRIO 형의 데이터
	 */
	public ExamRIO getRecordById(int studentid) throws Exception{
		
		//generic이 ExamRIO인 list를 선언하고  jdbctemplate 실행결과를 담는다.
		//특이한 점은 데이터를 하나 뽑는데도 list로 선언한다는 것이다.
		//전체 출력 메소드와 매우 흡사하다
		List<ExamRIO> results = jdbcTemplate.query(
				//쿼리문을 쓰고
				"select * from examtable where studentid=?;",
				//콤마 뒤에 RowMapper 객체를 만든다.
				//해당 객체에서 ExamRIO형(u)을 반환하는 maprow메소드를 정의한다.(출력 데이터를 담는다)
				//그리고 해당 결과를 results에 담는다.
				new RowMapper<ExamRIO>() {
					//maprow메소드에 ResultSet이 매개변수로 있어서 각 데이터마다 하나씩 set해준다.
					@Override
					public ExamRIO mapRow(ResultSet rset, int rowNum) throws SQLException{
						ExamRIO u = new ExamRIO();
						u.setName(rset.getString("name"));
						u.setStudentid(rset.getInt("studentid"));
						u.setKor(rset.getInt("kor"));
						u.setEng(rset.getInt("eng"));
						u.setMat(rset.getInt("mat"));
						return u;
					}
					//그리고 쿼리문의 ?부분에 들어갈 데이터를 끝에 넣는다.
				},studentid);
		//데이터를 담은 list를 반환 시 조건을 걸어서 조건에 맞게 보낸다
		//삼항 연산자로 isEmpty()인지 아닌지 판단해서 리턴하도록 하였다.
		//empty면 null값이 반환되고, 아니면  results.get(0)이 반환된다.
		//즉, 메소드에서 정의한 리턴 형(ExamRIO)에 맞게 데이터가 리턴 될 수 있다.
		return results.isEmpty() ? null : results.get(0);
	}
}
