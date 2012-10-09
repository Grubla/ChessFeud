package se.chalmers.chessfeud.model;

import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.King;
import se.chalmers.chessfeud.model.pieces.Knight;
import se.chalmers.chessfeud.model.pieces.NoPiece;
import se.chalmers.chessfeud.model.pieces.Pawn;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Queen;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;

public class Rules {
	
	private static final int[] HORSE_X = {-2, -1, 1, 2, 2, 1, -1, -2};
	private static final int[] HORSE_Y = {1, 2, 2, 1, -1, -2, -2, -1};
	private static final Piece[][] START_BOARD = {
		{new Rook(C.TEAM_BLACK), new Knight(C.TEAM_BLACK), new Bishop(C.TEAM_BLACK), 
			new Queen(C.TEAM_BLACK), new King(C.TEAM_BLACK), new Bishop(C.TEAM_BLACK), 
			new Knight(C.TEAM_BLACK), new Rook(C.TEAM_BLACK)},
		{new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK), 
				new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK), 
				new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK)},
		{new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece()},
		{new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece()},
		{new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece()},
		{new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece()},
		{new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE), 
			new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE), 
			new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE)},
		{new Rook(C.TEAM_WHITE), new Knight(C.TEAM_WHITE), new Bishop(C.TEAM_WHITE), 
				new Queen(C.TEAM_WHITE), new King(C.TEAM_WHITE), new Bishop(C.TEAM_WHITE), 
				new Knight(C.TEAM_WHITE), new Rook(C.TEAM_WHITE)}
	};
	
	 
	public static Piece startBoard(int x, int y){
		return START_BOARD[y][x];
	}
	/**
	 * Returns true if the board is in an state that makes the game over.
	 * @param cb, the current chessboard to be checked
	 * @return true if game over.
	 */
	public boolean gameOver(ChessBoard cb){
		return false;
	}
	
	public static boolean isCheck(ChessBoard cb, int team){
		for(int x = 0; x < cb.getWidth(); x++)
			for(int y = 0; y < cb.getHeight(); y++){
				Piece kingPiece = cb.getPieceAt(x, y);
				if(kingPiece.getId() ==  C.PIECE_KING && kingPiece.getTeam() == team){
					for(int dx = -1; dx <= 1; dx++)
						for(int dy = -1; dy <= 1; dy++){
							if(inBounds(x+dx,y+dy) && cb.isEmpty(new Position(x+dx, y+dy)) && !kingPiece.equals(new Position(x,y))){
								int dir = 2;
								while(inBounds(x+dx,y+dy) && cb.isEmpty(new Position(x+dx*dir, y+dy*dir))){
									dir++;
								}
								if(inBounds(x+dx,y+dy)){
									Piece pi = cb.getPieceAt(x+dx*dir, y+dy*dir);
									if(Math.abs(dx*dy) == 0 && (pi.getId() == C.PIECE_QUEEN || pi.getId() == C.PIECE_ROOK))
										return true;
									if(Math.abs(dx*dy) == 1 && (pi.getId() == C.PIECE_QUEEN || pi.getId() == C.PIECE_BISHOP))
										return true;
								}							
							}
						}
					for(int i = 0; i < HORSE_X.length; i++){
						int dx = HORSE_X[i];
						int dy = HORSE_Y[i];
						if(inBounds(x+dx,y+dy) && cb.getPieceAt(x+dx,y+dy).getId() == C.PIECE_KNIGHT)
							return true;
					}
					//Check for pawns aswell
					int forward = team == C.TEAM_WHITE ? 1 : -1;
					if(inBounds(x+1,y+forward) && cb.getPieceAt(x+1, y+forward).getId() == C.PIECE_PAWN)
						return true;
					if(inBounds(x-1,y+forward) && cb.getPieceAt(x-1, y+forward).getId() == C.PIECE_PAWN)
						return true;
				}
			}
		return false;
	}
	
	public static boolean isDraw(ChessBoard cb, int nextTurn){
		if(!isCheck(cb, nextTurn)){
			for(int x = 0; x < cb.getWidth(); x++)
				for(int y = 0; y < cb.getHeight(); y++)
					if(cb.getPieceAt(x, y) != null && cb.getPieceAt(x, y).getTeam() == nextTurn){
						if(getPossibleMoves(cb, new Position(x, y)).size() > 0)
							return false;
					}
			return true;
		}
		return false;
	}
	
	public static boolean isCheckMate(ChessBoard cb, int nextTurn){
		if(isCheck(cb, nextTurn)){
			for(int x = 0; x < cb.getWidth(); x++)
				for(int y = 0; y < cb.getHeight(); y++)
					if(cb.getPieceAt(x, y) != null && cb.getPieceAt(x, y).getTeam() == nextTurn){
						if(getPossibleMoves(cb, new Position(x, y)).size() > 0)
							return false;
					}
			return true;
		}
		return false;
	}
	
	public static List<Position> getPossibleMoves(ChessBoard cb, Position selected){
		List<Position> pm = new LinkedList<Position>();
		Piece piece = cb.getPieceAt(selected);
		List<List<Position>> tempMoves = piece.theoreticalMoves(selected);
		if(!(piece.getId() == C.PIECE_PAWN)){
			for(List<Position> l : tempMoves){
				boolean canMove = true;
				for(Position p : l){
					ChessBoard tmpBoard = new ChessBoard(cb, selected, p);
					if(canMove && Rules.isCheck(tmpBoard,cb.getPieceAt(selected).getTeam())){
						if(cb.isEmpty(p))
							pm.add(p);
						else{
							if(cb.getPieceAt(selected).getTeam() == cb.getPieceAt(p).getTeam())
								pm.add(p);
							canMove = false;
						}
					}
				}
			}
		}else{ // ID == PAWN
			int dy = piece.getTeam() == C.TEAM_WHITE ? -1 : 1;
			int startY = piece.getTeam() == C.TEAM_WHITE ? 6 : 1;
			if(inBounds(selected.getX(), selected.getY()+dy) && cb.getPieceAt(selected.getX(), selected.getY()+dy) == null){
				pm.add(new Position(selected.getX(), selected.getY()+dy));
				if(inBounds(selected.getX(), selected.getY()+2*dy) && cb.getPieceAt(selected.getX(), selected.getY()+2*dy) == null && selected.getY() == startY){
					pm.add(new Position(selected.getX(), selected.getY()+2*dy));
				}
			}
			//Check if there is pieces to take diagonally forward
			if(inBounds(selected.getX()-1, selected.getY()+dy) && cb.getPieceAt(selected.getX()-1, selected.getY()+dy).getTeam() != piece.getTeam())
				pm.add(new Position(selected.getX()-1, selected.getY()+dy));
			if(inBounds(selected.getX()+1, selected.getY()+dy) && cb.getPieceAt(selected.getX()+1, selected.getY()+dy).getTeam() != piece.getTeam())
				pm.add(new Position(selected.getX()+1, selected.getY()+dy));
		}
		return pm;
	}
	
	private static boolean inBounds(int x, int y){
		return 0 <= x && x < 8 && 0 <= y && y < 8;
	}
}
