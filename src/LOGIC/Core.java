/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

/**
 *
 * @author Jean
 */
public class Core implements Comparable {
    
    private int positionAtArray;
    private Process currentProcess = null;
    private String algorithm = "";
    
    public Core(int position,String p_algorithm){
        this.positionAtArray= position;
        this.algorithm=p_algorithm;
    }

    public int getPositionAtArray() {
        return positionAtArray;
    }

    
    
    public Process getCurrentProcess() {
        return currentProcess;
    }

    public void setCurrentProcess(Process currentProcess) {
        this.currentProcess = currentProcess;
    }
       

    @Override
    public int compareTo(Object o) {
        if(algorithm.equals("FCFS - First Come First Served")){
            int comapeCore=((Core)o).getCurrentProcess().getInitTime();
            return this.currentProcess.getInitTime()-comapeCore;
            
        }else if(algorithm.equals("SRT - Shortest Remaining Time")){
            return 0;
            
        }else if(algorithm.equals("SJF - Shortest Job First")){
            int comapeCore=((Core)o).getCurrentProcess().getBurstTime();
            return this.currentProcess.getBurstTime()-comapeCore;
            
        }else if(algorithm.equals("HHRR - Highest Response Ratio Next")){
            return 0;
        }else{
            return 0;
        }
  
    }


    
    
    
}
