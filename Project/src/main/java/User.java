import java.util.Objects;

public class User {
    public enum Color{
        WHITE,
        BLACK,
        YELLOW
    }
    private final int id;
    private final Color color;
    private int countArmy = 1;

//    private final int rating;

    public User(int id, Color color){
        this.id = id;
        this.color = color;
    }

    public int getId() {
        return id;
    }

    public Color getColor(){
        return color;
    }

    public int getCountArmy(){
        return countArmy;
    }

    // добавляем или удаляем войска
    public void addOrDeleteArmy(int addOrDeleteCountArmy){
        countArmy += addOrDeleteCountArmy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id && countArmy == user.countArmy && color == user.color;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, color, countArmy);
    }
}
