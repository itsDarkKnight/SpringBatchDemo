package com.SpringBatch.example;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfig {

    @Bean
    public Job jobBean(JobRepository jobRepository, JobCompletionImpl listener, Step steps)
    {
        return new JobBuilder("job",jobRepository)
                .listener(listener)
                .start(steps)
                .build();
    }
    @Bean
    public Step steps(JobRepository jobRepository,
                      DataSourceTransactionManager transactionManager, ItemReader<Complain> reader
    ,ItemProcessor<Complain,Complain> itemProcessor,
                      ItemWriter<Complain> itemWriter)
    {
        return new StepBuilder("jobStep",jobRepository)
                .<Complain,Complain>chunk(5,transactionManager)
                .reader(reader)
                .processor(itemProcessor)
                .writer(itemWriter)
                .build();
    }

    @Bean
    public FlatFileItemReader<Complain> reader()
    {
        return  new FlatFileItemReaderBuilder<Complain>()
                .name("itereader")
                .resource(new ClassPathResource("data1.csv"))
                .delimited()
                .names("ID","Name","product","Company","State","Submit_via")
                .targetType(Complain.class)
                .build();
    }

    @Bean
    public ItemWriter<Complain> itemWriter(DataSource dataSource)
    {
        return new JdbcBatchItemWriterBuilder<Complain>()
                .sql("insert into Complain(ID,Name,product,Company,State,Submit_via) values (:ID,:Name,:product,:Company,:State,:Submit_via)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }

    @Bean
    public ItemProcessor<Complain,Complain>itemProcessor()
    {
        return new CustomItemProcessor();
    }
}
