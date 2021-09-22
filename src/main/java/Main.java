import com.google.gson.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;


public class Main {

    static ArrayList<Integer> arrayFull = new ArrayList<>();
    static ArrayList<Integer> arrayFirst = new ArrayList<>();
    static int midTarget, newTarget;
    static boolean targetDone = false;

    public static void main(String[] args) {

//                test arrays
//                ArrayList<Integer> arrayFull = new ArrayList<>(
//                4, 5, 2, 8, 4, 5, 6, 7, 7, 1, 5, 2, 9,6,5,4,7,8,2,6,3,6,5,4, 3,5,9,7, 1, 5, 2, 9,6));
//                Arrays.asList(348, 56, 85, 258, 188, 234, 42, 55, 234,  386, 79));
//                Arrays.asList(56, 85, 593, 42, 405, 89, 3, 8));
//                Arrays.asList(1, 2, 3, 4, 5, 6, 7));
//                Arrays.asList(7, 6, 5, 4, 3, 2, 1));
//                Arrays.asList(1, 2, 3, 4, 5));
//                Arrays.asList(20, 5, 6, 7, 0, 23, 7, 1, 4, 65));
//                Arrays.asList(3, 3, 3, 5, 7));
//                Arrays.asList(4, 5, 6, 7, 8));
//                Arrays.asList(0,0,0,0,0,0,0,0,0,0));

        File file = new File("src/main/resources/inputJson.json");
        arrayFull = deserialize(file);
        printInput(arrayFull);

        midTarget = targetGetValue(arrayFull);
        calcFirstArray(midTarget, 0);

        if (arrayFirst.isEmpty()) {
            System.out.println ("Mid target: " + midTarget + " is unavailable. \n" +
                                "New closest to mid target: " + newTarget);
            System.out.println("-----------------------------------------------------------------");
            calcFirstArray(newTarget, 0);
        }

        ArrayList<Integer> arraySecond = calcSecondArray(arrayFull);
        printResult(arrayFirst, arraySecond);
        serialize(arrayFirst, arraySecond);
   }

    static void calcFirstArray(int target, int index) {
        calcClosestTarget(target);

        if (target == 0) {
            System.out.println("Target done!");
            targetDone = true;
        }

        for (int i = index; i < arrayFull.size(); i++) {
            if (target - arrayFull.get(i) >= 0) {
                calcFirstArray(target - arrayFull.get(i), index + 1);
                index++;
                if (targetDone) {
                    arrayFirst.add(arrayFull.get(i));
                    return;
                }
            }
        }
    }

    static void calcClosestTarget (int target) {
        if (midTarget - target > newTarget)
            newTarget = midTarget - target;
    }

    static int targetGetValue (ArrayList<Integer> arrayList) {
        return Math.round(sumOfArrayElements(arrayList) / 2f);
    }

    static int sumOfArrayElements (ArrayList<Integer> arrayList) {
        int sum = 0;
        for (Integer num : arrayList)
            sum += num;
        return sum;
    }

    static ArrayList<Integer> calcSecondArray (ArrayList<Integer> arrayList) {
        for (Integer searchNum : arrayFirst) {
            if (arrayList.contains(searchNum)) {
                for (Integer numToDelete : arrayList) {
                    if (searchNum.equals(numToDelete)) {
                        arrayList.remove(numToDelete);
                        break;
                    }
                }
            }
        }
        return arrayList;
    }

    static void serialize (ArrayList<Integer> array1, ArrayList<Integer> array2){
        QuickSort.sort(array1);
        QuickSort.sort(array2);
        JsonClass jsonClass = new JsonClass(array1, array2);
        Gson gson = new Gson();
        String json = gson.toJson(jsonClass);

        File file = new File("src/main/resources/outputJson.json");

        try (PrintWriter writer = new PrintWriter(new FileWriter(file))){
            writer.write(json);
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Output JSON:" + "\n" + json);
        System.out.println("-----------------------------------------------------------------");
    }

    static ArrayList<Integer> deserialize (File file){
        try (Scanner scanner = new Scanner(new FileReader(file))) {
            StringBuilder inputStr = new StringBuilder();
            while (scanner.hasNext()) {
                inputStr.append(scanner.nextLine());
            }

            Gson gson = new Gson();
            JsonClass input = gson.fromJson(String.valueOf(inputStr), JsonClass.class);
            return input.getFirstArray();

        } catch (FileNotFoundException e) {
            System.out.println("Input JSON file not found.");
            System.out.println("-----------------------------------------------------------------");
        } catch (JsonSyntaxException e) {
            System.out.println("Wrong JSON format.");
            System.out.println("-----------------------------------------------------------------");
        }
        return new ArrayList<>();
    }

    static ArrayList<Integer> checkInputArrayExceptions (ArrayList<Integer> arrayList) {

//          Input array size = 1 or array is empty
        if (arrayList.size() == 0 || arrayList.size() == 1) {
            return arrayList;
        }

//          Input array size = 2 or all numbers in array are 0
        if (arrayList.size() == 2 || sumOfArrayElements(arrayList) == 0) {
            arrayList.remove(0);
            return arrayList;
        }

//          Biggest element of input array >= average number of input array sum
        int maxElement = 0;
        int indexOfMaxElement = 0;
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) > maxElement){
                maxElement = arrayList.get(i);
                indexOfMaxElement = i;
            }
        }
        if (maxElement >= targetGetValue((arrayList))) {
            arrayList.remove(indexOfMaxElement);
            return arrayList;
        }

        return arrayList;
    }

    static void printInput (ArrayList<Integer> fullArray) {
        System.out.println("Input array: " + fullArray);
        System.out.println("Array sum: " + sumOfArrayElements(fullArray));
        System.out.println("Target: " + targetGetValue(fullArray));
        System.out.println("Q-ty of elements: " + fullArray.size());
        System.out.println("-----------------------------------------------------------------");
    }

    static void printResult (ArrayList<Integer> arrayList1, ArrayList<Integer> arrayList2) {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("First array: " + arrayList1);
        System.out.println("Array sum: " + sumOfArrayElements(arrayList1));
        System.out.println("Q-ty of elements: " + arrayList1.size() + "\n");
        System.out.println("Second array: " + arrayList2);
        System.out.println("Array sum: " + sumOfArrayElements(arrayList2));
        System.out.println("Q-ty of elements: " + arrayList2.size());
        System.out.println("-----------------------------------------------------------------");
    }
}

