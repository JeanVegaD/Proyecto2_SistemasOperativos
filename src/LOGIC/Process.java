/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import java.awt.Color;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author Jean
 */
public class Process {
    
    private int ID;
    private String name = "";
    private ArrayList<String> instruccionsOfProcess = new ArrayList<>();
    private String colorProcess;
    private int initTime = 0;
    private int burstTime = 0;
    private int waitingTime = 0;
    private String estado = "new";
    private String PC = "0";
    private String AC = "0";;
    private String AX = "0";
    private String BX = "0";
    private String CX = "0";
    private String DX = "0";
    //private ArrayList<String> pila =  new ArrayList<String>();
    //private int CPU = -1;
    private int countOfInstructionExecuted = 0;
    private String transcuredTime  = "0";
    private String finishTime = "";
    
    
    public Process(int id,File tempFile){
        try {
            instruccionsOfProcess= (ArrayList<String>) Files.readAllLines(tempFile.toPath(), StandardCharsets.UTF_8);//lectura del archivo
            burstTime = instruccionsOfProcess.size();
            createRandomColor();
            this.ID=id;
            this.name=tempFile.getName();
        } catch (Exception ex) {
            
        }
    }
    
    private void createRandomColor(){
        Random rand = new Random();
        float r = (float) (rand.nextFloat() / 2f + 0.5);
        float g = (float) (rand.nextFloat() / 2f + 0.5);
        float b = (float) (rand.nextFloat() / 2f + 0.5);   
        Color your_color = new Color(r,g,b);

        colorProcess = "#"+Integer.toHexString(your_color.getRGB()).substring(2);
    }
    
    public void loadProcesstoCore(int loadTime){
        this.estado = "execution";
        if(initTime==0){
            initTime= loadTime;
        }
    }
    
    public void executeInstruction(){
        
        countOfInstructionExecuted++;
    }
    
    public boolean isEndOFProcess(){
        if(countOfInstructionExecuted == instruccionsOfProcess.size()){
            this.estado = "finished";
            return true;
        }else{
            return false;
        }
    }

    public ArrayList<String> getInstruccionsOfProcess() {
        return instruccionsOfProcess;
    }

    public void setInstruccionsOfProcess(ArrayList<String> instruccionsOfProcess) {
        this.instruccionsOfProcess = instruccionsOfProcess;
    }

    public int getInitTime() {
        return initTime;
    }

    public void setInitTime(int initTime) {
        this.initTime = initTime;
    }

    public int getBurstTime() {
        return burstTime;
    }

    public void setBurstTime(int burstTime) {
        this.burstTime = burstTime;
    }

    public int getID() {
        return ID;
    }

    public String getName() {
        return name;
    }

    public String getEstado() {
        return estado;
    }

    public String getPC() {
        return PC;
    }

    public String getAC() {
        return AC;
    }

    public String getAX() {
        return AX;
    }

    public String getBX() {
        return BX;
    }

    public String getCX() {
        return CX;
    }

    public String getDX() {
        return DX;
    }

    /*public ArrayList<String> getPila() {
        return pila;
    }

    public int getCPU() {
        return CPU;
    }*/

    public String getTranscuredTime() {
        return transcuredTime;
    }

    public String getFinishTime() {
        return finishTime;
    }

    public String getColorProcess() {
        return colorProcess;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    
    
    
    
}
