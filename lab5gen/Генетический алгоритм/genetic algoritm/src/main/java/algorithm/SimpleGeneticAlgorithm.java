package algorithm;


import lombok.Data;

@Data
public class SimpleGeneticAlgorithm {

    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.025;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private static byte[] solution = new byte[64];

    public boolean runAlgorithm(int populationSize, String solution) {

        /* 1)  На этапе инициализации мы задаём нашу Population как фиксированную двоичную битовую строку.
               Указываем размер Population и ожидаемое окончательное решение */
        if (solution.length() != SimpleGeneticAlgorithm.solution.length) {
            throw new RuntimeException("The solution needs to have " + SimpleGeneticAlgorithm.solution.length + " bytes");
        }

        //  2) Сохраняем желаемое решение и создаём случайную Population
        setSolution(solution);
        Population myPop = new Population(populationSize, true);

        int generationCount = 1;

        // 3) В основном цикле программы мы переходим к evaluate each Individual by the fitness function
        // (простыми словами, чем лучше Individual, тем большее значение фитнес-функции он получает)
        while (myPop.getFittest().getFitness() < getMaxFitness()) {
            System.out.println("Generation: " + generationCount + " Correct genes found: " + myPop.getFittest().getFitness());
            myPop = evolvePopulation(myPop);
            generationCount++;
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes: ");
        System.out.println(myPop.getFittest());
        return true;
    }

    public Population evolvePopulation(Population pop) {
        int elitismOffset;
        Population newPopulation = new Population(pop.getIndividuals().size(), false);

        // 5) На этом этапе нам нужно создать новую Population. Во-первых, нам нужно выбрать два родительских Individual
        // из Population, в соответствии с их пригодностью. Главное - позволить лучшим Individual из текущего поколения
        // переноситься на следующее без изменений. Эта стратегия называется Elitism.
        if (elitism) {
            newPopulation.getIndividuals().add(0, pop.getFittest());
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }

        // 8) В кроссовере мы меняем местами биты из каждого выбранного Individual в случайно выбранном месте.
        // Весь процесс выполняется внутри следующего цикла.
        for (int i = elitismOffset; i < pop.getIndividuals().size(); i++) {
            Individual indiv1 = tournamentSelection(pop);
            Individual indiv2 = tournamentSelection(pop);
            Individual newIndiv = crossover(indiv1, indiv2);
            newPopulation.getIndividuals().add(i, newIndiv);
        }
        // 9) После кроссовера новое потомство отправляется в новую Population. Этот шаг называется Acceptance.
        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            mutate(newPopulation.getIndividual(i));
        }

        return newPopulation;
    }


    // 4) Vы сравниваем два объекта Individual по символам. Если мы не можем найти идеальное решение,
    // нам нужно перейти к следующему шагу, который представляет собой эволюцию Population.
    protected static int getFitness(Individual individual) {
        int fitness = 0;
        for (int i = 0; i < individual.getDefaultGeneLength() && i < solution.length; i++) {
            if (individual.getSingleGene(i) == solution[i]) {
                fitness++;
            }
        }
        return fitness;
    }

    //6) Чтобы выбрать два лучших Individual, мы собираемся применить tournament selection strategy:
    private Individual tournamentSelection(Population pop) {
        Population tournament = new Population(tournamentSize, false);
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.getIndividuals().size());
            tournament.getIndividuals().add(i, pop.getIndividual(randomId));
        }
        Individual fittest = tournament.getFittest();
        return fittest;
    }

    // 7) Победитель каждого этапа "турнира" (наиболее подготовленный) отправляется в следующий этап Crossover.
    private Individual crossover(Individual indiv1, Individual indiv2) {
        Individual newSol = new Individual();
        for (int i = 0; i < newSol.getDefaultGeneLength(); i++) {
            if (Math.random() <= uniformRate) {
                newSol.setSingleGene(i, indiv1.getSingleGene(i));
            } else {
                newSol.setSingleGene(i, indiv2.getSingleGene(i));
            }
        }
        return newSol;
    }


    //10) Mutation. Мутация используется для сохранения генетического разнообразия от одного
    // поколения Population к следующему. В данном случае выполняется случайная побитовая инвертация.
    private void mutate(Individual indiv) {
        for (int i = 0; i < indiv.getDefaultGeneLength(); i++) {
            if (Math.random() <= mutationRate) {
                byte gene = (byte) Math.round(Math.random());
                indiv.setSingleGene(i, gene);
            }
        }
    }

    // После этого нам достаточно повторять действия из пунктов 3-9 до тех пор, пока не удастся достичь полного совпадения.

    protected int getMaxFitness() {
        int maxFitness = solution.length;
        return maxFitness;
    }

    protected void setSolution(String newSolution) {
        solution = new byte[newSolution.length()];
        for (int i = 0; i < newSolution.length(); i++) {
            String character = newSolution.substring(i, i + 1);
            if (character.contains("0") || character.contains("1")) {
                solution[i] = Byte.parseByte(character);
            } else {
                solution[i] = 0;
            }
        }
    }

}
