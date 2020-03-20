package cores.NPCEncounters;

public interface NPC {
    //Some methods will be implemented in the future.
    static String getImage() {
        int rand = (int) (Math.random() * 13);
        return "file:src/images/spaceships/" + rand + ".png";
    }
}
