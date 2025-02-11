// 전역 변수로 response를 저장할 변수 선언
let globalResponse = null;

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
                inflationIncrease: $("#inflationIncrease").is(":checked"),
                duration: isNaN(parseInt($("#duration").val())) ? 0 : parseInt($("#duration").val()),        // 투자 기간 (년)
            };

            console.log(data.inflationIncrease);

            $.ajax({
              url: "/api/getCalculation",
              method: "GET",
              data: data,
              success: function (response) {
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
    window.location.href = "/calculator"; // "/calculator" 경로로 리디렉션
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
        let cursorPosition = input.selectionStart; // 현재 커서 위치 저장

        // 값이 있을 경우에만 포맷 적용
        if (value) {
            $(this).val(parseInt(value, 10).toLocaleString());
        } else {
            $(this).val(""); // 입력값이 없으면 빈 문자열 유지
        }

        // 커서 위치 복원
        input.setSelectionRange(cursorPosition, cursorPosition);
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

document.addEventListener('DOMContentLoaded', function () {
    // 툴팁을 트리거할 요소들을 선택
    var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));

    // 툴팁 초기화
    var tooltipList = tooltipTriggerList.map(function (tooltipTriggerEl) {
        return new bootstrap.Tooltip(tooltipTriggerEl);
    });
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