using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.IO;
using StreamSimulator;
using System.Configuration;

namespace StreamSimulator
{
    class Program
    {
        static void Main(string[] args)
        {
            int messageMode = 1;
            String filePath = ConfigurationManager.AppSettings.Get("StreamingDirectory");
            bool convertToGATE = Convert.ToBoolean(ConfigurationManager.AppSettings.Get("ConvertToGATE"));

            if (args.Length == 1)
            {
                messageMode = Convert.ToInt32(args[0]);
            }
            else if (args.Length == 2)
            {
                messageMode = Convert.ToInt32(args[0]);
                filePath = args[1];
            }

            string[] filePaths = Directory.GetFiles(filePath, "*");
            List<String> s = new List<string>(filePaths);
            StreamFiles streamer = new StreamFiles(s, convertToGATE);

            switch (messageMode)
            {
                case 1:

                    streamer.sendOnce();
                    break;

                case 2:
                    streamer.sendLoop();
                    break;
                
                default:
                    Console.WriteLine("choose streaming mode 1 or 2");
                    break;
            }

            
        }
    }
}
