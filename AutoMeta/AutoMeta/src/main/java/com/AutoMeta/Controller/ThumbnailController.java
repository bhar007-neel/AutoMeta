package com.AutoMeta.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ThumbnailController {
    @GetMapping("/thumbnail")  // endpoint
    public String getThumbnail(){
        return "thumbnails";
    }

}
