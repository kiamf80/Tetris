import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameBoard extends JPanel {
	private static final long serialVersionUID = -3077304618271596354L;
	static boolean pcontinue = false;
	private ArrayList<Color> colors = new ArrayList<>();
	private Point initialPoint;
	private int nextPiece;
	private int currentPiece;
	private int rotation;
	boolean gameOver = false;
	boolean pause = false;
	private ArrayList<Integer> nextPieces = new ArrayList<Integer>();
	long Score = 0;
	private Color[][] gameBoard = new Color[12][24];

	// Function to save game when you exit.
	void saveGame() throws FileNotFoundException, IOException, ParseException {
		JSONObject jo = new JSONObject();
		JSONArray ja = new JSONArray();
		for(int i=0;i<12;i++) {
			JSONArray temp = new JSONArray();
			for(int j=0;j<24;j++) {
				if (gameBoard[i][j]==Color.BLACK){
					temp.add(0);
				}
				else if (gameBoard[i][j]==Color.DARK_GRAY){
					temp.add(1);
				}
				else if (gameBoard[i][j]==Color.RED){
					temp.add(2);
				}
				else if (gameBoard[i][j]==Color.BLUE){
					temp.add(3);
				}
				else if (gameBoard[i][j]==Color.GREEN){
					temp.add(4);
				}
				else if (gameBoard[i][j]==Color.YELLOW){
					temp.add(5);
				}
				else if (gameBoard[i][j]==Color.CYAN){
					temp.add(6);
				}
				else if (gameBoard[i][j]==Color.PINK){
					temp.add(7);
				}
				else if (gameBoard[i][j]==Color.MAGENTA){
					temp.add(8);
				}
				else if (gameBoard[i][j]==Color.ORANGE){
					temp.add(9);
				}
			}
			ja.add(temp);
		}
		jo.put("board", ja);
		PrintWriter pw = new PrintWriter(new File("C:\\Users\\asus\\Desktop\\Tetris\\save.json"));
		pw.write(jo.toJSONString());
		pw.flush();
	}

	// function to get the saved game.
	void getPreviousGame() throws FileNotFoundException, IOException, ParseException {
		Object obj = new JSONParser().parse(new FileReader(new File("C:\\Users\\asus\\Desktop\\Tetris\\save.json")));
		JSONObject jo = (JSONObject) obj;
		JSONArray ja = (JSONArray) jo.get("board");
		for(int i=0;i<12;i++) {
			JSONArray temp = (JSONArray)ja.get(i);
			for(int j=0;j<24;j++) {
				if ((Long)temp.get(j)==0L){
					gameBoard[i][j] = Color.BLACK;
				}
				else if ((Long)temp.get(j)==1L){
					gameBoard[i][j] = Color.DARK_GRAY;
				}
				else if ((Long)temp.get(j)==2L){
					gameBoard[i][j] = Color.RED;
				}
				else if ((Long)temp.get(j)==3L){
					gameBoard[i][j] = Color.BLUE;
				}
				else if ((Long)temp.get(j)==4L){
					gameBoard[i][j] = Color.GREEN;
				}
				else if ((Long)temp.get(j)==5L){
					gameBoard[i][j] = Color.YELLOW;
				}
				else if ((Long)temp.get(j)==6L){
					gameBoard[i][j] = Color.CYAN;
				}
				else if ((Long)temp.get(j)==7L){
					gameBoard[i][j] = Color.PINK;
				}
				else if ((Long)temp.get(j)==8L){
					gameBoard[i][j] = Color.MAGENTA;
				}
				else if ((Long)temp.get(j)==9L){
					gameBoard[i][j] = Color.ORANGE;
				}
			}
		}
		
	}

	// creates gameBoard and drops first piece.
	void init() throws FileNotFoundException, IOException, ParseException {
		Collections.addAll(colors, Color.RED, Color.BLUE, Color.GREEN, Color.YELLOW, Color.CYAN, Color.PINK,
				Color.MAGENTA, Color.ORANGE);
		if(pcontinue) {
			getPreviousGame();
		}
		else {
			for (int i = 0; i < 12; i++) {
				for (int j = 0; j < 24; j++) {
					if (i == 0 || j == 0 || j == 22 || i == 11) {
						gameBoard[i][j] = Color.DARK_GRAY;
					} else {
						gameBoard[i][j] = Color.BLACK;
					}
				}

			}
		}
		newPiece();
	}
	//funtion to pause the game.
	void pause() {
		pause = !pause;
	}

	// creates new random piece.
	void newPiece() {
		if (!gameOver) {
			initialPoint = new Point(3, 2);
			rotation = 0;
			if (nextPieces.size() < 2) {
				if (!nextPieces.isEmpty()) {
					int temp = nextPieces.get(0);
					nextPieces.remove((Object) temp);
					for (int i = 0; i < 7; i++) {
						if (i != temp) {
							nextPieces.add(i);
						}
					}
					Collections.shuffle(nextPieces);
					nextPieces.add(0, temp);
				} else {
					nextPieces.clear();
					nextPieces.add(0);
					nextPieces.add(1);
					nextPieces.add(2);
					nextPieces.add(3);
					nextPieces.add(4);
					nextPieces.add(5);
					nextPieces.add(6);
					Collections.shuffle(nextPieces);
				}
			}
			Collections.shuffle(colors);
			currentPiece = nextPieces.get(0);
			nextPiece = nextPieces.get(1);
			nextPieces.remove(0);
		}
	}

	// move horizantaly.
	void hMove(int i) {
		if (!pause) {
			if (!doesCollide(initialPoint.x + i, initialPoint.y, rotation)) {
				initialPoint.x += i;
			}
			repaint();
		}
	}

	// drops down a piece one home.
	void dropDown() {
		if (!pause) {
			if (!doesCollide(initialPoint.x, initialPoint.y + 1, rotation)) {
				initialPoint.y += 1;
			} else {
				fix();
			}
			repaint();
		}
	}

	// rotate
	void rotate(int i) {
		if (!pause) {
			int temp = (rotation + i) % 4;
			if (rotation < 0) {
				rotation = 3;
			}
			if (!doesCollide(initialPoint.x, initialPoint.y, temp)) {
				rotation = temp;
			}
			repaint();

		}
	}

	boolean doesCollide(int x, int y, int rotation) {
		if (rotation < 0) {
			rotation = 3;
		}
		for (Point p : Pieces.Pieces[currentPiece][rotation]) {
			if (gameBoard[p.x + x][p.y + y] != Color.BLACK) {
				return true;
			}
		}
		return false;
	}

	// fixes a piece in it's current position.
	void fix() {
		for (Point p : Pieces.Pieces[currentPiece][rotation]) {
			gameBoard[initialPoint.x + p.x][initialPoint.y + p.y] = colors.get(currentPiece);
		}
		clearRows();
		newPiece();
	}

	// deletes the chosen row.
	void deleteRow(int row) {
		for (int j = row - 1; j > 0; j--) {
			for (int i = 1; i < 12; i++) {
				gameBoard[i][j + 1] = gameBoard[i][j];
			}
		}
	}

	// clear completed rows and award points.
	void clearRows() {
		boolean gap;
		int numClears = 0;

		for (int j = 21; j > 0; j--) {
			gap = false;
			for (int i = 1; i < 11; i++) {
				if (gameBoard[i][j] == Color.BLACK) {
					gap = true;
					break;
				}
			}
			if (!gap) {
				deleteRow(j);
				j += 1;
				numClears += 1;
			}
		}

		switch (numClears) {
		case 1:
			Score += 100;
			break;
		case 2:
			Score += 300;
			break;
		case 3:
			Score += 500;
			break;
		case 4:
			Score += 800;
			break;
		}
	}

	// draw piece.
	void drawPiece(Graphics g) throws FileNotFoundException, IOException, ParseException {
		g.setColor(colors.get(currentPiece));
		if (rotation < 0) {
			rotation = 3;
		}
		for (Point p : Pieces.Pieces[currentPiece][rotation]) {
			if (doesCollide(3, 3, rotation)) {
				gameOver = true;
				init();
				saveGame();
			}
			g.fillRect((p.x + initialPoint.x) * 26, (p.y + initialPoint.y) * 26, 25, 25);
		}

	}

	public void paintComponent(Graphics g) {
		try {
			saveGame();
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
		// Paint the well
		g.fillRect(0, 0, 26 * 12, 26 * 23);
		for (int i = 0; i < 12; i++) {
			for (int j = 0; j < 23; j++) {
				g.setColor(gameBoard[i][j]);
				g.fillRect(26 * i, 26 * j, 25, 25);
			}
		}

		// Display the score
		g.setColor(Color.WHITE);
		g.drawString("" + Score, 19 * 12, 25);
		// next Piece
		g.setColor(Color.WHITE);
		g.drawString("instruction:", 400, 50);
		g.drawString("esc:pause", 400, 75);
		g.drawString("space:dropdown", 400, 100);
		g.drawString("use your keyboard to navigate", 400, 125);
		g.drawString("next piece", 400, 380);
		if (rotation < 0) {
			rotation = 3;
		}
		g.setColor(Color.BLACK);
		for (int x = 0; x < 4; x++) {
			for (int y = 0; y < 4; y++) {
				g.fillRect(x * 26 + 400, y * 26 + 400, 25, 25);
			}
		}
		g.setColor(Color.WHITE);
		for (Point p : Pieces.Pieces[nextPiece][0]) {
			g.fillRect((p.x) * 26 + 400, (p.y) * 26 + 400, 25, 25);
		}
		// Draw the currently falling piece
		try {
			drawPiece(g);
		} catch (IOException | ParseException e) {
			e.printStackTrace();
		}
	}
}
