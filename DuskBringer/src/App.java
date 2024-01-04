import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) throws FileNotFoundException {
        // change what file its reading. Changes depending on what computer its on
        File input = new File("/home/kylevo/Desktop/JavaTest/DuskBringer/src/input4.txt");
        Scanner scan = new Scanner(input);

        // cleans string of most special characters
        String data = scan.nextLine().toLowerCase().replaceAll("[-+.^:,()!’]", "").replaceAll("\\s", "")
                .replaceAll("\\d", "").replaceAll(";", "");

        scan.close();
        char letter[] = data.toCharArray();

        // 2D array of Letter freqency CSV file
        double[][] freqLang = new double[87][16];
        ArrayList<Character> freqLangKey = new ArrayList<Character>();

        // Array of the Letters in the sentence
        double[] freqSen = new double[data.length()];
        ArrayList<Character> freqSenKey = new ArrayList<Character>();

        // All languges its able to read
        List<String> lang = Arrays.asList("English", "French", "German", "Spanish", "Portuguese", "Esperanto",
                "Italian", "Turkish", "Swedish", "Polish", "Dutch", "Danish", "Icelandic", "Finnish", "Czech",
                "Hungarian");
        // keeps track of the Chi-Square of each languge
        double[] langNum = new double[16];
        int index = 0;

        readFreq(freqLang, freqLangKey); // puts the CSV file into a 2d array
        readSen(data, letter, freqSen, freqSenKey);// Finds the letter freqency of the sentence
        freqSen = Arrays.copyOf(freqSen, freqSenKey.size()); // rezies the sentence letter freqency

        double d = 0; // doubles don't have ^ as a function so this has to be done :,(

        // Goes throught the sentence letter by letter
        for (int i = 0; i < freqSenKey.size() - 1; i++) {
            // Finds matching letter in the CSV file
            for (index = 0; index < freqLangKey.size(); index++) {
                if (freqSenKey.get(i) == freqLangKey.get(index)) {
                    // Goes through all languges to calculate the Chi-Square for the letter at int i
                    for (int j = 0; j < freqLang[0].length; j++) {
                        //Math
                        d = freqSen[i] * 100 - freqLang[index][j];
                        langNum[j] = langNum[j] + (d * d) / freqLang[0][j];
                    }
                    // Goes back to start the next letter in the sentence
                    break;
                }
            }
        }
        System.out.println(Arrays.toString(freqSen));
        System.out.println(freqSenKey);
        System.out.println(lang.get(smallest(langNum)));

    }

    public static void readFreq(double[][] freqency, ArrayList<Character> key) {
        String num = "";
        String data = "";
        double value = 0;
        File freq = new File("/home/kylevo/Desktop/JavaTest/DuskBringer/src/Letter freqency - Sheet1.csv");
        try {
            Scanner fre = new Scanner(freq);
            for (int i = 0; i < freqency.length; i++) {
                data = fre.nextLine();
                key.add(data.charAt(0));
                for (int j = 0; j < freqency[0].length - 1; j++) {
                    num = data.substring(data.indexOf(",") + 1, data.indexOf(",", data.indexOf(",") + 1));
                    data = data.substring(data.indexOf(",", data.indexOf(",") + 1));
                    value = Double.valueOf(num);
                    freqency[i][j] = value;
                }
                num = data.substring(data.indexOf(",") + 1, data.length());
                value = Double.valueOf(num);
                freqency[i][15] = value;
            }
            fre.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void readSen(String data, char[] sen, double[] freqSen, ArrayList<Character> freqSenKey) {
        int error = 0;
        for (int i = 0; i < data.length(); i++) {
            if (sen[i] != '0') {
                freqSen[i - error] = 1;
                for (int j = i + 1; j < data.length(); j++) {
                    if (sen[i] == sen[j]) {
                        freqSen[i - error]++;
                        sen[j] = '0';
                    }
                }
            } else {
                error++;
            }

        }
        for (int j = 0; j < data.length(); j++) {
            if (sen[j] != '0') {
                freqSenKey.add(sen[j]);
            }
        }

        for (int x = 0; x < freqSen.length; x++) {
            freqSen[x] = Double.valueOf(freqSen[x] / sen.length);
        }
    }

    public static int smallest(double[] arr) {
        double max = arr[0];
        int maxInd = 0;
        for (int i = 1; i < arr.length; i++)
            if (arr[i] < max) {
                max = arr[i];
                maxInd = i;
            }
        return maxInd;
    }
}
