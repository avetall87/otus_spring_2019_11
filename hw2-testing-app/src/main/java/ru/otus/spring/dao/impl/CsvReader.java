package ru.otus.spring.dao.impl;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.dao.QuestionReader;
import ru.otus.spring.dao.dto.QuestionDto;
import ru.otus.spring.dao.mappers.QuestionMapper;
import ru.otus.spring.domain.Question;

import java.io.File;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
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
                    .filter(Objects::nonNull)
                    .map(QuestionMapper::map)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            log.error("Something else is wrong here", e);
            return Collections.emptyList();
        }
    }
}
