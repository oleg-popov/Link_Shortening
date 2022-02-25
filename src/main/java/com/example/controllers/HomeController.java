package com.example.controllers;

import com.example.CodeGenerator;
import com.example.dao.ShorterDao;
import com.example.entity.Shorter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class HomeController {
    private final ShorterDao repository;
    private final CodeGenerator codeGenerator;
    @Value("${shorter.length}")
    private Integer shorterLength ;

    @Autowired
    public HomeController(ShorterDao repository) {
        this.repository = repository;
        this.codeGenerator = new CodeGenerator();
    }

    @PostMapping
    public String createShortUrl(Shorter shorter , RedirectAttributes attributes) {
        System.out.println(shorter.getOriginalUrl());
        if (shorter != null) {
            String hash = codeGenerator.generate(shorterLength);
            shorter.setHash(hash);
            repository.save(shorter);
            attributes.addFlashAttribute("shorter" , shorter);
            System.out.println(shorter.getHash());
        }
        return "redirect:/";
    }

    @ResponseBody
    @GetMapping("/{hash}")
    public ResponseEntity redirectShorter(@PathVariable("hash") String hash) {
        //TODO find hash in DB and redirect to original URL
        Shorter shorter = repository.findByHash(hash);
        if (shorter != null) {
            HttpHeaders headers = new HttpHeaders();
            headers.add("Location", shorter.getOriginalUrl());
            return new ResponseEntity<String>(headers, HttpStatus.FOUND);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/")
    String index(){
        return "index";
    }
}
