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
        System.out.println(counter);
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
        System.out.println(gatenumber + " " + counter);
    }    
/**
    simulation method takes in netlist, inputlist, nGates(number of gates) as arguments which are of type File, File, and integer. This method goes through the inputlist and
    converts the strings to integer and saves each row of inputs in the array inputvalues, iteratively. After doing so, the computation method is called with inputvalues, 
    netlist, and nGates as arguments. simulation method is of type void and therefore, doesnt' return anything. 
*/
    public void simulation(File netlist, File inputlist, int nGates) throws IOException{
        FileReader fr = new FileReader(inputlist.getAbsolutePath());
        LineNumberReader lnr = new LineNumberReader(fr);
        String str;
        int length;
        while((str = lnr.readLine()) != null){
            String[] inputs = str.split("\\s");
            length = inputs.length;
            int[] inputvalues = new int[length];
            for(int x = 0; x< length; x++){ //storing each row of inputlist iteratively
                inputvalues[x] = Integer.parseInt(inputs[x]);
            }
            computation(inputvalues, netlist, nGates); //invoking computation method to calculate the output at each gate
        }

    }
/**
    computation method takes in inputvalues, netlist, and nGates as arguments which are of type array[integers], File, and integer. This method goes through each line of netfile
    and if it encounters any primary inputs (inputs starting with I), it assigns input1 and input2 its corresponding values which are stored in inputvalues[] array. When this method 
    encounters constants such as 1, 0 etc. it assigns input1/input2 with the corresponding value in gatevalues[] array. After the assignment for input1 and input2 is finished,
    this method invokes other method based on whether netlistline[0] array corresponds to AND, NOT, or OR gate. Finally, this method prints out the gate value at each gate.
    This method is of type void and therefore, doesn't return anything. 
*/
    public void computation(int[] inputvalues, File netlist, int nGates) throws IOException{
        FileReader fr = new FileReader(netlist.getAbsolutePath());
        LineNumberReader lnr = new LineNumberReader(fr);
        String str;
        int[] gatevalues = new int[nGates];
        int input1, input2;
        while((str = lnr.readLine()) != null){
            String[] netlistline = str.split("\\s");
            int gatenumber = Integer.parseInt(netlistline[0]);
            if(netlistline[2].charAt(0) == 'I'){
                char y = netlistline[2].charAt(1);
                input1 = Character.getNumericValue(y);
                input1 = inputvalues[input1];
            }
            else{
                input1 = gatevalues[Integer.parseInt(netlistline[2])];
            }
             if(netlistline[1].charAt(0) == 'N'){
                gatevalues[Integer.parseInt(netlistline[0])] = notGate(input1);
            }
            //check if input 2 exists and assign them with values
            if(netlistline.length == 4){
                if(netlistline[3].charAt(0) == 'I'){
                    char z = netlistline[3].charAt(1);
                    input2 = Character.getNumericValue(z);
                    input2 = inputvalues[input2];
                }
                else{
                    input2 = gatevalues[Integer.parseInt(netlistline[3])];
                }

            if(netlistline[1].charAt(0) == 'A'){ //AND GATE
                gatevalues[Integer.parseInt(netlistline[0])] = andGate(input1, input2); //invokes andGate method
            }
            else if(netlistline[1].charAt(0) == 'O'){ //OR GATE
                gatevalues[Integer.parseInt(netlistline[0])] = orGate(input1, input2); //invokes orGate method
            }
            else{
                gatevalues[Integer.parseInt(netlistline[0])] = notGate(input1); // invokes notGate method
            }
        }        
        }//end of while loop
                for(int x =0; x< gatevalues.length; x++){
            if(x<gatevalues.length - 1){
                System.out.printf("%d ", gatevalues[x]);
            }
            else{
                System.out.printf("%d \n", gatevalues[x]);
            }
        }
    }
/**
    andGate method takes A and B as arguments which are of type integer. It computers the logical AND of inputs A and B and returns this value, which is stored in gatevalues[]
*/
    public int andGate(int A, int B){
        if((A==1) && (B == 1)){
            return 1;
        }
        else{
            return 0;
        }
    }
 /**
    orGate method takes A and B as inputs which are of type integer. This method computes the logical OR of the given inputs and returns the value, which is stored in gatevalues[]
 */   
    public int orGate(int A, int B){
        if((A==0) && (B == 0)){
            return 0;
        }
        else{
            return 1;
        }
    }
/**
    notGate takes in one input of type integer and returns the compliment value of the input which is stored in gatevalues[]
*/    
    public int notGate(int A){
        if(A == 1){
            return 0;
        }
        else{
            return 1;
        }
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
            statistics.simulation(netlist,inputlist,nGates);
        }

    }
}