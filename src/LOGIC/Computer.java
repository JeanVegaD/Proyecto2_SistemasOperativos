/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package LOGIC;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 *
 * @author Jean
 */
public class Computer {
    
    
    String [][] showArrayTable = new String[5][500];
    
    int mainMemory;
    int virtualMemory;
    String selectedAlgothim;
    int quantum;
    int countQuantum = 0;
    String typeOfAssigment;
    int fixedMemoryValue;
    int paginationMemoryValue;
    ArrayList<Integer> mainSegmentation;
    ArrayList<Integer> virtualSegmentation;
    ArrayList<LOGIC.Process> loadedFiles;
    
    
    //computer things
    
    ArrayList<Object[]> mainMemoryContent = new ArrayList<>();
    ArrayList<Object[]> virtualMemoryContent = new ArrayList<>();
    LOGIC.Core C1;
    LOGIC.Core C2;
    LOGIC.Core C3;
    LOGIC.Core C4;
    LOGIC.Core C5;
    
    LOGIC.Core currentCore = null;
    ArrayList<LOGIC.Core> colaEjecucion = new ArrayList<LOGIC.Core>();
    
    
    
    public Computer(int p_mainMemory,int p_virtualMemory,String p_selectedAlgothim,int p_quantum,
        String p_typeOfAssigment,String p_fixedMemoryValue,String p_paginationMemoryValue,ArrayList<Integer> p_mainSegmentation,ArrayList<Integer> p_virtualSegmentation,ArrayList<LOGIC.Process> p_loadedFiles){
        
        
        
        this.mainMemory=p_mainMemory;
        this.virtualMemory= p_virtualMemory;
        this.selectedAlgothim=p_selectedAlgothim;
        this.quantum = p_quantum;
        this.typeOfAssigment = p_typeOfAssigment;
        if(tryParseInt(p_fixedMemoryValue)){
            this.fixedMemoryValue= Integer.parseInt(p_fixedMemoryValue);
        }else{
            this.fixedMemoryValue=0;
        }
        if(tryParseInt(p_paginationMemoryValue)){
            this.paginationMemoryValue= Integer.parseInt(p_paginationMemoryValue);
        }else{
            this.paginationMemoryValue=0;
        }
        this.mainSegmentation= p_mainSegmentation;
        this.virtualSegmentation= p_virtualSegmentation;
        this.loadedFiles=p_loadedFiles;
        
        setShowArrayTable();
        setConfigMemory();
        
        //definicion de cores
        C1 = new Core(0,selectedAlgothim);
        C2 = new Core(1,selectedAlgothim);
        C3 = new Core(2,selectedAlgothim);
        C4 = new Core(3,selectedAlgothim);
        C5 = new Core(4,selectedAlgothim);
         
    }
    
    public Computer(){
        setShowArrayTable();  
        setConfigMemory();
        
        
    }
    
    
    private boolean tryParseInt(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    //configura el arreglo que se muestra en pantalla
    private void setShowArrayTable(){
         Arrays.fill(showArrayTable[0], 0, 500, "");
         Arrays.fill(showArrayTable[1], 0, 500, "");
         Arrays.fill(showArrayTable[2], 0, 500, "");
         Arrays.fill(showArrayTable[3], 0, 500, "");
         Arrays.fill(showArrayTable[4], 0, 500, "");
    }
    
    //retorna el arreglo de los nucleos
    public String[][] getShowArrayTable() {
        return showArrayTable;
    }
    
    //condfigura la gestion de memoria de la aplicacion
    public void setConfigMemory(){
        
        if(typeOfAssigment.equals("Fixed")){
            int tempMain = 0;
            int cont = 0;
            for(int i =fixedMemoryValue; i<= mainMemory;i+=fixedMemoryValue ){
                Object[] tempObject = {null,null,fixedMemoryValue};
                mainMemoryContent.add(tempObject);
                cont++;
                tempMain=i;
            }
            if(mainMemory% fixedMemoryValue != 0){
                Object[] tempObject = {null,null,mainMemory-tempMain};
                mainMemoryContent.add(tempObject);
            }
            
            tempMain = 0;
            cont = 0;
            for(int i =fixedMemoryValue; i<= virtualMemory;i+=fixedMemoryValue ){
                Object[] tempObject = {null,null,fixedMemoryValue};
                virtualMemoryContent.add(tempObject);
                cont++;
                tempMain=i;
            }
            if(virtualMemory% fixedMemoryValue != 0){
                Object[] tempObject = {null,null,virtualMemory-tempMain};
                virtualMemoryContent.add(tempObject);
            }
            
        }else if (typeOfAssigment.equals("Pagination")){
           
            int tempMain = 0;
            int cont = 0;
            for(int i =paginationMemoryValue; i<= mainMemory;i+=paginationMemoryValue ){
                Object[] tempObject = {null,null,paginationMemoryValue};
                mainMemoryContent.add(tempObject);
                cont++;
                tempMain=i;
                   
            }
            if(mainMemory% paginationMemoryValue != 0){
                Object[] tempObject = {null,null,mainMemory-tempMain};
                mainMemoryContent.add(tempObject);
            }
            
            tempMain = 0;
            cont = 0;
            for(int i =paginationMemoryValue; i<= virtualMemory;i+=paginationMemoryValue ){
                Object[] tempObject = {null,null,paginationMemoryValue};
                virtualMemoryContent.add(tempObject);
                cont++;
                tempMain=i;
            }
            if(virtualMemory% paginationMemoryValue != 0){
               Object[] tempObject = {null,null,virtualMemory-tempMain};
               virtualMemoryContent.add(tempObject);
            }
        }
        else if (typeOfAssigment.equals("Segmentation")){
            int temp = 0 ;
            for (int i : mainSegmentation) {
               temp+=i;
            }
            int cont = 0;
            for (int i : mainSegmentation) {
                Object[] tempObject = {null,null,mainSegmentation.get(cont)};
                mainMemoryContent.add(tempObject);
                cont++;
            }
            if(temp!=mainMemory){
                Object[] tempObject = {null,null,mainMemory-temp};
                mainMemoryContent.add(tempObject);
            }
           
            
            //virtual
            temp = 0 ;
            for (int i : virtualSegmentation) {
               temp+=i;
            }
            
            cont = 0;
            for (int i : virtualSegmentation) {
                Object[] tempObject = {null,null,virtualSegmentation.get(cont)};
                virtualMemoryContent.add(tempObject);
                cont++;
            }
            if(temp!=virtualMemory){
               Object[] tempObject = {null,null,virtualMemory-temp};
                virtualMemoryContent.add(tempObject);
            }          
        }
        else{

        }
    }
    
    //con cada ejecucion actualiza los procesos en memoeria
    public void actualziarMemorias(int time){
        //se recorre la memoria principal buscando procesos finalizados
        for (int i = 0 ; i < mainMemoryContent.size(); i++) {
            if(mainMemoryContent.get(i)[0]!=null){
                LOGIC.Process tempProcess = (LOGIC.Process) mainMemoryContent.get(i)[0];
                if(tempProcess.getEstado().equals("finalized")){
                    mainMemoryContent.get(i)[0]= null;
                }
            }
        }
        
        //carga procesos de la memoria virtual a la principal
        for (int i = 0 ; i < virtualMemoryContent.size(); i++) {
            if(virtualMemoryContent.get(i)[0]!=null){
                LOGIC.Process tempProcess = (LOGIC.Process) virtualMemoryContent.get(i)[0];
                insertProcessOnMain(tempProcess); 
            }
        }
        
        //carga procesos nuevos
        for (int i = 0 ; i < loadedFiles.size(); i++) {
            if(loadedFiles.get(i).getEstado().equals("new")){
                if(insertProcessOnMain(loadedFiles.get(i))){
                    loadedFiles.get(i).setEstado("prepared");
                }else if(insertProcessOnVirtual(loadedFiles.get(i))){
                     loadedFiles.get(i).setEstado("prepared");
                }
            }
        }
        
        
        //asigna nucleos a los procesos
        //carga procesos de la memoria virtual a la principal
        for (int i = 0 ; i < mainMemoryContent.size(); i++) {
            LOGIC.Process tempProcess = (LOGIC.Process) mainMemoryContent.get(i)[0];
            if(tempProcess!=null){
                if(tempProcess.getEstado().equals("prepared")){
                    if(C1.getCurrentProcess()==null){
                        C1.setCurrentProcess(tempProcess);
                        tempProcess.loadProcesstoCore(time);
                        
                    }else if(C2.getCurrentProcess()==null){
                        C2.setCurrentProcess(tempProcess);
                        tempProcess.loadProcesstoCore(time);


                    }else if(C3.getCurrentProcess()==null){
                        C3.setCurrentProcess(tempProcess);
                        tempProcess.loadProcesstoCore(time);

                    }else if(C4.getCurrentProcess()==null){
                        C4.setCurrentProcess(tempProcess);
                        tempProcess.loadProcesstoCore(time);
                        
                    }else if(C5.getCurrentProcess()==null){
                        C5.setCurrentProcess(tempProcess);
                        tempProcess.loadProcesstoCore(time);
                    }
                }
            }
        }
        
    }
    
    public boolean insertProcessOnMain(LOGIC.Process tempProcess){
        if(typeOfAssigment.equals("Dynamic")){
            int temp = 0; 
            for(int i = 0 ; i < mainMemoryContent.size(); i++){
                temp+=(int)mainMemoryContent.get(i)[2];
            }
            if(mainMemory-temp>=tempProcess.getBurstTime()){
                Object[] tempObject = {tempProcess,tempProcess.getBurstTime(),tempProcess.getBurstTime()};
                mainMemoryContent.add(tempObject);
                return true;
            }else{
                return false;
            }
            
        }else{
            for(int i = 0 ; i < mainMemoryContent.size(); i++){
                if(mainMemoryContent.get(i)[0]==null &&  tempProcess.getBurstTime()<= (int)mainMemoryContent.get(i)[2]){
                    mainMemoryContent.get(i)[0] =tempProcess;
                    mainMemoryContent.get(i)[1] =tempProcess.getBurstTime();
                    return true;
                }
            }
            return false;
        }
    }
    
    public boolean insertProcessOnVirtual(LOGIC.Process tempProcess){
        if(typeOfAssigment.equals("Dynamic")){
            int temp = 0; 
            for(int i = 0 ; i < virtualMemoryContent.size(); i++){
                temp+=(int)virtualMemoryContent.get(i)[2];
            }
            if(virtualMemory-temp>=tempProcess.getBurstTime()){
                Object[] tempObject = {tempProcess,tempProcess.getBurstTime(),tempProcess.getBurstTime()};
                virtualMemoryContent.add(tempObject);
                return true;
            }else{
                return false;
            }
        }else{
            for(int i = 0 ; i < virtualMemoryContent.size(); i++){
                if(virtualMemoryContent.get(i)[0]==null &&  tempProcess.getBurstTime()<= (int)virtualMemoryContent.get(i)[2]){
                    virtualMemoryContent.get(i)[0] =tempProcess;
                    virtualMemoryContent.get(i)[1] =tempProcess.getBurstTime();
                    return true;
                }
            }
            return false;
        }
    }
    
    public void insertOnQueue(Core tempCore){
        if(selectedAlgothim.equals("FCFS - First Come First Served")){
            colaEjecucion.add(tempCore);
            Collections.sort(colaEjecucion);
            
        }else if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
             colaEjecucion.add(tempCore);
            
        }else if(selectedAlgothim.equals("SJF - Shortest Job First")){
            colaEjecucion.add(tempCore);
            Collections.sort(colaEjecucion);
            
        }else if(selectedAlgothim.equals("HHRR - Highest Response Ratio Next")){
            colaEjecucion.add(tempCore);
            
        }else{
            colaEjecucion.add(tempCore);
        }
        
        for(Core temp: colaEjecucion){
            System.out.println(temp.getCurrentProcess().getID());
        }
        System.out.println("");
    }
    
    public void loadprocesstoQueue(int time){
        
        if(C1.getCurrentProcess()!= null){
            if(C1.getCurrentProcess().getInitTime()==time && !colaEjecucion.contains(C1)){
                insertOnQueue(C1);
                if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
                    if(currentCore!=null){
                        insertOnQueue(currentCore);
                        currentCore = null;
                    }

                }
            }
        }
        if(C2.getCurrentProcess()!= null){
            if(C2.getCurrentProcess().getInitTime()==time && !colaEjecucion.contains(C2)){
                insertOnQueue(C2);
                if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
                    if(currentCore!=null){
                        insertOnQueue(currentCore);
                        currentCore = null;
                    }
                }
            }
        }
        if(C3.getCurrentProcess()!= null){
            if(C3.getCurrentProcess().getInitTime()==time && !colaEjecucion.contains(C3)){
                insertOnQueue(C3);
                if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
                    if(currentCore!=null){
                        insertOnQueue(currentCore);
                        currentCore = null;
                    }
                }
            }
        }
        if(C4.getCurrentProcess()!= null){
            if(C4.getCurrentProcess().getInitTime()==time && !colaEjecucion.contains(C4)){
                insertOnQueue(C4);
                if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
                    if(currentCore!=null){
                        insertOnQueue(currentCore);
                        currentCore = null;
                    }
                }
            }
        }
        if(C5.getCurrentProcess()!= null){
            if(C5.getCurrentProcess().getInitTime()==time && !colaEjecucion.contains(C5)){
                insertOnQueue(C5);
                if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
                    if(currentCore!=null){
                        insertOnQueue(currentCore);
                        currentCore = null;
                    }
                }
            }
        }
        
        
        
        
        
        
    }

    public void increaseTime(int time){
        actualziarMemorias(time);
        loadprocesstoQueue(time);
       
        if(selectedAlgothim.equals("RR - Round Robin")){

            if(this.countQuantum==quantum){
                if(currentCore!=null){
                    insertOnQueue(currentCore);
                    currentCore = null;
                }

                countQuantum=1;
            }else{

                this.countQuantum++;
            }
            
        }
        
        if(currentCore==null){
            setExecutionCore(time);
            
        }
        if(currentCore!=null){
            //executar instruccion
            currentCore.getCurrentProcess().executeInstruction();
            
            //pintar la ejecucion
            setShowExceutedProcessTime(currentCore.getPositionAtArray(),time,currentCore.getCurrentProcess().getColorProcess());
            
            //preguntar si ya termino el proceso y realizar algo
            if(currentCore.getCurrentProcess().isEndOFProcess()){
                currentCore.setCurrentProcess(null);
                currentCore=null;
            }
        }
        increaseWaitingTime(time);
        


    }
    
    private void setExecutionCore(int time){
        if(selectedAlgothim.equals("FCFS - First Come First Served")){
            if(colaEjecucion.size()>0){
                if(colaEjecucion.get(0).getCurrentProcess().getInitTime()<=time){
                    currentCore= colaEjecucion.get(0);
                    colaEjecucion.remove(0);
                    
                }
            }  
        }else if(selectedAlgothim.equals("SRT - Shortest Remaining Time")){
            if(colaEjecucion.size()>0){
                //obtener el proceso de remaingn time
                int bt = colaEjecucion.get(0).getCurrentProcess().getBurstTime();
                int et = colaEjecucion.get(0).getCurrentProcess().getCountOfInstructionExecuted();
                int restante =  bt- et;
                int  res =restante;
                Core tempCore=colaEjecucion.get(0);
                for(Core forCore: colaEjecucion){
                    bt = forCore.getCurrentProcess().getBurstTime();
                    et = forCore.getCurrentProcess().getCountOfInstructionExecuted();
                    restante =  bt- et;
                    if(restante<=res){
                        System.out.print("restante: ");
                        System.out.println(restante);
                        res=restante;
                        tempCore=forCore;
                    }
                }
                currentCore=tempCore;
                colaEjecucion.remove(tempCore);
            }  
        }else if(selectedAlgothim.equals("SJF - Shortest Job First")){
            if(colaEjecucion.size()>0){
                if(colaEjecucion.get(0).getCurrentProcess().getInitTime()<=time){
                    currentCore= colaEjecucion.get(0);
                    colaEjecucion.remove(0);
                }
            }  
        }else if(selectedAlgothim.equals("HHRR - Highest Response Ratio Next")){
            if(colaEjecucion.size()>0){
                //obtener el proceso de mejor tamano
                double rr =0;
                Core tempCore=null;
                for(Core forCore: colaEjecucion){
                    int s2 = forCore.getCurrentProcess().getBurstTime();
                    int w2 = forCore.getCurrentProcess().getWaitingTime();
                    int  suma2 = w2+s2;
                    double  rr2 = (double) suma2 / s2;
                    
                    if(rr2>=rr){
                        rr=rr2;
                        tempCore=forCore;
                    }
                }
                currentCore=tempCore;
                colaEjecucion.remove(tempCore);
            }  
        }else{
            if(colaEjecucion.size()>0){
                if(colaEjecucion.get(0).getCurrentProcess().getInitTime()<=time){
                    currentCore= colaEjecucion.get(0);
                    colaEjecucion.remove(0);
                    
                }
            } 
        }
    }
    
    public void increaseWaitingTime(int time){
        for(Core coretemp: colaEjecucion ){
            if(time> coretemp.getCurrentProcess().getInitTime()){
                 coretemp.getCurrentProcess().increaseWaitingTime();
            }
        }
    }
    
    private void setShowExceutedProcessTime(int i, int j, String color){
        showArrayTable[i][j]=color;
    }
    
    
    /*geters*/

    public ArrayList<Process> getLoadedFiles() {
        return loadedFiles;
    }

    public ArrayList<Integer> getVirtualSegmentation() {
        return virtualSegmentation;
    }

    public ArrayList<Object[]> getVirtualMemoryContent() {
        return virtualMemoryContent;
    }

    public ArrayList<Object[]> getMainMemoryContent() {
        return mainMemoryContent;
    }    
}
