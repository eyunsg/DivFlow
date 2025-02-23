package com.yunsung.divflow;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;


    @RestController
    @RequestMapping("/api")
    public class InvestmentCalculatorController {
        @GetMapping("/getCalculation")
        public Map<String, Long> getCalculation(
                @RequestParam
                        (name = "stockGrowth", required = false, defaultValue = "0.0")
                double stockGrowth,
                @RequestParam float dividendGrowth, // 배당성장률
                @RequestParam float yield, // 배당률
                @RequestParam boolean reinvest, // 배당금 재투자 여부
                @RequestParam float inflation, // 인플레이션
                @RequestParam float tax, // 세금
                @RequestParam long initial, // 초기 투자금
                @RequestParam long monthly, // 월 적립식 투자금
                @RequestParam long increase, // 매년 적립금 증액
                @RequestParam boolean inflationIncrease, // 물가연동 적립금 증가
                @RequestParam long yearly, // 연중 추가 납입
                @RequestParam int duration, // 투자 기간
                @RequestParam boolean insurancePayment // 건보료 납부 여부
        ) {
            Map<String, Long> result = calculateDividend(
                    stockGrowth,
                    dividendGrowth, yield, reinvest,
                    inflation, tax, initial,
                    monthly, increase, inflationIncrease,
                    yearly, duration, insurancePayment);
            return result;
        }
    public Map<String, Long> calculateDividend(
            double stockGrowth,
            float dividendGrowth, float yield,
            boolean reinvest, float inflation, float tax, long initial,
            long monthly, long increase, boolean inflationIncrease,
            long yearly, int duration, boolean insurancePayment
    ) {
        // 평가금액
        double stockTotalInvestment = initial;

        // 평가금액 (배당계산용)
        double totalInvestment = initial;

        // 매입금액
        double purchaseAmount = initial;

        // 현재 월 배당금
        double currentDividend = 0;

        // 월 적립금
        long monthlyInvestment = monthly;

        // 월 배당 성장률
        double monthlyDividendIncrease = Math.pow(1 + dividendGrowth / 100, 1.0 / 12);

        // 월 주가 상승률
        double monthlyStockIncrease = Math.pow(1 + stockGrowth / 100, 1.0 / 12);

        // 월 배당률
        double monthlyDividend = (Math.pow(1 + yield / 100, 1.0 / 12) - 1);

        // 세전 연 배당금액
        double preTaxAnnualDividend = 0;

        // 세후 연 배당금액
        double annualDividend = 0;

        // 월 보험료
        double insurance = 0;

        // 인플레이션 누적값
        double cumulativeInflationRate = 1;

        Long additionalTax = 0L;

        for (int year = 0; year < duration; year++) {

            // 세전 연 배당금액 초기화
            preTaxAnnualDividend = 0;
            annualDividend = 0;

            stockTotalInvestment += yearly;
            totalInvestment += yearly;
            purchaseAmount += yearly;

            for (int month = 0; month < 12; month++) {
                // 월 적립금 투입
                stockTotalInvestment += monthlyInvestment;
                totalInvestment += monthlyInvestment;
                purchaseAmount += monthlyInvestment;

                // 월 성장률 적용
                totalInvestment *= monthlyDividendIncrease;
                stockTotalInvestment *= monthlyStockIncrease;

                // 세전 연 배당금액에 추가
                preTaxAnnualDividend += (totalInvestment * monthlyDividend);

                // 세후 연 배당금액에 추가
                annualDividend += (totalInvestment * monthlyDividend) * (1 - tax / 100);

                // 현재 월 배당금 계산 (세금 적용)
                currentDividend = (totalInvestment * monthlyDividend) * (1 - tax / 100);

                // 보험료 납부
                if (insurancePayment) {
                    currentDividend -= insurance;
                }

                // 배당금 재투자
                if (reinvest) {
                    totalInvestment += currentDividend;
                    stockTotalInvestment += currentDividend;
                }
            }

            // 세전 연 배당금액이 2천만원 초과일 때
            if (preTaxAnnualDividend > 20000000) {
                // 건강보험료 계산
                double score = 95.295 + (((preTaxAnnualDividend / 10000) - 336) * 0.283);
                insurance = score * 208.4;
                // 장기요양보험료 계산 (최종 보험료)
                insurance += insurance * 0.1281;
            }
            // 매년 적립금 증액
            if (!inflationIncrease) {
                monthlyInvestment += increase;
            } else {
                monthlyInvestment *= (1 + inflation / 100);
            }

            // 인플레이션 누적
            cumulativeInflationRate *= 1 - inflation / 100;
        }

        // 투자 기간이 "현재"일 때
        if (duration == 0) {
            // 세전 배당
            preTaxAnnualDividend = (totalInvestment * monthlyDividend) * 12;

            annualDividend = (totalInvestment * monthlyDividend) * (1 - tax / 100) * 12;

            currentDividend = (totalInvestment * monthlyDividend);

            // 세전 연 배당금액이 2천만원 초과일 때
            if (preTaxAnnualDividend > 20000000) {
                // 건강보험료 계산
                double score = 95.295 + (((preTaxAnnualDividend / 10000) - 336) * 0.283);
                insurance = score * 208.4;
                // 장기요양보험료 계산 (최종 보험료)
                insurance += insurance * 0.1281;
            }
        }

        Map<String, Long> result = new HashMap<>();

        // 매입금액
        result.put("purchaseAmount", (long) purchaseAmount);

        // 평가금액
        result.put("totalInvestment", (long) stockTotalInvestment);

        // 월 배당금 (인플레이션 X)
        result.put("noInflationCurrentDevidend", (long) currentDividend);

        // 월 배당금 (인플레이션 O)
        result.put("inflationCurrentDevidend", (long) (currentDividend * cumulativeInflationRate));

        // 월 보험료
        result.put("insurance", (long) insurance);

        // 월 배당금 (인플레이션 X) - 월 보험료 = 실질 사용 가능 금액
        Long realUsableAmount = (long) ((currentDividend - insurance) * cumulativeInflationRate);
        result.put("realUsableAmount", realUsableAmount);

        // 종합소득세 추가 납부 계산
        if (preTaxAnnualDividend > 20_000_000) {
            additionalTax = additionalTax((long) preTaxAnnualDividend);
            result.put("additionalTax", additionalTax);
        } else {
            result.put("additionalTax", (long) 0);
        }

        // 세전 연 배당금액
        result.put("preTaxAnnualDividend", (long) preTaxAnnualDividend);

        // 세후 연 배당금액
        result.put("annualDividend", (long) annualDividend);

        return result;
    }

    public Long comprehensiveIncomeTax(long preTaxAnnualDividend) {
        long withholdingTaxAmount = 0;
        long amount = preTaxAnnualDividend - 20_000_000; // 20_000_000은 납세의무 종결금액

        if (amount <= 0) {
            return 0L;
        }

        // 6%
        if (amount > 14_000_000) { // 14,000,000 초과
            withholdingTaxAmount += (14_000_000 * 6 / 100);
            amount -= 14_000_000;
        } else { // 14,000,000 이하
            withholdingTaxAmount += amount * 6 / 100;
            return (long) withholdingTaxAmount;
        }

        // 15%
        if (amount > 36_000_000) { // 50,000,000 초과
            withholdingTaxAmount += (36_000_000 * 15 / 100);
            amount -= 36_000_000;
        } else { // 14,000,000원 초과 50,000,000원 이하
            withholdingTaxAmount += amount * 15 / 100;
            return (long) withholdingTaxAmount;
        }

        // 24%
        if (amount > 38_000_000) { // 50,000,000 초과
            withholdingTaxAmount += (38_000_000 * 24 / 100);
            amount -= 38_000_000;
        } else { // 50,000,000원 초과 88,000,000원 이하
            withholdingTaxAmount += amount * 24 / 100;
            return (long) withholdingTaxAmount;
        }

        // 35%
        if (amount > 62_000_000) { // 150,000,000 초과
            withholdingTaxAmount += (62_000_000L * 35 / 100);
            amount -= 62_000_000;
        } else { // 88,000,000원 초과 150,000,000원 이하
            withholdingTaxAmount += amount * 35 / 100;
            return (long) withholdingTaxAmount;
        }

        // 38%
        if (amount > 150_000_000) { // 최대액 초과
            withholdingTaxAmount += (150_000_000L * 38 / 100);
            amount -= 150_000_000;
        } else { // 150,000,000원 초과 300,000,000원 이하
            withholdingTaxAmount += amount * 38 / 100;
            return (long) withholdingTaxAmount;
        }

        // 40%
        if (amount > 200_000_000) {
            withholdingTaxAmount += (200_000_000L * 40 / 100);
            amount -= 200_000_000;
        } else { // 300,000,000원 초과 500,000,000원 이하
            withholdingTaxAmount += amount * 40 / 100;
            return (long) withholdingTaxAmount;
        }

        // 42%
        if (amount > 500_000_000) {
            withholdingTaxAmount += (500_000_000L * 42 / 100);
            amount -= 500_000_000;
        } else { // 500,000,000원 초과 1,000,000,000원 이하
            withholdingTaxAmount += amount * 42 / 100;
            return (long) withholdingTaxAmount;
        }

        // 45%
        if (amount > 0) {
            withholdingTaxAmount += amount * 45 / 100;
        }

        return withholdingTaxAmount;
    }

    public Long additionalTax(long preTaxAnnualDividend) {
        // 원천징수세
        Long tax = (preTaxAnnualDividend - 20_000_000) * 15 / 100;
        // 종합소득세
        Long additionalTax = comprehensiveIncomeTax(preTaxAnnualDividend);
        if (tax < additionalTax) { // 추가 납부액
            return additionalTax - tax;
        } else {
            return (long) 0;
        }
    }
}