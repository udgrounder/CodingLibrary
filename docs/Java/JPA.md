# JPA Entity 기본 구조 최적화

### 기본 컨셉 및 규칙

< 기본 컨셉>

1. Entity객체(데이터)를 불완전 한 형태로 생성 하거나 실수로 데이터를 오염 시키지 않아야 한다.
2. 개발자가 실수 할 수 있는 지점을 최대한 줄인다.
3. 성능 최적화를 진행 한다.

<코딩 규칙>

- 구조
    - Setter를 만들지 않는다. (기본 컨셉 1)
    - 클래스에 AllArgsConstructor 생성자와 Class Builder를 만들때 AccessLevel 을 Protected 로 한다. (기본 컨셉 1)
      - 이때 BuilderName을 설정한다. 이걸 안하게 되면 기본 메인으로 생성한 Builder 를 덮어쓰기 때문에 외부에서 Main builder 를 사용 못하게 된다. 그래서 이름을
      privateBuilder 로 만들고 내부에서 해당 Builder 를 사용한다.
    - DefaultColumn 값을 설정 하지 않는다. (기본 컨셉 2)
      - 코딩을 통해서 처리 하는 것을 기본으로 하고 직접 설정하지 않은 값이 적용 되는 일이 없도록 사용 하지 않는다.
        - DynamicInsert를 사용하지 않는다.(기본 컨셉 2)
            - Insert나 Update 시에 의도 하지 않는 기본 값이 설정 되는 것을 방지 한다.(Default 값을 사용 하지 않는 것과 같은 이유 이다.)
            - Update 시에 데이터 객체를 조회 해서 객체 메소드를 통해서만 처리 하기 때문에 사용 하지 않는게 안전 한다.
        - Datebase 에서 구조적으로 넣어야 하는 설정은 모두 기술 한다.(기본 컨셉 2)
            - 예를 들어 nullable = false 같이 구조적인 설정 값은 모두 설정한다.
    - 종속 관계를 가진 객체는 모두 LAZY로 쓰자. 즉시 로딩 사용하지 말자 (기본 컨셉 3)
        - 즉시 로딩을 하게 되면 관련 된 하위 객체를 모두 가져 오게 되기 때문에 불필요한 쿼리가 실행되어 성능 저하를 유발 한다.
- 객체 생성시
    - 반드시 필요한 값만 입력 받는 생성자 Builder를 만들어 객체를 생성 한다.
        - 여러 방식의 생성자가 필요한 경우 생성자 빌더를 여러개 만들어 처리 한다.
        - 메인 생성자 Builder 외 엔 모두 BuilderMethodName 명을 지정 해서 사용 한다.
    - 생성자 Builder를 통해서 처리 하기 어려운 경우는 Static 메소드 Builder 를 만들어 사용 한다.
        - 기본 컨셉을 해치지 않기 위해서 클래스 Builder 와 AllArgsConstructor 의 AccessLevel 을 Private으로 제한 한다.
- 데이터 갱신시
    - Entity 내부에 명확한 목적을 가진 메소드만 만들고 그 메소드 안에서 데이터 갱신에 대해서 책임 진다.


### 샘플 소스

```java


/**
 * 빈자리 알람 Entity
 */
@Getter
@Builder(builderClassName = "privateBuilder", builderMethodName = "privateBuilder", access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity // Entity에서는 Setter가 아닌, 명확한 Function사용 한다.
@Table(name = "vacancy")
//@DynamicInsert  // null 값은 insert 시에 제외 한다.(기본값으로 설정 되게 처리함) -> 사용하지 않는다.
public class Vacancy extends BaseTimeEntity {

    @Id  // PK 필드
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto increment
    private Long vacancyId;

    @Column(nullable = false)
    private Long campId;

    @Column(nullable = false)
    private Long memberId;

    @Column(nullable = false)
    private LocalDate bookingStartDate;

    @Column(nullable = false)
    private LocalDate bookingEndDate;

    @Column(nullable = false)
    private Boolean isAlert;

    @Column(nullable = false)
    private Boolean isDeleted;

    @Builder
    public Vacancy(Long campId, Long memberId, LocalDate bookingStartDate, LocalDate bookingEndDate) {

        Assert.notNull(campId, "campId cannot be null");
        Assert.notNull(memberId, "memberId cannot be null");
        Assert.notNull(bookingStartDate, "bookingStartDate cannot be null");
        Assert.notNull(bookingEndDate, "bookingEndDate cannot be null");

        this.campId = campId;
        this.memberId = memberId;
        this.bookingStartDate = bookingStartDate;
        this.bookingEndDate = bookingEndDate;
        this.isAlert = false;
        this.isDeleted = false;
    }

    @Builder(builderClassName = "byNodateBuilder", builderMethodName = "byNodateBuilder")
    public Vacancy(Long campId, Long memberId) {

        Assert.notNull(campId, "campId cannot be null");
        Assert.notNull(memberId, "memberId cannot be null");

        this.campId = campId;
        this.memberId = memberId;
        this.bookingStartDate = LocalDate.now();
        this.bookingEndDate = LocalDate.now();
        this.isAlert = false;
        this.isDeleted = false;
    }

    @Builder(builderClassName = "byBookingStartDateBuilder", builderMethodName = "byBookingStartDateBuilder")
    public static Vacancy byBookingStartDateBuilder(LocalDate bookingStartDate) {
        Assert.notNull(bookingStartDate, "bookingStartDate cannot be null");
        return Vacancy.privateBuilder().bookingStartDate(bookingStartDate).build();
    }

    public void updateBookingDate(LocalDate bookingStartDate, LocalDate bookingEndDate, Boolean isAlert) {
        Assert.notNull(bookingStartDate, "bookingStartDate cannot be null");
        Assert.notNull(bookingEndDate, "bookingEndDate cannot be null");
        Assert.notNull(isAlert, "isAlert cannot be null");

        this.bookingStartDate = bookingStartDate;
        this.bookingEndDate = bookingEndDate;
        this.isAlert = isAlert;
    }

    public void delete() {
        isDeleted = true;
    }

}


```
 
