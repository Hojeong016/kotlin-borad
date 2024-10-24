package com.example.demo.repository

import com.example.demo.entity.Comment
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface CommentRepository : JpaRepository<Comment, Long> {
    //특정 게시글의 댓글
    @Query("SELECT * FROM Comment WHERE board_id = :boardId", nativeQuery = true)
    fun findByBoarId(@Param("boardId") boardId: String): MutableList<Comment>
    //내가 적은 댓글(특정 닉네임)
    @Query("SELECT * FROM Comment WHERE nickname = :nickname", nativeQuery = true)
    fun findByNickname(@Param("nickname") nickname: String): MutableList<Comment>
}