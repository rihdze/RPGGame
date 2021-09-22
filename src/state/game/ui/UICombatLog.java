package state.game.ui;

import UI.*;
import core.Size;
import state.State;

import javax.swing.*;
import java.awt.*;


public class UICombatLog extends VerticalContainer {

    private UIText combatLog;
    private UIText currentWeapon;
    private UIText currentPotion;
    private String test;


    public UICombatLog(Size windowSize) {
        super(windowSize);


        this.alignment = new Alignment(Alignment.Position.START, Alignment.Position.END);
        this.combatLog = new UIText("Equipment:");

        this.currentWeapon = new UIText("");
        this.currentPotion = new UIText("");

        setFixedSize(new Size(400, 150));
        setBackgroundColor(Color.DARK_GRAY);
        addUIComponent(combatLog);
        addUIComponent(new UIText("Weapon:"));
        addUIComponent(currentWeapon);
        addUIComponent(new UIText("Potion:"));
        addUIComponent(currentPotion);
    }

    @Override
    public void update(State state){
        super.update(state);
        if(state.getPlayer().getWeapon() != null){
            currentWeapon.setText(state.getPlayer().getWeapon().getWeaponName_DB()+ ", weapon damage: " + String.valueOf(state.getPlayer().getWeapon().getWeaponDamage_DB()));
        } else {
            currentWeapon.setText("no weapon equipped");
        }
        if(state.getPlayer().getPotion() != null){
            currentPotion.setText(state.getPlayer().getPotion().getPotionName_DB());
        } else {
            currentPotion.setText("no potion equipped");
        }

    }
}
