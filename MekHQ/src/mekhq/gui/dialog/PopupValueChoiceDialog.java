/*
 * AlertPopup.java
 *
 * Created on Jan 6, 2010, 10:46:02 PM
 */

package mekhq.gui.dialog;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ResourceBundle;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.WindowConstants;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import megamek.common.util.EncodeControl;

/**
 *
 * @author natit
 */
public class PopupValueChoiceDialog extends JDialog implements WindowListener, ChangeListener {

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private JButton btnDone;
    private JButton btnCancel;
    private JPanel pnlButton;
    private JSpinner value;
    private SpinnerNumberModel model;
    private boolean validData = true;
    private int max;
    private int min;
    // End of variables declaration//GEN-END:variables

    /**
	 * This was originally set up as a text entry dialog, but there is
	 * really no reason to use it instead of the pre-fab inputdialog that
	 * comes with java and it was actually causing problems because it uses
	 * a textpane instead of a textfield. Since it is currently only called by
	 * the set xp command in MekHQView, I am going to refactor it into a
	 * numeric value setter using a spinner.
	 */
	private static final long serialVersionUID = 8376874926997734492L;
	/** Creates new form */
	public PopupValueChoiceDialog(Frame parent, boolean modal, String title, int current, int min) {
		super(parent, modal);
        this.min = min;
        model = new SpinnerNumberModel(current, min, null, 1);
        setTitle(title);
        initComponents();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
	}
	
    public PopupValueChoiceDialog(Frame parent, boolean modal, String title, int current, int min, int max) {
        super(parent, modal);
        this.max = max;
        this.min = min;
        model = new SpinnerNumberModel(current, min, max, 1);
        setTitle(title);
        initComponents();
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(this);
    }

    private void initComponents() {

    	pnlButton = new JPanel();
        btnDone = new JButton();
        btnCancel = new JButton();
        value = new JSpinner(model);

        // Verifier so people get limited to the 1-100 range when using manual input
        value.addChangeListener(this);

		ResourceBundle resourceMap = ResourceBundle.getBundle("mekhq.resources.PopupValueChoiceDialog", new EncodeControl()); //$NON-NLS-1$
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setName("Form"); // NOI18N

        btnDone.setText(resourceMap.getString("btnDone.text")); // NOI18N
        btnDone.setName("btnDone"); // NOI18N
        btnDone.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnDoneActionPerformed(evt);
            }
        });

        btnCancel.setText(resourceMap.getString("btnCancel.text")); // NOI18N
        btnCancel.setName("btnCancel"); // NOI18N
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                btnCancelActionPerformed(evt);
            }
        });

        pnlButton.setLayout(new GridLayout(0,2));
        pnlButton.add(btnDone);
        pnlButton.add(btnCancel);

        value.setName("value"); // NOI18N

        getContentPane().setLayout(new BorderLayout());

        getContentPane().add(value,BorderLayout.CENTER);
        getContentPane().add(pnlButton, BorderLayout.PAGE_END);
        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnDoneActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        if (validData) {
            this.setVisible(false);
        } else {
            showInvalidPopup();
        }
    }//GEN-LAST:event_btnDoneActionPerformed

    private void btnCancelActionPerformed(ActionEvent evt) {//GEN-FIRST:event_btnDoneActionPerformed
        value.getModel().setValue(-1);
    	this.setVisible(false);
    }

    /**
    * @param args the command line arguments
    */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                PopupValueChoiceDialog dialog = new PopupValueChoiceDialog(new JFrame(), true, "Label", 0, 0, 1);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    public int getValue() {
    	return (Integer)value.getValue();
    }


    @Override
    public void windowActivated(WindowEvent arg0) {
    }

    @Override
    public void windowClosed(WindowEvent arg0) {
    }

    @Override
    public void windowClosing(WindowEvent arg0) {
        if (arg0.getComponent() != this.btnDone) {
            value.getModel().setValue(-1);
            this.setVisible(false);
        }
    }

    @Override
    public void windowDeactivated(WindowEvent arg0) {
    }

    @Override
    public void windowDeiconified(WindowEvent arg0) {
    }

    @Override
    public void windowIconified(WindowEvent arg0) {
    }

    @Override
    public void windowOpened(WindowEvent arg0) {
    }

    @Override
    public void stateChanged(ChangeEvent arg0) {
        if (value != null) {
            Integer val = (Integer)((JSpinner)value).getModel().getValue();
            if ((max > 0 && val > max) || val < min) {
                validData = false;
            } else {
                validData = true;
            }
        }
    }

    private void showInvalidPopup() {
        JOptionPane.showMessageDialog(null,
                "Accepted values for bulk purchases are "+min+"-"+max
                    +System.lineSeparator()+"You've entered: "+((Integer)((JSpinner)value).getModel().getValue()),
                "Invalid value",
                JOptionPane.ERROR_MESSAGE);
    }
}
