using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Messaging;
using System.Threading;
using log4net;
using log4net.Config;
using XMLCorporaFormats;

namespace StreamSimulator
{
    class StreamFiles
    {
        List<String> filelist = null;
        Messenger messenger = null;
        int delay = 10;
        private static readonly ILog logger = LogManager.GetLogger(typeof(StreamFiles));
        bool performConversion = true;

        public StreamFiles(List<String> files, bool conversion)
        {
            this.filelist = files;
            this.messenger = new Messenger();
            this.performConversion = conversion;

        }


        public void sendLoop()
        {
            while (true)
            {
                if (this.filelist != null && this.filelist.Count != 0)
                {

                    int messageNum = 1;
                    int messageCount = this.filelist.Count;
                    foreach (String file in this.filelist)
                    {
                        if (this.performConversion == true)
                        {
                            List<String> documents = XMLFormatConverter.convertFIRSTXMLtoGATE(file);
                            int docNr = 0;
                            foreach (String doc in documents)
                            {
                                messenger.sendMessage(doc);
                                logger.Info("Sends doc nr " + ++docNr + " from corpora number: " + messageNum++ + " / " + messageCount);
                                Thread.Sleep(delay);
                            }

                        }
                        else {
                            System.IO.StreamReader myFile = new System.IO.StreamReader(file);
                            String message = myFile.ReadToEnd();
                            myFile.Close();
                            messenger.sendMessage(message);
                            logger.Info("Sends corpora number: " + messageNum++ + " / " + messageCount);
                            Thread.Sleep(delay);
                        }


                    }
                }
            }

        }

        public void sendOnce()
        {
            if (this.filelist != null && this.filelist.Count != 0)
            {
                int messageNum = 1;
                int messageCount = this.filelist.Count;
                foreach (String file in this.filelist)
                {
                    if (this.performConversion == true)
                    {
                        List<String> documents = XMLFormatConverter.convertFIRSTXMLtoGATE(file);
                        if (documents.Count == 0) {
                            logger.Info("Empty parser result. Skipping corpora: " + messageNum++ + " / " + messageCount);
                        }
                        
                        foreach (String doc in documents)
                        {
                            messenger.sendMessage(doc);
                            logger.Info("Sends corpora number: " + messageNum++ + " / " + messageCount);
                            Thread.Sleep(delay);
                        }

                    }
                    else
                    {
                        System.IO.StreamReader myFile = new System.IO.StreamReader(file);
                        String message = myFile.ReadToEnd();
                        myFile.Close();
                        messenger.sendMessage(message);
                        logger.Info("Sends corpora number: " + messageNum++ + " / " + messageCount);
                        Thread.Sleep(delay);
                    }

                }
            }
            Console.Write("Streaming finished. Nothing more to send.");

        }


        
    }
}
