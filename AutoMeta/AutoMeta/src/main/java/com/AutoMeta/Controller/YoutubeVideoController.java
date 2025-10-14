package com.AutoMeta.Controller;

import com.AutoMeta.Model.VideoDetails;
import com.AutoMeta.Service.ThumbnailService;
import com.AutoMeta.Service.YoutubeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;


@Controller
@RequiredArgsConstructor
public class YoutubeVideoController{
    private final YoutubeService youtubeService;

    private final ThumbnailService service;

    @GetMapping("/youtube/video-details")
    public String showVideoForm(){
        return "video-details";

    }


    @PostMapping("/youtube/video-details")
    public String fetchVideoDetails(@RequestParam String videoUrlOrId, Model model){
        String videoId = service.extractVideoId(videoUrlOrId);

        if(videoId == null){
            model.addAttribute("error", "Invalid youtube Video URL or ID");
            return "video-details";
        }

        VideoDetails details = youtubeService.getVideoDetails(videoId);
        if(videoId == null){
            model.addAttribute("error","video not found....");

        }
        else {
            model.addAttribute("videoDetails",details);
        }
        model.addAttribute("videoUrlOrId", videoUrlOrId);
        return "video-details";

    }
}