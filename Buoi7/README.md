# Tạo quan hệ giữa các bảng trong JPA, phân trang

## Quan hệ 1-1
- JPA cho phép thiết lập mối quan hệ giữa các bảng trong database hay các lớp trong spring boot. 
- Có thể hiểu đơn giản tương tự như quan hệ kết tập: thuộc tính của lớp này là đối tượng của lớp kia, có thể là 1 danh sách hay 1 đối tượng duy nhất
- Bước cơ bản để thiết lập quan hệ:
  - Tham số mappedBy trong các annotation quan hệ để thể hiện lớp này liên kết với lớp nào
  - Sử dụng @JoinColumn để thể hiện giữa 2 lớp liên kết đó sử dụng Id để làm khóa ngoại, tham số name có thể dùng để đăt tên cho khóa ngoại
- @OneToOne: tạo quan hệ 1-1 của 2 lớp

```java
class User {
    @Id
    private int id;
    private String name;

    @OneToOne
    @JoinColumn(name = "phone_id")
    private Phone phone;
}

class Phone {
    @Id
    private int id;
    private String name;

    @OneToOne(mappedBy = "phone")
    private Student student;
}
```

## Quan hệ 1-n, n-1 với mappedBy, cascade 
- @OneToMany, @ManyToOne: Tạo quan hệ 1-n, n-1 giữa 2 lớp
- Ngoài mappedBy, cần chỉ rõ thêm CascadeType giữa 2 lớp, vì trong quan hệ 1-n và n-1 giữa lớp cha và lớp con có sự rằng buộc chặt chẽ, khi CRUD cần phải làm kĩ càng, vì vậy sử dụng CascadeType giúp tiện lợi trong quá trình code và đảm bảo được tính toàn vẹn dữ liệu.
- Các loại CascadeType sử dụng:
  - PERSIST: Tự động persist trên các đối tượng liên quan khi đối tượng cha được persist.
  - MERGE: Tự động merge trên các đối tượng liên quan khi đối tượng cha được merge.
  - REMOVE: Tự động remove trên các đối tượng liên quan khi đối tượng cha được remove.
  - DETACH: Tự động detach trên các đối tượng liên quan khi đối tượng cha được detach.
  - REFRESH: Tự động refresh trên các đối tượng liên quan khi đối tượng cha được refresh
  - ALL: Các thay đổi trên đối tượng cha được tự động áp dụng cho tất cả các đối tượng con liên quan. Nó bao gồm tất cả các giá trị khác của CascadeType. (thường xuyên được sử dụng)

```java
class Student {
    @Id
    private int id;
    private String name;

    @OneToMany(mappedBy = "phone", cascade = CascadeType.ALL)
    private List<Subject> subjects;
}

class Subject {
    @Id
    private int id;
    private String name;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}

```

## FetchType
- Thêm nữa, khi truy vấn gọi đến đối tượng có các quan hệ 1-n, n-1, lớp cha sẽ load dữ liệu của lớp con (ví dụ khi gọi đến Student sẽ load dữ liệu của Subject do được liên kết), trong JPA có thể chỉ định sẽ load dữ liệu đó theo cách nào, gọi là FetchType
- Các loại FetchType sử dụng:
  - Lazy: các đối tượng liên quan đến đối tượng hiện tại sẽ chỉ được tải khi cần thiết. Nghĩa là, các đối tượng liên quan sẽ không được tải ngay lập tức khi đối tượng hiện tại được truy xuất. Thay vào đó, các đối tượng liên quan sẽ được tải khi các trường hoặc phương thức chứa các đối tượng đó được truy xuất
  - EAGER: các đối tượng liên quan đến đối tượng hiện tại sẽ được tải ngay lập tức khi đối tượng hiện tại được truy xuất. Nghĩa là, khi đối tượng hiện tại được truy xuất, tất cả các đối tượng liên quan đến nó cũng sẽ được tải ngay lập tức

- Ví dụ với lớp Subject có quan hệ @ManyToOne với lớp Student:
  - Khi truy xuất Subject. Student sẽ không được tải ngay lập tức nếu dùng LAZY: nó sẽ được tải khi truy cập trường student của Subject
  - Ngược lại nếu sử dụng EAGER: khi truy xuất Subject sẽ tải ngay lập tức student. Điều này có thể dẫn đến giảm hiệu xuất chương trình

## Lỗi vòng lặp vô hạn
- Lưu ý khi sử dụng quan hệ 1-n và n-1:
  - Có thể xảy ra vòng lặp vô hạn khi lấy dữ liệu ở dạng json, lý do là lớp cha có quan hệ với lớp con, lớp cha chứa dữ liệu lớp con, khi gọi đến lớp con thì lớp con cũng chứa dữ liệu lớp cha và cứ lặp lại như vậy.
  - Ứng dụng dùng @JsonIgnore và @JsonManagedReference, @JsonBackReference để loại bỏ dữ liệu bị lặp lại
- @JsonIgnore: bỏ qua một trường (thuộc tính) khi chuyển đổi đối tượng sang định dạng JSON
- @JsonManagedReference: Được sử dụng với quan hệ @OneToMany hoặc @ManyToMany để chỉ định đối tượng phía trên trong mối quan hệ. Khi một đối tượng được chuyển đổi sang định dạng JSON, đối tượng phía trên sẽ được bao gồm, trong khi các đối tượng phía dưới sẽ được bỏ qua.
- @JsonBackReference: Được sử dụng với quan hệ @OneToMany hoặc @ManyToMany để chỉ định đối tượng phía dưới trong mối quan hệ. Khi một đối tượng được chuyển đổi sang định dạng JSON, đối tượng phía dưới sẽ được bao gồm, trong khi các đối tượng phía trên sẽ được bỏ qua.

```java
class Student {
    @Id
    private int id;
    private String name;

    @OneToMany(mappedBy = "phone", fetch = FetchType.EAGER cascade = CascadeType.ALL)
    @JsonIgnore
    // @JsonManagedReference
    private List<Subject> subjects;
}

class Subject {
    @Id
    private int id;
    private String name;

    @ManyToOne(fetch = FetchType.ALL)
    @JoinColumn(name = "student_id")
    // @JsonBackReference
    private Student student;
}

```

## Data Transfer Object - DTO
- DTO là cách thức giúp tách biệt dữ liệu trong các Entity, giúp giảm sự phụ thuộc và tăng tính linh hoạt ứng dụng
- DTO chỉ cung cấp cho người dùng những thông tin cần thiết của Entity, ví dụ người dùng không cần quan tâm đến quan hệ giữa Student và Subject, chỉ cần biết thông tin giữa chúng là gì và DTO cung cấp những thông tin thực sự cần thiết

```java
class StudentDTO {
    private String name;
}

class SubjectDTO {
    private String name;
    private int studentId;
}
```

## Pageable trong JPA
- JPA cung cấp đối tượng Pageable giúp phân trang dữ liệu người dùng tìm kiếm
- Người dùng cung cấp page size là chia đều số dữ liệu trên mỗi trang, page number chỉ định người dùng muốn mở trang bao nhiêu 
- Ví dụ có 22 user theo thứ tự, page size là 4 và page number là 3, vậy tổng sẽ có 6 trang mỗi trang có 4 user, trang cuối (6) chỉ có 2 user, vào trang 3 sẽ là các user 9, 10, 11, 12 

```java
@Repository
interface UserRepository extends JpaRepository<User, Integer> {
    List<User> findAll(Pageable pageable);
} 

@Autowired
UserRepository userRepository;

@GetMapping("/users")
public ResponseEntity<?> pageableFindAll(@RequestParam(name = "page", required = false) int page,
                                         @RequestParam(name = "size", required = false) int size) {
    if (page < 0) {
        return ResponseEntity.ok(userRepository.findAll());
    }
    else {
        Page<User> users = userRepository.findAll(PageRequest.of(page, size));
        return ResponseEntity.ok(users.getContent());
    }
}
```