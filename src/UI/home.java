/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package UI;

import LOGIC.Computer;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

/**
 *
 * @author Jean
 */
public class home extends javax.swing.JFrame {

    /**
     * Creates new form home
     */
    
    Computer computer;
    int sizeOfDisply = 0;
    int executionTime = 0;
    DefaultTableModel modelInitProcess;
    DefaultTableModel modelTableCores;
    
    DefaultTableModel modelTableMain;
    DefaultTableModel modelTableVirtual;
    
    public home(int p_mainMemory,int p_virtualMemory,String p_selectedAlgothim,int p_quantum,
        String p_typeOfAssigment,String p_fixedMemoryValue,String paginationMemoryValue,ArrayList<Integer> p_mainSegmentation,ArrayList<Integer> p_virtualSegmentation,ArrayList<LOGIC.Process> loadedFiles) {
         try{
            setLocationRelativeTo(null);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        
        initComponents();
        
        computer = new Computer(p_mainMemory,p_virtualMemory,p_selectedAlgothim,p_quantum,p_typeOfAssigment,p_fixedMemoryValue,paginationMemoryValue,p_mainSegmentation,p_virtualSegmentation,loadedFiles);
        
        lbl_mainMemory.setText(Integer.toString(p_mainMemory));
        lbl_virtualMemory.setText(Integer.toString(p_virtualMemory));
        lbl_plannignAlgorithm.setText(p_selectedAlgothim);
        lbl_memoryAssigment.setText(p_typeOfAssigment);
        
        setTableConfig();
        actualizarTablaInicialProcesos();
        
        sizeOfDisply= computer.getShowArrayTable()[0].length;
        setTableConfig();
        
        
        
        Thread thread = new Thread(){
            public void run(){
                try {
                    execution();
                } catch (InterruptedException ex) {
                    Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
        
        
    
    }
    
    public home(){
        try{
            setLocationRelativeTo(null);
            JFrame.setDefaultLookAndFeelDecorated(true);
            JDialog.setDefaultLookAndFeelDecorated(true);
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        }catch (Exception e){
            e.printStackTrace();
        }
        initComponents();
        
        computer = new Computer();
        sizeOfDisply= computer.getShowArrayTable()[0].length;
        setTableConfig();
        //actualizarTablaInicialProcesos();
        //paintTableProcess();
        //actualizarTablasMemorias()
        
        
  
    
        Thread thread = new Thread(){
            public void run(){
                try {
                    execution();
                } catch (InterruptedException ex) {
                    Logger.getLogger(home.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
        thread.start();
    }
    
    public void execution() throws InterruptedException{
        while(true){
            lbl_excution.setText(Integer.toString(executionTime));
            computer.increaseTime(executionTime);
            
            //tablee
            reFillTable();
            repaintTable();  
            actualizarTablasMemorias();
            actualizarTablaInicialProcesos();
            
            Thread.sleep(1000);
            executionTime++;
            
        }
    }
    
    
    
    /*Rellemna la tabla de los procesos*/
    private void actualizarTablaInicialProcesos(){
        modelInitProcess = (DefaultTableModel) tbl_processFiles.getModel();
        clearTable(modelInitProcess);
        
         ArrayList<LOGIC.Process> tempLoadedProcess = computer.getLoadedFiles();
          for( int i = 0; i<tempLoadedProcess.size(); i++){
            LOGIC.Process tempProcess= tempLoadedProcess.get(i);
            Object[] objectData = new Object[] { 
                tempProcess.getName(),
                String.valueOf(tempProcess.getInitTime()),
                String.valueOf(tempProcess.getBurstTime()),
                String.valueOf(tempProcess.getFinishTime()),
                String.valueOf(tempProcess.getTr()),
                String.valueOf(tempProcess.getTrts()),
                tempProcess.getAC(),
                tempProcess.getAX(),
                tempProcess.getBX(),
                tempProcess.getCX(),
                tempProcess.getDX()
                        };
            modelInitProcess.addRow(objectData);            
        }
        tbl_processFiles.setModel(modelInitProcess);
        paintTableProcess();
        
    }
    
    
    private void paintTableProcess(){
        ArrayList<LOGIC.Process> tempLoadedProcess = computer.getLoadedFiles();
        for( int i = 0; i<11; i++){

            tbl_processFiles.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);

                final LOGIC.Process temp = tempLoadedProcess.get(row);
                l.setBackground(Color.decode(temp.getColorProcess()));
                return l;
                }
            }); 

        }
         tbl_processFiles.repaint();
        
    }
    
    
    /*rellena las tablas de la memoria*/
    
    private void actualizarTablasMemorias(){
        modelTableMain = (DefaultTableModel) tbl_mainMemory.getModel();
        clearTable(modelTableMain);
        ArrayList<Object[]> tempMainMemory = computer.getMainMemoryContent();
        for( int i = 0; i<tempMainMemory.size(); i++){
            modelTableMain.addRow(tempMainMemory.get(i));            
        }
        tbl_mainMemory.setModel(modelTableMain);
        
        modelTableVirtual = (DefaultTableModel) tbl_virtualMemory.getModel();
        clearTable(modelTableVirtual);
         ArrayList<Object[]>  tempVirtualMemory = computer.getVirtualMemoryContent();
          for( int i = 0; i<tempVirtualMemory.size(); i++){
            modelTableVirtual.addRow(tempVirtualMemory.get(i));            
        }
        tbl_virtualMemory.setModel(modelTableVirtual);
    }
    
    
    
    /*Limpia cualquier tabla con el modelo especifico*/
    private void clearTable(DefaultTableModel p_model) {
        if (p_model.getRowCount() > 0) {
            int countrows = p_model.getRowCount();
            for (int i = countrows; i > 0; i--) {
                p_model.removeRow(i - 1);
            }
        }
    }
    
    
    /*establece la configuracion inicial de la tabla de procesos*/
    private void setTableConfig(){
        DefaultTableModel model = new DefaultTableModel(); 
        TableColumnModel columnModel = tbl_showCores.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(50);
       
        for( int i = 0; i<computer.getShowArrayTable()[0].length; i++){
            model.addColumn(i);
            
        }
        String[][] temp = computer.getShowArrayTable();
            
        model.addRow(temp[0]);
        model.addRow(temp[1]);
        model.addRow(temp[2]);
        model.addRow(temp[3]);
        model.addRow(temp[4]);
        
        tbl_showCores.setModel(model);
        
    }
    
    
    /*rellena la tabla de procesos en cada ejecucion*/
    private void reFillTable(){
        modelTableCores = (DefaultTableModel) tbl_showCores.getModel();
        clearTable(modelTableCores);
        
        String[][] temp = computer.getShowArrayTable();
        
        modelTableCores.addRow(temp[0]);
        modelTableCores.addRow(temp[1]);
        modelTableCores.addRow(temp[2]);
        modelTableCores.addRow(temp[3]);
        modelTableCores.addRow(temp[4]);
        
        tbl_showCores.setModel(modelTableCores);
    }
    
 
    /**repinta la tabla de los procesos*/
    private void repaintTable(){
        for( int i = 0; i<sizeOfDisply; i++){
            tbl_showCores.getColumnModel().getColumn(i).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
                JLabel l = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
                if(value==""){
                    l.setBackground(Color.white);
                    l.setForeground(Color.white);
                }else{
                    l.setBackground(Color.decode(value.toString()));
                    l.setForeground(Color.decode(value.toString()));
                }
                return l;
                }
            }); 
            tbl_showCores.repaint();
        }
    }

   
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel24 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tbl_processFiles = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_showCores = new javax.swing.JTable();
        jLabel4 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        lbl_excution = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        lbl_mainMemory = new javax.swing.JLabel();
        lbl_memoryAssigment = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        lbl_virtualMemory = new javax.swing.JLabel();
        lbl_plannignAlgorithm = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_virtualMemory = new javax.swing.JTable();
        jScrollPane7 = new javax.swing.JScrollPane();
        tbl_mainMemory = new javax.swing.JTable();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();

        jLabel24.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        jLabel24.setForeground(new java.awt.Color(69, 90, 100));
        jLabel24.setText("Memory assignment");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel25.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(69, 90, 100));
        jLabel25.setText("Virtual memory");
        jPanel1.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 30, 130, 40));

        tbl_processFiles.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process", "Arrival time", "Service time", "Final time", "Tr", "Tr/Ts", "AC", "AX", "BX", "CX", "DX"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, true, true, true, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tbl_processFiles);

        jPanel1.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 700, 170));

        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);

        tbl_showCores.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4", "Title 5", "Title 6", "Title 7", "Title 8", "Title 9", "Title 10", "Title 11", "Title 12", "Title 13", "Title 14", "Title 15", "Title 16", "Title 17", "Title 18", "Title 19", "Title 20", "Title 21", "Title 22", "Title 23", "Title 24", "Title 25", "Title 26", "Title 27", "Title 28", "Title 29", "Title 30", "Title 31", "Title 32", "Title 33", "Title 34", "Title 35", "Title 36", "Title 37", "Title 38", "Title 39", "Title 40", "Title 41", "Title 42", "Title 43", "Title 44", "Title 45", "Title 46", "Title 47", "Title 48", "Title 49", "Title 50", "Title 51", "Title 52", "Title 53", "Title 54", "Title 55", "Title 56", "Title 57", "Title 58", "Title 59", "Title 60", "Title 61", "Title 62", "Title 63", "Title 64", "Title 65", "Title 66", "Title 67", "Title 68", "Title 69", "Title 70", "Title 71", "Title 72", "Title 73", "Title 74", "Title 75", "Title 76", "Title 77", "Title 78", "Title 79", "Title 80", "Title 81", "Title 82", "Title 83", "Title 84", "Title 85", "Title 86", "Title 87", "Title 88", "Title 89", "Title 90", "Title 91", "Title 92", "Title 93", "Title 94", "Title 95", "Title 96", "Title 97", "Title 98", "Title 99", "Title 100"
            }
        ));
        tbl_showCores.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tbl_showCores.setEnabled(false);
        tbl_showCores.setRowHeight(25);
        tbl_showCores.getTableHeader().setResizingAllowed(false);
        tbl_showCores.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(tbl_showCores);

        jPanel1.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 340, 1310, 170));

        jLabel4.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(69, 90, 100));
        jLabel4.setText("Information");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(1100, 30, -1, -1));
        jPanel1.add(jSeparator2, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 270, 1390, 20));

        jLabel26.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(69, 90, 100));
        jLabel26.setText("Cores execution");
        jPanel1.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 290, 210, 40));

        jLabel27.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel27.setForeground(new java.awt.Color(69, 90, 100));
        jLabel27.setText("Main memory");
        jPanel1.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 30, 130, 40));

        lbl_excution.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lbl_excution.setForeground(new java.awt.Color(69, 90, 100));
        lbl_excution.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_excution.setText("Main memory");
        jPanel1.add(lbl_excution, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 210, 150, 30));

        jLabel29.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(69, 90, 100));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel29.setText("Execution time");
        jPanel1.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 210, 150, 30));

        jLabel30.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(69, 90, 100));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel30.setText("Core 5");
        jPanel1.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, 60, 20));

        jLabel31.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(69, 90, 100));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel31.setText("Virtual memory");
        jPanel1.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 120, 150, 30));

        lbl_mainMemory.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lbl_mainMemory.setForeground(new java.awt.Color(69, 90, 100));
        lbl_mainMemory.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_mainMemory.setText("Main memory");
        jPanel1.add(lbl_mainMemory, new org.netbeans.lib.awtextra.AbsoluteConstraints(1230, 90, 150, 30));

        lbl_memoryAssigment.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lbl_memoryAssigment.setForeground(new java.awt.Color(69, 90, 100));
        lbl_memoryAssigment.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_memoryAssigment.setText("Main memory");
        jPanel1.add(lbl_memoryAssigment, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 150, 130, 30));

        jLabel34.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(69, 90, 100));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel34.setText("Planning algorithm");
        jPanel1.add(jLabel34, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 180, 150, 30));

        jLabel35.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel35.setForeground(new java.awt.Color(69, 90, 100));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel35.setText("Memory assignment");
        jPanel1.add(jLabel35, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 150, 150, 30));

        lbl_virtualMemory.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lbl_virtualMemory.setForeground(new java.awt.Color(69, 90, 100));
        lbl_virtualMemory.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_virtualMemory.setText("Main memory");
        jPanel1.add(lbl_virtualMemory, new org.netbeans.lib.awtextra.AbsoluteConstraints(1240, 120, 140, 30));

        lbl_plannignAlgorithm.setFont(new java.awt.Font("Microsoft YaHei UI", 0, 14)); // NOI18N
        lbl_plannignAlgorithm.setForeground(new java.awt.Color(69, 90, 100));
        lbl_plannignAlgorithm.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        lbl_plannignAlgorithm.setText("Main memory");
        jPanel1.add(lbl_plannignAlgorithm, new org.netbeans.lib.awtextra.AbsoluteConstraints(1250, 180, 130, 30));

        jLabel28.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 24)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(69, 90, 100));
        jLabel28.setText("Process");
        jPanel1.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 290, 40));

        tbl_virtualMemory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process", "BT", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(tbl_virtualMemory);

        jPanel1.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(900, 70, 160, 170));

        tbl_mainMemory.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Process", "BT", "Size"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(tbl_mainMemory);

        jPanel1.add(jScrollPane7, new org.netbeans.lib.awtextra.AbsoluteConstraints(730, 70, 160, 170));

        jLabel32.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(69, 90, 100));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel32.setText("Main memory");
        jPanel1.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(1090, 90, 150, 30));

        jLabel33.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(69, 90, 100));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel33.setText("Core 1");
        jPanel1.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 370, 70, 20));

        jLabel36.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(69, 90, 100));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel36.setText("Core 2");
        jPanel1.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 395, 60, 20));

        jLabel37.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(69, 90, 100));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel37.setText("Core 3");
        jPanel1.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 420, 60, 20));

        jLabel38.setFont(new java.awt.Font("Microsoft YaHei UI", 1, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(69, 90, 100));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel38.setText("Core 4");
        jPanel1.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 445, 60, 20));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1406, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 531, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(home.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new home().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel lbl_excution;
    private javax.swing.JLabel lbl_mainMemory;
    private javax.swing.JLabel lbl_memoryAssigment;
    private javax.swing.JLabel lbl_plannignAlgorithm;
    private javax.swing.JLabel lbl_virtualMemory;
    private javax.swing.JTable tbl_mainMemory;
    private javax.swing.JTable tbl_processFiles;
    private javax.swing.JTable tbl_showCores;
    private javax.swing.JTable tbl_virtualMemory;
    // End of variables declaration//GEN-END:variables
}
