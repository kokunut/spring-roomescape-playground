package roomescape;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ReservationController {

    // 예약 목록 (DB 대신 서버 메모리에 저장)
    private List<Reservation> reservations = new ArrayList<>();

    public ReservationController() {
        reservations.add(new Reservation(1L, "브라운", "2023-01-01", "10:00"));
        reservations.add(new Reservation(2L, "브라운", "2023-01-02", "11:00"));
        reservations.add(new Reservation(3L, "브라운", "2023-01-03", "12:00"));
    }

    // [화면] GET /reservation → templates/reservation.html 응답
    @GetMapping("/reservation")
    public String reservationPage() {
        return "reservation";
    }

    // [데이터] GET /reservations → 예약 목록을 JSON으로 응답
    @GetMapping("/reservations")
    @ResponseBody // 반환값을 화면 이름이 아니라 본문 데이터(JSON)로 보냄
    public List<Reservation> getReservations() {
        return reservations;
    }

//    ① 사용자가 브라우저에서 /reservation 접속
//      ↓ 서버가 화면(reservation.html)을 보내 줌
//    ② 화면이 열리면서, 화면 안의 JS가 /reservations 로 요청
//      ↓ 서버가 예약 목록 JSON을 보내 줌
//    ③ JS가 그 목록을 표로 그려서 보여 줌
}

