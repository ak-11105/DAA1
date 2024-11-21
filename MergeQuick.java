package daa;
import java.util.*;
public class MergeQuick {
	static public int partition(int arr[],int start,int end) {
		int pivotIndex = start;
		int count = 0;
		for(int i = start; i <= end; ++i) {
			if(arr[i] < arr[pivotIndex])
				++count;
		}
		pivotIndex = start + count;
		swap(arr,pivotIndex,start);
		int i = start,j = end;
		while(i < pivotIndex && j > pivotIndex) {
			while(arr[i] < arr[pivotIndex]) {
				++i;
			}
			while(arr[j] > arr[pivotIndex]){
				--j;
			}
			if(i < pivotIndex && j > pivotIndex) {
				swap(arr,i,j);
				++i;
				--j;
			}
		}
		return pivotIndex;
	}
	static void swap(int[] arr,int i,int j) {
		int temp = arr[i];
		arr[i] = arr[j];
		arr[j] = temp;
	}
	static public void merge(int arr[],int start,int end) {
		int mid = start + (end - start)/2;
		int len1 = mid - start + 1;
		int len2 = end - mid;
		int[] first = new int[len1];
		int[] second = new int[len2];
		int mainIndex = start;
		for(int i = 0; i < len1; ++i) {
			first[i] = arr[mainIndex++];
		}
		mainIndex = mid + 1;
		for(int i = 0; i < len2; ++i) {
			second[i] = arr[mainIndex++];
		}
		int index1 = 0;
		int index2 = 0;
		mainIndex = start;
		while(index1 < len1 && index2 < len2) {
			if(first[index1] < second[index2]) {
				arr[mainIndex] = first[index1];
				++index1;
				++mainIndex;
			}
			else {
				arr[mainIndex] = second[index2];
				++index2;
				++mainIndex;
			}
		}
		while(index1 < len1) {
			arr[mainIndex++] = first[index1++];
		}
		while(index2 < len2) {
			arr[mainIndex++] = second[index2++];
		}
	}
	static public void MergeSort(int arr[],int start,int end) {
		if(start >= end)
			return ;
		int mid = start + (end - start)/2;
		MergeSort(arr,start,mid);
		MergeSort(arr,mid + 1,end);
		merge(arr,start,end);
	}
	static public void QuickSort(int arr[],int start,int end) {
		if(start >= end)
			return;
		int pivot = partition(arr,start,end);
		QuickSort(arr,start,pivot - 1);
		QuickSort(arr,pivot + 1,end);
	}
	public static int[] generateRandomArray(int size) {
        Random rand = new Random();
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = rand.nextInt(1000); // Random integers from 0 to 999
        }
        return array;
    }
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter the size of the Array:");
		int size = sc.nextInt();
		int[] arr = generateRandomArray(size);
//		int[] arr = new int[size];
		
//		System.out.println("Enter " + size + " numbers:");
//		for(int i = 0; i < size; ++i) {
//			arr[i] = sc.nextInt();
//		}
//		for(int i = 0; i < size; ++i) {
//			System.out.print(arr[i] + " ");
//		}
//		System.out.println();
		System.out.println("After Sorting:");
		//QuickSort(arr,0,size - 1);
		long start = System.nanoTime();
		MergeSort(arr,0,size - 1);
		long end = System.nanoTime();
		for(int i = 0; i < size; ++i) {
			System.out.print(arr[i] + " ");
		}
		System.out.println("Time Requried for MergeSort : " + (end - start) + "ns");
	}
}
