
import java.util.ArrayList;
import java.util.Random;
import javax.swing.*;

//representing an action tile on the gameboard
//actions can be positive or negative outcomes (hence conflicting attributes types)
public class ActionTile extends Tile{
	//represent the output on the board in tileInfo String
    private String tileInfo;
    private int tileFlag;

    //flags correlate to specific Actions in the class
    //flags start at 1 and correlate to scenio[x] found below
    ActionTile(int flag){

        this.tileFlag = flag;
        this.setTileName("Action Tile");
        switch(flag){
            case 1:
                this.tileInfo = "Wow, you found $100 on the sidewalk " +
                    "on your way to class. Nice find!";
                break;
            case 2:
                this.tileInfo = "The IRS showed up out of nowhere! You've been fined $250 " +
                    "for tax evasion! Bummer.";
                break;
            case 3:
                this.tileInfo = "Congratulations you won a beauty contest! Collect $300.";
                break;
            case 4:
                this.tileInfo = "Bank robbery successful! Congrats you got $500!";
                break;
            case 5:
                this.tileInfo = "Pay each player $50 for being quality friends!";
                break;
            case 6:
                this.tileInfo = "Redirect!";
                break;
            case 7:
                this.tileInfo = "Lucky Day";
                break;
            case 8:
                this.tileInfo = "Donate to Charity!";
                break;
            default:
                this.tileInfo = "invalid flage error";
        }

    }

    public String getTileInfo(){
        return this.tileInfo;
    }

    public int getTileFlag(){
        return this.tileFlag;
    }

    /*
    This is the function used to actually perform the action on a player. This
    method will be what is called from topMenu.java when a player lands on
    an actionTile.
    @param p - the player that has landed on the tile
    @parma pList - the list of all players in the game
    @return boolean - true if the action has taken place - false the flag is incorrect
        for some reason
    */
    public boolean performAction(Player p, ArrayList<Player> pList, OaklandOligarchy game, TopMenu.RollListener rl){
        switch(this.tileFlag){
            case 1:
                this.scenario1(p);
                break;
            case 2:
                this.scenario2(p);
                break;
            case 3:
                this.scenario3(p);
                break;
            case 4:
                this.scenario4(p);
                break;
            case 5:
                this.scenario5(p, pList);
                break;
            case 6:
                this.scenario6(p, game, rl);
                break;
            case 7:
                this.scenario7(p, pList);
                break;
            case 8:
                this.scenario8(p);
                break;
            default:
                return false;
        }
        return true;
    }

    //In this scenario the player finds a hundred dollar bill on the sidewalk
    private void scenario1(Player p){
        JOptionPane.showMessageDialog(null, this.getTileInfo());
        p.setMoney(p.getMoney() + 100);
    }

    //In this scenario the IRS shows up and sees you havent been paying taxes. fined 250.
    private void scenario2(Player p){
        JOptionPane.showMessageDialog(null, this.getTileInfo());
        p.setMoney(p.getMoney() - 250);
    }

    //Basic action to win money
    //@param p - the current player to apply action to
    private void scenario3(Player p){
        JOptionPane.showMessageDialog(null, this.getTileInfo());
        p.setMoney(p.getMoney() + 300);
    }

    //In this scenario you rob a bank and get 500 dollars
    private void scenario4(Player p){
        JOptionPane.showMessageDialog(null, this.getTileInfo());
        p.setMoney(p.getMoney() + 500);
    }

    //In this scenario you pay all other players 50 dollars
    //Note: this code will break if two players share the same name
    //@ pList - list of all players so they they may be awarded money
    private void scenario5(Player p, ArrayList<Player> pList){
        JOptionPane.showMessageDialog(null, this.getTileInfo());
        for(int i = 0; i < pList.size(); ++i){
            if(!pList.get(i).getName().equals(p.getName())){
                pList.get(i).setMoney(pList.get(i).getMoney() + 50);
                p.setMoney(p.getMoney() - 50);
            }
        }
    }

    //may not be able to move backward
    //In this scenario the player is pushed a random amount of spaces forward or backward
    private void scenario6(Player p, OaklandOligarchy game, TopMenu.RollListener rl){
        Random rand = new Random();
        int distance = rand.nextInt(10) + 1;
        int direction = rand.nextInt(2);
        //if direction = 0 -- backward
        if(direction == 0){
            distance *= -1;
            JOptionPane.showMessageDialog(null, "Move " + distance * -1 + " spaces backward!");
        }else{
            JOptionPane.showMessageDialog(null, "Move " + distance + " spaces forward!");
        }
        rl.animatedMovePlayer(game.getGameBoard(), game.getIndexCurrentTurnPlayer(), p.getPosition(), distance);
        p.setPosition((p.getPosition() + distance) % 36);
    }

    //select a specific player to take 100 dollars from
    //@param p - current player
    //@param pList - list of players
    private void scenario7(Player p, ArrayList<Player> pList){
        boolean found = false;
        do{
            String playerName = JOptionPane.showInputDialog("Your lucky day! Pick a player to take $100 from!");
            found = false;

            for(int i = 0; i < pList.size(); ++i){
                if(pList.get(i).getName().equals(playerName)){
                    found = true;
                    pList.get(i).setMoney(pList.get(i).getMoney() - 100);
                    p.setMoney(p.getMoney() + 100);
                    break;
                }
            }
            if(found == false){
                JOptionPane.showMessageDialog(null, "That name does not exist! Please re-enter a valid name.");
            }
        }while(found == false);
    }

    //player donates to charity and loses money
    //@param p - current player
    private void scenario8(Player p){
        JOptionPane.showMessageDialog(null, "Wow, what a generous deed. You donated $50 to charity!");
        p.setMoney(p.getMoney() - 50);
    }
}
