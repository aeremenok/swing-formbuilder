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

/** @author aeremenok Date: 09.08.2010 Time: 17:29:02 */
public class Address
{
    private String country;
    private String city;
    private int house;

    public String getCity()
    {
        return city;
    }

    public String getCountry()
    {
        return country;
    }

    public int getHouse()
    {
        return house;
    }

    public void setCity( final String city )
    {
        this.city = city;
    }

    public void setCountry( final String country )
    {
        this.country = country;
    }

    public void setHouse( final int house )
    {
        this.house = house;
    }
}
