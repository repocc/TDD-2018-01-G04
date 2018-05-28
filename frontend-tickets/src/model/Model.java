package model;

import java.util.Observable;

import controller.ControllerContext;

public class Model extends Observable 
{
	private ControllerContext context;
	
	public Model(ControllerContext context)
	{
		this.context = context;
	}
	
	public void notifyContext()
	{
		//TODO: Change context according to guest or admin
		context.start();
	}
}
