import org.junit.Test;
import static org.junit.Assert.*;

public class GameBoardTest {
	
	@Test
	public void testGetTileCategoryID1(){
		GameBoard gb = new GameBoard(0, 0, 1, 1);
		assertEquals(gb.getTileCategoryID(20), 3);
		
	}
	
	@Test
	public void testGetTileCategoryID2(){
		GameBoard gb = new GameBoard(0, 0, 1, 1);
		assertEquals(gb.getTileCategoryID(11), 2);
		
	}
	
	@Test
	public void testGetTileCategoryID3(){
		GameBoard gb = new GameBoard(0, 0, 1, 1);
		assertEquals(gb.getTileCategoryID(2), 1);
		
	}
	
	@Test
	public void testGetTileCategoryID4(){
		GameBoard gb = new GameBoard(0, 0, 1, 1);
		assertEquals(gb.getTileCategoryID(29), 4);
		
	}
	
	@Test
	public void testGetTileCategoryID5(){
		GameBoard gb = new GameBoard(0, 0, 1, 1);
		assertEquals(gb.getTileCategoryID(18), 0);
		
	}
}
