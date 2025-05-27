package kr.hahaha98757.ewtg.utils;

import kr.hahaha98757.ewtg.data.CustomJCheckBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class AbstractJPanel extends JPanel {

    protected JButton createJButton(int x, int y, int width, int height, ImageIcon icon, ImageIcon pressedIcon, ActionListener listener) {
        var btn = new JButton();
        btn.setBorderPainted(false);
        btn.setContentAreaFilled(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBounds(x, y, width, height);
        btn.setIcon(icon);
        btn.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent event) {
                btn.setIcon(pressedIcon);
            }

            @Override
            public void mouseExited(MouseEvent event) {
                btn.setIcon(icon);
            }
        });
        if (listener != null) btn.addActionListener(listener);
        return btn;
    }

    protected JLabel createJLabel(String text, int  x, int y, int width, int height, boolean isCenter) {
        var label = new JLabel(text);
        label.setBounds(x, y, width, height);
        if (isCenter) label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setFont(Tools.checkFont);
        return label;
    }

    protected JLabel createJLabel(String text, int x, int y, int width, int height) {
        return createJLabel(text, x, y, width, height, false);
    }

    protected JTextArea createJTextArea(String text, int x, int y, int width, int height) {
        var textArea = new JTextArea(text);
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setEditable(false);
        textArea.setFocusable(false);
        textArea.setBorder(null);
        textArea.setBounds(x, y, width, height);
        textArea.setFont(Tools.textAreaFont);
        return textArea;
    }

    protected CustomJCheckBox createJCheckBox(String text, int x, int y, int width, int height, boolean defaultValue, SelectedEventListener listener) {
        var checkBox = new CustomJCheckBox(text);
        checkBox.setFont(Tools.checkFont);
        checkBox.setBounds(x, y, width, height);
        checkBox.setBorderPainted(false);
        checkBox.setContentAreaFilled(false);
        checkBox.setFocusPainted(false);
        checkBox.setSelected(defaultValue);
        if (listener != null) checkBox.addUserItemListener(listener);
        return checkBox;
    }

    protected CustomJCheckBox createJCheckBox(String text, int x, int y, int width, int height, boolean defaultValue) {
        return createJCheckBox(text, x, y, width, height, defaultValue, null);
    }

    protected JSpinner createJSpinner(int x, int y, int width, int height, int defaultValue, int min, int max, int step, ChangeEventListener listener) {
        var spinner = new JSpinner(new SpinnerNumberModel(defaultValue, min, max, step));
        spinner.setBounds(x, y, width, height);
        spinner.setFont(Tools.checkFont);
        if (listener != null) spinner.addChangeListener(event -> listener.onChange((int) ((JSpinner) event.getSource()).getValue()));
        return spinner;
    }

    protected JSpinner createJSpinner(int x, int y, int width, int height, int defaultValue, int min, int max, int step) {
        return createJSpinner(x, y, width, height, defaultValue, min, max, step, null);
    }
}
