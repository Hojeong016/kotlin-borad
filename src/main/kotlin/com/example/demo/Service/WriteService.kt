package com.example.demo.Service

import com.example.demo.entity.Write
import com.example.demo.entity.WriteDTO
import com.example.demo.repository.WriteRepository
import org.springframework.stereotype.Service

@Service
class WriteService(private val writeRepository : WriteRepository) {


    //creat(생성)
    fun create(writeDto : WriteDTO ){
        val write =  Write().apply{ //Kotlin No-Arg 플러그인을 Gradle에 추가
            title = writeDto.title
            content = writeDto.content
            image = writeDto.image?.bytes
        }

        writeRepository.save(write)
    }

    //read
    fun findAll() : List<Write> = writeRepository.findAll()
    // 널처리

    //update(수정)
    //run or with --> 결과를 반환
    fun update(id: Long,writeDto : WriteDTO){
        val write = writeRepository.findById(id).orElse(null)
        //let을 통해 널이 아닐때만 저장에 성공하도록
        write ?.let{
            it.title = writeDto.title
            it.content = writeDto.content
            it.image = writeDto.image?.bytes
            writeRepository.save(it)
        }
    }

    //delete(삭제)
    fun delete(id: Long) = writeRepository.deleteById(id)
    //널처리
}