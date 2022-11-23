import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Game {
    private final int DIGITNUMBER = 3;
    private final ArrayList<Digit> myNumber;
    private ArrayList<Digit> userNumber;

    private int ballCount;
    private int strikeCount;
    private boolean isNothing = true;

    private boolean isOver = false;

    Game() {
        System.out.println("숫자 야구 게임을 시작합니다.");
        myNumber = new ArrayList<>();
        userNumber = new ArrayList<>();
        createNumber();
    }

    private void createNumber() {
        for (int i = 0; i < DIGITNUMBER; i++) {
            myNumber.add(new Digit(myNumber.size(), myNumber));
        }
    }

    public void increaseBall() {
        ballCount += 1;
    }

    public void increaseStrike() {
        strikeCount += 1;
    }

    private boolean isInputWrong(String input) {
        if (input.length() != DIGITNUMBER) { //숫자 개수 검사
            return true;
        }

        HashSet<Character> hashSet = new HashSet<>();
        for (int i = 0; i < input.length(); i++) { //숫자인지 검사
            char currentChar = input.charAt(i);
            hashSet.add(currentChar);
            if (!Character.isDigit(currentChar)) {
                return true;
            }
        }

        if (input.length() != hashSet.size()) { //숫자 중복 검사
            return true;
        }

        return false;
    }

    private void getUserInput() throws IOException {
        System.out.println("숫자를 입력해주세요");

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String input = br.readLine();

        if (isInputWrong(input)) {
            throw new IllegalArgumentException();
        }

        for (int i = 0; i < DIGITNUMBER; i++) {
            userNumber.add(new Digit(i, Integer.parseInt(String.valueOf(input.charAt(i)))));
        }
    }

    public void printResult() {
        if (isNothing) {
            System.out.println("Nothing!");
        } else if (strikeCount == 3) {
            System.out.println("3스트라이크");
            isOver = true;
        } else {
            System.out.println(ballCount + "ball " + strikeCount + "strike");
        }
    }

    public void initValues() {
        ballCount = 0;
        strikeCount = 0;
        isNothing = true;
        userNumber = new ArrayList<>();
    }

    public class Digit {
        private final int order;
        private final int number;

        Digit(int order, ArrayList<Digit> myNumber) {
            Random random = new Random();
            this.order = order;
            int temp = random.nextInt(10);
            while (isSameNumber(myNumber, temp)) {
                temp = random.nextInt(10);
            }
            this.number = temp;
        }

        Digit(int order, int number) {
            this.order = order;
            this.number = number;
        }

        private boolean isSameNumber(ArrayList<Digit> myNumber, int temp) {
            for (Digit digit : myNumber) {
                if (digit.getNumber() == temp) {
                    return true;
                }
            }
            return false;
        }

        public int getNumber() {
            return number;
        }

        public int getOrder() {
            return order;
        }

        public void checkUserNumber(ArrayList<Digit> myNumber) {
            for (int i = 0; i < DIGITNUMBER; i++) {
                Digit digit = myNumber.get(i);
                if (digit.getNumber() == this.number) {
                    if (digit.getOrder() == this.order) {
                        increaseStrike();
                        isNothing = false;
                    } else {
                        increaseBall();
                        isNothing = false;
                    }
                }
            }
        }
    }

    public void playGame() throws IOException {
        getUserInput();

        for (int i = 0; i < DIGITNUMBER; i++) {
            userNumber.get(i).checkUserNumber(myNumber);
        }

        printResult();

        if (isOver) {
            return;
        } else {
            initValues();
        }

    }

    public static void main(String[] args) throws IOException {
        Game game = new Game();

        while (true) {
            game.playGame();
        }
    }
}