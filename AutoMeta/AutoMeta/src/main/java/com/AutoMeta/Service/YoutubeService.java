package com.AutoMeta.Service;

import com.AutoMeta.Model.SearchVideo;
import com.AutoMeta.Model.Video;
import com.AutoMeta.Model.VideoDetails;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * YoutubeService
 *
 * This service is responsible for interacting with the YouTube Data API
 * to search for videos based on a given title or keyword.
 * It uses Spring's WebClient for non-blocking HTTP calls.
 *
 * Responsibilities:
 *  - Search videos on YouTube using title/keywords
 *  - Retrieve video IDs from the search response
 *  - Prepare data models for further processing (like thumbnails, metadata, etc.)
 *
 * Author: Neelmani Bhardwaj
 * Date: October 2025
 */
@Service
@RequiredArgsConstructor
public class YoutubeService {

    /**
     * WebClient builder injected by Spring.
     * Used to perform asynchronous API calls to the YouTube Data API.
     */
    private final WebClient.Builder webClientBuilder;

    /**
     * API key for authenticating YouTube Data API requests.
     * Value is injected from application.properties or environment variable.
     */
    @Value("${youtube.api.key}")
    private String apiKey;

    /**
     * Base URL for the YouTube Data API.
     * Example: https://www.googleapis.com/youtube/v3
     */
    @Value("${youtube.api.base.url}")
    private String baseUrl;

    /**
     * The maximum number of related videos to fetch for each search.
     * This helps control API usage and limit the response size.
     */
    @Value("${youtube.api.max.related.videos}")
    private int maxRelatedVideos;

    /**
     * Searches for videos on YouTube based on the provided video title.
     *
     * Steps:
     *  1. Calls the helper method to retrieve video IDs from YouTube.
     *  2. (Future implementation) Fetches detailed information about those videos.
     *
     * @param videoTitle The search keyword or title to look up on YouTube.
     * @return A SearchVideo object containing results (to be implemented).
     */
    public SearchVideo searchVideos(String videoTitle) {
        List<String> videoIds = searchForVideosIds(videoTitle);

        // TODO: Use videoIds to fetch full video details (title, description, etc.)
       if(videoIds.isEmpty()){
           return SearchVideo.builder()
                   .primaryVideo(null)
                   .relatedVideos(Collections.emptyList())
                   .build();
       }
       String primaryVideoId = videoIds.get(0);
       List<String> relatedVideoIds =videoIds.subList(1,Math.min(videoIds.size(), maxRelatedVideos));
       Video primaryVideo = getVideoById(primaryVideoId);
       List<Video> relatedVideos = new ArrayList<>();
       for(String id: relatedVideoIds){
           Video video = getVideoById(id);
           if(video != null){
               relatedVideos.add(video);
           }
       }
       return SearchVideo.builder()
               .primaryVideo(primaryVideo)
               .relatedVideos(relatedVideos)
               .build();
    }

    /**
     * Helper method that makes a request to the YouTube Search API
     * and retrieves the list of video IDs matching the given title.
     *
     * @param videoTitle The search query (e.g., "Spring Boot Tutorial").
     * @return List of YouTube video IDs matching the search query.
     */
    private List<String> searchForVideosIds(String videoTitle) {

        // Build a WebClient and call the /search endpoint of the YouTube API
        SearchApiResponse response = webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/search")
                        .queryParam("part", "snippet") // We need snippet for title, description, etc.
                        .queryParam("q", videoTitle) // The user query
                        .queryParam("type", "video") // Restrict results to videos only
                        .queryParam("maxResults", maxRelatedVideos) // Limit number of returned videos
                        .queryParam("key", apiKey) // API key for authentication
                        .build())
                .retrieve()
                .bodyToMono(SearchApiResponse.class)
                .block(); // Block the response and actually returns the object, async mono

        if(response ==null || response.items == null){
            return Collections.emptyList();
        }

        List<String> videoIds = new ArrayList<>();

        for(SearchItem item : response.items){
            videoIds.add(item.id.videoId);
        }
        return videoIds;
        // TODO: Extract video IDs from the response (currently not returned)

    }
    public VideoDetails getVideoDetails(String videoId) {
        // Call YouTube Data API to get video details
        VideoApiResponse response = webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                        .path("/videos")
                        .queryParam("part", "snippet")
                        .queryParam("id", videoId)
                        .queryParam("key", apiKey)
                        .build())
                .retrieve()
                .bodyToMono(VideoApiResponse.class)
                .block();

        // Handle missing or empty response
        if (response == null || response.items == null || response.items.isEmpty()) {
            System.out.println("No video data found for ID: " + videoId);
            return null;
        }

        // Extract snippet (title, description, etc.)
        Snippet snippet = response.items.get(0).snippet;

        // Determine thumbnail URL safely
        String thumbnailUrl = null;
        if (snippet.thumbnails != null) {
            if (snippet.thumbnails.high != null && snippet.thumbnails.high.url != null)
                thumbnailUrl = snippet.thumbnails.high.url;
            else if (snippet.thumbnails.medium != null && snippet.thumbnails.medium.url != null)
                thumbnailUrl = snippet.thumbnails.medium.url;
            else if (snippet.thumbnails._default != null && snippet.thumbnails._default.url != null)
                thumbnailUrl = snippet.thumbnails._default.url;
        }

        // Fallback to YouTube static thumbnail if API didn’t provide one
        if (thumbnailUrl == null || thumbnailUrl.isEmpty()) {
            thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/hqdefault.jpg";
        }

        System.out.println("Fetched thumbnail URL for " + videoId + ": " + thumbnailUrl);

        // Build and return the VideoDetails object
        return VideoDetails.builder()
                .id(videoId)
                .title(snippet.title)
                .description(snippet.description)
                .tags(snippet.tags == null ? Collections.emptyList() : snippet.tags)
                .thumbnailUrl(thumbnailUrl)
                .channelTitle(snippet.channelTitle)
                .publishedAt(snippet.publishedAt)
                .build();
    }







    public Video getVideoById(String videoId){
        VideoApiResponse response = webClientBuilder.baseUrl(baseUrl).build()
                .get()
                .uri(uriBuilder -> uriBuilder
                       .path("/videos")
                        .queryParam("part", "snippet")
                        .queryParam("id",videoId)
                        .queryParam("key",apiKey)
                        .build())
                .retrieve()
                .bodyToMono(VideoApiResponse.class)
                .block();

        if(response == null || response.items == null){
            return null;
        }
        Snippet snippet = response.items.get(0).snippet;
        return Video.builder()
                .id(videoId)
                .channelTitle(snippet.channelTitle)
                .title(snippet.title)
                .tags(snippet.tags ==null ? Collections.emptyList() : snippet.tags)
                .build();
    }

    // -----------------------------
    // Below are inner static classes
    // representing the structure of the YouTube API JSON response.
    // -----------------------------

    /**
     * Represents the overall structure of the search API response.
     * Contains a list of SearchItem objects (each item = one search result).
     */
    @Data
    static class SearchApiResponse {
        List<SearchItem> items;
    }

    /**
     * Represents each individual search result item in the API response.
     * Contains an ID object that holds the video ID.
     */
    @Data
    static class SearchItem {
        Id id;
    }

    /**
     * Holds the unique video ID returned from the YouTube API.
     */
    @Data
    static class Id {
        String videoId; // Use lowercase "videoId" to match actual YouTube API JSON key
    }

    /**
     * Represents the structure of the video details API response (future use).
     * Used when fetching full video metadata.
     */
    @Data
    static class VideoApiResponse {
        List<VideoItem> items;
    }

    @Data
    static class VideoItem{
        Snippet snippet;
    }

    /**
     * Represents a video’s basic information, including its title,
     * description, channel name, tags, and thumbnail links.
     */
    @Data
    static class Snippet {
        String title;
        String description;
        String channelTitle;
        String publishedAt;
        List<String> tags;
        Thumbnails thumbnails;
    }

    /**
     * Represents multiple available thumbnail resolutions for a video.
     * The helper method selects the best available thumbnail URL.
     */
    @Data
    static class Thumbnails {
        Thumbnail maxres;
        Thumbnail high;
        Thumbnail medium;
        Thumbnail _default;

        /**
         * Returns the best available thumbnail URL
         * by checking higher-quality images first.
         */
        String getBestThumbnailUrl() {
            if (maxres != null) return maxres.url;
            if (high != null) return high.url;
            if (medium != null) return medium.url;
            return _default != null ? _default.url : "";
        }
    }
    static class Thumbnail{
        String url;
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
