# Buổi 9: File upload/download handling
## 1.	File upload
- Sử dụng nền tảng cloudinary (hoặc lưu local) để lưu trữ file ảnh và video miễn phí
- Trong database chỉ lưu đường dẫn đến ảnh
- Tạo CloundinaryConfig để kết nối cloundinary với spring boot qua api_key, api_secret, clound_name

```java
@Configuration
public class CloudinaryConfig {
    @Bean
    Cloudinary configCloudinary() {
        Map<String, String> config = new HashMap<>();
        config.put("api_key", "here");
        config.put("api_secret", "here");
        config.put("cloud_name", "here");
        return new Cloudinary(config);
    }
}
```

- Mỗi tài khoản được cung cấp api_key, api_secret, clound_name, url riêng, sử dụng nó để setup cho chương trình

- Sau đó setup trong properties 
```
CLOUDINARY_URL=...

spring.servlet.multipart.enabled=true

spring.servlet.multipart.file-size-threshold=2KB

spring.servlet.multipart.max-file-size=200MB

spring.servlet.multipart.max-request-size=215MB
```

- Tạo utils với Cloundinary: chứa các phương thức để convert multipart file sang file, lấy url của file, và xóa file qua url
```java
@Component
@RequiredArgsConstructor
public class CloudinaryUtil {
    private final Cloudinary cloudinary;

    public File convertMultipartToFile(MultipartFile file) throws IOException {
        File convertFile = new File(Objects.requireNonNull(file.getOriginalFilename()));
        FileOutputStream outputStream = new FileOutputStream(convertFile);
        outputStream.write(file.getBytes());
        outputStream.close();
        return convertFile;
    }

    public String getUrlFromFile(MultipartFile multipartFile) throws Exception {
        try {
            Map<?, ?> mapFile = cloudinary.uploader().upload(multipartFile.getBytes(), ObjectUtils.emptyMap());
            //Todo : ObjectUtils.emptyMap : returns an empty 'java.util.Map' object
            //Todo : mapFile.get("secure_url") : is a key used in the metadata returned after uploading an image
            return mapFile.get("secure_url").toString();
        } catch (Exception exception) {
            throw new Exception("The process get url from file failed!");
        }
    }

    public String removeFileToUrl(String url) throws Exception {
        try {
            String publicId = url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf("."));
            cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
            return "Remove file to url is successfully";
        } catch (Exception exception) {
            throw new Exception("Upload image failed");
        }
    }
}
```

## 2. Spring validation
- Sử dụng để kiểm tra tính hợp lệ của thuộc tính dữ liệu được thêm vào bằng DTO
- Sử dụng các annotation giúp validate dễ dàng hơn
  - @NotBlank(message = “…”)
  - @NotNull(message = “…”)
  - @NotEmpty(message = “…”)
  - @Min, @Max, @Size(min = , max = ), @Length(same)
  - @Valid: sử dụng trong controller để kiểm tra tính hợp lệ của đối tượng được đánh dấu, dựa trên các ràng buộc được đánh dấu như trên nếu có
  - @Pattern(…) có thể tùy ý validate theo cách của bạn 
- Ngoài ra có thể custom validation trong spring boot

```java
public class AccountDTO {
    @NotNull(message = "ERROR, TRY AGAIN")
    @Pattern(regexp = "^[a-z][a-z0-9]{3,15}$", message = ErrorMessage.INVALID_FORMAT_USERNAME)
    private String username;

    @NotNull(message = "ERROR, TRY AGAIN")
    @Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)[a-zA-Z\\d]{6,}$", message = ErrorMessage.INVALID_FORMAT_PASSWORD)
    private String password;

    @NotNull(message = "ERROR, TRY AGAIN")
    private String repeatPassword;

    @NotNull(message = "ERROR, TRY AGAIN")
    @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$", message = ErrorMessage.INVALID_FORMAT_EMAIL)
    private String email;
}
```

## 3. Tự tạo 1 annotation
- Bạn có thể định nghĩa một Annotation bằng cách tạo một interface và đánh dấu nó bằng Annotation @interface
- @Retention(RetentionPolicy.RUNTIME) chỉ ra rằng Annotation này sẽ được giữ lại và có thể được truy cập trong thời gian chạy. 
- @Target(ElementType.TYPE) chỉ ra rằng Annotation này có thể được áp dụng cho các class.

- Ví dụ tạo annotation cho restful api 
```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@RestController
@RequestMapping("/api/v1")
public @interface RestApiV1 {
}
```
- Ví dụ tạo annotation lấy ra thông tin người đang đăng nhập    
```java
@Target({ElementType.PARAMETER, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@AuthenticationPrincipal
public @interface CurrentUser {
}
```