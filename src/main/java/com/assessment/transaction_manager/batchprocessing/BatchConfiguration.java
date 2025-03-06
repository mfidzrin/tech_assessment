package com.assessment.transaction_manager.batchprocessing;

import com.assessment.transaction_manager.model.TransactionOrder;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

@Configuration
public class BatchConfiguration {

    @Bean
    public DataSourceTransactionManager transactionManager(DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean
    public FlatFileItemReader<TransactionOrder> reader() {
        DelimitedLineTokenizer tokenizer = new DelimitedLineTokenizer();
        tokenizer.setDelimiter("|");
        tokenizer.setNames("accountNo", "transactionAmount", "description", "transactionDate", "transactionTime", "customerId");

        return new FlatFileItemReaderBuilder<TransactionOrder>()
                .name("transactionOrderItemReader")
                .resource(new ClassPathResource("dataSource.txt"))
                .lineTokenizer(tokenizer)
                .targetType(TransactionOrder.class)
                .linesToSkip(1)
                .recordSeparatorPolicy(new NewPolicy())
                .strict(false)
                .build();
    }

    @Bean
    public TransactionItemProcessor processor() {
        return new TransactionItemProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<TransactionOrder> writer(DataSource dataSource) {
        JdbcBatchItemWriter<TransactionOrder> writer = new JdbcBatchItemWriter<>();
        writer.setItemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>());
        writer.setSql("INSERT INTO transaction_order (account_no, transaction_amount, description, transaction_date, transaction_time, customer_id) VALUES (:accountNo, :transactionAmount, :description, :transactionDate, :transactionTime, :customerId)");
        writer.setDataSource(dataSource);
        return writer;
    }

    @Bean
    public Job importTransactionJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return new JobBuilder("importTransactionJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                                    FlatFileItemReader<TransactionOrder> reader, TransactionItemProcessor processor,
                                    JdbcBatchItemWriter<TransactionOrder> writer) throws Exception {
        return new StepBuilder("step1", jobRepository)
                .<TransactionOrder, TransactionOrder>chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }
}
