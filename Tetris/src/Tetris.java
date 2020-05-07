import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.TextField;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.json.simple.parser.ParseException;
public class Tetris {

	public static void startNewGame() throws FileNotFoundException, IOException, ParseException {
		JFrame jf = new JFrame("Tetris");
		jf.setDefaultCloseOperation(jf.EXIT_ON_CLOSE);
		jf.setSize(600, 630);
		GameBoard game = new GameBoard();
		Pieces p = new Pieces();
		game.init();
		jf.add(game);
		jf.setBackground(Color.BLACK);
		jf.setVisible(true);
		jf.addKeyListener(new KeyListener() {
			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_ESCAPE:
					game.pause();
				case KeyEvent.VK_UP:
					game.rotate(-1);
					break;
				case KeyEvent.VK_DOWN:
					game.rotate(+1);
					break;
				case KeyEvent.VK_LEFT:
					game.hMove(-1);
					break;
				case KeyEvent.VK_RIGHT:
					game.hMove(+1);
					break;
				case KeyEvent.VK_SPACE:
					game.dropDown();
					game.Score += 1;
					break;
				}
			}

			public void keyReleased(KeyEvent e) {
			}
		});
		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				game.dropDown();
				if (game.gameOver) {
					jf.remove(game);
					TextField t1 = new TextField("GAME OVER.Please enter your name to save your score.");
					TextField name = new TextField();
					submitButton submit = new submitButton("submit", jf, name, game.Score, timer);
					t1.setBounds(200, 100, 300, 300);
					name.setBounds(200, 100, 300, 450);
					submit.setBounds(200, 100, 300, 600);
					jf.add(t1, BorderLayout.NORTH);
					jf.add(name, BorderLayout.CENTER);
					jf.add(submit, BorderLayout.SOUTH);
					jf.setVisible(true);
				}
			}

		}, 0, 1000);
	}

	public static void main(String[] args) {
		JFrame menu = new JFrame("TETRIS");
		menu.setDefaultCloseOperation(menu.EXIT_ON_CLOSE);
		menu.setSize(500, 500);
		startButton start = new startButton("start", menu);
		exitButton exit = new exitButton("exit", menu);
		continueButton cb = new continueButton("Continue previous game",menu);
		JPanel jp = new JPanel();
		ImageIcon logo = new ImageIcon("C:\\Users\\asus\\Desktop\\Tetris\\Tetris-Logo-980x553.jpg");
		menu.add(new JLabel(logo), BorderLayout.CENTER);
		jp.setLayout(new BoxLayout(jp, BoxLayout.LINE_AXIS));
		jp.add(start);
		jp.add(cb);
		jp.add(exit);
		menu.add(jp, BorderLayout.NORTH);
		menu.setVisible(true);
	}
}
