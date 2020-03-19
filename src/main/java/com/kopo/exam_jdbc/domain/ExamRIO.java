package com.kopo.exam_jdbc.domain;

public class ExamRIO {
	
	//다룰 데이터 선언
	//실제 DB에 있는 데이터와 동일 시 하는 것이 좋다.
	private String name;	//이름
	private int studentid;	//학번
	private int kor;		//국어성적
	private int eng;		//영어 성적
	private int mat;		//수학 성적
	
	//constructor 선언 빈 생성자와 데이터가 같이 들어있는 생성자를 같이 만들어 준다.
	public ExamRIO() {
		super();
	}
	//해당 객체를 생성할 때 여기 5개의 데이터가 들어간다.
	public ExamRIO(String name, int studentid, int kor, int eng, int mat) {
		super();
		this.name = name;
		this.studentid = studentid;
		this.kor = kor;
		this.eng = eng;
		this.mat = mat;
	}
	
	//getter, setter 메소드 생성 : 데이터를 불러오고 적용할 때 사용한다. 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getStudentid() {
		return studentid;
	}
	public void setStudentid(int studentid) {
		this.studentid = studentid;
	}
	public int getKor() {
		return kor;
	}
	public void setKor(int kor) {
		this.kor = kor;
	}
	public int getEng() {
		return eng;
	}
	public void setEng(int eng) {
		this.eng = eng;
	}
	public int getMat() {
		return mat;
	}
	public void setMat(int mat) {
		this.mat = mat;
	}
	
	
	
	
	

}
