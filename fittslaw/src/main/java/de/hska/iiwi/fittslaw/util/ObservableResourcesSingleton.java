package de.hska.iiwi.fittslaw.util;

import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ObservableResourcesSingleton {

	private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

	private static ObservableResourcesSingleton instance = null;
	
	private ObservableResourcesSingleton() {
	}
	
	public static ObservableResourcesSingleton getInstance() {
		if (instance == null) {
			instance = new ObservableResourcesSingleton();
		}
		return instance;
	}

	public ObjectProperty<ResourceBundle> resourcesProperty() {
		return resources;
	}
	public final ResourceBundle getResources() {
		return resourcesProperty().get();
	}
	public final void setResources(ResourceBundle resources) {
		resourcesProperty().set(resources);
	}

	public StringBinding getStringBinding(String key) {
		return new StringBinding() {
			{
				bind(resourcesProperty());
			}
			@Override
			public String computeValue() {
				return getResources().getString(key);
			}
		};
	}
}