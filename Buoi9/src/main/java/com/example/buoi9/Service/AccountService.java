package com.example.buoi9.Service;

import com.example.buoi9.dto.AccountDto;
import com.example.buoi9.entity.Account;
import com.example.buoi9.repository.AccountRepository;
import com.example.buoi9.utils.CloudinaryUtil;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AccountService {
    private static final Path CURRENT_FOLDER = Paths.get(System.getProperty("user.dir"));
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final CloudinaryUtil cloudinaryUtil;

    public List<Account> getAll() {
        return accountRepository.findAll();
    }

    public Account createUsingLocalDisk(AccountDto accountDTO) throws IOException {
        Path staticPath = Paths.get("static");
        Path imagePath = Paths.get("images");
        if (!Files.exists(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath))) {
            Files.createDirectories(CURRENT_FOLDER.resolve(staticPath).resolve(imagePath));
        }
        Path file = CURRENT_FOLDER.resolve(staticPath)
                .resolve(imagePath).resolve(Objects.requireNonNull(accountDTO.getAvatar().
                        getOriginalFilename()));
        try (OutputStream os = Files.newOutputStream(file)) {
            os.write(accountDTO.getAvatar().getBytes());
        }
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(accountDTO.getPassword());
        account.setAvatar(imagePath.resolve(accountDTO.getAvatar().getOriginalFilename()).toString());
        return accountRepository.save(account);
    }

    public Account createUsingCloudinary(AccountDto accountDTO) {
        Account account = new Account();
        account.setUsername(accountDTO.getUsername());
        account.setPassword(account.getPassword());
        try {
            String url = cloudinaryUtil.getUrlFromFile(accountDTO.getAvatar());
            account.setAvatar(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return accountRepository.save(account);
    }
}
