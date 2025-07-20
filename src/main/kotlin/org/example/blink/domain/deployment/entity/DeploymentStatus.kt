package org.example.blink.domain.deployment.entity

enum class DeploymentStatus(val description: String) {
    PENDING("배포 대기 중"),
    BUILDING("빌드 중"),
    DEPLOYING("배포 중"),
    RUNNING("실행 중"),
    STOPPED("중지됨"),
    FAILED("실패"),
    ROLLBACK("롤백")
} 