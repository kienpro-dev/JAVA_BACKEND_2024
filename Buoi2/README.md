# Buổi 2: Tổng quan MVC - Thymeleaf và xử lý dữ liệu giữa view và controller
## MVC: Model - View - Controller
- Model: Đại diện cho dữ liệu. Model thường chứa thông tin về các đối tượng và thực hiện các thao tác liên quan đến dữ liệu như truy xuất cơ sở dữ liệu.
- View: Là phần hiển thị dữ liệu cho người dùng. View có nhiệm vụ nhận dữ liệu từ Model và hiển thị chúng theo cách thích hợp. Điều này bao gồm việc tạo giao diện người dùng và hiển thị dữ liệu trên đó.
- Controller: Là thành phần trung gian giữa Model và View. Controller nhận yêu cầu từ người dùng thông qua giao diện (View), sau đó xử lý yêu cầu, tương tác với Model để truy xuất hoặc cập nhật dữ liệu, và sau đó trả kết quả về cho View để hiển thị cho người dùng.

[](https://github.com/techmely/hoc-lap-trinh/assets/29374426/4f3a0d22-eebe-49bd-bc32-9df03fe8ae6b)

- Luồng hoạt động:
  - User gửi request từ client tới server.
  - Controller nhận request và xử lý dữ liệu sau đó đưa đến View
  - View nhận dữ liệu từ và hiển thị nó lên giao diện

## Thymeleaf
- Thymeleaf là một thư viện mã nguồn mở và được coi là một View Engine, được sử dụng để xử lý và tạo ra các trang HTML, XML, JavaScript, CSS, text, và raw content
- Ngoài ra thymeleaf được sử dụng chủ yếu để truyền và nhận dữ liệu giữa View Và Controller
- Thymeleaf sử dụng các biến để chỉ định dữ liệu sử dụng, thậm chí là câu lệnh if, vòng lặp
- Ngoài ra để xử lý dữ liệu, thymeleaf sử dụng các Standard Expression
- Cú pháp của thymeleaf sẽ là thuộc tính của 1 thẻ html và bắt đầu bằng ' th: ', theo sau là kiểu dữ liệu xử lý ' text ', cuối cùng là 1 Standard Expression để chỉ định dữ liệu ' ${...} '
  
```html
    <div th:text="${string}"></div>
```

## Xử lý dữ liệu giữa view và controller
- 1 số annotation sử dụng:
  - @Controller: sử dụng để xử lý các request từ client và trả về các đối tượng Model, View hoặc String để hiển thị dữ liệu trên trang web. TH trả về các String là tên các trang html
  - @RequestMapping và @GetMapping: sử dụng để ánh xạ một request tới một phương thức xử lý tương ứng trong controller. Hiểu đơn giản là đường dẫn (Url) của trang web
  - @RequestParam hoặc @ModelAttribute: gửi dữ liệu từ view đến controller qua form html
  
### Controller -> View
- Model: được sử dụng để lưu trữ thông tin dưới dạng key-value, dùng để cho thymeleaf lấy dữ liệu bằng Standard Expression
- Cú pháp:
```java
    Model model;
    model.addAttribute("string", "Hello world");
```

- Bên cạnh đó có thể sử dụng th:object và *(...) trong thymeleaf nếu muốn gửi 1 đối tượng thay vì 1 giá trị đơn thuần
- th:each được sử dụng như 1 vòng lặp for-each

### View -> Controller 
- Sử dụng form html để có thể gửi dữ liệu 

```html
    <form th:action="@{/about}" method="get">
        <input name="name" type="text">
        <input name="age" type="text">
        <input type="submit" value="submit">
    </form>
```

- @RequestParam để bắt các tham số tương ứng trong request được gửi đi
```java
    @RequestMapping("/about")
    public String about(@RequestParam("name") String name, @RequestParam("age") String age, Model model) {
        model.addAttribute("name", name);
        model.addAttribute("age", age);
        return "about";
    }
```

- Ngoài ra thường có thể gửi và nhận dữ liệu dưới dạng object Student với 2 thuộc tính name, age
- @ModelAttribute để bắt các đối tượng được truyền lên, nó sẽ tự tìm các giá trị tương ứng tên thuộc tính và gán vào
```java
    @RequestMapping(value = "/about", method = RequestMethod.POST)
    public String about(@ModelAttribute Student student, Model model) {
        model.addAttribute("student", student);
        return "test";
    }

```

- Trong quá trình xử lý giữ liệu, có thể sử dụng redirect để truyển hướng giữa các URL cụ thể 