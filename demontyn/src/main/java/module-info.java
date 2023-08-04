module org.helmo {
	requires javafx.controls;
	requires java.desktop;
	requires javafx.graphics;
	requires javafx.base;

	opens org.helmo.gbeditor.viewmodels to javafx.base;


	//BD require
	requires mysql.connector.java;
	requires java.sql;

	exports org.helmo.gbeditor;

}