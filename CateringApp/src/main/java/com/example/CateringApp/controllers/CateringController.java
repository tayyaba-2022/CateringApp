package com.example.CateringApp.controllers;
import com.example.CateringApp.entity.Alien;
import com.example.CateringApp.entity.Catering;
import com.example.CateringApp.entity.Status;
import com.example.CateringApp.repository.CateringRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("catering")
public class CateringController {
    @Autowired
    private CateringRepository repo;
    WebClient client=WebClient.create("http://localhost:8080");
    @GetMapping("all")
    public List<Catering> getAll(){
        return repo.findAll();
    }

    @GetMapping("{id}")
    public Catering getById(@PathVariable int id){
       Optional<Catering> catering= repo.findById(id);
        if(catering.isPresent()){
            return catering.get();
        }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("create")
    public Catering create(@RequestBody Catering catering){
        return repo.save(catering);
    }

    @GetMapping("/status/{status}")
    public List<Catering> getByStatus(@PathVariable Status status){
        return repo.findByStatus(status);
    }

    @PutMapping("update/{id}")
    public Catering update(@RequestBody Catering catering,@PathVariable int id){
       if(repo.existsById(id)){
           catering.setId(id);
           return repo.save(catering);
       }else{
            throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
       }
    }

    @ExceptionHandler(HttpClientErrorException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    String handleClientError(){
        return "NOT FOUND: Please try with another ID";
    }

    @GetMapping("/client")
    public String getWebClientDetails(){
        Mono<Alien> alienMono = client.get()
                .uri("/crud/aliens/{id}", "1")
                .retrieve()
                .bodyToMono(Alien.class);
        alienMono.subscribe(System.out::println);
        return String.valueOf(alienMono.log());
    }
}
