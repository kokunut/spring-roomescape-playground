package roomescape;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RoomescapeApplication {
    public static void main(String[] args) {
        SpringApplication.run(RoomescapeApplication.class, args);
    }

}

//스프링 시작
//  ↓ roomescape 패키지를 훑으면서
//  ↓ "@Controller 스티커가 붙은 클래스" 를 찾음
//  ↓ 그 안의 @GetMapping 들을 읽어서 "GET / 는 home() 담당" 이라고 연결표를 만듦
//요청: GET /
//        ↓ 연결표를 보고 home() 실행
