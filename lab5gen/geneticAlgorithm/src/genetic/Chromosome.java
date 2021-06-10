package genetic;

public class Chromosome {
    public static final int GENES_COUNT = 5;
    public static final int TARGET_VALUE = 22;
    public static final float MUTATION_LIKELIHOOD1 = 5F;
    public static final float MUTATION_LIKELIHOOD2 = 10F;

    private int[] genes = new int[GENES_COUNT];
    private float fitness;

        public float getFitness() {
        return fitness;
    }

    public void setFitness(float fitness) {
        this.fitness = fitness;
    }

    public int[] getGenes() {
        return genes;
    }

    public void setGenes(int[] genes) {
        this.genes = genes;
    }

    private static int function(int u, int w, int x, int y, int z) {
        return z + u*u*w*x*y + w*w*x*y*y*z*z + u*u*w*w*x*y*z + u*w*w*x*y*z*z;
    }

    public float calculateFitness() {
        int u = genes[0];
        int w = genes[1];
        int x = genes[2];
        int y = genes[3];
        int z = genes[4];
        int closeness = Math.abs(TARGET_VALUE - function(u, w, x, y, z));
        System.out.println("Близость: " + closeness);
        return 0 != closeness ? 1 / (float) closeness : Population.TARGET_IS_REACHED_FLAG;
    }

    public Chromosome mutateWithGivenLikelihood(int childNumber) {
        System.out.println("-----Мутация: ");
        Chromosome result = (Chromosome) this.cloneChromosome();
        for (int i = 0; i < GENES_COUNT; i++) {
            float randomPercent = Main.getRandomFloat(0, 300);
            float mut = 0;
            if(childNumber <1)
                mut = MUTATION_LIKELIHOOD1;
            else
                mut = MUTATION_LIKELIHOOD2;

            if (randomPercent < mut) {
                int oldValue = result.getGenes()[i];
                int newValue = Population.getRandomGene();
                System.out.println("Мутация произошла в гене №" + i + ", старое значение = " + oldValue + ", новое значение = " + newValue);
                result.getGenes()[i] = newValue;
            }
        }
        System.out.println("----------");
        return result;
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append("(");
        for (int i = 0; i < GENES_COUNT; ++i) {
            result.append(genes[i]);
            result.append(i < GENES_COUNT - 1 ? ", " : "");
        }
        result.append(")");
        return result.toString();
    }

    private Object cloneChromosome() {
        Chromosome resultChromosome = new Chromosome();
        resultChromosome.setFitness(this.getFitness());
        resultChromosome.setGenes(this.genes.clone());
        return resultChromosome;
    }
}
