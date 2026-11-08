package com.example.anken_manager.config;

import com.example.anken_manager.entity.Anken;
import com.example.anken_manager.entity.AppUser;
import com.example.anken_manager.entity.Customer;
import com.example.anken_manager.repository.AnkenRepository;
import com.example.anken_manager.repository.CustomerRepository;
import com.example.anken_manager.repository.UserRepository;
import java.time.LocalDate;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    public CommandLineRunner initData(AnkenRepository ankenRepository,
                                      CustomerRepository customerRepository,
                                      UserRepository userRepository,
                                      PasswordEncoder passwordEncoder) {
        return args -> {
            // テスト用ユーザー(パスワードはハッシュ化して保存する)
            AppUser admin = new AppUser();
            admin.setUsername("admin");
            admin.setPassword(passwordEncoder.encode("password"));
            userRepository.save(admin);

            // 顧客を先に登録する
            Customer c1 = new Customer();
            c1.setName("株式会社サンプル");
            customerRepository.save(c1);

            Customer c2 = new Customer();
            c2.setName("テスト商事");
            customerRepository.save(c2);

            Customer c3 = new Customer();
            c3.setName("例示工業");
            customerRepository.save(c3);

            // 案件は、登録済みの顧客を紐づける
            Anken a1 = new Anken();
            a1.setName("基幹システム刷新");
            a1.setCustomer(c1);
            a1.setStatus("商談中");
            a1.setCreatedDate(LocalDate.now());
            ankenRepository.save(a1);

            Anken a2 = new Anken();
            a2.setName("営業支援ツール導入");
            a2.setCustomer(c2);
            a2.setStatus("受注");
            a2.setCreatedDate(LocalDate.now());
            ankenRepository.save(a2);

            Anken a3 = new Anken();
            a3.setName("勤怠管理システム");
            a3.setCustomer(c3);
            a3.setStatus("失注");
            a3.setCreatedDate(LocalDate.now());
            ankenRepository.save(a3);
        };
    }
}