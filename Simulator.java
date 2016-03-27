/**
 * Created by vijaychandra on 3/24/16.
 */
import java.io.File;
import java.io.FileReader;
import java.io.LineNumberReader;
import java.io.IOException;
import java.util.StringTokenizer;

public class Simulator {

    /**
    numberOfGates method takes in 'netfile' as argument which is of File type. This method goes through the netfile and returns (int) the number of gates present in the netfile.
    */
    public int numberOfGates(File netfile) throws IOException{
        //creating file reader
        FileReader fr = new FileReader(netfile.getAbsolutePath());
        LineNumberReader lnr = new LineNumberReader(fr);

        String str;
        int counter = 0;
        while( (str = lnr.readLine()) != null ){ //continue looping until end of file state is reached. 
            String[] string = str.split("\\s");
            counter = Integer.parseInt(string[0]);  //converting string to integer.
        }
        counter += 1; // gate number starts with '0'
        System.out.println("Number of gates in the netlist file are: "+ counter);
        return counter;
    }
    /**
    fanOut method takes in netfile, and gatenum as arguments which are of type File, and int. This method iterates through the netfile and computes the fanout for each gate.
    This method is of type void and therefore doesn't return anything. 
    */
    public void fanOut(File netfile, int gatenum) throws IOException{
        FileReader fr = new FileReader(netfile.getAbsolutePath());
        LineNumberReader lnr = new LineNumberReader(fr);
        String str;
        int gatenumber = gatenum, input1, input2;
        int counter = 0;
        while( (str = lnr.readLine()) != null ){
            String[] element = str.split("\\s");
            
            if(element[2].charAt(0) == 'I'){                         
                input1 = -1;
            }
            else{
            input1 = Integer.parseInt(element[2]);
            }
            if(gatenumber == input1){
                counter += 1;
            }
            //check if input 2 exists     
            if(element.length == 4){ 
                if(element[3].charAt(0) == 'I'){
                    input2 = -1;
            } else{
                input2 = Integer.parseInt(element[3]);
                }
             if(gatenumber == input2){
                counter += 1;
                } 
            }
        }
        System.out.println("Fanout of gate #"+ gatenumber + " is: " + counter);
    }    


    public static void main(String args[]) throws IOException{
        if(args.length == 2){ //checks for two command line arguments
            File netlist = new File(args[0]);
            File inputlist = new File(args[1]);
            Simulator statistics = new Simulator();
            int nGates = statistics.numberOfGates(netlist);
            for(int x =0; x < nGates; x++){ //iterates through all the gates and computes fanout for each gate.
            statistics.fanOut(netlist, x);
            } 
        }

    }
}