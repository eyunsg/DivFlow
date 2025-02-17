package com.yunsung.divflow;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/guide")
public class GuideController {
    @GetMapping("")
    public String guide() {
        return "guideList";
    }

    @GetMapping("/compound_interest_importance")
    public String compound_interest_importance() {
        return "compound_interest_importance";
    }

    @GetMapping("/dividend_reinvestment_strategy")
    public String dividend_reinvestment_strategy() {
        return "dividend_reinvestment_strategy";
    }

    @GetMapping("/dividend_reinvestment_tips")
    public String dividend_reinvestment_tips() {
        return "dividend_reinvestment_tips";
    }

    @GetMapping("/dividend_stocks_etf_guide")
    public String dividend_stocks_etf_guide() {
        return "dividend_stocks_etf_guide";
    }

    @GetMapping("/importance_of_dividend_reinvestment")
    public String importance_of_dividend_reinvestment() {
        return "importance_of_dividend_reinvestment";
    }

    @GetMapping("/investment_guide")
    public String investment_guide() {
        return "investment_guide";
    }

    @GetMapping("/reinvestment_strategy_analysis")
    public String reinvestment_strategy_analysis() {
        return "reinvestment_strategy_analysis";
    }
}
