package model;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 템플릿 관리를 담당하는 클래스
 * 템플릿의 CRUD 및 파일 저장/로드 기능 제공
 */
public class TemplateManager {
    private static final String SAVE_FILE = "templates.dat";
    private List<EmailTemplate> templates;
    private Long nextId;
    private Set<String> categories;

    // 리스너 패턴을 위한 인터페이스
    public interface TemplateChangeListener {
        void onTemplatesChanged();
    }

    private List<TemplateChangeListener> listeners = new ArrayList<>();

    public TemplateManager() {
        this.templates = new ArrayList<>();
        this.categories = new HashSet<>();
        this.nextId = 1L;
        loadTemplates();
    }

    /**
     * 템플릿 추가
     */
    public void addTemplate(EmailTemplate template) {
        template.setId(nextId++);
        templates.add(template);
        categories.add(template.getCategory());
        saveTemplates();
        notifyListeners();
    }

    /**
     * 템플릿 수정
     */
    public void updateTemplate(EmailTemplate template) {
        for (int i = 0; i < templates.size(); i++) {
            if (templates.get(i).getId().equals(template.getId())) {
                templates.set(i, template);
                categories.add(template.getCategory());
                saveTemplates();
                notifyListeners();
                break;
            }
        }
    }

    /**
     * 템플릿 삭제
     */
    public void deleteTemplate(Long id) {
        templates.removeIf(t -> t.getId().equals(id));
        updateCategories();
        saveTemplates();
        notifyListeners();
    }

    /**
     * ID로 템플릿 조회
     */
    public EmailTemplate getTemplate(Long id) {
        return templates.stream()
                .filter(t -> t.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    /**
     * 모든 템플릿 조회
     */
    public List<EmailTemplate> getAllTemplates() {
        return new ArrayList<>(templates);
    }

    /**
     * 카테고리별 템플릿 조회
     */
    public List<EmailTemplate> getTemplatesByCategory(String category) {
        if (category == null || category.isEmpty() || "전체".equals(category)) {
            return getAllTemplates();
        }
        return templates.stream()
                .filter(t -> category.equals(t.getCategory()))
                .collect(Collectors.toList());
    }

    /**
     * 즐겨찾기 템플릿 조회
     */
    public List<EmailTemplate> getFavoriteTemplates() {
        return templates.stream()
                .filter(EmailTemplate::isFavorite)
                .collect(Collectors.toList());
    }

    /**
     * 키워드로 템플릿 검색
     */
    public List<EmailTemplate> searchTemplates(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            return getAllTemplates();
        }

        String lowerKeyword = keyword.toLowerCase();
        return templates.stream()
                .filter(t -> t.getTitle().toLowerCase().contains(lowerKeyword) ||
                        t.getContent().toLowerCase().contains(lowerKeyword) ||
                        t.getCategory().toLowerCase().contains(lowerKeyword))
                .collect(Collectors.toList());
    }

    /**
     * 즐겨찾기 상태 토글
     */
    public void toggleFavorite(Long id) {
        for (EmailTemplate template : templates) {
            if (template.getId().equals(id)) {
                template.setFavorite(!template.isFavorite());
                saveTemplates();
                notifyListeners();
                break;
            }
        }
    }

    /**
     * 모든 카테고리 조회
     */
    public Set<String> getAllCategories() {
        return new HashSet<>(categories);
    }

    /**
     * 템플릿을 파일로 저장
     */
    private void saveTemplates() {
        try (ObjectOutputStream oos = new ObjectOutputStream(
                new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(templates);
            oos.writeObject(nextId);
        } catch (IOException e) {
            System.err.println("템플릿 저장 실패: " + e.getMessage());
        }
    }

    /**
     * 파일에서 템플릿 로드
     */
    @SuppressWarnings("unchecked")
    private void loadTemplates() {
        File file = new File(SAVE_FILE);
        if (!file.exists()) {
            // 초기 샘플 템플릿 추가
            addSampleTemplates();
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(
                new FileInputStream(file))) {
            templates = (List<EmailTemplate>) ois.readObject();
            nextId = (Long) ois.readObject();
            updateCategories();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("템플릿 로드 실패: " + e.getMessage());
            addSampleTemplates();
        }
    }

    /**
     * 카테고리 목록 업데이트
     */
    private void updateCategories() {
        categories.clear();
        templates.forEach(t -> categories.add(t.getCategory()));
    }

    /**
     * 샘플 템플릿 추가
     */
    private void addSampleTemplates() {
        addTemplate(new EmailTemplate(
                "문의 메일",
                "안녕하세요 {name}님,\n\n{position}에 관해 문의드립니다.\n\n{content}\n\n감사합니다.\n{sender}",
                "문의"
        ));

        addTemplate(new EmailTemplate(
                "감사 인사",
                "{name}님께,\n\n{event}에 참석해 주셔서 진심으로 감사드립니다.\n\n{message}\n\n다시 한번 감사드립니다.\n{date}\n{sender}",
                "감사"
        ));

        addTemplate(new EmailTemplate(
                "사과 메일",
                "{name}님,\n\n{issue}에 대해 진심으로 사과드립니다.\n\n{explanation}\n\n다시는 이런 일이 없도록 하겠습니다.\n\n{sender}",
                "사과"
        ));
    }

    /**
     * 리스너 추가
     */
    public void addChangeListener(TemplateChangeListener listener) {
        listeners.add(listener);
    }

    /**
     * 리스너에게 변경 알림
     */
    private void notifyListeners() {
        listeners.forEach(TemplateChangeListener::onTemplatesChanged);
    }
}