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
    private int finishTime = 0;
    private int tr = 0;
    private int trts = 0;
    private String estado = "new";
    private String PC = "0";
    private String AC = "0";;
    private String AX = "0";
    private String BX = "0";
    private String CX = "0";
    private String DX = "0";
    private ArrayList<String> pila =  new ArrayList<String>();
    //private int CPU = -1;
    private int countOfInstructionExecuted = 0;
    private String transcuredTime  = "0";

    
    private boolean flag = false;
    
    
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
        String inst =instruccionsOfProcess.get(countOfInstructionExecuted);
        String[] subinst = inst.replace(",", "").split("\\s+");
        String type = subinst[0].toUpperCase();
        
        String reg;
        String reg1;
        String reg2;
        int valAC;
        int res;
        
        switch(type){
            case "LOAD":     
                reg = subinst[1].toUpperCase();
                if(reg.equals("AX")){
                    this.setAC(this.getAX());
                }else if (reg.equals("BX")){
                    this.setAC(this.getBX());
                }else if (reg.equals("CX")){
                    this.setAC(this.getCX());
                }else{
                    this.setAC(this.getDX());
                } 
                break;
            case "STORE":
                reg = subinst[1].toUpperCase();
                if(reg.equals("AX")){
                    this.setAX(this.getAC());
                }else if (reg.equals("BX")){
                    this.setBX(this.getAC());
                }else if (reg.equals("CX")){
                    this.setCX(this.getAC());
                }else{
                    this.setDX(this.getAC());
                }
                    
                break;
            case "MOV":
                reg1 = subinst[1].toUpperCase();
                reg2 = subinst[2].toUpperCase();
                if(reg1.equals("AX")){
                    if(reg2.equals("AX")){
                        this.setAX(this.getAX());
                    }else if(reg2.equals("BX")){
                        this.setAX(this.getBX());
                    }else if(reg2.equals("CX")){
                        this.setAX(this.getCX());
                    }else{
                        this.setAX(this.getDX());
                    }
                }else if(reg1.equals("BX")){
                    if(reg2.equals("AX")){
                        this.setBX(this.getAX());
                    }else if(reg2.equals("BX")){
                        this.setBX(this.getBX());
                    }else if(reg2.equals("CX")){
                        this.setBX(this.getCX());
                    }else{
                        this.setBX(this.getDX());
                    }
                }else if(reg1.equals("CX")){
                    if(reg2.equals("AX")){
                        this.setCX(this.getAX());
                    }else if(reg2.equals("BX")){
                        this.setCX(this.getBX());
                    }else if(reg2.equals("CX")){
                        this.setCX(this.getCX());
                    }else{
                        this.setCX(this.getDX());
                    }
                }else{
                    if(reg2.equals("AX")){
                        this.setDX(this.getAX());
                    }else if(reg2.equals("BX")){
                        this.setDX(this.getBX());
                    }else if(reg2.equals("CX")){
                        this.setDX(this.getCX());
                    }else{
                        this.setDX(this.getDX());
                    }
                }
                break;
            case "ADD":
                valAC = Integer.parseInt(this.getAC());
                reg = subinst[1].toUpperCase();
                int valreg = 0;
                if(reg.equals("AX")){
                    valreg = Integer.parseInt(this.getAX());
                }else if (reg.equals("BX")){
                    valreg = Integer.parseInt(this.getBX());
                }else if (reg.equals("CX")){
                    valreg = Integer.parseInt(this.getCX());
                }else{
                   valreg = Integer.parseInt(this.getDX());
                }
                res = valAC + valreg;
                this.setAC(String.valueOf(res));
                break;
            case "SUB": 
                valAC = Integer.parseInt(this.getAC());
                reg = subinst[1].toUpperCase();
                valreg = 0;
                if(reg.equals("AX")){
                    valreg = Integer.parseInt(this.getAX());
                }else if (reg.equals("BX")){
                    valreg = Integer.parseInt(this.getBX());
                }else if (reg.equals("CX")){
                    valreg = Integer.parseInt(this.getCX());
                }else{
                   valreg = Integer.parseInt(this.getDX());
                }
                res = valAC - valreg;
                this.setAC(String.valueOf(res));
                    
                break;
            case "INC":  
                valAC = Integer.parseInt(this.getAC());
                res = valAC + 1 ;
                this.setAC(String.valueOf(res));
                break;
            case "DEC":  
                valAC = Integer.parseInt(this.getAC());
                res = valAC - 1 ;
                this.setAC(String.valueOf(res)); 
                break;
            case "INT":
                /*
                if(subinst.length == 2){
                    this.currentProcess.setEstado("wating");
                    compu.addProcessToWait(this.currentProcess);
                    if(subinst[1].toUpperCase().equals("20H")){
                        this.countInstruction=this.instructions.size();
                    }else if(subinst[1].toUpperCase().equals("09H")){
                        compu.sendMessagetoOutput(this.name+ " / DX: "+ this.currentProcess.getDX());
                        compu.sendMessagetoOutput(this.name+ " / enter to continue");
                    }else if(subinst[1].toUpperCase().equals("10H")){
                        compu.sendMessagetoOutput(this.name+ " / enter value: ");
                        this.tempDX=true;
                    }else{
                        this.compu.sendMessagetoOutput("invalid instruction: " +inst);
                    }   
                }else{
                    this.compu.sendMessagetoOutput("invalid instruction: " +inst);
                }*/
                break;
            case "JUM":  
                int jump = Integer.parseInt(subinst[1]);  ;
                if(jump>0){
                    if(jump + this.countOfInstructionExecuted < this.instruccionsOfProcess.size()){
                        this.countOfInstructionExecuted+=jump-1;
                    }
                }else{
                    if(this.countOfInstructionExecuted + jump > 0){
                        this.countOfInstructionExecuted+=jump-1;
                    }
                }
                   
                break;
            case "CMP":
                int val1;
                int val2;
                reg1 = subinst[1].toUpperCase();
                reg2 = subinst[2].toUpperCase();
                //buscar el valor de el primer registro
                if(reg1.equals("AX")){
                     val1 = Integer.parseInt(this.getAX());
                 }else if (reg1.equals("BX")){
                     val1 = Integer.parseInt(this.getBX());
                 }else if (reg1.equals("CX")){
                    val1 = Integer.parseInt(this.getCX());
                 }else{
                    val1 = Integer.parseInt(this.getDX());
                 }
                //buscar el valor de el segundo registro
                if(reg2.equals("AX")){
                         val2 = Integer.parseInt(this.getAX());
                 }else if (reg2.equals("BX")){
                     val2 = Integer.parseInt(this.getBX());
                 }else if (reg2.equals("CX")){
                    val2 = Integer.parseInt(this.getCX());
                 }else{
                    val2 = Integer.parseInt(this.getDX());
                 }
                if(val1 == val2){
                    this.flag = true;
                }else{
                    this.flag = false;
                } 
                break;
            case "JE":
                jump = Integer.parseInt(subinst[1]);
                if(this.flag){
                    if(jump>0){
                         if(jump + this.countOfInstructionExecuted < this.instruccionsOfProcess.size()){
                             this.countOfInstructionExecuted+=jump-1;
                         }
                     }else{
                         if(this.countOfInstructionExecuted + jump > 0){
                             this.countOfInstructionExecuted+=jump-1;
                         }
                     }
                }
   
                break;
            case "JNE":
                jump = Integer.parseInt(subinst[1]);
                if(!this.flag){
                    if(jump>0){
                         if(jump + this.countOfInstructionExecuted < this.instruccionsOfProcess.size()){
                             this.countOfInstructionExecuted+=jump-1;
                         }
                     }else{
                         if(this.countOfInstructionExecuted + jump > 0){
                             this.countOfInstructionExecuted+=jump-1;
                         }
                     }
                }
                   
                break;
            case "PUSH":
                if(this.getPila().size()<=4){
                    reg = subinst[1].toUpperCase();
                    if(reg.equals("AX")){
                        this.getPila().add(this.getAX());
                    }else if (reg.equals("BX")){
                        this.getPila().add(this.getBX());
                    }else if (reg.equals("CX")){
                       this.getPila().add(this.getCX());
                    }else{
                       this.getPila().add(this.getDX());
                    }
                }    
                break;
            case "POP":
                if(this.getPila().size()>0){
                    reg = subinst[1].toUpperCase();
                    String val = this.getPila().get(0);
                    this.getPila().remove(0);
                    if(reg.equals("AX")){
                        this.setAX(val);
                    }else if (reg.equals("BX")){
                        this.setBX(val);
                    }else if (reg.equals("CX")){
                       this.setCX(val);
                    }else{
                       this.setDX(val);
                    }
                }
                    
                break;
            case "PARAM": 
                    if(subinst.length == 2){
                       String val = subinst[1].toUpperCase();
                            if(this.getPila().size()<=4){
                                this.getPila().add(val);
                            }
                    }
                    if(subinst.length == 3){
                       String val = subinst[1].toUpperCase();
                       String val2s = subinst[2].toUpperCase();
                        if(this.getPila().size()<=3){
                             this.getPila().add(val);
                             this.getPila().add(val2s);
                         }
                    }
                    if(subinst.length == 4){
                       String val = subinst[1].toUpperCase();
                       String val2s = subinst[2].toUpperCase();
                       String val3 = subinst[3].toUpperCase();
                       
                        if(this.getPila().size()<=2){
                             this.getPila().add(val);
                             this.getPila().add(val2s);
                             this.getPila().add(val3);
                            }
                    } 
            
            default:
            break;
        }
        
        countOfInstructionExecuted++;
    }
    
    public void increaseWaitingTime(){
        this.waitingTime++;
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

    public ArrayList<String> getPila() {
        return pila;
    }

    /*public int getCPU() {
        return CPU;
    }*/

    public String getTranscuredTime() {
        return transcuredTime;
    }

    public int getFinishTime() {
        return finishTime;
    }

    public String getColorProcess() {
        return colorProcess;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public void setAC(String AC) {
        this.AC = AC;
    }

    public void setAX(String AX) {
        this.AX = AX;
    }

    public void setBX(String BX) {
        this.BX = BX;
    }

    public void setCX(String CX) {
        this.CX = CX;
    }

    public void setDX(String DX) {
        this.DX = DX;
    }

    public int getTr() {
        return tr;
    }

    public int getTrts() {
        return trts;
    }

    public int getWaitingTime() {
        return waitingTime;
    }

    public int getCountOfInstructionExecuted() {
        return countOfInstructionExecuted;
    }

    
    
    
    
    
    
    
    
}
