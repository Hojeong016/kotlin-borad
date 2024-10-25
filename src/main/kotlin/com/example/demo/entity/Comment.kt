package com.example.demo.entity

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
    var created_at : LocalDateTime = LocalDateTime.now(),

    @Column(name = "updated_at")
    var updated_at: LocalDateTime = LocalDateTime.now()
)

data class CommentDTO(
    val boardId : String? = null,
    val nickname : String? = null,
    val content: String? = null,
    val created_at : LocalDateTime = LocalDateTime.now(),
    val updated_at: LocalDateTime? = null
)