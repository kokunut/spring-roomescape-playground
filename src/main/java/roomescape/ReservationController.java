package roomescape;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import static io.micrometer.common.util.StringUtils.isBlank;

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
        if (isBlank(request.getName()) || isBlank(request.getDate()) || isBlank(request.getTime())) {
            throw new BadRequestReservationException("예약 정보가 비어 있습니다.");
        }
        // 받은 정보에 새 id를 붙여 Reservation 생성 (getAndIncrement: 현재 번호 주고 +1)
        Reservation newReservation = new Reservation(index.getAndIncrement(), request.getName(), request.getDate(), request.getTime());
        reservations.add(newReservation);   // 명단에 저장
        return ResponseEntity
                .created(URI.create("/reservations/" + newReservation.getId()))
                .body(newReservation);
    }

    @DeleteMapping("/reservations/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        // removeIf는 하나라도 지우면 true, 아무것도 못 지우면 false를 돌려줌
        boolean removed = reservations.removeIf(reservation -> reservation.getId().equals(id));
        if (!removed) {
            throw new NotFoundReservationException("해당 예약이 없습니다.");
        }
        return ResponseEntity.noContent().build();
    }

    // 비어 있는지 검사하는 도우미 (null 또는 공백만 있어도 비어 있다고 봄)
    private boolean isBlank(String value) {
        return value == null || value.isBlank();
    }

    // NotFoundReservationException이 던져지면 404로 응답
    @ExceptionHandler(NotFoundReservationException.class)
    public ResponseEntity<Void> handleNotFound(NotFoundReservationException e) {
        return ResponseEntity.notFound().build();
    }

    // BadRequestReservationException이 던져지면 400으로 응답
    @ExceptionHandler(BadRequestReservationException.class)
    public ResponseEntity<Void> handleBadRequest(BadRequestReservationException e) {
        return ResponseEntity.badRequest().build();
    }
}

