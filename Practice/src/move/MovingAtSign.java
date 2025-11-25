package move;

// 파일명: MovingAtSign.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MovingAtSign extends JPanel {
    private final int rows = 20;
    private final int cols = 40;
    private final char[][] grid = new char[rows][cols];

    private int playerRow = rows / 2;
    private int playerCol = cols / 2;

    // 현재 이동 방향 (-1,0,1)
    private int dr = 0;
    private int dc = 0;

    private final Timer moveTimer;

    public MovingAtSign() {
        setPreferredSize(new Dimension(cols * 16, rows * 18));
        setBackground(Color.BLACK);
        setFocusable(true);
        initGrid();

        // 키 바인딩: WASD + 화살표 (키를 누르면 방향 설정, 뗄 때 멈춤)
        setupKeyBindings();

        // 타이머: 키가 눌려있는 동안 일정 간격으로 플레이어 이동 및 repaint
        moveTimer = new Timer(75, e -> {
            if (dr != 0 || dc != 0) {
                movePlayer(dr, dc);
            }
        });
        moveTimer.start();
    }

    private void initGrid() {
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                grid[r][c] = '*';
            }
        }
    }

    private void setupKeyBindings() {
        InputMap im = getInputMap(WHEN_IN_FOCUSED_WINDOW);
        ActionMap am = getActionMap();

        // WASD (press)
        bindKey(im, am, "W-pressed", KeyStroke.getKeyStroke('W'), () -> setDirection(-1, 0));
        bindKey(im, am, "w-pressed", KeyStroke.getKeyStroke('w'), () -> setDirection(-1, 0));
        bindKey(im, am, "S-pressed", KeyStroke.getKeyStroke('S'), () -> setDirection(1, 0));
        bindKey(im, am, "s-pressed", KeyStroke.getKeyStroke('s'), () -> setDirection(1, 0));
        bindKey(im, am, "A-pressed", KeyStroke.getKeyStroke('A'), () -> setDirection(0, -1));
        bindKey(im, am, "a-pressed", KeyStroke.getKeyStroke('a'), () -> setDirection(0, -1));
        bindKey(im, am, "D-pressed", KeyStroke.getKeyStroke('D'), () -> setDirection(0, 1));
        bindKey(im, am, "d-pressed", KeyStroke.getKeyStroke('d'), () -> setDirection(0, 1));

        // Arrow keys too
        bindKey(im, am, "UP-pressed", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), () -> setDirection(-1, 0));
        bindKey(im, am, "DOWN-pressed", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), () -> setDirection(1, 0));
        bindKey(im, am, "LEFT-pressed", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), () -> setDirection(0, -1));
        bindKey(im, am, "RIGHT-pressed", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), () -> setDirection(0, 1));

        // Release -> stop movement if that key's direction was active
        bindKey(im, am, "W-released", KeyStroke.getKeyStroke('W', 0, true), () -> stopDirection(-1, 0));
        bindKey(im, am, "w-released", KeyStroke.getKeyStroke('w', 0, true), () -> stopDirection(-1, 0));
        bindKey(im, am, "S-released", KeyStroke.getKeyStroke('S', 0, true), () -> stopDirection(1, 0));
        bindKey(im, am, "s-released", KeyStroke.getKeyStroke('s', 0, true), () -> stopDirection(1, 0));
        bindKey(im, am, "A-released", KeyStroke.getKeyStroke('A', 0, true), () -> stopDirection(0, -1));
        bindKey(im, am, "a-released", KeyStroke.getKeyStroke('a', 0, true), () -> stopDirection(0, -1));
        bindKey(im, am, "D-released", KeyStroke.getKeyStroke('D', 0, true), () -> stopDirection(0, 1));
        bindKey(im, am, "d-released", KeyStroke.getKeyStroke('d', 0, true), () -> stopDirection(0, 1));

        bindKey(im, am, "UP-released", KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0, true), () -> stopDirection(-1, 0));
        bindKey(im, am, "DOWN-released", KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0, true), () -> stopDirection(1, 0));
        bindKey(im, am, "LEFT-released", KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0, true), () -> stopDirection(0, -1));
        bindKey(im, am, "RIGHT-released", KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0, true), () -> stopDirection(0, 1));

        // Space to reset position
        bindKey(im, am, "SPACE-pressed", KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0), () -> {
            playerRow = rows / 2;
            playerCol = cols / 2;
            repaint();
        });
    }

    private void bindKey(InputMap im, ActionMap am, String name, KeyStroke ks, Runnable action) {
        im.put(ks, name);
        am.put(name, new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                action.run();
            }
        });
    }

    private synchronized void setDirection(int nr, int nc) {
        dr = nr;
        dc = nc;
        // 한 번 즉시 이동해서 반응성을 높임
        movePlayer(dr, dc);
    }

    // release된 키의 방향과 현재 방향이 같으면 멈춤
    private synchronized void stopDirection(int nr, int nc) {
        if (dr == nr && dc == nc) {
            dr = 0;
            dc = 0;
        }
    }

    private void movePlayer(int drow, int dcol) {
        int newR = playerRow + drow;
        int newC = playerCol + dcol;
        if (newR >= 0 && newR < rows && newC >= 0 && newC < cols) {
            playerRow = newR;
            playerCol = newC;
            repaint();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // 글자 출력 설정
        g.setFont(new Font(Font.MONOSPACED, Font.BOLD, 14));
        FontMetrics fm = g.getFontMetrics();

        int cellWidth = fm.charWidth('*');
        int cellHeight = fm.getHeight();

        // 중앙 정렬 보정
        int startX = 5;
        int startY = fm.getAscent() + 5;

        // 배경 별 찍기
        g.setColor(Color.GREEN.darker());
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                int x = startX + c * cellWidth;
                int y = startY + r * cellHeight;
                g.drawString(String.valueOf(grid[r][c]), x, y);
            }
        }

        // 플레이어 @ 그리기 (강조 색)
        g.setColor(Color.YELLOW);
        int px = startX + playerCol * cellWidth;
        int py = startY + playerRow * cellHeight;
        g.drawString("@", px, py);

        // (선택) 플레이어 좌표 표시
        g.setColor(Color.WHITE);
        g.drawString("Pos: (" + playerRow + ", " + playerCol + ")  |  WASD or Arrow keys to move, SPACE reset", 5, getHeight() - 8);
    }

    private static void createAndShowGui() {
        JFrame frame = new JFrame("WASD로 @ 움직이기 (행님용)");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        MovingAtSign panel = new MovingAtSign();
        frame.setContentPane(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // 포커스 강제 (키 입력을 받기 위해)
        SwingUtilities.invokeLater(panel::requestFocusInWindow);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(MovingAtSign::createAndShowGui);
    }
}
