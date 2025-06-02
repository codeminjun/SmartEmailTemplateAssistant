package ui;

import model.EmailTemplate;
import model.TemplateManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

/**
 * 템플릿 추가/수정 다이얼로그
 */
public class TemplateDialog extends JDialog {
    private JTextField titleField;
    private JTextArea contentArea;
    private JComboBox<String> categoryCombo;
    private JCheckBox favoriteCheckBox;
    private JButton saveButton;
    private JButton cancelButton;

    private EmailTemplate template;
    private TemplateManager templateManager;
    private boolean isEditMode;

    // 다크모드 관련
    private boolean isDarkMode = false;
    private static final Color DARK_BG = new Color(30, 30, 30);
    private static final Color DARK_PANEL = new Color(45, 45, 45);
    private static final Color DARK_TEXT = new Color(220, 220, 220);
    private static final Color DARK_BORDER = new Color(60, 60, 60);

    public TemplateDialog(Frame parent, EmailTemplate template, TemplateManager manager) {
        super(parent, true);
        this.template = template;
        this.templateManager = manager;
        this.isEditMode = (template != null);

        // 부모 프레임에서 다크모드 상태 가져오기
        if (parent instanceof MainFrame) {
            this.isDarkMode = ((MainFrame) parent).isDarkMode();
        }

        initUI();
        if (isEditMode) {
            loadTemplateData();
        }

        // 다크모드 적용
        if (isDarkMode) {
            applyDarkMode();
        }
    }

    public void setDarkMode(boolean darkMode) {
        this.isDarkMode = darkMode;
        if (darkMode) {
            applyDarkMode();
        }
    }

    private void initUI() {
        setTitle(isEditMode ? "템플릿 수정" : "새 템플릿");
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 상단 패널 (제목, 카테고리, 즐겨찾기)
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙 패널 (내용)
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 하단 패널 (버튼)
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(600, 500);
        setLocationRelativeTo(getParent());
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        if (isDarkMode) {
            panel.setBackground(DARK_BG);
        }

        // 제목
        JLabel titleLabel = new JLabel("제목:");
        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        if (isDarkMode) {
            titleLabel.setForeground(DARK_TEXT);
        }
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(titleLabel, gbc);

        titleField = new JTextField();
        titleField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        if (isDarkMode) {
            titleField.setBackground(DARK_PANEL);
            titleField.setForeground(DARK_TEXT);
            titleField.setCaretColor(DARK_TEXT);
            titleField.setBorder(BorderFactory.createLineBorder(DARK_BORDER));
        }
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        gbc.gridwidth = 2;
        panel.add(titleField, gbc);

        // 카테고리
        JLabel categoryLabel = new JLabel("카테고리:");
        categoryLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        if (isDarkMode) {
            categoryLabel.setForeground(DARK_TEXT);
        }
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        gbc.gridwidth = 1;
        panel.add(categoryLabel, gbc);

        categoryCombo = new JComboBox<>();
        categoryCombo.setEditable(true);
        categoryCombo.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        if (isDarkMode) {
            categoryCombo.setBackground(DARK_PANEL);
            categoryCombo.setForeground(DARK_TEXT);
        }

        // 기존 카테고리 추가
        for (String category : templateManager.getAllCategories()) {
            categoryCombo.addItem(category);
        }

        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panel.add(categoryCombo, gbc);

        // 즐겨찾기
        favoriteCheckBox = new JCheckBox("즐겨찾기");
        favoriteCheckBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        if (isDarkMode) {
            favoriteCheckBox.setBackground(DARK_BG);
            favoriteCheckBox.setForeground(DARK_TEXT);
        }
        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(favoriteCheckBox, gbc);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(isDarkMode ? DARK_BORDER : Color.GRAY),
                "내용",
                javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION,
                javax.swing.border.TitledBorder.DEFAULT_POSITION,
                new Font("맑은 고딕", Font.PLAIN, 12),
                isDarkMode ? DARK_TEXT : Color.BLACK
        ));

        if (isDarkMode) {
            panel.setBackground(DARK_BG);
        }

        contentArea = new JTextArea();
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        if (isDarkMode) {
            contentArea.setBackground(DARK_PANEL);
            contentArea.setForeground(DARK_TEXT);
            contentArea.setCaretColor(DARK_TEXT);
        }

        JScrollPane scrollPane = new JScrollPane(contentArea);
        if (isDarkMode) {
            scrollPane.setBorder(BorderFactory.createLineBorder(DARK_BORDER));
            scrollPane.getViewport().setBackground(DARK_PANEL);
        }
        panel.add(scrollPane, BorderLayout.CENTER);

        // 변수 안내 패널
        JPanel infoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        infoPanel.setBackground(isDarkMode ? DARK_PANEL : new Color(240, 240, 240));
        infoPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        JLabel infoLabel = new JLabel("💡 변수 사용: {name}, {date}, {position} 등의 형태로 입력하세요.");
        infoLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        if (isDarkMode) {
            infoLabel.setForeground(DARK_TEXT);
        }
        infoPanel.add(infoLabel);

        panel.add(infoPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(isDarkMode ? DARK_BG : Color.WHITE);

        if (isDarkMode) {
            saveButton = createStyledButton("저장", new Color(39, 174, 96), Color.WHITE);
            cancelButton = createStyledButton("취소", new Color(60, 60, 60), DARK_TEXT);
        } else {
            saveButton = createStyledButton("저장", new Color(46, 204, 113), Color.WHITE);
            cancelButton = createStyledButton("취소", new Color(240, 240, 240), Color.BLACK);
        }

        panel.add(saveButton);
        panel.add(cancelButton);

        // 이벤트 리스너
        saveButton.addActionListener(e -> saveTemplate());
        cancelButton.addActionListener(e -> dispose());

        return panel;
    }

    private JButton createStyledButton(String text, Color bgColor, Color fgColor) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                if (getModel().isPressed()) {
                    g2.setColor(bgColor.darker());
                } else if (getModel().isRollover()) {
                    g2.setColor(bgColor.brighter());
                } else {
                    g2.setColor(bgColor);
                }
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);

                // 텍스트 그리기
                g2.setColor(fgColor);
                g2.setFont(getFont());
                FontMetrics fm = g2.getFontMetrics();
                int x = (getWidth() - fm.stringWidth(getText())) / 2;
                int y = (getHeight() + fm.getAscent()) / 2 - 2;
                g2.drawString(getText(), x, y);
                g2.dispose();
            }
        };

        button.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        button.setPreferredSize(new Dimension(100, 35));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void loadTemplateData() {
        titleField.setText(template.getTitle());
        contentArea.setText(template.getContent());
        categoryCombo.setSelectedItem(template.getCategory());
        favoriteCheckBox.setSelected(template.isFavorite());
    }

    private void saveTemplate() {
        String title = titleField.getText().trim();
        String content = contentArea.getText().trim();
        String category = (String) categoryCombo.getSelectedItem();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목을 입력해주세요.");
            return;
        }

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "내용을 입력해주세요.");
            return;
        }

        if (category == null || category.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "카테고리를 입력해주세요.");
            return;
        }

        if (isEditMode) {
            template.setTitle(title);
            template.setContent(content);
            template.setCategory(category.trim());
            template.setFavorite(favoriteCheckBox.isSelected());
            templateManager.updateTemplate(template);
        } else {
            EmailTemplate newTemplate = new EmailTemplate(title, content, category.trim());
            newTemplate.setFavorite(favoriteCheckBox.isSelected());
            templateManager.addTemplate(newTemplate);
        }

        dispose();
    }

    private void applyDarkMode() {
        getContentPane().setBackground(DARK_BG);
        if (getContentPane() instanceof JPanel) {
            ((JPanel) getContentPane()).setBorder(new EmptyBorder(15, 15, 15, 15));
            applyDarkModeToComponent(getContentPane());
        }
    }

    private void applyDarkModeToComponent(Container container) {
        for (Component comp : container.getComponents()) {
            if (comp instanceof JPanel) {
                comp.setBackground(DARK_BG);
            } else if (comp instanceof JLabel) {
                comp.setForeground(DARK_TEXT);
            }

            if (comp instanceof Container) {
                applyDarkModeToComponent((Container) comp);
            }
        }
    }
}