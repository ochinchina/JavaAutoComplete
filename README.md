JavaAutoComplete
================
In one project, I need a auto-complete input component in java GUI. And start to search from Internet and found paper http://www.algosome.com/articles/java-jcombobox-autocomplete.html. In this paper, it implements the java auto-complete in simple way.

Borrowed idea from this project, I write a new version of java auto-complete component.

There are two ways to use this auto-complete component:

* use AutoCompleteJComboBox class

This class is derived from the JComboBox. If a auto-completed JComboBox wanted, this class can be directly replace the JComboBox with some extra initialization.
```java
For example:

//create a new AutoCompleteJComboBox

JFrame frame = ...

AutoCompleteJComboBox comboBox = new AutoCompleteJComboBox();

//create all the text can be auto-completed
ArrayList< String > autoCompleteTexts = new ArrayList< String >() {{
	add( "Test");
	add( "Text");
	add( "Book");
	add( "Bike");
}};
comboBox.init( new DefaultAutoCompleteTextFinder( autoCompleteTexts ));
 
frame.add( comboBox );
...
```

* use JComboBoxAutoCompleteInitializer class

The method 1 require you create a AutoCompleteJComboBox to replace existing JComboBox. But sometimes, the user does not want to use the auto-completed function in this way.

We can use JComboBoxAutoCompleteInitializer to attach "auto-completed" function to an already created JComboBox components.

For example:

```java
JFrame frame = ...

JComboBox comboBox = new JComboBox();
frame.add( comboBox );

//create all the text can be auto-completed
ArrayList< String > autoCompleteTexts = new ArrayList< String >() {{
	add( "Test");
	add( "Text");
	add( "Book");
	add( "Bike");
}};

JComboBoxAutoCompleteInitializer::init( comboBox, new DefaultAutoCompleteTextFinder( autoCompleteTexts ) );
 
...
```
