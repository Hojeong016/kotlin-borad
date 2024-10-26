package com.example.demo.entity

import com.example.demo.tsid.TSID
import com.example.demo.tsid.TSIDListener
import com.fasterxml.jackson.annotation.JsonFormat
import jakarta.persistence.*


import org.springframework.web.multipart.MultipartFile
import java.time.LocalDateTime
// 추후에 게시글이 보이는 범위 조정하기 (나만보기, 친구만 보기, 전체 보기)
// 처음에 붉은 줄이 뜬 이유 : KAPT 설정과 관련이 있다.
@Entity
@EntityListeners(TSIDListener::class)
data class Board(
    @Id
    @TSID
    @Column(name = "board_id")
    var id : String ?= null,
    //왜 고유키를 널로 설정하는가? = 널로 설정해야 JPA가 처리할 수 있다.

    @Column(name = "nickname")
    var nickname : String ?= null,

    @Column(name = "board_title")
    var title : String = "",

    @Column(name = "board_content")
    var content : String = "",

    @Lob
    @Column(name = "board_file")
    var image: ByteArray ? = null,

    @Column(name = "board_created_at")
    var createAt: LocalDateTime = LocalDateTime.now()
)

//클라이언트에게 전달
@JsonFormat(pattern = "yyyy-MM-dd")
data class BoardDTO(
    val id : String ?= null,
    val title : String,
    val content : String,
    val image : String? = null,
    val nickname : String,
    val createAt: LocalDateTime = LocalDateTime.now()
)

//JSON 타입으로 날짜를 받을때 JsonFormat 어노테이션을 추가시켜주어야 합니다.
//추가를 안한상태에서 댓글을 작성에서 목록에 표기 되었을 때 날짜가 이상하게 나옵니다
//클라이언트에게 받아 온
data class BoardUploadDTO(
    val title: String,
    val content: String,
    val image: MultipartFile? = null, // 이미지 업로드 시 사용하는 MultipartFile
    val nickname: String,
    val createAt: LocalDateTime = LocalDateTime.now()
)

//멀티파일 형식으로 데이터를 받아오고 -> 바이너리 타입으로 변환
//HTTP를 통해 파일을 전송할 때, 전송되는 파일은 멀티파트 형식으로 전달되며 -> 각 파트에는 텍스트나 바이너리 데이터를 포함할 수 있다.
// List<>를 통해 다중 파일 업로드로 지원 가능하다.

//주의 dto는 불변성 유지로 val 타입 쓰기