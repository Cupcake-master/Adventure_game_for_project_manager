package ru.itis;

import ru.itis.models.Option;
import ru.itis.models.ProjectManager;
import ru.itis.models.Task;
import ru.itis.utils.Changer;
import ru.itis.utils.Inspector;

import java.util.*;

public class Main {
    private static ProjectManager pm;

    public static void main(String[] args) {
        pm = new ProjectManager();
        System.out.println(pm);
        Tasker tasker = new Tasker("src\\main\\resources\\answers.txt",
                "src\\main\\resources\\tasks.txt");

        for (Task task : tasker.getTasks()) {
            solving(task);
        }
    }

    private static void solving(Task task) {
        Scanner sc = new Scanner(System.in);
        System.out.println(task.getCondition());
        List<Option> options = new ArrayList<>(task.getOptionsAndEffects().keySet());
        options.stream().map(answer
                -> answer.getSelectionCondition() + " " + answer.getText()).forEach(System.out::println);
        System.out.println("[1/2/3] ?");
        try {
            int i = sc.nextInt();
            Option option = options.get(i - 1);
            if (!Inspector.checkingThePossibilityOfOptions(pm, option.getSelectionCondition())) {
                System.out.println("This option is not available to you by condition: "
                        + option.getSelectionCondition());
                solving(task);
            }else{
                String s = task.getOptionsAndEffects().get(option);
                Changer.dataChange(pm, s);
                System.out.println(pm);
            }
        } catch (InputMismatchException | IndexOutOfBoundsException ex) {
            System.out.println("You entered an invalid character. Choose an answer option!");
            solving(task);
        }
    }
}
