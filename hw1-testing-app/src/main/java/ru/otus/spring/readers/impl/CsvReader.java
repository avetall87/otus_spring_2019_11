package ru.otus.spring.readers.impl;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ClassPathResource;
import ru.otus.spring.domain.Answer;
import ru.otus.spring.domain.Question;
import ru.otus.spring.readers.QuestionReader;

import java.io.File;
import java.util.Collections;
import java.util.List;

import static com.fasterxml.jackson.databind.DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT;
import static com.fasterxml.jackson.databind.DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES;

//@Slf4j
public class CsvReader implements QuestionReader {

    @Override
    public List<Question> read(String pathToCsv) {
        try {
            CsvMapper mapper = new CsvMapper();
            CsvSchema bootstrapSchema = mapper.schemaWithHeader().sortedBy("question", "answers");
            mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
            mapper.enable(CsvParser.Feature.SKIP_EMPTY_LINES);
            mapper.disable(CsvParser.Feature.FAIL_ON_MISSING_COLUMNS);
            File file = new ClassPathResource(pathToCsv).getFile();
            MappingIterator<Question> readValues = mapper.readerWithSchemaFor(Question.class).with(bootstrapSchema).readValues(file);
            return readValues.readAll();
        } catch (Exception e) {
            System.out.println("Error occurred while loading many to many relationship from file = " + pathToCsv + "\n" + e.getMessage());
            return Collections.emptyList();
        }
    }
}
