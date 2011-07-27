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

import org.formbuilder.annotations.UIHidden;
import org.formbuilder.annotations.UIReadOnly;
import org.formbuilder.annotations.UITitle;

import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

/** @author aeremenok 2010 */
public class Person
{
    private String name;
    private String description;
    private int age;
    private Date birthDate;
    private boolean gender;
    private Set<Account> goodAccounts;
    private Set<Account> badAccounts;
    private Role role;
    private long id;

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Person ) )
        {
            return false;
        }

        final Person person = (Person) o;

        if ( age != person.age )
        {
            return false;
        }
        if ( birthDate != null ? !birthDate.equals( person.birthDate ) : person.birthDate != null )
        {
            return false;
        }
        if ( name != null ? !name.equals( person.name ) : person.name != null )
        {
            return false;
        }

        return true;
    }

    public int getAge()
    {
        return age;
    }

    public Set<Account> getBadAccounts()
    {
        return badAccounts;
    }

    @UIReadOnly
    @UITitle( "Date of birth" )
    public Date getBirthDate()
    {
        return birthDate;
    }

    public String getDescription()
    {
        return description;
    }

    public Set<Account> getGoodAccounts()
    {
        return goodAccounts;
    }

    @UIHidden
    public long getId()
    {
        return id;
    }

    @Size( min = 3 )
    @UITitle( "Person's first name" )
    public String getName()
    {
        return name;
    }

    public Role getRole()
    {
        return role;
    }

    @Override
    public int hashCode()
    {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + age;
        result = 31 * result + ( birthDate != null ? birthDate.hashCode() : 0 );
        return result;
    }

    public boolean isGender()
    {
        return gender;
    }

    public void setAge( final int age )
    {
        this.age = age;
    }

    public void setBadAccounts( final Set<Account> badAccounts )
    {
        this.badAccounts = badAccounts;
    }

    public void setBirthDate( final Date birthDate )
    {
        this.birthDate = birthDate;
    }

    public void setDescription( final String description )
    {
        this.description = description;
    }

    public void setGender( final boolean gender )
    {
        this.gender = gender;
    }

    public void setGoodAccounts( final Set<Account> goodAccounts )
    {
        this.goodAccounts = goodAccounts;
    }

    public void setId( final long id )
    {
        this.id = id;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    public void setRole( final Role role )
    {
        this.role = role;
    }

    @Override
    public String toString()
    {
        return "Person{" + "name='" + name + '\'' + ", age=" + age + ", birthDate=" + birthDate + '}';
    }
}
