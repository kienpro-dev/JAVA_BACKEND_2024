# Xử lý Exception trong Spring Boot

## Build và run ứng dụng Spring Boot trên cmd
- Maven cung cấp build project spring ra 1 file jar để có thể sử dụng mà không cần phải chạy code
- Cú pháp:
  - mvn clean install
  - java -jar "project_name.jar" (mở trước cmd trong thư mục chứa file jar đã build, project_name/target/...)
  
## Tìm hiểu thêm 1 số annotation
- @Value: được sử dụng để inject giá trị mặc định vào trong một bean của ứng dụng. Giá trị được inject có thể định nghĩa trong file application.properties

```java
    user.username=admin
    user.password=123
```

```java

@Configuration
class UserConfig {

    @Value("${user.username}")
    private String username;

    @Value("${user.password}")
    private String password;
}
```

- @PathVariable: được sử dụng để lấy giá trị từ URL và truyền vào một phương thức của controller, cho phép trích xuất giá trị động từ URL để dùng.

```java
@GetMapping("/user/getUser/{id}")
public ResponseEntity<?> getUser(@PathVariable int id) {
    // code
}
```
- Khi một request được gửi đến /user/{id}, giá trị của {id} trong URL sẽ được trích xuất và gán vào biến id bên trong phương thức. Sau đó phương thức có thể dùng biến id này để truy vấn thông tin của user có id đó

- @RequestBody: có tác dụng tương tự @ModelAttribute hay @RequestParam khi có thể gửi dữ liệu từ client lên server để xử lý trong controller. @RequestBody thường dùng để chuyển định dạng dữ liệu json được gửi đi sang dữ liệu đối tượng trong Java để dễ xử lý (tác dụng ngược lại với @ResponseBody)

```java
@PostMapping("/user/createUser")
public ResponseEntity<?> createUser(@RequestBody User user) {
    // code
}
```

## Custom Exception 
![](https://drive.google.com/file/d/1RbX1wIT1zvxVpnoDKHo3CUHeTGqYQZ6D/view)
- Xử lý ngoại lệ (Exception handling) là một phần quan trọng của việc phát triển ứng dụng, bao gồm cả việc xây dựng các Restful API trong Spring Boot
- @RestControllerAdvice thường được sử dụng cùng với @ExceptionHandler để can thiệp vào việc xử lý của các @RestController.
- @ResponseStatus cho phép bạn định nghĩa HTTP status code mà bạn muốn trả về cho người dùng. 
[Xem thêm các HTTP Status Code phổ biến](https://topdev.vn/blog/http-status-code-la-gi/?amp&utm_source=google&utm_medium=cpc&utm_campaign=topdev&utm_content=performance&gad_source=1&gclid=Cj0KCQjw2uiwBhCXARIsACMvIU3R15eGzM5VSYmlfhgSzMsjNMGx_XScadeCyePppGq23f57pDdGUZsaAsCSEALw_wcB)

- Mục đích của việc custom exception để gửi về người dùng thông tin hữu ích hơn theo ý hiểu của người lập trình
- Các bước custom exception để xử lý:
B1: Tạo các model, controller cho ứng dụng
B2: Tạo đối tượng ErrorRespone để gửi thông tin lỗi về client
B3: Tự định nghĩa 1 lớp Exception để tiến hành custom theo ý của người lập trình
B4: Tạo lớp ExceptionHandler dùng các annotation để bắt lỗi trong chương trình
B5: Test thử bằng postman

