package a1515.bomb4you;

public class Player {

    public String Name=null;
    public int Gold=0;
    public int Cash=0;
    public int Score=0;
    public String ClanName=null;
    public String GameMode=null;

    public void setGameMode(String mode){
        GameMode=mode;
    }
    public void setName(String name){
        Name=name;
    }
    public void AddCash(int amount){
        Cash=Cash+amount;
    }
    public void AddGold(int amount){
        Gold=Gold+amount;
    }
    public void RemoveCash(int amount){
        Cash=Cash-amount;
    }
    public void RemoveGold(int amount){
        Gold=Gold-amount;
    }
    public void SetClan(String name){
        ClanName=name;
    }
    public void RemoveClan(){
        ClanName=null;
    }
    public int getGold(){
        return Gold;
    }
    public int getCash(){
        return Cash;
    }
    public int getScore(){
        return Score;
    }
}
