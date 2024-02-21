package lect1_tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class GameWindow extends JFrame {
    Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private static final int WINDOW_WIDTH = 450;
    private static final int WINDOW_HEIGHT = 500;
    private int WINDOW_POSX = (screenSize.width - WINDOW_WIDTH) / 2;
    private int WINDOW_POSY = (screenSize.height - WINDOW_HEIGHT) / 2;

    JButton btnStart = new JButton("Новая игра");
    JButton btnExit = new JButton("Выход");
    SettingsWindow settingsWindow;
    Map map;

    GameWindow(){
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocation(WINDOW_POSX, WINDOW_POSY);
        //setLocationRelativeTo(null);
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);
        setTitle("ИГРА КРЕСТИКИ-НОЛИКИ");
        setResizable(false);

        settingsWindow = new SettingsWindow(this);
        map = new Map();

        btnExit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });

        btnStart.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                settingsWindow.setVisible(true);
            }
        });

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(btnStart);
        panBottom.add(btnExit);

        add(panBottom, BorderLayout.SOUTH);

        add(map);

        setVisible(true);

    }

    public void startNewGame(int mode, int fieldSizeX, int fieldSizeY, int winLength){
        map.startNewGame(mode, fieldSizeX, fieldSizeY, winLength);

    }

}
