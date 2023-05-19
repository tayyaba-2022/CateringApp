package com.example.CateringApp.EndPoints;

import com.example.CateringApp.entity.Catering;
import com.example.CateringApp.entity.Status;
import com.example.CateringApp.repository.CateringRepository;
import org.springframework.boot.actuate.endpoint.annotation.Endpoint;
import org.springframework.boot.actuate.endpoint.annotation.ReadOperation;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.stream.Collectors;

@Component
@Endpoint(id="catering-status")
public class CateringEndPoint {
    private final CateringRepository repo;

    public CateringEndPoint(CateringRepository repo){
        this.repo=repo;
    }
    @ReadOperation
    Map<Status, Long> getCateringByStatus(){
        return repo.findAll()
                .stream()
                .collect(Collectors.groupingBy(Catering::getStatus,Collectors.counting()));
    }
}
