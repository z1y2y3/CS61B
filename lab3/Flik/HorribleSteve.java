public class HorribleSteve {
    public static void main(String[] args) {
        int i = 0;
        for (; i < 500; i++) {
            if (!Flik.isSameNumber(i, i)) {   //for all numbers < 500
                break; // break exits the for loop!
            }
        }
        System.out.println("i is " + i);
    }
}