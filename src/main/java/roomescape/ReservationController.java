package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Controller
public class ReservationController {

    // 예약 목록 (DB 대신 서버 메모리에 저장)
    private List<Reservation> reservations = new ArrayList<>();

    private final AtomicLong index = new AtomicLong(1);

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

    @PostMapping("/reservations")
    public ResponseEntity<Reservation> create(@RequestBody Reservation request){
        // 받은 정보에 새 id를 붙여 Reservation 생성 (getAndIncrement: 현재 번호 주고 +1)
        Reservation newReservation = new Reservation(index.getAndIncrement(), request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);   // 명단에 저장
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        reservations.removeIf(reservation -> reservation.getId().equals(id));
        return ResponseEntity.noContent().build();
    }
}

