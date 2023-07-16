package Main;

public class PrologCell {

	private int x;
	private int y;

	private int value;

	public PrologCell(int x, int y, int value) {
		this.x = x;
		this.y = y;
		this.value = value;
	}

	@Override
	public String toString() {
		// v(c(1,1), D, _)
		return "v(c(" + x + "," + y + "), D, " + ((value == 0) ? "_" : value) + ")";

	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
