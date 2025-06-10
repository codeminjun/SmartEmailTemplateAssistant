# Git 커밋 가이드

## 1. 프로젝트 디렉터리로 이동
```bash
cd /Users/kimminjun/Desktop/SmartEmailTemplateAssistant
```

## 2. 변경사항 확인
```bash
git status
```

## 3. 변경된 파일들 스테이징
```bash
# 모든 변경사항 추가
git add .

# 또는 개별 파일 추가
git add src/model/TemplateManager.java
git add src/ui/MainFrame.java
git add README.md
```

## 4. 커밋 메시지 작성
```bash
git commit -m "✨ feat: 메인화면 원클릭 즐겨찾기 토글 기능 추가

- 템플릿 목록에 클릭 가능한 별표 버튼 구현
- TemplateManager에 toggleFavorite() 메서드 추가
- 별표 클릭으로 즐겨찾기 상태 즉시 토글
- 수정 대화상자 없이도 즐겨찾기 변경 가능
- UI: 즐겨찾기(★ 금색) vs 일반(☆ 회색) 시각적 구분
- README.md 업데이트: 새 기능 사용법 및 변경내역 추가

Resolves: 메인화면에서 즐겨찾기 설정이 불편한 문제
"
```

## 5. 원격 저장소에 푸시
```bash
# main 브랜치에 푸시
git push origin main

# 또는 master 브랜치에 푸시 (저장소 설정에 따라)
git push origin master
```

## 6. (선택사항) 태그 생성 및 푸시
```bash
# v1.1.0 태그 생성
git tag -a v1.1.0 -m "Release v1.1.0: 원클릭 즐겨찾기 기능"

# 태그 푸시
git push origin v1.1.0
```

## 7. GitHub에서 확인
- GitHub 저장소로 이동해서 커밋이 올라갔는지 확인
- README.md가 업데이트되었는지 확인
- 필요시 Release 노트 작성

## 🎯 커밋 컨벤션
이 프로젝트는 다음 커밋 컨벤션을 사용합니다:
- ✨ feat: 새로운 기능
- 🐛 fix: 버그 수정
- 📝 docs: 문서 수정
- 🎨 style: 코드 포맷팅
- ♻️ refactor: 코드 리팩토링
- ⚡ perf: 성능 개선
- ✅ test: 테스트 추가/수정
