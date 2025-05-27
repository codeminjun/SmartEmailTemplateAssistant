import ui.MainFrame;
import javax.swing.*;
import java.awt.*;

/**
 * Smart Email Template Assistant 실행 클래스
 */
public class Main {
    public static void main(String[] args) {
        // 한글 입력 문제 해결을 위한 설정
        System.setProperty("apple.awt.UIElement", "false");
        System.setProperty("apple.awt.graphics.EnableQ2DX", "true");
        System.setProperty("apple.awt.graphics.UseQuartz", "true");

        // Mac OS 설정
        System.setProperty("apple.laf.useScreenMenuBar", "true");
        System.setProperty("apple.awt.application.name", "Smart Email Template Assistant");

        // 한글 입력을 위한 Input Method 설정
        System.setProperty("awt.im.style", "on-the-spot");

        // Look and Feel 설정
        try {
            // Mac에서는 시스템 Look and Feel 사용
            String osName = System.getProperty("os.name").toLowerCase();
            if (osName.contains("mac")) {
                // Mac OS의 경우 Aqua Look and Feel 사용
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());

                // 한글 입력 개선을 위한 설정
                UIManager.put("TextArea.font", new Font("맑은 고딕", Font.PLAIN, 14));
                UIManager.put("TextField.font", new Font("맑은 고딕", Font.PLAIN, 14));
                UIManager.put("TextPane.font", new Font("맑은 고딕", Font.PLAIN, 14));

                // 버튼 렌더링 설정
                UIManager.put("Button.foreground", Color.BLACK);
                UIManager.put("Button.select", Color.LIGHT_GRAY);
                UIManager.put("Button.focus", Color.LIGHT_GRAY);
                UIManager.put("Button.border", BorderFactory.createEmptyBorder(4, 8, 4, 8));
            } else {
                // Windows나 Linux의 경우
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            }

            // 폰트 안티앨리어싱 설정
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");

        } catch (Exception e) {
            e.printStackTrace();
        }

        // UI 스레드에서 실행
        SwingUtilities.invokeLater(() -> {
            MainFrame frame = new MainFrame();
            frame.setVisible(true);
        });
    }
}