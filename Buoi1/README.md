# Buổi 1: Giới thiệu Spring Framework và 1 số khái niệm cơ bản

## Spring Framework là gì?
- Spring là một Java framework siêu to và khổng lồ, làm được đủ mọi thứ. Nó được chia thành nhiều module, mỗi module làm một chức năng, ví dụ Spring Core, Web, Data access, AOP, ...
- Spring được xây dựng dựa trên 2 khái niệm nền tảng là Dependency injection và AOP (Aspect Oriented Programming) hay còn gọi là lập trình hướng khía cạnh

- Để tạo 1 ứng dụng Spring khá vất vả: tạo project bằng maven hoặc gradle, thư viện, cấu hình XML, buil project, cấu hình server, ... 
- Spring Boot ra đời để khắc phục các điểm yếu trong Spring Framework

![text](https://images.viblo.asia/b831d7fe-158a-4663-b2d3-6680541e0604.png) 

- Spring đồng thời cung cấp nhiều framework con khác nhau:
  - Spring MVC: Spring MVC được thiết kế dành cho phát triển các ứng dụng web

  - Spring Security: Cung cấp các cơ chế xác thực và phân quyền cho ứng dụng của bạn

  - Spring Boot: Spring Boot là một framework giúp chúng ta phát triển cũng như chạy ứng dụng một cách nhanh chóng hoàn toàn độc lập

  - Spring Data: Cung cấp mô hình lập trình dựa trên Spring quen thuộc và nhất quán để truy cập dữ liệu trong khi vẫn giữ được các đặc điểm đặc biệt của kho dữ liệu cơ bản.
  - ...

## Cài đặt môi trường Spring
- Web server: 
  - Web server là một máy chủ web được thiết kế để lưu trữ hoặc cung cấp các tài nguyên web cho các máy tính khác trên Internet.
  - Web server có khả năng tiếp nhận các request từ các trình duyệt web và gửi phản hồi đến client thông qua giao thức HTTP hoặc các giao thức khác
  - Cách hoạt động của web server: khởi động máy chủ web -> nhận request -> xử lý -> trả vể response -> kết thúc phiên
  - ![text](https://topdev.vn/blog/wp-content/uploads/2019/05/webserver-la-gi.jpg)

- Apache Tomcat:
  - Apache Tomcat là một máy chủ ứng dụng web được sử dụng rộng rãi trong cộng đồng Java. Nó cung cấp một môi trường chạy cho các ứng dụng web Java Servlet và JSP
  - Trong Spring, bạn cũng có thể sử dụng Apache HTTP Server như một máy chủ web bằng cách tích hợp nó với các ứng dụng Spring thông qua các plugin hoặc proxy.

- Maven:
  - Maven sử dụng một định dạng XML được gọi là Project Object Model (POM) để quản lý các thông tin liên quan đến dự án như các thư viện, tài nguyên và các plugin được sử dụng trong quá trình xây dựng.
  - Nó cũng được sử dụng để build, run, deploy,… project.
  - Cài đặt maven: https://stackjava.com/install/maven-phan-1-maven-la-gi-cai-dat-maven.html

## DI và IoC trong Spring
![text](https://toidicodedao.files.wordpress.com/2015/09/ioc-and-mapper-in-c-1-638.jpg)
- DI (Dependency Injection) và IoC (Inversion of Control): để quản lý các phụ thuộc và tạo ra các đối tượng được quản lý bởi framework.
- DI:
  - Dependency Injection là một design pattern giúp thực hiện loosely coupled và giúp code hiệu quả, linh hoạt hơn
  - Là một cơ chế để triển khai IoC
  - DI là việc các Object nên phụ thuộc vào các Interface và thể hiện chi tiết của nó sẽ được Inject vào đối tượng lúc Runtime
  - Có 3 cách để thực hiện DI trong Spring:
    - Constructor Injection: Các đối tượng phụ thuộc được truyền vào constructor của đối tượng được tạo ra
    - Setter Injection: Các đối tượng được truyền vào thông qua setter method
    - Field Injection: Các đối tượng được truyền vào trực tiếp thông qua các trường của đối tượng được tạo ra

- IoC:
  - Dependency Injection giúp giảm thiểu sự phụ thuộc giữa các dependency với nhau, dễ mở rộng code. Tuy nhiên nếu một class có hàng chục dependency, việc inject thủ công chúng có thể đem lại sự bất tiện. Thay vào đó, ta sử dụng IoC Container
  - IoC Container trong Spring được gọi là Application context, một kho chứa tất cả các Bean. Khi một class được đánh dấu là một component thì IoC Container sẽ tạo ra một Object (một Bean)
    
- VD minh họa:

```java
  public class User{
    private IPhone iphone;
    public User(){
      iphone = new IPhone(); 
    }
}
```
- Khi bạn tạo ra một User, bạn sẽ tạo ra thêm 1 IPhone đi kèm với User đó. Lúc này, IPhone tồn tại mang ý nghĩa là dependency (phụ thuộc) của User.
- Vấn đề: Các Class không nên phụ thuộc vào các kế thừa cấp thấp, mà nên phụ thuộc vào Abstraction (lớp trừu tượng).

```java
  
  public interface Phone {
    
  }

  
  public class IPhone implements Phone {
    
  }

  public class User{
      private Phone phone;

      public User(Phone phone){
        this.phone = phone;
      }

      public static void main(String[] args) {
        Phone phone = new Iphone();
        User user = new User(phone); 
      }
  }
```
- Dependency Injection là việc các Object nên phụ thuộc vào các Abstract Class và thể hiện chi tiết của nó sẽ được Inject vào đối tượng lúc runtime.
- Tuy nhiên, lúc này, khi code bạn sẽ phải kiêm thêm nhiệm vụ Inject dependency (tiêm sự phụ thuộc). Thử tưởng tượng một Class có hàng chục dependency thì bạn sẽ phải tự tay inject từng ý cái. Việc này lại dẫn tới khó khăn trong việc code, quản lý code và dependency

```java
  public static void main(String[] args) {
      Phone phone = new Iphone();
      Hair hair = new Undercut();
      Outfit outfit = new Vest();
      User user = new User(phone, hair, outfit);
  }

```

- Lúc này, chúng ta sẽ phải nghĩ tới nguyên lý hoạt động của IoC:
  - Định nghĩa trước toàn bộ các dependency có trong Project, mô tả nó và tống nó vào 1 cái kho (container) và giao cho một quản lý không gian lưu trữ (context). 
  - Bất kỳ các Class nào khi khởi tạo, nó cần dependency gì, thì sẽ tự tìm trong container rồi inject vào đối tượng.

## 1 số annotation hỗ trợ
- Nguyên lý hoạt động của Spring Boot: SpringBoot tạo ra 1 container có tên là “ApplicationContext”, nó có khả năng quản lý 1 không gian lưu trữ gọi là “Context”, khi được tạo ra container sẽ tìm kiếm các “bean” trong Project và đưa vào context lưu trữ
- Bean: thực chất chỉ là một đối tượng (object) được quản lý bởi Spring container thay vì các đối tượng (object) thông thường khác được quản lý bởi các dev qua từ khóa khai báo new 

- 1 số annotation ứng dụng DI và IoC trong Spring Boot:
  - @Component: 
    - Dùng để đánh dấu các lớp giúp container biết lớp đó là 1 bean
    - Phương thức: getBean() thì container sẽ tìm trong context có bean nào có tên đó không và lấy ra 
    - Class được đánh dấu là Component sẽ tự tạo ra 1 instance và đưa vào ApplicationContext để quản lý
  - @Autowired:
    - Đánh dấu các dependancy là Autowired giúp spring tự inject 1 instance của depandency được đánh dấu là bean bằng @Component