package MergeSortGen;

import java.io.*;
import java.lang.reflect.Array;
import java.util.InputMismatchException;
import java.util.Random;
import java.util.Scanner;

public class MergeSortGen<T extends Comparable<T>> {

    @SuppressWarnings("unchecked")
    public static <T extends Comparable<T>> T[] createRandomArray(int arrayLength, T[] array) {
        Random random = new Random();
        for (int i = 0; i < arrayLength; i++) {
            // Random Number 1-100
            array[i] = (T) ((Object) random.nextInt(101));
        }
        return array;
    }
    

    public static <T> void writeArrayToFile(T[] array, String filename) {
        File file = new File(filename);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
            for (T element : array) {
                writer.write(element.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            // Do nothing if an exception occurs.
        }
    }

    // Merging the two subarrays L and M into array.
    public static <T extends Comparable<T>> void merge(T[] array, int left, int mid, int right) {
        // Calculate the size of the subarrays
        int n1 = mid - left + 1;
        int n2 = right - mid;

        // Creating temporary arrays to store the subarrays.
        @SuppressWarnings("unchecked")
        T[] L = (T[]) Array.newInstance(array.getClass().getComponentType(), n1);
        @SuppressWarnings("unchecked")
        T[] M = (T[]) Array.newInstance(array.getClass().getComponentType(), n2);

        // Copying elements from the original array to subarrays.
        System.arraycopy(array, left, L, 0, n1);
        System.arraycopy(array, mid + 1, M, 0, n2);

        // Initializing the indices for the subarrays and the original array
        int i = 0, j = 0, k = left;

        // Comparing and copying the elements from the subarrays to the original array in sorted order.
        while (i < n1 && j < n2) {
            if (L[i].compareTo(M[j]) <= 0) {
                array[k] = L[i];
                i++;
            } else {
                array[k] = M[j];
                j++;
            }
            k++;
        }

        // Copying the remaining elements from the subarrays to the original array if any.
        while (i < n1) {
            array[k] = L[i];
            i++;
            k++;
        }

        while (j < n2) {
            array[k] = M[j];
            j++;
            k++;
        }
    }

    // Dividing the array into two subarrays to sort and merge them.
    public static <T extends Comparable<T>> void mergeSort(T[] array, int left, int right) {
        if (left < right) {
            // Finding the mid point.
            int mid = (left + right) / 2;

            // Recursively sorting the left and right subarrays.
            mergeSort(array, left, mid);
            mergeSort(array, mid + 1, right);

            // Merging sorted subarrays.
            merge(array, left, mid, right);
        }
    }

    // Instructions for taking user input.
    public static void main(String[] args) {
        Integer[] array;
        try (Scanner keyboard = new Scanner(System.in)) {
            // Ask the user for input mode.
            System.out.println("Enter 1 to type in an integer or 2 to give an input file:");
            int mode = keyboard.nextInt();
            if (mode == 1) {
                System.out.println("Enter the length of the array:");
                int length = keyboard.nextInt();
                array = (Integer[]) Array.newInstance(Integer.class, length);
                array = createRandomArray(length, array);
            } else if (mode == 2) {
                // Asking for the name of the input file.
                System.out.println("Enter the name of the input file:");
                String filename = keyboard.next();
                try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                    // Converting the BufferedReader to an IntStream and then to an array.
                    // The IntStream is a stream of primitive int values.
                    // The toArray method returns an array containing the elements of the stream.
                    array = reader.lines().map(Integer::parseInt).toArray(Integer[]::new);
                } catch (IOException e) {
                    // Handling the exception.
                    System.out.println("IO error: " + e.getMessage());
                    return;
                }
            } else {
                // Invalid input mode message.
                System.out.println("Invalid input mode: " + mode);
                return;
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input: " + e.getMessage());
            return;
        }

        // Writing the array to a file named "output.txt".
        writeArrayToFile(array, "output.txt");

        // Sorting the array using merge sort.
        mergeSort(array, 0, array.length - 1);

        // Writing the sorted array to a file named "sorted.txt".
        writeArrayToFile(array, "sorted.txt");
    }
}
