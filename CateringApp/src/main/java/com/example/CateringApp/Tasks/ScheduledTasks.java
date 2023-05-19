package com.example.CateringApp.Tasks;

import com.example.CateringApp.entity.Catering;
import com.example.CateringApp.entity.Status;
import com.example.CateringApp.repository.CateringRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class ScheduledTasks {
    @Autowired
    private CateringRepository repo;

    private static final Logger logger= LoggerFactory.getLogger(ScheduledTasks.class);

    @Scheduled(cron = "0 51 12 * * 1")
    public void reportStatus(){
        List<Catering> list = repo.findAll();
        Map<Status, List<Catering>> map=list.stream().collect(Collectors.groupingBy(Catering::getStatus));
        logger.info("Statuses Currently"+map);
    }
}
