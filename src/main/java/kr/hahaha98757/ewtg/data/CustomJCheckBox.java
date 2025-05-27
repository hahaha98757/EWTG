package kr.hahaha98757.ewtg.data;

import kr.hahaha98757.ewtg.utils.SelectedEventListener;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class CustomJCheckBox extends JCheckBox {
    private boolean toggledByMethod = false;

    public CustomJCheckBox(String text) {
        super(text);
    }


    @Override
    public void setSelected(boolean b) {
        toggledByMethod = true;
        super.setSelected(b);
        toggledByMethod = false;
    }

    @Deprecated
    @Override
    public void addItemListener(ItemListener l) {
        throw new RuntimeException("It is not allowed. Use addUserItemListener method.");
    }

    public void addUserItemListener(SelectedEventListener listener) {
        super.addItemListener(event -> {
            if (!toggledByMethod) listener.onSelect(event.getStateChange() == ItemEvent.SELECTED);
        });
    }
}