package com.hexagon.springmvc.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hexagon.springmvc.service.SalaryService;

@Controller
public class SalaryController {

    private final SalaryService salaryService;

    public SalaryController(SalaryService salaryService) {
        this.salaryService = salaryService;
    }

    // 전체 급여 목록은 인사 관리자만 조회
    @PreAuthorize("hasRole('HR_ADMIN')")
    @GetMapping("/salary/list")
    public String list(Model model) {

        model.addAttribute(
                "salaries",
                salaryService.getSalaryList()
        );

        return "salary/list";
    }
}