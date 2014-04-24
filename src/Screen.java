/**
 * Block Screen of Gameplay
 */

/**
 * @author tyler
 *
 */
public class Screen {

	public static final int NUM_ROWS = 23;
	public static final int NUM_COLS = 10;
	public static final int DIF = NUM_ROWS - BlockGUI.NUM_ROWS;
	private static int TYPE = 0;
	
	public enum Block {
		// empty location
		EMPTY,
		// 4 in a line
		TALL,
		// L-shaped piece
		ELL,
		// Backwards L-shaped piece
		BACKELL,
		// 3 in a line, one on top
		HORN,
		// 2 left and top, 2 right and below
		EGYPT,
		// 2 left and bottom, 2 right and top
		SNAKE,
		// 2 top, 2 bottom
		SQUARE;
		
		public int[][] key() {
			int[][] key = new int[3][2];
			
			if (TYPE == 0) {
				
				if (this.equals(TALL)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = 1;
					key[1][1] = 0;
					key[2][0] = 2;
					key[2][1] = 0;
				} 
				else if (this.equals(ELL)) {
					key[0][0] = -2;
					key[0][1] = 0;
					key[1][0] = -1;
					key[1][1] = 0;
					key[2][0] = 0;
					key[2][1] = 1;
				}
				else if (this.equals(BACKELL)) {
					key[0][0] = -2;
					key[0][1] = 0;
					key[1][0] = -1;
					key[1][1] = 0;
					key[2][0] = 0;
					key[2][1] = -1;
				}
				else if (this.equals(HORN)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = -1;
					key[2][0] = 0;
					key[2][1] = 1;
				}
				else if (this.equals(EGYPT)) {
					key[0][0] = -1;
					key[0][1] = -1;
					key[1][0] = -1;
					key[1][1] = 0;
					key[2][0] = 0;
					key[2][1] = 1;
				}
				else if (this.equals(SNAKE)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = -1;
					key[1][1] = 1;
					key[2][0] = 0;
					key[2][1] = -1;
				}
				else if (this.equals(SQUARE)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 1;
				}
				
			}
			else if (TYPE == 1) {
				
				if (this.equals(TALL)) {
					key[0][0] = 0;
					key[0][1] = -2;
					key[1][0] = 0;
					key[1][1] = -1;
					key[2][0] = 0;
					key[2][1] = 1;
				}
				else if (this.equals(ELL)) {
					key[0][0] = 0;
					key[0][1] = 1;
					key[1][0] = 0;
					key[1][1] = 2;
					key[2][0] = 1;
					key[2][1] = 0;
				}
				else if (this.equals(BACKELL)) {
					key[0][0] = 0;
					key[0][1] = 1;
					key[1][0] = 0;
					key[1][1] = 2;
					key[2][0] = -1;
					key[2][1] = 0;
				}
				else if (this.equals(HORN)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = -1;
					key[1][1] = 0;
					key[2][0] = 0;
					key[2][1] = 1;
				}
				else if (this.equals(EGYPT)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = -1;
					key[2][1] = 1;
				}
				else if (this.equals(SNAKE)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 1;
				}
				else if (this.equals(SQUARE)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 1;
				}
				
			}
			else if (TYPE == 2) {
				
				if (this.equals(TALL)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = -1;
					key[1][1] = 0;
					key[2][0] = -2;
					key[2][1] = 0;
				}
				else if (this.equals(ELL)) {
					key[0][0] = 0;
					key[0][1] = -1;
					key[1][0] = 1;
					key[1][1] = 0;
					key[2][0] = 2;
					key[2][1] = 0;
				}
				else if (this.equals(BACKELL)) {
					key[0][0] = 0;
					key[0][1] = 1;
					key[1][0] = 1;
					key[1][1] = 0;
					key[2][0] = 2;
					key[2][1] = 0;
				}
				else if (this.equals(HORN)) {
					key[0][0] = 0;
					key[0][1] = -1;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 0;
				}
				else if (this.equals(EGYPT)) {
					key[0][0] = -1;
					key[0][1] = -1;
					key[1][0] = -1;
					key[1][1] = 0;
					key[2][0] = 0;
					key[2][1] = 1;
				}
				else if (this.equals(SNAKE)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = -1;
					key[1][1] = 1;
					key[2][0] = 0;
					key[2][1] = -1;
				}
				else if (this.equals(SQUARE)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 1;
				}
				
			}
			else if (TYPE == 3) {
				
				if (this.equals(TALL)) {
					key[0][0] = 0;
					key[0][1] = -1;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 0;
					key[2][1] = 2;
				}
				else if (this.equals(ELL)) {
					key[0][0] = 0;
					key[0][1] = -1;
					key[1][0] = 0;
					key[1][1] = -2;
					key[2][0] = -1;
					key[2][1] = 0;
				}
				else if (this.equals(BACKELL)) {
					key[0][0] = 0;
					key[0][1] = -2;
					key[1][0] = 0;
					key[1][1] = -1;
					key[2][0] = 1;
					key[2][1] = 0;
				}
				else if (this.equals(HORN)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = 1;
					key[1][1] = 0;
					key[2][0] = 0;
					key[2][1] = -1;
				}
				else if (this.equals(EGYPT)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = -1;
					key[2][1] = 1;
				}
				else if (this.equals(SNAKE)) {
					key[0][0] = -1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 1;
				}
				else if (this.equals(SQUARE)) {
					key[0][0] = 1;
					key[0][1] = 0;
					key[1][0] = 0;
					key[1][1] = 1;
					key[2][0] = 1;
					key[2][1] = 1;
				}
				
			}
			
			return key;
		}
	}
	
	private Block[][] screen;
	private boolean[][] dead;
	private static int row;
	private static int col;
	
	public Screen() 
	{
		screen = new Block[NUM_ROWS][NUM_COLS];
		dead = new boolean[NUM_ROWS][NUM_COLS];
		for (int i=0; i<NUM_ROWS; i++)
			for (int j=0; j<NUM_COLS; j++) {
				screen[i][j] = Block.EMPTY;
				dead[i][j] = false;
			}
	}
	
	public void setForNew() {
		row = 2;
		col = 4;
		TYPE = 0;
	}
	
	public void newPiece(double percent) {
		
		Block piece = Block.EMPTY;
		
		if (percent < .14)
			piece = Block.TALL;
		else if (percent < .28)
			piece = Block.ELL;
		else if (percent < .42)
			piece = Block.BACKELL;
		else if (percent < .56)
			piece = Block.HORN;
		else if (percent < .70)
			piece = Block.EGYPT;
		else if (percent < .85)
			piece = Block.SNAKE;
		else if (percent >= .85)
			piece = Block.SQUARE;
		
		createNewPiece(piece, false);
	}

	public boolean checkDead() {
		
		boolean isDead = false;
		int[][] key = screen[row][col].key();
		
		if (row+1 == NUM_ROWS || dead[row+1][col] == true)
			isDead = true;
		for (int i=0; i<3; i++) {
			int r = row+key[i][0];
			int c = col+key[i][1];
			if (r+1 == NUM_ROWS || dead[r+1][c] == true)
				isDead = true;
		}
			
		return isDead;
	}
	
	public boolean validMove(int dir) {
		
		boolean valid = true;
		int[][] key = screen[row][col].key();
		
		if (col+dir < 0 || col+dir >= NUM_COLS || dead[row][col+dir] == true)
			valid = false;
		for (int i=0; i<3; i++) {
			int r = row+key[i][0];
			int c = col+key[i][1];
			if (c+dir < 0 || c+dir >= NUM_COLS || dead[r][c+dir] == true)
				valid = false;
		}
			
		return valid;
	}

	public boolean tryRotate() {
		
		boolean valid = true;
		incrementTYPE(true);
		int[][] key = screen[row][col].key();

		for (int i=0; i<3; i++) {
			int r = row+key[i][0];
			int c = col+key[i][1];
			if (r < 0 || r >= NUM_ROWS || c < 0 || c >= NUM_COLS || dead[r][c] == true)
				valid = false;
		}
		
		incrementTYPE(false);
		
		if (valid) 
			commitRotate();
		
		return valid;
	} 
	
	public void commitDead() {
		
		Block piece = screen[row][col];
		int[][] key = piece.key();
		
		dead[row][col] = true;
		for (int i=0; i<3; i++)
			dead[row+key[i][0]][col+key[i][1]] = true;
		
	}
	
	public void commitMove(int dir) {
		
		Block piece = screen[row][col];
		
		if (!dead[row][col]) {
			createNewPiece(piece, true);	
			col = col+dir;
			createNewPiece(piece, false);
		}
	}
	
	public void commitFall() {
		
		Block piece = screen[row][col];
		
		createNewPiece(piece, true);	
		row++;
		createNewPiece(piece, false);
	}
	
	
	public void commitRotate() {
		
		Block piece = screen[row][col];
		
		createNewPiece(piece, true);
		incrementTYPE(true);
		createNewPiece(piece, false);
	}
	
	public int clearRows() {
			
		boolean[] clear = new boolean[NUM_ROWS];
		int count = 0;
		
		for (int i=NUM_ROWS-1; i>2; i--) {
		
			clear[i] = false;
			count = 0;
			while (count < 10) {
				if (!dead[i][count])
					break;
				count++;
			}

			if (count == 10)
				clear[i] = true;
		}
		
		int score = 0;
		int toMove = 0;
		
		for (int i=NUM_ROWS-1; i>3; i--) {
			
			boolean stay = clear[i];
			
			if (clear[i] == true) {
				score++;
				toMove++;
				clear[i] = clear[i-toMove];
				clear[i-toMove] = false;
			}
			for (int j=0; j<10; j++) {
				screen[i][j] = screen[i-toMove][j];
				dead[i][j] = dead[i-toMove][j];
			}
			if (stay)
				i++;
		}
		
		return score;
	}
	
	public boolean checkGameOver() {
		
		for (int i=0; i<10; i++)
			if (dead[2][i] == true)
				return true;
		return false;
	}

	public Block getBlock(int row, int col) {
		return screen[row][col];
	}
	
	public void setBlock(int row, int col, Block block) {
		if (row >= 0 && row < NUM_ROWS && col >= 0 && col < NUM_COLS)
			screen[row][col] = block; 
	}
	
	public boolean isDead(int row, int col) {
		if (dead[row][col])
			return true;
		return false;
	}
	
	public int getRows() {
		return NUM_ROWS;
	}
	
	public int getCols() {
		return NUM_COLS;
	}
	
	public void incrementTYPE(boolean increment) {
		if (increment)
			TYPE++;
		else
			TYPE--;
		TYPE = TYPE%4;
		
		if (TYPE < 0)
			TYPE = 3;
	}
	
	private void createNewPiece(Block piece, boolean erase) {
		
		int[][] key = piece.key();
		
		if (erase)
			piece = Block.EMPTY;
		
		screen[row][col] = piece;
		for (int i=0; i<3; i++)
			screen[row+key[i][0]][col+key[i][1]] = piece;
	}
}
