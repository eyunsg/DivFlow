package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/topMarketCap")
public class TopMarketCapController {
    @GetMapping("")
    public String topMarketCap() {
        return "topUsMarketCapList";
    }

    @GetMapping("/aapl")
    public String aapl() {
        return "aapl";
    }

    @GetMapping("/amzn")
    public String amzn() {
        return "amzn";
    }

    @GetMapping("/avgo")
    public String avgo() {
        return "avgo";
    }

    @GetMapping("/brka")
    public String brka() {
        return "brka";
    }

    @GetMapping("/googl")
    public String googl() {
        return "googl";
    }

    @GetMapping("/lly")
    public String lly() {
        return "lly";
    }

    @GetMapping("/ma")
    public String ma() {
        return "ma";
    }

    @GetMapping("/meta")
    public String metaStock() {
        return "meta";
    }

    @GetMapping("/msft")
    public String msft() {
        return "msft";
    }

    @GetMapping("/nvda")
    public String nvda() {
        return "nvda";
    }

    @GetMapping("/tsla")
    public String tsla() {
        return "tsla";
    }

    @GetMapping("/tsm")
    public String tsm() {
        return "tsm";
    }

    @GetMapping("/unh")
    public String unh() {
        return "unh";
    }

    @GetMapping("/v")
    public String v() {
        return "v";
    }

    @GetMapping("/wmt")
    public String wmt() {
        return "wmt";
    }
}
