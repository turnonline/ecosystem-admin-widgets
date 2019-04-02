package org.ctoolkit.turnonline.widget.shared.ui;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public interface HasModel<T>
{
    void bind( T model );

    void fill( T model );
}
