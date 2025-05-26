package ui;

import model.EmailTemplate;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 이메일 미리보기 다이얼로그
 */
public class PreviewDialog extends JDialog {
    private EmailTemplate template;
    private JTextArea previewArea;
    private JButton copyButton;
    private JButton closeButton;
    private Map<String, JTextField> variableFields;
    private JPanel variablePanel;

    public PreviewDialog(Frame parent, EmailTemplate template) {
        super(parent, "템플릿 미리보기", true);
        this.template = template;
        this.variableFields = new HashMap<>();

        initUI();
        updatePreview();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 상단 패널 (템플릿 정보)
        JPanel infoPanel = createInfoPanel();
        mainPanel.add(infoPanel, BorderLayout.NORTH);

        // 변수 입력 패널
        variablePanel = createVariablePanel();
        if (variablePanel != null) {
            mainPanel.add(variablePanel, BorderLayout.WEST);
        }

        // 중앙 패널 (미리보기)
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 하단 패널 (버튼)
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(700, 500);
        setLocationRelativeTo(getParent());
    }

    private JPanel createInfoPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        panel.setBorder(BorderFactory.createTitledBorder("템플릿 정보"));

        JLabel titleLabel = new JLabel("제목: " + template.getTitle());
        JLabel categoryLabel = new JLabel("카테고리: " + template.getCategory());

        titleLabel.setFont(new Font("맑은 고딕", Font.BOLD, 14));
        categoryLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

        panel.add(titleLabel);
        panel.add(Box.createHorizontalStrut(20));
        panel.add(categoryLabel);

        if (template.isFavorite()) {
            JLabel favoriteLabel = new JLabel("★");
            favoriteLabel.setFont(new Font("맑은 고딕", Font.BOLD, 16));
            favoriteLabel.setForeground(Color.ORANGE);
            panel.add(Box.createHorizontalStrut(10));
            panel.add(favoriteLabel);
        }

        return panel;
    }

    private JPanel createVariablePanel() {
        List<String> variables = template.extractVariables();
        if (variables.isEmpty()) {
            return null;
        }

        JPanel innerPanel = new JPanel();
        innerPanel.setLayout(new BoxLayout(innerPanel, BoxLayout.Y_AXIS));

        for (String variable : variables) {
            JPanel varPanel = new JPanel(new BorderLayout(5, 5));
            varPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

            JLabel label = new JLabel(variable + ":");
            label.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
            label.setPreferredSize(new Dimension(80, 25));

            JTextField field = new JTextField();
            field.setFont(new Font("맑은 고딕", Font.PLAIN, 14));

            // 특별한 변수 처리
            if ("date".equalsIgnoreCase(variable)) {
                field.setText(LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy년 MM월 dd일")));
            }

            varPanel.add(label, BorderLayout.WEST);
            varPanel.add(field, BorderLayout.CENTER);

            innerPanel.add(varPanel);
            variableFields.put(variable, field);

            // 실시간 미리보기 업데이트
            field.getDocument().addDocumentListener(new javax.swing.event.DocumentListener() {
                public void insertUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
                public void removeUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
                public void changedUpdate(javax.swing.event.DocumentEvent e) { updatePreview(); }
            });
        }

        // 스크롤 가능한 패널로 감싸기
        JScrollPane scrollPane = new JScrollPane(innerPanel);
        scrollPane.setPreferredSize(new Dimension(250, 0));

        // 외부 패널 생성
        JPanel outerPanel = new JPanel(new BorderLayout());
        outerPanel.setBorder(BorderFactory.createTitledBorder("변수 입력"));
        outerPanel.add(scrollPane, BorderLayout.CENTER);

        return outerPanel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createTitledBorder("미리보기"));

        previewArea = new JTextArea();
        previewArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        previewArea.setLineWrap(true);
        previewArea.setWrapStyleWord(true);
        previewArea.setEditable(false);
        previewArea.setBackground(new Color(245, 245, 245));

        JScrollPane scrollPane = new JScrollPane(previewArea);
        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);

        copyButton = createStyledButton("클립보드에 복사", new Color(52, 152, 219), Color.WHITE);
        closeButton = createStyledButton("닫기", new Color(240, 240, 240), Color.BLACK);

        panel.add(copyButton);
        panel.add(closeButton);

        // 이벤트 리스너
        copyButton.addActionListener(e -> copyToClipboard());
        closeButton.addActionListener(e -> dispose());

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
        button.setPreferredSize(new Dimension(140, 35));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));

        return button;
    }

    private void updatePreview() {
        Map<String, String> replacements = new HashMap<>();

        for (Map.Entry<String, JTextField> entry : variableFields.entrySet()) {
            String value = entry.getValue().getText();
            if (value.isEmpty()) {
                value = "{" + entry.getKey() + "}";
            }
            replacements.put(entry.getKey(), value);
        }

        String preview = template.replaceAllVariables(replacements);
        previewArea.setText(preview);
    }

    private void copyToClipboard() {
        String content = previewArea.getText();
        StringSelection selection = new StringSelection(content);
        Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(selection, null);

        JOptionPane.showMessageDialog(this,
                "클립보드에 복사되었습니다!",
                "복사 완료",
                JOptionPane.INFORMATION_MESSAGE);
    }
}