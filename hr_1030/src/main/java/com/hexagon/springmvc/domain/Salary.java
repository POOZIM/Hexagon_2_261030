package com.hexagon.springmvc.domain;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Salary {

    private Long salaryId;           // 급여 번호
    private Long empId;              // 사원 번호
    private String salaryYearMonth; // 지급 연월: 2026-11

    private BigDecimal baseSalary;   // 기본급
    private BigDecimal bonus;        // 수당
    private BigDecimal deduction;    // 공제액
    private BigDecimal netSalary;    // 실수령액

    private LocalDate paymentDate;  // 지급일
}