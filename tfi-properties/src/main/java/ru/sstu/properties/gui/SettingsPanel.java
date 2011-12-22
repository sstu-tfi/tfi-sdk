package ru.sstu.properties.gui;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import ru.sstu.properties.core.PropertyException;
import ru.sstu.properties.core.PropertyHelper;

/**
 * <code>SettingsPanel</code> class represents panel for editing of properties.
 *
 * @author Denis_Murashev
 *
 * @param <T> class that stores properties
 * @since Properties 1.0
 */
public class SettingsPanel<T> extends JPanel {

	private static final long serialVersionUID = 8179383598369273055L;

	private final T settings;
	private List<JTextField> textFields = new ArrayList<JTextField>();

	/**
	 * Initializes panel for given settings.
	 *
	 * @param settings settings to be managed
	 * @param resource resource bundle name
	 * @throws PropertyException if cannot create panel for given settings
	 */
	public SettingsPanel(T settings, String resource) throws PropertyException {
		this.settings = settings;
		init(resource);
	}

	/**
	 * @return the settings
	 */
	public T getSettings() {
		return settings;
	}

	/**
	 * Updates settings.
	 *
	 * @throws PropertyException if cannot update properties
	 */
	public void update() throws PropertyException {
		Map<Object, Object> properties = new HashMap<Object, Object>();
		for (JTextField f : textFields) {
			Object key = f.getName();
			Object value = f.getText();
			properties.put(key, value);
		}
		PropertyHelper.load(settings, properties);
	}

	private void init(String resource) throws PropertyException {
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTHWEST;
		c.fill = GridBagConstraints.HORIZONTAL;
		final int inset = 5;
		c.insets = new Insets(inset, inset, inset, inset);
		c.gridx = 0;
		c.gridy = 0;
		ResourceBundle bundle = ResourceBundle.getBundle(resource);
		Map<Object, Object> properties = PropertyHelper.toProperties(settings);
		final int count = 15;
		for (Object key : properties.keySet()) {
			add(new JLabel(bundle.getString(key.toString())), c);
			c.gridx = 1;
			JTextField field = new JTextField(properties.get(key).toString(),
					count);
			field.setName(key.toString());
			textFields.add(field);
			add(field, c);
			c.gridy++;
			c.gridx = 0;
		}
	}
}
