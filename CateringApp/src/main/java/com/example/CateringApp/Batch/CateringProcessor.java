package com.example.CateringApp.Batch;

import com.example.CateringApp.entity.Catering;
import com.example.CateringApp.entity.Status;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class CateringProcessor implements ItemProcessor<Catering, Catering> {

    private static final Logger LOGGER = LoggerFactory.getLogger(CateringProcessor.class);

    @Override
    public Catering process(final Catering catering) throws Exception {
        int id = catering.getId();
        String customerName = catering.getCustomerName().toUpperCase();
        String phoneNumber = catering.getPhoneNumber();
        String email = catering.getEmail();
        String menu=catering.getMenu();
        int noOfGuests=catering.getNoOfGuests();
        Status status= catering.getStatus();

        Catering cat = new Catering(id, customerName, phoneNumber,email,menu,noOfGuests,status);
        LOGGER.info("Converting ( {} ) into ( {} )", catering, cat);

        return cat;
    }
}
