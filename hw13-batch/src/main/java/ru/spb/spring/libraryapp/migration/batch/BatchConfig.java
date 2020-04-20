package ru.spb.spring.libraryapp.migration.batch;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.spb.spring.libraryapp.domain.Author;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorProcessorHelper;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorReaderHelper;
import ru.spb.spring.libraryapp.migration.batch.author.AuthorWriterHelper;
import ru.spb.spring.libraryapp.migration.batch.book.BookProcessorHelper;
import ru.spb.spring.libraryapp.migration.batch.book.BookReaderHelper;
import ru.spb.spring.libraryapp.migration.batch.book.CustomBookCommentsWriter;
import ru.spb.spring.libraryapp.migration.batch.genre.GenreProcessorHelper;
import ru.spb.spring.libraryapp.migration.batch.genre.GenreReaderHelper;
import ru.spb.spring.libraryapp.migration.batch.genre.GenreWriterHelper;

@Slf4j
@Configuration
@EnableBatchProcessing
@RequiredArgsConstructor
public class BatchConfig {

    @Value("${batch.process.step.chunk.size:#{5}}")
    public Integer stepChunkSize;

    private final JobBuilderFactory jobBuilderFactory;

    private final StepBuilderFactory stepBuilderFactory;

    private final AuthorReaderHelper authorReaderHelper;

    private final AuthorProcessorHelper authorProcessorHelper;

    private final AuthorWriterHelper authorWriterHelper;

    private final BookReaderHelper bookReaderHelper;

    private final BookProcessorHelper bookProcessorHelper;

    private final CustomBookCommentsWriter customBookCommentsWriter;

    private final GenreProcessorHelper genreProcessorHelper;

    private final GenreReaderHelper genreReaderHelper;

    private final GenreWriterHelper genreWriterHelper;

    @Bean
    public Job migrationJob(@Qualifier("authorMigrationStep") Step authorMigration,
                            @Qualifier("bookMigrationStep") Step bookMigrationStep,
                            @Qualifier("genreMigrationStep") Step genreMigrationStep) {
        return jobBuilderFactory.get("migrationJob")
                .preventRestart()
                .start(authorMigration)
                .next(genreMigrationStep)
                .next(bookMigrationStep)
                .incrementer(new RunIdIncrementer())
                .listener(new JobExecutionListener() {
                    @Override
                    public void beforeJob(JobExecution jobExecution) {
                        log.info("Start job authorMigration");
                    }

                    @Override
                    public void afterJob(JobExecution jobExecution) {
                        log.info("End job authorMigration");
                    }
                })
                .build();
    }

    @Bean("authorMigrationStep")
    public Step authorMigrationStep() {
        return stepBuilderFactory.get("authorMigrationStep")
                .chunk(stepChunkSize)
                .reader(authorReaderHelper.reader())
                .listener(new ItemReadListener<Author>() {
                    @Override
                    public void beforeRead() {
                        System.out.println(1);
                    }

                    @Override
                    public void afterRead(Author item) {
                        System.out.println(item);
                    }

                    @Override
                    public void onReadError(Exception ex) {
                        System.out.println(ex.getMessage());
                    }
                })
                .processor(authorProcessorHelper.processor())
                .writer(authorWriterHelper.writerJdbc())
                .build();
    }

    @Bean("genreMigrationStep")
    public Step genreMigrationStep() {
        return stepBuilderFactory.get("genreMigrationStep")
                .chunk(stepChunkSize)
                .reader(genreReaderHelper.reader())
                .processor(genreProcessorHelper.processor())
                .writer(genreWriterHelper.writerJdbc())
                .build();
    }

    @Bean("bookMigrationStep")
    public Step bookMigrationStep() {
        return stepBuilderFactory.get("bookMigrationStep")
                .chunk(stepChunkSize)
                .reader(bookReaderHelper.reader())
                .processor(bookProcessorHelper.processor())
                .writer(customBookCommentsWriter)
                .build();
    }

}
