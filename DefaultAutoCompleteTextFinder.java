import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The interface AutoCompleteTextFinder default implementation.
 * 
 * @author Steven Ou
 */
public class DefaultAutoCompleteTextFinder implements AutoCompleteTextFinder {

    private List< String > mTexts = new ArrayList< String >();
    
    public DefaultAutoCompleteTextFinder( List< String > textList ) {
        if( textList != null ) {
            mTexts.addAll( textList );
        }
        Collections.sort( mTexts );
    }
    
    public DefaultAutoCompleteTextFinder() {
        
    }
    
    public void add( String text ) {
        if( text != null ) {
            mTexts.add( text );
            Collections.sort( mTexts );
        }
    }
    
    @Override
    public List<String> findAutoCompleteTextOf(String text) {
        if( text == null || text.isEmpty() ) {
            return new ArrayList< String >( mTexts );
        }
                
        ArrayList< String > result = new ArrayList< String >();
                
        for( String s: mTexts ) {
            if( s.toLowerCase().startsWith( text.toLowerCase() ) ) {
                result.add( s );
            }
        }

        return result;
    }
    
}
