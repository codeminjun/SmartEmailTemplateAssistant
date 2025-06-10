# 📧 Smart Email Template Assistant

<div align="center">
  <img src="https://img.shields.io/badge/Java-11+-007396?style=for-the-badge&logo=java&logoColor=white" alt="Java">
  <img src="https://img.shields.io/badge/Swing-GUI-FF6B6B?style=for-the-badge" alt="Swing">
  <img src="https://img.shields.io/badge/Platform-Cross--platform-4EAA25?style=for-the-badge" alt="Platform">
  <img src="https://img.shields.io/badge/Gmail-Integration-EA4335?style=for-the-badge&logo=gmail&logoColor=white" alt="Gmail">
  <img src="https://img.shields.io/badge/License-MIT-yellow?style=for-the-badge" alt="License">
</div>

<div align="center">
  <h3>🚀 반복적인 이메일 작성 시간을 80% 단축시켜주는 스마트한 템플릿 관리 도구</h3>
</div>

## 📌 목차
- [소개](#-소개)
- [주요 기능](#-주요-기능)
- [시작하기](#-시작하기)
- [사용 방법](#-사용-방법)
- [프로젝트 구조](#-프로젝트-구조)
- [기술 스택](#-기술-스택)
- [기여하기](#-기여하기)


## 🎯 소개

Smart Email Template Assistant는 비즈니스 환경에서 반복적으로 작성되는 이메일을 효율적으로 관리하고 자동화하는 데스크탑 애플리케이션입니다. 

### 왜 필요한가요?
- 📧 매일 비슷한 형식의 이메일을 반복 작성하는데 지치셨나요?
- 🔄 수신자 이름이나 날짜를 매번 수정하는 것이 번거로우신가요?
- 📝 팀 전체가 일관된 형식의 이메일을 작성하길 원하시나요?
- ✉️ 작성한 이메일을 Gmail로 바로 발송하고 싶으신가요?

**Smart Email Template Assistant가 해결해드립니다!**

## ✨ 주요 기능

### 🗂️ 템플릿 관리
- **CRUD 기능**: 템플릿 생성, 조회, 수정, 삭제
- **카테고리 분류**: 문의, 감사, 사과, 안내 등 체계적 관리
- **원클릭 즐겨찾기**: 메인화면에서 별표 클릭으로 즐겨찾기 토글
- **즐겨찾기 관리**: 자주 사용하는 템플릿 빠른 접근
- **더블클릭 미리보기**: 빠른 템플릿 확인

### 🔄 스마트 변수 시스템
- **자동 변수 감지**: `{name}`, `{date}`, `{company}` 등
- **실시간 치환**: 입력과 동시에 결과 미리보기
- **날짜 자동 입력**: `{date}` 변수 자동 처리

### 📧 Gmail 이메일 발송 (v1.2.0 새 기능!)
- **직접 발송**: 템플릿에서 Gmail로 바로 이메일 발송
- **다중 수신자**: 여러 명에게 동시 발송 지원
- **앱 비밀번호**: 안전한 2단계 인증 지원
- **계정 정보 저장**: 편리한 재사용

### 🌙 다크모드 지원 (v1.2.0 새 기능!)
- **눈 보호**: 어두운 환경에서 편안한 사용
- **토글 전환**: 🌙/☀️ 버튼으로 즉시 전환
- **설정 저장**: 프로그램 재시작 시에도 유지
- **전체 UI 적용**: 모든 다이얼로그 다크모드 지원

### 🔍 검색 및 필터링
- **통합 검색**: 제목, 내용, 카테고리에서 검색
- **다중 필터**: 카테고리 + 즐겨찾기 조합 필터
- **실시간 결과**: 타이핑과 동시에 결과 표시

### 💾 데이터 영속성
- **자동 저장**: 모든 변경사항 자동 저장
- **안전한 보관**: 직렬화를 통한 데이터 보존
- **백업 복구**: 프로그램 재시작 시 자동 로드

## 🚀 시작하기

### 시스템 요구사항
- **Java**: JDK 11 이상
- **OS**: Windows 7+, macOS 10.12+, Linux
- **메모리**: 512MB RAM 이상
- **디스크**: 50MB 이상
- **네트워크**: Gmail 발송 기능 사용 시 인터넷 연결 필요

### 설치 방법

#### 1. 소스코드에서 빌드
```bash
# 저장소 클론
git clone https://github.com/codeminjun/SmartEmailTemplateAssistant.git
cd SmartEmailTemplateAssistant

# 라이브러리 포함하여 컴파일
javac -cp "lib/*:." -d out src/*.java src/model/*.java src/ui/*.java src/util/*.java

# 실행
java -cp "lib/*:out" Main
```

#### 2. JAR 파일 실행 (권장)
```bash
# JAR 파일 다운로드 후
java -jar SmartEmailTemplateAssistant.jar
```

### 필요 라이브러리
- **JavaMail API**: `javax.mail-1.6.2.jar` (Gmail 발송 기능)

## 📖 사용 방법

### 1. 템플릿 생성
```
1. [새 템플릿] 버튼 클릭
2. 제목: "프로젝트 문의 메일"
3. 카테고리: "문의" 선택 또는 새로 입력
4. 내용 작성:
   안녕하세요 {name}님,
   {project}에 대해 문의드립니다.
   {content}
   감사합니다.
5. [저장] 클릭
```

### 2. 템플릿 사용
```
1. 템플릿 목록에서 원하는 템플릿 더블클릭
2. 변수 입력:
   - name: 김철수
   - project: 웹사이트 리뉴얼
   - content: 일정 확인 부탁드립니다
3. [클립보드에 복사] 또는 [이메일 발송] 클릭
```

### 3. 즐겨찾기 관리 (v1.3.0 새 기능!)
```
1. 템플릿 목록에서 오른쪽 별표 클릭
   - ☆ (빈 별): 즐겨찾기 추가
   - ★ (금색 별): 즐겨찾기 제거
2. 쉬운 원클릭으로 즉시 전환
3. 수정 버튼 없이도 바로 변경 가능!
```

### 4. Gmail로 이메일 발송
```
1. 미리보기에서 [이메일 발송] 클릭
2. Gmail 계정 정보 입력:
   - Gmail 주소: your.email@gmail.com
   - 앱 비밀번호: 16자리 앱 비밀번호
3. 받는 사람, 제목 입력
4. [발송] 클릭
```

### 5. 다크모드 사용
```
1. 메인 화면 상단의 🌙 버튼 클릭
2. 다크모드 ↔️ 라이트모드 전환
3. 설정은 자동 저장됨
```

### 6. 검색 및 필터
- **검색**: 상단 검색창에 키워드 입력
- **카테고리 필터**: 드롭다운에서 카테고리 선택
- **즐겨찾기**: 체크박스로 즐겨찾기만 표시

## 📁 프로젝트 구조

```
SmartEmailTemplateAssistant/
├── src/
│   ├── Main.java                 # 애플리케이션 진입점
│   ├── model/
│   │   ├── EmailTemplate.java    # 템플릿 엔티티 클래스
│   │   └── TemplateManager.java  # 비즈니스 로직 및 데이터 관리
│   ├── ui/
│   │   ├── MainFrame.java        # 메인 윈도우 (다크모드 지원)
│   │   ├── TemplateDialog.java   # 템플릿 편집 다이얼로그
│   │   ├── PreviewDialog.java    # 미리보기 다이얼로그
│   │   └── EmailDialog.java      # 이메일 발송 다이얼로그
│   └── util/
│       └── EmailSender.java      # Gmail SMTP 이메일 발송
├── lib/
│   └── javax.mail-1.6.2.jar      # JavaMail 라이브러리
├── resources/
│   └── icon.png                  # 애플리케이션 아이콘
├── templates.dat                 # 템플릿 저장 파일 (자동 생성)
├── settings.properties           # 설정 파일 (다크모드 등)
├── email_credentials.properties  # 이메일 계정 정보 (선택적)
└── README.md
```

## 🛠 기술 스택

### 핵심 기술
- **언어**: Java 11
- **GUI**: Swing (크로스 플랫폼 지원)
- **데이터 저장**: Java Serialization
- **이메일**: JavaMail API (Gmail SMTP)
- **디자인 패턴**: MVC, Observer, Template Method

### 주요 기능별 기술
- **다크모드**: Custom Component Painting
- **실시간 미리보기**: DocumentListener
- **변수 치환**: Regular Expression
- **Gmail 연동**: SMTP with TLS/SSL
- **즐겨찾기 토글**: Custom Cell Renderer

## 🎨 스크린샷

### 라이트모드
- 깔끔한 흰색 배경의 기본 테마
- 명확한 테두리와 고대비 텍스트

### 다크모드
- 눈에 편안한 어두운 배경 (RGB: 30, 30, 30)
- 부드러운 색상 조합으로 장시간 사용 가능

## 🤝 기여하기

### 기여 방법
1. 이 저장소를 Fork 하세요
2. Feature 브랜치를 생성하세요 (`git checkout -b feature/AmazingFeature`)
3. 변경사항을 Commit 하세요 (`git commit -m 'Add some AmazingFeature'`)
4. 브랜치에 Push 하세요 (`git push origin feature/AmazingFeature`)
5. Pull Request를 열어주세요

### 코딩 컨벤션
- **Java 명명 규칙** 준수
- **JavaDoc** 주석 작성
- **들여쓰기**: 4 spaces
- **최대 줄 길이**: 120자

### 이슈 리포팅
버그를 발견하셨나요? [Issues](https://github.com/codeminjun/SmartEmailTemplateAssistant/issues)에 등록해주세요!

## 📅 변경 내역

### v1.3.0 (2025-06-10) - 최신 버전
- ✨ **새로운 기능**: 메인화면 원클릭 즐겨찾기 토글
- 🎆 **UI 개선**: 템플릿 목록에 클릭 가능한 별표 버튼 추가
- 🚀 **UX 향상**: 수정 대화상자 없이도 즐겨찾기 설정 가능
- 🎨 **디자인**: 즐겨찾기 상태에 따른 금색/회색 별표 표시

### v1.2.0 (2025.06.02)
- ✅ Gmail 이메일 발송 기능 추가
- ✅ 다크모드 완벽 지원
- ✅ 모든 다이얼로그 다크모드 적용
- ✅ 설정 자동 저장 기능
- ✅ 미리보기 버튼 제거 (더블클릭으로 통합)
- ✅ UI/UX 개선

### v1.1.0 (2025.06.02)
- ✅ 기본 템플릿 관리 기능
- ✅ 변수 치환 시스템
- ✅ 검색 및 필터링
- ✅ 데이터 영속성

## 📋 로드맵

### v1.4 (예정)
- [ ] 템플릿 내보내기/가져오기 (CSV, JSON)
- [ ] 단축키 지원
- [ ] 다국어 지원 (영어, 일본어, 중국어)
- [ ] 모바일 반응형 UI

### v2.0 (계획)
- [ ] 클라우드 동기화
- [ ] 팀 공유 기능
- [ ] AI 기반 템플릿 추천
- [ ] 이메일 클라이언트 플러그인

## 🐛 알려진 이슈

- macOS에서 한글 입력 시 가끔 버퍼링 발생 → 설정으로 해결
- Gmail 발송 시 앱 비밀번호 필요 (일반 비밀번호 사용 불가)
- 대용량 템플릿 (10,000개 이상) 로딩 시 지연 → 페이징 구현 예정

## 👨‍💻 개발자

- **개발자**: MinJun Kim
- **이메일**: koexmin@gmail.com
- **GitHub**: [@codeminjun](https://github.com/codeminjun)


---

<div align="center">
  <p>⭐ 이 프로젝트가 도움이 되셨다면 Star를 눌러주세요!</p>
  <p>🐛 버그를 발견하셨다면 Issue를 등록해주세요!</p>
  <p>🤝 개선사항이 있다면 Pull Request를 보내주세요!</p>
  <br>
  <p>Made with ❤️ by codeminjun </p>
</div>
