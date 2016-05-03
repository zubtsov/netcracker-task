package entities;

import javax.xml.bind.annotation.XmlElement;

public class Discipline
{
    private String name;
    private String description;


    public Discipline()
    {
    }

    public Discipline(String name, String description)
    {
        this.name = name;
        this.description = description;
    }
    @XmlElement(name = "name")
    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    @XmlElement(name = "description")
    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    @Override
    public boolean equals(Object obj)
    {
        //Написан неверно вообще говоря
        Discipline d = (Discipline) obj;
        return name.equals(d.name) && description.equals(d.description);
    }
}
