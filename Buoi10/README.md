# Buổi 10: Spring security
## Tổng quan lý thuyết
- Spring security là 1 framework cho phép lập trình viên cung cấp 1 trình xác thực (Authentication) và phân quyền (Authorization) trong ứng dụng Java
- Đồng thời giúp ứng dụng java bảo mật tốt hơn và tránh 1 số tấn công qua mạng
- Luồng hoạt động bên trong spring security
    - Người dùng gửi các request đến hệ thống: truy cập vào trang web và thực hiện hành động nào đó
    -	Spring xem xét yêu cầu, và xem người dùng đã xác thực hay chưa. Nếu chưa, sẽ được chuyển đến trang login để xác thực người dùng 
    -	Spring nhận thông tin từ login và sử dụng UserDetailService để xác thực xem người dùng đó có trong database không và trả về 1 UserDetail nếu thông tin đúng
    -	Lúc này, coi như đã xác thực thành công thì spring sẽ tạo ra 1 phiên làm việc (session), đồng thời xác định xem quyền truy cập của người dùng vào tài nguyên 
    -	Nếu xác thực không thành công hoặc không có quyền truy cập tài nguyên, spring sẽ thông báo lỗi 
    -	Quá trình này được lặp lại suốt phiên làm việc, cho đến khi người dùng đăng xuất hoặc phiên làm việc đã hết hạn. 
    -	Spring xóa token hoặc phiên làm việc cũ và kết thúc xác thực khi người dùng đăng xuất 

![](https://media.techmaster.vn/api/static/c77cd27k0cmou6gu4m20/53wX-J9Y)

## Xác thực (Authentication)
- Việc xác thực đầu tiên được thực hiện bởi spring qua việc login, để xác thực xem người dùng có tồn tại trong DB không. 
- Sau khi thành công truy cập tài nguyên, spring tạo ra 1 phiên hoặc token suốt quá trìng làm việc 
- Mỗi 1 request của người dùng, spring sử dụng session hoặc token để xem trong phiên đó người dùng đã xác thực hay chưa và có thể bắt họ xác thực lại qua việc login
- Việc xác thực kết thúc khi người dùng đăng xuất và phiên làm việc cũ sẽ được xóa đi
- Các thành phần quan trọng trong việc xác thực:
  + SecurityContextHolder: là nơi spring lưu trữ thông tin chi tiết của người dùng đã được xác thực
  + SecurityContext: thu được từ trên, và chứa cái Authentication của người dùng đã được xác thực hiện tại
  + Authentication: có thể là đầu vào cho AuthenticationManager, để cung cấp thông tin xác thực từ người dùng hoặc người dùng hiện tài từ SecurityContext
  + GrantedAuthority: quyền truy cập được cấp cho người dùng trong Authentication
  + AuthenticationManager: là 1 API định nghĩa cách spring xác thực người dùng
  + ProviderManager: là 1 implementation phổ biến của AuthenticationManager
  + AuthenticationProvider: thực hiên chỉ định các xác thực cụ thể 
  + AuthenticationEntryPoint: sử dụng để yêu cầu xác thực từ người dùng trong 1 phiên làm việc cụ thể, như là đăng nhập
  + UsernamePasswordAuthenticationToken : 1 class cung cấp sẵn trong SpringSecurity để đại diện cho thông tin xác thực người dùng dựa trên username và password.
  + UserDetailService : Là 1 interface trong SpringSecurity , chứa method loadUserByUsername , sử dụng để tìm kiếm thông tin người dùng và trả về 1 đối tượgn UserDetail.
  + UserDetails : Là 1 interface trong SpringSecurity , đại diện cho thông tin người dùng trong hệ thống. Cung cấp các thông tin như là username , password , roles hoặc các thông tin khác.

## Phân quyền (Authorization)
- Sau khi xác thực thành công, việc phân quyền cho người dùng khi sử dụng trang web là cần thiết. Điều này cho phép lập trình viên kiểm soát được những thứ mà người dùng hay người quản trị có thể truy cập vào tài nguyên
- Các quyền của người dùng được lưu trữ bằng string hoặc phổ biến hơn là sử dụng enum để tối ưu giúp định nghĩa rõ ràng các quyền của người dùng.
- Các quyền đó sẽ được phân vào các endpoint do người dùng mong muốn bằng @Configuration
- Hoặc có thể phân quyền trực tiếp trong controller bằng Sử dụng các annotation như @PreAuthorize, @PostAuthorize, @Secured, @RolesAllowed, và @EnableGlobalMethodSecurity để xác định quyền truy cập vào các phương thức của controller.