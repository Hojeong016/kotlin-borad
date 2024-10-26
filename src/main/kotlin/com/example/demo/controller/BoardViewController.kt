package com.example.demo.controller


import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping


@Controller
class BoardViewController {

    // 게시물 작성 폼 호출
    @GetMapping("/write")
    fun newWrite(): String {
        return "board"
    }

    // 게시물 목록 화면 호출
    @GetMapping("/")
    fun boardList(): String {
        return "board"
    }
}