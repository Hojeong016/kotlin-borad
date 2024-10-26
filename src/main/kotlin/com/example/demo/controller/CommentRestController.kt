package com.example.demo.controller

import com.example.demo.Service.CommentService
import com.example.demo.entity.Comment
import com.example.demo.entity.CommentDTO
import com.example.demo.entity.CreateCommentDTO
import com.example.demo.entity.CreateReplyDTO
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/comments")
class CommentRestController(private val commentService: CommentService) {


    @GetMapping("/read/{BoardId}")
    fun readComment(@PathVariable("BoardId") boardId: String) : MutableList<Comment> = commentService.readComment(boardId)

    @PostMapping("/create")
    fun createComment(@RequestBody createCommentDTO: CreateCommentDTO) : ResponseEntity<String> {
        val id: String = createCommentDTO.boardId
        commentService.createComment(id,createCommentDTO)
        return ResponseEntity.ok("댓글 생성에 성공했습니다.")
    }

    @PostMapping("/create/reply/{parentsId}")
    fun createReply(@PathVariable("parentsId") parentId:Long, @RequestBody createReplyDTO: CreateReplyDTO):ResponseEntity<String>{
        commentService.createReply(parentId,createReplyDTO)
        return ResponseEntity.ok("대댓글 생성에 성공했습니다.\n 댓글 내용 : ${createReplyDTO.content}" )
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