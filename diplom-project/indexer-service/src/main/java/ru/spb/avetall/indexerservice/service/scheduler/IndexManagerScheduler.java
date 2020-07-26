package ru.spb.avetall.indexerservice.service.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ru.spb.avetall.indexerservice.domain.LinkIndex;
import ru.spb.avetall.indexerservice.service.IndexDataService;

import java.time.LocalDateTime;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotEmpty;
import static ru.spb.avetall.indexerservice.utils.DateTimeUtils.toLocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class IndexManagerScheduler {

    private static final int ROWS_COUNT = 50;
    private final IndexDataService indexDataService;

    @Scheduled(fixedDelay = 100000)
    public void removeNotTodayIndexData() {
        try {
            LocalDateTime today = LocalDateTime.now();

            int page = 0;
            List<LinkIndex> linkIndices;
            while ((linkIndices = indexDataService.findAllPage(page, ROWS_COUNT)).size() > 0){

                linkIndices.stream()
                        .filter(linkIndex -> isNotEmpty(linkIndex.getCreationDate()))
                        .filter(linkIndex -> toLocalDateTime(linkIndex.getCreationDate()).toLocalDate().isBefore(today.toLocalDate()))
                        .forEach(linkIndex -> indexDataService.deleteById(linkIndex.getId()));

                page ++;
            }

            log.debug("RemoveOldIndexData was processed for creation date={}", today);
        } catch (Exception ex) {
            log.error("RemoveOldIndexData exception", ex);
        }
    }

}
