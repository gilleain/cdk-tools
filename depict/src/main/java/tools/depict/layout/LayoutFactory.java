package tools.depict.layout;

public class LayoutFactory {
	
	public static GridLayout makeGridLayout(int n) {
		int r = (int) Math.floor(n / 2);
		int c = n / r;
		return new GridLayout(r, c);
	}
	
	public static GridLayout makeHorizontalLayout(int n) {
		return new GridLayout(1, n);
	}
	
	public static GridLayout makeVerticalLayout(int n) {
		return new GridLayout(n, 1);
	}

}
