import java.awt.BorderLayout;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Timer;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class startButton extends JButton implements ActionListener {
	JFrame jf = new JFrame();
	public startButton(String s, JFrame jf) {
		super(s);
		this.jf = jf;
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		try {
			GameBoard.pcontinue = false;
			Tetris.startNewGame();
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		jf.setVisible(false);
	}

}
class continueButton extends JButton implements ActionListener {
	JFrame jf = new JFrame();

	public continueButton(String s, JFrame jf) {
		super(s);
		this.jf = jf;
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		GameBoard.pcontinue = true;
		try {
			Tetris.startNewGame();
		} catch (IOException | ParseException e1) {
			e1.printStackTrace();
		}
		jf.setVisible(false);
	}

}
class exitButton extends JButton implements ActionListener {
	JFrame jf = new JFrame();

	public exitButton(String s, JFrame jf) {
		super(s);
		this.jf = jf;
		this.addActionListener(this);
	}

	public void actionPerformed(ActionEvent e) {
		jf.setVisible(false);
		System.exit(0);
	}
}

class submitButton extends JButton implements ActionListener {
	JFrame jf = new JFrame();
	TextField tf = new TextField();
	long score;
	Timer timer = new Timer();

	public submitButton(String s, JFrame jf, TextField tf, long score, Timer timer) {
		super(s);
		this.jf = jf;
		this.score = score;
		this.tf = tf;
		this.addActionListener(this);
		this.timer = timer;
	}

	public void actionPerformed(ActionEvent e) {
		String n = tf.getText();
		JSONObject jo = new JSONObject();
		File F = new File("C:\\Users\\asus\\Desktop\\Tetris\\player.json");
		try {
			Object obj = new JSONParser().parse(new FileReader(F));
			jo = (JSONObject) obj;
			JSONArray ja = (JSONArray) jo.get("players");
			JSONArray ja1 = new JSONArray();
			ja1.add(n);
			ja1.add(score);
			ja.add(ja1);
			jo.clear();
			jo.put("players", ja);
			PrintWriter pw = new PrintWriter(F);
			pw.write(jo.toJSONString());
			pw.flush();
			Map<String, ArrayList<Long>> m = new LinkedHashMap<String, ArrayList<Long>>();
			ArrayList<Long> topScores = new ArrayList<Long>();
			HashSet<String> names = new HashSet<String>();
			ja.add(ja1);
			for (int i = 0; i < ja.size(); i++) {
				ja1.clear();
				ArrayList<Long> temp = new ArrayList<Long>();
				ja1 = (JSONArray) ja.get(i);
				if (ja1.size() > 1) {
					temp.add((Long) ja1.get(1));
					topScores.add((Long) ja1.get(1));
					names.add((String) ja1.get(0));
					if (m.get((String) ja1.get(0)) != null) {
						temp.addAll(m.get((String) ja1.get(0)));
					}
					m.put((String) ja1.get(0), temp);
				}
			}
			topScores.add(score);
			topScores.sort(null);
			DefaultListModel<String> l1 = new DefaultListModel<>();
			l1.addElement("Top Scores");
			boolean flag = false;
			for (int i = 1; i < 21; i++) {
				String st = "";
				for (String temp : names) {
					for (long l : m.get(temp)) {
						if (l == topScores.get(topScores.size() - i)) {
							st = temp;
							flag = true;
							break;
						}
					}
					if (flag) {
						flag = false;
						break;
					}
				}
				if (st.equals("")) {
					st = "YOU";
				}
				l1.addElement(i + "." + topScores.get(topScores.size() - i) + " by " + st);
			}
			JList<String> list = new JList<>(l1);
			jf.setVisible(false);
			JFrame jf2 = new JFrame("GAMEOVER");
			jf2.setSize(500, 500);
			jf2.add(list, BorderLayout.CENTER);
			jf2.add(new startButton("START NEW GAME", jf2), BorderLayout.SOUTH);
			jf2.setDefaultCloseOperation(jf2.EXIT_ON_CLOSE);
			jf2.setVisible(true);
			timer.cancel();
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		} catch (ParseException e1) {
			e1.printStackTrace();
		}
	}

}