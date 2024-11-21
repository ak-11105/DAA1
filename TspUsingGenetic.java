package daa;
import java.util.*;

public class TspUsingGenetic{

	static int numCities=4;
	static Random rand = new Random();
	static int[][] distMatrix = {
			{0,10,15,20},
			{5,0,9,10},
			{6,13,0,12},
			{8,8,9,0}
	};
//	Fitness Function
	public static int calculateTourLength(int[] tour) {
		int length = 0;
		for(int i=0;i<tour.length-1;i++) {
			length+=distMatrix[tour[i]][tour[i+1]];
		}
		length+=distMatrix[tour[tour.length-1]][0];
		return length;
	}
//	crossover Funtion
	
//	parent1 = [0,1,2,3,4]
//		parent2 = [3,1,2,4,0]
//			child = [0,1,3,2,4]
			
	public static int[] crossover(int[] parent1,int[] parent2) {
		int[] child = new int[numCities];
		int crossoverPoint = rand.nextInt(numCities);
		for(int i=0;i<crossoverPoint;i++) {
			child[i] = parent1[i];
		}
		boolean[] visited = new boolean[numCities];
		for(int i=0;i<crossoverPoint;i++) {
			visited[child[i]]=true;
		}
		int currIndex = crossoverPoint;
		for(int i=0;i<numCities;i++) {
			if(!visited[parent2[i]]) {
				child[currIndex++] = parent2[i];
			}
		}
		return child;
	}
//	child = [0,1,3,2,4]
//			
//			i = 2
//			j = 4		
//	child = [0,1,4,2,3]
			
//	Mutation Function
	public static void mutate(int[] tour) {
		int i = rand.nextInt(numCities);
		int j = rand.nextInt(numCities);
		while(i==j) {
			j = rand.nextInt(numCities);
		}
		int temp = tour[i];
		tour[i] = tour[j];
		tour[j] = temp;
	}
//	Generate random chromosome
	public static int[] generateRandomChromosome() {
		int[] chromosome = new int[numCities];
		for(int i=0;i<numCities;i++) {
			chromosome[i] = i;
		}
		for(int i=0;i<numCities;i++) {
			int j = rand.nextInt(numCities);
			int temp = chromosome[i];
			chromosome[i] = chromosome[j];
			chromosome[j] = temp;
		}
		return chromosome;
	}
//	Genetic algorithm
	public static int[] genTSP(int generations,int populationSize) {
		int[][] population = new int[populationSize][numCities];
		for(int i=0;i<populationSize;i++) {
			population[i] = generateRandomChromosome();
		}
		for(int gen=0;gen<generations;gen++) {
			int[] fitness = new int[populationSize];
			for(int i=0;i<populationSize;i++) {
				fitness[i] = calculateTourLength(population[i]);
			}
			int[] parent1 = population[0];
			int[] parent2 = population[1];
			int bestFitness = Integer.MAX_VALUE;
			int secondBest = Integer.MAX_VALUE;
			for(int i=0;i<populationSize;i++) {
				if(bestFitness>fitness[i]) {
					parent2 = parent1;
					secondBest = bestFitness;
					bestFitness=fitness[i];
					parent1 = population[i];
				}else if(secondBest>fitness[i]) {
					secondBest = fitness[i];
					parent2 = population[i];
				}
			}
			int[][] newPopulation = new int[populationSize][numCities];
			for(int i=0;i<populationSize;i++) {
				int[] child = crossover(parent1,parent2);
				mutate(child);
				newPopulation[i] = child;
			}
			population = newPopulation;
		}
		int[] bestTour = population[0];
		int bestTourLength = calculateTourLength(bestTour);
		for(int i=1;i<populationSize;i++) {
			int tourLength = calculateTourLength(population[i]);
			if(tourLength<bestTourLength) {
				bestTour = population[i];
				bestTourLength = tourLength;
			}
		}
		return bestTour;
	}
	private static void printChromosome(int[] bestTour) {
		System.out.println(Arrays.toString(bestTour));
	}
	public static void main(String[] args) {
		int generations = 500;
		int populationSize = 50;
		int[] bestTour = genTSP(generations,populationSize);
		System.out.println("Best Tour: ");
	    printChromosome(bestTour);
	    System.out.println("Total Distance: " + calculateTourLength(bestTour));
	}
	
}