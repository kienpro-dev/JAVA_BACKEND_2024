# Database Connection, Spring Data JPA

## Spring Data JPA: 
- Spring Data JPA: JPA là một phần trong hệ sinh thái Spring Data, nó tạo ra một layer ở giữa tầng service và database, giúp chúng ta thao tác với database một cách dễ dàng hơn, tự động config và giảm thiểu code thừa thãi. Nó đã wrapper Hibernate và tạo ra một interface mạnh mẽ. 

```java
#DATABASE
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/demo
spring.datasource.username=root
spring.datasource.password=123456

# JPA / HIBERNATE
# spring.jpa.show-sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.MySQL5InnoDBDialect
spring.jpa.hibernate.ddl-auto=update

```

## @Entity
- Trong Spring Data JPA, chúng ta có thể sử dụng @Entity để đánh dấu một lớp là một thực thể (entity) trong CSDL. Khi chúng ta khởi chạy ứng dụng Spring, Spring Data JPA sẽ tự động tạo các bảng trong CSDL tương ứng với các lớp được đánh dấu bằng @Entity.
- Tất cả các thuộc tính của lớp được ánh xạ vào các cột trong bảng tương ứng trong CSDL.
- Thường được sử dụng chung với: 
  - @Table(name = "table_name"): Đánh dấu lớp với tên của bảng tương ứng trong CSDL.
  - @Id: Đánh dấu thuộc tính id là khóa chính của bảng.
  - @GeneratedValue(): Định nghĩa cách sinh khóa chính, có thể chuyền vào GenerationType.IDENTITY để sinh khóa chính tự động tăng.
  - @Column(name = "column_name"): Đánh dấu thuộc tính với tên cột tương ứng trong CSDL.

## RESTful API CRUD
- RESTful API CRUD: là một phương pháp thiết kế các API hỗ trợ các thao tác cơ bản như Create (Tạo mới), Read (Đọc), Update (Cập nhật) và Delete (Xóa)
- Sử dụng với các annotation như @GetMapping, @PostMapping, @PutMapping, @DeleteMapping 
  - Create (Tạo mới): sử dụng HTTP POST để tạo mới một tài nguyên trong API.
  - Read (Đọc): sử dụng HTTP GET để lấy thông tin một tài nguyên từ API.
  - Update (Cập nhật): sử dụng phương thức HTTP PUT hoặc PATCH để cập nhật một tài nguyên trong API.
  - Delete (Xóa): sử dụng phương thức HTTP DELETE để xóa một tài nguyên trong API.

## Query Creation
-	Trong Spring JPA, có cơ chế mà ta có thể xây dựng query mà không cần viết thêm code thông qua cách đặt tên của method: Find..By, read..By, query..By, count..By, và get..By, và …
-	Ta cần tạo 1 interface kế thừa JpaRepository, và đánh dấu lớp đó bởi annotation @Repository
-	@Repository: dùng để đánh dấu 1 lớp là 1 repository có trách nhiệm xử lý tương tác với cơ sở dữ liệu .
-	JpaRepository<?,?> một interface của Spring Data JPA cung cấp các phương thức cơ bản để tương tác với cơ sở dữ liệu. Interface này được định nghĩa với hai tham số, đầu tiên là kiểu của đối tượng, thứ hai là kiểu của khóa chính.
-	Bên trong có thể viết các đối tượng sử dụng truy vấn:

```java
User findByUsername(String username);

User findByUsernameAndId(String username, Long id);

User findByUsernameOrId(String username, Long id);
```

## @Query
-	Spring JPA hỗ trợ thêm 1 phương pháp khác để xây dựng truy vấn thông qua các query tự viết. Sử dụng thay thế khi một query xử lý quá phức tạp, việc sử dụng ở trên có thể khiến tên của đối tượng trở nên quá dài.
-	@Query: được sử dụng để định nghĩa truy vấn SQL t cho một phương thức trong Repository.
-	Ta có thể viết query thông qua JPQL và native query: JPQL truy vấn thông qua đối tượng dựa trên lớp đối tượng đó thay vì CSDL trong khi native query là 1 câu truy vấn SQL thuần túy.
```java
// JPQL
@Query("select u from User u where " + 
        "u.id = ?1 and u.username = ?2") 
User findUser(Long id, String username);

// native Querry
@Query(value = "select * from users where " +
        "id = ?1 and username = ?2", nativeQuery = true) 
User findUserNative(Long id, String username);

```
