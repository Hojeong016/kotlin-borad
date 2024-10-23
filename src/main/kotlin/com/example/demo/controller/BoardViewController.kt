package com.example.demo.controller

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping

@Controller
class BoardViewController {

    //게시물 작성 폼 호출
    @GetMapping("/new")
    fun newWrite(): String = "main/write"

}