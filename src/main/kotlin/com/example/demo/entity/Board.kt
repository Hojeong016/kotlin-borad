package com.example.demo.entity

import com.example.demo.tsid.TSID
import com.example.demo.tsid.TSIDListener
import jakarta.persistence.*


import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime

// 처음에 붉은 줄이 뜬 이유 : KAPT 설정과 관련이 있다.
@Entity
@EntityListeners(TSIDListener::class)
data class Board(
    @Id
    @TSID
    @Column(name = "board_id")
    var id : String ?= null,
    //왜 고유키를 널로 설정하는가? = 널로 설정해야 JPA가 처리할 수 있다.

    @Column(name = "board_name")
    var nickname : String ?= null,

    @Column(name = "board_title")
    var title : String = "",

    @Column(name = "board_content")
    var content : String = "",

    @Column(name = "board_file")
    var image: ByteArray ? = null,

    @Column(name = "board_created_at")
    var createAt: LocalDateTime = LocalDateTime.now()
)


data class BoardDTO(
    val title : String,
    val content : String,
    val image : MultipartFile? = null,
    val nickname : String,
    val createAt: LocalDateTime = LocalDateTime.now()
)

//멀티파일 형식으로 데이터를 받아오고 -> 바이너리 타입으로 변환
//HTTP를 통해 파일을 전송할 때, 전송되는 파일은 멀티파트 형식으로 전달되며 -> 각 파트에는 텍스트나 바이너리 데이터를 포함할 수 있다.
// List<>를 통해 다중 파일 업로드로 지원 가능하다.

//주의 dto는 불변성 유지로 val 타입 쓰기