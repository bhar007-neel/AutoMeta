package com.AutoMeta.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data                  //@Data  This single annotation secretly generates: Getters, Setters, toString(), equals(), hashCode(),Without Lombok, you’d write 40+ lines of garbage.
@NoArgsConstructor
@AllArgsConstructor

public class VideoDetails {
    private String id;
    private String title;
    private String description;
    private String thumbnailUrl;
    private String channelTitle;
    private List<String> tags;
    private String publishedAt;

}
