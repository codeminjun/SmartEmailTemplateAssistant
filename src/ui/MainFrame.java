package ui;

import model.EmailTemplate;
import model.TemplateManager;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

/**
 * 메인 프레임 클래스
 */
public class MainFrame extends JFrame {
    private TemplateManager templateManager;
    private JList<EmailTemplate> templateList;
    private DefaultListModel<EmailTemplate> listModel;
    private JComboBox<String> categoryCombo;
    private JTextField searchField;
    private JButton searchButton;
    private JButton newButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton previewButton;
    private JCheckBox favoriteCheckBox;

    public MainFrame() {
        templateManager = new TemplateManager();
        initUI();
        loadTemplates();

        // 템플릿 변경 리스너 등록
        templateManager.addChangeListener(this::loadTemplates);
    }

    private void initUI() {
        setTitle("Smart Email Template Assistant");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 메인 패널
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));

        // 상단 패널 (검색 및 필터)
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙 패널 (템플릿 목록)
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 하단 패널 (버튼)
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        // 창 크기 설정
        setSize(800, 600);
        setLocationRelativeTo(null);

        // 아이콘 설정
        try {
            setIconImage(new ImageIcon(getClass().getResource("/icon.png")).getImage());
        } catch (Exception e) {
            // 아이콘이 없을 경우 무시
        }
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createTitledBorder("검색 및 필터"));

        // 검색 패널
        JPanel searchPanel = new JPanel(new BorderLayout(5, 0));
        searchField = new JTextField();
        searchField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        searchButton = new JButton("검색");
        searchButton.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        searchPanel.add(searchField, BorderLayout.CENTER);
        searchPanel.add(searchButton, BorderLayout.EAST);

        // 필터 패널
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        JLabel categoryLabel = new JLabel("카테고리:");
        categoryLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        categoryCombo = new JComboBox<>();
        categoryCombo.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        categoryCombo.setPreferredSize(new Dimension(150, 30));

        favoriteCheckBox = new JCheckBox("즐겨찾기만 보기");
        favoriteCheckBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        filterPanel.add(categoryLabel);
        filterPanel.add(categoryCombo);
        filterPanel.add(favoriteCheckBox);

        panel.add(searchPanel, BorderLayout.NORTH);
        panel.add(filterPanel, BorderLayout.SOUTH);

        // 이벤트 리스너
        searchButton.addActionListener(e -> searchTemplates());
        searchField.addActionListener(e -> searchTemplates());
        categoryCombo.addActionListener(e -> filterTemplates());
        favoriteCheckBox.addActionListener(e -> filterTemplates());

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("템플릿 목록"));

        // 템플릿 리스트
        listModel = new DefaultListModel<>();
        templateList = new JList<>(listModel);
        templateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        templateList.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        templateList.setCellRenderer(new TemplateListCellRenderer());

        // 더블클릭 시 미리보기
        templateList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    showPreview();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(templateList);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);

        newButton = createStyledButton("새 템플릿", new Color(52, 152, 219), Color.WHITE);
        editButton = createStyledButton("수정", new Color(240, 240, 240), Color.BLACK);
        deleteButton = createStyledButton("삭제", new Color(231, 76, 60), Color.WHITE);
        previewButton = createStyledButton("미리보기", new Color(240, 240, 240), Color.BLACK);

        panel.add(newButton);
        panel.add(editButton);
        panel.add(deleteButton);
        panel.add(previewButton);

        // 이벤트 리스너
        newButton.addActionListener(e -> showTemplateDialog(null));
        editButton.addActionListener(e -> editTemplate());
        deleteButton.addActionListener(e -> deleteTemplate());
        previewButton.addActionListener(e -> showPreview());

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

    private void loadTemplates() {
        SwingUtilities.invokeLater(() -> {
            // 카테고리 로드
            updateCategories();

            // 템플릿 로드
            filterTemplates();
        });
    }

    private void updateCategories() {
        String selected = (String) categoryCombo.getSelectedItem();
        categoryCombo.removeAllItems();
        categoryCombo.addItem("전체");

        for (String category : templateManager.getAllCategories()) {
            categoryCombo.addItem(category);
        }

        if (selected != null) {
            categoryCombo.setSelectedItem(selected);
        }
    }

    private void filterTemplates() {
        listModel.clear();

        List<EmailTemplate> templates;
        if (favoriteCheckBox.isSelected()) {
            templates = templateManager.getFavoriteTemplates();
        } else {
            String category = (String) categoryCombo.getSelectedItem();
            templates = templateManager.getTemplatesByCategory(category);
        }

        for (EmailTemplate template : templates) {
            listModel.addElement(template);
        }
    }

    private void searchTemplates() {
        String keyword = searchField.getText().trim();
        listModel.clear();

        List<EmailTemplate> templates = templateManager.searchTemplates(keyword);
        for (EmailTemplate template : templates) {
            listModel.addElement(template);
        }
    }

    private void showTemplateDialog(EmailTemplate template) {
        TemplateDialog dialog = new TemplateDialog(this, template, templateManager);
        dialog.setVisible(true);
    }

    private void editTemplate() {
        EmailTemplate selected = templateList.getSelectedValue();
        if (selected != null) {
            showTemplateDialog(selected);
        } else {
            JOptionPane.showMessageDialog(this, "수정할 템플릿을 선택해주세요.");
        }
    }

    private void deleteTemplate() {
        EmailTemplate selected = templateList.getSelectedValue();
        if (selected != null) {
            int result = JOptionPane.showConfirmDialog(this,
                    "정말 삭제하시겠습니까?", "확인",
                    JOptionPane.YES_NO_OPTION);

            if (result == JOptionPane.YES_OPTION) {
                templateManager.deleteTemplate(selected.getId());
            }
        } else {
            JOptionPane.showMessageDialog(this, "삭제할 템플릿을 선택해주세요.");
        }
    }

    private void showPreview() {
        EmailTemplate selected = templateList.getSelectedValue();
        if (selected != null) {
            PreviewDialog dialog = new PreviewDialog(this, selected);
            dialog.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(this, "미리볼 템플릿을 선택해주세요.");
        }
    }

    /**
     * 커스텀 리스트 셀 렌더러
     */
    private class TemplateListCellRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {

            JLabel label = (JLabel) super.getListCellRendererComponent(
                    list, value, index, isSelected, cellHasFocus);

            if (value instanceof EmailTemplate) {
                EmailTemplate template = (EmailTemplate) value;
                String text = String.format("%s%s [%s]",
                        template.isFavorite() ? "★ " : "",
                        template.getTitle(),
                        template.getCategory());
                label.setText(text);
            }

            return label;
        }
    }
}