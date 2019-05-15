package biz.turnonline.ecosystem.widget.shared.rest.search;

/**
 * @author <a href="mailto:pohorelec@turnonlie.biz">Jozef Pohorelec</a>
 */
public class SearchGlobal
{
    private String id = null;

    private String name = null;

    private String description = null;

    private String link = null;

    private String imageUrl = null;

    private GlobalItemType type = null;

    public String getId()
    {
        return id;
    }

    public void setId( String id )
    {
        this.id = id;
    }

    public String getName()
    {
        return name;
    }

    public void setName( String name )
    {
        this.name = name;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription( String description )
    {
        this.description = description;
    }

    public String getLink()
    {
        return link;
    }

    public void setLink( String link )
    {
        this.link = link;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl( String imageUrl )
    {
        this.imageUrl = imageUrl;
    }

    public GlobalItemType getType()
    {
        return type;
    }

    public void setType( GlobalItemType type )
    {
        this.type = type;
    }
}
