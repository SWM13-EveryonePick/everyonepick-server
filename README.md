## ๐ธย ๋ชจ๋์ Pick

![Untitled (1) แแฎแผแแกแซ](https://user-images.githubusercontent.com/55652627/201305948-0da577e7-38e4-43b6-9fae-a4e64f919ad2.png)

### ํ๋ก์ ํธ ์๊ฐ (SW Maestro 13th - 2022.05 ~ 2022.11)

**์ด ๊ฐ๋ฐ์ธ์ : 3๋ช**

- AI : 1๋ช, Android : 1๋ช, Backend : 1๋ช

**์ ์๋ฐฐ๊ฒฝ**

- ๋จ์ฒด์ฌ์ง์ SNS ์ฌ๋ฆฌ๊ณ ์ ํ  ๋ ์๋ฒฝํ 1์ฅ์ ๊ณ ๋ฅด๊ธฐ ์ด๋ ต๋ค.
- ๋จ์ฒด์ฌ์ง์ ๊ทธ๋ฃน๋ณ๋ก ๊ด๋ฆฌํ๊ธฐ ์ด๋ ต๋ค.
- ๋จ์ฒด์ฌ์ง์ ์ฐ์ ๋ ํฌ์ฆ๋ฅผ ์ ํ๊ธฐ ์ด๋ ต๋ค.

**3๊ฐ์ง ๊ธฐ๋ฅ**

- ์ฌ๋ฌ ์ฅ์ ๋จ์ฒด์ฌ์ง๋ค ์ค ๊ฐ์ ๋ณธ์ธ์ด ์๋์จ ์ฌ์ง์ ์ ํํ๋ฉด ํ๋์ ์ฌ์ง์ผ๋ก ๋ง๋ค์ด์ค ๋ค SNS์ ๊ณต์ ํ  ์ ์๋ AI ์ผ๊ตดํฉ์ฑ ๊ธฐ๋ฅ
- ๋จ์ฒด์ฌ์ง์ ๊ทธ๋ฃน๋ณ๋ก ๊ด๋ฆฌํ  ์ ์๋ ๋จ์ฒด ์จ๋ฒ ๊ธฐ๋ฅ
- ์นด๋ฉ๋ผ์ ์ธ์ ๋ณ ๋จ์ฒดํฌ์ฆ ๋ ์ด์ด๋ฅผ ํ์ํด์ฃผ๋ ๋จ์ฒด ํฌ์ฆ๋ ์ด์ด ๊ธฐ๋ฅ

**ํ๋ก์ ํธ ๋ณธ์ธ ์ญํ  ๋ฐ ๊ธฐ์ ์คํ**

- Backend ์๋ฒ ๊ฐ๋ฐ ๋ฐ Infra ๊ตฌ์ถ
    - Spring Boot, Spring Data JPA, Spring Security, OAuth 2.0, JWT
    - MySQL, Redis, Kafka, Docker, Jenkins
    - AWS EC2, RDS, S3, CloudFront, ElastiCache, MSK, ECR

API ๋ฌธ์ : https://www.everyonepick.com/swagger-ui/index.html

ERD : https://www.erdcloud.com/d/DWhuXgteoYBQHhKzm

## System Architecture
<img width="1466" alt="everyonepick_system_architecture" src="https://user-images.githubusercontent.com/55652627/204117083-7f7f0bac-dec8-49c1-9279-c46559151b61.png">

## Git Convention
- ํ์ : `[PICK-1] <์๋ฌด๋ช>: <์์ ๋ด์ฉ>`

|์๋ฌด๋ช|๋ด์ฉ|
| :-----------------------------------: | :---------------------------------------: |
| **ADD** |   ์ฝ๋๋ ํ์คํธ, ์์ , ๋ฌธ์ ๋ฑ์ ์ถ๊ฐ   |
| **FIX** | ์ฌ๋ฐ๋ฅด์ง ์์ ๋์์ ๊ณ ์น ๊ฒฝ์ฐ |
| **REMOVE** |   ์ฝ๋์ ์ญ์ ๊ฐ ์์ ๋   |
| **UPDATE** |   ๋ฌธ์๋ ๋ฆฌ์์ค, ๋ผ์ด๋ธ๋ฌ๋ฆฌ๋ฑ์ ์์ , ์ถ๊ฐ, ๋ณด์   |
| **FEAT** |  ์๋ก์ด ๊ธฐ๋ฅ ์ถ๊ฐ   |
| **MODIFY** | ์ฃผ๋ก ๋ฌธ๋ฒ์ ์ค๋ฅ๋ ํ์์ ๋ณ๊ฒฝ, ์ด๋ฆ ๋ณ๊ฒฝ ๋ฑ   |
| REFACTOR |   ์ฝ๋์ ์ ๋ฉด์ ์ธ ์์    |
| DOCS |  ๋ฌธ์์ ๊ฐ์    |
| RENAME | ํ์ผ์ ์ด๋ฆ ๋ณ๊ฒฝ |
| TEST | TEST ์ฝ๋ ๊ด๋ จ   |
