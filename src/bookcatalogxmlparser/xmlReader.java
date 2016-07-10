/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bookcatalogxmlparser;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.*;
import org.xml.sax.SAXException;
/**
 *
 * @author Graf_Nameless
 */
public class xmlReader {
    public List<BookClass> books;
    public xmlReader(File f) throws ParserConfigurationException, SAXException, IOException{
        books = new ArrayList<>();
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = docFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(f);
        doc.getDocumentElement().normalize();
        NodeList booksNL = doc.getElementsByTagName("book");        
        for(int i = 0; i < booksNL.getLength(); i++){
            BookClass book = new BookClass();
            Node bookN = booksNL.item(i);
            Element bookE = (Element) bookN;
            book.setId(bookE.getAttribute("id"));
            book.setAuthor(bookE.getElementsByTagName("author").item(0).getTextContent());
            book.setDescription(bookE.getElementsByTagName("description").item(0).getTextContent());
            book.setGenre(bookE.getElementsByTagName("genre").item(0).getTextContent());
            book.setPrice(bookE.getElementsByTagName("price").item(0).getTextContent());
            try {
                book.setPublish_date(bookE.getElementsByTagName("publish_date").item(0).getTextContent());
            } catch (ParseException ex) {
                Logger.getLogger(xmlReader.class.getName()).log(Level.SEVERE, null, ex);
            }
            book.setTitle(bookE.getElementsByTagName("title").item(0).getTextContent());
            books.add(book);
        }
    }
    
}
