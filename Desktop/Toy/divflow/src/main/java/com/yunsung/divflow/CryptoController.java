package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/crypto")
public class CryptoController {
    @GetMapping("")
    public String crypto() {
        return "cryptoList";
    }

    @GetMapping("/btc")
    public String btc() {
        return "btc";
    }

    @GetMapping("/eth")
    public String eth() {
        return "eth";
    }
}
