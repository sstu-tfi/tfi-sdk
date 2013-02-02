package ru.sstu.web.jsf;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIViewRoot;
import javax.faces.context.FacesContext;

import org.apache.log4j.Logger;

import ru.sstu.web.core.Messages;

import com.sun.faces.application.ApplicationAssociate;
import com.sun.faces.application.ApplicationResourceBundle;

/**
 * {@code Messages} class provides access to JSF message resources.
 *
 * @author Alexander_Ignatov
 * @author Denis_Murashev
 * @since Web 1.0
 */
public final class JsfMessages extends Messages {

	/**
	 * Logger.
	 */
	private static Logger log = Logger.getLogger(JsfMessages.class);

	/**
	 * Initializes messages for JSF.
	 */
	public JsfMessages() {
		ApplicationAssociate associate = ApplicationAssociate.getInstance(
				FacesContext.getCurrentInstance().getExternalContext());
		Map<String, ApplicationResourceBundle> resourceBundles = associate
				.getResourceBundles();
		List<String> bundles = new ArrayList<String>();
		for (Map.Entry<String, ApplicationResourceBundle> entry
				: resourceBundles.entrySet()) {
			bundles.add(entry.getValue().getBaseName());
		}
		setBundles(bundles.toArray(new String[bundles.size()]));
	}

	/**
	 * This method returns <code>FacesMessage</code> instance with default
	 * bundle name and given resource ID.
	 *
	 * @param resourceId resource ID
	 * @return <code>FacesMessage</code> instance
	 */
	public FacesMessage getMessage(String resourceId) {
		return getMessage(resourceId, (Object[]) null);
	}

	/**
	 * This method returns <code>FacesMessage</code> instance with default
	 * bundle name, resource ID, and parameters.
	 *
	 * @param resourceId resource ID
	 * @param params     parameters
	 * @return <code>FacesMessage</code> instance
	 */
	public FacesMessage getMessage(String resourceId, Object... params) {
		FacesMessage message = null;
		for (String bundle : getBundles()) {
			message = getMessage(bundle, resourceId, params);
			// TODO Now it is always not null!
			if (message != null) {
				return message;
			}
		}
		return message;
	}

	/**
	 * This method returns <code>FacesMessage</code> instance with a given
	 * bundle name, resource ID, and parameters.
	 *
	 * @param bundleName bundle name
	 * @param resourceId resource ID
	 * @param params parameters
	 * @return <code>FacesMessage</code> instance
	 */
	public FacesMessage getMessage(String bundleName, String resourceId,
			Object... params) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		String appBundle = app.getMessageBundle();
		String summary = getString(appBundle, bundleName, resourceId, params);
		if (summary == null) {
			// TODO Questions should be generated
			// only after checking in all bundles.
			String questions = "???";
			StringBuilder buffer = new StringBuilder();
			buffer.append(questions).append(resourceId).append(questions);
			summary = buffer.toString();
		}
		String detail = getString(appBundle, bundleName, resourceId + "_detail",
				params);
		return new FacesMessage(summary, detail);
	}

	/**
	 * Get the resource string with the given ID and parameters from the bundle.
	 *
	 * @param bundle resource bundle
	 * @param resourceId resource ID
	 * @param params parameters
	 * @return message summary
	 */
	@Override
	public String getString(String bundle, String resourceId,
			Object... params) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application app = context.getApplication();
		String appBundle = app.getMessageBundle();
		return getString(appBundle, bundle, resourceId, params);
	}

	/**
	 * Get the resource string with the given ID and parameters from the bundle.
	 *
	 * @param bundle1 resource bundle1
	 * @param bundle2 resource bundle2
	 * @param resourceId resource ID
	 * @param params parameters
	 * @return message detail
	 */
	private static String getString(String bundle1, String bundle2,
			String resourceId, Object[] params) {
		FacesContext context = FacesContext.getCurrentInstance();
		String resource = null;
		Locale locale = getLocale(context);
		if (bundle1 != null) {
			resource = getResource(bundle1, resourceId);
		}
		if (resource == null) {
			resource = getResource(bundle2, resourceId);
		}
		if (resource == null) {
			return null; // no match
		}
		if (params == null) {
			return resource;
		}
		MessageFormat formatter = new MessageFormat(resource, locale);
		return formatter.format(params);
	}

	/**
	 * Provides resource with given id from given bundle.
	 *
	 * @param bundleName bundle name
	 * @param resourceId resource id
	 * @return text resource
	 */
	private static String getResource(String bundleName, String resourceId) {
		FacesContext context = FacesContext.getCurrentInstance();
		Locale locale = getLocale(context);
		ClassLoader loader = getClassLoader();
		ResourceBundle bundle = ResourceBundle.getBundle(bundleName, locale,
				loader);
		if (bundle != null) {
			try {
				return bundle.getString(resourceId);
			} catch (MissingResourceException ex) {
				log.info(resourceId + " resource is missing.");
			}
		}
		return null;
	}

	/**
	 * Gets the current locale.
	 *
	 * @param context view context
	 * @return locale
	 */
	private static Locale getLocale(FacesContext context) {
		Locale locale = null;
		UIViewRoot viewRoot = context.getViewRoot();
		if (viewRoot != null) {
			locale = viewRoot.getLocale();
		}
		if (locale == null) {
			locale = Locale.getDefault();
		}
		return locale;
	}

	/**
	 * Gets the current class loader. You need it to locate the resource bundle.
	 *
	 * @return class loader
	 */
	private static ClassLoader getClassLoader() {
		ClassLoader loader = Thread.currentThread().getContextClassLoader();
		if (loader == null) {
			loader = ClassLoader.getSystemClassLoader();
		}
		return loader;
	}
}
