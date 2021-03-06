package cores.objects;

import cores.characters.Player;

import java.util.Random;

public class Upgrade {
    private Player.SkillType upgradeType;
    private int upgradeLvl;
    private String upgradeName;

    public Upgrade(Player.SkillType type, int lvl, String name) {
        this.upgradeType = type;
        this.upgradeLvl = lvl;
        this.upgradeName = name;
    }

    public String toString() {
        return upgradeName + " (increases " + upgradeType.getName()
                + " skill by " + upgradeLvl + ")";
    }

    public int getUpgradeLvl() {
        return upgradeLvl;
    }

    public static Upgrade getRandomUpgrade(Player player, int techLevel) {
        //upgrade item generation
        Random upGen = new Random();
        int diff = player.getGame().getDifficulty().ordinal();
        Player.SkillType upgradeType
                = Player.SkillType.values()[upGen.nextInt(Player.SkillType.values().length)];
        int upgradeLvl = ((6 / (diff + 1)) + (upGen.nextInt(techLevel + 1)));
        String upgradeName = "";
        String bet = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < techLevel / 2; ++i) {
            sb.append(bet.charAt(upGen.nextInt(bet.length())));
        }

        String nameGen = sb.toString();
        switch (upgradeType) {
        case PIL:
            upgradeName = nameGen.toUpperCase() + "corp Engine Upgrade v"
                    + (upGen.nextInt(9) + 1) + "." + (upGen.nextInt(9) + 1);
            break;
        case FIG:
            upgradeName = nameGen.toUpperCase() + "corp Weapons Module v"
                    + (upGen.nextInt(9) + 1) + "." + (upGen.nextInt(9) + 1);
            break;
        case MER:
            upgradeName = ((char) (upGen.nextInt(26) + 'A')) + "/"
                    + ((char) (upGen.nextInt(26) + 'A'))
                    + " TradeAssist Mk. " + (upGen.nextInt(9) + 1);
            break;
        case ENG:
            upgradeName = nameGen.toUpperCase() + "corp Multitool "
                    + (upGen.nextInt(9) + 1) + "-" + (upGen.nextInt(9) + 1);
            break;
        default:
            throw new UnknownError("Unknown Error!");
        }
        return new Upgrade(upgradeType, upgradeLvl, upgradeName);
    }

    public int getPrice() {
        return upgradeLvl * 10;
    }

    public Player.SkillType getUpgradeType() {
        return upgradeType;
    }
}
