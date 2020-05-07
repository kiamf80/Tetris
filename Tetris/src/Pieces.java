import java.awt.Point;

public class Pieces {
	static Point[][][] Pieces;
	public Pieces() {
		Pieces = init();
	}
	Point[][][] init() {
		Point[][][] Pieces = {
				// I-piece
				{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
						{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) },
						{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(3, 1) },
						{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(1, 3) } },
				// T-piece
				{ { new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
						{ new Point(1, 2), new Point(1, 0), new Point(1, 1), new Point(0, 1) },
						{ new Point(1, 2), new Point(0, 1), new Point(1, 1), new Point(2, 1) },
						{ new Point(1, 2), new Point(1, 0), new Point(1, 1), new Point(2, 1) } },
				// S-piece
				{ { new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
						{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) },
						{ new Point(1, 0), new Point(2, 0), new Point(0, 1), new Point(1, 1) },
						{ new Point(0, 0), new Point(0, 1), new Point(1, 1), new Point(1, 2) } },
				// RS-piece
				{ { new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
						{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) },
						{ new Point(0, 0), new Point(1, 0), new Point(1, 1), new Point(2, 1) },
						{ new Point(1, 0), new Point(0, 1), new Point(1, 1), new Point(0, 2) } },
				// L-Piece
				{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 2) },
						{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 2) },
						{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 0) },
						{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 0) } },
				// RL-Piece
				{ { new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(2, 0) },
						{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(2, 2) },
						{ new Point(0, 1), new Point(1, 1), new Point(2, 1), new Point(0, 2) },
						{ new Point(1, 0), new Point(1, 1), new Point(1, 2), new Point(0, 0) } },
				// RECT-Piece
				{ { new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
						{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
						{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) },
						{ new Point(0, 0), new Point(0, 1), new Point(1, 0), new Point(1, 1) } } };
		return Pieces;
	}
}
