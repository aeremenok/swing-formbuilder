Generates swing components from java beans at runtime

Allows to create swing forms in the following manner:

```
Form<Person> form = FormBuilder.map( Person.class ).buildForm();
```

or

```
Form<Person> form = FormBuilder.map( Person.class ).with( new SampleBeanMapper<Person>() {
            @Override
            protected JComponent mapBean( Person sample,
                                          SampleContext<Person> ctx )
            {
                final Box box = Box.createHorizontalBox();
                box.add( ctx.label( sample.getName() ) );
                box.add( ctx.editor( sample.getName() ) );
                return box;
            }
} ).buildForm();
```

This is the new repository of http://code.google.com/p/swing-formbuilder/
