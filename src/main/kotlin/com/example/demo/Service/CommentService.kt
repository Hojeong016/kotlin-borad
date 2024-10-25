package com.example.demo.Service

import com.example.demo.entity.Board
import com.example.demo.entity.Comment
import com.example.demo.entity.CommentDTO
import com.example.demo.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentService(private val commentRepository: CommentRepository, private val boardService: BoardService) {

    //댓글 생성하기
    @Transactional
    fun createComment(id:String?,commentDTO : CommentDTO){
        println("시작")
        val board : Board = boardService.findOnly(commentDTO.boardId)
        val comment = Comment().apply {
                this.boardId = board
                nickname = commentDTO.nickname
                content = commentDTO.content
                created_at = commentDTO.created_at}
            commentRepository.save(comment)
        }

    //댓글 수정하기 = 내가 작성한 댓글만 수정이 가능하다.
    @Transactional
    fun updateComment(id:Long,commentDTO : CommentDTO){
        //추후에는 인증 객체로 들어올 예정
        var comment :Comment ?= commentRepository.findById(id).orElse(null)
        comment?.let {
            it.content = commentDTO.content
            it.updated_at = LocalDateTime.now()
            commentRepository.save(it)
        }
    }

    //댓글 조회하기(게시물)
    fun readComment(boardId : String) : MutableList<Comment> = commentRepository.findByBoarId(boardId)

    //댓글 조회하기(닉네임)
    fun readCommentByNickname(nickname : String) : MutableList<Comment> = commentRepository.findByNickname(nickname)

    //댓글 삭제하기 = 내가 작성한 댓글만 삭제해야한다.
    fun deleteComment(nickname: String,commentId: Long) {
        val comment = commentRepository.findById(commentId).orElse(null)
        if(comment.nickname == nickname)  commentRepository.deleteById(commentId) else throw NullPointerException()
    }
}