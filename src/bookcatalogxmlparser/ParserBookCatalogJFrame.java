/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookcatalogxmlparser;

import java.awt.Desktop;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.xml.parsers.ParserConfigurationException;
import org.xml.sax.SAXException;

/**
 *
 * @author Graf_Nameless
 */
public class ParserBookCatalogJFrame extends javax.swing.JFrame {
    public static File f1, f2;    
    JFileChooser fc = new JFileChooser();
    List<BookClass> books;
    /**
     * Creates new form ParserBookCatalogJFrame
     */
    public ParserBookCatalogJFrame() {
        initComponents();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        getFileJButton = new javax.swing.JButton();
        pathToFileJTextField = new javax.swing.JTextField();
        handleJButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        getFileJButton.setText("Файл");
        getFileJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                getFileJButtonActionPerformed(evt);
            }
        });

        pathToFileJTextField.setEditable(false);

        handleJButton.setText("Обработать");
        handleJButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                handleJButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(getFileJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(handleJButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pathToFileJTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(getFileJButton)
                    .addComponent(pathToFileJTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(handleJButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void getFileJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_getFileJButtonActionPerformed
        fc.setCurrentDirectory(new File("."));
        int returnVal = fc.showDialog(getFileJButton, "Attach");
        if (returnVal == JFileChooser.APPROVE_OPTION)
        {
            f1 = fc.getSelectedFile();
            pathToFileJTextField.setText(fc.getSelectedFile().getPath()); 
        }
    }//GEN-LAST:event_getFileJButtonActionPerformed

    private void handleJButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_handleJButtonActionPerformed
        boolean err = false;
        books = new ArrayList<>();
        try {
            xmlReader xml = new xmlReader(f1);
            books = xml.books;
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            err = true;
            JOptionPane.showMessageDialog(this, "Во время разбора файла возникла ошибка: " + ex.getMessage(), "Ошибка разбора", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        PrintToExcel pte = new PrintToExcel();
        int i = 1;
        for(BookClass book : books){
            pte.InsertData(book, i);
            i++;
        }
        try {
            pte.SaveAndCloseExcel("End.xlsx");
        } catch (IOException ex) {            
            err = true;
            JOptionPane.showMessageDialog(this, "Файл End.xlsx занят!", "Ошибка сохранения", JOptionPane.ERROR_MESSAGE);
            //Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        if(!err){
            Object[] options = {"Да", "Нет"};
            int n = JOptionPane.showOptionDialog(this,
            "Обработка завершена! " + 
            "Открыть файл?",
            "Обработка завершена!",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
            if(n == 0){
                try {
                    Desktop.getDesktop().open(new File("End.xlsx"));
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(this, "Ошибка при открытии файла End.xlsx!", "Ошибка открытия", JOptionPane.ERROR_MESSAGE);
                    //Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }//GEN-LAST:event_handleJButtonActionPerformed

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
            java.util.logging.Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ParserBookCatalogJFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ParserBookCatalogJFrame().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton getFileJButton;
    private javax.swing.JButton handleJButton;
    private javax.swing.JTextField pathToFileJTextField;
    // End of variables declaration//GEN-END:variables
}
