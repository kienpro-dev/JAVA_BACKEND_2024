# Buổi 3: Restful API, HTTP

## Restful API là gì?
-	RESTful API là một tiêu chuẩn dùng trong việc thiết kế các API cho các ứng dụng web để quản lý các resource.
-	RESTful API chú trọng vào tài nguyên hệ thống bao gồm các trạng thái tài nguyên được định dạng và được truyền tải qua HTTP.

## HTTP là gì?
- HTTP method là phương thức được thực hiện thông qua URL gửi lên server và trả về client dữ liệu dạng json. 
- Các method phổ biến: 
  - GET: lấy thông tin từ Server
  - POST: gửi thông tin lên Server
  - PUT: thay đổi thông tin dữ liệu trên Server với các thông tin được tải lên 
  - DELETE:  gỡ bỏ tất cả các đại diện hiện tại của resource

![](https://images.ctfassets.net/vwq10xzbe6iz/5sBH4Agl614xM7exeLsTo7/9e84dce01735f155911e611c42c9793f/rest-api.png)

- Bên cạnh các method, mỗi lần server phản hồi sẽ có những status khác nhau:
  - 1xx: Thông tin
  - 2xx: Thành công
  - 3xx: Sự điều hướng lại 
  - 4xx: Lỗi client
  - 5xx: Lỗi Server

## 1 số annotation thêm
- @ComponentScan:
    -	Thông thường khi chạy chương trình, Spring Boot sẽ đi tìm và lấy ra các bean trong toàn bộ project.
    -	Để cấu hình lại cho Spring Boot chỉ tìm kiếm các bean trong 1 package nhất định thì có thể sử dụng tới @ComponentScan(“package_path”).

- @PostMapping: sử dụng để định nghĩa một phương thức HTTP POST. Phương thức này được sử dụng để tạo mới một tài nguyên trên server.
- @ResponseBody: sử dụng để trả về dữ liệu dưới dạng json qua HTTP
- @RestController = @Controller + @ResponseBody
    -	Dùng @RestController thay thế cho cụm @Controller + @ResponseBody.
    -	Tại sao lại như vậy, đơn giản chỉ là sự phát triển phiên bản của Spring Framework, @Controller ra đời trước, @RestController ra đời sau nên bỏ đi để code gọn hơn

- @Configuration và @Bean
  - @Configuration là một Annotation đánh dấu trên một Class cho phép SpringFramework biết được đây là nơi định nghĩa ra các Bean.
  - @Bean là một Annotation được đánh dấu trên các method cho phép Spring Boot biết được đây là Bean và sẽ thực hiện đưa Bean này vào Context.
  - @Bean sẽ nằm trong các class có đánh dấu @Configuration.
-	Về bản chất, @Configuration cũng tương tự @Component, nó chỉ khác về cách sử dụng. Trong thực tế, nếu một Bean có quá nhiều logic để khởi tạo và cấu hình, thì chúng ta sẽ sử dụng @Configuration và @Bean để tự tay tạo ra Bean. Việc tự tay tạo ra Bean như này có thể hiểu phần nào là chúng ta đang config cho chương trình, thay vì dùng @Component.

