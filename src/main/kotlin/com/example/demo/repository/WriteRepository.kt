package com.example.demo.repository

import com.example.demo.entity.Board
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

//public 접근제어자 생략
//코틀린에서는 기본값이 public,
@Repository
interface WriteRepository : JpaRepository<Board, String>