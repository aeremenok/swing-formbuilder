/*
 * Copyright (C) 2010 Andrey Yeremenok (eav1986__at__gmail__com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and limitations under the License.
 */

/**
 *
 */
package domain;

/** @author aeremenok 2010 */
public class Account
{
    private String code;
    private Person person;
    private int amount;

    public Account()
    {
    }

    public Account( final String code )
    {
        this.code = code;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Account ) )
        {
            return false;
        }

        final Account account = (Account) o;

        if ( amount != account.amount )
        {
            return false;
        }
        if ( code != null ? !code.equals( account.code ) : account.code != null )
        {
            return false;
        }

        return true;
    }

    public int getAmount()
    {
        return amount;
    }

    public String getCode()
    {
        return code;
    }

    public Person getPerson()
    {
        return person;
    }

    @Override
    public int hashCode()
    {
        int result = code != null ? code.hashCode() : 0;
        result = 31 * result + amount;
        return result;
    }

    public void setAmount( final int amount )
    {
        this.amount = amount;
    }

    public void setCode( final String code )
    {
        this.code = code;
    }

    public void setPerson( final Person person )
    {
        this.person = person;
    }

    @Override
    public String toString()
    {
        return code;
    }
}
