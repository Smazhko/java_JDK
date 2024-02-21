package lect1_tictactoe;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Dictionary;
import java.util.Hashtable;

// МЕНЕДЖЕРЫ РАСПОЛОЖЕНИЯ
// https://java-online.ru/swing-layout.xhtml#managerLayoutsTest

public class SettingsWindow extends JFrame{
    private static final int WINDOW_WIDTH = 250;
    private static final int WINDOW_HEIGHT = 380;
    private static final int MIN_FIELD_SIZE = 3;
    private static final int MAX_FIELD_SIZE = 10;
    private int mode = 0;
    private int fieldSizeX = 4;
    private int fieldSizeY = 4;
    private int winLength = 3;

    JButton btnStart = new JButton("Начать новую игру");

    public SettingsWindow(GameWindow gameWindow){
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        this.setLocationRelativeTo(gameWindow);
        this.setLayout(new FlowLayout()); // менеджер расположения - сетка: 4 строки, 1 колонка

        setResizable(false);

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                gameWindow.startNewGame(mode, fieldSizeX, fieldSizeY, winLength);
                setVisible(false);
            }
        });
        btnStart.setSize(WINDOW_WIDTH, 32);
        btnStart.setAlignmentX(Component.CENTER_ALIGNMENT);

        // MODE PANEL
        JPanel modePanel = new JPanel(new GridLayout(3, 1));
        JLabel modeLabel = new JLabel("Выберите режим игры");
        JRadioButton rbutton1 = new JRadioButton("Игрок против компьютера");
        JRadioButton rbutton2 = new JRadioButton("Два игрока");
        rbutton1.setSelected(true);
        ButtonGroup btnGrp = new ButtonGroup();
        rbutton1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mode = 0;
            }
        });
        rbutton2.addActionListener(e -> mode = 1); // с помощью ЛЯМБДЫ
        btnGrp.add(rbutton1);
        btnGrp.add(rbutton2);
        modePanel.add(modeLabel);
        modePanel.add(rbutton1);
        modePanel.add(rbutton2);

        // FIELD SIZE PANEL =========================
        JPanel fieldSizePanel = new JPanel(new GridLayout(3, 1));
        JLabel sizeLbl = new JLabel("Размеры поля:");
        JSlider sizeXSlider = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, 4); // 3 и 10, 4 - мин и макс значения, текущее значение
        JSlider sizeYSlider = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, 4); // 3 и 10, 4 - мин и макс значения, текущее значение

        Dictionary<Integer, JLabel> labels = new Hashtable<>();
        for (int i = MIN_FIELD_SIZE; i <= MAX_FIELD_SIZE; i++) {
            labels.put(i, new JLabel("" + i));
        }

        sizeXSlider.setMinorTickSpacing(1);
        sizeXSlider.setPaintTicks(true);
        sizeXSlider.setPaintLabels(true);
        sizeXSlider.setSnapToTicks(true);
        sizeXSlider.setLabelTable(labels);

        sizeYSlider.setMinorTickSpacing(1);
        sizeYSlider.setPaintTicks(true);
        sizeYSlider.setPaintLabels(true);
        sizeYSlider.setSnapToTicks(true);
        sizeYSlider.setLabelTable(labels);

        fieldSizePanel.add(sizeLbl);
        fieldSizePanel.add(sizeXSlider);
        fieldSizePanel.add(sizeYSlider);

        // WIN LENGTH PANEL
        JPanel winLenPanel = new JPanel(new GridLayout(2, 1));
        JLabel winLenLbl = new JLabel("Выигрышная длина:");
        JSlider winLenSlider = new JSlider(MIN_FIELD_SIZE, MAX_FIELD_SIZE, MIN_FIELD_SIZE); // мин и макс значения, текущее значение

        winLenSlider.setMinorTickSpacing(1);
        winLenSlider.setPaintTicks(true);
        winLenSlider.setPaintLabels(true);
        winLenSlider.setSnapToTicks(true);

        winLenSlider.setLabelTable(labels);
        winLenSlider.setValue(3);

        winLenPanel.add(winLenLbl, BorderLayout.NORTH);
        winLenPanel.add(winLenSlider, BorderLayout.SOUTH);

        // ДОБАВЛЕНИЕ ПОВЕДЕНИЯ СЛАЙДЕРАМ =================================================

        ChangeListener chList = new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                fieldSizeX = sizeXSlider.getValue();
                fieldSizeY = sizeYSlider.getValue();
                sizeLbl.setText("Размеры поля: " + sizeXSlider.getValue() + " х " + sizeYSlider.getValue());

                int maxWinLength = Math.max(fieldSizeX, fieldSizeY);
                winLenSlider.setMaximum(maxWinLength);
                winLenSlider.repaint();

                if (winLenSlider.getValue() > maxWinLength) {
                    winLength = maxWinLength;
                    winLenLbl.setText("Выигрышная длина: " + winLength);
                }
            }
        };

        sizeXSlider.addChangeListener(chList);
        sizeYSlider.addChangeListener(chList);


        winLenSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                winLength = winLenSlider.getValue();
                winLenLbl.setText("Выигрышная длина: " + winLength);
            }
        });

        // =========================================================

        // JPanel mainPanel = new JPanel()
        add(modePanel);
        add(fieldSizePanel);
        add(winLenPanel);

        add(btnStart);
    }


}
