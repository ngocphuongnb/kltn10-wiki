/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package Index;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebService;
import org.apache.solr.client.solrj.SolrServerException;

/**
 *
 * @author tuandom
 */
@WebService()
public class WSIndex {

    /**
     * Web service operation
     */
    @WebMethod(operationName = "Index2SolrWiki")
    public boolean Index2SolrWiki(@WebParam(name = "title")
    String title, @WebParam(name = "timestamp")
    Calendar timestamp, @WebParam(name = "ip")
    String ip, @WebParam(name = "username")
    String username, @WebParam(name = "comment")
    String comment, @WebParam(name = "restriction")
    String restriction, @WebParam(name = "text")
    String text) throws IOException {

        MySolrJ ms = new MySolrJ();
        try {
           ms.ImportWiki2Solr(title, timestamp, ip, text, restriction, username, comment, 20000);
            return true;
        } catch (MalformedURLException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
       } catch (SolrServerException ex) {
            Logger.getLogger(WSIndex.class.getName()).log(Level.SEVERE, null, ex);
       }
       return false;
    }

}
