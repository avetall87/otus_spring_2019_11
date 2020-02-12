package ru.spb.otus.libraryapp.changelog;

import com.github.cloudyrock.mongock.ChangeLog;
import com.github.cloudyrock.mongock.ChangeSet;
import org.springframework.data.mongodb.core.MongoTemplate;
import ru.spb.otus.libraryapp.domain.Author;

@ChangeLog
public class DatabaseChangelog {

  @ChangeSet(order = "001", id = "someChangeWithSpringDataTemplate", author = "testAuthor")
  public void someChange1(MongoTemplate mongoTemplate) {
    mongoTemplate.save(Author.builder().firstName("first name").lastName("last name").patronymic("patronymic").build());
  }

}
