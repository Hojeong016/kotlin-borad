package com.example.demo.controller

import com.example.demo.Service.CommentService
import com.example.demo.entity.Comment
import com.example.demo.entity.CommentDTO
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentRestController(private val commentService: CommentService) {


    @GetMapping("/read/{BoardId}")
    fun readComment(@PathVariable("BoardId") boardId: String) : MutableList<Comment> = commentService.readComment(boardId)

    @PostMapping("/create")
    fun createComment(@RequestBody commentDTO: CommentDTO) : ResponseEntity<String> {
        val id: String? = commentDTO.boardId
        commentService.createComment(id,commentDTO)
        return ResponseEntity.ok("댓글 생성에 성공했습니다.")
    }


}