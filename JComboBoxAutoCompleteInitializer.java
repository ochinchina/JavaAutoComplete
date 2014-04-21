import java.awt.Component;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.JTextComponent;

/**
 * @author Steven Ou
 */
public class JComboBoxAutoCompleteInitializer {
        
    public static void init( final JComboBox combobox, final AutoCompleteTextFinder autoCompleteTextFinder ) {
        combobox.setEditable(true);
        Component c = combobox.getEditor().getEditorComponent();
        if ( c instanceof JTextComponent ){
            final JTextComponent tc = (JTextComponent)c;
            tc.getDocument().addDocumentListener( new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {
                    update();
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    update();
                }

                @Override
                public void changedUpdate(DocumentEvent e) {
                }
                
                private void update(){
                    SwingUtilities.invokeLater(new Runnable(){                    
                        @Override
                        public void run() {
                            combobox.setEditable( false );
                            combobox.removeAllItems();
                            List< String > findResult = autoCompleteTextFinder.findAutoCompleteTextOf(tc.getText() );
                            if( findResult.isEmpty() || !findResult.get( 0 ).equalsIgnoreCase( tc.getText() )) {
                                combobox.addItem( tc.getText() );
                            }
                            for( String s: findResult )  {
                                combobox.addItem( s );
                            }
                            combobox.setPopupVisible(true);
                            combobox.setEditable(true);
                            combobox.grabFocus();
                        }
                    });
                }
            } );
                        
            tc.addFocusListener( new FocusListener() {

                @Override
                public void focusGained(FocusEvent e) {
                    if ( tc.getText().length() > 0 ){
                        combobox.setPopupVisible(true);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                }
                
            });
        }
                
    } 
    
    
    public static void main( String[] args ) {
        List<String> myWords = new ArrayList<String>() {{
            add("car");
            add("cap");
            add("canadian");
            add("cape");
            add("caprecious");
            add("catepult");
            add("bike");
        }};
        
        AutoCompleteTextFinder finder = new DefaultAutoCompleteTextFinder( myWords );
        
        JFrame frame = new JFrame();
        JComboBox comboBox = new JComboBox();

        frame.add( comboBox );
        
        JComboBoxAutoCompleteInitializer.init( comboBox, finder );

        frame.pack();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        frame.setVisible(true);
    }
}
