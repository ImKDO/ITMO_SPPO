package org.example.InterfaceAnimal;

public enum LevelVoice {
    HIGH(" громко"),
    MEDIUM(" средне"),
    LOW(" тихо");
    private String  lvl_voice;
    LevelVoice(String lvl_voice){
        this.lvl_voice = lvl_voice;
    }
    public String getLvl_voice(){
        return lvl_voice;
    }

}
