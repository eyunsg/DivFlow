package com.yunsung.divflow;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class InvestmentCalculatorController {
    @GetMapping("/getCalculation")
    public long getCalculation(
            @RequestParam float growth, // 배당성장률
            @RequestParam float yield, // 배당률
            @RequestParam boolean reinvest, // 배당금 재투자 여부
            @RequestParam float inflation, // 인플레이션
            @RequestParam float tax, // 세금
            @RequestParam long initial, // 초기 투자금
            @RequestParam long monthly, // 월 적립식 투자금
            @RequestParam long increase, // 매년 적립금 증액
            @RequestParam int duration // 투자 기간
    ) {
        long monthlyDividend = calculateDividend(
                growth, yield, reinvest,
                inflation, tax, initial,
                monthly, increase, duration);
        return monthlyDividend;
    }

    public long calculateDividend(
            float growth, float yield, boolean reinvest,
            float inflation, float tax, long initial,
            long monthly, long increase, int duration
    ) {

        // 총액
        double totalInvestment = initial;

        // 현재 월 배당금
        double currentDevidend = 0;

        // 월 적립금
        long monthlyInvestment = monthly;

        // 월 성장률
        double monthlyIncrease = Math.pow(1 + growth / 100, 1.0 / 12);

        // 월 배당률
        double monthlyDividend = (Math.pow(1 + yield / 100, 1.0 / 12) - 1);

        // 월 인플레이션률
        double monthlyInflation = Math.pow(1 - inflation / 100, 1.0 / 12);

        for (int year = 0; year < duration; year++) {
            for (int month = 0; month < 12; month++) {
                // 월 적립금 투입
                totalInvestment += monthlyInvestment;
                // 월 성장률 적용
                totalInvestment *= monthlyIncrease;
                // 현재 월 배당금 계산 (세금 적용)
                currentDevidend = (totalInvestment * monthlyDividend) * (1 - tax / 100);
                // 배당금 재투자
                if (reinvest) {
                    totalInvestment += currentDevidend;
                }
                totalInvestment *= monthlyInflation;
            }
            // 매년 적립금 증액
            monthlyInvestment += increase;
        }
        // 월 배당금
        return (long) currentDevidend;
    }
}