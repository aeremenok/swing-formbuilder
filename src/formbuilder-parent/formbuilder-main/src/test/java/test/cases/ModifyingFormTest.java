package test.cases;

import domain.Person;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.testng.annotations.Test;

import javax.swing.*;
import java.awt.*;

import static org.formbuilder.mapping.form.FormFactories.MODIFYING;
import static org.testng.Assert.assertEquals;

/** @author eav Date: 01.09.2010 Time: 23:20:06 */
public class ModifyingFormTest
        extends FormTest
{
    @Test
    public void setAndGetValue()
    {
        final Form<Person> form = env.buildFormInEDT( FormBuilder.map( Person.class ).formsOf( MODIFYING ) );

        final JComponent component = form.asComponent();
        env.verifyLayout( component, JPanel.class, GridBagLayout.class );
        env.addToWindow( form );

        env.setValueInEDT( form, null );
        assert form.getValue() != null;

        final Person person = env.createPerson();
        env.setValueInEDT( form, person );
        requireSameInstance( form, person );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();

        wrapperPanel.label( "name" ).requireText( "Person's first name" );
        wrapperPanel.label( "age" ).requireText( "Age" );
        wrapperPanel.label( "birthDate" ).requireText( "Date of birth" );
        wrapperPanel.label( "gender" ).requireText( "Gender" );

        wrapperPanel.textBox( "name" ).requireText( person.getName() );
        wrapperPanel.spinner( "age" ).requireValue( person.getAge() );
        wrapperPanel.spinner( "birthDate" ).requireValue( person.getBirthDate() );
        wrapperPanel.checkBox( "gender" ).requireSelected();
    }

    private void requireSameInstance( final Form<Person> form,
                                      final Person oldValue )
    {
        final Person newValue = form.getValue();
        assertEquals( newValue, oldValue );
        assert form.getValue() == oldValue;
    }
}
