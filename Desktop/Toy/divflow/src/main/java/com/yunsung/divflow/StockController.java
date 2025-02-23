package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StockController {
    @GetMapping("/spy")
    public String spy() {
        return "spy";
    }

    @GetMapping("/qqq")
    public String qqq() {
        return "qqq";
    }

    @GetMapping("/schd")
    public String schd() {
        return "schd";
    }

    @GetMapping("/aapl")
    public String aapl() {
        return "aapl";
    }

    @GetMapping("/amzn")
    public String amzn() {
        return "amzn";
    }

    @GetMapping("/brkb")
    public String brkb() {
        return "brkb";
    }

    @GetMapping("/cost")
    public String cost() {
        return "cost";
    }

    @GetMapping("/ge")
    public String ge() {
        return "ge";
    }

    @GetMapping("/lin")
    public String lin() {
        return "lin";
    }

    @GetMapping("/lly")
    public String lly() {
        return "lly";
    }

    @GetMapping("/meta")
    public String metaStock() {
        return "meta";
    }

    @GetMapping("/nee")
    public String nee() {
        return "nee";
    }

    @GetMapping("/pld")
    public String pld() {
        return "pld";
    }

    @GetMapping("/xom")
    public String xom() {
        return "xom";
    }

    @GetMapping("/xlb")
    public String xlb() {
        return "xlb";
    }


    @GetMapping("/xlc")
    public String xlc() {
        return "xlc";
    }


    @GetMapping("/xle")
    public String xle() {
        return "xle";
    }


    @GetMapping("/xlf")
    public String xlf() {
        return "xlf";
    }


    @GetMapping("/xli")
    public String xli() {
        return "xli";
    }


    @GetMapping("/xlk")
    public String xlk() {
        return "xlk";
    }


    @GetMapping("/xlp")
    public String xlp() {
        return "xlp";
    }


    @GetMapping("/xlre")
    public String xlre() {
        return "xlre";
    }


    @GetMapping("/xlu")
    public String xlu() {
        return "xlu";
    }


    @GetMapping("/xlv")
    public String xlv() {
        return "xlv";
    }


    @GetMapping("/xly")
    public String xly() {
        return "xly";
    }
}
