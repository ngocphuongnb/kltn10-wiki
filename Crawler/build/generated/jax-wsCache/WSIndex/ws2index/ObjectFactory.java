
package ws2index;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the ws2index package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _IOException_QNAME = new QName("http://Index/", "IOException");
    private final static QName _Index2SolrWiki_QNAME = new QName("http://Index/", "Index2SolrWiki");
    private final static QName _Index2SolrWikiResponse_QNAME = new QName("http://Index/", "Index2SolrWikiResponse");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: ws2index
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Index2SolrWiki }
     * 
     */
    public Index2SolrWiki createIndex2SolrWiki() {
        return new Index2SolrWiki();
    }

    /**
     * Create an instance of {@link Index2SolrWikiResponse }
     * 
     */
    public Index2SolrWikiResponse createIndex2SolrWikiResponse() {
        return new Index2SolrWikiResponse();
    }

    /**
     * Create an instance of {@link IOException }
     * 
     */
    public IOException createIOException() {
        return new IOException();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link IOException }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Index/", name = "IOException")
    public JAXBElement<IOException> createIOException(IOException value) {
        return new JAXBElement<IOException>(_IOException_QNAME, IOException.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Index2SolrWiki }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Index/", name = "Index2SolrWiki")
    public JAXBElement<Index2SolrWiki> createIndex2SolrWiki(Index2SolrWiki value) {
        return new JAXBElement<Index2SolrWiki>(_Index2SolrWiki_QNAME, Index2SolrWiki.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link Index2SolrWikiResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://Index/", name = "Index2SolrWikiResponse")
    public JAXBElement<Index2SolrWikiResponse> createIndex2SolrWikiResponse(Index2SolrWikiResponse value) {
        return new JAXBElement<Index2SolrWikiResponse>(_Index2SolrWikiResponse_QNAME, Index2SolrWikiResponse.class, null, value);
    }

}
