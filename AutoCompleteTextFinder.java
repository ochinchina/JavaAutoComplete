import java.util.List;

/**
 *
 * @author Steven Ou
 */
public interface AutoCompleteTextFinder {
    /**
     * find all the strings starts with text
     */
    List< String > findAutoCompleteTextOf( String text );
}
