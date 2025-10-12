package com.AutoMeta.Service;

import org.springframework.stereotype.Service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ThumbnailService {
    // so Youtube has 3 different types of links and service should take care of all of them

    // This method extracts the YouTube video ID from a given URL or ID string.
    // It handles different formats of YouTube links and returns the 11-character video ID.


    public String extractVideoId(String url){
        // Case 1: If the input is already just the 11-character YouTube ID (no URL)

     if(url.matches("^[a-zA-Z-0-9_-]{11}$")){
         return url;
     }
        // Case 2: Define possible YouTube URL patterns that contain the video ID
        String[] patterns={
                "(?:https?:\\/\\/)?(?:www\\.)?youtube\\.com\\/watch\\?v=([a-zA-Z0-9_-]{11})",
                "(?:https?:\\/\\/)?(?:www\\.)?youtu\\.be\\/([a-zA-Z0-9_-]{11})",
                "(?:https?:\\/\\/)?(?:www\\.)?youtube\\.com\\/embed\\/([a-zA-Z0-9_-]{11})"
        };

        // Loop through each pattern to find which one matches the given URL
     for(String pattern: patterns){
         // Create a Matcher object to check if the current pattern matches any part of the 'url'
         Matcher matcher = Pattern.compile(pattern).matcher(url);

         // If a match is found in the URL for this pattern
         if(matcher.find()){
             // Return the matched substring from the URL
             return matcher.group(1);
         }
     }
     return null;

    }
}
