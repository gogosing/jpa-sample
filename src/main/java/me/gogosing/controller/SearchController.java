package me.gogosing.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import me.gogosing.service.SearchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * 통합 검색 Controller.
 */
@Slf4j
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    /**
     * 통합 검색.
     * @param title 검색어.
     * @return 검색 결과.
     */
    @GetMapping("/search")
    public Map<String, Object> getSearchResult(@RequestParam(value = "title") String title) {
        return Map.of(
                "albums", searchService.getAlbumList(title),
                "songs", searchService.getSongList(title)
        );
    }
}
