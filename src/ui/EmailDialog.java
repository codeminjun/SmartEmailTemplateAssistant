package ui;

import util.EmailSender;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Base64;

/**
 * 이메일 발송 다이얼로그
 */
public class EmailDialog extends JDialog {
    private JTextField toField;
    private JTextField subjectField;
    private JTextArea contentArea;
    private JTextField gmailField;
    private JPasswordField passwordField;
    private JButton sendButton;
    private JButton cancelButton;
    private JCheckBox saveCredentialsCheckBox;

    private EmailSender emailSender;
    private static final String CREDENTIALS_FILE = "email_credentials.properties";

    public EmailDialog(Frame parent, String prefilledContent) {
        super(parent, "이메일 발송", true);
        this.emailSender = new EmailSender();

        initUI();
        if (prefilledContent != null) {
            contentArea.setText(prefilledContent);
        }
        loadCredentials();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));

        // 상단 패널 (Gmail 설정)
        JPanel topPanel = createTopPanel();
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // 중앙 패널 (이메일 내용)
        JPanel centerPanel = createCenterPanel();
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 하단 패널 (버튼)
        JPanel bottomPanel = createBottomPanel();
        mainPanel.add(bottomPanel, BorderLayout.SOUTH);

        add(mainPanel);

        setSize(700, 600);
        setLocationRelativeTo(getParent());
    }

    private JPanel createTopPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("Gmail 계정 설정"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Gmail 주소
        JLabel gmailLabel = new JLabel("Gmail 주소:");
        gmailLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(gmailLabel, gbc);

        gmailField = new JTextField();
        gmailField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panel.add(gmailField, gbc);

        // 앱 비밀번호
        JLabel passwordLabel = new JLabel("앱 비밀번호:");
        passwordLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(passwordLabel, gbc);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panel.add(passwordField, gbc);

        // 계정 정보 저장 체크박스
        saveCredentialsCheckBox = new JCheckBox("계정 정보 저장");
        saveCredentialsCheckBox.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 2;
        panel.add(saveCredentialsCheckBox, gbc);

        // 도움말
        JLabel helpLabel = new JLabel("<html><font color='blue'>💡 Gmail 2단계 인증 후 앱 비밀번호를 생성하세요</font></html>");
        helpLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
        helpLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        helpLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                showHelpDialog();
            }
        });
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        panel.add(helpLabel, gbc);

        return panel;
    }

    private JPanel createCenterPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createTitledBorder("이메일 내용"));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 받는 사람
        JLabel toLabel = new JLabel("받는 사람:");
        toLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;
        panel.add(toLabel, gbc);

        toField = new JTextField();
        toField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        toField.setToolTipText("여러 명에게 보낼 때는 쉼표(,)로 구분하세요");
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        panel.add(toField, gbc);

        // 제목
        JLabel subjectLabel = new JLabel("제목:");
        subjectLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;
        panel.add(subjectLabel, gbc);

        subjectField = new JTextField();
        subjectField.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 1;
        panel.add(subjectField, gbc);

        // 내용
        JLabel contentLabel = new JLabel("내용:");
        contentLabel.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;
        gbc.anchor = GridBagConstraints.NORTH;
        panel.add(contentLabel, gbc);

        contentArea = new JTextArea();
        contentArea.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
        contentArea.setLineWrap(true);
        contentArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(contentArea);
        scrollPane.setPreferredSize(new Dimension(0, 200));
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 1;
        gbc.weighty = 1;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(scrollPane, gbc);

        return panel;
    }

    private JPanel createBottomPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        panel.setBackground(Color.WHITE);

        sendButton = createStyledButton("발송", new Color(46, 204, 113), Color.WHITE);
        cancelButton = createStyledButton("취소", new Color(240, 240, 240), Color.BLACK);

        panel.add(sendButton);
        panel.add(cancelButton);

        // 이벤트 리스너
        sendButton.addActionListener(e -> sendEmail());
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

    private void sendEmail() {
        String to = toField.getText().trim();
        String subject = subjectField.getText().trim();
        String content = contentArea.getText().trim();
        String gmail = gmailField.getText().trim();
        String password = new String(passwordField.getPassword());

        // 유효성 검사
        if (gmail.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Gmail 계정 정보를 입력해주세요.");
            return;
        }

        if (to.isEmpty()) {
            JOptionPane.showMessageDialog(this, "받는 사람 이메일을 입력해주세요.");
            return;
        }

        if (subject.isEmpty()) {
            JOptionPane.showMessageDialog(this, "제목을 입력해주세요.");
            return;
        }

        if (content.isEmpty()) {
            JOptionPane.showMessageDialog(this, "내용을 입력해주세요.");
            return;
        }

        // 이메일 주소 유효성 검사
        String[] recipients = to.split(",");
        for (String recipient : recipients) {
            if (!EmailSender.isValidEmail(recipient.trim())) {
                JOptionPane.showMessageDialog(this,
                        "잘못된 이메일 주소입니다: " + recipient.trim());
                return;
            }
        }

        // 발송 처리
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        sendButton.setEnabled(false);

        SwingWorker<Boolean, Void> worker = new SwingWorker<Boolean, Void>() {
            @Override
            protected Boolean doInBackground() {
                emailSender.setCredentials(gmail, password);
                return emailSender.sendEmailToMultiple(to, subject, content);
            }

            @Override
            protected void done() {
                setCursor(Cursor.getDefaultCursor());
                sendButton.setEnabled(true);

                try {
                    boolean success = get();
                    if (success) {
                        if (saveCredentialsCheckBox.isSelected()) {
                            saveCredentials();
                        }
                        JOptionPane.showMessageDialog(EmailDialog.this,
                                "이메일이 성공적으로 발송되었습니다!");
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(EmailDialog.this,
                                "이메일 발송에 실패했습니다.\n계정 정보와 앱 비밀번호를 확인해주세요.",
                                "발송 실패", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (Exception e) {
                    JOptionPane.showMessageDialog(EmailDialog.this,
                            "오류가 발생했습니다: " + e.getMessage(),
                            "오류", JOptionPane.ERROR_MESSAGE);
                }
            }
        };

        worker.execute();
    }

    private void showHelpDialog() {
        String helpText = "<html><body style='width: 400px; padding: 10px;'>" +
                "<h2>Gmail 앱 비밀번호 설정 방법</h2>" +
                "<ol>" +
                "<li><b>Gmail 계정 설정</b>으로 이동</li>" +
                "<li><b>보안</b> 탭 선택</li>" +
                "<li><b>2단계 인증</b> 활성화</li>" +
                "<li><b>앱 비밀번호</b> 생성 (앱 선택: 메일)</li>" +
                "<li>생성된 16자리 비밀번호를 복사하여 사용</li>" +
                "</ol>" +
                "<p><font color='red'>주의: 일반 Gmail 비밀번호가 아닌 앱 비밀번호를 사용해야 합니다!</font></p>" +
                "</body></html>";

        JOptionPane.showMessageDialog(this, helpText, "도움말", JOptionPane.INFORMATION_MESSAGE);
    }

    private void saveCredentials() {
        try {
            Properties props = new Properties();
            props.setProperty("gmail", gmailField.getText());
            // 비밀번호는 간단한 인코딩만 적용 (실제 환경에서는 더 안전한 방법 사용)
            String password = new String(passwordField.getPassword());
            props.setProperty("password", Base64.getEncoder().encodeToString(
                    password.getBytes()));

            props.store(new FileOutputStream(CREDENTIALS_FILE), "Email Credentials");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadCredentials() {
        File file = new File(CREDENTIALS_FILE);
        if (file.exists()) {
            try {
                Properties props = new Properties();
                props.load(new FileInputStream(file));

                gmailField.setText(props.getProperty("gmail", ""));
                String encodedPassword = props.getProperty("password", "");
                if (!encodedPassword.isEmpty()) {
                    passwordField.setText(new String(
                            Base64.getDecoder().decode(encodedPassword)));
                    saveCredentialsCheckBox.setSelected(true);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}