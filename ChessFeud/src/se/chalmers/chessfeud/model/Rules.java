package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.King;
import se.chalmers.chessfeud.model.pieces.Knight;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Queen;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;

public class Rules {
	
	private static final int[] HORSE_X = {-2, -1, 1, 2, 2, 1, -1, -2};
	private static final int[] HORSE_Y = {1, 2, 2, 1, -1, -2, -2, -1};
	
	public static Piece startBoard(int x, int y){
		return null;
	}
	/**
	 * Returns true if the board is in an state that makes the game over.
	 * @param cb, the current chessboard to be checked
	 * @return true if game over.
	 */
	public boolean gameOver(ChessBoard cb){
		return false;
	}
	
	public static boolean isCheck(ChessBoard cb){
		for(int x = 0; x < cb.width(); x++)
			for(int y = 0; y < cb.height(); y++){
				Piece kingPiece = cb.getPieceAt(x, y);
				if(kingPiece instanceof King){
					for(int dx = -1; dx <= 1; dx++)
						for(int dy = -1; dy <= 1; dy++){
							if(inBounds(x+dx,y+dy) && cb.isEmpty(new Position(x+dx, y+dy)) && !kingPiece.equals(new Position(x,y))){
								int dir = 2;
								while(inBounds(x+dx,y+dy) && cb.isEmpty(new Position(x+dx*dir, y+dy*dir))){
									dir++;
								}
								if(inBounds(x+dx,y+dy)){
									Piece pi = cb.getPieceAt(x+dx*dir, y+dy*dir);
									if(Math.abs(dx*dy) == 0 && (pi instanceof Queen || pi instanceof Rook))
										return true;
									if(Math.abs(dx*dy) == 1 && (pi instanceof Queen || pi instanceof Bishop))
										return true;
								}
								
							}
								
						}
					for(int i = 0; i < HORSE_X.length; i++){
						int dx = HORSE_X[i];
						int dy = HORSE_Y[i];
						if(inBounds(x+dx,y+dy) && cb.getPieceAt(x+dx,y+dy) instanceof Knight)
							return true;
					}
				}
			}
		return false;
	}
	
	private static boolean inBounds(int x, int y){
		return 0 <= x && x < 8 && 0 <= y && y < 8;
	}
}
