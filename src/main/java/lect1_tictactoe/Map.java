package lect1_tictactoe;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;

public class Map extends JPanel {
    private static final Random RANDOM = new Random();
    private static final int HUMAN_DOT = 1;
    private static final int AI_DOT = 2;
    private static final int EMPTY_DOT = 0;

    private int gameStateType;
    private static final int STATE_GAME = 0;
    private static final int STATE_WIN_HUMAN = 1;
    private static final int STATE_WIN_AI = 2;
    private static final int STATE_DRAW = 3;

    private static final String MSG_WIN_HUMAN = "Победил игрок!";
    private static final String MSG_WIN_AI = "Победил компьютер!";
    private static final String MSG_DRAW = "Ничья!";

    private int width, height, cellWidth, cellHeight;
    private int mode, fieldSizeX, fieldSizeY, winLen;
    private int[][] field;
    private boolean gameWork;

    Map() {
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if (gameWork) {
                    update(e);
                }
            }
        });
    }

    private void initMap() {
        field = new int[fieldSizeY][fieldSizeX];

        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                field[i][j] = EMPTY_DOT;
            }
        }
    }

    void startNewGame(int mode, int sizeX, int sizeY, int winLen) {
        this.mode = mode;
        this.fieldSizeX = sizeX;
        this.fieldSizeY = sizeY;
        this.winLen = winLen;

        initMap();
        gameWork = true;
        gameStateType = STATE_GAME;

        repaint();
    }

    private void update(MouseEvent mouseEvent) {
        int x = mouseEvent.getX() / cellWidth;
        int y = mouseEvent.getY() / cellHeight;
        if (!isValidCell(x, y) || !isEmptyCell(x, y)) {
            return;
        }
        field[y][x] = HUMAN_DOT;
        if (checkEndGame(HUMAN_DOT, STATE_WIN_HUMAN)) {
            return;
        }
        aiTurn();
        repaint();
        checkEndGame(AI_DOT, STATE_WIN_AI);
    }

    private void testBoard(){
        for (int i = 0; i < 3; i++) {
            System.out.println(Arrays.toString(field[i]));
        }
        System.out.println();
    }

    private boolean isValidCell(int x, int y) {
        return x >= 0 && x < fieldSizeX && y >= 0 && y < fieldSizeY;
    }

    private boolean isEmptyCell(int x, int y) {
        return field[y][x] == EMPTY_DOT;
    }

    private void aiTurn() {
        int x, y;
        do {
            x = RANDOM.nextInt(fieldSizeX);
            y = RANDOM.nextInt(fieldSizeY);
        } while (!isEmptyCell(x, y));
        field[y][x] = AI_DOT;
    }

    private boolean isMapFull() {
        for (int i = 0; i < fieldSizeY; i++) {
            for (int j = 0; j < fieldSizeX; j++) {
                if (field[i][j] == EMPTY_DOT) {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean checkEndGame(int dot, int gameOverType) {
        if (checkWin(dot)) {
            this.gameStateType = gameOverType;
            repaint();
            return true;
        } else if (isMapFull()) {
            this.gameStateType = STATE_DRAW;
            repaint();
            return true;
        }
        return false;
    }

    private boolean checkWin(int dot){
        for (int i = 0; i < fieldSizeX; i++) {
            for (int j = 0; j < fieldSizeY; j++) {
                if (checkLine(i, j, 1, 0, winLen, dot)) return true;
                if (checkLine(i, j, 1, 1, winLen, dot)) return true;
                if (checkLine(i, j, 0, 1, winLen, dot)) return true;
                if (checkLine(i, j, 1, -1, winLen, dot)) return true;
            }
        }
        return false;
    }

    private boolean checkLine(int x, int y, int vx, int vy, int len, int dot){
        int far_x = x + (len - 1) * vx;
        int far_y = y + (len - 1) * vy;
        if (!isValidCell(far_x, far_y)){
            return false;
        }
        for (int i = 0; i < len; i++) {
            if (field[y + i * vy][x + i * vx] != dot){
                return false;
            }
        }
        return true;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (gameWork) {
            render(g);
        }
    }

    private void render(Graphics gr) {
        Graphics2D g = (Graphics2D) gr;
        width = getWidth();
        height = getHeight();
        cellWidth = width / fieldSizeX;
        cellHeight = height / fieldSizeY;

        int dotSize = Math.min(cellWidth, cellHeight) - 2 * 10;
        int paddingX = (cellWidth - dotSize) / 2;
        int paddingY = (cellHeight - dotSize) / 2;

        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        g.setStroke(new BasicStroke(2));
        g.setColor(Color.BLACK);
        for (int w = 0; w < fieldSizeX; w++) {
            int x = w * cellWidth;
            g.drawLine(x, 0, x, height);
        }
        for (int h = 0; h < fieldSizeY; h++) {
            int y = h * cellHeight;
            g.drawLine(0, y, width, y);
        }


        g.setStroke(new BasicStroke(5));

        for (int y = 0; y < fieldSizeY; y++) {
            for (int x = 0; x < fieldSizeX; x++) {
                if (field[y][x] == EMPTY_DOT){
                    continue;
                }
                if (field[y][x] == HUMAN_DOT) {
                    g.setColor(Color.BLUE);
                    g.drawLine(x * cellWidth + paddingX, y * cellHeight + paddingY,
                            (x + 1) * cellWidth - paddingX, (y + 1) * cellHeight - paddingY);
                    g.drawLine(x * cellWidth + paddingX, (y + 1) * cellHeight - paddingY,
                            (x + 1) * cellWidth - paddingX, y * cellHeight + paddingY);
                } else if (field[y][x] == AI_DOT) {
                    g.setColor(Color.RED);
                    g.drawOval(x * cellWidth + paddingX, y * cellHeight + paddingY,
                            dotSize, dotSize);
                } else {
                    throw new RuntimeException("unchecked value " + field[y][x] +
                            " in cell: x=" + x + " y=" + y);
                }
            }
        }
        if (gameStateType != STATE_GAME){
            showMessage(g);
        }
    }


    private void showMessage(Graphics g) {

        int msgWidth = 400;
        int msgPaddingX = (getWidth() - msgWidth) / 2;

        int msgHeight = 70;
        int msgPaddingY = (getHeight() - msgHeight) / 2;

        g.setColor(Color.DARK_GRAY);
        g.fillRect(msgPaddingX, msgPaddingY, msgWidth, msgHeight);
        g.setColor(Color.YELLOW);
        g.setFont(new Font("Tahoma", Font.BOLD, 32));
        switch (gameStateType){
            case STATE_DRAW -> g.drawString(MSG_DRAW, msgPaddingX + (msgWidth - 110) / 2, msgPaddingY + 50); // 110 - длина строки в пикселях
            case STATE_WIN_HUMAN -> g.drawString(MSG_WIN_HUMAN, msgPaddingX + (msgWidth - 260) / 2, msgPaddingY + 50);
            case STATE_WIN_AI -> g.drawString(MSG_WIN_AI, msgPaddingX + (msgWidth - 350) / 2 , msgPaddingY + 50);
            default -> throw new RuntimeException("Unchecked gameOverState: " + gameStateType);
        }
        gameWork = false;
    }
}