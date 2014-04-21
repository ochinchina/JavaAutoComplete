import java.awt.Component;
import java.awt.GridLayout;
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
 *
 * @author Steven Ou
 */
public class AutoCompleteJComboBox extends JComboBox{
    AutoCompleteTextFinder mAutoCompleteTextFinder;
    
    public AutoCompleteJComboBox() {
        super();
    }
    public AutoCompleteJComboBox( AutoCompleteTextFinder autoCompleteTextFinder) {
        super();
        init( autoCompleteTextFinder );
    }
    
    public void init( AutoCompleteTextFinder autoCompleteTextFinder) {
        
        mAutoCompleteTextFinder = autoCompleteTextFinder;        
        setEditable(true);
        Component c = getEditor().getEditorComponent();
        
        if ( c instanceof JTextComponent ){
            final JTextComponent tc = (JTextComponent)c;
            tc.getDocument().addDocumentListener(new DocumentListener(){

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
                            setEditable( false );
                            removeAllItems();
                            List< String > texts = mAutoCompleteTextFinder.findAutoCompleteTextOf(tc.getText() );                            
                            if( !texts.contains( tc.getText() ) ) {
                                addItem( tc.getText() );
                            }
                            for( String s: texts )  {
                                addItem( s );
                            }
                            setPopupVisible(true);
                            setEditable(true);
                            tc.grabFocus();
                        }
                    });
                }
                
            });
            
            tc.addFocusListener( new FocusListener() {

                @Override
                public void focusGained(FocusEvent e) {
                    if ( tc.getText().length() > 0 ){
                        setPopupVisible(true);
                    }
                }

                @Override
                public void focusLost(FocusEvent e) {
                }
                
            });
        }
        
    }
    
    public static void main( String[] args ) {
        

        AutoCompleteTextFinder finder = new AutoCompleteTextFinder() {
            private List<String> mTexts = new ArrayList<String>() {{
                add("bike");
                add("car");
                add("cap");
                add("cape");
                add("canadian");
                add("caprecious");
                add("catepult");
            }};
            
            @Override
            public List<String> findAutoCompleteTextOf(String text) {            
                if( text == null || text.isEmpty() ) {
                    return mTexts;
                }
                
                ArrayList< String > result = new ArrayList< String >();
                
                for( String s: mTexts ) {
                    if( s.toLowerCase().startsWith( text.toLowerCase() ) ) {
                        result.add( s );
                    }
                }
                
                return result;
            }
            
        };

				JFrame frame = new JFrame();

                                frame.setLayout( new GridLayout( 5, 1 ));
                                /*JComboBox comboBox = new JComboBox();
                                comboBox.setEditable(true);
                                frame.add( comboBox );
                                comboBox = new JComboBox();
                                comboBox.setEditable(true);
                                frame.add(comboBox );
                                comboBox = new JComboBox();
                                comboBox.setEditable(true);
                                frame.add( comboBox );
                                comboBox = new JComboBox();
                                comboBox.setEditable(true);
                                frame.add( comboBox );
                                comboBox = new JComboBox();
                                comboBox.setEditable(true);
                                frame.add( comboBox );*/
				frame.add( new AutoCompleteJComboBox( finder ) );
                                frame.add( new AutoCompleteJComboBox( finder ) );
                                frame.add( new AutoCompleteJComboBox( finder ) );
                                frame.add( new AutoCompleteJComboBox( finder ) );
                                frame.add( new AutoCompleteJComboBox( finder ) );
                                

				frame.pack();

				frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

				frame.setVisible(true);
    }
}

