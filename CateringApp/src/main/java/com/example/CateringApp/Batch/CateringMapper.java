package com.example.CateringApp.Batch;

import com.example.CateringApp.entity.Catering;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.validation.BindException;

public class CateringMapper implements FieldSetMapper<Catering>{
    @Override
    public Catering mapFieldSet(FieldSet fieldSet) throws BindException {
        return null;
    }
}
