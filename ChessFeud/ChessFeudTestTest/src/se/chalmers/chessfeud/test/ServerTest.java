package se.chalmers.chessfeud.test;

import se.chalmers.chessfeud.constants.DbHandler;
import android.test.AndroidTestCase;

public class ServerTest extends AndroidTestCase {
	
	private DbHandler dbh = DbHandler.getInstance();
	
	public void testLogin() {
		assertTrue(dbh.login("grubla", "lol"));
	}
	public void testAddUser() {
		dbh.addUser("foo@bar.com", "test", "testpw");
		boolean b = dbh.login("test", "testpw");
		dbh.deleteUser("test");
		assertTrue(b);
	}
	public void testDeleteUser() {
		dbh.addUser("foo@bar.com","delete","delete");
		boolean b = dbh.userExists("delete");
		dbh.deleteUser("delete");
		assertTrue(b && !dbh.userExists("delete"));
	}
	public void testUserExists() {
		dbh.addUser("foo@bar.com", "exists", "exists");
		dbh.addUser("hej", "exists2", "exists2");
		boolean b = dbh.userExists("exists");
		boolean b2 = dbh.userExists("exists2");
		dbh.deleteUser("exists");
		dbh.deleteUser("exists2");
		assertTrue(b&&b2);
	}
	public void testNewGame() {
		dbh.addUser("asd", "newTest", "pass");
		
	}
}
