package com.kennethrdzg.smalltalk.config;

import java.util.Collections;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemReader;
import org.springframework.batch.item.file.FlatFileItemWriter;
import org.springframework.batch.item.file.transform.DelimitedLineAggregator;
import org.springframework.batch.item.file.transform.LineAggregator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.core.task.TaskExecutor;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.PlatformTransactionManager;

import com.kennethrdzg.smalltalk.entities.Post;
import com.kennethrdzg.smalltalk.entities.PostLike;
import com.kennethrdzg.smalltalk.entities.User;
import com.kennethrdzg.smalltalk.repository.PostLikeRepository;
import com.kennethrdzg.smalltalk.repository.PostRepository;
import com.kennethrdzg.smalltalk.repository.UserRepository;

@Configuration
public class BatchConfig{
    @Autowired
    public UserRepository userRepository;

    @Autowired
    public PostRepository postRepository;

    @Autowired
    public PostLikeRepository postLikeRepository;

    @Bean
    public TaskExecutor taskExecutor(){
        SimpleAsyncTaskExecutor asyncTaskExecutor = new SimpleAsyncTaskExecutor();
        asyncTaskExecutor.setConcurrencyLimit(100);
        return asyncTaskExecutor;
    }

    @Bean
    public ItemReader<User> userReader(){
        RepositoryItemReader<User> reader = new RepositoryItemReader<User>();
        reader.setRepository(userRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("id", Sort.Direction.DESC));
        return reader;
    }

    @Bean
    public ItemReader<Post> postReader(){
        RepositoryItemReader<Post> reader = new RepositoryItemReader<Post>();
        reader.setRepository(postRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("id", Sort.Direction.DESC));
        return reader;
    }

    @Bean
    public ItemReader<PostLike> postLikeReader(){
        RepositoryItemReader<PostLike> reader = new RepositoryItemReader<PostLike>();
        reader.setRepository(postLikeRepository);
        reader.setMethodName("findAll");
        reader.setSort(Collections.singletonMap("id", Sort.Direction.DESC));
        return reader;
    }

    @Bean
    public LineAggregator<User> userLineAggregator(){
        DelimitedLineAggregator<User> lineAggregator = new DelimitedLineAggregator<User>();
        lineAggregator.setDelimiter(",");
        return lineAggregator;
    }

    @Bean
    public LineAggregator<Post> postLineAggregator(){
        DelimitedLineAggregator<Post> lineAggregator = new DelimitedLineAggregator<Post>();
        lineAggregator.setDelimiter(",");
        return lineAggregator;
    }

    @Bean
    public LineAggregator<PostLike> postLikeLineAggregator(){
        DelimitedLineAggregator<PostLike> lineAggregator = new DelimitedLineAggregator<PostLike>();
        lineAggregator.setDelimiter(",");
        return lineAggregator;
    }

    @Bean
    public ItemWriter<User> userWriter(){
        FlatFileItemWriter<User> writer = new FlatFileItemWriter<User>();
        writer.setName("userWriter");
        writer.setResource(new FileSystemResource("src/main/resources/backup/user_data_backup.csv"));
        writer.setLineAggregator(userLineAggregator());
        return writer;
    }

    @Bean
    public ItemWriter<Post> postWriter(){
        FlatFileItemWriter<Post> writer = new FlatFileItemWriter<Post>();
        writer.setName("postWriter");
        writer.setResource(new FileSystemResource("src/main/resources/backup/post_data_backup.csv"));
        writer.setLineAggregator(postLineAggregator());
        return writer;
    }

    @Bean
    public ItemWriter<PostLike> postLikeWriter(){
        FlatFileItemWriter<PostLike> writer = new FlatFileItemWriter<PostLike>();
        writer.setName("postLikeWriter");
        writer.setResource(new FileSystemResource("src/main/resources/backup/post_like_data_backup.csv"));
        writer.setLineAggregator(postLikeLineAggregator());
        return writer;
    }

    @Bean
    public Step userBackupStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("user-backup", jobRepository).<User, User>chunk(100, transactionManager)
            .reader(userReader())
            .writer(userWriter())
            .taskExecutor(taskExecutor())
            .build();
    }

    @Bean Step postBackupStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("post-backup", jobRepository).<Post, Post>chunk(100, transactionManager)
            .reader(postReader())
            .writer(postWriter())
            .taskExecutor(taskExecutor())
            .build();
    }

    @Bean Step postLikeBackupStep(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new StepBuilder("post-like-backup", jobRepository).<PostLike, PostLike>chunk(100, transactionManager)
            .reader(postLikeReader())
            .writer(postLikeWriter())
            .taskExecutor(taskExecutor())
            .build();
    }

    @Bean
    public Job backupJob(JobRepository jobRepository, PlatformTransactionManager transactionManager){
        return new JobBuilder("backup-job", jobRepository)
        .incrementer(new RunIdIncrementer())
            .flow(userBackupStep(jobRepository, transactionManager))
            .next(postBackupStep(jobRepository, transactionManager))
            .next(postLikeBackupStep(jobRepository, transactionManager))
            .end().build();
    }
}
