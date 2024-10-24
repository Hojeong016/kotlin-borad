package com.example.demo.controller

import com.example.demo.Service.WriteService
import com.example.demo.entity.Board
import com.example.demo.entity.BoardDTO
import com.example.demo.entity.QBoard.board
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api")
class WriteController(private val writeService: WriteService) {

    //전체 게시물 조화
    @GetMapping("/read")
    fun read() : List<Board> = writeService.findAll()

    //단일 게시물 조회
    @GetMapping("/read/{id}")
    fun readOnly(@PathVariable("id") id:String) : Board = writeService.findOnly(id)

    @PostMapping("/write")
    fun create(@RequestBody writeDto : BoardDTO) : Unit = writeService.create(writeDto)

    //게시물 수정
    @PatchMapping("/write/{id}")
    fun update(@PathVariable("id") id:String,  @RequestBody writeDto : BoardDTO) : ResponseEntity<String> {
        writeService.update(id, writeDto)
        return ResponseEntity.ok("수정에 성공했습니다.")
    }

    //게시물 삭제
    @DeleteMapping("/write/{id}")
    fun delete(@PathVariable("id") id:String) : ResponseEntity<String>{
        writeService.delete(id)
        return ResponseEntity.ok("게시물을 삭제하였습니다.")
    }


    // 값이 안들어올 경우 코틀린에서은 어떻게 컨트롤러에서 널처리를 하는가?
    //컨트롤러와 서비스단 모두 널처리를 주는 것이 좋을까 아니면 서비스단에서만 널처리를 하는 것이 좋을까?
    //컨트롤러 단에서는 정말 간단한 부분에 대한 널처리를 진행한다. : @RequestParam 또는 @RequestBody에 널 값이 들어오는 경우 기본적인 처리를 할 수 있다.
    //방법 -> @RestControllerAdvice로 작성한 전역 예외 처리기로 애플리케이션의 모든 컨트롤러에서 발생하는 NullPointerException를 처리하였다.
}

//새롭게 알게된 예외 전역 처리
//ControllerAdvice와 RestControllerAdvice
//Spring 프레임워크에서 다양하게 에러 처리를 할 수 있는데 그 중 한 방법은 ControllerAdvice입니다.
//ControllerAdvice를 사용하게 되면 가장 큰 장점이 전역적으로 예외를 처리할 수 있다는 점입니다.
//RestControllerAdvice의 역할 역시 전역적으로 예외를 처리하게 해줍니다.
//하지만 @RestControllerAdvice는 @Controller와 @RestController의 차이처럼 @ResponseBody가 추가되어 Json으로 응답을 해준다는 점이 @ControllerAdvice와 다릅니다.
//즉, @ControllerAdvice + @ResponseBody = @RestControllerAdvice입니다.

//동작 방식
//예외 발생: 애플리케이션의 어떤 컨트롤러에서든지 NullPointerException이나 기타 처리 대상 예외가 발생합니다.
//전역 예외 처리: 스프링이 예외를 감지하면 **@RestControllerAdvice**에 정의된 @ExceptionHandler 메서드 중 해당 예외를 처리할 수 있는 메서드를 자동으로 호출합니다.
//예외 응답 반환: GlobalExceptionHandler에서 정의한 대로 클라이언트에게 적절한 응답(예: HTTP 상태 코드와 메시지)을 반환합니다.