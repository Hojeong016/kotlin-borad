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

    @PatchMapping("/update/{CommentId}")
    fun updateComment(@PathVariable("CommentId")  commentId: Long,@RequestBody commentDTO: CommentDTO) : ResponseEntity<String> {
        commentService.updateComment(commentId,commentDTO)
       return ResponseEntity.ok("수정에 성공했습니다.")
    }

    @DeleteMapping("/delete/{CommentId}/{nickname}")
    fun deleteComment(@PathVariable("CommentId") commentId: Long, @PathVariable("nickname") nickname : String ) : ResponseEntity<String> {
        // 추후에는 인증 객체에서 닉네임을 뽑아와서 보내기
        commentService.deleteComment(nickname, commentId)
        return ResponseEntity.ok("댓글 삭제에 성공했습니다.")
    }
}