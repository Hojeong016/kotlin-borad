package com.example.demo.Service

import com.example.demo.entity.Board
import com.example.demo.entity.Comment
import com.example.demo.entity.CommentDTO
import com.example.demo.entity.QBoard.board
import com.example.demo.repository.CommentRepository
import org.springframework.stereotype.Service

@Service
class CommentService(private val commentRepository: CommentRepository, private val writeService: WriteService) {

    //댓글 생성하기
    fun createComment(id:String?,commentDTO : CommentDTO){
        println("시작")
        val board : Board = writeService.findOnly(commentDTO.boardId)
        val comment = Comment().apply {
                this.boardId = board
                nickname = commentDTO.nickname
                content = commentDTO.content
                created_at = commentDTO.created_at}
            commentRepository.save(comment)
        }


    //댓글 수정하기
    //댓글 조회하기(게시물)
    fun readComment(boardId : String) : MutableList<Comment> = commentRepository.findByBoarId(boardId)
    //댓글 조회하기(닉네임)
    //댓글 삭제하기

}