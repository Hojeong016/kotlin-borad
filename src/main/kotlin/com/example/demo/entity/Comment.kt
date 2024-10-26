package com.example.demo.entity

import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import java.time.LocalDateTime

@Entity
data class Comment(

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "comment_id")
    var id : Long ?= null,
    // 해당 댓글의 부모 게시글

    //하나의 게시판에는 여러개의 댓글 . 게시물과 댓글을 부모 자식으로 설계
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    var boardId: Board = Board(),

    @Column(name = "nickname")
    var nickname : String? = null,

    @Column(name = "comment_content")
    var content : String? = null,

    @Column(name = "created_at")
    var createdAt : LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updatedAt: LocalDateTime = LocalDateTime.now(),

    //대댓글 구현을 위한 속성 추가

    //깊이
    @Column(name = "deep")
    var deep : Int? = null,

    //무한루프에 빠지지않게 null로 설정함
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentId")
    var parentId: Comment? = null,

    @Column(name = "is_deleted")
    var isDeleted: Boolean = false,

)

//클라이언트에게 전달
data class CommentDTO(
    val boardId : String,
    val nickname : String? = null,
    val content: String? = null,
    val createdAt : LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
)

//클라이언트에게 받아 온
data class CreateCommentDTO(
    val boardId : String,
    val nickname : String? = null,
    val content: String? = null,
    val createdAt : LocalDateTime = LocalDateTime.now(),
)

//대댓글 dto
data class CreateReplyDTO(
    val parentId: Long,
    val nickname : String? = null,
    val content: String? = null,
    val createdAt : LocalDateTime = LocalDateTime.now(),
)

//업데이트 dto
/*data class UpdateCommentDTO(
    val boardId : String,
)*/

//