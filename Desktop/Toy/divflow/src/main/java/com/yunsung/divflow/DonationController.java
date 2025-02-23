package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DonationController {
    @GetMapping("/donation")
    public String donaton(){
        return "donation";
    }
}
