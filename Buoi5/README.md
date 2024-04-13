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

