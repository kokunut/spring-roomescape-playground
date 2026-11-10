package roomescape;

// 예약 한 건을 나타내는 클래스 (DTO: 데이터를 담아 주고받는 상자)
//
// [만든 이유]
// - 예약 한 건의 틀: API 명세의 { "id", "name", "date", "time" } 모양을 필드로 정의
// - 응답: List<Reservation> 을 반환하면 스프링이 JSON 배열로 변환 (화면의 JS가 받아서 표로 그림)

public class Reservation {
    private Long id;
    private String name;
    private String date;
    private String time;

    public Reservation(Long id, String name, String date, String time) {
        this.id = id;
        this.name = name;
        this.date = date;
        this.time = time;
    }

    // getter: 스프링이 값을 꺼내 JSON 으로 만들 때 필요
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getDate() { return date; }
    public String getTime() { return time; }
}
