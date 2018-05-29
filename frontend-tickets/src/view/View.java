package view;

import model.Model;

public abstract class View {

	private Model model;
	
	public View(Model model)
	{
		this.model = model;
	}

	public abstract void showView();
	
	public Model getModel()
	{
		return model;
	}
		
}
