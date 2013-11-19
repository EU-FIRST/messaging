using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Messaging;
using System.Threading;
using log4net;
using log4net.Config;
using System.IO;

namespace Test
{
    class Test
    {
        private static readonly ILog logger = LogManager.GetLogger(typeof(Test));
        //Test method
        static void Main(string[] args)
        {
            Random random = new Random();
            Messenger messenger = new Messenger();

            double messageNum = 0;
            double inMessageNum = 0;
            int messageTest = 3;
            int delay = 10;
            if (args.Length == 1)
            {
                messageTest = Convert.ToInt32(args[0]);
            }
            else if (args.Length == 2)
            {
                messageTest = Convert.ToInt32(args[0]);
                delay = Convert.ToInt32(args[1]);
            }

            switch (messageTest)
            {
                // sends test messages
                case 1:
                    for (int i = 0; i <= 1000; i++)
                    {
                        messenger.sendMessage(i.ToString());
                        logger.Info("Sends message number: " + messageNum++);
                        Thread.Sleep(delay);
                    }
                    messenger.stopMessaging();
                    Thread.Sleep(delay);
                    break;
                // Receives messages until the finish command
                case 2:
                    while (!messenger.isMessagingFinished())
                    {
                        String inMessage = messenger.getMessage();

                        if (inMessage != null)
                        {
                            logger.Info("Gets message number: " + inMessageNum++);
                            Thread.Sleep(delay);

                        }
                    }
                    break;
                // Sends files in the dataset to the message listener
                case 3:
                    string[] filePaths = Directory.GetFiles(@"Dataset", "*");
                    foreach (string file in filePaths)
                    {
                        System.IO.StreamReader myFile =
                         new System.IO.StreamReader(file);
                        String message = myFile.ReadToEnd();
                        myFile.Close();
                        messenger.sendMessage(message);
                        logger.Info("Sends message number: " + messageNum++);
                        Thread.Sleep(delay);

                    }
                    messenger.stopMessaging();
                    Thread.Sleep(1000);
                    break;
                // Sends received messages
                case 4:
                    while (!messenger.isMessagingFinished())
                    {
                        String inMessage = messenger.getMessage();

                        if (inMessage != null)
                        {
                            logger.Info("Gets message number: " + inMessageNum++);
                            messenger.sendMessage(inMessage);
                            logger.Info("Sends message number: " + inMessageNum);
                            Thread.Sleep(delay);
                        }
                    }
                    break;
                // Sends files in the dataset to the message listener in a loop
                case 5:
                    while (true)
                    {
                        string[] filePaths2 = Directory.GetFiles(@"Dataset", "*");
                        foreach (string file in filePaths2)
                        {
                            System.IO.StreamReader myFile =
                             new System.IO.StreamReader(file);
                            String message = myFile.ReadToEnd();
                            myFile.Close();
                            messenger.sendMessage(message);
                            logger.Info("Sends message number: " + messageNum++);
                            Thread.Sleep(delay);

                        }
                    }
            }

            Console.WriteLine("Main program execeution finishes");

        }
    }
}
