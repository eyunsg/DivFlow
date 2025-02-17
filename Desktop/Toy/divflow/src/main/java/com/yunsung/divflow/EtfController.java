package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/etf")
public class EtfController {
    @GetMapping("")
    public String etfList() {
        return "etfList";
    }

    @GetMapping("/dgro")
    public String dgro() {
        return "dgro";
    }

    @GetMapping("/dvy")
    public String dvy() {
        return "dvy";
    }

    @GetMapping("/fyd")
    public String fyd() {
        return "fyd";
    }

    @GetMapping("/hdv")
    public String hdv() {
        return "hdv";
    }

    @GetMapping("/jepi")
    public String jepi() {
        return "jepi";
    }

    @GetMapping("/jepq")
    public String jepq() {
        return "jepq";
    }

    @GetMapping("/nobl")
    public String nobl() {
        return "nobl";
    }

    @GetMapping("/schd")
    public String schd() {
        return "schd";
    }

    @GetMapping("/sdy")
    public String sdy() {
        return "sdy";
    }

    @GetMapping("/spyd")
    public String spyd() {
        return "spyd";
    }

    @GetMapping("/vig")
    public String vig() {
        return "vig";
    }

    @GetMapping("/vym")
    public String vym() {
        return "vym";
    }
}
