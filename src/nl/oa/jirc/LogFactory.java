package nl.oa.jirc;

/**
 *
 * @author Michael Martinsen
 */
public class LogFactory {

    public static Log getLog(Class<?> aClass) {
        return new Log(aClass.getSimpleName());
    }
    
}
