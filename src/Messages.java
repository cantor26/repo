import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Locale;

public class Messages {
	private static final String BUNDLE_NAME = "messages"; //$NON-NLS-1$

	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	
	/*private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	.getBundle(BUNDLE_NAME,Locale.ENGLISH);
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
	.getBundle(BUNDLE_NAME,Locale.FRENCH);*/

	private Messages() {
	}

	public static String getString(String key) {
		try {
			return RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			return '!' + key + '!';
		}
	}
}
