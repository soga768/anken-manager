package com.example.anken_manager.controller;

import com.example.anken_manager.entity.Anken;
import com.example.anken_manager.entity.Customer;
import com.example.anken_manager.repository.AnkenRepository;
import com.example.anken_manager.repository.CustomerRepository;
import java.time.LocalDate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AnkenController {

    private final AnkenRepository ankenRepository;
    private final CustomerRepository customerRepository;

    public AnkenController(AnkenRepository ankenRepository,
                           CustomerRepository customerRepository) {
        this.ankenRepository = ankenRepository;
        this.customerRepository = customerRepository;
    }

    @GetMapping("/ankens")
    public String list(Model model) {
        model.addAttribute("ankens", ankenRepository.findAll());
        return "anken/list";
    }

    @GetMapping("/ankens/new")
    public String newForm(Model model) {
        Anken anken = new Anken();
        anken.setCustomer(new Customer());
        model.addAttribute("anken", anken);
        model.addAttribute("customers", customerRepository.findAll());
        return "anken/form";
    }

    @PostMapping("/ankens")
    public String create(@ModelAttribute Anken anken) {
        Customer customer = customerRepository.findById(anken.getCustomer().getId()).orElseThrow();
        anken.setCustomer(customer);
        anken.setCreatedDate(LocalDate.now());
        ankenRepository.save(anken);
        return "redirect:/ankens";
    }

    @GetMapping("/ankens/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Anken anken = ankenRepository.findById(id).orElseThrow();
        model.addAttribute("anken", anken);
        model.addAttribute("customers", customerRepository.findAll());
        return "anken/edit";
    }

    @PostMapping("/ankens/{id}/update")
    public String update(@PathVariable Long id, @ModelAttribute Anken anken) {
        Anken target = ankenRepository.findById(id).orElseThrow();
        Customer customer = customerRepository.findById(anken.getCustomer().getId()).orElseThrow();
        target.setName(anken.getName());
        target.setCustomer(customer);
        target.setStatus(anken.getStatus());
        ankenRepository.save(target);
        return "redirect:/ankens";
    }

    @PostMapping("/ankens/{id}/delete")
    public String delete(@PathVariable Long id) {
        ankenRepository.deleteById(id);
        return "redirect:/ankens";
    }
}