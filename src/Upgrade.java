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
        switch(upgradeType) {
            case PIL:
                return upgradeName + " (increases Pilot skill by " + upgradeLvl + ")";
            case FIG:
                return upgradeName + " (increases Fighter skill by " + upgradeLvl + ")";
            case MER:
                return upgradeName + " (increases Merchant skill by " + upgradeLvl + ")";
            case ENG:
                return upgradeName + " (increases Engineer skill by " + upgradeLvl + ")";
        }
        return null;
    }
}
