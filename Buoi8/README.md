# Quan hệ nhiều nhiều trong JPA, cách ánh xạ dữ liệu
## Quan hệ nhiều nhiều với @ManyToMany
- Khi thiết kế cơ sở dữ liệu, việc nối trực tiếp 2 bảng có quan hệ nhiều-nhiều với nhau là không được khuyến khích , nó ảnh hưởng đến hiệu suất của chương trình khi gọi dữ liệu cũng như lưu trữ chúng ở 2 bảng
- Vì vậy khi có 2 bảng có quan hệ nhiều-nhiều với nhau, nên tách 1 quan hệ nhiều-nhiều thành 2 quan hệ 1-nhiều, tức là tạo 1 bảng trung gian và cho 2 lớp đó quan hệ 1-nhiều đến bảng đó, bảng trung gian này lưu khóa chính của 2 bảng trở 2 khóa phụ
- JPA cung cấp thêm @ManyToMany và @JoinTable để hỗ trợ tạo quan hệ nhiều-nhiều giữa 2 lớp

```java
@Entity
class Book {
    @Id
    private Long id;

    private String name;

    @ManyToMany(cascade = CascadeType.ALL, mappedBy = "books")
    @JsonIgnore
    private List<Genre> genres;
}

@Entity 
class Genre {
    @Id
    private Long id;

    private String name;

    @ManyToMany
    @JsonIgnore
    @JoinTable(name = "books_genres",
        joinColumns = @JoinColumn(name = "book_id", foreignKey = @ForeignKey(name = "FK_BOOK_GENRE1")),
        inverseJoinColumns = @JoinColumn(name = "genre_id", foreignKey = @ForeignKey(name = "FK_BOOK_GENRE2"))
    )
    private List<Book> books = new ArrayList<>();
}

```

- Giải thích:
  - Sử dụng @ManyToMany để tạo quan hệ nhiều nhiều giữa 2 bảng
  - Sử dụng @JoinTable để tạo bảng trung gian cho 2 bảng trước liên kết đến
  - Thuộc tính name chỉ định tên bảng trung gian
  - Thuộc tính joinColumns và inverseJoinColumns sử dụng @JoinColumn để sử dụng 2 khóa chính từ 2 bảng để tạo khóa ngoại liên kết
  - Có thể chỉ định thêm thuộc tính foreignKey với @ForeignKey để chỉ định ràng buộc khóa ngoại (tương tự từ khóa CONSTRANT trong sql)
- Lưu ý khi sử dụng quan hệ nhiều nhiều:
  - Việc sử dụng @ManyToMany và @JoinTable tạo bảng trung gian có thể hữu ích ở nhiều trường hợp, tuy nhiên sẽ không thể thêm bất kì thuộc tính nào mà người dùng muốn vào bảng trung gian, ví dụ muốn thêm ngày tạo, ngày sửa thì không được.
  - Giải pháp là tạo 1 lớp khác và sử dụng chính lớp đó làm bảng trung gian thay vì tạo tự động bằng @JoinTable

## Mô hình 3 lớp: Three Layers trong thiết kế API
![](https://media.geeksforgeeks.org/wp-content/uploads/20231108115918/Three-Tier-architecture.png)

- Three Layers là một kiến trúc kiểu client/server mà trong đó giao diện người dùng, các quy tắc xử lý, và việc lưu trữ dữ liệu được phát triển như những module độc lập, và hầu hết là được duy trì trên các nền tảng độc lập, và mô hình 3 lớp được coi là một kiến trúc phần mềm và là một mẫu thiết kế.” 
- Mô hình 3 lớp có 3 tầng:
  - Presentation layer: tầng này tương tác với người dùng, bằng View, Controller (trong MVC) hoặc API (nếu có).
  - Business logic layer: Chứa toàn bộ logic của chương trình, các đa số code nằm ở đây, đại diện là Service trong Spring 
  - Data access layer: Tương tác với database, trả về kết quả cho tầng business logic, đại diện là Repository trong Spring

## Ánh xạ dữ liệu trong Spring
- Trong spring có 2 cách để ánh xạ dữ liệu chính từ DTO <-> Entity: ModelMapper và MapStruct
  - ModelMapper: Sử dụng trong những đối tượng cần ánh xạ đơn giản và dùng nhanh chóng do là API thủ công có sẵn
  - MapStruct: Cần tự thiết lập phúc tạp hơn. Thường dùng trong những ánh xạ phức tạp hơn và project lớn hơn, cần hiệu xuất cao.

```java
@Entity
class Student {
    @Id
    private Long id;

    private String name;

    private int age;

    private String address;
}

class StudentDTO {
    private String name;

    private int age;

    private String address;
}
```

- Sử dụng ModelMapper để chuyển từ Student <-> StudentDTO
- Tạo file config:
```java
@Configuration
public class ModelMapperConfig {
    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return modelMapper;
    }
}
```
- Có 3 mức độ mapping:
  - MatchingStrategies.STANDARD: không quan tâm thứ tự thuộc tính, mọi từ trong tên thuộc tính nguồn phải tồn tại trong tên thuộc tính đích, mọi từ của thuộc tính đích phải khớp hết.
  - MatchingStrategies.LOOSE: không quan tâm thứ tự thuộc tính, từ cuối cùng phải có trong tên thuộc tính đích, chỉ cần từ cuối trong thuộc tính đích khớp là được.
  - MatchingStrategies.STRICT: Thứ tự từ phải đúng, mọi từ trong thuộc tính nguồn phải khớp với toàn bộ từ, mọi từ trong thuộc tính đích phải khớp hết
- Vài thuộc tính cấu hình căn bản như sau:
  - setSkipNullEnabled(): có cho phép bỏ qua thuộc tính null hay không
  - setDeepCopyEnabled(): mặc định dùng shallow copy, dùng deep copy thì sẽ chậm hơn.
  - setMatchingStrategy(): đặt loại mapping (như phần trên)
  - setFieldAccessLevel(): chỉ định field truy cập ở mức độ nào (private, public,...)
  - setMethodAccessLevel(): chỉ định mức độ truy cập method, getter và setter (như trên)
  - setSourceNameTokenizer(): chỉ định quy ước đặt tên cho thuộc tính ở source (object nguồn dùng để map)
  - setDestinationNameTokenizer(): chỉ định quy ước đặt tên cho thuộc tính ở đích (object map ra).

## Swagger
- Swagger là một framework phổ biến được sử dụng để tạo tài liệu mô tả cho các dự án API. Nó cho phép bạn mô tả API của mình bằng các chú thích (annotations) trong mã nguồn Java, sau đó tự động tạo ra tài liệu API dưới dạng trang web dễ đọc.
- Swagger hỗ trợ việc quản lý tài liệu API, bao gồm:
  - Tự động tạo tài liệu API từ mã nguồn Java.
  - Hỗ trợ mô tả các request, response, parameter, và các thông tin khác của API.
  - Cho phép thử nghiệm API trực tiếp từ tài liệu mô tả.
  - Hỗ trợ nhiều phiên bản API khác nhau.
  - Tích hợp với các công cụ khác như Postman để kiểm tra API.

- Thêm dependancy
```xml
<dependencies>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger2</artifactId>
        <version>2.9.2</version>
    </dependency>
    <dependency>
        <groupId>io.springfox</groupId>
        <artifactId>springfox-swagger-ui</artifactId>
        <version>2.9.2</version>
    </dependency>
</dependencies>
```

- Tạo file config:
```java
@Configuration
@EnableSwagger2
public class Swagger2Config {
  @Bean
  public Docket api() {
    return new Docket(DocumentationType.SWAGGER_2).select()
        .apis(RequestHandlerSelectors.basePackage("com.example.project-name.controller"))
        .paths(PathSelectors.regex("/.*"))
        .build()
        .apiInfo(apiEndPointsInfo());
  }

  private ApiInfo apiEndPointsInfo() {
    return new ApiInfoBuilder().title("Test API")
        .description("Documentation for Test API v1.0")
        .version("1.0")
        .contact(new Contact("Nguyen Kien", "", "tienkiennropro@gmail.com"))
        .license("Apache 2.0")
        .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
        .build();
  }
}
```