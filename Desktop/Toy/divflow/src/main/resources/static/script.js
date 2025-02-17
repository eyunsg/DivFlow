let globalResponse = null;

function sendCalculationAjaxRequest() {

            const data = {
                dividendGrowth: isNaN(parseFloat($("#dividendGrowth").val())) ? 0 : parseFloat($("#dividendGrowth").val()),           // 배당 성장률 (%)
                yield: isNaN(parseFloat($("#yield").val())) ? 0 : parseFloat($("#yield").val()),             // 배당률 (%)
                reinvest: $("#reinvest").is(":checked"),         // 배당금 재투자 (체크 여부)
                inflation: isNaN(parseFloat($("#inflation").val())) ? 0 : parseFloat($("#inflation").val()), // 물가 상승률 (%)
                tax: isNaN(parseFloat($("#tax").val())) ? 0 : parseFloat($("#tax").val()),                  // 세금 (%)
                initial: isNaN(parseFloat($("#initial").val().replace(/[^0-9]/g, ''))) ? 0 : parseFloat($("#initial").val().replace(/[^0-9]/g, '')), // 초기 투자금 (₩)
                monthly: isNaN(parseFloat($("#monthly").val().replace(/[^0-9]/g, ''))) ? 0 : parseFloat($("#monthly").val().replace(/[^0-9]/g, '')), // 월 적립식 투자금 (₩)
                increase: isNaN(parseFloat($("#increase").val().replace(/[^0-9]/g, ''))) ? 0 : parseFloat($("#increase").val().replace(/[^0-9]/g, '')), // 매년 적립금 증액 (₩)
                inflationIncrease: $("#inflationIncrease").is(":checked"),
                duration: isNaN(parseInt($("#duration").val())) ? 0 : parseInt($("#duration").val()),        // 투자 기간 (년)
                insurancePayment: $("#insurancePayment").is(":checked"),
            };

            $.ajax({
              url: "/api/getCalculation",
              method: "GET",
              data: data,
              success: function (response) {
                    $('#annualDividendType').text("세전");

                    globalResponse = response;

                    // 매입금액
                    const purchaseAmount = response.purchaseAmount;
                    // 평가금액
                    const totalInvestment = response.totalInvestment;
                    // 수익률(%)
                    const growthRate = (((totalInvestment - purchaseAmount) / purchaseAmount) * 100).toFixed(2);

                    $('#annualDividend').text(response.preTaxAnnualDividend.toLocaleString());
                    $('#noInflationMonthlyDividend').text(response.noInflationCurrentDevidend.toLocaleString());
                    $('#inflationMonthlyDividend').text(response.inflationCurrentDevidend.toLocaleString());
                    $('#monthlyInsurance').text(response.insurance.toLocaleString());
                    $('#growthRate').text(growthRate);
                    $('#purchaseAmount').text(response.purchaseAmount.toLocaleString());
                    $('#totalInvestment').text(response.totalInvestment.toLocaleString());
                    $('#additionalTax').text(response.additionalTax.toLocaleString());
                },
                error: function (err) {
                    console.error("에러 발생:", err);
                }
            });
    }

$("#calculator").click(function() {
    window.location.href = "/calculator";
});

function formatNumberInput(selector) {
    $(selector).on("focus", function () {
        // 포맷 해제 (숫자만 남기기)
        $(this).val($(this).val().replace(/,/g, ''));
    });

    $(selector).on("blur", function () {
        let value = $(this).val().replace(/[^0-9]/g, ''); // 숫자만 남기기

        if (value) {
            $(this).val(parseInt(value, 10).toLocaleString()); // 포맷 적용
        }
    });

    $(selector).on("input", function () {
        let input = $(this)[0];  // input 요소 가져오기
        let value = input.value.replace(/[^0-9]/g, ''); // 숫자만 남기기

        // 숫자만 유지, 포맷 적용하지 않음
        $(this).val(value);
    });

    $(selector).on("keyup", function (e) {
            if (e.key === "Enter") {
                $(this).blur(); // Enter 누를 시 blur() 호출하여 포커스 해제
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
    event.target.value = event.target.value.replace(/[^0-9.]/g, '').replace(/(\..*)\./g, '$1');
}

// [data-bs-toggle="tooltip"] 속성을 가진 요소를 모두 선택
$('[data-bs-toggle="tooltip"]').each(function () {
    // 각 요소마다 Bootstrap의 Tooltip을 활성화
    new bootstrap.Tooltip(this);
});

// 투자기간 0 년 -> 현재
function updateDurationLabel(value) {
    const displayText = value === '0' ? '현재' : `${value}년 후`;
    document.getElementById('durationValue').innerText = displayText;
}

let clicked = false;  // 클릭 상태 추적 변수

$("#annualDividendToggleButton").click(function(e) {
    e.preventDefault();
    var button = $(this);
    var icon = $("#toggleIcon");  // 아이콘을 선택

    // 초기 상태 설정
    if ($('#annualDividendType').text() === "세전") {
        clicked = false;
    }

    // 클릭된 상태에 따라 배당금 값과 아이콘을 토글
    if (!clicked) {
        // 첫 번째 클릭 (세전으로 설정)
        $('#annualDividend').text(globalResponse.annualDividend.toLocaleString());
        $('#annualDividendType').text("세후");

        // 아이콘을 세전 상태로 변경
        icon.attr("class", "bi bi-arrow-repeat");

        // 상태 변경
        clicked = true;
    } else {
        // 두 번째 클릭 (세후로 설정)
        $('#annualDividend').text(globalResponse.preTaxAnnualDividend.toLocaleString());
        $('#annualDividendType').text("세전");

        // 아이콘을 세후 상태로 변경
        icon.attr("class", "bi bi-cash-stack");

        // 상태 변경
        clicked = false;
    }
});

$('#inflationIncrease').change(function() {
            if ($(this).prop('checked')) {
                $('#increase').prop('disabled', true).addClass('disabled'); // 체크되면 비활성화 및 삭선 추가
            } else {
                $('#increase').prop('disabled', false).removeClass('disabled'); // 체크 해제되면 활성화 및 삭선 제거
            }
        });

// 페이지 로드 시 초기 상태에 맞게 처리
if ($('#inflationIncrease').prop('checked')) {
    $('#increase').prop('disabled', true).addClass('disabled');
}


// 나중에 서버 배포 후 테스트
// 모든 입력 필드에 대해 포커스 시 전체 선택
//$('input[type="text"]').on('focus', function() {
//    $(this).select();
//});