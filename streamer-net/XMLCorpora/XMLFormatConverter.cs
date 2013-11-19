using System;
using System.Collections.Generic;
using System.Text;
using Latino.Workflows.TextMining;
using System.Xml;
using System.IO;
using System.Xml.Serialization;

namespace XMLCorporaFormats
{
    public class XMLFormatConverter
    {

        public static List<String> convertFIRSTXMLtoGATE(String file)
        {
            List<String> documents = new List<string>();

            // conversion from FIRST XML to GATE XML
            // load FIRST XML corpus
            DocumentCorpus corpus = new DocumentCorpus();
            corpus.ReadXml(new XmlTextReader(new StreamReader(file)));
            // save documents as GATE XML
            XmlWriterSettings xmlSettings = new XmlWriterSettings();
            xmlSettings.Indent = true;
            xmlSettings.NewLineOnAttributes = true;
            xmlSettings.CheckCharacters = false;
            xmlSettings.Encoding = Encoding.UTF8;
            //int i = 0;
            foreach (Document doc in corpus.Documents)
            {
                StringBuilder docXML = new StringBuilder();
                XmlWriter writer = XmlWriter.Create(docXML, xmlSettings);
                doc.WriteGateXml(writer, /*writeTopElement=*/true, /*removeBoilerplate=*/true);
                //doc.WriteXml(writer, /*writeTopElement=*/true);
                String docstr = docXML.ToString();
                documents.Add(docstr);

                writer.Close();


            }


            return documents;
        }

    }
}
