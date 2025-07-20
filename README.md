# Blink - 자동 배포 서비스

## 📋 프로젝트 개요

Blink는 Dockerfile을 통한 자동 배포 서비스를 제공하는 Spring Boot 기반의 백엔드 API입니다.
워크스페이스와 프로젝트를 관리하고, Docker 컨테이너 기반의 배포를 자동화할 수 있습니다.

## 🏗️ 시스템 아키텍처

```
Workspace (워크스페이스)
├── Project (프로젝트)
│   ├── Dockerfile
│   ├── Environment Variables (환경변수)
│   └── Deployments (배포)
│       ├── Build Logs (빌드 로그)
│       ├── Deployment Logs (배포 로그)
│       └── Status (배포 상태)
```

## 🛠️ 기술 스택

-   **Framework**: Spring Boot 3.4.4
-   **Language**: Kotlin 1.9.25
-   **Build Tool**: Gradle (Kotlin DSL)
-   **Database**: MySQL
-   **Cache**: Redis
-   **ORM**: Spring Data JPA + Hibernate
-   **API Documentation**: Swagger/OpenAPI 3
-   **Code Style**: ktlint

## 🎯 주요 기능

### 1. 워크스페이스 관리

-   워크스페이스 생성, 조회, 수정, 삭제
-   워크스페이스별 프로젝트 관리

### 2. 프로젝트 관리

-   프로젝트 생성, 조회, 수정, 삭제
-   Dockerfile 관리 및 유효성 검사
-   워크스페이스별 프로젝트 조회

### 3. 환경변수 관리

-   프로젝트별 환경변수 설정
-   시크릿 환경변수 마스킹 처리
-   환경변수 CRUD 기능

### 4. 배포 관리

-   버전별 배포 관리
-   배포 상태 추적 (PENDING, BUILDING, DEPLOYING, RUNNING, STOPPED, FAILED, ROLLBACK)
-   빌드/배포 로그 관리
-   Docker 이미지 및 컨테이너 정보 관리

## 📁 프로젝트 구조

```
src/main/kotlin/org/example/blink/
├── config/                     # 설정 클래스
│   ├── JpaConfig.kt
│   └── SwaggerConfig.kt
├── common/                     # 공통 컴포넌트
│   ├── entity/BaseEntity.kt
│   ├── exception/
│   └── response/ApiResponse.kt
└── domain/                     # 도메인별 패키지
    ├── workspace/              # 워크스페이스 도메인
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── entity/
    │   └── dto/
    ├── project/                # 프로젝트 도메인
    │   ├── controller/
    │   ├── service/
    │   ├── repository/
    │   ├── entity/
    │   └── dto/
    ├── deployment/             # 배포 도메인
    │   ├── entity/
    │   └── dto/
    └── environment/            # 환경변수 도메인
        ├── entity/
        └── dto/
```

## 🚀 시작하기

### 전제 조건

-   Java 21
-   MySQL 8.0+
-   Redis 6.0+
-   Docker (배포 기능 사용 시)

### 환경 설정

1. **데이터베이스 설정**

    ```bash
    # MySQL 데이터베이스 생성
    CREATE DATABASE blink;
    ```

2. **환경변수 설정** (`src/main/resources/env.yaml`)

    ```yaml
    # 데이터베이스 설정
    DB_URL: localhost
    DB_NAME: blink
    DB_USERNAME: root
    DB_PASSWORD: "your_password"

    # Redis 설정
    REDIS_HOST: localhost
    REDIS_PORT: 6379
    REDIS_PASSWORD: ""
    ```

### 실행 방법

1. **프로젝트 클론**

    ```bash
    git clone <repository-url>
    cd blink
    ```

2. **의존성 설치 및 실행**

    ```bash
    ./gradlew bootRun
    ```

3. **API 문서 확인**
    - Swagger UI: http://localhost:8080/swagger-ui.html
    - API Docs: http://localhost:8080/v3/api-docs

## 📚 API 문서

### 주요 엔드포인트

#### 워크스페이스 API

-   `POST /api/v1/workspaces` - 워크스페이스 생성
-   `GET /api/v1/workspaces` - 워크스페이스 목록 조회
-   `GET /api/v1/workspaces/{id}` - 워크스페이스 조회
-   `GET /api/v1/workspaces/{id}/detail` - 워크스페이스 상세 조회
-   `PUT /api/v1/workspaces/{id}` - 워크스페이스 수정
-   `DELETE /api/v1/workspaces/{id}` - 워크스페이스 삭제

#### 프로젝트 API

-   `POST /api/v1/projects` - 프로젝트 생성
-   `GET /api/v1/projects/{id}` - 프로젝트 조회
-   `GET /api/v1/projects/{id}/detail` - 프로젝트 상세 조회
-   `GET /api/v1/projects/workspace/{workspaceId}` - 워크스페이스별 프로젝트 목록
-   `PUT /api/v1/projects/{id}` - 프로젝트 수정
-   `PUT /api/v1/projects/{id}/dockerfile` - Dockerfile 수정
-   `DELETE /api/v1/projects/{id}` - 프로젝트 삭제

### API 응답 형식

```json
{
    "success": true,
    "message": "성공",
    "data": {
        // 응답 데이터
    },
    "errorCode": null,
    "errors": null,
    "timestamp": "2024-01-01T00:00:00"
}
```

## 🔧 개발 환경

### 코드 스타일

```bash
# ktlint 검사
./gradlew ktlintCheck

# ktlint 자동 포맷팅
./gradlew ktlintFormat
```

### 테스트 실행

```bash
./gradlew test
```

### Docker 빌드

```bash
./gradlew jibDockerBuild
```

## 🗃️ 데이터베이스 스키마

### 주요 테이블

-   `workspaces` - 워크스페이스 정보
-   `projects` - 프로젝트 정보
-   `deployments` - 배포 정보
-   `environments` - 환경변수 정보

## 🔮 향후 계획

-   [ ] 배포 서비스 구현 (Docker 빌드/배포 자동화)
-   [ ] 환경변수 관리 API 구현
-   [ ] 배포 로그 실시간 스트리밍
-   [ ] 웹훅 기능 추가
-   [ ] 모니터링 및 알림 기능
-   [ ] 롤백 기능 강화
-   [ ] CI/CD 파이프라인 연동

## 📝 라이선스

이 프로젝트는 MIT 라이선스 하에 배포됩니다.

## 🤝 기여

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📞 연락처

프로젝트에 대한 문의사항이 있으시면 이슈를 등록해주세요.
