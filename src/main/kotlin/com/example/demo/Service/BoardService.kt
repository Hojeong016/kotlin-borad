package com.example.demo.Service

import com.example.demo.entity.Board
import com.example.demo.entity.BoardDTO
import com.example.demo.entity.BoardUploadDTO
import com.example.demo.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*


@Service
class BoardService(private val boardRepository : BoardRepository) {


    //creat(생성)
    @Transactional
    fun create(BoardUploadDTO: BoardUploadDTO) {
        val write = Board().apply { //Kotlin No-Arg 플러그인을 Gradle에 추가
            title = BoardUploadDTO.title
            content = BoardUploadDTO.content
            image = BoardUploadDTO.image?.bytes
            nickname = BoardUploadDTO.nickname
        }

        boardRepository.save(write)
    }

    //read
    //주의 객체 -> 프로필 인코딩 진행하기
    fun findAll(): List<BoardDTO> {
        return boardRepository.findAll().map { board ->
            BoardDTO(
                id = board.id,
                title = board.title,
                content = board.content,
                nickname = board.nickname ?: "익명",
                image = encodeProfileImage(board.image) ?: "없음",
                createAt = board.createAt
            )
        }
    }
    // 널처리
    fun findOnly(id: String) :BoardDTO {
        return boardRepository.findById(id).map { board ->
           BoardDTO(
               id = board.id,
               title = board.title,
               content = board.content,
               nickname = board.nickname ?: "익명",
               createAt = board.createAt,
               image = encodeProfileImage(board.image)?: "없음"
           )
       }.orElse(null)
        }


    //update(수정)
    //run or with --> 결과를 반환
    @Transactional
    fun update(id: String, BoardUploadDTO: BoardUploadDTO) {
        val write = boardRepository.findById(id).orElse(null)
        //let을 통해 널이 아닐때만 저장에 성공하도록
        write?.let {
            it.title = BoardUploadDTO.title
            it.content = BoardUploadDTO.content
            it.image = BoardUploadDTO.image?.bytes
            boardRepository.save(it)
        }
        //여기에서 만약 널? 후속 처림 주는 방법 추가하기
    }

    //delete(삭제)
    fun delete(id: String) = boardRepository.deleteById(id)
    //널처리

    fun encodeProfileImage(profile: ByteArray?): String? {
        return profile?.let {
            val base64 = Base64.getEncoder().encodeToString(it)
            "data:image/png;base64,$base64"
        }
    }

    }



