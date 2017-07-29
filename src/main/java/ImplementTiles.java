

public class ImplementTiles{

    //Array of 36 Tiles
    private Tile tileArray[];

    //set a Tile at a specific location within the GameBoard (array location)
    //@param newTile - the tile to be added
    //@param location - the location in the array to add the new tile
    public void setTile(Tile newTile, int location){
        this.tileArray[location] = newTile;
    }

    //get a Tile at a specific location within the Gameboard/array
    //@param location - the location of the tile within the tileArray
    public Tile getTile(int location){
        return this.tileArray[location];
    }

    //change PropertyTile names and values if you'd like
    ImplementTiles(){
        this.tileArray = new Tile[GameBoard.TILE_COUNT];
        //for testing purposes

        /*
        for(int i = 0; i < 36; ++i){
            tileArray[i] = new ActionTile(8);

			String num_str = Integer.toString(i);
            tileArray[i] = new PropertyTile("Property"+num_str, (i + 1) * 10, i);

        }
        */
        //Going for 28 properties and 8 action tiles

        //cheap properties to start - Friday night favorites
        tileArray[0] = new PropertyTile("Antoons Pizza", 50, 8);
        tileArray[1] = new PropertyTile("Peter's Pub", 60, 12);


        //monopoly block - freshman basics
        tileArray[2] = new PropertyTile("Market Central", 90, 14);
        tileArray[3] = new PropertyTile("Market-to-Go", 100, 16);
        tileArray[4] = new PropertyTile("Bus 61A", 200, 50);

        //action tiles example
        tileArray[5] = new ActionTile(1);
        tileArray[6] = new ActionTile(2);
        tileArray[7] = new ActionTile(7);

        //insert actions maybe? - utility PropertyTile?

        //monopoly block - mexican food trifecta
        tileArray[8] = new PropertyTile("Qudoba", 150, 20);
        tileArray[9] = new PropertyTile("Las Palmas Tacos", 160, 21);
        //space for another action?
        tileArray[10] = new ActionTile(3);
        tileArray[11] = new PropertyTile("Chipotle", 160, 22);
        //insert actions?
        tileArray[12] = new ActionTile(4);

        //monopoly block - Schenly Plaza
        tileArray[13] = new PropertyTile("Bus 61B", 200, 50);
        //space for another action?
        tileArray[14] = new ActionTile(5);
        tileArray[15] = new PropertyTile("The Porch Restaurant", 200, 25);
        tileArray[16] = new PropertyTile("Asia Tea House", 210, 25);
        //actions?
        tileArray[17] = new ActionTile(6);
        //tileArray[18] = new ActionTile(1);

        //monopoly block - Union Dining
        tileArray[18] = new PropertyTile("Pizza Hut", 220, 27);
        //space for another action?
        tileArray[19] = new ActionTile(8);
        tileArray[20] = new PropertyTile("Taco Bell", 230, 29);
        tileArray[21] = new PropertyTile("Sub Connection", 230, 30);



        //monopoly block - Deep South O
        tileArray[22] = new PropertyTile("Bus 61C", 200, 50);
        tileArray[23] = new PropertyTile("Bootleggers", 250, 32);
        //space for another action?
        tileArray[24] = new ActionTile(2);
        tileArray[25] = new PropertyTile("Campus Deli", 260, 32);
        tileArray[26] = new ActionTile(5);

        //monopoly block - Academic Buildings
        tileArray[27] = new PropertyTile("Posvar Hall", 260, 34);
        tileArray[28] = new PropertyTile("Lawrence Hall", 280, 35);
        //space for another action?
        tileArray[29] = new ActionTile(3);
        tileArray[30] = new PropertyTile("Sennot Square", 280, 36);
        //3 spaces for actions?
        tileArray[31] = new PropertyTile("Bus 61D", 200, 50);
        tileArray[32] = new ActionTile(7);
        tileArray[33] = new ActionTile(6);
        //monopoly block - The big properties of Pitt
        //space
        tileArray[34] = new PropertyTile("Professor Laboon's Office", 340, 40);
        //space for another action?
        tileArray[35] = new PropertyTile("The Cathedral of Learning", 380, 44);

    }

}
