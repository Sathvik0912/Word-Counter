package wordcounter;

import javax.swing.*;
import java.awt.*;
import java.util.regex.*;

public class WordCounter {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WordCounter().createAndShowGUI());
    }

    void createAndShowGUI() {
        JFrame frame = new JFrame("Word Counter");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);

        JTabbedPane tabs = new JTabbedPane();
        tabs.addTab("Tab 1", createTabPanel());

        frame.setLayout(new BorderLayout());
        frame.add(tabs, BorderLayout.CENTER);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    JPanel createTabPanel() {
        JPanel panel = new JPanel(new BorderLayout(5,5));
        JTextArea area = new JTextArea(10, 40);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        area.setFocusTraversalKeysEnabled(false);
        area.getInputMap().put(KeyStroke.getKeyStroke("TAB"), "none");
        JScrollPane scroll = new JScrollPane(area);
        JButton search = new JButton("Search");
        JLabel result = new JLabel("Enter text and click Search");
        search.addActionListener(e -> {
            String text = area.getText();
            if(text.trim().isEmpty())
                result.setText("No text entered.");
            else
                result.setText("Number of words: " + countWords(text));
        });

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bottom.add(search);
        bottom.add(result);
        panel.add(scroll, BorderLayout.CENTER);
        panel.add(bottom, BorderLayout.SOUTH);
        return panel;
    }

    int countWords(String text) {
        text = text.replaceAll("-\\s*\\n\\s*", "");
        Pattern p = Pattern.compile("\\([^)]*\\)|\\{[^}]*\\}|\\[[^]]*\\]");
        Matcher m = p.matcher(text);
        java.util.List<String> brackets = new java.util.ArrayList<>();
        while(m.find()) brackets.add(m.group());
        String withoutBrackets = text.replaceAll("\\([^)]*\\)|\\{[^}]*\\}|\\[[^]]*\\]", " ");
        String[] words = withoutBrackets.trim().split("\\s+");
        int count = 0;
        for(String w : words) if(!w.isEmpty()) count++;
        return brackets.size() + count;
    }
}
