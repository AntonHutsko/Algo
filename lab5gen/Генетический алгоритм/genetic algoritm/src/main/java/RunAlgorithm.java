
import algorithm.SimpleGeneticAlgorithm;

import java.util.Scanner;

        //Алгоритм начинается с set of solutions (представленного individuals), называемого population.
        //Решения из одной популяции берутся и используются для формирования новой population, так как есть шанс,
        //что новая популяция будет лучше старой.
        //Элементы, выбранные для формирования новых решений (offspring), выбираются в соответствии с их fitness - чем
        //они более подходящие, тем больше у них шансов на воспроизведение.

public class RunAlgorithm {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        Scanner in = new Scanner(System.in);
        System.out.println("Run algorithm:");
        System.out.println("1 - Simple Genetic Algorithm");
        int decision = in.nextInt();
        switch (decision) {

            case 1:

                SimpleGeneticAlgorithm ga = new SimpleGeneticAlgorithm();
                ga.runAlgorithm(22, "0000000000000100100100100100100001000010010000101000100000100000");
                break;

            default:
                System.out.println("Unknown option");
                break;
        }
        in.close();
    }

}