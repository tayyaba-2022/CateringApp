package com.example.CateringApp.Batch;

import com.example.CateringApp.entity.Catering;
import com.example.CateringApp.repository.CateringRepository;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.TaskletStep;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
public class CateringBatch {
    @Autowired
    private CateringRepository repository;
    @Bean
    @StepScope
    public FlatFileItemReader<Catering> reader(){
        return new FlatFileItemReaderBuilder().name("coffeeItemReader")
                .resource(new ClassPathResource("upload.csv"))
                .delimited()
                .names(new String[] {  "id", "customerName", "phoneNumber", "email","menu","noOfGuests","status" })
                .fieldSetMapper(new BeanWrapperFieldSetMapper() {{
                    setTargetType(Catering.class);
                }})
                .build();
    }


    @Bean
    public JdbcBatchItemWriter writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .sql("INSERT INTO catering (id, customer_name, phone_number, email,no_of_guests,menu,status) VALUES (:id, :customerName, :phoneNumber, :email,:noOfGuests,:menu,:status)")
                .dataSource(dataSource)
                .build();
    }

    @Bean
    public Job importUserJob(JobRepository jobRepository, JobCompletionNotificationListener listener, Step step1) {
        return new JobBuilder("importUserJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .listener(listener)
                .flow(step1)
                .end()
                .build();
    }
    @Bean
    public Step step1(JobRepository jobRepository, PlatformTransactionManager transactionManager, JdbcBatchItemWriter writer) {
        return new StepBuilder("step1", jobRepository)
                .<Catering, Catering> chunk(10, transactionManager)
                .reader(reader())
                .processor(processor())
                .writer(writer)
                .build();
    }

    @Bean
    public CateringProcessor processor() {
        return new CateringProcessor();
    }

}
