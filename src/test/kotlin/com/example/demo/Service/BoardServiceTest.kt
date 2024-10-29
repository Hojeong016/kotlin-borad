package com.example.demo.Service

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName

class BoardServiceTest {

    @DisplayName("게시물 생성 테스트")
    @Test
    fun create() {
    }
}
//@BeforeEach : 각각의 테스트 메서드가 실행되기 전 특정 작업을 수행해야할 때 사용한다.  주로 목업 데이터를 미리 세팅해 둘 때 사용
//@AfterEach : 각각의 테스트 메서드가 실행이 된 후 특정 작업을 수행해야할 때 사용한다.

//@BeforeAll
//@AfterAll
//한 클래스의 모든 메서드가 실행되기 점 실행이 된 후 특정 작업을 수행할 때 사용한다.

//@TestInstance :
// junit5의 테스트 인스턴스 생성 기본 단위 = 메서드
// 메서드간에 영향을 주는 메서드를 사용하고 싶을때 @TestInstance 사용하여 테스트 인스턴스 생성의 범위를 설정하면 된다.
//PER_METHOD : 메소드 단위 (기본값)
//PER_CLASS : 클래스 단위