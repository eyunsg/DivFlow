package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExplanationController {
    @GetMapping("/etf")
    public String etf() {
        return "/etf/etfList";
    }

    @GetMapping("/etf/dgro")
    public String dgro() {
        return "/etf/dgro";
    }

    @GetMapping("/etf/dvy")
    public String dvy() {
        return "/etf/dvy";
    }

    @GetMapping("/etf/fyd")
    public String fyd() {
        return "/etf/fyd";
    }

    @GetMapping("/etf/hdv")
    public String hdv() {
        return "/etf/hdv";
    }

    @GetMapping("/etf/jepi")
    public String jepi() {
        return "/etf/jepi";
    }

    @GetMapping("/etf/jepq")
    public String jepq() {
        return "/etf/jepq";
    }

    @GetMapping("/etf/nobl")
    public String nobl() {
        return "/etf/nobl";
    }

    @GetMapping("/etf/schd")
    public String schd() {
        return "/etf/schd";
    }

    @GetMapping("/etf/sdy")
    public String sdy() {
        return "/etf/sdy";
    }

    @GetMapping("/etf/spyd")
    public String spyd() {
        return "/etf/spyd";
    }

    @GetMapping("/etf/vig")
    public String vig() {
        return "/etf/vig";
    }

    @GetMapping("/etf/vym")
    public String vym() {
        return "/etf/vym";
    }

    @GetMapping("/guide")
    public String guide() {
        return "/guide/guideList";
    }

    @GetMapping("/guide/compound_interest_importance")
    public String compound_interest_importance() {
        return "/guide/compound_interest_importance";
    }

    @GetMapping("/guide/dividend_reinvestment_strategy")
    public String dividend_reinvestment_strategy() {
        return "/guide/dividend_reinvestment_strategy";
    }

    @GetMapping("/guide/dividend_reinvestment_tips")
    public String dividend_reinvestment_tips() {
        return "/guide/dividend_reinvestment_tips";
    }

    @GetMapping("/guide/dividend_stocks_etf_guide")
    public String dividend_stocks_etf_guide() {
        return "/guide/dividend_stocks_etf_guide";
    }

    @GetMapping("/guide/importance_of_dividend_reinvestment")
    public String importance_of_dividend_reinvestment() {
        return "/guide/importance_of_dividend_reinvestment";
    }

    @GetMapping("/guide/investment_guide")
    public String investment_guide() {
        return "/guide/investment_guide";
    }

    @GetMapping("/guide/reinvestment_strategy_analysis")
    public String reinvestment_strategy_analysis() {
        return "/guide/reinvestment_strategy_analysis";
    }

    @GetMapping("/topMarketCap")
    public String topMarketCap() {
        return "/top_us_market_cap/topUsMarketCapList";
    }

    @GetMapping("/topMarketCap/aapl")
    public String aapl() {
        return "/top_us_market_cap/aapl";
    }

    @GetMapping("/topMarketCap/amzn")
    public String amzn() {
        return "/top_us_market_cap/amzn";
    }

    @GetMapping("/topMarketCap/avgo")
    public String avgo() {
        return "/top_us_market_cap/avgo";
    }

    @GetMapping("/topMarketCap/brka")
    public String brka() {
        return "/top_us_market_cap/brka";
    }

    @GetMapping("/topMarketCap/googl")
    public String googl() {
        return "/top_us_market_cap/googl";
    }

    @GetMapping("/topMarketCap/lly")
    public String lly() {
        return "/top_us_market_cap/lly";
    }

    @GetMapping("/topMarketCap/ma")
    public String ma() {
        return "/top_us_market_cap/ma";
    }

    @GetMapping("/topMarketCap/meta")
    public String meta() {
        return "/top_us_market_cap/meta";
    }

    @GetMapping("/topMarketCap/msft")
    public String msft() {
        return "/top_us_market_cap/msft";
    }

    @GetMapping("/topMarketCap/nvda")
    public String nvda() {
        return "/top_us_market_cap/nvda";
    }

    @GetMapping("/topMarketCap/tsla")
    public String tsla() {
        return "/top_us_market_cap/tsla";
    }

    @GetMapping("/topMarketCap/tsm")
    public String tsm() {
        return "/top_us_market_cap/tsm";
    }

    @GetMapping("/topMarketCap/unh")
    public String unh() {
        return "/top_us_market_cap/unh";
    }

    @GetMapping("/topMarketCap/v")
    public String v() {
        return "/top_us_market_cap/v";
    }

    @GetMapping("/topMarketCap/wmt")
    public String wmt() {
        return "/top_us_market_cap/wmt";
    }

    @GetMapping("/crypto")
    public String crypto() {
        return "/crypto/cryptoList";
    }

    @GetMapping("/crypto/btc")
    public String btc() {
        return "/crypto/btc";
    }

    @GetMapping("/crypto/eth")
    public String eth() {
        return "/crypto/eth";
    }
}
