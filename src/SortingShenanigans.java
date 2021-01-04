import java.util.ArrayList;
import java.util.Scanner;
import java.util.Collections;
import java.util.Arrays;
import java.util.Random;

public class SortingShenanigans {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static final String[] colors = new String[]{
        ANSI_BLACK, ANSI_PURPLE, ANSI_BLUE, ANSI_CYAN,
        ANSI_GREEN, ANSI_YELLOW, ANSI_RED,
    };

    public static final String[] sorts = new String[] {
        "Bogo Sort", "Bubble Sort", "Selection Sort", "Insertion Sort",
        "Quick Sort", "Merge Sort", "Heap Sort"
    };
    public static void main(String[] args) throws Exception{
        //UI GOES HERE

        ArrayList<Integer> items = new ArrayList<Integer>();
        Scanner in = new Scanner(System.in);
        String input = "";
        int inputStage = 0;

        String selectedSort = "None";
        int totalElements = 0;
        int totalRuns = 0;

        long start;
        long end;
        long totalTime = 0;
        
        while (inputStage < 7) {
            // Necessary loop in order to keep prompting user if
            // bad input was given

            if (inputStage == 0 || inputStage == 1) {
                sysClear();
                for (int i=0; i < sorts.length; i++) {
                    System.out.println("["+colors[Arrays.asList(sorts).indexOf(sorts[i])]+(i+1)+ANSI_RESET+"] " + sorts[i]);
                }

                System.out.println();

                if (inputStage == 0) {
                    System.out.println("Which sort would you like to analyze? [1-7] " + ANSI_PURPLE);
                }
                else {
                    System.out.println(ANSI_RED+"Invalid input"+ANSI_RESET+".");
                    System.out.println("Which sort would you like to analyze? [1-7] " + ANSI_PURPLE);
                }
                
                input = in.nextLine();

                if (input.matches("[1-7]")) {
                    inputStage = 2;
                    selectedSort = sorts[Integer.parseInt(input)-1];
                }
                else {
                    inputStage = 1;
                }

            }
            else if (inputStage == 2 || inputStage == 3) {
                sysClear();
                System.out.println("Selected Sort: " + colors[Arrays.asList(sorts).indexOf(selectedSort)] + selectedSort + ANSI_RESET);
                System.out.println();

                if (inputStage == 3) {
                    System.out.println(ANSI_RED+"Invalid input"+ANSI_RESET+".");
                }
                System.out.println("How many elements would you like to generate? " + ANSI_PURPLE);
                input = in.nextLine();

                if (input.matches("[0-9]+")) {
                    totalElements = Integer.parseInt(input);
                    inputStage = 4;
                }
                else {
                    inputStage = 3;
                }
            }
            else if (inputStage == 4 || inputStage == 5) {
                sysClear();
                System.out.println("Selected Sort: " + colors[Arrays.asList(sorts).indexOf(selectedSort)] + selectedSort + ANSI_RESET);
                System.out.println("Total Elements: " + ANSI_WHITE + totalElements + ANSI_RESET);
                System.out.println();

                if (inputStage == 5) {
                    System.out.println(ANSI_RED+"Invalid input"+ANSI_RESET+".");
                }
                System.out.println("How many test runs would you like to conduct? " + ANSI_PURPLE);
                input = in.nextLine();

                if (input.matches("[0-9]+")) {
                    totalRuns = Integer.parseInt(input);
                    inputStage = 6;
                }
                else {
                    inputStage = 5;
                }
            }
            else if (inputStage == 6) {
                System.out.println(ANSI_RESET+"Generating Elements...");
                items.clear();
                for (int i=0; i<totalElements; i++) {
                    items.add(i);
                }

                long runCount = 1;
                for (int i=0; i<totalRuns+1; i++) {
                    // Shuffle items
                    if (i == 1) {
                        continue;
                    }
                    System.out.print("\nReshuffling Arraylist...");
                    Collections.shuffle(items);
                    System.out.println("\n"+colors[Arrays.asList(sorts).indexOf(selectedSort)] + selectedSort + ANSI_RESET + " run ["+colors[Arrays.asList(sorts).indexOf(selectedSort)]+(runCount)+ANSI_RESET+"]");
                    System.out.println(">   Sorting...");

                    Thread t = new Thread(new TimeManager(System.currentTimeMillis()));
                    t.start();
                    start = System.currentTimeMillis();

                    switch (selectedSort) {
                        case "Bogo Sort":
                            bogoSort(items);
                            break;
                        case "Bubble Sort":
                            bubbleSort(items);
                            break;
                        case "Selection Sort":
                            selectionSort(items);
                            break;
                        case "Insertion Sort":
                            insertionSort(items);
                            break;
                        case "Quick Sort":
                            quickSort(items);
                            break;
                        case "Merge Sort":
                            // Does not actually return sorted list ): because of final
                            items = mergeSort(items);
                            break;
                        case "Heap Sort":
                            heapSort(items);
                            break;
                    }

                    end = System.currentTimeMillis();
                    t.interrupt();
    
                    totalTime += end-start;
                    runCount ++;
                }
                
                System.out.println();
                System.out.println();
                System.out.println("All sort runs completed.");
                System.out.println(
                    "Average sort time for " + colors[Arrays.asList(sorts).indexOf(selectedSort)] + totalElements + ANSI_RESET + " elements: " + 
                    (double)(totalTime/totalRuns) + " ms"
                    );
                
                System.out.println( 
                    "Average sort time per element: " + ((double)(totalTime/totalRuns)/totalElements) + " ms"
                );

                // TODO: Add complexity analysis here

                System.out.println();
                System.out.println("Press " + ANSI_PURPLE + "ENTER" + ANSI_RESET + " to restart program.");
                System.out.println("Type " + ANSI_RED + "EXIT" + ANSI_RESET + " to quit program.");
                input = in.nextLine();

                if (input.toLowerCase().equals("exit")) {
                    inputStage = 999;
                }
                else {
                    input = "";
                    inputStage = 0;
                }
                
            }
        }
        
    }
    public static void sysClear() {
        System.out.print("\033[H\033[2J" + "\u001B[0m");
        System.out.flush();
    }


    public static void complexityAnalysis(String sortChoice, int arraySize, int testRuns) {
        //TEST RUNS HERE
    }

    public static boolean bogoSort(ArrayList<Integer> sortMe) {
        while (isUnsorted(sortMe) != -1)
        {
            Collections.shuffle(sortMe);
        }
        return true;
    }

    public static boolean bubbleSort(ArrayList<Integer> sortMe) {

        int temp;
        for (int i = 0; i < sortMe.size()-1; i++)
            for (int j = 0; j < sortMe.size()-i-1; j++)
                if (sortMe.get(j) > sortMe.get(j+1))
                {
                    temp = sortMe.get(j);
                    sortMe.set(j, sortMe.get(j+1));
                    sortMe.set(j+1, temp);
                }

        return false;
    }

    public static boolean selectionSort(ArrayList<Integer> sortMe) {
        for(int i = 0; i < sortMe.size(); i++) {
            int min = sortMe.get(0+i);
            int ele = i;
            for(int a = 0; a < sortMe.size() - i; a++) {
                if(sortMe.get(i+a)<min){
                    min = sortMe.get(i+a);
                    ele = i+a;
                }
            }
            sortMe.remove(ele);
            sortMe.add(i, min);
        }
        return false;
    }

    public static boolean insertionSort(ArrayList<Integer> sortMe) {
        for(int i = 0; i < sortMe.size(); i++) {
            int c = sortMe.get(i);
            int p = i-1;
            while (p > -1 && sortMe.get(p)>c){
                sortMe.set(p+1, sortMe.get(p));
                p--;
            }
            sortMe.set(p+1, c);
        }
        return false;
    }
    public static void quickSort(ArrayList<Integer> sortMe) {
		quickSort(sortMe, 0, sortMe.size() -1);
	}
    public static void quickSort(ArrayList<Integer> sortMe, int low, int high) {
        if (low < high+1) {
			int p = partition(sortMe, low, high);
			quickSort(sortMe, low, p-1);
			quickSort(sortMe, p+1, high);
		}
    }
    // moves all the values that are less than the pivot to the left and values greater than the pivot to the greater
    // returns the index of the pivot
    private static void swap(ArrayList<Integer> sortMe, int low, int high) {

        int temp = sortMe.get(low);
        sortMe.set(low, sortMe.get(high));
        sortMe.set(high, temp);
    }

    public static int partition(ArrayList<Integer> sortMe, int low, int high){
        int split = low + 1;
		for (int i = split; i <= high; i++) {
			if (sortMe.get(i) < sortMe.get(low)) {
				swap(sortMe, i, split++);
			}
		}
		swap(sortMe, low, split-1);
        return split-1;
    }

    public static ArrayList<Integer> mergeSort(ArrayList<Integer> sortMe) {
        // IF LIST IS 1 ELEMENT LONG RETURN INPUT
        if (sortMe.size() == 1) return sortMe;

        // INITIALIZING
        ArrayList<Integer> leftArray = new ArrayList<Integer>();
        ArrayList<Integer> rightArray = new ArrayList<Integer>();
        ArrayList<Integer> sortedArray = new ArrayList<Integer>();
        int high = sortMe.size();

        // CREATE LEFT AND RIGHT ARRAYS
        for (int i = 0; i < high/2; i++) {
          leftArray.add(sortMe.get(i));
        }
        for (int i = high/2; i < high; i++) {
          rightArray.add(sortMe.get(i));
        }

        // MERGESORT BOTH ARRAYS
        leftArray = mergeSort(leftArray);
        rightArray = mergeSort(rightArray);

        // MERGES BOTH ARRAYS
        while (!leftArray.isEmpty() && !rightArray.isEmpty()) {
            if (leftArray.get(0) > rightArray.get(0)) {
                sortedArray.add(rightArray.get(0));
                rightArray.remove(0);
            } else {
                sortedArray.add(leftArray.get(0));
                leftArray.remove(0);
            }
        }
        while (!leftArray.isEmpty()) {
            sortedArray.add(leftArray.get(0));
            leftArray.remove(0);
        }
        while (!rightArray.isEmpty()) {
            sortedArray.add(rightArray.get(0));
            rightArray.remove(0);
        }

        // RETURN RESULT
        return sortedArray;
    }

    private static void heapify(ArrayList<Integer> heap, int heapCap) {
        for(int i = (heap.size() / 2) - 1; i >= 0; i--) {
            siftDown(heap, i, heapCap);
        }
    }

    private static void siftDown(ArrayList<Integer> heap, int index, int heapCap) {
      int leftChildIndex = index * 2 + 1;
      int rightChildIndex = index * 2 + 2;
      int largest = index;

      if(leftChildIndex < heapCap && heap.get(leftChildIndex) > heap.get(largest) && (rightChildIndex >= heapCap || heap.get(leftChildIndex) > heap.get(rightChildIndex))) {
          largest = leftChildIndex;
          swap(heap, largest, index);
          siftDown(heap, leftChildIndex, heapCap);
          return;
      }

      if(rightChildIndex < heapCap && heap.get(rightChildIndex) > heap.get(largest)) {
          largest = rightChildIndex;
          swap(heap, largest, index);
          siftDown(heap, rightChildIndex, heapCap);
          return;
      }
    }

    public static boolean heapSort(ArrayList<Integer> heap) throws Exception{
        heapify(heap, heap.size());
        for(int i = 0; i < heap.size(); i++) {
            swap(heap, 0, heap.size() - i - 1);
            siftDown(heap, 0, heap.size() - i - 1);
        }
        return true;
    }

    private static void isSorted(ArrayList<Integer> testMe) throws Exception{
        //VALIDATE IF ARRAYLIST IS SORTED
        for(int i = 1; i < testMe.size(); i++) {
            if(testMe.get(i-1) > testMe.get(i)) {
                throw new Exception("ArrayList unsorted, index i-1 > index i : " + testMe.get(i-1) + " > " + testMe.get(i));
            }
        }
    }

    // Modified Version

    private static int isUnsorted(ArrayList<Integer> testMe){
        //VALIDATE IF ARRAYLIST IS SORTED
        // Returns index of unsorted pair
        // If sorted, returns -1
        for(int i = 1; i < testMe.size(); i++) {
            if(testMe.get(i-1) > testMe.get(i)) {
                return i;
            }
        }
        return -1;
    }
}
