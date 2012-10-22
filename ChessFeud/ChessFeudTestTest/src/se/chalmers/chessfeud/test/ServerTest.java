package se.chalmers.chessfeud.test;

import se.chalmers.chessfeud.constants.DbHandler;
import se.chalmers.chessfeud.constants.Game;
import se.chalmers.chessfeud.constants.PlayerInfo;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.InstrumentationTestCase;

public class ServerTest extends InstrumentationTestCase {
	
	private DbHandler dbh = DbHandler.getInstance();
	/**
	 * Tests the login.
	 */
	public void testLogin() {
		assertTrue(dbh.login("grubla", "lol"));
	}
	/**
	 * Adds a user to the db and checks if its there.
	 */
	public void testAddUser() {
		dbh.addUser("foo@bar.com", "test", "testpw");
		boolean b = dbh.login("test", "testpw");
		dbh.deleteUser("test");
		assertTrue(b);
	}
	/**
	 * Adds a user and then deletes in and checks if its still there.
	 */
	public void testDeleteUser() {
		dbh.addUser("foo@bar.com","delete","delete");
		boolean b = dbh.userExists("delete");
		dbh.deleteUser("delete");
		assertTrue(b && !dbh.userExists("delete"));
	}
	/**
	 * Adds two users to the database and checks if they exist.
	 */
	public void testUserExists() {
		dbh.addUser("foo@bar.com", "exists", "exists");
		dbh.addUser("hej", "exists2", "exists2");
		boolean b = dbh.userExists("exists");
		boolean b2 = dbh.userExists("exists2");
		dbh.deleteUser("exists");
		dbh.deleteUser("exists2");
		assertTrue(b&&b2);
	}
	/**
	 * Adds a new game to the db between two players, and then checks if the getGames returns 1 game, then sets the game finished.
	 */
	public void testNewGame() {
		dbh.addUser("asd", "newgametest", "pass");
		dbh.addUser("asd", "newgametest2", "pass");
		PlayerInfo player = PlayerInfo.getInstance();
		player.tryLogin(getInstrumentation().getContext());
		player.login("newgametest", "pass", getInstrumentation().getContext());
		dbh.newGame("newgametest2", new ChessModel(0).exportModel());
		boolean b = (dbh.getGames().size() == 1);
		dbh.setGameFinished("newgametest2");
		dbh.deleteUser("newgametest");
		dbh.deleteUser("newgametest2");
		assertTrue(b);
	}
	/**
	 * Makes a move with an existing game and then checks if the move is saved to the database.
	 */
	public void testNewMove() {
		dbh.addUser("asd", "newmovetest", "pass");
		dbh.addUser("asd", "newmovetest2", "pass");
		PlayerInfo player = PlayerInfo.getInstance();
		player.tryLogin(getInstrumentation().getContext());
		player.login("newmovetest", "pass", getInstrumentation().getContext());
		dbh.newGame("newmovetest2", new ChessModel(0).exportModel());
		ChessModel c = new ChessModel(0);
		c.click(new Position(0, 6));
		c.click(new Position(0,5));
		dbh.newMove("newmovetest2", c.exportModel());
		boolean b = new Game(dbh.getGames().get(0),0).getGameBoard().equals(c.exportModel());
		dbh.setGameFinished("newmovetest2");
		dbh.deleteUser("newmovetest");
		dbh.deleteUser("newmovetest2");
		assertTrue(b);
	}
	/**
	 * Tests the method getStats().
	 */
	public void testGetStats() {
		dbh.addUser("asd", "getstatstest", "pass");
		PlayerInfo player = PlayerInfo.getInstance();
		player.tryLogin(getInstrumentation().getContext());
		player.login("getstatstest", "pass", getInstrumentation().getContext());
		boolean b = (dbh.getStats().equals("0/0/0/0"));
		dbh.incDraws();
		dbh.incLosses();
		dbh.incLosses();
		dbh.incWins();
		dbh.incWins();
		dbh.incWins();
		boolean b2 = (dbh.getStats().equals("3/2/1/0"));
		dbh.deleteUser("getstatstest");
		assertTrue(b && b2);
	}
	/**
	 * Adds two games and checks if the are saved and can be found with getgames.
	 */
	public void testGetGames() {
		dbh.addUser("asd", "ggtest", "pass");
		dbh.addUser("asd", "ggtest2", "pass");
		dbh.addUser("asd", "ggtest3", "pass");
		PlayerInfo player = PlayerInfo.getInstance();
		player.tryLogin(getInstrumentation().getContext());
		player.login("ggtest", "pass", getInstrumentation().getContext());
		dbh.newGame("ggtest2", new ChessModel(0).exportModel());
		dbh.newGame("ggtest3", new ChessModel(0).exportModel());
		boolean b = dbh.getGames().size() == 2;
		dbh.setGameFinished("ggtest2");
		dbh.setGameFinished("ggtest3");
		dbh.deleteUser("ggtest");
		dbh.deleteUser("ggtest2");
		dbh.deleteUser("ggtest3");
		assertTrue(b);
	}
	/**
	 * Test getFinishedGames.
	 */
	public void testGetFinishedGames() {
		dbh.addUser("asd", "gfgtest", "pass");
		dbh.addUser("asd", "gfgtest2", "pass");
		dbh.addUser("asd", "gfgtest3", "pass");
		PlayerInfo player = PlayerInfo.getInstance();
		player.tryLogin(getInstrumentation().getContext());
		player.login("gfgtest", "pass", getInstrumentation().getContext());
		dbh.newGame("gfgtest2", new ChessModel(0).exportModel());
		dbh.newGame("gfgtest3", new ChessModel(0).exportModel());
		dbh.setGameFinished("gfgtest3");
		boolean b = dbh.getFinishedGames().size() == 1;
		dbh.setGameFinished("gfgtest2");
		dbh.deleteUser("gfgtest");
		dbh.deleteUser("gfgtest2");
		dbh.deleteUser("gfgtest3");
		assertTrue(b);
	}
	/**
	 * Tests the methods incDraws, incLosses and incWins.
	 */
	public void testIncMethods() {
		dbh.addUser("asd", "inctest", "pass");
		PlayerInfo player = PlayerInfo.getInstance();
		player.tryLogin(getInstrumentation().getContext());
		player.login("inctest", "pass", getInstrumentation().getContext());
		dbh.incDraws();
		dbh.incDraws();
		dbh.incDraws();
		dbh.incLosses();
		dbh.incWins();
		dbh.incWins();
		dbh.incWins();
		boolean b = dbh.getStats().equals("3/1/3/0");
		dbh.incLosses();
		dbh.incLosses();
		dbh.incLosses();
		dbh.incDraws();
		dbh.incWins();
		boolean b2 = dbh.getStats().equals("4/4/4/0");
		dbh.deleteUser("inctest");
		assertTrue(b && b2);
	}
}
