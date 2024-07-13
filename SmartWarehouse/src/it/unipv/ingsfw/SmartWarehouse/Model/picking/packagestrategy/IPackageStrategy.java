package it.unipv.ingsfw.SmartWarehouse.Model.picking.packagestrategy;

public interface IPackageStrategy {
	public static final int SMALL =10;
	public static final int MEDIUM =15;
	public static final int LARGE =20;
	public String  calculatePackages();
   	public boolean isPackageCorrect(String typeP, int quantity,int fr);
	public int getCountfrl();
	public int getCountfrm();
	public int getCountfrs();
	public int getCountPack();	
}
