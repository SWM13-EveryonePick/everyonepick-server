## 📸 모두의 Pick

![Untitled (1) 중간](https://user-images.githubusercontent.com/55652627/201305948-0da577e7-38e4-43b6-9fae-a4e64f919ad2.png)

### 프로젝트 소개 (SW Maestro 13th - 2022.05 ~ 2022.11)

**총 개발인원 : 3명**

- AI : 1명, Android : 1명, Backend : 1명

**제안배경**

- 단체사진을 SNS 올리고자 할 때 완벽한 1장을 고르기 어렵다.
- 단체사진을 그룹별로 관리하기 어렵다.
- 단체사진을 찍을 때 포즈를 정하기 어렵다.

**3가지 기능**

- 여러 장의 단체사진들 중 각자 본인이 잘나온 사진을 선택하면 하나의 사진으로 만들어준 뒤 SNS에 공유할 수 있는 AI 얼굴합성 기능
- 단체사진을 그룹별로 관리할 수 있는 단체 앨범 기능
- 카메라에 인원 별 단체포즈 레이어를 표시해주는 단체 포즈레이어 기능

**프로젝트 본인 역할 및 기술스택**

- Backend 서버 개발 및 Infra 구축
    - Spring Boot, Spring Data JPA, Spring Security, OAuth 2.0, JWT
    - MySQL, Redis, Kafka, Docker, Jenkins
    - AWS EC2, RDS, S3, CloudFront, ElastiCache, MSK, ECR

API 문서 : https://www.everyonepick.net/swagger-ui/index.html
ERD : https://www.erdcloud.com/d/DWhuXgteoYBQHhKzm

## System Architecture
<img width="1466" alt="everyonepick_system_architecture" src="https://user-images.githubusercontent.com/55652627/204117083-7f7f0bac-dec8-49c1-9279-c46559151b61.png">

## Git Convention
- 형식 : `[PICK-1] <업무명>: <작업 내용>`

|업무명|내용|
| :-----------------------------------: | :---------------------------------------: |
| **ADD** |   코드나 테스트, 예제, 문서 등의 추가   |
| **FIX** | 올바르지 않은 동작을 고친 경우 |
| **REMOVE** |   코드의 삭제가 있을 때   |
| **UPDATE** |   문서나 리소스, 라이브러리등의 수정, 추가, 보완   |
| **FEAT** |  새로운 기능 추가   |
| **MODIFY** | 주로 문법의 오류나 타입의 변경, 이름 변경 등   |
| REFACTOR |   코드의 전면적인 수정   |
| DOCS |  문서의 개정   |
| RENAME | 파일의 이름 변경 |
| TEST | TEST 코드 관련   |
