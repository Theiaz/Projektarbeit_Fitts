package de.hska.iiwi.fittslaw;

import java.util.ResourceBundle;

import javafx.beans.binding.StringBinding;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;

public class ObservableResourceSingleton {

	private ObjectProperty<ResourceBundle> resources = new SimpleObjectProperty<>();

	private static ObservableResourceSingleton instance = null;
	
	private ObservableResourceSingleton() {
	}
	
	public static ObservableResourceSingleton getInstance() {
		if (instance == null) {
			instance = new ObservableResourceSingleton();
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