

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
        this.tileArray = new Tile[36];
        //for testing purposes for now
        for(int i = 0; i < 36; ++i){

			String num_str = Integer.toString(i);
            tileArray[i] = new PropertyTile("Property"+num_str, (i + 1) * 10, i);

        }

        //Going for 28 properties and 8 action tiles
        /*
        //cheap properties to start - Friday night favorites
        tileArray[0] = new PropertyTile("Antoons Pizza", 50, 8);
        tileArray[1] = new PropertyTile("Peter's Pub", 60, 12);


        //monopoly block - freshman basics
        tileArray[2] = new PropertyTile("Market Central", 90, 14);
        tileArray[3] = new PropertyTile("Market-to-Go", 100, 16);
        tileArray[4] = new PropertyTile("The Perch", 110, 17);

        //action tiles example
        tileArray[5] = new ActionTile("You lose your wallet on the way to class. Lose 60 dollars");
        tileArray[6] = new ActionTile("You win a beauty contest. Win 100 dollars");


        //insert actions maybe? - utility PropertyTile?

        //monopoly block - mexican food trifecta
        tileArray[8] = new PropertyTile("Qudoba", 150, 20);
        tileArray[9] = new PropertyTile("Las Palmas Tacos", 160, 21);
        //space for another action?
        tileArray[11] = new PropertyTile("Chipotle", 160, 22);
        //insert actions?


        //monopoly block - Schenly Plaza
        tileArray[13] = new PropertyTile("Schenly Lawn", 180, 23); //cant really own this? change it?
        //space for another action?
        tileArray[15] = new PropertyTile("The Porch Restaurant", 200, 25);
        tileArray[16] = new PropertyTile("Asia Tea House", 210, 25);
        //actions?


        //monopoly block - Union Dining
        tileArray[18] = new PropertyTile("Pizza Hut", 220, 27);
        //space for another action?
        tileArray[20] = new PropertyTile("Taco Bell", 230, 29);
        tileArray[21] = new PropertyTile("Sub Connection", 230, 30);



        //monopoly block - Deep South O
        tileArray[22] = new PropertyTile("Larry and Carol's Pizza", 250, 30);
        tileArray[23] = new PropertyTile("Bootleggers", 250, 32);
        //space for another action?
        tileArray[25] = new PropertyTile("Campus Deli", 260, 32);


        //monopoly block - Academic Buildings
        tileArray[27] = new PropertyTile("Posvar Hall", 260, 34);
        tileArray[28] = new PropertyTile("Lawrence Hall", 280, 35);
        //space for another action?
        tileArray[30] = new PropertyTile("Sennot Square", 280, 36);
        //3 spaces for actions?


        //monopoly block - The big properties of Pitt
        tileArray[34] = new PropertyTile("Heinz Chapel", 320, 38);
        //space
        tileArray[34] = new PropertyTile("Professor Laboon's Office", 340, 40);
        //space for another action?
        tileArray[36] = new PropertyTile("The Cathedral of Learning", 380, 44);
        */
    }

	public Tile getTile(int index) {
        return tileArray[index];
    }
}
