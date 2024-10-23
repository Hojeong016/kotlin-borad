package com.example.demo.tsid

import jakarta.persistence.PrePersist
import java.time.Instant
import java.util.*

class TSIDListener {
    //jpa 생명주기 관리 :  @PrePersist : 엔티티가 데이터베이스에 처음 저장되기 전에 호출되는 메서드를 지정하는 데 사용
    @PrePersist
    fun prePersist(entity: Any) {
        try {
            val fields = entity.javaClass.declaredFields //getDeclaredFields()는 특정 클래스의 모든 필드를 반환합니다
            for (field in fields) {
                if (field.isAnnotationPresent(TSID::class.java) && field.type == String::class.java) {
                    field.isAccessible = true //필드 접근을 허용

                    val tstd = createTSID()
                    field[entity] = tstd
                }
            }
        } catch (e: IllegalAccessException) {
            throw RuntimeException(e)
        }
    }

    private fun createTSID(): String {
        return Instant.now().toEpochMilli().toString() + UUID.randomUUID().toString()
    }
}
