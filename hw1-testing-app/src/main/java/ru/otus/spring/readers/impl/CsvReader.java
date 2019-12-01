package ru.otus.spring.readers.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Question;
import ru.otus.spring.dto.QuestionDto;
import ru.otus.spring.mappers.QuestionMapper;
import ru.otus.spring.readers.QuestionReader;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CsvReader implements QuestionReader {

    @Override
    public List<Question> read(String pathToCsv) {
        try {
            CsvSchema bootstrapSchema = CsvSchema.builder()
                    .addColumn("question", CsvSchema.ColumnType.STRING)
                    .addArrayColumn("answers", "|")
                    .addColumn("correctAnswer")
                    .build();

            CsvMapper mapper = new CsvMapper();
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
            mapper.enable(CsvParser.Feature.TRIM_SPACES);
            mapper.disable(CsvParser.Feature.FAIL_ON_MISSING_COLUMNS);
            mapper.enable(JsonGenerator.Feature.IGNORE_UNKNOWN);

            File file = new ClassPathResource(pathToCsv).getFile();
            MappingIterator<QuestionDto> readValues = mapper.readerWithSchemaFor(QuestionDto.class).with(bootstrapSchema).readValues(file);

            return readValues.readAll()
                    .stream()
                    .map(QuestionMapper::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            System.out.println("Error occurred while loading many to many relationship from file = " + pathToCsv + "\n" + e.getMessage());
            return Collections.emptyList();
        }
    }
}
