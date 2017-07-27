import org.junit.Test;
import static org.junit.Assert.*;

public class GameBoardTest {
	
	@Test
	public void testGetTileCategoryID1(){
		OaklandOligarchy oo = new OaklandOligarchy("test");
		GameBoard gb = new GameBoard(0, 0, 1, 1, oo);
		assertEquals(gb.getTileCategoryID(20), 3);
		
	}
	
	@Test
	public void testGetTileCategoryID2(){
		OaklandOligarchy oo = new OaklandOligarchy("test");
		GameBoard gb = new GameBoard(0, 0, 1, 1, oo);
		assertEquals(gb.getTileCategoryID(11), 2);
		
	}
	
	@Test
	public void testGetTileCategoryID3(){
		OaklandOligarchy oo = new OaklandOligarchy("test");
		GameBoard gb = new GameBoard(0, 0, 1, 1, oo);
		assertEquals(gb.getTileCategoryID(2), 1);
		
	}
	
	@Test
	public void testGetTileCategoryID4(){
		OaklandOligarchy oo = new OaklandOligarchy("test");
		GameBoard gb = new GameBoard(0, 0, 1, 1, oo);
		assertEquals(gb.getTileCategoryID(29), 4);
		
	}
	
	@Test
	public void testGetTileCategoryID5(){
		OaklandOligarchy oo = new OaklandOligarchy("test");
		GameBoard gb = new GameBoard(0, 0, 1, 1, oo);
		assertEquals(gb.getTileCategoryID(18), 0);
		
	}
}
