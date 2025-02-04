function sendCalculationAjaxRequest() {

        const data = {
            growth: isNaN(parseFloat($("#growth").val())) ? 0 : parseFloat($("#growth").val()),           // 배당 성장률 (%)
            yield: isNaN(parseFloat($("#yield").val())) ? 0 : parseFloat($("#yield").val()),             // 배당률 (%)
            reinvest: $("#reinvest").is(":checked"),         // 배당금 재투자 (체크 여부)
            inflation: isNaN(parseFloat($("#inflation").val())) ? 0 : parseFloat($("#inflation").val()), // 물가 상승률 (%)
            tax: isNaN(parseFloat($("#tax").val())) ? 0 : parseFloat($("#tax").val()),                  // 세금 (%)
            initial: isNaN(parseFloat($("#initial").val().replace(/[^0-9]/g, ''))) ? 0 : parseFloat($("#initial").val().replace(/[^0-9]/g, '')), // 초기 투자금 (₩)
            monthly: isNaN(parseFloat($("#monthly").val().replace(/[^0-9]/g, ''))) ? 0 : parseFloat($("#monthly").val().replace(/[^0-9]/g, '')), // 월 적립식 투자금 (₩)
            increase: isNaN(parseFloat($("#increase").val().replace(/[^0-9]/g, ''))) ? 0 : parseFloat($("#increase").val().replace(/[^0-9]/g, '')), // 매년 적립금 증액 (₩)
            duration: isNaN(parseInt($("#duration").val())) ? 0 : parseInt($("#duration").val()),        // 투자 기간 (년)
        };

        $.ajax({
          url: "/api/getCalculation",
          method: "GET",
          data: data,
          success: function (response) {
                // 매입금액
                const purchaseAmount = response.purchaseAmount;
                // 평가금액
                const totalInvestment = response.totalInvestment;
                // 수익률(%)
                const growthRate = (((totalInvestment - purchaseAmount) / purchaseAmount) * 100).toFixed(2);

                $('#noInflationMonthlyDividend').text(response.noInflationCurrentDevidend.toLocaleString());
                $('#inflationMonthlyDividend').text(response.inflationCurrentDevidend.toLocaleString());
                $('#monthlyInsurance').text(response.insurance.toLocaleString());
                $('#growthRate').text(growthRate);
                $('#purchaseAmount').text(response.purchaseAmount.toLocaleString());
                $('#totalInvestment').text(response.totalInvestment.toLocaleString());
                $('#realUsableAmount').text(response.realUsableAmount.toLocaleString());
            },
            error: function (err) {
                console.error("에러 발생:", err);
            }
        });
}

$("#calculator").click(function() {
    window.location.href = "/calculator"; // "/calculator" 경로로 리디렉션
});

// 특정 input 필드에 콤마를 추가하는 함수
    function formatNumberInput(selector) {
        $(selector).on("input", function() {
            // 현재 입력값에서 숫자만 남기기
            let value = $(this).val().replace(/[^0-9]/g, '');

            // 값이 있을 경우에만 포맷 적용
            if (value) {
                $(this).val(parseInt(value, 10).toLocaleString());
            } else {
                $(this).val(""); // 입력값이 없으면 빈 문자열 유지
            }
        });
    }

    // 적용할 input 필드에 대해 실행
    $(document).ready(function() {
        formatNumberInput("#initial");
        formatNumberInput("#monthly");
        formatNumberInput("#increase");
    });

function validateInput(event) {
        // 숫자가 아닌 문자는 모두 삭제
        event.target.value = event.target.value.replace(/[^0-9]/g, '');
}