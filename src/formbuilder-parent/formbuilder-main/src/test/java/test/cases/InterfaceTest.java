package test.cases;

import domain.Address;
import domain.AddressImpl;
import org.fest.swing.fixture.ContainerFixture;
import org.fest.swing.fixture.JPanelFixture;
import org.formbuilder.Form;
import org.formbuilder.FormBuilder;
import org.formbuilder.mapping.beanmapper.SampleBeanMapper;
import org.formbuilder.mapping.beanmapper.SampleContext;
import org.testng.annotations.Test;

import javax.annotation.Nonnull;
import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static org.formbuilder.mapping.form.FormFactories.MODIFYING;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNull;

/** @author eav Date: 01.09.2010 Time: 23:35:15 */
public class InterfaceTest
        extends FormTest
{
    @Test
    public void mapReplicating()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ) );
        env.addToWindow( form );

        final Address oldValue = new AddressImpl();
        env.setValueInEDT( form, oldValue );
        assertNull( oldValue.getStreet() );

        final ContainerFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "street" ).requireEmpty();

        final Address newValue = form.getValue();

        assertNull( newValue.getStreet(), newValue.getStreet() );

        assertEquals( oldValue, newValue );
        assert oldValue != newValue;
    }

    @Test( dependsOnMethods = "mapReplicating" )
    public void mapModifying()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ).formsOf( MODIFYING ) );
        env.addToWindow( form );

        final Address oldValue = new AddressImpl();
        env.setValueInEDT( form, oldValue );

        final Address newValue = form.getValue();

        assertEquals( oldValue, newValue );
        assert oldValue == newValue;
    }

    @Test( dependsOnMethods = "mapModifying" )
    public void mapNullModifying()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ).formsOf( MODIFYING ) );
        env.addToWindow( form );

        env.setValueInEDT( form, null );

        final Address newValue = form.getValue();
        assert newValue != null;
    }

    @Test( dependsOnMethods = "mapNullModifying" )
    public void useSampleMapping()
    {
        final Form<Address> form = env.buildFormInEDT( FormBuilder.map( Address.class ).with( new SampleBeanMapper<Address>()
        {
            @Override
            protected JComponent mapBean( @Nonnull final Address sample,
                                          @Nonnull final SampleContext<Address> ctx )
            {
                final JPanel panel = new JPanel( new BorderLayout() );
                panel.add( ctx.editor( sample.getHouse() ), NORTH );
                panel.add( ctx.editor( sample.getStreet() ), SOUTH );
                return panel;
            }
        } ) );
        env.addToWindow( form );

        final Address oldValue = new AddressImpl();
        oldValue.setHouse( 7 );
        oldValue.setStreet( "Main st." );
        
        env.setValueInEDT( form, oldValue );

        final JPanelFixture wrapperPanel = env.getWrapperPanelFixture();
        wrapperPanel.textBox( "street" ).requireText( "Main st." );
        wrapperPanel.spinner( "house" ).requireValue( 7 );
    }
}
