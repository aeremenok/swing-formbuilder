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
package domain;

/** @author aeremenok Date: 30.07.2010 Time: 13:53:28 */
public class Role
{
    private String name;

    public Role()
    {
    }

    public Role( final String name )
    {
        this.name = name;
    }

    @Override
    public boolean equals( final Object o )
    {
        if ( this == o )
        {
            return true;
        }
        if ( !( o instanceof Role ) )
        {
            return false;
        }

        final Role role = (Role) o;

        if ( name != null ? !name.equals( role.name ) : role.name != null )
        {
            return false;
        }

        return true;
    }

    public String getName()
    {
        return name;
    }

    @Override
    public int hashCode()
    {
        return name != null ? name.hashCode() : 0;
    }

    public void setName( final String name )
    {
        this.name = name;
    }

    @Override
    public String toString()
    {
        return name;
    }
}
