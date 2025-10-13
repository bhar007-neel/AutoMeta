package com.AutoMeta.Service;

import com.AutoMeta.Model.SearchVideo;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
@RequiredArgsConstructor
public class YoutubeService {
    // we will be using web client dependency


    private final WebClient.Builder webClientBuilder;

    @Value("${youtube.api.key}")
    private String apiKey;

    @Value("${youtube.api.base.url}")
    private String baseUrl;

    @Value("${youtube.api.max.related.videos}")
    private int maxRelatedVideos;

    public SearchVideo searchVideos(String videoTitle){
        List<String> videoIds = searchForVideosIds(videoTitle);

        return null;

    }

    private List<String> searchForVideosIds(String videoTitle) {
        SearchApiResponse response = webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet")
                        .queryParam("q", videoTitle)
                        .queryParam("type", "video")
                        .queryParam("maxResult", maxRelatedVideos)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(SearchApiResponse.class)
                .block();

    }
    @Data
    static  class SearchApiResponse{
        List<SearchItem> items;
    }

    @Data
    static class SearchItem{
        Id id;
    }

    @Data
    static class Id{
        String VideoId;
    }

    @Data
    static class VideoApiResponse{
        List<VideoItem> items;
    }

    @Data
    static class Snippet{
        String title;
        String description;
        String channelTitle;
        String publishedAt;
        List<String> tags;
        Thumbnails thumbnails;

    }

    @Data
    static class Thumbnails {
        Thumbnail maxres;
        Thumbnail high;
        Thumbnail medium;
        Thumbnail _default;

        String getBestThumbnailUrl() {
            if (maxres != null) return maxres.url;
            if (high != null) return high.url;
            if (medium != null) return medium.url;
            return _default != null ? _default.url : "";
        }
    }
















//DTO (Data Transfer Object): A simple object used to move data between layers or services. Holds fields + getters/setters; no business logic, no DB annotations.
//
//POJO (Plain Old Java Object): Any ordinary Java class not tied to a framework. Often used as DTOs or models.
//
//Entity (JPA Entity): Class mapped to a database table via JPA/Hibernate annotations. Represents persisted state.
//
//Repository / DAO: Layer that talks to the database. Repositories (e.g., JpaRepository) provide CRUD; DAOs are the manual equivalent.
//
//Service: Contains business logic and coordinates repositories, mappers, and external calls. No HTTP or DB mapping here.
//
//Controller (Web layer): Handles HTTP requests/responses, validates input, calls services, returns DTOs.
//
//VO (Value Object): Immutable object defined by its values (e.g., Money, Email). Equality by content, not identity.
//
//BO (Business Object) / Domain Model: Rich object that models business rules/behaviors; may aggregate entities/VOs.
//
//Mapper (Assembler): Converts between Entity ↔ DTO (e.g., MapStruct or manual mapping).
//
//Model vs Entity vs DTO (quick contrast):
//
//Entity: DB-mapped, persistence concerns.
//
//DTO: Transport shape for APIs/UI; no logic.
//
//Domain Model/BO: Encapsulates business rules and behavior.
//
//Why DTOs? Decouple API from database shape, minimize over-fetching/leaking fields, version easily, reduce serialization payloads.
