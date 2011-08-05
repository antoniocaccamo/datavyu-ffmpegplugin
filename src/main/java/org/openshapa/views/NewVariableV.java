package org.openshapa.views;

import com.usermetrix.jclient.Logger;
import org.openshapa.OpenSHAPA;
import org.openshapa.models.db.legacy.Column;
import org.openshapa.models.db.legacy.DataColumn;
import org.openshapa.models.db.legacy.LogicErrorException;
import org.openshapa.models.db.legacy.MatrixVocabElement;
import org.openshapa.models.db.legacy.NominalFormalArg;
import org.openshapa.models.db.legacy.SystemErrorException;

import com.usermetrix.jclient.UserMetrix;
import javax.swing.GroupLayout;
import javax.swing.LayoutStyle;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import javax.swing.undo.CompoundEdit;
import javax.swing.undo.UndoableEdit;
import org.openshapa.models.db.Datastore;
import org.openshapa.models.db.DeprecatedDatabase;
import org.openshapa.models.db.DeprecatedVariable;
import org.openshapa.models.db.legacy.Database;
import org.openshapa.undoableedits.AddVariableEdit;


/**
 * The dialog for users to add new variables to the spreadsheet.
 */
public final class NewVariableV extends OpenSHAPADialog {

    /** The database to add the new variable to. */
    private Datastore model;

    /** The logger for this class. */
    private static final Logger LOGGER = UserMetrix.getLogger(NewVariableV.class);

    /**
     * Constructor, creates a new form to create a new variable.
     * 
     * @param parent
     *            The parent of this form.
     * @param modal
     *            Should the dialog be modal or not?
     */
    public NewVariableV(final java.awt.Frame parent, final boolean modal) {
        super(parent, modal);
        LOGGER.event("newVar - show");
        initComponents();
        setName(this.getClass().getSimpleName());

        model = OpenSHAPA.getProjectController().getDB();

        // init button group
        buttonGroup1.add(textTypeButton);
        buttonGroup1.add(nominalTypeButton);
        buttonGroup1.add(predicateTypeButton);
        buttonGroup1.add(matrixTypeButton);
        buttonGroup1.add(integerTypeButton);
        buttonGroup1.add(floatTypeButton);

        getRootPane().setDefaultButton(okButton);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed"
    // desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        nameField = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        textTypeButton = new javax.swing.JRadioButton();
        nominalTypeButton = new javax.swing.JRadioButton();
        predicateTypeButton = new javax.swing.JRadioButton();
        matrixTypeButton = new javax.swing.JRadioButton();
        integerTypeButton = new javax.swing.JRadioButton();
        floatTypeButton = new javax.swing.JRadioButton();
        okButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        java.util.ResourceBundle bundle =
                java.util.ResourceBundle
                        .getBundle("org/openshapa/views/resources/NewVariableV"); // NOI18N
        setTitle(bundle.getString("title.text")); // NOI18N
        setName("Form"); // NOI18N
        setResizable(false);

        jLabel1.setText(bundle.getString("jLabel2.text")); // NOI18N
        jLabel1.setName("jLabel1"); // NOI18N

        jLabel2.setText(bundle.getString("nameField.text")); // NOI18N
        jLabel2.setName("jLabel2"); // NOI18N

        nameField.setName("nameField"); // NOI18N

        jPanel1
                .setBorder(javax.swing.BorderFactory
                        .createTitledBorder("Type:"));
        jPanel1.setName("jPanel1"); // NOI18N

        textTypeButton.setSelected(true);
        textTypeButton.setLabel(bundle.getString("textTypeButton.text")); // NOI18N
        textTypeButton.setName("textTypeButton"); // NOI18N

        nominalTypeButton.setLabel(bundle.getString("nominalTypeButton.text")); // NOI18N
        nominalTypeButton.setName("nominalTypeButton"); // NOI18N

        predicateTypeButton.setLabel(bundle
                .getString("predicateTypeButton.text")); // NOI18N
        predicateTypeButton.setName("predicateTypeButton"); // NOI18N

        matrixTypeButton.setLabel(bundle.getString("matrixTypeButton.text")); // NOI18N
        matrixTypeButton.setName("matrixTypeButton"); // NOI18N

        integerTypeButton.setLabel(bundle.getString("integerTypeButton.text")); // NOI18N
        integerTypeButton.setName("integerTypeButton"); // NOI18N

        floatTypeButton.setLabel(bundle.getString("floatTypeButton.text")); // NOI18N
        floatTypeButton.setName("floatTypeButton"); // NOI18N

        GroupLayout jPanel1Layout =
                new GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout
                .setHorizontalGroup(jPanel1Layout
                        .createParallelGroup(
                                GroupLayout.Alignment.LEADING)
                        .addGroup(
                                jPanel1Layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(textTypeButton)
                                                        .addComponent(nominalTypeButton))
                                        .addGap(16, 16, 16)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(matrixTypeButton)
                                                        .addComponent(predicateTypeButton))
                                        .addPreferredGap(
											    LayoutStyle.ComponentPlacement.RELATED,
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(integerTypeButton)
                                                        .addComponent(floatTypeButton))
                                        .addContainerGap()));
        jPanel1Layout
                .setVerticalGroup(jPanel1Layout
                        .createParallelGroup(
                                GroupLayout.Alignment.LEADING)
                        .addGroup(
                                jPanel1Layout
                                        .createSequentialGroup()
                                        .addGroup(
                                                jPanel1Layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(textTypeButton)
                                                        .addGroup(
                                                                jPanel1Layout
                                                                        .createSequentialGroup()
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(
                                                                                                integerTypeButton)
                                                                                        .addComponent(
                                                                                                predicateTypeButton))
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addGroup(
                                                                                jPanel1Layout
                                                                                        .createParallelGroup(
                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(
                                                                                                floatTypeButton)
                                                                                        .addComponent(
                                                                                                matrixTypeButton)
                                                                                        .addComponent(
                                                                                                nominalTypeButton))))
                                        .addContainerGap()));

        org.jdesktop.application.ResourceMap resourceMap =
                org.jdesktop.application.Application.getInstance(
                        org.openshapa.OpenSHAPA.class).getContext()
                        .getResourceMap(NewVariableV.class);
        okButton.setText(resourceMap.getString("okButton.text")); // NOI18N
        okButton.setName("okButton"); // NOI18N
        okButton.setPreferredSize(new java.awt.Dimension(65, 23));
        okButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                okButtonActionPerformed(evt);
            }
        });

        cancelButton.setLabel(bundle.getString("cancelButton.text")); // NOI18N
        cancelButton.setName("cancelButton"); // NOI18N
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(final java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        GroupLayout layout =
                new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout
                .setHorizontalGroup(layout
                        .createParallelGroup(
                                GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addContainerGap(
                                                GroupLayout.DEFAULT_SIZE,
                                                Short.MAX_VALUE)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(
                                                LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.TRAILING,
                                                                false)
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                okButton,
                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.PREFERRED_SIZE)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                cancelButton))
                                                        .addGroup(
                                                                GroupLayout.Alignment.LEADING,
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addComponent(
                                                                                jLabel1)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                nameField))
                                                        .addComponent(
																jPanel1,
                                                                GroupLayout.Alignment.LEADING,
                                                                GroupLayout.PREFERRED_SIZE,
                                                                GroupLayout.DEFAULT_SIZE,
                                                                GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap()));
        layout
                .setVerticalGroup(layout
                        .createParallelGroup(
                                GroupLayout.Alignment.LEADING)
                        .addGroup(
                                layout
                                        .createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(
                                                layout
                                                        .createParallelGroup(
                                                                GroupLayout.Alignment.LEADING)
                                                        .addComponent(jLabel2)
                                                        .addGroup(
                                                                layout
                                                                        .createSequentialGroup()
                                                                        .addGroup(
                                                                                layout
                                                                                        .createParallelGroup(
                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(
                                                                                                nameField,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.PREFERRED_SIZE)
                                                                                        .addComponent(
                                                                                                jLabel1))
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addComponent(
                                                                                jPanel1,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                Short.MAX_VALUE)
                                                                        .addPreferredGap(
                                                                                LayoutStyle.ComponentPlacement.RELATED)
                                                                        .addGroup(
                                                                                layout
                                                                                        .createParallelGroup(
                                                                                                GroupLayout.Alignment.BASELINE)
                                                                                        .addComponent(
                                                                                                cancelButton)
                                                                                        .addComponent(
                                                                                                okButton,
                                                                                                GroupLayout.PREFERRED_SIZE,
                                                                                                GroupLayout.DEFAULT_SIZE,
                                                                                                GroupLayout.PREFERRED_SIZE))))
                                        .addContainerGap()));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    @Deprecated private Database getLegacyDB() {
        return ((DeprecatedDatabase) model).getDatabase();
    }

    /**
     * The action to invoke when the user selects the ok button.
     * 
     * @param evt
     *            The event that triggered this action.
     */
    private void okButtonActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_okButtonActionPerformed
        try {
            LOGGER.event("newVar - create column:" + getVariableType());
            Column.isValidColumnName(getLegacyDB(), getVariableName());
            DataColumn dc = new DataColumn(getLegacyDB(),
                                           getVariableName(),
                                           getVariableType());        
            DeprecatedVariable var = new DeprecatedVariable(dc);
            var.setSelected(true);            

            // perform the operation           
            model.addVariable(var);

            // If the column is a matrix - default to a single nominal variable
            // rather than untyped.
            if (matrixTypeButton.isSelected()) {
                MatrixVocabElement mve = getLegacyDB().getMatrixVE(var.getLegacyVariable().getItsMveID());
                mve.deleteFormalArg(0);
                mve.appendFormalArg(new NominalFormalArg(getLegacyDB(), "<arg0>"));
                getLegacyDB().replaceMatrixVE(mve);
            }
                        
            // record the effect
            UndoableEdit edit = new AddVariableEdit(getVariableName(), getVariableType(), matrixTypeButton.isSelected());                       
             
            // Display any changes.
            OpenSHAPA.getApplication().getMainView().getComponent().revalidate();

            dispose();
            finalize();
            
            // notify the listeners
            OpenSHAPA.getView().getUndoSupport().postEdit(edit);
                    
        // Whoops, user has done something strange - show warning dialog.
        } catch (LogicErrorException fe) {
            OpenSHAPA.getApplication().showWarningDialog(fe);

        // Whoops, programmer has done something strange - show error message.
        } catch (SystemErrorException e) {
            LOGGER.error("Unable to add variable to database", e);
            OpenSHAPA.getApplication().showErrorDialog();

        // Whoops, unable to destroy dialog correctly.
        } catch (Throwable e) {
            LOGGER.error("Unable to release window NewVariableV.", e);
        }
    }// GEN-LAST:event_okButtonActionPerformed

    /**
     * The action to invoke when the user selects the cancel button.
     * 
     * @param evt
     *            The event that triggered this action.
     */
    private void cancelButtonActionPerformed(final java.awt.event.ActionEvent evt) {// GEN-FIRST:event_cancelButtonActionPerformed
        try {
            dispose();
            finalize();

            // Whoops, unable to destroy dialog correctly.
        } catch (Throwable e) {
            LOGGER.error("Unable to release window NewVariableV.", e);
        }
    }// GEN-LAST:event_cancelButtonActionPerformed

    /**
     * @return The name of the new variable the user has specified.
     */
    public String getVariableName() {
        return nameField.getText();
    }

    /**
     * @return The type of variable the user has selected to use.
     */
    public MatrixVocabElement.MatrixType getVariableType() {
        if (textTypeButton.isSelected()) {
            return MatrixVocabElement.MatrixType.TEXT;
        } else if (nominalTypeButton.isSelected()) {
            return MatrixVocabElement.MatrixType.NOMINAL;
        } else if (predicateTypeButton.isSelected()) {
            return MatrixVocabElement.MatrixType.PREDICATE;
        } else if (matrixTypeButton.isSelected()) {
            return MatrixVocabElement.MatrixType.MATRIX;
        } else if (integerTypeButton.isSelected()) {
            return MatrixVocabElement.MatrixType.INTEGER;
        } else if (floatTypeButton.isSelected()) {
            return MatrixVocabElement.MatrixType.FLOAT;
        } else {
            return MatrixVocabElement.MatrixType.UNDEFINED;
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton cancelButton;
    private javax.swing.JRadioButton floatTypeButton;
    private javax.swing.JRadioButton integerTypeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JRadioButton matrixTypeButton;
    private javax.swing.JTextField nameField;
    private javax.swing.JRadioButton nominalTypeButton;
    private javax.swing.JButton okButton;
    private javax.swing.JRadioButton predicateTypeButton;
    private javax.swing.JRadioButton textTypeButton;
    // End of variables declaration//GEN-END:variables
}
