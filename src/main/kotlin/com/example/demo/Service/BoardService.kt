package com.example.demo.Service

import com.example.demo.entity.Board
import com.example.demo.entity.BoardDTO
import com.example.demo.repository.BoardRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class BoardService(private val boardRepository : BoardRepository) {


    //creat(생성)
    @Transactional
    fun create(writeDto: BoardDTO) {
        val write = Board().apply { //Kotlin No-Arg 플러그인을 Gradle에 추가
            title = writeDto.title
            content = writeDto.content
            image = writeDto.image?.bytes
        }

        boardRepository.save(write)
    }

    //read
    fun findAll(): List<Board> = boardRepository.findAll()

    // 널처리
    fun findOnly(id: String?) :Board = boardRepository.findById(id.toString()).orElse(null)

    //update(수정)
    //run or with --> 결과를 반환
    @Transactional
    fun update(id: String, writeDto: BoardDTO) {
        val write = boardRepository.findById(id).orElse(null)
        //let을 통해 널이 아닐때만 저장에 성공하도록
        write?.let {
            it.title = writeDto.title
            it.content = writeDto.content
            it.image = writeDto.image?.bytes
            boardRepository.save(it)
        }
        //여기에서 만약 널? 후속 처림 주는 방법 추가하기
    }

        //delete(삭제)
        fun delete(id: String) = boardRepository.deleteById(id)
        //널처리
    }
