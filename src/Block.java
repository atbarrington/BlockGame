/**
 * Block User Management
 */

/**
 * @author tyler
 *
 */


public class Block {

	Screen screen;
	private static double next = -1;
	private static int rowsCleared;
	
	public Block() {
		screen = new Screen();
		rowsCleared = 0;
	}
	
	public int getScore() {
		int score = 10*rowsCleared*rowsCleared;
		rowsCleared = 0;
		return score;
	}
	
	public boolean checkGameOver() {
		return screen.checkGameOver();
	}
	
	public boolean sendNewPiece() {
		for (int i=0; i<Screen.NUM_ROWS; i++)
			for (int j=0; j<Screen.NUM_COLS; j++)
				if (!(isEmpty(i, j) || isDead(i, j)))
					return false;
		
		if (next == -1)
			next = (Math.random());
		
		screen.setForNew();
		screen.newPiece(next);
		next = (Math.random());
		return true;
	}
	
	public double next() {
		return next;
	}
	
	public boolean movePiece(int direction) {
		
		if (!screen.validMove(direction))
			return false;
		
		screen.commitMove(direction);
		return true;
	}
	
	public boolean fallPiece() {
				
		if (screen.checkDead()) {
			screen.commitDead();
			rowsCleared += screen.clearRows();
			return false;
		}
		
		screen.commitFall(); 
		return true;
	}
	
	public void rotatePiece() {
		screen.tryRotate();
	}
	
	public boolean isEmpty(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.EMPTY)
			return true;
		return false;
	}
	
	public boolean isDead(int row, int col) {
		if (screen.isDead(row, col))
			return true;
		return false;
	}
	
	public boolean isTall(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.TALL)
			return true;
		return false;
	}
	
	public boolean isEll(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.ELL)
			return true;
		return false;
	}
	
	public boolean isBackEll(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.BACKELL)
			return true;
		return false;
	}
	
	public boolean isHorn(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.HORN)
			return true;
		return false;
	}
	
	public boolean isEgypt(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.EGYPT)
			return true;
		return false;
	}
	
	public boolean isSnake(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.SNAKE)
			return true;
		return false;
	}
	
	public boolean isSquare(int row, int col) {
		if (screen.getBlock(row, col) == Screen.Block.SQUARE)
			return true;
		return false;
	}
	
}