using System;
using System.Collections.Generic;
using System.Text;
using Latino.Workflows.TextMining;
using System.Xml;
using System.IO;
using System.Xml.Serialization;

namespace XMLCorporaFormats
{
    class Program
    {
        static void Main(string[] args)
        {
            // conversion from FIRST XML to GATE XML
            // load FIRST XML corpus
            DocumentCorpus corpus = new DocumentCorpus();
            corpus.ReadXml(new XmlTextReader(new StreamReader(@"D:\streamer\files\23_55_08_450f24c0969d49d2883fc17a6f4e2af0.xml")));
            // save documents as GATE XML
            XmlWriterSettings xmlSettings = new XmlWriterSettings();
            xmlSettings.Indent = true;
            xmlSettings.NewLineOnAttributes = true;
            xmlSettings.CheckCharacters = false;
            xmlSettings.Encoding = Encoding.UTF8;
            int i = 0;
            foreach (Document doc in corpus.Documents)
            {
                StreamWriter streamWriter = new StreamWriter(string.Format(@"D:\streamer\output\{0}.xml", ++i));
                XmlWriter writer = XmlWriter.Create(streamWriter, xmlSettings);
                doc.WriteGateXml(writer, /*writeTopElement=*/true, /*removeBoilerplate=*/true);
                //doc.WriteXml(writer, /*writeTopElement=*/true);
                String docstr = writer.ToString();

                writer.Close();
                streamWriter.Close();
                
            }
            Console.ReadKey();
        }



    }
}
