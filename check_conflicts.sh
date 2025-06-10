#!/bin/bash

echo "=== Git 상태 확인 ==="
git status

echo ""
echo "=== 최근 커밋 로그 ==="
git log --oneline -5

echo ""
echo "=== 원격 브랜치와 비교 ==="
git log --oneline main..origin/main

echo ""
echo "=== 충돌 파일 확인 ==="
git diff --name-only --diff-filter=U
