package org.example.Interface_Animal;

public enum Level_Voice{
    HIGH(" громко"),
    MEDIUM(" средне"),
    LOW(" тихо");
    private String  lvl_voice;
    Level_Voice(String lvl_voice){
        this.lvl_voice = lvl_voice;
    }
    public String getLvl_voice(){
        return lvl_voice;
    }

}
