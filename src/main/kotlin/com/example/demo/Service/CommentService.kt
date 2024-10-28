package com.example.demo.Service

import com.example.demo.entity.*
import com.example.demo.repository.CommentRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class CommentService(private val commentRepository: CommentRepository, private val boardService: BoardService) {

    //댓글 생성하기
    @Transactional
    fun createComment(id:String,createCommentDTO : CreateCommentDTO){
        println("시작")
        val dto : BoardDTO = boardService.findOnly(id)
        val board = Board(id = dto.id,)
        val comment = Comment().apply {
                this.boardId = board
                nickname = createCommentDTO.nickname
                content = createCommentDTO.content
                createdAt = createCommentDTO.createdAt
                deep = 0
                isDeleted = false
                parentId = null}
            commentRepository.save(comment)
        }
    //부모 댓글이 되기 때문에 깊이와 부모 아이디 초기값으로 설정해두기

    @Transactional
    fun createReply(parentId : Long,createReplyDTO : CreateReplyDTO){
        //부모 댓글 확인
        val pComment : Comment =  commentRepository.findById(parentId).orElse(null)
        val reply = Comment().apply {
            boardId = pComment.boardId
            this.parentId = pComment
            nickname = createReplyDTO.nickname
            content = createReplyDTO.content
            createdAt = LocalDateTime.now()/*createReplyDTO.createdAt*/
            deep = (pComment.deep ?: 0) + 1
            isDeleted = false
        }
        commentRepository.save(reply)
    }


    //댓글 수정하기 = 내가 작성한 댓글만 수정이 가능하다.
    @Transactional
    fun updateComment(id:Long,commentDTO : CommentDTO){
        //추후에는 인증 객체로 들어올 예정
        var comment :Comment ?= commentRepository.findById(id).orElse(null)
        comment?.let {
            it.content = commentDTO.content
            it.updatedAt = LocalDateTime.now()
            commentRepository.save(it)
        }
    }

    //댓글 조회하기(게시물)
    /*fun readComment(boardId : String) : MutableList<Comment> = commentRepository.findByBoarId(boardId)*/
    fun readComment(boardId : String) : List<Comment>{
        val allComments = commentRepository.findByBoarId(boardId)
        val result = mutableListOf<Comment>()
        val addedComments = mutableSetOf<Long>()

        allComments.filter { it.parentId == null }.forEach{parentComment ->  addCommentWithReplies(parentComment,result,addedComments,allComments)}

        return result
    }
    //댓글 조회하기(닉네임)
    fun readCommentByNickname(nickname : String) : MutableList<Comment> = commentRepository.findByNickname(nickname)

    //댓글 삭제하기 = 내가 작성한 댓글만 삭제해야한다.
    fun deleteComment(nickname: String,commentId: Long) {
        val comment = commentRepository.findById(commentId).orElse(null)
        if(comment.nickname == nickname)  commentRepository.deleteById(commentId) else throw NullPointerException()
    }

    fun addCommentWithReplies(comment: Comment,result: MutableList<Comment>,addedComments: MutableSet<Long>,allComments: List<Comment>){

        if(addedComments.contains(comment.id)) return
        result.add(comment)
        addedComments.add(comment.id!!)
        // 현재 댓글의 자식 댓글들을 찾습니다.
        val replies = allComments.filter { it.parentId?.id == comment.id }
        // 자식 댓글 각각에 대해 재귀적으로 addCommentWithReplies 함수를 호출합니다.
        replies.forEach { reply ->
            addCommentWithReplies(reply, result, addedComments, allComments)
        }

    }
}