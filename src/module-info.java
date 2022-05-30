module Tester {
	requires javafx.controls;
	requires javafx.graphics;
	requires javafx.media;
	requires javafx.base;
	
	opens application to javafx.graphics, javafx.fxml;
}
