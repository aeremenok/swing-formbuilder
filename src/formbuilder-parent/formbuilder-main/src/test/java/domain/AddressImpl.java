package domain;

/** @author eav Date: 01.09.2010 Time: 23:34:00 */
public class AddressImpl
        implements Address
{
    private String street;
    private int house;

    public String getStreet()
    {
        return street;
    }

    public void setStreet( final String street )
    {
        this.street = street;
    }

    public int getHouse()
    {
        return house;
    }

    public void setHouse( final int house )
    {
        this.house = house;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof AddressImpl ) )
        {
            return false;
        }

        final AddressImpl address = (AddressImpl) o;

        if ( house != address.house )
        {
            return false;
        }
        if ( street != null ? !street.equals( address.street ) : address.street != null )
        {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode()
    {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + house;
        return result;
    }

    @Override
    public String toString()
    {
        return "AddressImpl{" + "street='" + street + '\'' + ", house=" + house + '}';
    }
}
