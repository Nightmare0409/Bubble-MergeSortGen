import java.util.*;
import java.io.*;
import java.lang.reflect.Array;

public class BubbleSortGen<T extends Comparable<T>> {

    public static <T extends Comparable<T>> T[] createRandomArray(int arrayLength, Class<T> type) {
        Random random = new Random();
        @SuppressWarnings("unchecked")
        T[] array = (T[]) Array.newInstance(type, arrayLength);
    
        for (int i = 0; i < array.length; i++) {
            if (type.equals(Integer.class)) {
                array[i] = type.cast(Integer.valueOf(random.nextInt(101)));
            } else if (type.equals(Double.class)) {
                array[i] = type.cast(Double.valueOf(random.nextDouble() * 100));
            } else if (type.equals(Character.class)) {
                array[i] = type.cast(Character.valueOf((char) (random.nextInt(26) + 'a')));
            }
        }
        return array;
    }
    

    public static <T> void writeArrayToFile(T[] array, String filename) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filename))) {
            for (T element : array) {
                writer.write(element.toString());
                writer.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static <T extends Comparable<T>> void bubbleSort(T[] array) {
        int n = array.length;

        for (int i = 0; i < n - 1; i++) {
            for (int j = 0; j < n - i - 1; j++) {
                if (array[j].compareTo(array[j + 1]) > 0) {
                    T temp = array[j];
                    array[j] = array[j + 1];
                    array[j + 1] = temp;
                }
            }
        }
    }

    public static void main(String[] args) {
        try (Scanner keyboard = new Scanner(System.in)) {
            System.out.println("Enter 1 to type in an integer or 2 to give an input file:");
            int mode = keyboard.nextInt();

            if (mode == 1) {
                System.out.println("Enter the length of the array:");
                int length = keyboard.nextInt();
                Integer[] array = createRandomArray(length, Integer.class);
                writeArrayToFile(array, "output.txt");
                bubbleSort(array);
                writeArrayToFile(array, "sorted.txt");
            } else if (mode == 2) {
                System.out.println("Enter the name of the input file:");
                String filename = keyboard.next();

                try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
                    Integer[] array = reader.lines().map(Integer::valueOf).toArray(Integer[]::new);
                    writeArrayToFile(array, "output.txt");
                    bubbleSort(array);
                    writeArrayToFile(array, "sorted.txt");
                } catch (IOException e) {
                    System.out.println("IO error: " + e.getMessage());
                }
            } else {
                System.out.println("Invalid input mode: " + mode);
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input: " + e.getMessage());
        }
    }
}
