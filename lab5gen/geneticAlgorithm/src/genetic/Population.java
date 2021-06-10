package genetic;

import java.util.*;


public class Population {

    public static final int POPULATION_COUNT = 5;
    public static final int PAIR_COUNT = 2;
    public static final int GENE_MIN = -300;
    public static final int GENE_MAX = 300 ;
    public static final int TARGET_IS_REACHED_FLAG = -5;
    public static final int TARGET_NOT_REACHED_FLAG = -6;
    public static final int SIZE_OF_TOURNAMENT = 3;
    public static final int CHILD_COUNT = 2;
    public static final int SIZE_OF_CHILD_POPULATION = 10;

    private Chromosome[] population = new Chromosome[POPULATION_COUNT];

    public void createInitialPopulation() {
        System.out.println("-----Создание начальной популяции: ");
        for (int i = 0; i < population.length; i++) {
            System.out.println("Создание хромосомы №" + i);
            population[i] = new Chromosome();
            fillChromosomeWithRandomGenes(population[i]);
        }
        System.out.println("----------\n");
    }

    private void fillChromosomeWithRandomGenes(Chromosome chromosome) {
        System.out.println("-----Заполнение хромосомы случайными генами: ");
        for (int i = 0; i < Chromosome.GENES_COUNT; i++) {
            chromosome.getGenes()[i] = getRandomGene();
            System.out.println("Ген №" + i + " = " + chromosome.getGenes()[i]);
        }
        System.out.println("----------");
    }

    public static int getRandomGene() {
        return getRandomNumber(GENE_MIN, GENE_MAX);
    }

    private static int getRandomNumber(int min, int max) {
        return min + (int) ((1 + max - min) * Math.random());
    }

    public int fillChromosomesWithFitnesses() {
        System.out.println("-----Высчитывание приспособленности для всех хромосом: ");
        for (int i = 0; i < POPULATION_COUNT; i++) {
            float currentFitness = population[i].calculateFitness();
            population[i].setFitness(currentFitness);
            System.out.println("Приспособленность хромосомы № " + i + ": " + population[i].getFitness());
            if (currentFitness == TARGET_IS_REACHED_FLAG) return i;
        }
        System.out.println("----------");
        return TARGET_NOT_REACHED_FLAG;
    }

    public Chromosome[][] getPairsForCrossover(){
        System.out.println("-----Поиск пар для скрещивания:");
        Chromosome[][] pairs = new Chromosome[POPULATION_COUNT][PAIR_COUNT];
        for (int i = 0; i < POPULATION_COUNT; i++){
            pairs[i] = randomSelection(population, SIZE_OF_TOURNAMENT);
        }
        System.out.println("----------");
        return pairs;
    }

    private Chromosome[] randomSelection(Chromosome[] population, int sizeOfTournament) {
        System.out.println("Cлучайная селекция...");
            List<Chromosome> pair = new ArrayList<>();
        pair.add(population[getRandomNumber(0, POPULATION_COUNT-1)]);
        pair.add(population[getRandomNumber(0, POPULATION_COUNT-1)]);
        while(pair.get(0)==pair.get(1)){
            pair.set(1, population[getRandomNumber(0, POPULATION_COUNT-1)]);
        }
        System.out.println("Пара родителей по выбранная случайно: " + Arrays.toString(findBest(pair)));
        return findBest(pair);
    }

    private Chromosome[] findBest(List<Chromosome> tournament){
        Chromosome [] pair = new Chromosome[PAIR_COUNT];
        pair[0] = tournament.get(0);
        pair[1] = tournament.get(1);
        return pair;
    }

    private Chromosome[] crossoverSinglePoint(Chromosome parent1, Chromosome parent2) {
        System.out.println("Пропорциональное вероятностное скрещивание: ");
        System.out.println("Хромосома 1-ого родителя: " + parent1);
        System.out.println("Хромосома 2-ого родителя: " + parent2);
        Chromosome[] result = new Chromosome[CHILD_COUNT];
        result[0] = new Chromosome();
        result[1] = new Chromosome();
        for (int i = 0; i < Chromosome.GENES_COUNT; i++) {
            if (Math.random() < parent1.getFitness()/(parent1.getFitness()+parent2.getFitness())) {
                result[0].getGenes()[i] = parent1.getGenes()[i];
                result[1].getGenes()[i] = parent2.getGenes()[i];
            } else {
                result[0].getGenes()[i] = parent2.getGenes()[i];
                result[1].getGenes()[i] = parent1.getGenes()[i];
            }
        }
        System.out.println("Итогововая хромосома №0:\n" + result[0]);
        System.out.println("Итогововая хромосома №1:\n" + result[1]);
        System.out.println("----------");
        return result;
    }

    private float getAllFitnessesSum() {
        float allFitnessesSum = 0.0F;
        for (int i = 0; i < POPULATION_COUNT; ++i)
            allFitnessesSum += population[i].getFitness();
        return allFitnessesSum;
    }

    private Chromosome[] performCrossoverAndMutationForThePopulation(Chromosome[][] pairs) {
        Chromosome[] children = new Chromosome[SIZE_OF_CHILD_POPULATION];
        Chromosome[] result = new Chromosome[CHILD_COUNT];
        System.out.println("-----Скрещивания: ");
        for (int i = 0; i < SIZE_OF_CHILD_POPULATION; i+=2) {

            System.out.println("-----Скрещивание №" + i);
            Chromosome firstParent = pairs[i/2][0];
            Chromosome secondParent = pairs[i/2][1];

            if (i < 6) {
                result = crossoverSinglePoint(firstParent, secondParent);
            }
            else{
                result = crossoverSinglePoint(firstParent, secondParent);
            }
            children[i] = result[0];
            children[i+1] = result[1];
            System.out.println("Хромосома 1-го потомка перед мутацией: " + children[i]);
            children[i] = children[i].mutateWithGivenLikelihood(0);
            System.out.println("Хромосома 1-го потомка после мутации: " + children[i]);
            System.out.println("Хромосома 2-го потомка перед мутацией: " + children[i+1]);
            children[i+1] = children[i+1].mutateWithGivenLikelihood(1);
            System.out.println("Хромосома 2-го потомка после мутации: " + children[i+1]);
        }
        System.out.println("----------");
        return children;
    }

    public Chromosome[] getNewPopulation(Chromosome[][] pairs){
        Chromosome[] children  = performCrossoverAndMutationForThePopulation(pairs);
        Chromosome[] newPopulation = new Chromosome[POPULATION_COUNT];
        int index = 0;
        for (int i =0; i < children.length; i++){
            float currentFitness = children[i].calculateFitness();
            children[i].setFitness(currentFitness);
            if (currentFitness == TARGET_IS_REACHED_FLAG){
                newPopulation[index] = children[i];
                index++;
            }
        }

        float totalFitness = 0;
        index = 0;
        for (int i =0; i < children.length; i+=2) {
            totalFitness = children[i].getFitness() + children[i+1].getFitness() + population[index].getFitness();
            if (Math.random() < children[i].getFitness()/totalFitness)
                newPopulation[index] = children[i];
            else if (Math.random() < (children[i].getFitness() + children[i+1].getFitness())/totalFitness)
                newPopulation[index] = children[i+1];
            else if (Math.random() < 1)
                newPopulation[index] = population[index];
            index++;
        }
        System.out.println("\n Новая популяция: ");
        for (int i = 0; i < newPopulation.length; i++){
            System.out.println("Хромосома № " + i + ": ");
            System.out.println(newPopulation[i].toString());
        }
        System.out.println("\n");

        return newPopulation;
    }

    public Chromosome[] getPopulation() {
        return population;
    }

    public void setPopulation(Chromosome[] population) {
        this.population = population;
    }


}
