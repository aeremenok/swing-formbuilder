package org.formbuilder.mapping.form;

import org.formbuilder.Form;
import org.formbuilder.mapping.BeanMapping;

import javax.annotation.Nonnull;
import javax.swing.*;

/**
 * Most common implementations of {@link FormFactory}
 *
 * @author eav Date: 01.09.2010 Time: 23:00:08
 * @see FormFactory
 * @see Form
 * @see BeanModifyingForm
 * @see BeanReplicatingForm
 */
public enum FormFactories
        implements FormFactory
{
    /** creates {@link BeanModifyingForm}s */
    MODIFYING
            {
                public <B> Form<B> createForm( @Nonnull final JComponent panel,
                                               @Nonnull final Class<B> beanClass,
                                               @Nonnull final BeanMapping beanMapping )
                {
                    return new BeanModifyingForm<B>( panel, beanMapping, beanClass );
                }
            },
    /** creates {@link BeanReplicatingForm}s */
    REPLICATING
            {
                public <B> Form<B> createForm( @Nonnull final JComponent panel,
                                               @Nonnull final Class<B> beanClass,
                                               @Nonnull final BeanMapping beanMapping )
                {
                    return new BeanReplicatingForm<B>( panel, beanClass, beanMapping );
                }
            }
}
