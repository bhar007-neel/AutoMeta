package com.AutoMeta.Controller;

import com.AutoMeta.Service.ThumbnailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ThumbnailController {
    @Autowired //@Autowired is used for dependency injection — it automatically provides (or injects) an instance of the required class, so you don’t have to manually create it with
    ThumbnailService service;

    // This method handles GET requests for the "/thumbnail" URL.
    // It simply returns the "thumbnails" view page (e.g., thumbnails.html or thumbnails.jsp)

    @GetMapping("/thumbnail")  // endpoint
    public String getThumbnail(){
        return "thumbnails";
    }

    // This method handles POST requests for the "/get-thumbnail" URL
    // It processes the user's input (video URL or ID), extracts the video ID, and sends back the thumbnail URL
    @PostMapping("/get-thumbnail")
    public String showThumbnail(@RequestParam ("videoUrlOrId") String videoUrlOrId, Model model){

        // Calls the service method to extract YouTube video ID from the entered URL or ID
        String videoId = service.extractVideoId(videoUrlOrId);

        if(videoId==null){
            model.addAttribute("error", "Invalid Youtube URL");
            return "thumbnails";
        }
        // Construct the full thumbnail URL using the extracted video ID

        String thumbnailUrl = "https://img.youtube.com/vi/" + videoId + "/maxresdefault.jpg";

        // Pass the thumbnail URL to the view (so it can be displayed)
        model.addAttribute("thumbnailUrl", thumbnailUrl);
        model.addAttribute("videoId", videoId);
        return "thumbnails";

    }

}


/* Concept	Explanation
 @Controller	Marks a class as a web controller in Spring MVC.
 @Service	Marks a class as part of the business logic layer.
 @Autowired	Automatically injects a dependency managed by Spring.
 @GetMapping	Handles HTTP GET requests.
 @PostMapping	Handles HTTP POST requests.
 Model	Used to send data from controller → view.
 Pattern & Matcher	Classes used for regular expression matching in Java.
 matcher.find()	Checks if the pattern exists in the input string.
 matcher.group(1)	Returns the matched text inside the first capturing group ( ). */

/*
===========================================================
   FILE INTERACTION & DATA FLOW EXPLANATION
===========================================================

This project has two main backend components:

1️⃣ ThumbnailController.java  -->  Handles HTTP requests from the user (Frontend)
2️⃣ ThumbnailService.java     -->  Contains the business logic to process YouTube URLs

🔹 HOW THEY WORK TOGETHER:

- The user opens the "thumbnails" webpage and enters a YouTube URL or video ID.
- When they submit the form, a POST request is sent to the endpoint "/get-thumbnail".
- The ThumbnailController receives this request and extracts the input parameter `videoUrlOrId`.
- The controller then calls the ThumbnailService’s `extractVideoId()` method.
- The service uses regular expressions to check if the input matches any valid YouTube link format.
- If a match is found, it returns the 11-character video ID back to the controller.
- The controller constructs the full thumbnail image URL using that video ID.
- This thumbnail URL is then added to the Model (so it can be shown in the frontend HTML view).
- Finally, the controller returns the "thumbnail" view, displaying the image to the user.

🔹 FRONTEND FLOW SUMMARY:
User input → Controller (receives request) → Service (process logic) → Controller (adds data) → View (renders result)

So basically:
Frontend form → ThumbnailController → ThumbnailService → Model → Frontend HTML page.

===========================================================
*/

