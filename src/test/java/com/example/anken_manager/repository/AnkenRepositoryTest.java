package com.example.anken_manager.repository;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.anken_manager.entity.Anken;
import com.example.anken_manager.entity.Customer;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.data.jpa.test.autoconfigure.DataJpaTest;

@DataJpaTest
class AnkenRepositoryTest {

    @Autowired
    private AnkenRepository ankenRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    void 案件を保存して取得できる() {
        Customer customer = new Customer();
        customer.setName("テスト顧客");
        customerRepository.save(customer);

        Anken anken = new Anken();
        anken.setName("テスト案件");
        anken.setCustomer(customer);
        anken.setStatus("商談中");
        anken.setCreatedDate(LocalDate.now());
        Anken saved = ankenRepository.save(anken);

        Anken found = ankenRepository.findById(saved.getId()).orElseThrow();

        assertThat(found.getName()).isEqualTo("テスト案件");
        assertThat(found.getCustomer().getName()).isEqualTo("テスト顧客");
    }
}