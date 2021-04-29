package securityservices.core.shared.services.serializers.xmlapis;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import org.xml.sax.InputSource;
//imports de JDOM
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;
import org.jdom2.output.Format;

import securityservices.core.shared.services.serializers.Xml;

public class JDom implements Xml {

    protected SAXBuilder builder; //Classe per crear un document JDOM
    protected Document doc; // classe que representa el document
    protected Element rootNode, node; // node arrel
    protected List subnodes; // llista de subnodes
    protected XMLOutputter jdomToXML;
    protected Boolean iscreated;

    public JDom() {
        builder = new SAXBuilder();
        iscreated = true;
    }

    @Override
    public void set(String xmlDoc) {
        try {
            builder = new SAXBuilder();

            InputSource is = new InputSource();
            is.setCharacterStream(new StringReader(xmlDoc));

            doc = (Document) builder.build(is);
            iscreated = true;
        } catch (JDOMException | IOException ex) {
            iscreated = false;
        }
        rootNode = doc.getRootElement();
        subnodes = rootNode.getChildren();
    }

    @Override
    public String toString() {
        String result = "";
        try {
            TransformerFactory transFabric = TransformerFactory.newInstance();
            Transformer transformer = transFabric.newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");

            jdomToXML = new XMLOutputter();
            jdomToXML.setFormat(Format.getPrettyFormat());
            result = jdomToXML.outputString(doc);
        } catch (TransformerConfigurationException ex) {
            return result;
        }

        return result;
    }

    @Override
    public void createDocument() {
        doc = new Document();
    }

    @Override
    public void setRootNode(String node) {
        rootNode = new Element(node);//creació del node arrel del document XML
        doc.setRootElement(rootNode);
    }

    @Override
    public void setNode(String node, String value) {
        this.node = new Element(node);
        this.node.addContent(value);
        rootNode.addContent(this.node);
    }

    @Override
    public String getRootNode() {
        return String.valueOf(doc.getRootElement());
    }

    @Override
    public String getValueNode(String node) {
        for (int i = 0; i < subnodes.size(); i++) {
            this.node = (Element) subnodes.get(i);

            if (this.node.getName().equals(node)) {
                return this.node.getText();
            }
        }
        return null;
    }

    @Override
    public String[] getSubNodes(String node) {
        return null;
    }

    @Override
    public void setAtributes(String node, String atribs) {

    }

    @Override
    public String[] getAtributes(String node) {
        return null;
    }

    @Override
    public void setSubNode(String node, String subnode, String value) {

    }

    @Override
    public void setArrayNodes(String node, ArrayList<String> subnodelist, ArrayList<String> nodeValueslist) {

    }
}
