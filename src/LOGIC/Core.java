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
    
    public Core(int position){
        this.positionAtArray= position;
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
    
    public void executeInstruction(){
        
    }
    
    

    @Override
    public int compareTo(Object o) {
        int comapeCore=((Core)o).getCurrentProcess().getInitTime();
        /* For Ascending order*/
        return this.currentProcess.getInitTime()-comapeCore;
    }


    
    
    
}
