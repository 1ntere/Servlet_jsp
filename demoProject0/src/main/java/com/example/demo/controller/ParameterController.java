package com.example.demo.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.model.dto.MemberDTO;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Controller // 응답|요청|제어 역할 명시 + Bean 등록
                                 //Bean : 스프링이 알아서 만들고 관리한다
@RequestMapping("param") //여기 밑에 작성하는 모든 주소 앞에 param이 기본으로 붙음
@Slf4j //Slf4j (Simple logging facade 4(for) Java
       //System.out.println과 비슷한 종류
       //System 출력에 비해 logging을 사용하는게 메모리 부담이 적기 때문에
       //log를 이용한 메세지 출력 시 자주 사용
public class ParameterController {

	//GETMAPPING main
	@GetMapping("main") //param/main 주소로 GET 방식 요청을 만듦
	public String paramMain() {
		return "param/param-main";
	  //return 할 때 폴더명/파일명 작성
        		    //param-main : param-main.html은 templates 밑에 param이라는 폴더에 존재
		      //param/param-main : html 파일을 볼 때 기본으로 templates 폴더를 바라보고
		      //                   그 하위 폴더인 param 폴더의 param-main.html 파일을 바라본다는 의미
	}
	
	@PostMapping("test1") //param/test1 POST 방식 요청
	public String paramTest1(HttpServletRequest req) {
		String inputName = req.getParameter("inputName");
		//param-main.html 파일에서 13번째 줄
		//<input type="text" name="inputName">에서
		//inputName으로 존재하는 값 inputName으로 가져오기
		String inputAddress = req.getParameter("inputAddress");
		//param-main.html 파일에서 15번째 줄
		//<input type="text" name="inputAddress">에서
		//inputAddress로 존재하는 값 inputAddress로 가져오기
		int inputAge = Integer.parseInt(req.getParameter("inputAge"));
		             //Integer.parseInt : 기본으로 가져오는 값이 String이기 때문에 Int로 변환
		//param-main.html 파일에서 14번째 줄
		//<input type="number" name="inputAge">에서
		//inputAge로 존재하는 값 inputAge로 가져오기
		
		//inputName과 inputAddress와 inputAge가 제대로 작성되었는지 확인하기
		System.out.println("이름 확인 : " + inputName);
		System.out.println("나이 확인 : " + inputAge);
		System.out.println("주소 확인 : " + inputAddress);
		
		//System.out.println 대신 log.debug를 활용해서 출력하는 것이 메모리 부담이 적음
		//코드 오류를 해결하기 위한 로그
		//코드 오류는 없는데 정상적으로 수행이 안되거나
		//값이 잘못된 경우 값을 추적
		log.info("정보 확인하기");
		log.debug("로그로 이름 확인 : " + inputName);
		log.debug("로그로 나이 확인 : " + inputAge);
		log.debug("로그로 주소 확인 : " + inputAddress);
		
		/*
		Spring에서 재요청(Redirect) 하는 방법
		Controller 메서드 반환(return) 값에
		redirect:요청주소
		작성하면 되돌아가짐
		*/
		return "redirect:/param/main";
	}
	/*
	2. @RequestParam - 낱개(한 개, 단 수)개 파라미터 얻어오기
	request 객체를 이용한 파라미터 전달 어노테이션
	매개변수 앞에 해당 어노테이션을 작성하면, 매개변수에 값이 작성됨
	작성되는 데이터는 매개변수(파라미터) 타입이 맞게 형변환(parse)이 자동으로 수행
	
	속성 추가 작성법
	@RequestParam(value="name", requires="false", defaultValue="1")
	
	value : 전달받은 input 태그의 name 속성 값
	required : 입력된 name 속성 값, 파라미터(매개변수) 필수 여부 지정 (기본값 true)
	           required = true 인 파라미터가 존재하지 않는다면 400 Bad Request 에러 발생
	           required = true 인 파라미터가 null인 경우에도 400 Bad Request 에러 발생
	defaultValue : 파라미터 중 일치하는 name 속성 값이 없을 경우 대입할 값 지정
	               required가 false일 경우 사용
	*/
	
	//400 Bad Request(잘못된 요청)
	//파라미터 불충분
	/*
	templates/param/param-main.html, 31~37번째 줄
	
	<form action="연결주소값2" method="POST">
		책 제목 : <input type="text" name="title"> <br>
		작성자 : <input type="text" name="writer"> <br>
		가격 : <input type="number" name="price"> <br>
		출판사 : <input type="text" name="publisher"> <br>
		<button>제출하기</button>
	</form>
	*/
	@PostMapping("test2") //param/test2 POST 방식 요청
	public String paramTest2(@RequestParam(/*value=*/"title"/*, required=true*/) String title,
							 @RequestParam(          "writer"                  ) String writer,
							 @RequestParam(          "price"                   ) int price,
							 @RequestParam(value="publisher", defaultValue="교보문고", required=false) String publisher) {
		log.info("문제없이 insert 가능한지 확인하기");
		log.debug("책 제목 : " + title);
		log.debug("작성자 : " + writer);
		log.debug("가격 : " + price);
		log.debug("출판사 : " + publisher);
		return "redirect:/param/main";
	}
	
	/*3. @RequestParma 어노테이션을 이용 (복수, 다수) 파라미터 가져오기*/
	//String[]
	//List<자료형>
	//Map<String, Object>
	
	//defaultValue 속성은 사용할 수 없음
	@PostMapping("test3")
	public String paramTest3(@RequestParam(value="color", required=false) String[] colorArr, 
							 @RequestParam(value="fruit", required=false) List<String> fruitList, 
							 @RequestParam Map<String, Object> paramMap) {
		log.info("colorArr : " + Arrays.toString(colorArr));
		log.info("fruitList : " + fruitList);
		log.info("paramMap : " + paramMap);
		
		//key(name속성값)이 중복되면 덮어쓰기가 됨
		//같은 name 속성 파라미터가 String[] / List로 나뉘어서 저장되는 것은 힘듦
		return "redirect:/param/main";
	}
	
	/*
	DTO와 VO
	DTO : Data Transfer Object, 데이터 캡슐화를 통해 데이터를 전달하고 관리
		  한 계층에서 다른 계층으로 데이터 전송을 위해 사용
		  계층 : 데이터가 HTML에서 DB로 전송되는 것은 한 계층에서 다른 계층으로 전송된다고 함

	VO  : Value Object, 값 자체를 표현하는 객체
	      한 번 값이 생성되면 그 값을 변경할 수 없음
	      생성자를 통해 값을 설정하고 Setter메서드를 제공하지는 않음
	*/
	/*
	@ModelAttribute
	DTO(또는 VO)와 같이 사용하는 어노테이션
	전달받은 파라미터(매개변수)의 name 속성 값이
	같이 사용되는 DTO의 필드명과 같다면
	자동으로 Setter를 호출해서 필드에 값을 저장
	
	★주의사항
	DTO에는 기본 생성자가 필수로 존재해야 함
	DTO에는 Setter가 필수로 존재해야 함
	
	어노테이션이 자동으로 생략 가능
	
	@ModelAttribute를 이용해 값이 필드에 저장된 객체를 커맨드 객체라고 함
	*/
	/*
	MemberDTO에 존재하는 변수
	private String memberId;
	private String memberPw;
	private String memberName;
	private int memberAge;
	*/
	@PostMapping("test4")
	public String paramTest4(/*@ModelAttribute*/ MemberDTO inputMember) {
		                     //@ModelAttribute : 생략가능
		//lombok으로 만든 Setter와 Getter로 값 가져오거나 설정하기
		//Setter와 Getter를 굳이 따로 만들지 않아도
		//lombok에서 @Setter, @Getter를 통해 가져오기 때문에 사용 가능함
		MemberDTO mem = new MemberDTO();
		mem.getMemberId();
		mem.getMemberPw();
		mem.getMemberName();
		mem.getMemberAge(); //Getter를 통해 나이 가져오기
		log.info("inputMember에 대한 get 정보 가져오기 : " + inputMember.toString());
		
		mem.setMemberId("1");
		mem.setMemberPw("pass01");
		mem.setMemberName("가나다");
		mem.setMemberAge(20); //Setter를 통해 나이 가져오기
		log.info("inputMember에 대한 set 정보 가져오기 : " + inputMember.toString());
		
		return "redirect:/param/main";
	}
}
